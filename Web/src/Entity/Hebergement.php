<?php

namespace App\Entity;

use App\Repository\HebergementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=HebergementRepository::class)

 */
class Hebergement 
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * Groups("post:read")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Titre est obligatoire")
     * @Assert\Length(
     *     min=5,
     *     max=50,
     *     minMessage="Le titre doit contenir au moins 5 carcatères ",
     *     maxMessage="Le titre doit contenir au plus 20 carcatères"
     * )
     * Groups("post:read")
     */
    private $titre;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Type est obligatoire")
     * Groups("post:read")
     */
    private $type;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotNull(message="Le Prix doît être différent de 0")
     * Groups("post:read")
     */
    private $prix;

    /**
     * @ORM\Column(type="string", length=255)
     * Groups("post:read")
     */
    private $image;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Adresse est obligatoire")
     * Groups("post:read")
     */
    private $adresse;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Periode est obligatoire")
     * Groups("post:read")
     */
    private $periode;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Le Champ Description est obligatoire")
     */
    private $choix;

    /**
     * @ORM\Column(type="date")
     * @Assert\GreaterThanOrEqual("today")
     * Groups("post:read")
     */
    private $dateH;

    /**
     * @ORM\ManyToOne(targetEntity=Pays::class, inversedBy="hebergement")
     * @Assert\NotBlank(message="Le Champ Description est obligatoire")
     * Groups("post:read")
     */
    private $pays;

    /**
     * @ORM\OneToMany(targetEntity=HebergementClient::class, mappedBy="hebergement")
     */
    private $hebergementClients;

    public function __construct()
    {
        $this->hebergementClients = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }
    public function setId(int $Id): self
    {
        $this->Id=$Id;
        return $this;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

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

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

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

    public function getPeriode(): ?string
    {
        return $this->periode;
    }

    public function setPeriode(string $periode): self
    {
        $this->periode = $periode;

        return $this;
    }

    public function getChoix(): ?string
    {
        return $this->choix;
    }

    public function setChoix(string $choix): self
    {
        $this->choix = $choix;

        return $this;
    }

    public function getDateH(): ?\DateTimeInterface
    {
        return $this->dateH;
    }

    public function setDateH(\DateTimeInterface $dateH): self
    {
        $this->dateH = $dateH;

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

    /**
     * @return Collection<int, HebergementClient>
     */
    public function getHebergementClients(): Collection
    {
        return $this->hebergementClients;
    }

    public function addHebergementClient(HebergementClient $hebergementClient): self
    {
        if (!$this->hebergementClients->contains($hebergementClient)) {
            $this->hebergementClients[] = $hebergementClient;
            $hebergementClient->setHebergement($this);
        }

        return $this;
    }

    public function removeHebergementClient(HebergementClient $hebergementClient): self
    {
        if ($this->hebergementClients->removeElement($hebergementClient)) {
            // set the owning side to null (unless already changed)
            if ($hebergementClient->getHebergement() === $this) {
                $hebergementClient->setHebergement(null);
            }
        }

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->id,
            'pays' => $this->pays,
            'titre' => $this->titre,
            'type' => $this->type,
            'prix' => $this->prix,
            'image' => $this->image,
            'adresse' => $this->adresse,
            'periode' => $this->periode,
            'choix' => $this->choix,
            'dateH' => $this->dateH->format("d-m-Y")

        );
    }

    public function setUp($pays, $titre, $type, $prix, $image, $adresse, $periode, $choix, $dateH)
    {
        $this->pays = $pays;
        $this->titre = $titre;
        $this->type = $type;
        $this->prix = $prix;
        $this->image = $image;
        $this->adresse = $adresse;
        $this->periode = $periode;
        $this->choix = $choix;
        $this->dateH = $dateH;

    }
}
