<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\EvenementRepository;


/**
 * @ORM\Entity(repositoryClass=EvenementRepository::class)
 */
class Evenement
{
    /**
     * @var int
     *
     * @ORM\Column(name="idEvmt", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idevmt;

    /**
     * @Assert\NotBlank(message=" nom doit etre non vide")
     * @Assert\Length(
     *      min = 2,
     *      minMessage=" Entrer au mini de 2 caracteres"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $nom;

    /**
     * @Assert\NotBlank(message=" Type doit etre non vide")
     * @Assert\Length(
     *      min = 2,
     *      minMessage=" Entrer au mini de 2 caracteres"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $type;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull
     */
    private $image;

    /**
     * @Assert\NotBlank(message=" nbr_place doit etre non vide")
     * @var int
     * @Assert\Range(
     *      min = 1,
     *      max = 200,
     *      minMessage = "le numéro de place doit être au moins 1",
     *      maxMessage = "le numéro de lieu ne doit pas dépasser 200"
     * )
     * @ORM\Column(name="nbr_place", type="integer", nullable=false)
     */
    private $nbrPlace;

    /**
     * @var \DateTime
     * @Assert\GreaterThan("today", message="La date de depart doit être ultérieur à la date d'aujourd'hui")
     * @ORM\Column(name="date", type="date", nullable=false)
     */
    private $date;

   

   

    /**
     * @Assert\NotBlank(message=" prix vol doit etre non vide")
     * @var float
     * @Assert\Type(type="float", message="Invalid! doit être un nombre  ")

     * @ORM\Column(name="prix", type="float", precision=10, scale=0, nullable=false)
     */
    private $prix;

    /**
     * @ORM\OneToMany(targetEntity=Reservation::class, mappedBy="evenement",cascade={"persist"})
     */
    private $reservation;


    /**
     * @return int
     */
    public function getIdevmt(): ?int
    {
        return $this->idevmt;
    }

    /**
     * @param int $idevmt
     */
    public function setIdevmt(int $idevmt): void
    {
        $this->idevmt = $idevmt;
    }

    /**
     * @return string
     */
    public function getNom(): ?string
    {
        return $this->nom;
    }
    public function __toString() {     return $this->nom; }

    /**
     * @param string $nom
     */
    public function setNom(string $nom): void
    {
        $this->nom = $nom;
    }
    public function getReservation(): ?string
    {
        return $this->reservation;
    }

    /**
     * @return string
     */
    public function getType(): ?string
    {
        return $this->type;
    }

    /**
     * @param string $type
     */
    public function setType(string $type): void
    {
        $this->type = $type;
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
     * @return \DateTime
     */
    public function getDate(): ?\DateTime
    {
        return $this->date;
    }

    /**
     * @param \DateTime $date
     */
    public function setDate(\DateTime $date): void
    {
        $this->date = $date;
    }

    /**
     * @return float
     */
    public function getPrix(): ?float
    {
        return $this->prix;
    }

    /**
     * @param float $prix
     */
    public function setPrix(float $prix): void
    {
        $this->prix = $prix;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->idevmt,
            'nom' => $this->nom,
            'type' => $this->type,
            'image' => $this->image,
            'nbrPlace' => $this->nbrPlace,
            'date' => $this->date->format("d-m-Y"),
            'prix' => $this->prix

        );
    }

    public function setUp($nom, $type, $image, $nbrPlace, $date, $prix)
    {
        $this->nom = $nom;
        $this->type = $type;
        $this->image = $image;
        $this->nbrPlace = $nbrPlace;
        $this->date = $date;
        $this->prix = $prix;

    }
}
