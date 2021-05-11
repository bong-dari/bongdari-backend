

CREATE TABLE IF NOT EXISTS `jtest`.`member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(255) NULL,
  `name` VARCHAR(255) NULL,
  `sms_agreement` BIT NULL,
  `birth_date` DATE NULL,
  `contact` VARCHAR(255) NULL,
  `created_date` DATE NULL,
  `email` VARCHAR(255) NULL,
  `gender` INT NULL,
  `is_deleted` BIT NULL,
  `sns` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jtest`.`board` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `capacity` INT NULL,
  `category` INT NULL,
  `contact` VARCHAR(255) NULL,
  `details` VARCHAR(255) NULL,
  `end_date` DATE NULL,
  `start_date` DATE NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_board_member_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `jtest`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jtest`.`institution` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(255) NULL,
  `address` VARCHAR(255) NULL,
  `city` VARCHAR(255) NULL,
  `contact` VARCHAR(255) NULL,
  `created_date` DATE NULL,
  `latitude` FLOAT NULL,
  `longitude` FLOAT NULL,
  `gu` VARCHAR(255) NULL,
  `name` VARCHAR(255) NULL,
  `password` VARCHAR(255) NULL,
  `unique_name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jtest`.`volunteer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `capacity` INT NULL,
  `contact` VARCHAR(255) NULL,
  `created_date` DATE NULL,
  `details` VARCHAR(255) NULL,
  `end_date` DATE NULL,
  `gender` INT NULL,
  `location` VARCHAR(255) NULL,
  `manager` VARCHAR(255) NULL,
  `start_date` DATE NULL,
  `time` VARCHAR(255) NULL,
  `title` VARCHAR(255) NULL,
  `institution_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_volunteer_institution1_idx` (`institution_id` ASC) VISIBLE,
  CONSTRAINT `fk_volunteer_institution1`
    FOREIGN KEY (`institution_id`)
    REFERENCES `jtest`.`institution` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jtest`.`application` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `is_canceled` BIT NULL,
  `member_id` BIGINT NOT NULL,
  `volunteer_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_application_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_application_volunteer1_idx` (`volunteer_id` ASC) VISIBLE,
  CONSTRAINT `fk_application_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `jtest`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_application_volunteer1`
    FOREIGN KEY (`volunteer_id`)
    REFERENCES `jtest`.`volunteer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `jtest`.`status` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `capacity` INT NULL,
  `date` DATE NULL,
  `volunteer_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_volunteer1_idx` (`volunteer_id` ASC) VISIBLE,
  CONSTRAINT `fk_status_volunteer1`
    FOREIGN KEY (`volunteer_id`)
    REFERENCES `jtest`.`volunteer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;