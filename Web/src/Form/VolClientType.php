<?php

namespace App\Form;
use App\Entity\Pays;
use App\Entity\Trip;

use App\Entity\VolClient;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class VolClientType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('periode')
            ->add('email')

            ->add('dateVol',DateType::class,[
                'widget' => 'single_text'
                        ])           ->add('pays',EntityType::class,
            ['class'=>Pays::class,
            'choice_label'=>'pays',
            'multiple'=>false,
        ])
        ->add('trip',EntityType::class,
        ['class'=>Trip::class,
        'choice_label'=>'trip',
        'multiple'=>false,
    ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => VolClient::class,
        ]);
    }
}
