<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Regex;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Vich\UploaderBundle\Form\Type\VichImageType;

class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email',EmailType::class,['attr'=>['class'=>'form-control']])
        
            ->add('nom',TextType::class,['constraints' => [ new Regex([
                'pattern'=>"/\d/",
                'match'=>false,
                'message'=>"Your name cannot contain a number"
                    ]
                )],'attr'=>['class'=>'form-control']])
                ->add('prenom',TextType::class,['constraints' => [ new Regex([
                    'pattern'=>"/\d/",
                    'match'=>false,
                    'message'=>"Your prenom cannot contain a number"
                        ]
                    )],'attr'=>['class'=>'form-control']])
                    ->add('tel',IntegerType::class,[ 'constraints' => [ new Length([
                        'min' => 8,
                        'minMessage' => 'Your numero de telephone should be at least {{ limit }} numbers',
                        // max length allowed by Symfony for security reasons
                        'max' => 4096,
                    ]),],'attr'=>['class'=>'form-control']])
            ->add('cin',IntegerType::class,[ 'constraints' => [ new Length([
                'min' => 8,
                'minMessage' => 'Your cin should be {{ limit }} numbers',
                // max length allowed by Symfony for security reasons
                'max' => 8,
            ])],'attr'=>['class'=>'form-control']])
            ->add('imageFile', VichImageType::class)
            ->add('isVerified')

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
