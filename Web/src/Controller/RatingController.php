<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Exception;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Rating;
use App\Form\RatingType;
use Snipe\BanBuilder\CensorWords;


class RatingController extends AbstractController
{
  
    /**
     * @Route("/rating")
     */
    public function indexFront(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Rating::class); 
        $rating=$repository->findAll();
        return $this->render('rating/afficheRatingFront.html.twig', 
        ['ratings'=>$rating]); 
    }
  /**
     * @Route("/ratingback")
     */
    public function indexBack(): Response
    {
        $repository=$this->getDoctrine()->getRepository(Rating::class); 
        $rating=$repository->findAll();
        return $this->render('rating/afficheRating.html.twig', 
        ['ratings'=>$rating]); 
    }
  

    /**
     * @Route("/newRating")
     */
    public function ajout(Request $request)
    {
        $rating = new Rating();


        $this->createForm(RatingType::class);
        $form = $this->createForm(RatingType::class, $rating)
            ->add('submit', SubmitType::class)
            ->handleRequest($request);
            $rating = new Rating();
            $form = $this->createForm(RatingType::class, $rating);
            $form->handleRequest($request);

        if ($form->isSubmitted()) {
            $censor = new CensorWords;
            $langs = array('fr','it','en-us','en-uk','de','es');
            $badwords = $censor->setDictionary($langs);
            $censor->setReplaceChar("*");
            $string = $censor->censorString($rating->getMsg());
            $rating->setMsg($string['clean']);
            $rating = $form->getData();

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($rating);
            $entityManager->flush();

            return $this->redirect('/rating/afficher');
        }

        return $this->render('rating/ajouterRating.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/updateRating/{idRating}" ,name="updateRating")
     */
    public function modifier(Request $request, $idRating)
    {
        $rating = $this->getDoctrine()->getRepository(Rating::class)->find($idRating);

        $this->createForm(RatingType::class);
        $form = $this->createForm(RatingType::class, $rating)
            ->add('submit', SubmitType::class)
            ->handleRequest($request);

        if ($form->isSubmitted()) {
            $rating = $form->getData();

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($rating);
            $entityManager->flush();

            return $this->redirect('/rating');
        }

        return $this->render('rating/modifierRatingFront.html.twig', [
            'rating' => $rating,
            'form' => $form->createView(),
        ]);
    }

  
            /**
         * @Route ("/delete/{id}",name="delete")
         */
        public function delete($id)
        {
            $em=$this->getDoctrine()->getManager();
            $rating= $em ->getRepository (Rating::class)->find($id);
            $em->remove($rating);
            $em->flush();
             return $this->redirectToRoute('/newRating') ;
        }
}
