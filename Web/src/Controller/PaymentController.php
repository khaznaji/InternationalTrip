<?php

namespace App\Controller;

use App\Entity\Reservation;
use App\Entity\Evenement;
use App\Repository\ReservationRepository;
use Stripe\Checkout\Session;
use Stripe\Stripe;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class PaymentController extends AbstractController
{
    /**
     * @Route("/payment/pay", name="payment_p")
     */
    public function index(): Response
    {
        return $this->render('payment/index.html.twig', [
            'controller_name' => 'PaymentController',
        ]);
    }

    /**
     * @Route("/checkout/{idreservation}", name="checkoutp")
     */
    public function checkout($stripeSK, SessionInterface $session, $idreservation): Response
    {
        $idreservation= $this->getDoctrine()->getRepository(Reservation::class)->find($idreservation);
         $total=$idreservation->getPrix();


        Stripe::setApiKey($stripeSK);
        $session = Session::create([
            'payment_method_types' => ['card'],
            'line_items'           => [
                [
                    'price_data' => [
                        'currency'     => 'eur',
                        'product_data' => [
                            'name' => 'Paiement',
                        ],
                        'unit_amount'  => $total,
                    ],
                    'quantity'   => 100,
                ]
            ],
            'mode'                 => 'payment',
            'success_url'          => $this->generateUrl('success_url_p', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url'           => $this->generateUrl('cancel_url_p', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return $this->redirect($session->url, 303);
    }

    /**
     * @Route("/success-url/pay", name="success_url_p")
     */
    public function successUrl(): Response
    {
        return $this->render('payment/success.html.twig', []);
    }

    /**
     * @Route("/success", name="success")
     */
    public function success(): Response
    {
        return $this->render('payment/done.html.twig', []);
    }

    /**
     * @Route("/cancel-url/pay", name="cancel_url_p")
     */
    public function cancelUrl(): Response
    {
        return $this->render('payment/cancel.html.twig', []);
    }
}









