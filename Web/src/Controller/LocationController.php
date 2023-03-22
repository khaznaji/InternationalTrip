<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Location;
use App\Form\LocationType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;




class LocationController extends AbstractController
{
    /**
     * @Route("/location", name="app_location")
     */
    public function index(): Response
    {
        return $this->render('location/index.html.twig', [
            'controller_name' => 'LocationController',
        ]);
    }
     /**
     * @Route("/afficheL", name="afficheL")
     */
    public function afficheL(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Location::class);
        //faire appel Ã  la fonction findAll()
        $location=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('location/afficheL.html.twig', [
            'c' => $location,
        ]);
    }
     /**
     * @Route("/frontl", name="frontC")
     */
    public function frontl(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Location::class);
        //faire appel Ã  la fonction findAll()
        $location=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('location/frontl.html.twig', [
            'c' => $location,
        ]);
    }
     /**
     * @Route("/suppl/{id}", name="suppL")
     */
    public function suppl($id): Response

    {
        //récupérer le classroom à supprimer
        $location=$this->getDoctrine()->getRepository(Location::class)->find($id);
        //on passe à la suppression
        $em=$this->getDoctrine()->getManager();
        $em->remove($location);
        $em->flush();

        return $this->redirectToRoute('afficheL');
    }
     
    /**
     * @Route("/ajoutL", name="ajoutL")
     */
    public function ajoutL(Request $request): Response
    {
        //crÃ©ation du formulaire

        $c= new Location();
        $form = $this->createForm(LocationType::class, $c);
        $form -> add ('Ajouter', SubmitType::Class);
        //rÃ©cupÃ©rer les donnÃ©es saisies depuis la requete http
        $form->handleRequest($request);
        if ($form->isSubmitted()&& $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($c);
            $em->flush();
            

            return $this->redirectToRoute('afficheL');
        }


        return $this->render('location/ajoutL.html.twig', [
            'form' => $form->createView(),
        ]);

    }
    /**
     * @Route("/modifL/{id}", name="modifL")
     */
    public function modifL(Request $request,$id): Response
    {
        //rÃ©cupÃ©rer le classroom Ã  modifier
        $location=$this->getDoctrine()->getRepository(Location::class)->find($id);
        //crÃ©ation du formulaire rempli
        $form=$this->createForm(LocationType::class,$location);
        //rÃ©cupÃ©rer les donnÃ©es saisies depuis la requete http
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();
            return $this->redirectToRoute('afficheL');
        }

        return $this->render('location/modifL.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    

}
