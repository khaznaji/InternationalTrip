<?php

namespace App\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Trip;
use App\Repository\TripRepository;
use App\Form\TripType;
use Symfony\Component\Serializer\Nomalizer;

use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse ;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
class TripController extends AbstractController
{
    /**
     * @Route("/trip", name="app_trip")
     */
    public function index(): Response
    {
        return $this->render('trip/index.html.twig', [
            'controller_name' => 'TripController',
        ]);
    }
   /**
  * @Route("/newTrip", name="newTrip")
   
  */

  public function newTrip(Request $request )
  {   $trip= new Trip();
      $form =$this->createForm (TripType::class, $trip);
      $form -> add ('Ajouter', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted()&& $form->isValid()){
          $trip= $form->getData();
          $em= $this->getDoctrine()->getManager();
          $em->persist ($trip);
          $em->flush();
          
          return $this->redirectToRoute('newTrip');
      }
      return $this->render('trip/index.html.twig', [
          'form' => $form -> createView (),
      ]);
  }

  /**
   * @Route("/AfficheTrip", name="AfficheTrip")
   */
  public function AfficheTrip(){
      $repository=$this->getDoctrine()->getRepository(Trip::class); 
      $trip=$repository->findAll();
      return $this->render('trip/Affiche.html.twig', 
      ['aa'=>$trip]); 
   }
   
      /**
       * @Route ("/deleteTrip/{id}",name="deleteTrip")
       */
  public function deleteTrip($id)
  {
      $em=$this->getDoctrine()->getManager();
      $trip= $em->getRepository (Trip::class)->find($id);
      $em->remove($trip);
      $em->flush();
       return $this->redirectToRoute('AfficheTrip') ;
  }
    /**
   * @Route("/updateTrip/{id}", name="updateTrip")
   */
  public function updateTrip(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $trip= $em ->getRepository (Trip::class)->find ($id);
      $form =$this->createForm (TripType::class, $trip);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form->isValid())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheTrip');
      }
      return $this->render('trip/Modifier.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
  
    /**
     * @Route("/displayTrip",name="displayTrip")
     */
    public function displayTrip(NormalizerInterface $Normalizer) {
        $repository = $this->getDoctrine()->getRepository(Trip::class);
        $hebergement=$repository->findAll();
        $jsonContent =$Normalizer ->normalize($hebergement,'json',['groups'=>'post:read']);
        return $this->render('trip/all.html.twig', [
            'data'=> $jsonContent,
        ]);
        return new Response(json_encode($jsonContent));
    }
}

