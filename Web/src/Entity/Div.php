<?php

namespace App\Entity;

use App\Repository\DivRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=DivRepository::class)
 * @ORM\Table(name="`div`")
 */
class Div
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nom;

    /**
     * @ORM\Column(type="integer")
     */
    private $numtel;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $adresse;

    /**
     * @ORM\ManyToOne(targetEntity=Types::class, inversedBy="divs")
     */
    private $types;

    /**
     * @ORM\OneToMany(targetEntity=Like::class, mappedBy="nom")
     */
    private $likes;

    public function __construct()
    {
        $this->likes = new ArrayCollection();
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

    public function getNumtel(): ?int
    {
        return $this->numtel;
    }

    public function setNumtel(int $numtel): self
    {
        $this->numtel = $numtel;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getTypes(): ?types
    {
        return $this->types;
    }

    public function setTypes(?types $types): self
    {
        $this->types = $types;

        return $this;
    }

    /**
     * @return Collection<int, Like>
     */
    public function getLikes(): Collection
    {
        return $this->likes;
    }

    public function addLike(Like $like): self
    {
        if (!$this->likes->contains($like)) {
            $this->likes[] = $like;
            $like->setNom($this);
        }

        return $this;
    }

    public function removeLike(Like $like): self
    {
        if ($this->likes->removeElement($like)) {
            // set the owning side to null (unless already changed)
            if ($like->getNom() === $this) {
                $like->setNom(null);
            }
        }

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->id,
            'types' => $this->types,
            'nom' => $this->nom,
            'numtel' => $this->numtel,
            'adresse' => $this->adresse

        );
    }

    public function setUp($types, $nom, $numtel, $adresse)
    {
        $this->types = $types;
        $this->nom = $nom;
        $this->numtel = $numtel;
        $this->adresse = $adresse;

    }
}
