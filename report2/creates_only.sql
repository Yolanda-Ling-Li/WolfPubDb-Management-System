-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.1-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table zli58.Articles_Chapters
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Authors
DROP TABLE IF EXISTS `Authors`;
CREATE TABLE IF NOT EXISTS `Authors` (
  `person_id` int(11) NOT NULL,
  `type` char(16) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `Authors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Author_write_articles_or_chapters
DROP TABLE IF EXISTS `Author_write_articles_or_chapters`;
CREATE TABLE IF NOT EXISTS `Author_write_articles_or_chapters` (
  `person_id` int(11) NOT NULL,
  `art_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`art_id`),
  KEY `art_id` (`art_id`),
  KEY `FK_Author_write_articles_or_chapters_Publications` (`pub_id`),
  CONSTRAINT `FK_Author_write_articles_or_chapters_Publications` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Author_write_articles_or_chapters_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Authors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Author_write_articles_or_chapters_ibfk_2` FOREIGN KEY (`art_id`) REFERENCES `Articles_Chapters` (`art_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Author_write_Books
DROP TABLE IF EXISTS `Author_write_Books`;
CREATE TABLE IF NOT EXISTS `Author_write_Books` (
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`pub_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `Author_write_Books_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Authors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Author_write_Books_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `Books` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Books
DROP TABLE IF EXISTS `Books`;
CREATE TABLE IF NOT EXISTS `Books` (
  `pub_id` int(11) NOT NULL,
  `edition` int(11) DEFAULT NULL,
  `ISBN` varchar(120) DEFAULT NULL,
  `topic` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`pub_id`),
  CONSTRAINT `Books_ibfk_1` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Distributors
DROP TABLE IF EXISTS `Distributors`;
CREATE TABLE IF NOT EXISTS `Distributors` (
  `person_id` int(11) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `contact_person` varchar(128) NOT NULL,
  `phone_num` varchar(20) NOT NULL,
  `type` varchar(128) NOT NULL,
  `city` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `address` varchar(128) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `Distributors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Editors
DROP TABLE IF EXISTS `Editors`;
CREATE TABLE IF NOT EXISTS `Editors` (
  `person_id` int(11) NOT NULL,
  `type` char(16) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `Editors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Editor_edit_Publications
DROP TABLE IF EXISTS `Editor_edit_Publications`;
CREATE TABLE IF NOT EXISTS `Editor_edit_Publications` (
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`pub_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `Editor_edit_Publications_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Editors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Editor_edit_Publications_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Issues
DROP TABLE IF EXISTS `Issues`;
CREATE TABLE IF NOT EXISTS `Issues` (
  `pub_id` int(11) NOT NULL,
  `period_id` int(11) NOT NULL,
  PRIMARY KEY (`pub_id`),
  CONSTRAINT `Issues_ibfk_1` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Orders
DROP TABLE IF EXISTS `Orders`;
CREATE TABLE IF NOT EXISTS `Orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `num_of_copy` int(11) NOT NULL,
  `date` date NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `shipping_cost` decimal(10,2) NOT NULL,
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `person_id` (`person_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Distributors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Orders_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `Publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Payments
DROP TABLE IF EXISTS `Payments`;
CREATE TABLE IF NOT EXISTS `Payments` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `type` varchar(128) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `person_id` int(11) NOT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Periodicals
DROP TABLE IF EXISTS `Periodicals`;
CREATE TABLE IF NOT EXISTS `Periodicals` (
  `period_id` int(11) NOT NULL AUTO_INCREMENT,
  `periodicity` varchar(20) NOT NULL,
  `topic` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`period_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Persons
DROP TABLE IF EXISTS `Persons`;
CREATE TABLE IF NOT EXISTS `Persons` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.Publications
DROP TABLE IF EXISTS `Publications`;
CREATE TABLE IF NOT EXISTS `Publications` (
  `pub_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`pub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
