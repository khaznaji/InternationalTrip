<?php

namespace App\Entity;

use App\Repository\LocationcRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=LocationcRepository::class)
 */
class Locationc
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
     *     minMessage="Le model doit contenir au moins 5 carcatères ",
     *     maxMessage="Le model doit contenir au plus 20 carcatères"
     * )
     */
    private $model;

   

    /**
     * @ORM\Column(type="date")
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
     * @ORM\ManyToOne(targetEntity=Chauffeur::class, inversedBy="locationcs")
     */
    private $Chauffeur;

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

    public function getChauffeur(): ?Chauffeur
    {
        return $this->Chauffeur;
    }

    public function setChauffeur(?Chauffeur $Chauffeur): self
    {
        $this->Chauffeur = $Chauffeur;

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->id,
            'chauffeur' => $this->Chauffeur,
            'model' => $this->model,
            'dateloc' => $this->dateloc->format("d-m-Y"),
            'duree' => $this->duree

        );
    }

    public function setUp($chauffeur, $model, $dateloc, $duree)
    {
        $this->Chauffeur = $chauffeur;
        $this->model = $model;
        $this->dateloc = $dateloc;
        $this->duree = $duree;

    }
}
