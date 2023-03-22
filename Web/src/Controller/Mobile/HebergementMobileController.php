<?php
namespace App\Controller\Mobile;

use App\Entity\Hebergement;
use App\Repository\HebergementRepository;
use App\Repository\PaysRepository;
use DateTime;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/mobile/hebergement")
 */
class HebergementMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(HebergementRepository $hebergementRepository): Response
    {
        $hebergements = $hebergementRepository->findAll();

        if ($hebergements) {
            return new JsonResponse($hebergements, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, PaysRepository $paysRepository): JsonResponse
    {
        $hebergement = new Hebergement();

        return $this->manage($hebergement, $paysRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, HebergementRepository $hebergementRepository, PaysRepository $paysRepository): Response
    {
        $hebergement = $hebergementRepository->find((int)$request->get("id"));

        if (!$hebergement) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($hebergement, $paysRepository, $request, true);
    }

    public function manage($hebergement, $paysRepository, $request, $isEdit): JsonResponse
    {   
        $pays = $paysRepository->find((int)$request->get("pays"));
        if (!$pays) {
            return new JsonResponse("pays with id " . (int)$request->get("pays") . " does not exist", 203);
        }
        
        $file = $request->files->get("file");
        if ($file) {
            $imageFileName = md5(uniqid()) . '.' . $file->guessExtension();

            try {
                $file->move($this->getParameter('brochures_directory'), $imageFileName);
            } catch (FileException $e) {
                dd($e);
            }
        } else {
            if ($request->get("image")) {
                $imageFileName = $request->get("image");
            } else {
                $imageFileName = "null";
            }
        }
        
        $hebergement->setUp(
            $pays,
            $request->get("titre"),
            $request->get("type"),
            (int)$request->get("prix"),
            $imageFileName,
            $request->get("adresse"),
            $request->get("periode"),
            $request->get("choix"),
            DateTime::createFromFormat("d-m-Y", $request->get("dateH"))
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($hebergement);
        $entityManager->flush();

        return new JsonResponse($hebergement, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, HebergementRepository $hebergementRepository): JsonResponse
    {
        $hebergement = $hebergementRepository->find((int)$request->get("id"));

        if (!$hebergement) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($hebergement);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, HebergementRepository $hebergementRepository): Response
    {
        $hebergements = $hebergementRepository->findAll();

        foreach ($hebergements as $hebergement) {
            $entityManager->remove($hebergement);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
    /**
     * @Route("/image/{image}", methods={"GET"})
     */
    public function getPicture(Request $request): BinaryFileResponse
    {
        return new BinaryFileResponse(
            $this->getParameter('brochures_directory') . "/" . $request->get("image")
        );
    }
    
}
