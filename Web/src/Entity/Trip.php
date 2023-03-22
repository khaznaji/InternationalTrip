<?php

namespace App\Entity;

use App\Repository\TripRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=TripRepository::class)
 */
class Trip
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
     * Groups("post:read")
     */
    private $trip;

    /**
     * @ORM\OneToMany(targetEntity=Vol::class, mappedBy="trip")
     */
    private $vol;

    /**
     * @ORM\OneToMany(targetEntity=VolClient::class, mappedBy="trip")
     */
    private $VolClient;

    public function __construct()
    {
        $this->vol = new ArrayCollection();
        $this->VolClient = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTrip(): ?string
    {
        return $this->trip;
    }

    public function setTrip(string $trip): self
    {
        $this->trip = $trip;

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
            $vol->setTrip($this);
        }

        return $this;
    }

    public function removeVol(Vol $vol): self
    {
        if ($this->vol->removeElement($vol)) {
            // set the owning side to null (unless already changed)
            if ($vol->getTrip() === $this) {
                $vol->setTrip(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, VolClient>
     */
    public function getVolClient(): Collection
    {
        return $this->VolClient;
    }

    public function addVolClient(VolClient $volClient): self
    {
        if (!$this->VolClient->contains($volClient)) {
            $this->VolClient[] = $volClient;
            $volClient->setTrip($this);
        }

        return $this;
    }

    public function removeVolClient(VolClient $volClient): self
    {
        if ($this->VolClient->removeElement($volClient)) {
            // set the owning side to null (unless already changed)
            if ($volClient->getTrip() === $this) {
                $volClient->setTrip(null);
            }
        }

        return $this;
    }
}
