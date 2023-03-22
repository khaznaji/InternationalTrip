<?php

namespace App\Form;

use App\Entity\Location;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Chauffeur;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class LocationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('model')
            ->add('prix')
            ->add('dateloc',DateType::class,[ 
                'widget'=>'single_text'
            ])
            ->add('duree')
            ->add('nom',EntityType::class,
            ['class'=>Chauffeur::class,
            'choice_label'=>'nom',
            'multiple'=>false,
        ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Location::class,
        ]);
    }
}
