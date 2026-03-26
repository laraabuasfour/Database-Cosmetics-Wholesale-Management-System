-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: warehousesystem
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Al Salam Market',1,'2024-12-02'),(2,'Nablus Fresh Store',4,'2024-12-05'),(3,'Gaza Central Shop',1,'2024-12-08'),(4,'Hebron Trade Center',9,'2024-12-11'),(5,'Jenin Food Market',1,'2024-12-14'),(6,'Tulkarm Grocery',2,'2024-12-17'),(7,'Bethlehem Corner Store',1,'2024-12-20'),(8,'Qalqilya Mini Market',5,'2024-12-23'),(9,'Salfit Local Shop',1,'2024-12-26'),(10,'Jericho Date Store',4,'2024-12-29'),(11,'Al Salam Market',9,'2024-12-03'),(12,'Nablus Fresh Store',2,'2024-12-06'),(13,'Gaza Central Shop',5,'2024-12-09'),(14,'Hebron Trade Center',4,'2024-12-12'),(15,'Jenin Food Market',9,'2024-12-15'),(16,'Tulkarm Grocery',4,'2025-01-03'),(17,'Bethlehem Corner Store',1,'2025-01-06'),(18,'Qalqilya Mini Market',4,'2025-01-09'),(19,'Salfit Local Shop',9,'2025-01-12'),(20,'Jericho Date Store',4,'2025-01-15'),(21,'Al Salam Market',2,'2025-01-18'),(22,'Nablus Fresh Store',4,'2025-01-21'),(23,'Gaza Central Shop',5,'2025-01-24'),(24,'Hebron Trade Center',4,'2025-01-27'),(25,'Jenin Food Market',1,'2025-01-30'),(26,'Tulkarm Grocery',9,'2025-01-04'),(27,'Bethlehem Corner Store',2,'2025-01-07'),(28,'Qalqilya Mini Market',5,'2025-01-10'),(29,'Salfit Local Shop',1,'2025-01-13'),(30,'Jericho Date Store',9,'2025-01-16'),(31,'Al Salam Market',9,'2025-02-02'),(32,'Nablus Fresh Store',1,'2025-02-05'),(33,'Gaza Central Shop',9,'2025-02-08'),(34,'Hebron Trade Center',4,'2025-02-11'),(35,'Jenin Food Market',9,'2025-02-14'),(36,'Tulkarm Grocery',2,'2025-02-17'),(37,'Bethlehem Corner Store',9,'2025-02-20'),(38,'Qalqilya Mini Market',5,'2025-02-23'),(39,'Salfit Local Shop',9,'2025-02-26'),(40,'Jericho Date Store',1,'2025-02-03'),(41,'Al Salam Market',4,'2025-02-06'),(42,'Nablus Fresh Store',2,'2025-02-09'),(43,'Gaza Central Shop',5,'2025-02-12'),(44,'Hebron Trade Center',1,'2025-02-15'),(45,'Jenin Food Market',4,'2025-02-18'),(46,'Tulkarm Grocery',2,'2025-03-03'),(47,'Bethlehem Corner Store',1,'2025-03-06'),(48,'Qalqilya Mini Market',2,'2025-03-09'),(49,'Salfit Local Shop',4,'2025-03-12'),(50,'Jericho Date Store',2,'2025-03-15'),(51,'Al Salam Market',9,'2025-03-18'),(52,'Nablus Fresh Store',2,'2025-03-21'),(53,'Gaza Central Shop',5,'2025-03-24'),(54,'Hebron Trade Center',2,'2025-03-27'),(55,'Jenin Food Market',1,'2025-03-30'),(56,'Tulkarm Grocery',4,'2025-03-04'),(57,'Bethlehem Corner Store',9,'2025-03-07'),(58,'Qalqilya Mini Market',5,'2025-03-10'),(59,'Salfit Local Shop',1,'2025-03-13'),(60,'Jericho Date Store',4,'2025-03-16'),(61,'Al Salam Market',5,'2025-04-02'),(62,'Nablus Fresh Store',1,'2025-04-05'),(63,'Gaza Central Shop',5,'2025-04-08'),(64,'Hebron Trade Center',4,'2025-04-11'),(65,'Jenin Food Market',5,'2025-04-14'),(66,'Tulkarm Grocery',9,'2025-04-17'),(67,'Bethlehem Corner Store',5,'2025-04-20'),(68,'Qalqilya Mini Market',2,'2025-04-23'),(69,'Salfit Local Shop',5,'2025-04-26'),(70,'Jericho Date Store',1,'2025-04-29'),(71,'Al Salam Market',4,'2025-04-03'),(72,'Nablus Fresh Store',9,'2025-04-06'),(73,'Gaza Central Shop',2,'2025-04-09'),(74,'Hebron Trade Center',1,'2025-04-12'),(75,'Jenin Food Market',4,'2025-04-15'),(76,'Tulkarm Grocery',1,'2025-05-02'),(77,'Bethlehem Corner Store',4,'2025-05-05'),(78,'Qalqilya Mini Market',1,'2025-05-08'),(79,'Salfit Local Shop',9,'2025-05-11'),(80,'Jericho Date Store',1,'2025-05-14'),(81,'Al Salam Market',2,'2025-05-17'),(82,'Nablus Fresh Store',1,'2025-05-20'),(83,'Gaza Central Shop',5,'2025-05-23'),(84,'Hebron Trade Center',1,'2025-05-26'),(85,'Jenin Food Market',4,'2025-05-29'),(86,'Tulkarm Grocery',9,'2025-05-03'),(87,'Bethlehem Corner Store',2,'2025-05-06'),(88,'Qalqilya Mini Market',5,'2025-05-09'),(89,'Salfit Local Shop',4,'2025-05-12'),(90,'Jericho Date Store',9,'2025-05-15'),(91,'Al Salam Market',4,'2025-06-02'),(92,'Nablus Fresh Store',1,'2025-06-05'),(93,'Gaza Central Shop',4,'2025-06-08'),(94,'Hebron Trade Center',9,'2025-06-11'),(95,'Jenin Food Market',4,'2025-06-14'),(96,'Tulkarm Grocery',2,'2025-06-17'),(97,'Bethlehem Corner Store',4,'2025-06-20'),(98,'Qalqilya Mini Market',5,'2025-06-23'),(99,'Salfit Local Shop',1,'2025-06-26'),(100,'Jericho Date Store',9,'2025-06-29');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-10 17:41:55
