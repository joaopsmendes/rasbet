-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema rasbet
-- -----------------------------------------------------
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utilizador` (
  `email` VARCHAR(45) NOT NULL,
  `dataNascimento` DATE NOT NULL,
  `NIF` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Aposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Aposta` (
  `idAposta` INT NOT NULL,
  `montante` DOUBLE NOT NULL,
  `data` DATE NOT NULL,
  `Utilizador_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAposta`, `Utilizador_email`),
  INDEX `fk_Aposta_Utilizador1_idx` (`Utilizador_email` ASC) VISIBLE,
  CONSTRAINT `fk_Aposta_Utilizador1`
    FOREIGN KEY (`Utilizador_email`)
    REFERENCES `mydb`.`Utilizador` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TipoAposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TipoAposta` (
  `Aposta` INT NOT NULL,
  `TipoApostacol` VARCHAR(45) NULL,
  PRIMARY KEY (`Aposta`),
  CONSTRAINT `fk_TipoAposta_Aposta`
    FOREIGN KEY (`Aposta`)
    REFERENCES `mydb`.`Aposta` (`idAposta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Carteira`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Carteira` (
  `saldo` DOUBLE NOT NULL,
  `freebets` DOUBLE NOT NULL,
  `Utilizador_email` VARCHAR(45) NOT NULL,
  `Utilizador_email1` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Utilizador_email`, `Utilizador_email1`),
  INDEX `fk_Carteira_Utilizador1_idx` (`Utilizador_email1` ASC) VISIBLE,
  CONSTRAINT `fk_Carteira_Utilizador1`
    FOREIGN KEY (`Utilizador_email1`)
    REFERENCES `mydb`.`Utilizador` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Transação`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Transação` (
  `idTransação` INT NOT NULL,
  `valor` DOUBLE NOT NULL,
  `dataTransacao` DATE NOT NULL,
  `Carteira_Utilizador_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTransação`, `Carteira_Utilizador_email`),
  INDEX `fk_Transação_Carteira1_idx` (`Carteira_Utilizador_email` ASC) VISIBLE,
  CONSTRAINT `fk_Transação_Carteira1`
    FOREIGN KEY (`Carteira_Utilizador_email`)
    REFERENCES `mydb`.`Carteira` (`Utilizador_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Favorito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Favorito` (
  `idFavorito` INT NOT NULL,
  `favorito` VARCHAR(45) NOT NULL,
  `Utilizador_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idFavorito`, `Utilizador_email`),
  INDEX `fk_Favorito_Utilizador1_idx` (`Utilizador_email` ASC) VISIBLE,
  CONSTRAINT `fk_Favorito_Utilizador1`
    FOREIGN KEY (`Utilizador_email`)
    REFERENCES `mydb`.`Utilizador` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Desporto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Desporto` (
  `idDesporto` INT NOT NULL,
  `tipoDesporto` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDesporto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Jogo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Jogo` (
  `idJogo` INT NOT NULL,
  `Desporto_idDesporto` INT NOT NULL,
  `resultado` VARCHAR(45) NULL,
  PRIMARY KEY (`idJogo`, `Desporto_idDesporto`),
  INDEX `fk_Jogo_Desporto1_idx` (`Desporto_idDesporto` ASC) VISIBLE,
  CONSTRAINT `fk_Jogo_Desporto1`
    FOREIGN KEY (`Desporto_idDesporto`)
    REFERENCES `mydb`.`Desporto` (`idDesporto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Odd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Odd` (
  `idOdd` INT NOT NULL,
  `Jogo_idJogo` INT NOT NULL,
  `valor` DOUBLE NOT NULL,
  `opcao` VARCHAR(45) NULL,
  `Aposta_idAposta` INT NOT NULL,
  `Aposta_Utilizador_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idOdd`, `Jogo_idJogo`, `Aposta_idAposta`, `Aposta_Utilizador_email`),
  INDEX `fk_Odd_Jogo1_idx` (`Jogo_idJogo` ASC) VISIBLE,
  INDEX `fk_Odd_Aposta1_idx` (`Aposta_idAposta` ASC, `Aposta_Utilizador_email` ASC) VISIBLE,
  CONSTRAINT `fk_Odd_Jogo1`
    FOREIGN KEY (`Jogo_idJogo`)
    REFERENCES `mydb`.`Jogo` (`idJogo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Odd_Aposta1`
    FOREIGN KEY (`Aposta_idAposta` , `Aposta_Utilizador_email`)
    REFERENCES `mydb`.`Aposta` (`idAposta` , `Utilizador_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TipoUtilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TipoUtilizador` (
  `Tipo` VARCHAR(45) NOT NULL,
  `Utilizador_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Utilizador_email`),
  CONSTRAINT `fk_TipoUtilizador_Utilizador1`
    FOREIGN KEY (`Utilizador_email`)
    REFERENCES `mydb`.`Utilizador` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Notificação`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Notificação` (
  `idNotificação` INT NOT NULL,
  `conteudo` VARCHAR(45) NOT NULL,
  `vista` TINYINT NOT NULL,
  `Utilizador_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idNotificação`, `Utilizador_email`),
  INDEX `fk_Notificação_Utilizador1_idx` (`Utilizador_email` ASC) VISIBLE,
  CONSTRAINT `fk_Notificação_Utilizador1`
    FOREIGN KEY (`Utilizador_email`)
    REFERENCES `mydb`.`Utilizador` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
