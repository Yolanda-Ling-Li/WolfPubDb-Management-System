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

-- Dumping structure for table zli58.articles_chapters
DROP TABLE IF EXISTS `articles_chapters`;
CREATE TABLE IF NOT EXISTS `articles_chapters` (
  `art_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `text` text NOT NULL,
  `title` varchar(128) NOT NULL,
  `topic` varchar(128) DEFAULT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`art_id`),
  KEY `FK_articles_chapters_publications` (`pub_id`),
  CONSTRAINT `FK_articles_chapters_publications` FOREIGN KEY (`pub_id`) REFERENCES `publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.authors
DROP TABLE IF EXISTS `authors`;
CREATE TABLE IF NOT EXISTS `authors` (
  `person_id` int(11) NOT NULL,
  `type` char(16) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `authors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.author_write_articles_or_chapters
DROP TABLE IF EXISTS `author_write_articles_or_chapters`;
CREATE TABLE IF NOT EXISTS `author_write_articles_or_chapters` (
  `person_id` int(11) NOT NULL,
  `art_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`art_id`),
  KEY `art_id` (`art_id`),
  KEY `FK_author_write_articles_or_chapters_publications` (`pub_id`),
  CONSTRAINT `FK_author_write_articles_or_chapters_publications` FOREIGN KEY (`pub_id`) REFERENCES `publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `author_write_articles_or_chapters_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `authors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `author_write_articles_or_chapters_ibfk_2` FOREIGN KEY (`art_id`) REFERENCES `articles_chapters` (`art_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.author_write_books
DROP TABLE IF EXISTS `author_write_books`;
CREATE TABLE IF NOT EXISTS `author_write_books` (
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`pub_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `author_write_books_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `authors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `author_write_books_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `books` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.books
DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `pub_id` int(11) NOT NULL,
  `edition` int(11) DEFAULT NULL,
  `ISBN` varchar(120) DEFAULT NULL,
  `topic` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`pub_id`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`pub_id`) REFERENCES `publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.distributors
DROP TABLE IF EXISTS `distributors`;
CREATE TABLE IF NOT EXISTS `distributors` (
  `person_id` int(11) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `contact_person` varchar(128) NOT NULL,
  `phone_num` varchar(20) NOT NULL,
  `type` varchar(128) NOT NULL,
  `city` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `address` varchar(128) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `distributors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.editors
DROP TABLE IF EXISTS `editors`;
CREATE TABLE IF NOT EXISTS `editors` (
  `person_id` int(11) NOT NULL,
  `type` char(16) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `editors_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.editor_edit_publications
DROP TABLE IF EXISTS `editor_edit_publications`;
CREATE TABLE IF NOT EXISTS `editor_edit_publications` (
  `person_id` int(11) NOT NULL,
  `pub_id` int(11) NOT NULL,
  PRIMARY KEY (`person_id`,`pub_id`),
  KEY `pub_id` (`pub_id`),
  CONSTRAINT `editor_edit_publications_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `editors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `editor_edit_publications_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.issues
DROP TABLE IF EXISTS `issues`;
CREATE TABLE IF NOT EXISTS `issues` (
  `pub_id` int(11) NOT NULL,
  `period_id` int(11) NOT NULL,
  PRIMARY KEY (`pub_id`),
  CONSTRAINT `issues_ibfk_1` FOREIGN KEY (`pub_id`) REFERENCES `publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.orders
DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
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
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `distributors` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`pub_id`) REFERENCES `publications` (`pub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.payments
DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `type` varchar(128) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `person_id` int(11) NOT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.periodicals
DROP TABLE IF EXISTS `periodicals`;
CREATE TABLE IF NOT EXISTS `periodicals` (
  `period_id` int(11) NOT NULL AUTO_INCREMENT,
  `periodicity` varchar(20) NOT NULL,
  `topic` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`period_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.persons
DROP TABLE IF EXISTS `persons`;
CREATE TABLE IF NOT EXISTS `persons` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table zli58.publications
DROP TABLE IF EXISTS `publications`;
CREATE TABLE IF NOT EXISTS `publications` (
  `pub_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`pub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
