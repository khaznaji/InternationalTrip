<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\HebergementClient;
use App\Repository\HebergementClientRepository;
use App\Form\HebergementClientType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Dompdf\Dompdf;
use Dompdf\Options;
use Twilio\Rest\Client;

class HebergementClientController extends AbstractController
{
    private $twilio;

    public function __construct(Client $twilio) {
        $this->twilio = $twilio;

    }
    /**
     * @Route("/hebergement/client", name="app_hebergement_client")
     */
    public function index(): Response
    {
        return $this->render('hebergement_client/index.html.twig', [
            'controller_name' => 'HebergementClientController',
        ]);
    }
      /**
     * @Route("/AfficheHebergementClient", name="AfficheHebergementClient")
     */
    public function AfficheVolClient(){
        $repository=$this->getDoctrine()->getRepository(HebergementClient::class); 
        $hebergementClient=$repository->findAll();
        return $this->render('hebergement_client/Affiche.html.twig', 
        ['aa'=>$hebergementClient]); 
     }
       /**
     * @Route("/AfficheHebergementClientFront", name="AfficheHebergementClientFront")
     */
    public function AfficheHebergementClientFront(){
        $repository=$this->getDoctrine()->getRepository(HebergementClient::class); 
        $hebergementClient=$repository->findAll();
        return $this->render('hebergement_client/AfficheFront.html.twig', 
        ['aa'=>$hebergementClient]); 
     }
      /**
  * @Route("/newHebergementClient", name="newHebergementClient")
   
  */

  public function newHebergementClient(Request $request,\Swift_Mailer $mailer )
  {   $hebergementClient= new HebergementClient();
      $form =$this->createForm (HebergementClientType::class  , $hebergementClient);
      $form -> add ('Ajouter', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted()&& $form-> isValid ()){
          $hebergementClient= $form->getData();
          $em= $this->getDoctrine()->getManager();
          $message=(new \Swift_Message('Ajout Hebergement'));
          $message->setFrom("oumaymalyna.khaznaji@esprit.tn");
          $message->setTo($form["email"]->getData());
          $message->setBody(
              $this->renderView(
                  'hebergement_client/listP.html.twig'
              ),
              'text/html'
          );
          $em->persist ($hebergementClient);
          $em->flush();
          $mailer->send($message);
          $sender = $this->getParameter('twilio_number');
          $message = $this->twilio->messages->create(
              '+21650190000', // Send text to this number
              array(
                  'from' => $sender, // My Twilio phone number
                  'body' => 'Un hebergement a été ajouté '
              )
          );

          return $this->redirectToRoute('AfficheHebergementClientFront');
      }
      return $this->render('hebergement_client/index.html.twig', [
          'form' => $form -> createView (),
      ]);
  }
   /**
         * @Route ("/deleteHebergementClient/{id}",name="deleteHebergementClient")
         */
        public function deleteVolClient($id)
        {
            $em=$this->getDoctrine()->getManager();
            $hebergementClient= $em ->getRepository (HebergementClient::class)->find ($id);
            $em->remove($hebergementClient);
            $em->flush();
           
             return $this->redirectToRoute('AfficheHebergementClient') ;
        }
         /**
         * @Route ("/deleteHebergementClientFront/{id}",name="deleteHebergementClientFront")
         */
        public function deleteHebergementClientFront($id)
        {
            $em=$this->getDoctrine()->getManager();
            $hebergementClient= $em ->getRepository (HebergementClient::class)->find ($id);
            $em->remove($hebergementClient);
            $em->flush();
             return $this->redirectToRoute('AfficheHebergementClientFront') ;
        }
            /**
   * @Route("/updateHebergementClient/{id}", name="updateHebergementClient")
   */
  public function updateHebergementClient(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $hebergementClient= $em ->getRepository (HebergementClient::class)->find ($id);
      $form =$this->createForm (HebergementClientType::class, $hebergementClient);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form-> isValid ())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheHebergementClient');
      }
      return $this->render('hebergement_client/Modifier.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
  /**
   * @Route("/updateHebergementClientFront/{id}", name="updateHebergementClientFront")
   */
  public function updateHebergementClientFront(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $hebergementClient= $em ->getRepository (HebergementClient::class)->find ($id);
      $form =$this->createForm (HebergementClientType::class, $hebergementClient);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form-> isValid ())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheHebergementClientFront');
      }
      return $this->render('hebergement_client/ModifierFront.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
/**
     * @Route("/listP", name="hebergementclient_list")
     */
    public function listp(HebergementClientRepository $hebergementClientRepository): Response
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
//rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $hebergementClientRepository= $this->getDoctrine()->getRepository(HebergementClient::class);
        //faire appel Ã  la fonction findAll()
        $HebergementClients = $hebergementClientRepository->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();


    // Retrieve the HTML generated in our twig file
$html = $this->renderView('hebergement_client/listP.html.twig', ['aa' => $HebergementClients,]);

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
  
}

