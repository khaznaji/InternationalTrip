<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Locationc;
use App\Form\LocationcType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class LocationcController extends AbstractController
{
    /**
     * @Route("/locationc", name="app_locationc")
     */
    public function index(): Response
    {
        return $this->render('locationc/index.html.twig', [
            'controller_name' => 'LocationcController',
        ]);
    }
    /**
     * @Route("/afficheLc", name="afficheLc")
     */
    public function afficheLc(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Locationc::class);
        //faire appel Ã  la fonction findAll()
        $locationc=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('locationc/afficheLc.html.twig', [
            'c' => $locationc,
        ]);
    }
    /**
     * @Route("/backafficheLc", name="backafficheLc")
     */
    public function backafficheLc(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Locationc::class);
        //faire appel Ã  la fonction findAll()
        $locationc=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('locationc/backafficheLc.html.twig', [
            'c' => $locationc,
        ]);
    }
     /**
     * @Route("/frontlc", name="frontCc")
     */
    public function frontlc(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Locationc::class);
        //faire appel Ã  la fonction findAll()
        $locationc=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('locationc/frontlc.html.twig', [
            'c' => $locationc,
        ]);
    }
    
     /**
     * @Route("/supplc/{id}", name="suppLc")
     */
    public function supplc($id): Response

    {
        //récupérer le classroom à supprimer
        $locationc=$this->getDoctrine()->getRepository(Locationc::class)->find($id);
        //on passe à la suppression
        $em=$this->getDoctrine()->getManager();
        $em->remove($locationc);
        $em->flush();

        return $this->redirectToRoute('afficheLc');
    }
     
    /**
     * @Route("/ajoutLc", name="ajoutLc")
     */
    public function ajoutLc(Request $request): Response
    {
        //crÃ©ation du formulaire

        $c= new Locationc();
        $form = $this->createForm(LocationcType::class, $c);
        $form -> add ('Ajouter', SubmitType::Class);
        //rÃ©cupÃ©rer les donnÃ©es saisies depuis la requete http
        $form->handleRequest($request);
        if ($form->isSubmitted()&& $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($c);
            $em->flush();
            

            return $this->redirectToRoute('afficheLc');
        }


        return $this->render('locationc/ajoutLc.html.twig', [
            'form' => $form->createView(),
        ]);

    }
    /**
     * @Route("/modifLc/{id}", name="modifLc")
     */
    public function modifLc(Request $request,$id): Response
    {
        //rÃ©cupÃ©rer le classroom Ã  modifier
        $locationc=$this->getDoctrine()->getRepository(Locationc::class)->find($id);
        //crÃ©ation du formulaire rempli
        $form=$this->createForm(LocationcType::class,$locationc);
        //rÃ©cupÃ©rer les donnÃ©es saisies depuis la requete http
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();
            return $this->redirectToRoute('afficheLc');
        }

        return $this->render('locationc/modifLc.html.twig', [
            'w' => $form->createView(),
        ]);
    }
    
}
