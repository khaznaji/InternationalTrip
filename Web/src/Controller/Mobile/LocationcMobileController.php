<?php
namespace App\Controller\Mobile;

use App\Entity\Locationc;
use App\Repository\LocationcRepository;
use App\Repository\ChauffeurRepository;
use DateTime;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/mobile/locationc")
 */
class LocationcMobileController extends AbstractController
{
    /**
     * @Route("", methods={"GET"})
     */
    public function index(LocationcRepository $locationcRepository): Response
    {
        $locationcs = $locationcRepository->findAll();

        if ($locationcs) {
            return new JsonResponse($locationcs, 200);
        } else {
            return new JsonResponse([], 204);
        }
    }

    /**
     * @Route("/add", methods={"POST"})
     */
    public function add(Request $request, ChauffeurRepository $chauffeurRepository): JsonResponse
    {
        $locationc = new Locationc();

        return $this->manage($locationc, $chauffeurRepository,  $request, false);
    }

    /**
     * @Route("/edit", methods={"POST"})
     */
    public function edit(Request $request, LocationcRepository $locationcRepository, ChauffeurRepository $chauffeurRepository): Response
    {
        $locationc = $locationcRepository->find((int)$request->get("id"));

        if (!$locationc) {
            return new JsonResponse(null, 404);
        }

        return $this->manage($locationc, $chauffeurRepository, $request, true);
    }

    public function manage($locationc, $chauffeurRepository, $request, $isEdit): JsonResponse
    {   
        $chauffeur = $chauffeurRepository->find((int)$request->get("chauffeur"));
        if (!$chauffeur) {
            return new JsonResponse("chauffeur with id " . (int)$request->get("chauffeur") . " does not exist", 203);
        }
        
        
        $locationc->setUp(
            $chauffeur,
            $request->get("model"),
            DateTime::createFromFormat("d-m-Y", $request->get("dateloc")),
            (int)$request->get("duree")
        );
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($locationc);
        $entityManager->flush();

        return new JsonResponse($locationc, 200);
    }

    /**
     * @Route("/delete", methods={"POST"})
     */
    public function delete(Request $request, EntityManagerInterface $entityManager, LocationcRepository $locationcRepository): JsonResponse
    {
        $locationc = $locationcRepository->find((int)$request->get("id"));

        if (!$locationc) {
            return new JsonResponse(null, 200);
        }

        $entityManager->remove($locationc);
        $entityManager->flush();

        return new JsonResponse([], 200);
    }

    /**
     * @Route("/deleteAll", methods={"POST"})
     */
    public function deleteAll(EntityManagerInterface $entityManager, LocationcRepository $locationcRepository): Response
    {
        $locationcs = $locationcRepository->findAll();

        foreach ($locationcs as $locationc) {
            $entityManager->remove($locationc);
            $entityManager->flush();
        }

        return new JsonResponse([], 200);
    }
    
}
