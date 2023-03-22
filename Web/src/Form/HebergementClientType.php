<?php

namespace App\Form;
use App\Entity\Hebergement;

use App\Entity\HebergementClient;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
class HebergementClientType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('periode')
            ->add('email')

            ->add('dateH',DateType::class,[
                'widget' => 'single_text'
                        ])             
                         
                          ->add('hebergement',EntityType::class,
            ['class'=>Hebergement::class,
            'choice_label'=>'titre',
            'multiple'=>false,
        ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => HebergementClient::class,
        ]);
    }
}
