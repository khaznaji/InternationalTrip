<?php

namespace App\Controller;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Entity\Evenement;
use App\Repository\EvenementRepository;
use App\Form\EvenementType;
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
use App\Entity\Reservation;
use App\Entity\User;
use App\Form\ReservationType;
use App\Repository\ReservationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
class EvenementController extends AbstractController
{
    
    /**
     * @Route("/Evenement", name="app_evenement_index") 
     */
    public function index(): Response
    {        $em = $this->getDoctrine()->getManager();

        $evenements = $em

            ->getRepository(Evenement::class)
            ->findAll();

        return $this->render('event/index.html.twig', [
            'evenements' => $evenements,
        ]);
    }
         /**
  * @Route("/newEvenement", name="newEvenement")
   
  */

    public function newEvenement(Request $request )
    {     $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $ev = $form->getData();
            $img = $form->get('image')->getData();
            $upload= md5(uniqid()).'.'.$img->guessExtension();
            try{
                $img->move(
                    $this->getParameter('images_directory'),
                    $upload
                );

            }catch (FileException $e){

            }
            $ev->setImage($upload);
            $em = $this->getDoctrine()->getManager();

            $em->persist($evenement);
            $em->flush();

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('event/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }
  /**
     * @Route("/AfficheEvenementFront", name="frontevenement")
     */
    public function AfficheEvenementFront( Request $request,PaginatorInterface $paginator): Response
    {            $entityManager = $this->getDoctrine()->getManager();

        $evenements = $entityManager->getRepository(Evenement::class)->findAll();

        return $this->render('frontRes/events.html.twig', [
            'totalEvenements' => count($evenements),
            'evenements' => $paginator->paginate($evenements,
                $request->query->getInt('page', 1), 2
            )
        ]);
    }
  /**
     * @Route("/statistique",name="statsevenement")
     */
    public function statistique(ReservationRepository $repo)
    {

        $evenement = $repo->findAll();

        $i =1;
        foreach ($repo->countById() as $v) {

            $eventid[] = $v[1];
            $event[] = $evenement[$i]->getEvent()->getNom();
            $i++;
        }

        return $this->render('frontRes/stat.html.twig', [
            'event' => json_encode($event),
            'eventid' => json_encode($eventid),


        ]);
    }
      /**
     * @Route("/evenement/{idevmt}/reserver", name="app_evenement_reserver")
     */
    public function reserver(Request $request, $idevmt, EntityManagerInterface $entityManager,\Swift_Mailer $mailer): Response
    {

        $reservationevenement = new Reservation();
        $idevenement = $this->getDoctrine()->getRepository(Evenement::class)->find($idevmt);
       // $user = $this->getDoctrine()->getRepository(User::class)->find($id);

        $form = $this->createForm(ReservationType::class, $reservationevenement);
        $form->handleRequest($request);
        $reservationevenement->setEvent($idevenement);
        $mnbp=$idevenement->getNbrPlace()-$reservationevenement->getNbrPlace();
        $pi= $idevenement->getPrix();
        $p= $idevenement->getPrix() * $reservationevenement->getNbrPlace() ;
       $reservationevenement->setPrix($p);
        $reservationevenement->setPrixI($pi);

        $idevenement->setNbrPlace($mnbp);

        if ($form->isSubmitted() && $form->isValid()) {


            $entityManager->persist($reservationevenement);

            $entityManager->flush();
            // On génère l'e-mail
            $message = (new \Swift_Message('Reservation'))
                ->setFrom('touchIT@gmail.com')
                ->setTo('abdelaziz.gharbi@esprit.tn'/*$user->getEmail()*/)
                ->setBody(
                    "Bonjour,<br><br>Merci pour votre Reservation " ,
                    'text/html'
                )
            ;

            // On envoie l'e-mail
            $mailer->send($message);

           return $this->redirectToRoute('app_reservation_index2', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('frontRes/res.html.twig', [

            'form' => $form->createView(),
        ]);
    }
     /**
     * @Route("/evenement/recherche", name="recherche_evenement")
     */
    public function rechercheEvenementBack(Request $request): Response
    {
        $evenement = $request->get("valeur-recherche");
        $recs = $this->getDoctrine()->getRepository(Evenement::class)->findStartingWith($evenement);

        $recsJson = [];
        $i = 0;
        foreach ($recs as $rec) {
            $recsJson[$i]["nom"] = $rec->getNom();
            $recsJson[$i]["type"] = $rec->getType();
            $recsJson[$i]["NombedesPlaces"] = $rec->getNbrPlace();
            $recsJson[$i]["Date"] = $rec->getDate()->format('Y-m-d ');
            $recsJson[$i]["Prix"] = $rec->getPrix();
            $recsJson[$i]["idevmt"] = $rec->getIdevmt();
            $recsJson[$i]["im"] = $rec->getImage();


            $i++;
        }
        return new Response(json_encode($recsJson));
    }
    /**
     * @Route("/Evenement/{idevmt}", name="app_evenement_show")
     */
    public function show(Evenement $evenement): Response
    {
        return $this->render('event/show.html.twig', [
            'evenement' => $evenement,
        ]);
    }
    /**
     * @Route("evenement/{idevmt}/edit", name="app_evenement_edit")
     */
    public function edit(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('event/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }
     /**
     * @Route("evenement/{idevmt}", name="app_evenement_delete")
     */
    public function delete(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete' . $evenement->getIdevmt(), $request->request->get('_token'))) {
            $entityManager->remove($evenement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
 }