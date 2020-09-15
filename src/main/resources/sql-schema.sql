drop schema ims;
CREATE SCHEMA IF NOT EXISTS `ims`;
USE `ims` ;
CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) NULL DEFAULT NULL,
    `surname` VARCHAR(40) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `item_name` VARCHAR(40) NULL DEFAULT NULL,
    `price` DOUBLE(5,2) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`order_customer` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`customer_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`)
);
CREATE TABLE IF NOT EXISTS `ims`.`order_products` (
	`id` INT(11) NOT NULL,
	`item_id` INT(11) NOT NULL,
	`quantity` INT(11) NOT NULL,
	FOREIGN KEY (`id`) REFERENCES `order_customer`(`id`),
	FOREIGN KEY (`item_id`) REFERENCES `items`(`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`accounts` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(40) NULL DEFAULT NULL,
    `password` VARCHAR(40) NULL DEFAULT NULL,
    `is_admin` BOOLEAN NULL DEFAULT NULL
);