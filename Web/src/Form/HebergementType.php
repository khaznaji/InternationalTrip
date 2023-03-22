<?php

namespace App\Form;
use App\Entity\Pays;

use App\Entity\Hebergement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class HebergementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre')
            ->add('type',ChoiceType::class,
           [ 'choices'  => [
                'Type' => null,
                'Hotel' => 'Hotel',
                'Maison d hote' => 'Maison d hote',
                'Villa' => 'Villa',
            ],
            'multiple'=>false ,  ] )
             ->add('image',FileType::class, array('data_class' => null,'required' => true,))
             ->add('pays',EntityType::class,
             ['class'=>Pays::class,
             'choice_label'=>'pays',
             'multiple'=>false,
         ])                     ->add('prix')
            ->add('adresse')
            ->add('periode')
            ->add('dateH',DateType::class,[
                'widget' => 'single_text'
                        ])
                       
                        ->add('choix')   

    
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Hebergement::class,
        ]);
    }
}
