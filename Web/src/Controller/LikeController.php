<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Like;
use App\Repository\LikeRepository;
use App\Form\LikeType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
class LikeController extends AbstractController
{
    /**
     * @Route("/like", name="app_like")
     */
    public function index(): Response
    {
        return $this->render('Like/index.html.twig', [
            'controller_name' => 'LikeController',
        ]);
    }
     /**
     * @Route("/AfficheLike", name="AfficheLike")
     */
    public function AfficheLike(){
        $repository=$this->getDoctrine()->getRepository(Like::class); 
        $like=$repository->findAll();
        return $this->render('Like/Affiche.html.twig', 
        ['dd'=>$like]); 
     }
       /**
     * @Route("/AfficheLikeFront", name="AfficheLikeFront")
     */
    public function AfficheLikeFront(){
        $repository=$this->getDoctrine()->getRepository(Like::class); 
        $like=$repository->findAll();
        return $this->render('Like/AfficheFront.html.twig', 
        ['dd'=>$like]); 
     }
      /**
  * @Route("/newLike", name="newLike")
   
  */

  public function newLike(Request $request )
  {   $like= new Like();
      $form =$this->createForm (LikeType::class  , $like);
      $form -> add ('Ajouter', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted()){
          $like= $form->getData();
          $em= $this->getDoctrine()->getManager();
          $em->persist ($like);
          $em->flush();
          return $this->redirectToRoute('newLike');
      }
      return $this->render('Like/index.html.twig', [
          'form' => $form -> createView (),
      ]);
  }
   /**
         * @Route ("/deleteLike/{id}",name="deleteLike")
         */
        public function deleteLike($id)
        {
            $em=$this->getDoctrine()->getManager();
            $like= $em ->getRepository (Like::class)->find ($id);
            $em->remove($like);
            $em->flush();
             return $this->redirectToRoute('AfficheLike') ;
        }
         /**
         * @Route ("/deleteLikeFront/{id}",name="deleteLikeFront")
         */
        public function deleteLikeFront($id)
        {
            $em=$this->getDoctrine()->getManager();
            $like= $em ->getRepository (Like::class)->find ($id);
            $em->remove($like);
            $em->flush();
             return $this->redirectToRoute('AfficheLikeFront') ;
        }
    }