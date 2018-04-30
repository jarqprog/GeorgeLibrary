
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`id`	INTEGER NOT NULL PRIMARY KEY UNIQUE,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`email`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL,
	`address_id`	INTEGER NOT NULL,
	FOREIGN KEY(`address_id`) REFERENCES `addresses`(`id`)
);
DROP TABLE IF EXISTS `texts`;
CREATE TABLE IF NOT EXISTS `texts` (
	`id`	INTEGER NOT NULL PRIMARY KEY UNIQUE,
	`title`	TEXT NOT NULL,
	`creation_date`	TEXT,
	`last_modification_date`	TEXT,
	`repository_id`	INTEGER NOT NULL,
	`content`	TEXT,
	FOREIGN KEY(`repository_id`) REFERENCES `repositories`(`id`)
);
DROP TABLE IF EXISTS `repositories`;
CREATE TABLE IF NOT EXISTS `repositories` (
	`id`	INTEGER NOT NULL PRIMARY KEY UNIQUE,
	`name`	TEXT NOT NULL,
	`creation_date`	TEXT NOT NULL,
	`last_modification_date`	TEXT,
	`owner_id`	INTEGER NOT NULL,
	FOREIGN KEY(`owner_id`) REFERENCES `users`(`id`)
);
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (
	`id`	INTEGER NOT NULL PRIMARY KEY UNIQUE,
	`postal_code`	TEXT NOT NULL,
	`city`	TEXT NOT NULL,
	`street`	TEXT NOT NULL,
	`house_no`	TEXT NOT NULL,
	`apartment_no`	TEXT
);
INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no) VALUES (1,'30-830','Kraków','Nad Potokiem','109','207'),
 (2,'42-125','Biała','Studzienna','1722','');

