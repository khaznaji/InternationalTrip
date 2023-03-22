<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\User;
use App\Repository\UserRepository;

class HomeController extends AbstractController
{
    /**
     * @Route("/homeb", name="app_homeb")
     */
    public function indexb(): Response
    {
        return $this->render('indexB.html.twig', [
            'controller_name' => 'HomeController',
              ]);
    }

    /**
     * @Route("/homef", name="app_homef")
     */
    public function indexf(): Response
    {
        return $this->render('indexF.html.twig', [
            'controller_name' => 'HomeController',
        ]);
    }
     /**
     * @Route("/admin", name="app_admin")
     */
    public function admin(UserRepository $UserRepository): Response
    {
       
        $users = $UserRepository->findByRole("ROLE_CLIENT");
        return $this->render('admin.html.twig', [
            
                'users' => $users
            ]);
    }
    /**
     * @Route("/profil", name="app_profil")
     */
    public function profil(): Response
    {
        return $this->render('profil.html.twig');
    }
}
