<?php

namespace App\Entity;

use App\Repository\ChauffeurRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ChauffeurRepository::class)
 */
class Chauffeur
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
    private $nom;

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
    private $prenom;

    /**
     * @ORM\Column(type="string", length=255)
      * @Assert\Length(
     *      min = 4,
     *      max = 20,
     *      minMessage = "Your name must be at least {{ limit }} characters long",
     *      maxMessage = "Your  name cannot be longer than {{ limit }} characters",
     *      allowEmptyString = false
     * )
     */
    private $sexe;

    /**
     * @ORM\Column(type="integer")
      * @Assert\NotEqualTo(
     *     value = 0
     *     )
     */
    private $num;

    /**
     * @ORM\Column(type="string", length=255)
     *  @Assert\NotBlank(message="Le Champ Titre est obligatoire")
     * @Assert\Length(
     *     min=3,
     *     max=50,
     *     minMessage="Le titre doit contenir au moins 3 carcatères ",
     *     maxMessage="Le titre doit contenir au plus 20 carcatères"
     * )
     */
    private $disponibilite;

    /**
     * @ORM\OneToMany(targetEntity=Location::class, mappedBy="nom")
     */
    private $location;

    /**
     * @ORM\OneToMany(targetEntity=Locationc::class, mappedBy="Chauffeur")
     */
    private $locationcs;

    public function __construct()
    {
        $this->location = new ArrayCollection();
        $this->locationcs = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getSexe(): ?string
    {
        return $this->sexe;
    }

    public function setSexe(string $sexe): self
    {
        $this->sexe = $sexe;

        return $this;
    }

    public function getNum(): ?int
    {
        return $this->num;
    }

    public function setNum(int $num): self
    {
        $this->num = $num;

        return $this;
    }

    public function getDisponibilite(): ?string
    {
        return $this->disponibilite;
    }

    public function setDisponibilite(string $disponibilite): self
    {
        $this->disponibilite = $disponibilite;

        return $this;
    }

    /**
     * @return Collection<int, Location>
     */
    public function getLocation(): Collection
    {
        return $this->location;
    }

    public function addLocation(Location $location): self
    {
        if (!$this->location->contains($location)) {
            $this->location[] = $location;
            $location->setNom($this);
        }

        return $this;
    }

    public function removeLocation(Location $location): self
    {
        if ($this->location->removeElement($location)) {
            // set the owning side to null (unless already changed)
            if ($location->getNom() === $this) {
                $location->setNom(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Locationc>
     */
    public function getLocationcs(): Collection
    {
        return $this->locationcs;
    }

    public function addLocationc(Locationc $locationc): self
    {
        if (!$this->locationcs->contains($locationc)) {
            $this->locationcs[] = $locationc;
            $locationc->setChauffeur($this);
        }

        return $this;
    }

    public function removeLocationc(Locationc $locationc): self
    {
        if ($this->locationcs->removeElement($locationc)) {
            // set the owning side to null (unless already changed)
            if ($locationc->getChauffeur() === $this) {
                $locationc->setChauffeur(null);
            }
        }

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->id,
            'nom' => $this->nom,
            'prenom' => $this->prenom,
            'sexe' => $this->sexe,
            'num' => $this->num,
            'disponibilite' => $this->disponibilite

        );
    }

    public function setUp($nom, $prenom, $sexe, $num, $disponibilite)
    {
        $this->nom = $nom;
        $this->prenom = $prenom;
        $this->sexe = $sexe;
        $this->num = $num;
        $this->disponibilite = $disponibilite;

    }
}
