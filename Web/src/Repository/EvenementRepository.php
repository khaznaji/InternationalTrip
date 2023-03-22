<?php

namespace App\Repository;

use App\Entity\Evenement;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\Security\Core\Exception\UnsupportedUserException;
use Symfony\Component\Security\Core\User\PasswordUpgraderInterface;
use Symfony\Component\Security\Core\User\UserInterface;

/**
 * @method Evenement|null find($id, $lockMode = null, $lockVersion = null)
 * @method Evenement|null findOneBy(array $criteria, array $orderBy = null)
 * @method Evenement[]    findAll()
 * @method Evenement[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EvenementRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Evenement::class);
    }

    public function countById(){
        return $this->createQueryBuilder('l')
            ->select('count(l.nom)')
            ->groupBy('l.nom')
            ->orderBy('l.idevmt')
            ->getQuery()
            ->getScalarResult(); // get the result as array
    }


   /* public function findByExampleField($value)
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.destination = :val')
            ->setParameter('val', $value)
            ->orderBy('u.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }

*/
    /*
    public function findOneBySomeField($value): ?User
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */


    public function findStartingWith($recherche)
    {
        return $this->createQueryBuilder('c')
            ->where('c.nom LIKE :val')
            ->orWhere('c.type LIKE :val')
            ->setParameter("val", $recherche . '%')
            ->getQuery()
            ->getResult();
    }
}
