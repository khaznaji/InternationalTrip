<?php
namespace App\Controller\Mobile;

use App\Entity\Div;
use App\Repository\DivRepository;
use App\Repository\TypesRepository;
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
 * @Route("/mobile/div")
 */
class DivMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(DivRepository $divRepository): Response
    {
        $divs = $divRepository->findAll();

        if ($divs) {
            return new JsonResponse($divs, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, TypesRepository $typesRepository): JsonResponse
    {
        $div = new Div();

        return $this->manage($div, $typesRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, DivRepository $divRepository, TypesRepository $typesRepository): Response
    {
        $div = $divRepository->find((int)$request->get("id"));

        if (!$div) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($div, $typesRepository, $request, true);
    }

    public function manage($div, $typesRepository, $request, $isEdit): JsonResponse
    {   
        $types = $typesRepository->find((int)$request->get("types"));
        if (!$types) {
            return new JsonResponse("types with id " . (int)$request->get("types") . " does not exist", 203);
        }
        
        
        $div->setUp(
            $types,
            $request->get("nom"),
            $request->get("numtel"),
            $request->get("adresse")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($div);
        $entityManager->flush();

        return new JsonResponse($div, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, DivRepository $divRepository): JsonResponse
    {
        $div = $divRepository->find((int)$request->get("id"));

        if (!$div) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($div);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, DivRepository $divRepository): Response
    {
        $divs = $divRepository->findAll();

        foreach ($divs as $div) {
            $entityManager->remove($div);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
