<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Entity\Types;
use App\Repository\TypesRepository;
use App\Form\TypesType;
use Doctrine\DBAL\Types\Types as TypesTypes;
use Symfony\Component\HttpFoundation\Request;
use Knp\Component\Pager\PaginatorInterface;


class TypesController extends AbstractController
{
    /**
     * @Route("/types", name="app_types") 
     */
    public function index(): Response
    {
        return $this->render('types/index.html.twig', [
            'controller_name' => 'TypesController',
        ]);
    }
     /**
     * @return string
     */
    private function generateUniqueFileName()
    {
        // md5() reduces the similarity of the file names generated by
        // uniqid(), which is based on timestamps
        return md5(uniqid());
    }
         /**
  * @Route("/newTypes", name="newTypes")
   
  */

    public function newTypes(Request $request )
    {   $types= new Types();
        $form =$this->createForm (TypesType::class  , $types);
        $form -> add ('Ajouter', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted()){
            $file = $form->get('image')->getData();

            $fileName = $this->generateUniqueFileName() . '.' . $file->guessExtension();
  
            // moves the file to the directory where brochures are stored
            $file->move(
                $this->getParameter('brochures_directory'),
                $fileName
            );
  
            $types->setImage($fileName);
            $types= $form->getData();
            $em= $this->getDoctrine()->getManager();
            $em->persist ($types);
            $em->flush();
            return $this->redirectToRoute('newTypes');
        }
        return $this->render('types/index.html.twig', [
            'form' => $form -> createView (),
        ]);
    }

    /**
     * @Route("/AfficheTypes", name="AfficheTypes")
     */
    public function AfficheTypes(){
        $repository=$this->getDoctrine()->getRepository(Types::class); 
        $types=$repository->findAll();
        return $this->render('types/Affiche.html.twig', 
        ['dd'=>$types]); 
     }
     
        /**
         * @Route ("/deleteTypes/{id}",name="deleteTypes")
         */
    public function deleteTypes($id)
    {
        $em=$this->getDoctrine()->getManager();
        $types= $em ->getRepository (Types::class)->find ($id);
        $em->remove($types);
        $em->flush();
         return $this->redirectToRoute('AfficheTypes') ;
    }
      /**
     * @Route("/updateTypes/{id}", name="updateTypes")
     */
    public function updateTypes(Request $request, $id)
    {
        $em= $this->getDoctrine()->getManager();
        $types= $em ->getRepository (Types::class)->find ($id);
        $form =$this->createForm (TypesType::class, $types);
        $form -> add ('Update/Modifier', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted() && $form-> isValid ())
        {
            $file = $form->get('image')->getData();
  
            $fileName = $this->generateUniqueFileName() . '.' . $file->guessExtension();

            // moves the file to the directory where brochures are stored
            $file->move(
                $this->getParameter('brochures_directory'),
                $fileName
            );

            $types->setImage($fileName);
            $em->flush();
            return $this->redirectToRoute('AfficheTypes');
        }
        return $this->render('types/Modifier.html.twig', [
            'form_title'=> "modifier un Event",
            'w' => $form -> createView (),
        ]);
    }

}