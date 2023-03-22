<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Reservation
 *
 * @ORM\Table(name="reservation", indexes={@ORM\Index(name="passager_ibfk_1", columns={"id_event"})})
 * @ORM\Entity
 */
class Reservation
{
    /**
     * @var int
     *
     * @ORM\Column(name="idReservation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idreservation;



    /**
     * @var string
     *
     * @ORM\Column(name="type_paiement", type="string", length=255, nullable=false)
     */
    private $typePaiement;

   

    /**
     * @var int
     * @Assert\Range(
     *      min = 1,
     *      max = 10,
     *      minMessage = "le numéro de place doit être au moins 1",
     *      maxMessage = "le numéro de lieu ne doit pas dépasser 10"
     * )
     * @ORM\Column(name="nbr_place", type="integer", nullable=false)
     */
    private $nbrPlace;

  

    /**
     * @return int
     */
    public function getIdreservation(): ?int
    {
        return $this->idreservation;
    }

    /**
     * @param int $idreservation
     */
    public function setIdreservation(int $idreservation): void
    {
        $this->idreservation = $idreservation;
    }

    /**
     * @return \DateTime
     */
    public function getDateReservation(): ?\DateTime
    {
        return $this->dateReservation;
    }

    /**
     * @param \DateTime $dateReservation
     */
    public function setDateReservation(\DateTime $dateReservation): void
    {
        $this->dateReservation = $dateReservation;
    }

    /**
     * @return string
     */
    public function getTypePaiement(): ?string
    {
        return $this->typePaiement;
    }

    /**
     * @param string $typePaiement
     */
    public function setTypePaiement(string $typePaiement): void
    {
        $this->typePaiement = $typePaiement;
    }


    /**
     * @return int
     */
    public function getNbrPlace(): ?int
    {
        return $this->nbrPlace;
    }

    /**
     * @param int $nbrPlace
     */
    public function setNbrPlace(int $nbrPlace): void
    {
        $this->nbrPlace = $nbrPlace;
    }

    /**
     * @var  /App/Entity/Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_event", referencedColumnName="idEvmt",onDelete="CASCADE")
     * })
     */
    private $event;

    

    /**
     * @ORM\Column(type="float" , nullable=true)
     */
    private $prix;

    /**
     * @ORM\Column(type="float" , nullable=true)
     */
    private $prixI;

    /**
     * @var  /App/Entity/User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id",onDelete="CASCADE")
     * })
     */
    private $user;


    /**
     * @return \App\Entity\Evenement
     */
    public function getEvent(): ?\App\Entity\Evenement
    {
        return $this->event;
    }

    /**
     * @param \App\Entity\Evenement $event
     */
    public function setEvent(\App\Entity\Evenement $event): void
    {
        $this->event = $event;
    }

    

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getPrixI(): ?float
    {
        return $this->prixI;
    }

    public function setPrixI(float $prixI): self
    {
        $this->prixI = $prixI;

        return $this;
    }

    /**
     * @param \App\Entity\User $user
     */
    public function setUser(\App\Entity\User $user): void
    {
        $this->user = $user;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->idreservation,
            'evenement' => $this->event,
            'user' => $this->user,
            'typePaiement' => $this->typePaiement,
            'nbrPlace' => $this->nbrPlace,
            'prix' => $this->prix,
            'prixI' => $this->prixI

        );
    }

    public function setUp($evenement, $user, $typePaiement, $nbrPlace, $prix, $prixI)
    {
        $this->event = $evenement;
        $this->user = $user;
        $this->typePaiement = $typePaiement;
        $this->nbrPlace = $nbrPlace;
        $this->prix = $prix;
        $this->prixI = $prixI;

    }

}
