BEGIN TRANSACTION;
DROP TABLE IF EXISTS `texts`;
CREATE TABLE IF NOT EXISTS `texts` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`title`	TEXT NOT NULL,
	`creationDate`	TEXT,
	`lastModificationDate`	TEXT,
	`repositoryId`	INTEGER NOT NULL,
	`content`	TEXT,
	FOREIGN KEY(`repositoryId`) REFERENCES `repositories`(`id`)
);
DROP TABLE IF EXISTS `repositories`;
CREATE TABLE IF NOT EXISTS `repositories` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	TEXT NOT NULL,
	`creationDate`	TEXT NOT NULL,
	`lastModificationDate`	TEXT,
	`ownerId`	INTEGER NOT NULL,
	FOREIGN KEY(`ownerId`) REFERENCES `users`(`id`)
);
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`email`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL,
	`addressId`	INTEGER NOT NULL,
	FOREIGN KEY(`addressId`) REFERENCES `addresses`(`id`)
);
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`postalCode`	TEXT NOT NULL,
	`city`	TEXT NOT NULL,
	`street`	TEXT NOT NULL,
	`houseNo`	TEXT NOT NULL,
	`apartmentNo`	TEXT
);
INSERT INTO `addresses` (id,postalCode,city,street,houseNo,apartmentNo) VALUES (1,'30-830','Kraków','Nad Potokiem','109','207'),
 (2,'42-125','Biała','Studzienna','1722',NULL);
COMMIT;
