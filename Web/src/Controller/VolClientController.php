<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\VolClient;
use App\Repository\VolClientRepository;
use App\Form\VolClientType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Dompdf\Dompdf;
use Dompdf\Options;

class VolClientController extends AbstractController
{    
    /**
     * @Route("/vol/client", name="app_vol_client")
     */
    public function index(): Response
    {
        return $this->render('vol_client/index.html.twig', [
            'controller_name' => 'VolClientController',
        ]);
    }
     /**
     * @Route("/AfficheVolClient", name="AfficheVolClient")
     */
    public function AfficheVolClient(){
        $repository=$this->getDoctrine()->getRepository(VolClient::class); 
        $volClient=$repository->findAll();
        return $this->render('vol_client/Affiche.html.twig', 
        ['aa'=>$volClient]); 
     }
       /**
     * @Route("/AfficheVolClientFront", name="AfficheVolClientFront")
     */
    public function AfficheVolClientFront(){
        $repository=$this->getDoctrine()->getRepository(VolClient::class); 
        $volClient=$repository->findAll();
        return $this->render('vol_client/AfficheFront.html.twig', 
        ['aa'=>$volClient]); 
     }
      /**
  * @Route("/newVolClient", name="newVolClient")
   
  */

  public function newVolClient(Request $request,\Swift_Mailer $mailer )
  {   $volClient= new VolClient();
      $form =$this->createForm (VolClientType::class  , $volClient);
      $form -> add ('Ajouter', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted()&& $form-> isValid ()){
          $volClient= $form->getData();
          $em= $this->getDoctrine()->getManager();
          $message=(new \Swift_Message('Ajout Vol'));
          $message->setFrom("oumaymalyna.khaznaji@esprit.tn");
          $message->setTo($form["email"]->getData());
          $message->setBody(
            $this->renderView(
                'vol_client/listP.html.twig'
            ),
            'text/html'
        );
              
          $em->persist ($volClient);
          $em->flush();
          $mailer->send($message);

          return $this->redirectToRoute('AfficheVolClientFront');
      }
      return $this->render('vol_client/index.html.twig', [
          'form' => $form -> createView (),
      ]);
  }
   /**
         * @Route ("/deleteVolClient/{id}",name="deleteVolClient")
         */
        public function deleteVolClient($id)
        {
            $em=$this->getDoctrine()->getManager();
            $volClient= $em ->getRepository (VolClient::class)->find ($id);
            $em->remove($volClient);
            $em->flush();
             return $this->redirectToRoute('AfficheVolClient') ;
        }
         /**
         * @Route ("/deleteVolClientFront/{id}",name="deleteVolClientFront")
         */
        public function deleteVolClientFront($id)
        {
            $em=$this->getDoctrine()->getManager();
            $volClient= $em ->getRepository (VolClient::class)->find ($id);
            $em->remove($volClient);
            $em->flush();
             return $this->redirectToRoute('AfficheVolClientFront') ;
        }
            /**
   * @Route("/updateVolClient/{id}", name="updateVolClient")
   */
  public function updateVolClient(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $volClient= $em ->getRepository (VolClient::class)->find ($id);
      $form =$this->createForm (VolClientType::class, $volClient);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form-> isValid ())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheVolClient');
      }
      return $this->render('vol_client/Modifier.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
  /**
   * @Route("/updateVolClientFront/{id}", name="updateVolClientFront")
   */
  public function updateVolClientFront(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $volClient= $em ->getRepository (VolClient::class)->find ($id);
      $form =$this->createForm (VolClientType::class, $volClient);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form-> isValid ())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheVolClientFront');
      }
      return $this->render('vol_client/ModifierFront.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
/**
     * @Route("/listP", name="volclient_list")
     */
    public function listp(VolClientRepository $volClientRepository): Response
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
//rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $volClientRepository= $this->getDoctrine()->getRepository(VolClient::class);
        //faire appel Ã  la fonction findAll()
        $volClients = $volClientRepository->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();


    // Retrieve the HTML generated in our twig file
$html = $this->renderView('vol_client/listP.html.twig', ['aa' => $volClients,]);

    // Load HTML to Dompdf
$dompdf->loadHtml($html);

    // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
$dompdf->setPaper('A4', 'portrait');

    // Render the HTML as PDF
$dompdf->render();

    // Output the generated PDF to Browser (force download)
$dompdf->stream("mypdf.pdf", ["Attachment" => false]);
        return new Response("The PDF file has been succesfully generated !");
}
/**
     * @Route("/map", name="mappAction  ")
     */
  public function mappAction()
  { 
    return $this->render('vol_client/newMap.html.twig');
    }
}
