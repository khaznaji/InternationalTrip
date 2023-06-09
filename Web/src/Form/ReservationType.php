<?php

namespace App\Form;

use App\Entity\Reservation;
use App\Entity\Evenement;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReservationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('typePaiement',ChoiceType::class,array(
                'label' => 'type Paiement',
                'choices' => [
                    'Chèque' => "Chèque",
                    'Espèce' => "Espèce",
                    'En ligne' => "En ligne"
                ],
                'expanded' => true,
                'multiple' => false
            ))
            ->add('nbrPlace')


        ;
    }


    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reservation::class,
        ]);
    }
}
