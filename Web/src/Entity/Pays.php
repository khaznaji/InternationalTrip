<?php

namespace App\Entity;

use App\Repository\PaysRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symdony\Component\Validator\Constraints\NotBlank;
/**
 * @ORM\Entity(repositoryClass=PaysRepository::class)
 */
class Pays
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
     * @Assert\NotBlank(message="Le Champ Pays est obligatoire")
     *Groups("post:read")
     */
    private $pays;

    /**
     * @ORM\OneToMany(targetEntity=Vol::class, mappedBy="pays", orphanRemoval=true)
     */
    private $vol;

    /**
     * @ORM\OneToMany(targetEntity=Hebergement::class, mappedBy="pays")
     */
    private $hebergement;

    /**
     * @ORM\OneToMany(targetEntity=VolClient::class, mappedBy="pays")
     */
    private $volC;

    public function __construct()
    {
        $this->vol = new ArrayCollection();
        $this->hebergement = new ArrayCollection();
        $this->volC = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPays(): ?string
    {
        return $this->pays;
    }

    public function setPays(string $pays): self
    {
        $this->pays = $pays;

        return $this;
    }



    /**
     * @return Collection<int, Vol>
     */
    public function getVol(): Collection
    {
        return $this->vol;
    }

    public function addVol(Vol $vol): self
    {
        if (!$this->vol->contains($vol)) {
            $this->vol[] = $vol;
            $vol->setPays($this);
        }

        return $this;
    }

    public function removeVol(Vol $vol): self
    {
        if ($this->vol->removeElement($vol)) {
            // set the owning side to null (unless already changed)
            if ($vol->getPays() === $this) {
                $vol->setPays(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Hebergement>
     */
    public function getHebergement(): Collection
    {
        return $this->hebergement;
    }

    public function addHebergement(Hebergement $hebergement): self
    {
        if (!$this->hebergement->contains($hebergement)) {
            $this->hebergement[] = $hebergement;
            $hebergement->setPays($this);
        }

        return $this;
    }

    public function removeHebergement(Hebergement $hebergement): self
    {
        if ($this->hebergement->removeElement($hebergement)) {
            // set the owning side to null (unless already changed)
            if ($hebergement->getPays() === $this) {
                $hebergement->setPays(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, VolClient>
     */
    public function getVolC(): Collection
    {
        return $this->volC;
    }

    public function addVolC(VolClient $volC): self
    {
        if (!$this->volC->contains($volC)) {
            $this->volC[] = $volC;
            $volC->setPays($this);
        }

        return $this;
    }

    public function removeVolC(VolClient $volC): self
    {
        if ($this->volC->removeElement($volC)) {
            // set the owning side to null (unless already changed)
            if ($volC->getPays() === $this) {
                $volC->setPays(null);
            }
        }

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->id,
            'pays' => $this->pays

        );
    }

    public function setUp($pays)
    {
        $this->pays = $pays;

    }
}
