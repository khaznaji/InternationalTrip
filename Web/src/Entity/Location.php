<?php

namespace App\Entity;

use App\Repository\LocationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=LocationRepository::class)
 */
class Location
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     *  @Assert\NotBlank(message="Le Champ Titre est obligatoire")
     * @Assert\Length(
     *     min=5,
     *     max=50,
     *     minMessage="Le titre doit contenir au moins 5 carcatères ",
     *     maxMessage="Le titre doit contenir au plus 20 carcatères"
     * )
     */
    private $model;

    /**
     * @ORM\Column(type="integer")
      * @Assert\NotEqualTo(
     *     value = 0
     *     )
     */
    private $prix;

    /**
     * @ORM\Column(type="date", length=255)
     */
    private $dateloc;

    /**
     * @ORM\Column(type="integer")
      * @Assert\NotEqualTo(
     *     value = 0
     *     )
     */
    private $duree;

    /**
     * @ORM\ManyToOne(targetEntity=Chauffeur::class, inversedBy="location")
     */
    private $nom;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getModel(): ?string
    {
        return $this->model;
    }

    public function setModel(string $model): self
    {
        $this->model = $model;

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

    public function getDateloc(): ?\DateTimeInterface
    {
        return $this->dateloc;
    }

    public function setDateloc(\DateTimeInterface $dateloc): self
    {
        $this->dateloc = $dateloc;

        return $this;
    }

    public function getDuree(): ?int
    {
        return $this->duree;
    }

    public function setDuree(int $duree): self
    {
        $this->duree = $duree;

        return $this;
    }

    public function getNom(): ?Chauffeur
    {
        return $this->nom;
    }

    public function setNom(?Chauffeur $nom): self
    {
        $this->nom = $nom;

        return $this;
    }
}
