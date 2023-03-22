<?php

namespace App\Form;

use App\Entity\Vol;
use App\Entity\Pays;
use App\Entity\Trip;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class VolType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('description')
            ->add('prix')

            ->add('pays',EntityType::class,
            ['class'=>Pays::class,
            'choice_label'=>'pays',
            'multiple'=>false,
        ])
        ->add('trip',EntityType::class,
        ['class'=>Trip::class,
        'choice_label'=>'trip',
        'multiple'=>false,
    ])
    ->add('image',FileType::class, array('data_class' => null,'required' => true,))
    ->add('periode')
        
        ->add('dateVol',DateType::class,[
            'widget' => 'single_text'
                    ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Vol::class,
        ]);
    }
}
