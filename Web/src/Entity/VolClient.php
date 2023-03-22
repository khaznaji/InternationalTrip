<?php

namespace App\Entity;

use App\Repository\VolClientRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * @ORM\Entity(repositoryClass=VolClientRepository::class)
 */
class VolClient
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Periode est obligatoire")
     */
    private $periode;

    /**
     * @ORM\Column(type="date")
     * @Assert\GreaterThanOrEqual("today")
     */
    private $dateVol;

    /**
     * @ORM\ManyToOne(targetEntity=Pays::class, inversedBy="volClient")
     * @Assert\NotBlank(message="Le Champ Pays est obligatoire")
     */
    private $pays;

    /**
     * @ORM\ManyToOne(targetEntity=Trip::class, inversedBy="VolClient")
     * @Assert\NotBlank(message="Le Champ Trip est obligatoire")
     */
    private $trip;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $email;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getDateVol(): ?\DateTimeInterface
    {
        return $this->dateVol;
    }

    public function setDateVol(\DateTimeInterface $dateVol): self
    {
        $this->dateVol = $dateVol;

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

    public function getTrip(): ?Trip
    {
        return $this->trip;
    }

    public function setTrip(?Trip $trip): self
    {
        $this->trip = $trip;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }
}
