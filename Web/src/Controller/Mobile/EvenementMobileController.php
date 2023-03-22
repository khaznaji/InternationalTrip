<?php
namespace App\Controller\Mobile;

use App\Entity\Evenement;
use App\Repository\EvenementRepository;
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
 * @Route("/mobile/evenement")
 */
class EvenementMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(EvenementRepository $evenementRepository): Response
    {
        $evenements = $evenementRepository->findAll();

        if ($evenements) {
            return new JsonResponse($evenements, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request): JsonResponse
    {
        $evenement = new Evenement();

        return $this->manage($evenement, $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, EvenementRepository $evenementRepository): Response
    {
        $evenement = $evenementRepository->find((int)$request->get("id"));

        if (!$evenement) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($evenement, $request, true);
    }

    public function manage($evenement, $request, $isEdit): JsonResponse
    {   
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
        
        $evenement->setUp(
            $request->get("nom"),
            $request->get("type"),
            $imageFileName,
            (int)$request->get("nbrPlace"),
            DateTime::createFromFormat("d-m-Y", $request->get("date")),
            (int)$request->get("prix")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($evenement);
        $entityManager->flush();

        return new JsonResponse($evenement, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, EvenementRepository $evenementRepository): JsonResponse
    {
        $evenement = $evenementRepository->find((int)$request->get("id"));

        if (!$evenement) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($evenement);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, EvenementRepository $evenementRepository): Response
    {
        $evenements = $evenementRepository->findAll();

        foreach ($evenements as $evenement) {
            $entityManager->remove($evenement);
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
