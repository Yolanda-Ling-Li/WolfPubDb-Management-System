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

-- Dumping data for table zli58.articles_chapters: ~8 rows (approximately)
/*!40000 ALTER TABLE `articles_chapters` DISABLE KEYS */;
INSERT INTO `articles_chapters` (`art_id`, `date`, `text`, `title`, `topic`) VALUES
	(1, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(2, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(3, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(4, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(5, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(6, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(7, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL),
	(8, '2020-03-17', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, ', 'Yet another title', NULL);
/*!40000 ALTER TABLE `articles_chapters` ENABLE KEYS */;

-- Dumping data for table zli58.authors: ~4 rows (approximately)
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` (`person_id`, `type`) VALUES
	(2, 'staff'),
	(8, 'invited'),
	(9, 'staff'),
	(10, 'invited');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;

-- Dumping data for table zli58.author_write_articles_or_chapters: ~4 rows (approximately)
/*!40000 ALTER TABLE `author_write_articles_or_chapters` DISABLE KEYS */;
INSERT INTO `author_write_articles_or_chapters` (`person_id`, `art_id`, `pub_id`) VALUES
	(2, 1, 1),
	(2, 2, 1),
	(2, 3, 2),
	(2, 4, 2);
/*!40000 ALTER TABLE `author_write_articles_or_chapters` ENABLE KEYS */;

-- Dumping data for table zli58.author_write_books: ~4 rows (approximately)
/*!40000 ALTER TABLE `author_write_books` DISABLE KEYS */;
INSERT INTO `author_write_books` (`person_id`, `pub_id`) VALUES
	(10, 1),
	(10, 2),
	(10, 3),
	(10, 4);
/*!40000 ALTER TABLE `author_write_books` ENABLE KEYS */;

-- Dumping data for table zli58.books: ~4 rows (approximately)
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` (`pub_id`, `edition`, `ISBN`, `topic`) VALUES
	(1, 1, '123', NULL),
	(2, 1, '123', NULL),
	(3, 1, '123', NULL),
	(4, 1, '123', NULL);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- Dumping data for table zli58.distributors: ~4 rows (approximately)
/*!40000 ALTER TABLE `distributors` DISABLE KEYS */;
INSERT INTO `distributors` (`person_id`, `balance`, `contact_person`, `phone_num`, `type`, `city`, `name`, `address`) VALUES
	(4, 100.00, 'Alice', '911', 'wholesale', 'Raleigh', 'Unknown', 'Unknown'),
	(5, 100.00, 'Alice', '911', 'wholesale', 'Raleigh', 'Unknown', 'Unknown'),
	(6, 100.00, 'Alice', '911', 'wholesale', 'Raleigh', 'Unknown', 'Unknown'),
	(7, 100.00, 'Alice', '911', 'wholesale', 'Raleigh', 'Unknown', 'Unknown');
/*!40000 ALTER TABLE `distributors` ENABLE KEYS */;

-- Dumping data for table zli58.editors: ~4 rows (approximately)
/*!40000 ALTER TABLE `editors` DISABLE KEYS */;
INSERT INTO `editors` (`person_id`, `type`) VALUES
	(3, 'staff'),
	(11, 'invited'),
	(12, 'staff'),
	(16, 'staff');
/*!40000 ALTER TABLE `editors` ENABLE KEYS */;

-- Dumping data for table zli58.editor_edit_publications: ~4 rows (approximately)
/*!40000 ALTER TABLE `editor_edit_publications` DISABLE KEYS */;
INSERT INTO `editor_edit_publications` (`person_id`, `pub_id`) VALUES
	(12, 5),
	(16, 2),
	(16, 5),
	(16, 7);
/*!40000 ALTER TABLE `editor_edit_publications` ENABLE KEYS */;

-- Dumping data for table zli58.issues: ~4 rows (approximately)
/*!40000 ALTER TABLE `issues` DISABLE KEYS */;
INSERT INTO `issues` (`pub_id`, `period_id`) VALUES
	(5, 1),
	(6, 1),
	(7, 1),
	(8, 2);
/*!40000 ALTER TABLE `issues` ENABLE KEYS */;

-- Dumping data for table zli58.orders: ~4 rows (approximately)
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`order_id`, `num_of_copy`, `date`, `price`, `shipping_cost`, `person_id`, `pub_id`) VALUES
	(1, 1, '2020-03-17', 100.00, 100.00, 6, 1),
	(2, 1, '2020-03-17', 100.00, 100.00, 6, 1),
	(3, 1, '2020-03-17', 100.00, 100.00, 6, 1),
	(4, 1, '2020-03-17', 100.00, 100.00, 6, 1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dumping data for table zli58.payments: ~4 rows (approximately)
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` (`pay_id`, `date`, `type`, `amount`, `person_id`) VALUES
	(1, '2020-03-17', 'one-time', 100.00, 1),
	(2, '2020-03-17', 'monthly', 1.00, 2),
	(3, '2020-03-17', 'weekly', 200.00, 3),
	(4, '2020-03-15', 'one-time', 3.00, 4);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;

-- Dumping data for table zli58.periodicals: ~4 rows (approximately)
/*!40000 ALTER TABLE `periodicals` DISABLE KEYS */;
INSERT INTO `periodicals` (`period_id`, `periodicity`, `topic`) VALUES
	(1, 'monthly', NULL),
	(2, 'weekly', NULL),
	(3, 'weekly', NULL),
	(4, 'weekly', NULL);
/*!40000 ALTER TABLE `periodicals` ENABLE KEYS */;

-- Dumping data for table zli58.persons: ~16 rows (approximately)
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` (`person_id`, `type`, `name`) VALUES
	(1, 'admin', 'Alice'),
	(2, 'author', 'Bob'),
	(3, 'editor', 'Emma'),
	(4, 'distributor', 'A'),
	(5, 'distributor', 'B'),
	(6, 'distributor', 'C'),
	(7, 'distributor', 'D'),
	(8, 'author', 'E'),
	(9, 'author', 'F'),
	(10, 'author', 'G'),
	(11, 'editor', 'H'),
	(12, 'editor', 'I'),
	(13, 'admin', 'J'),
	(14, 'admin', 'K'),
	(15, 'admin', 'L'),
	(16, 'editor', 'M');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;

-- Dumping data for table zli58.publications: ~8 rows (approximately)
/*!40000 ALTER TABLE `publications` DISABLE KEYS */;
INSERT INTO `publications` (`pub_id`, `title`, `date`) VALUES
	(1, 'Book1', '2020-03-17'),
	(2, 'Book2', '2020-03-17'),
	(3, 'Book3', '2020-03-17'),
	(4, 'Book4', '2020-03-17'),
	(5, 'Issue1', '2020-03-17'),
	(6, 'Issue2', '2020-03-17'),
	(7, 'Issue3', '2020-03-17'),
	(8, 'Issue4', '2020-03-17');
/*!40000 ALTER TABLE `publications` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
