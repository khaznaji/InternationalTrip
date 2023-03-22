<?php

namespace App\Entity;

use App\Repository\VolRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * @ORM\Entity(repositoryClass=VolRepository::class)
 */
class Vol
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Description est obligatoire")
     */
    private $description;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotNull(message="Le Prix doît être différent de 0")
     */
    private $prix;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Periode est obligatoire")
     */
    private $periode;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Image est obligatoire")
     */
    private $image;

    /**
     * @ORM\Column(type="date")
     * @Assert\GreaterThanOrEqual("today")
     */
    private $dateVol;

    /**
     * @ORM\ManyToOne(targetEntity=Trip::class, inversedBy="vol")
     * @Assert\NotBlank(message="Le Champ Trip est obligatoire")
     */
    private $trip;

    /**
     * @ORM\ManyToOne(targetEntity=Pays::class, inversedBy="vol")
     * @Assert\NotBlank(message="Le Champ Pays est obligatoire")
     */
    private $pays;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function setPrix(int $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getPeriode(): ?string
    {
        return $this->periode;
    }

    public function setPeriode(string $periode): self
    {
        $this->periode = $periode;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getDateVol(): ?\DateTimeInterface
    {
        return $this->dateVol;
    }

    public function setDateVol(\DateTimeInterface $dateVol): self
    {
        $this->dateVol = $dateVol;

        return $this;
    }

    public function getTrip(): ?Trip
    {
        return $this->trip;
    }

    public function setTrip(?Trip $trip): self
    {
        $this->trip = $trip;

        return $this;
    }

    public function getPays(): ?Pays
    {
        return $this->pays;
    }

    public function setPays(?Pays $pays): self
    {
        $this->pays = $pays;

        return $this;
    }
}
