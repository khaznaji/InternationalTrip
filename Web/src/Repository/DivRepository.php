<?php

namespace App\Repository;

use App\Entity\Div;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Div|null find($id, $lockMode = null, $lockVersion = null)
 * @method Div|null findOneBy(array $criteria, array $orderBy = null)
 * @method Div[]    findAll()
 * @method Div[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class DivRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Div::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(Div $entity, bool $flush = true): void
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function remove(Div $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    // /**
    //  * @return Div[] Returns an array of Div objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('d.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Div
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function findByNom()
    {
        return $this->createQueryBuilder('div')
            ->orderBy('div.nom','ASC')
            ->getQuery()
            ->getResult()
            ;
    }
}
