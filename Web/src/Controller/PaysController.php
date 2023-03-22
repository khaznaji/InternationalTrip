<?php

namespace App\Controller;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Entity\Pays;
use App\Repository\PaysRepository;
use App\Form\PaysType;
use Symfony\Component\HttpFoundation\Request;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Notifier\TexterInterface;
use Symfony\Component\Notifier\Message\SmsMessage;
use Symfony\Component\Notifier\Message;
use Twilio\Rest\Client;
use Symfony\Component\Serializer\Nomalizer;

use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse ;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class PaysController extends AbstractController
{
    private $twilio;

    public function __construct(Client $twilio) {
        $this->twilio = $twilio;

    }
    /**
     * @Route("/pays", name="app_pays") 
     */
    public function index(): Response
    {
        return $this->render('pays/index.html.twig', [
            'controller_name' => 'PaysController',
        ]);
    }
         /**
  * @Route("/newPays", name="newPays")
   
  */

    public function newPays(Request $request )
    {   $pays= new Pays();
        $form =$this->createForm (PaysType::class  , $pays);
        $form -> add ('Ajouter', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
    
      

     
            $pays= $form->getData();
            $em= $this->getDoctrine()->getManager();
            $em->persist ($pays);
            $em->flush();
           /* $sender = $this->getParameter('twilio_number');
            $message = $this->twilio->messages->create(
                '+21650190000', // Send text to this number
                array(
                    'from' => $sender, // My Twilio phone number
                    'body' => 'Un hebergement a été ajouté '
                )
            );*/
    
            return $this->redirectToRoute('newPays');

        }
        return $this->render('pays/index.html.twig', [
            'form' => $form -> createView (),
        ]);
    }

    /**
     * @Route("/AffichePays", name="AffichePays")
     */
    public function AffichePays(){
        $repository=$this->getDoctrine()->getRepository(Pays::class); 
        $pays=$repository->findAll();
        return $this->render('pays/Affiche.html.twig', 
        ['aa'=>$pays]); 
     }
     
        /**
         * @Route ("/deletePays/{id}",name="deletePays")
         */
    public function deletePays($id)
    {
        $em=$this->getDoctrine()->getManager();
        $pays= $em ->getRepository (Pays::class)->find ($id);
        $em->remove($pays);
        $em->flush();
         return $this->redirectToRoute('AffichePays') ;
    }
      /**
     * @Route("/updatePays/{id}", name="updatePays")
     */
    public function updatePays(Request $request, $id)
    {
        $em= $this->getDoctrine()->getManager();
        $pays= $em ->getRepository (Pays::class)->find ($id);
        $form =$this->createForm (PaysType::class, $pays);
        $form -> add ('Update/Modifier', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted() && $form-> isValid ())
        {
            $em->flush();
            return $this->redirectToRoute('AffichePays');
        }
        return $this->render('pays/Modifier.html.twig', [
            'form_title'=> "modifier un Event",
            'w' => $form -> createView (),
        ]);
    }

    /**
     * @Route("/listpays", name="listpays")
     */
    public function listpays(PaysRepository $paysRepository): Response
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
//rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $paysRepository= $this->getDoctrine()->getRepository(Pays::class);
        //faire appel Ã  la fonction findAll()
        $payss = $paysRepository->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();


    // Retrieve the HTML generated in our twig file
$html = $this->renderView('pays/listpays.html.twig', ['aa' => $payss,]);

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
     * @Route("/displayPays",name="displayPays")
     */
    public function displayPays(NormalizerInterface $Normalizer) {
        $repository = $this->getDoctrine()->getRepository(Pays::class);
        $hebergement=$repository->findAll();
        $jsonContent =$Normalizer ->normalize($hebergement,'json',['groups'=>'post:read']);
        return $this->render('pays/all.html.twig', [
            'data'=> $jsonContent,
        ]);
        return new Response(json_encode($jsonContent));
    }
}
