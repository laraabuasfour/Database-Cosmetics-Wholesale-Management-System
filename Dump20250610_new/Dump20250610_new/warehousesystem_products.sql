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
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Olive Oil',25.50,18.00,'2025-12-31',150,1),(2,'Za\'atar',8.75,6.50,'2025-06-30',200,1),(3,'Tahini',12.00,9.00,'2025-08-15',120,2),(4,'Freekeh',15.50,11.00,'2025-10-31',80,2),(5,'Halloumi Cheese',22.00,16.50,'2024-08-30',60,3),(6,'Labneh',18.00,13.50,'2024-07-15',90,3),(7,'Sumac',6.50,4.75,'2025-12-31',250,4),(8,'Bulgur',9.25,7.00,'2025-09-30',180,4),(9,'Rose Water',14.00,10.50,'2025-11-30',75,5),(10,'Orange Blossom Water',16.50,12.00,'2025-11-30',65,5),(11,'Makloubeh Rice',11.75,8.50,'2025-12-31',300,6),(12,'Palestinian Dates',28.00,20.00,'2024-12-31',100,6),(13,'Fatteh Mix',7.25,5.50,'2025-07-31',150,7),(14,'Musakhan Spice',13.50,10.00,'2025-09-15',90,7),(15,'Palestinian Honey',45.00,32.00,'2026-01-31',40,8),(16,'Carob Molasses',19.75,14.50,'2025-08-31',70,8),(17,'White Cheese',21.50,16.00,'2024-09-15',85,9),(18,'Kashkaval Cheese',26.00,19.50,'2024-08-31',55,9),(19,'Palestinian Tea',8.00,6.00,'2025-12-31',400,10),(20,'Arabic Coffee',35.00,25.00,'2025-10-31',120,10),(21,'Knafeh Cheese',24.50,18.00,'2024-07-31',45,11),(22,'Phyllo Dough',5.75,4.25,'2024-09-30',200,11),(23,'Pine Nuts',85.00,65.00,'2025-06-30',25,12),(24,'Almonds',32.00,24.00,'2025-08-31',60,12),(25,'Pistachios',75.00,58.00,'2025-07-31',35,13),(26,'Walnuts',28.50,21.00,'2025-09-30',80,13),(27,'Dried Figs',22.75,17.00,'2025-01-31',70,14),(28,'Dried Apricots',26.00,19.50,'2025-02-28',55,14),(29,'Pomegranate Molasses',17.25,13.00,'2025-12-31',95,15),(30,'Grape Leaves',4.50,3.25,'2024-10-31',300,15),(31,'Chickpeas',6.75,5.00,'2025-12-31',400,16),(32,'Lentils',7.50,5.50,'2025-12-31',350,16),(33,'Fava Beans',8.25,6.00,'2025-11-30',280,17),(34,'Black Beans',9.50,7.00,'2025-10-31',220,17),(35,'Semolina',5.25,3.75,'2025-08-31',500,18),(36,'All Purpose Flour',4.75,3.50,'2025-07-31',600,18),(37,'Coarse Salt',2.50,1.75,'2026-12-31',800,19),(38,'Sea Salt',8.50,6.25,'2026-12-31',150,19),(39,'Black Pepper',25.00,18.50,'2025-12-31',45,20),(40,'Cumin',18.75,14.00,'2025-11-30',80,20),(41,'Coriander',16.50,12.25,'2025-10-31',70,21),(42,'Cardamom',65.00,48.00,'2025-12-31',20,21),(43,'Cinnamon Sticks',32.50,24.00,'2025-09-30',35,22),(44,'Bay Leaves',12.75,9.50,'2025-08-31',60,22),(45,'Dried Mint',11.25,8.25,'2025-07-31',90,23),(46,'Dried Thyme',9.75,7.25,'2025-06-30',110,23),(47,'Turmeric',13.00,9.75,'2025-12-31',85,24),(48,'Paprika',15.50,11.50,'2025-11-30',75,24),(49,'Chili Powder',17.75,13.25,'2025-10-31',65,25),(50,'Ginger Powder',22.25,16.50,'2025-09-30',50,25),(51,'Garlic Powder',14.75,11.00,'2025-08-31',95,26),(52,'Onion Powder',12.50,9.25,'2025-07-31',100,26),(53,'Sesame Seeds',19.00,14.25,'2025-12-31',120,27),(54,'Nigella Seeds',24.50,18.25,'2025-11-30',40,27),(55,'Fennel Seeds',21.75,16.25,'2025-10-31',55,28),(56,'Anise Seeds',26.00,19.50,'2025-09-30',45,28),(57,'Coconut Oil',28.75,21.50,'2025-12-31',60,29),(58,'Sunflower Oil',15.25,11.50,'2025-08-31',200,29),(59,'Corn Oil',16.75,12.50,'2025-07-31',180,30),(60,'Canola Oil',18.50,13.75,'2025-06-30',150,30),(61,'Apple Vinegar',9.25,6.75,'2025-12-31',140,31),(62,'White Vinegar',7.75,5.75,'2025-12-31',160,31),(63,'Balsamic Vinegar',24.00,18.00,'2025-10-31',30,32),(64,'Pomegranate Vinegar',19.50,14.50,'2025-09-30',50,32),(65,'Tomato Paste',3.75,2.75,'2024-12-31',400,33),(66,'Harissa Paste',12.25,9.00,'2025-06-30',80,33),(67,'Tamarind Paste',16.75,12.50,'2025-08-31',60,34),(68,'Fig Jam',21.50,16.00,'2025-01-31',70,34),(69,'Apricot Jam',19.75,14.75,'2025-02-28',75,35),(70,'Orange Marmalade',18.25,13.50,'2025-03-31',80,35),(71,'Grape Molasses',23.50,17.50,'2025-12-31',55,36),(72,'Date Syrup',27.75,20.50,'2025-11-30',45,36),(73,'Carob Syrup',25.25,18.75,'2025-10-31',40,37),(74,'Rose Syrup',22.00,16.50,'2025-09-30',50,37),(75,'Mint Syrup',20.50,15.25,'2025-08-31',60,38),(76,'Lemon Syrup',18.75,14.00,'2025-07-31',70,38),(77,'Pickled Turnips',8.50,6.25,'2024-11-30',120,39),(78,'Pickled Cucumbers',7.25,5.50,'2024-10-31',140,39),(79,'Pickled Olives',15.75,11.75,'2025-06-30',100,40),(80,'Stuffed Olives',22.50,16.75,'2025-05-31',60,40),(81,'Green Olives',18.25,13.50,'2025-04-30',90,41),(82,'Black Olives',20.75,15.50,'2025-03-31',80,41),(83,'Kalamata Olives',28.50,21.25,'2025-02-28',40,42),(84,'Mixed Olives',24.75,18.50,'2025-01-31',55,42),(85,'Pita Bread',2.25,1.75,'2024-07-05',500,43),(86,'Arabic Bread',1.75,1.25,'2024-07-05',600,43),(87,'Manakish Bread',4.50,3.25,'2024-07-05',200,44),(88,'Ka\'ak Bread',3.75,2.75,'2024-07-05',250,44),(89,'Taboon Bread',2.75,2.00,'2024-07-05',400,45),(90,'Shrak Bread',2.00,1.50,'2024-07-05',450,45),(91,'Baklava',35.00,26.00,'2024-08-15',30,46),(92,'Mamoul',28.75,21.50,'2024-09-30',40,46),(93,'Knafeh',32.50,24.25,'2024-07-31',25,47),(94,'Muhallabia',15.50,11.50,'2024-08-31',60,47),(95,'Atayef',18.25,13.50,'2024-09-15',50,48),(96,'Qatayef',20.75,15.50,'2024-08-15',45,48),(97,'Halawet El Jibn',42.50,31.75,'2024-07-31',20,49),(98,'Aish El Saraya',38.00,28.50,'2024-08-31',25,49),(99,'Turkish Delight',26.50,19.75,'2025-01-31',35,50),(100,'Nougat',31.25,23.25,'2025-02-28',30,50);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-10 17:41:54
