<?php
namespace App\Controller\Mobile;

use App\Entity\Pays;
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
 * @Route("/mobile/pays")
 */
class PaysMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(PaysRepository $paysRepository): Response
    {
        $payss = $paysRepository->findAll();

        if ($payss) {
            return new JsonResponse($payss, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request): JsonResponse
    {
        $pays = new Pays();

        return $this->manage($pays, $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, PaysRepository $paysRepository): Response
    {
        $pays = $paysRepository->find((int)$request->get("id"));

        if (!$pays) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($pays, $request, true);
    }

    public function manage($pays, $request, $isEdit): JsonResponse
    {   
        
        $pays->setUp(
            $request->get("pays")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($pays);
        $entityManager->flush();

        return new JsonResponse($pays, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, PaysRepository $paysRepository): JsonResponse
    {
        $pays = $paysRepository->find((int)$request->get("id"));

        if (!$pays) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($pays);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, PaysRepository $paysRepository): Response
    {
        $payss = $paysRepository->findAll();

        foreach ($payss as $pays) {
            $entityManager->remove($pays);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
