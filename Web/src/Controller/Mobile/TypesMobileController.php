<?php
namespace App\Controller\Mobile;

use App\Entity\Types;
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
 * @Route("/mobile/types")
 */
class TypesMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(TypesRepository $typesRepository): Response
    {
        $typess = $typesRepository->findAll();

        if ($typess) {
            return new JsonResponse($typess, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request): JsonResponse
    {
        $types = new Types();

        return $this->manage($types, $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, TypesRepository $typesRepository): Response
    {
        $types = $typesRepository->find((int)$request->get("id"));

        if (!$types) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($types, $request, true);
    }

    public function manage($types, $request, $isEdit): JsonResponse
    {   
        $file = $request->files->get("file");
        if ($file) {
            $imageFileName = md5(uniqid()) . '.' . $file->guessExtension();

            try {
                $file->move($this->getParameter('images_directory'), $imageFileName);
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
        
        $types->setUp(
            $request->get("types"),
            $imageFileName
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($types);
        $entityManager->flush();

        return new JsonResponse($types, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, TypesRepository $typesRepository): JsonResponse
    {
        $types = $typesRepository->find((int)$request->get("id"));

        if (!$types) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($types);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, TypesRepository $typesRepository): Response
    {
        $typess = $typesRepository->findAll();

        foreach ($typess as $types) {
            $entityManager->remove($types);
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
            $this->getParameter('images_directory') . "/" . $request->get("image")
        );
    }
    
}
