<?php

namespace App\Controller;

use App\Entity\User;
use App\Security\EmailVerifier;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mime\Address;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\UserRepository;

use Symfony\Component\Security\Csrf\TokenGenerator;

use phpDocumentor\Reflection\DocBlock\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;


use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;

use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;
class FosUsermobileController extends AbstractController
{
    private $emailVerifier;

    public function __construct(EmailVerifier $emailVerifier)
    {
        $this->emailVerifier = $emailVerifier;
    }
    /**
     * @Route("/fos/usermobile", name="fos_usermobile")
     */
    public function index(): Response
    {
        return $this->render('fos_usermobile/index.html.twig', [
            'controller_name' => 'FosUsermobileController',
        ]);
    }
    /**
     * @Route("/User_Access_JSON",name="User_Access_JSON")
     */
    function SignIn(Request $request,NormalizerInterface $normalizer, UserPasswordEncoderInterface $encoder )
    {  $user = new User();
        //$username = $request->request->get("email");
        $username = $request->query->get("email");
        $password = $request->query->get("password");
        $hash =$encoder->encodePassword($user,$password);
        $em= $this->getDoctrine()->getManager();
        $user=$em->getRepository(User::class)->findOneBy(['email' => $username]);


        if($user)
        {

            /*
                          if(password_verify($password,$user->getPassword()))
                            {
                             $json=$normalizer->normalize( $user,'json',['groups'=>'post:read']);
                             return new Response(json_encode($json));
                            }*/
    $x=0;
            if($password==$user->getPassword())
            {
                $json=$normalizer->normalize( $user,'json',['groups'=>'post:read']);
                return new Response(json_encode($json));
            }

            else
            {
                return new Response("password not found ");
            }

            //return new Response(json_encode($json));
        }
        else
        {
            return new Response("email not found ");
        }



    }



    /**
     * @Route("/User_add_JSON",name="User_add_JSON")
     */
    function AjoutUser_JSON(NormalizerInterface $normalizer , Request $request , UserPasswordEncoderInterface $encoder,\Swift_Mailer $mailer ,TokenGeneratorInterface $tokenGenerator
    ): Response
    {



        $em=$this->getDoctrine()->getManager();
        $user = new User();
        //  $user->setIdentifiant($request->get('id'));
        $user->setNom($request->get('nom'));
        $user->setEmail($request->get('email'));
        $user->setTel($request->get('tel'));
        $user->setRoles($request->get('roles'));

        //$hash =$encoder->encodePassword($user,$request->get('password'));
        //$user->setPassword($hash);
        $user->setPassword($request->get('password'));
        $url = $user->getIsVerified();

        $user->setIsVerified(md5(uniqid()));
        $em->persist($user);
        $em->flush();

        $this->emailVerifier->sendEmailConfirmation('app_verify_email', $user,
            (new TemplatedEmail())
                ->from(new Address('oumaymalyna.khaznaji@esprit.tn', 'sportify'))
                ->to($user->getEmail())
                ->subject('Please Confirm your Email')
                ->htmlTemplate('fos_usermobile/Activation_JSON.html.twig')
        );
        $json=$normalizer->normalize( $user,'json',['groups'=>'post:read']);
        return new Response(json_encode($json));


    }
    /**
     * @param Request $request
     * @return RedirectResponse|Response
     * @Route("/Activation_JSON/{token}",name="Activation_JSON")
     */
    function Activation_JSON(NormalizerInterface $normalizer ,UserRepository $repo, $token,\Swift_Mailer $mailer)
    {
        $user = $repo->findOneBy(['isVerified'=>$token]);

        if(!$user)
        {
            throw $this->createNotFoundException('Cet Utilisateur n\'existe pas ');

        }
        $user->setIsVerified(null);
        $em =$this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();

       /* $qrCode = $qrcodeService->qrcode($user->getEmail());
        $path=dirname(__DIR__, 2).'/public/IMG_QRC/';
        //$attachement = (new \Swift_Attachment($path.$user->getIdentifiant().'.jpg'));
        $attachement = \Swift_Attachment::fromPath($path.$user->getEmail().'.png', 'image/jpeg');
        $message = ( new \Swift_Message('Recemment Inscrit '))

            ->setFrom('tunisiefoot2@gmail.com')
            ->setTo($user->getEmail())
            ->setBody($this->renderView('sign_in/Inscrit.html.twig',['Nom'=>$user->getPrenom()]),'text/html')
            ->attach($attachement);
        ;

        $mailer->send($message);

*/

// Attach it to the message

        $this->addFlash('message','Vous avez bien activer votre compte ');
        $json=$normalizer->normalize( $user,'json',['groups'=>'post:read']);
        return new Response("Your Account has been activated");

    }

    /**
     * @Route("/update_JSON/{id}",name="update_JSON")
     */
    function update_JSON(NormalizerInterface $normalizer , UserPasswordEncoderInterface $encoder,UserRepository $repo,Request $request, int $id) {


        $em=$this->getDoctrine()->getManager();
        $user=$repo->find($id);

        //  $user->setId($request->get('identifiant'));
        if($user)
        {
            $user->setUsername($request->get('username'));
            $user->setEmail($request->get('email'));
            $user->setTel($request->get('tel'));
            $user->setPassword($request->get('password'));

            //$user = new FosUser();
            //  $user->setIdentifiant($request->get('id'));


            //$hash =$encoder->encodePassword($user,$request->get('password'));
            //$user->setPassword($hash);



            $em->persist($user);
            $em->flush();
        }
        $json=$normalizer->normalize( $user,'json',['groups'=>'post:read']);
        return new Response("c est bon".json_encode($json));


    }
    /**
     * @Route("/User_deleteJ/{id}",name="User_delete")
     * @param int $id
     * @return RedirectResponse
     */
    function DeleteUserJ(int $id,UserRepository $repository ,  NormalizerInterface  $NormalizerInterface)
    {
        $user=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();

        $jsonContent = $NormalizerInterface ->normalize($user , 'json' , ['groups' => 'post:read']);
        return new Response("suppriméé succes").json_encode($jsonContent);
    }
}
