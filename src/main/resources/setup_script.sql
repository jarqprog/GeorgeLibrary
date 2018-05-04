
DROP TABLE IF EXISTS `contents`;
DROP TABLE IF EXISTS `texts`;
DROP TABLE IF EXISTS `repositories`;
DROP TABLE IF EXISTS `addresses`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE IF NOT EXISTS `users` (
	`id`	INTEGER NOT NULL UNIQUE,
	`name`	TEXT NOT NULL,
	`surname`	TEXT NOT NULL,
	`email`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL DEFAULT 123,
	PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `addresses` (
	`id`	INTEGER NOT NULL UNIQUE,
	`postal_code`	TEXT NOT NULL,
	`city`	TEXT NOT NULL,
	`street`	TEXT NOT NULL,
	`house_no`	TEXT NOT NULL,
	`apartment_no`	TEXT DEFAULT '-',
	`user_id`	INTEGER NOT NULL,
	FOREIGN KEY(`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
	PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `repositories` (
	`id`	INTEGER NOT NULL UNIQUE,
	`name`	TEXT NOT NULL,
	`creation_date`	TEXT NOT NULL,
	`last_modification_date`	TEXT,
	`user_id`	INTEGER NOT NULL,
	FOREIGN KEY(`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
	PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `texts` (
	`id`	INTEGER NOT NULL UNIQUE,
	`title`	TEXT NOT NULL,
	`creation_date`	TEXT NOT NULL,
	`last_modification_date`	TEXT NOT NULL,
	`repository_id`	INTEGER NOT NULL,
	`user_id`	INTEGER NOT NULL,
	FOREIGN KEY(`repository_id`) REFERENCES `repositories`(`id`) ON DELETE CASCADE,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`user_id`) REFERENCES `users`(`id`)
);

CREATE TABLE IF NOT EXISTS `contents` (
	`id`	INTEGER NOT NULL UNIQUE,
	`filepath`	TEXT NOT NULL,
	`creation_date`	TEXT NOT NULL,
	`text_id`	INTEGER NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`text_id`) REFERENCES `texts`(`id`) ON DELETE CASCADE
);

INSERT INTO `users` (id,name,surname,email,password) VALUES (1,'Jarek','Kucharczyk','jarek@gmail.com','ohohoho++asasas');
INSERT INTO `users` (id,name,surname,email,password) VALUES (2,'Jacek','Kowalski','jacek@gmail.com','ohohoho++asasas');
INSERT INTO `users` (id,name,surname,email,password) VALUES (3,'Marcin','Nowak','m@gmail.com','123');

INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no,user_id) VALUES (1,'30-830','Kraków','Nad Potokiem','109','207',1);
INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no,user_id) VALUES (2,'30-100','Kraków','Rynek Główny','2','-',2);
INSERT INTO `addresses` (id,postal_code,city,street,house_no,apartment_no,user_id) VALUES (3,'42-125','Biała','Górska','120','-',3);

INSERT INTO `repositories` (id,name,creation_date,last_modification_date,user_id) VALUES (1,'Notki','2018-05-01 00:28:44','2018-05-01 00:28:44',2);
INSERT INTO `repositories` (id,name,creation_date,last_modification_date,user_id) VALUES (2,'Nowa nazwa','2018-05-01 00:07:02','2018-05-01 00:07:02',2);
INSERT INTO `repositories` (id,name,creation_date,last_modification_date,user_id) VALUES (3,'Coś tamki','2018-05-01 00:28:44','2018-05-01 00:28:44',3);

INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (1,'Rozdzial1','2018-05-01 10:01','2018-05-01 10:30',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (2,'Rozdzial2','2018-05-01 10:01','2018-05-01 10:30',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (3,'Rozdzial3','2018-05-01 10:01','2018-05-01 10:30',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (4,'rob1','2018-05-01 10:01','2018-05-01 10:30',2,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (5,'rob2','2018-02-01 10:01','2018-05-01 10:30',2,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (6,'rob1','2018-02-01 10:01','2018-05-01 10:30',3,3);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (7,'rob2','2018-02-01 10:01','2018-05-01 10:30',3,3);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (8,'rob13','2018-02-01 10:01','2018-05-01 10:30',3,3);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (9,'W pustyni i w puszczy','2018-05-03 17:21:55','2018-05-03 17:21:55',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (10,'W pustyni i w puszczy11','2018-05-03 17:43:23','2018-05-03 17:43:23',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (11,'W pustyni i w puszczy11','2018-05-03 17:44:09','2018-05-03 17:44:09',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (12,'W pustyni i w puszczy11','2018-05-03 17:44:28','2018-05-03 17:44:28',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (13,'W pustyni i w puszczy11','2018-05-03 17:46:10','2018-05-03 17:46:10',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (14,'W pustyni i w puszczy11','2018-05-03 18:12:18','2018-05-03 18:12:18',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (15,'W pustyni i w puszczy11','2018-05-03 18:12:22','2018-05-03 18:12:22',1,2);
INSERT INTO `texts` (id,title,creation_date,last_modification_date,repository_id,user_id) VALUES (16,'W pustyni i w puszczy11','2018-05-03 18:12:27','2018-05-03 18:12:27',1,2);

INSERT INTO `contents` (id,filepath,creation_date,text_id) VALUES (1,'src/main/resources/repositories/border/jel.txt001/1.txt','2018-05-03 16:01:32',1);
INSERT INTO `contents` (id,filepath,creation_date,text_id) VALUES (2,'src/main/resources/repositories/border/2/1/14/2018_05_03_18_12_18.md','2018-05-03 18:12:18',14);
INSERT INTO `contents` (id,filepath,creation_date,text_id) VALUES (3,'src/main/resources/repositories/border/2/1/15/2018_05_03_18_12_23.md','2018-05-03 18:12:23',15);
INSERT INTO `contents` (id,filepath,creation_date,text_id) VALUES (4,'src/main/resources/repositories/border/2/1/13/2018_05_03_18_31_32.md','2018-05-03 18:31:32',13);
INSERT INTO `contents` (id,filepath,creation_date,text_id) VALUES (5,'src/main/resources/repositories/border/2/1/16/2018_05_03_18_12_27.md','2018-05-03 18:12:27',16);
