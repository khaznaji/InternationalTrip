<?php

namespace App\Form;
use App\Entity\Types;

use App\Entity\Div;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;


class DivType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
->add('types',EntityType::class,
             ['class'=>Types::class,
             'choice_label'=>'types',
             'multiple'=>false,
         ])            ->add('numtel')
            ->add('adresse')
           
    
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Div::class,
        ]);
    }
}
