<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220514163959 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE calendar (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(100) NOT NULL, start DATETIME NOT NULL, end DATETIME NOT NULL, description LONGTEXT NOT NULL, all_day TINYINT(1) NOT NULL, background_color VARCHAR(7) NOT NULL, border_color VARCHAR(7) NOT NULL, text_color VARCHAR(7) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE chauffeur (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, sexe VARCHAR(255) NOT NULL, num INT NOT NULL, disponibilite VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE `div` (id INT AUTO_INCREMENT NOT NULL, types_id INT DEFAULT NULL, nom VARCHAR(255) NOT NULL, numtel INT NOT NULL, adresse VARCHAR(255) NOT NULL, INDEX IDX_BDC6BE098EB23357 (types_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE evenement (idEvmt INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, type VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, nbr_place INT NOT NULL, date DATE NOT NULL, prix DOUBLE PRECISION NOT NULL, PRIMARY KEY(idEvmt)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE hebergement (id INT AUTO_INCREMENT NOT NULL, pays_id INT DEFAULT NULL, titre VARCHAR(255) NOT NULL, type VARCHAR(255) NOT NULL, prix INT NOT NULL, image VARCHAR(255) NOT NULL, adresse VARCHAR(255) NOT NULL, periode VARCHAR(255) NOT NULL, choix VARCHAR(255) NOT NULL, date_h DATE NOT NULL, INDEX IDX_4852DD9CA6E44244 (pays_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE hebergement_client (id INT AUTO_INCREMENT NOT NULL, hebergement_id INT DEFAULT NULL, periode VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, date_h DATE NOT NULL, INDEX IDX_A4CC1C1623BB0F66 (hebergement_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE `like` (id INT AUTO_INCREMENT NOT NULL, nom_id INT DEFAULT NULL, INDEX IDX_AC6340B3C8121CE9 (nom_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE location (id INT AUTO_INCREMENT NOT NULL, nom_id INT DEFAULT NULL, model VARCHAR(255) NOT NULL, prix INT NOT NULL, dateloc DATE NOT NULL, duree INT NOT NULL, INDEX IDX_5E9E89CBC8121CE9 (nom_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE locationc (id INT AUTO_INCREMENT NOT NULL, chauffeur_id INT DEFAULT NULL, model VARCHAR(255) NOT NULL, dateloc DATE NOT NULL, duree INT NOT NULL, INDEX IDX_A515ADE85C0B3BE (chauffeur_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE pays (id INT AUTO_INCREMENT NOT NULL, pays VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE rating (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, rating VARCHAR(255) NOT NULL, msg VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reservation (id_event INT DEFAULT NULL, id_user INT DEFAULT NULL, idReservation INT AUTO_INCREMENT NOT NULL, type_paiement VARCHAR(255) NOT NULL, nbr_place INT NOT NULL, prix DOUBLE PRECISION DEFAULT NULL, prix_i DOUBLE PRECISION DEFAULT NULL, INDEX IDX_42C849556B3CA4B (id_user), INDEX passager_ibfk_1 (id_event), PRIMARY KEY(idReservation)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reset_password_request (id INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, selector VARCHAR(20) NOT NULL, hashed_token VARCHAR(100) NOT NULL, requested_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\', expires_at DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\', INDEX IDX_7CE748AA76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE trip (id INT AUTO_INCREMENT NOT NULL, trip VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE types (id INT AUTO_INCREMENT NOT NULL, types VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, email VARCHAR(180) NOT NULL, roles LONGTEXT NOT NULL COMMENT \'(DC2Type:json)\', password VARCHAR(255) NOT NULL, is_verified TINYINT(1) NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, tel VARCHAR(255) NOT NULL, cin VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, updated_at DATETIME DEFAULT NULL, UNIQUE INDEX UNIQ_8D93D649E7927C74 (email), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE vol (id INT AUTO_INCREMENT NOT NULL, trip_id INT DEFAULT NULL, pays_id INT DEFAULT NULL, description VARCHAR(255) NOT NULL, prix INT NOT NULL, periode VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, date_vol DATE NOT NULL, INDEX IDX_95C97EBA5BC2E0E (trip_id), INDEX IDX_95C97EBA6E44244 (pays_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE vol_client (id INT AUTO_INCREMENT NOT NULL, pays_id INT DEFAULT NULL, trip_id INT DEFAULT NULL, periode VARCHAR(255) NOT NULL, date_vol DATE NOT NULL, email VARCHAR(255) NOT NULL, INDEX IDX_BF585A80A6E44244 (pays_id), INDEX IDX_BF585A80A5BC2E0E (trip_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE `div` ADD CONSTRAINT FK_BDC6BE098EB23357 FOREIGN KEY (types_id) REFERENCES types (id)');
        $this->addSql('ALTER TABLE hebergement ADD CONSTRAINT FK_4852DD9CA6E44244 FOREIGN KEY (pays_id) REFERENCES pays (id)');
        $this->addSql('ALTER TABLE hebergement_client ADD CONSTRAINT FK_A4CC1C1623BB0F66 FOREIGN KEY (hebergement_id) REFERENCES hebergement (id)');
        $this->addSql('ALTER TABLE `like` ADD CONSTRAINT FK_AC6340B3C8121CE9 FOREIGN KEY (nom_id) REFERENCES `div` (id)');
        $this->addSql('ALTER TABLE location ADD CONSTRAINT FK_5E9E89CBC8121CE9 FOREIGN KEY (nom_id) REFERENCES chauffeur (id)');
        $this->addSql('ALTER TABLE locationc ADD CONSTRAINT FK_A515ADE85C0B3BE FOREIGN KEY (chauffeur_id) REFERENCES chauffeur (id)');
        $this->addSql('ALTER TABLE reservation ADD CONSTRAINT FK_42C84955D52B4B97 FOREIGN KEY (id_event) REFERENCES evenement (idEvmt) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE reservation ADD CONSTRAINT FK_42C849556B3CA4B FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE reset_password_request ADD CONSTRAINT FK_7CE748AA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE vol ADD CONSTRAINT FK_95C97EBA5BC2E0E FOREIGN KEY (trip_id) REFERENCES trip (id)');
        $this->addSql('ALTER TABLE vol ADD CONSTRAINT FK_95C97EBA6E44244 FOREIGN KEY (pays_id) REFERENCES pays (id)');
        $this->addSql('ALTER TABLE vol_client ADD CONSTRAINT FK_BF585A80A6E44244 FOREIGN KEY (pays_id) REFERENCES pays (id)');
        $this->addSql('ALTER TABLE vol_client ADD CONSTRAINT FK_BF585A80A5BC2E0E FOREIGN KEY (trip_id) REFERENCES trip (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE location DROP FOREIGN KEY FK_5E9E89CBC8121CE9');
        $this->addSql('ALTER TABLE locationc DROP FOREIGN KEY FK_A515ADE85C0B3BE');
        $this->addSql('ALTER TABLE `like` DROP FOREIGN KEY FK_AC6340B3C8121CE9');
        $this->addSql('ALTER TABLE reservation DROP FOREIGN KEY FK_42C84955D52B4B97');
        $this->addSql('ALTER TABLE hebergement_client DROP FOREIGN KEY FK_A4CC1C1623BB0F66');
        $this->addSql('ALTER TABLE hebergement DROP FOREIGN KEY FK_4852DD9CA6E44244');
        $this->addSql('ALTER TABLE vol DROP FOREIGN KEY FK_95C97EBA6E44244');
        $this->addSql('ALTER TABLE vol_client DROP FOREIGN KEY FK_BF585A80A6E44244');
        $this->addSql('ALTER TABLE vol DROP FOREIGN KEY FK_95C97EBA5BC2E0E');
        $this->addSql('ALTER TABLE vol_client DROP FOREIGN KEY FK_BF585A80A5BC2E0E');
        $this->addSql('ALTER TABLE `div` DROP FOREIGN KEY FK_BDC6BE098EB23357');
        $this->addSql('ALTER TABLE reservation DROP FOREIGN KEY FK_42C849556B3CA4B');
        $this->addSql('ALTER TABLE reset_password_request DROP FOREIGN KEY FK_7CE748AA76ED395');
        $this->addSql('DROP TABLE calendar');
        $this->addSql('DROP TABLE chauffeur');
        $this->addSql('DROP TABLE `div`');
        $this->addSql('DROP TABLE evenement');
        $this->addSql('DROP TABLE hebergement');
        $this->addSql('DROP TABLE hebergement_client');
        $this->addSql('DROP TABLE `like`');
        $this->addSql('DROP TABLE location');
        $this->addSql('DROP TABLE locationc');
        $this->addSql('DROP TABLE pays');
        $this->addSql('DROP TABLE rating');
        $this->addSql('DROP TABLE reservation');
        $this->addSql('DROP TABLE reset_password_request');
        $this->addSql('DROP TABLE trip');
        $this->addSql('DROP TABLE types');
        $this->addSql('DROP TABLE user');
        $this->addSql('DROP TABLE vol');
        $this->addSql('DROP TABLE vol_client');
    }
}
