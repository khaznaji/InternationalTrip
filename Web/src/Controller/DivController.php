<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Div;
use App\Repository\DivRepository;
use App\Form\DivType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;

// Include Dompdf required namespaces
use Dompdf\Dompdf;
use Dompdf\Options;
// Include PhpSpreadsheet required namespaces
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Writer\Xlsx;

class DivController extends AbstractController
{
    /**
     * @Route("/div", name="app_div")
     */
    public function index(): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('Div/list.html.twig', [
            'title' => "Welcome to our PDF Test"
        ]);
        
        // Load HTML to Dompdf
        $dompdf->loadHtml($html);
        
        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);
        return $this->render('div/index.html.twig', [
            'controller_name' => 'DivController',
        ]);
    }
    
      /**
  * @Route("/newDiv", name="newDiv")
   
  */

  public function newDiv(Request $request )
  {   $div= new Div();
    $form =$this->createForm (DivType::class, $div);
    $form -> add ('Ajouter', SubmitType::Class);
    $form ->handleRequest($request);
    if ($form->isSubmitted()){
       
        $div= $form->getData();
        $em= $this->getDoctrine()->getManager();
        $em->persist ($div);
        $em->flush();
        return $this->redirectToRoute('newDiv');
    }
    return $this->render('div/index.html.twig', [
        'form' => $form -> createView (),
    ]);
  }
   /**
     * @Route("/AfficheDiv", name="AfficheDiv")
     */
    public function AfficheDiv(){
        $repository=$this->getDoctrine()->getRepository(Div::class); 
        $div=$repository->findAll();
        return $this->render('div/Affiche.html.twig', 
        ['dd'=>$div]); 
     }
      /**
     * @Route("/AfficheDivFront", name="AfficheDivFront")
     */
    public function AfficheDivFront(){
        $repository=$this->getDoctrine()->getRepository(Div::class); 
        $div=$repository->findAll();
        return $this->render('div/AfficheFront.html.twig', 
        ['dd'=>$div]); 
     }
            /**
         * @Route ("/deleteDiv/{id}",name="deleteDiv")
         */
    public function deleteDiv($id)
    {
        $em=$this->getDoctrine()->getManager();
        $div= $em ->getRepository (Div::class)->find($id);
        $em->remove($div);
        $em->flush();
        
         return $this->redirectToRoute('AfficheDiv') ;
    }
      /**
     * @Route("/updateDiv/{id}", name="updateDiv")
     */
    public function updateDiv(Request $request, $id)
    {
        $em= $this->getDoctrine()->getManager();
        $div= $em ->getRepository (Div::class)->find ($id);
        $form =$this->createForm (DivType::class, $div);
        $form -> add ('Update/Modifier', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted()){
           
            $div= $form->getData();
            $em= $this->getDoctrine()->getManager();
            $em->persist ($div);
            $em->flush();
            return $this->redirectToRoute('AfficheDiv');
        }
        return $this->render('div/Modifier.html.twig', [
            'form_title'=> "modifier un Event",
            'form' => $form -> createView (),
        ]);
    }
    /**
     * @Route("/Trier", name="Trier")
     */
    public function Trier(Request $request): Response
    {
        $repository = $this->getDoctrine()->getRepository(Div::class);
        $Div = $repository->findByNom();

        return $this->render('Div/Affiche.html.twig', [
            'dd' =>  $Div,
        ]);
    }
}
