DROP TABLE IF EXISTS `Articles_Chapters`;
CREATE TABLE IF NOT EXISTS `Articles_Chapters` (
  `art_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `text` text NOT NULL,
  `title` varchar(128) NOT NULL,
  `topic` varchar(128) DEFAULT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`art_id`),
  KEY `FK_Articles_Chapters_Publications` (`pub_id`),
  CONSTRAINT `FK_Articles_Chapters_Publications` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Authors`;
CREATE TABLE IF NOT EXISTS `Authors` (
  `person_id` int(11) NOT NULL,
  `type` char(16) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `Authors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Author_write_Articles_or_Chapters`;
CREATE TABLE IF NOT EXISTS `Author_write_Articles_or_Chapters` (
  `person_id` int(11) NOT NULL,
  `art_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`art_id`),
  KEY `art_id` (`art_id`),
  CONSTRAINT `Author_write_Articles_or_Chapters_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Authors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Author_write_Articles_or_Chapters_ibfk_2` FOREIGN KEY (`art_id`) REFERENCES `Articles_Chapters` (`art_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Author_write_Books`;
CREATE TABLE IF NOT EXISTS `Author_write_Books` (
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`pub_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `Author_write_Books_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Authors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Author_write_Books_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `Books` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Books`;
CREATE TABLE IF NOT EXISTS `Books` (
  `pub_id` int(11) NOT NULL,
  `edition` int(11) DEFAULT NULL,
  `ISBN` varchar(120) DEFAULT NULL,
  `topic` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`pub_id`),
  CONSTRAINT `Books_ibfk_1` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Distributors`;
CREATE TABLE IF NOT EXISTS `Distributors` (
  `person_id` int(11) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `contact_person` varchar(128) NOT NULL,
  `type` varchar(128) NOT NULL,
  `city` varchar(128) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `Distributors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Editors`;
CREATE TABLE IF NOT EXISTS `Editors` (
  `person_id` int(11) NOT NULL,
  `type` char(16) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `Editors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Editor_edit_Publications`;
CREATE TABLE IF NOT EXISTS `Editor_edit_Publications` (
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`pub_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `Editor_edit_Publications_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Editors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Editor_edit_Publications_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Issues`;
CREATE TABLE IF NOT EXISTS `Issues` (
  `pub_id` int(11) NOT NULL,
  `period_id` int(11) NOT NULL,
  PRIMARY KEY (`pub_id`),
  CONSTRAINT `Issues_ibfk_1` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE IF NOT EXISTS `Orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `num_of_copy` int(11) NOT NULL,
  `order_date` date NOT NULL,
  `delivery_date` date NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `shipping_cost` decimal(10,2) NOT NULL,
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `person_id` (`person_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Distributors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Orders_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `Payments`;
CREATE TABLE IF NOT EXISTS `Payments` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `type` varchar(128) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `person_id` int(11) NOT NULL,
  PRIMARY KEY (`pay_id`)
);

DROP TABLE IF EXISTS `Periodicals`;
CREATE TABLE IF NOT EXISTS `Periodicals` (
  `period_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  `periodicity` varchar(20) NOT NULL,
  `topic` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`period_id`)
);

DROP TABLE IF EXISTS `Persons`;
CREATE TABLE IF NOT EXISTS `Persons` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `gender` varchar(8),
  `age` int(11),
  `email` varchar(128),
  `phone_num` varchar(20),
  `address` varchar(128),
  PRIMARY KEY (`person_id`)
);

DROP TABLE IF EXISTS `Publications`;
CREATE TABLE IF NOT EXISTS `Publications` (
  `pub_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`pub_id`)
);

