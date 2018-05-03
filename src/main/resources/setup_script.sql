DROP TABLE IF EXISTS `contents`;
CREATE TABLE IF NOT EXISTS `contents` (
	`id`	INTEGER NOT NULL UNIQUE,
	`filepath`	TEXT NOT NULL UNIQUE,
	`creation_date`	TEXT NOT NULL,
	`text_id`	INTEGER NOT NULL,
	FOREIGN KEY(`text_id`) REFERENCES `texts`(`id`),
	PRIMARY KEY(`id`)
);
DROP TABLE IF EXISTS `texts`;
CREATE TABLE IF NOT EXISTS `texts` (
	`id`	INTEGER NOT NULL UNIQUE,
	`title`	TEXT NOT NULL,
	`creation_date`	TEXT NOT NULL,
	`last_modification_date`	TEXT NOT NULL,
	`repository_id`	INTEGER NOT NULL,
	`user_id`	INTEGER NOT NULL,
	FOREIGN KEY(`repository_id`) REFERENCES `repositories`(`id`),
	PRIMARY KEY(`id`)
);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (1,'Rozdzial1','2018-05-01 10:01','2018-05-01 10:30',1,1);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (2,'Rozdzial2','2018-05-01 10:01','2018-05-01 10:30',1,1);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (3,'Rozdzial3','2018-05-01 10:01','2018-05-01 10:30',1,1);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (4,'rob1','2018-05-01 10:01','2018-05-01 10:30',2,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (5,'rob2','2018-02-01 10:01','2018-05-01 10:30',2,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (6,'rob1','2018-02-01 10:01','2018-05-01 10:30',3,3);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (7,'rob2','2018-02-01 10:01','2018-05-01 10:30',3,3);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (8,'rob13','2018-02-01 10:01','2018-05-01 10:30',3,3);
DROP TABLE IF EXISTS `repositories`;
CREATE TABLE IF NOT EXISTS `repositories` (
	`id`	INTEGER NOT NULL UNIQUE,
	`name`	TEXT NOT NULL,
	`creation_date`	TEXT NOT NULL,
	`last_modification_date`	TEXT,
	`user_id`	INTEGER NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`user_id`) REFERENCES `users`(`id`)
);
INSERT INTO `repositories` (id,name,creation_date,last_modification_date,user_id) VALUES (1,'Notki','2018-05-01 00:28:44','2018-05-01 00:28:44',1);
INSERT INTO `repositories` (id,name,creation_date,last_modification_date,user_id) VALUES (2,'Nowa nazwa','2018-05-01 00:07:02','2018-05-01 00:07:02',2);
INSERT INTO `repositories` (id,name,creation_date,last_modification_date,user_id) VALUES (3,'Coś tamki','2018-05-01 00:28:44','2018-05-01 00:28:44',3);
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`id`	INTEGER NOT NULL UNIQUE,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`email`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL DEFAULT 123,
	PRIMARY KEY(`id`)
);
INSERT INTO `users` (id,name,surname,email,password) VALUES (1,'Jarek','Kucharczyk','jarek@gmail.com','ohohoho++asasas');
INSERT INTO `users` (id,name,surname,email,password) VALUES (2,'Jacek','Kowalski','jacek@gmail.com','ohohoho++asasas');
INSERT INTO `users` (id,name,surname,email,password) VALUES (3,'Marcin','Nowak','m@gmail.com','123');
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (
	`id`	INTEGER NOT NULL UNIQUE,
	`postal_code`	TEXT NOT NULL,
	`city`	TEXT NOT NULL,
	`street`	TEXT NOT NULL,
	`house_no`	TEXT NOT NULL,
	`apartment_no`	TEXT DEFAULT '-',
	`user_id`	INTEGER NOT NULL,
	FOREIGN KEY(`user_id`) REFERENCES `users`(`id`),
	PRIMARY KEY(`id`)
);
INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no,user_id) VALUES (1,'30-830','Kraków','Nad Potokiem','109','207',1);
INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no,user_id) VALUES (2,'30-100','Kraków','Rynek Główny','2','-',2);
INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no,user_id) VALUES (3,'42-125','Biała','Górska','120','-',3);