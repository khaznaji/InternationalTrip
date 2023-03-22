<?php
namespace App\Controller\Mobile;

use App\Entity\Chauffeur;
use App\Repository\ChauffeurRepository;
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
 * @Route("/mobile/chauffeur")
 */
class ChauffeurMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(ChauffeurRepository $chauffeurRepository): Response
    {
        $chauffeurs = $chauffeurRepository->findAll();

        if ($chauffeurs) {
            return new JsonResponse($chauffeurs, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request): JsonResponse
    {
        $chauffeur = new Chauffeur();

        return $this->manage($chauffeur, $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, ChauffeurRepository $chauffeurRepository): Response
    {
        $chauffeur = $chauffeurRepository->find((int)$request->get("id"));

        if (!$chauffeur) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($chauffeur, $request, true);
    }

    public function manage($chauffeur, $request, $isEdit): JsonResponse
    {   
        
        $chauffeur->setUp(
            $request->get("nom"),
            $request->get("prenom"),
            $request->get("sexe"),
            (int)$request->get("num"),
            $request->get("disponibilite")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($chauffeur);
        $entityManager->flush();

        return new JsonResponse($chauffeur, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, ChauffeurRepository $chauffeurRepository): JsonResponse
    {
        $chauffeur = $chauffeurRepository->find((int)$request->get("id"));

        if (!$chauffeur) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($chauffeur);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, ChauffeurRepository $chauffeurRepository): Response
    {
        $chauffeurs = $chauffeurRepository->findAll();

        foreach ($chauffeurs as $chauffeur) {
            $entityManager->remove($chauffeur);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
