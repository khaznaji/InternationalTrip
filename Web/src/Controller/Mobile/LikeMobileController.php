<?php
namespace App\Controller\Mobile;

use App\Entity\Like;
use App\Repository\LikeRepository;
use App\Repository\DivRepository;
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
 * @Route("/mobile/like")
 */
class LikeMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(LikeRepository $likeRepository): Response
    {
        $likes = $likeRepository->findAll();

        if ($likes) {
            return new JsonResponse($likes, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, DivRepository $divRepository): JsonResponse
    {
        $like = new Like();

        return $this->manage($like, $divRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, LikeRepository $likeRepository, DivRepository $divRepository): Response
    {
        $like = $likeRepository->find((int)$request->get("id"));

        if (!$like) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($like, $divRepository, $request, true);
    }

    public function manage($like, $divRepository, $request, $isEdit): JsonResponse
    {   
        $div = $divRepository->find((int)$request->get("div"));
        if (!$div) {
            return new JsonResponse("div with id " . (int)$request->get("div") . " does not exist", 203);
        }
        
        
        $like->setUp(
            $div
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($like);
        $entityManager->flush();

        return new JsonResponse($like, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, LikeRepository $likeRepository): JsonResponse
    {
        $like = $likeRepository->find((int)$request->get("id"));

        if (!$like) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($like);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, LikeRepository $likeRepository): Response
    {
        $likes = $likeRepository->findAll();

        foreach ($likes as $like) {
            $entityManager->remove($like);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
