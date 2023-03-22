<?php

namespace App\Entity;

use App\Repository\TypesRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=TypesRepository::class)
 */
class Types
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
    private $types;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $image;

    /**
     * @ORM\OneToMany(targetEntity=Div::class, mappedBy="types")
     */
    private $divs;

    public function __construct()
    {
        $this->divs = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTypes(): ?string
    {
        return $this->types;
    }

    public function setTypes(string $types): self
    {
        $this->types = $types;

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

    /**
     * @return Collection<int, Div>
     */
    public function getDivs(): Collection
    {
        return $this->divs;
    }

    public function addDiv(Div $div): self
    {
        if (!$this->divs->contains($div)) {
            $this->divs[] = $div;
            $div->setTypes($this);
        }

        return $this;
    }

    public function removeDiv(Div $div): self
    {
        if ($this->divs->removeElement($div)) {
            // set the owning side to null (unless already changed)
            if ($div->getTypes() === $this) {
                $div->setTypes(null);
            }
        }

        return $this;
    }
    public function jsonSerialize(): array
    {
        return array(
            'id' => $this->id,
            'types' => $this->types,
            'image' => $this->image

        );
    }

    public function setUp($types, $image)
    {
        $this->types = $types;
        $this->image = $image;
    }
}
