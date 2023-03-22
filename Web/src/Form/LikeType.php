<?php

namespace App\Form;
use App\Entity\Div;


use App\Entity\Like;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;


class LikeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
                       ->add('nom',EntityType::class,
            ['class'=>Div::class,
            'choice_label'=>'nom',
            'multiple'=>false,
        ])
       
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Like::class,
        ]);
    }
}