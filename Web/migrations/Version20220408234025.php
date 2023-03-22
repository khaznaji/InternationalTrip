<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220408234025 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE hebergement (id INT AUTO_INCREMENT NOT NULL, pays_id INT DEFAULT NULL, titre VARCHAR(255) NOT NULL, type VARCHAR(255) NOT NULL, prix INT NOT NULL, image VARCHAR(255) NOT NULL, adresse VARCHAR(255) NOT NULL, periode VARCHAR(255) NOT NULL, choix VARCHAR(255) NOT NULL, date_h DATE NOT NULL, INDEX IDX_4852DD9CA6E44244 (pays_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE pays (id INT AUTO_INCREMENT NOT NULL, pays VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE trip (id INT AUTO_INCREMENT NOT NULL, trip VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE vol (id INT AUTO_INCREMENT NOT NULL, trip_id INT DEFAULT NULL, pays_id INT DEFAULT NULL, description VARCHAR(255) NOT NULL, prix INT NOT NULL, periode VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, date_vol DATE NOT NULL, INDEX IDX_95C97EBA5BC2E0E (trip_id), INDEX IDX_95C97EBA6E44244 (pays_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE vol_client (id INT AUTO_INCREMENT NOT NULL, pays_id INT DEFAULT NULL, trip_id INT DEFAULT NULL, periode VARCHAR(255) NOT NULL, date_vol DATE NOT NULL, INDEX IDX_BF585A80A6E44244 (pays_id), INDEX IDX_BF585A80A5BC2E0E (trip_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE hebergement ADD CONSTRAINT FK_4852DD9CA6E44244 FOREIGN KEY (pays_id) REFERENCES pays (id)');
        $this->addSql('ALTER TABLE vol ADD CONSTRAINT FK_95C97EBA5BC2E0E FOREIGN KEY (trip_id) REFERENCES trip (id)');
        $this->addSql('ALTER TABLE vol ADD CONSTRAINT FK_95C97EBA6E44244 FOREIGN KEY (pays_id) REFERENCES pays (id)');
        $this->addSql('ALTER TABLE vol_client ADD CONSTRAINT FK_BF585A80A6E44244 FOREIGN KEY (pays_id) REFERENCES pays (id)');
        $this->addSql('ALTER TABLE vol_client ADD CONSTRAINT FK_BF585A80A5BC2E0E FOREIGN KEY (trip_id) REFERENCES trip (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE hebergement DROP FOREIGN KEY FK_4852DD9CA6E44244');
        $this->addSql('ALTER TABLE vol DROP FOREIGN KEY FK_95C97EBA6E44244');
        $this->addSql('ALTER TABLE vol_client DROP FOREIGN KEY FK_BF585A80A6E44244');
        $this->addSql('ALTER TABLE vol DROP FOREIGN KEY FK_95C97EBA5BC2E0E');
        $this->addSql('ALTER TABLE vol_client DROP FOREIGN KEY FK_BF585A80A5BC2E0E');
        $this->addSql('DROP TABLE hebergement');
        $this->addSql('DROP TABLE pays');
        $this->addSql('DROP TABLE trip');
        $this->addSql('DROP TABLE vol');
        $this->addSql('DROP TABLE vol_client');
    }
}
