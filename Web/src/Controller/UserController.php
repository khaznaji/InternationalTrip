<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\EditProfileType;
use Dompdf\Dompdf;
use Dompdf\Options;
/**
 * @Route("/user")
 */
class UserController extends AbstractController
{
    /**
     * @Route("/", name="app_user_index", methods={"GET"})
     */
    public function index(UserRepository $userRepository): Response
    {
        $user=$this->getUser();
        return $this->render('user/index.html.twig', [
            'users' => $userRepository->findAll(),
            "user"=>$user
        ]);
    }

    /**
     * @Route("/new", name="app_user_new", methods={"GET", "POST"})
     */
    public function new(Request $request, UserRepository $userRepository): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $userRepository->add($user);
            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('user/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="app_user_show", methods={"GET"})
     */
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="app_user_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, User $user, UserRepository $userRepository): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $userRepository->add($user);
            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('user/edit.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="app_user_delete", methods={"POST"})
     */
    public function delete(Request $request, User $user, UserRepository $userRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
            $userRepository->remove($user);
        }

        return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
    }

    /**
    * @Route("/pdf/profil", name="app_pdf")
    */
    public function pdf()
    {
        $user=$this->getUser();
        $pdfOptions = new Options();
    $pdfOptions->set('defaultFont', 'Arial');
    
    // Instantiate Dompdf with our options
    $dompdf = new Dompdf($pdfOptions);
    
    // Retrieve the HTML generated in our twig file
    $html = $this->renderView('user/pdf.html.twig', [
        'title' => "Welcome to our PDF Test",
        'user'=>$user,
        
    ]);
    
    // Load HTML to Dompdf
    $dompdf->loadHtml($html);
    
    // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
    $dompdf->setPaper('A4', 'landscape');

    // Render the HTML as PDF
    $dompdf->render();

    // Store PDF Binary Data
    $output = $dompdf->output();
    
    // In this case, we want to write the file in the public directory
    $publicDirectory = $this->getParameter('kernel.project_dir') . '/public';
    // e.g /var/www/project/public/mypdf.pdf
    $pdfFilepath =  $publicDirectory . '/'.$user->getNom().' '.$user->getPrenom().'.pdf';
    
    // Write file to the desired path
    file_put_contents($pdfFilepath, $output);
    
    $content = file_get_contents($pdfFilepath);

    // Send some text response
    $response = new Response();

    //set headers
    $response->headers->set('Content-Type', 'application/pdf');
    $response->headers->set('Content-Disposition', 'attachment;filename=mypdf'.$user->getNom().' '.$user->getPrenom().'.pdf"');

    $response->setContent($content);
    return $response;

    }
     /**
     * @Route("/modifier/profil", name="app_profil_edit")
     */
    public function editp(Request $request)
    {
        $user = $this->getUser();
        $form = $this->createForm(EditProfileType::class, $user);
        
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            
            $this->addFlash('message','profil modifier');
            return $this->redirectToRoute('app_profil');
        }

        return $this->render('editprofil.html.twig', [
            'form' => $form->createView(),
        ]);
    }
}
