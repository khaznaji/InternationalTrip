<?php
namespace App\Controller\Mobile;

use App\Entity\Reservation;
use App\Repository\ReservationRepository;
use App\Repository\EvenementRepository;
use App\Repository\UserRepository;
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
 * @Route("/mobile/reservation")
 */
class ReservationMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(ReservationRepository $reservationRepository): Response
    {
        $reservations = $reservationRepository->findAll();

        if ($reservations) {
            return new JsonResponse($reservations, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, EvenementRepository $evenementRepository, UserRepository $userRepository): JsonResponse
    {
        $reservation = new Reservation();

        return $this->manage($reservation, $evenementRepository,  $userRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, ReservationRepository $reservationRepository, EvenementRepository $evenementRepository, UserRepository $userRepository): Response
    {
        $reservation = $reservationRepository->find((int)$request->get("id"));

        if (!$reservation) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($reservation, $evenementRepository, $userRepository, $request, true);
    }

    public function manage($reservation, $evenementRepository, $userRepository, $request, $isEdit): JsonResponse
    {   
        $evenement = $evenementRepository->find((int)$request->get("evenement"));
        if (!$evenement) {
            return new JsonResponse("evenement with id " . (int)$request->get("evenement") . " does not exist", 203);
        }
        
        $user = $userRepository->find((int)$request->get("user"));
        if (!$user) {
            return new JsonResponse("user with id " . (int)$request->get("user") . " does not exist", 203);
        }
        
        
        $reservation->setUp(
            $evenement,
            $user,
            $request->get("typePaiement"),
            (int)$request->get("nbrPlace"),
            (int)$request->get("prix"),
            (int)$request->get("prixI")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($reservation);
        $entityManager->flush();

        return new JsonResponse($reservation, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, ReservationRepository $reservationRepository): JsonResponse
    {
        $reservation = $reservationRepository->find((int)$request->get("id"));

        if (!$reservation) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($reservation);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, ReservationRepository $reservationRepository): Response
    {
        $reservations = $reservationRepository->findAll();

        foreach ($reservations as $reservation) {
            $entityManager->remove($reservation);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
