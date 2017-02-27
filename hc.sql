-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: hc
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `status` enum('Booked','Cancelled','Complete') COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `schedule_id` (`schedule_id`,`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,15,2,'Booked'),(3,9,2,'Complete'),(4,42,2,'Cancelled');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Lars','Josiah','1-763-470-3974'),(2,'Samuel','Thomas','1-547-835-8114'),(3,'Armando','Hyatt','1-606-983-2657'),(4,'Josiah','Clark','1-587-510-6886'),(5,'Salvador','Benjamin','1-764-947-5389'),(6,'Axel','Cody','1-810-150-2781'),(7,'Xander','Ryder','1-732-376-5858'),(8,'Paul','Richard','1-508-346-0407'),(9,'Conan','Henry','1-704-856-9948'),(10,'Ronan','Samuel','1-617-255-6491');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `symptom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'Sigourney','Theodore','1-797-865-3688','P.O. Box 203, 6847 Id Street','luctus ut, pellentesque eget, dictum placerat,'),(2,'Orli','Chancellor','1-601-636-8173','Ap #834-5951 Nunc Road','posuere cubilia Curae; Phasellus ornare. Fusce mollis. Duis sit amet'),(3,'Irma','Salvador','1-796-334-6787','7487 Feugiat Street','pede. Nunc sed orci'),(4,'Britanney','Barrett','1-350-250-5414','P.O. Box 297, 8134 Duis Road','ac mattis ornare, lectus ante dictum mi, ac mattis'),(5,'Ainsley','Herrod','1-754-329-7107','Ap #797-8748 Porttitor Ave','varius ultrices, mauris ipsum porta elit, a'),(6,'Macy','Nero','1-964-376-8504','P.O. Box 326, 7916 Donec Avenue','Cum sociis natoque penatibus et magnis dis parturient montes,'),(7,'Selma','Keane','1-895-573-4936','P.O. Box 403, 6746 Ac St.','Vivamus non lorem vitae odio sagittis semper.'),(8,'Leandra','Giacomo','1-702-229-6689','9539 Praesent Rd.','a sollicitudin orci sem eget massa. Suspendisse eleifend. Cras sed'),(9,'Sybill','Brent','1-776-296-7020','736-1790 Aliquam Rd.','nulla. Integer'),(10,'Anika','Barrett','1-896-604-4434','754 Etiam Avenue','at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) NOT NULL,
  `day_of_week` enum('Mon','Tue','Wed','Thu','Fri') COLLATE utf8_unicode_ci NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,7,'Fri','23:37:00','15:00:00'),(2,8,'Thu','06:25:00','16:26:00'),(3,9,'Mon','07:58:00','15:26:00'),(4,5,'Tue','08:15:00','04:14:00'),(5,8,'Wed','11:58:00','18:07:00'),(6,1,'Tue','01:10:00','02:10:00'),(7,9,'Fri','20:10:00','01:11:00'),(8,2,'Fri','21:04:00','21:30:00'),(10,10,'Mon','18:30:00','19:57:00'),(11,6,'Thu','19:14:00','00:01:00'),(13,1,'Thu','11:44:00','16:05:00'),(14,10,'Tue','11:38:00','19:30:00'),(15,1,'Mon','17:32:00','20:38:00'),(16,2,'Wed','09:34:00','19:38:00'),(17,6,'Thu','06:14:00','17:16:00'),(18,4,'Tue','22:18:00','04:02:00'),(19,1,'Tue','00:26:00','08:13:00'),(20,6,'Tue','12:00:00','03:54:00'),(21,8,'Fri','03:20:00','06:04:00'),(22,3,'Tue','12:00:00','13:00:00'),(23,1,'Mon','20:30:00','03:03:00'),(24,9,'Thu','07:27:00','17:22:00'),(25,8,'Mon','22:44:00','19:04:00'),(26,3,'Tue','09:08:00','21:49:00'),(27,3,'Wed','02:08:00','04:18:00'),(28,2,'Wed','07:30:00','12:03:00'),(29,7,'Fri','08:07:00','14:22:00'),(30,9,'Mon','12:07:00','05:39:00'),(31,7,'Thu','10:33:00','17:54:00'),(32,5,'Tue','16:45:00','01:12:00'),(33,2,'Wed','05:29:00','19:28:00'),(34,7,'Mon','01:41:00','06:53:00'),(35,4,'Fri','09:43:00','07:20:00'),(36,5,'Tue','14:54:00','13:02:00'),(37,2,'Fri','00:04:00','00:37:00'),(38,5,'Wed','14:47:00','09:29:00'),(39,6,'Mon','08:25:00','18:57:00'),(40,1,'Tue','13:12:00','19:16:00'),(41,4,'Tue','05:30:00','23:55:00'),(42,10,'Thu','07:50:00','15:32:00'),(43,7,'Wed','05:44:00','01:38:00'),(44,7,'Thu','22:28:00','08:04:00'),(45,6,'Fri','23:38:00','14:46:00'),(46,1,'Wed','17:17:00','21:45:00'),(47,2,'Tue','17:04:00','07:14:00'),(48,8,'Tue','04:49:00','07:54:00'),(49,7,'Wed','23:59:00','20:35:00'),(50,2,'Mon','01:27:00','15:28:00'),(51,9,'Tue','09:57:00','21:06:00'),(52,2,'Thu','15:43:00','21:50:00'),(53,1,'Thu','15:54:00','22:46:00'),(54,5,'Thu','21:15:00','09:22:00'),(55,1,'Thu','21:57:00','09:04:00'),(56,9,'Mon','16:24:00','21:57:00'),(57,7,'Tue','17:10:00','12:42:00'),(58,4,'Fri','04:03:00','06:45:00'),(59,9,'Wed','19:59:00','16:25:00'),(60,6,'Mon','19:38:00','21:57:00'),(61,7,'Tue','19:42:00','14:26:00'),(62,8,'Tue','23:02:00','21:02:00'),(63,1,'Thu','09:49:00','18:56:00'),(64,8,'Fri','14:56:00','03:28:00'),(65,3,'Fri','01:44:00','13:47:00'),(66,9,'Wed','18:14:00','06:42:00'),(67,7,'Mon','05:47:00','13:45:00'),(68,5,'Wed','21:20:00','20:01:00'),(69,2,'Thu','14:33:00','10:32:00'),(70,7,'Wed','21:28:00','03:36:00'),(71,9,'Wed','03:18:00','21:51:00'),(72,1,'Mon','12:21:00','07:22:00'),(73,5,'Mon','08:08:00','20:23:00'),(74,9,'Tue','06:50:00','02:54:00'),(75,6,'Thu','23:12:00','08:52:00'),(76,7,'Mon','21:40:00','13:23:00'),(77,3,'Wed','22:46:00','08:55:00'),(78,8,'Mon','05:52:00','17:32:00'),(79,5,'Thu','13:54:00','19:30:00'),(80,2,'Thu','07:32:00','05:52:00'),(81,5,'Thu','11:06:00','00:53:00'),(82,3,'Tue','19:49:00','03:32:00'),(83,4,'Wed','06:39:00','02:03:00'),(84,4,'Tue','16:30:00','00:23:00'),(85,6,'Wed','10:23:00','11:14:00'),(86,5,'Wed','20:11:00','19:01:00'),(87,9,'Fri','06:08:00','20:56:00'),(88,5,'Wed','06:26:00','08:19:00'),(89,4,'Thu','03:30:00','09:54:00'),(90,7,'Mon','06:45:00','18:59:00'),(91,5,'Tue','03:36:00','20:08:00'),(92,10,'Mon','00:10:00','20:47:00'),(93,4,'Wed','16:10:00','02:35:00'),(94,9,'Mon','18:26:00','18:03:00'),(95,10,'Mon','05:33:00','19:32:00'),(96,3,'Thu','08:47:00','18:51:00'),(97,7,'Thu','10:48:00','15:24:00'),(98,5,'Fri','11:07:00','15:30:00'),(99,9,'Tue','17:34:00','11:10:00'),(100,7,'Wed','10:49:00','02:53:00'),(101,1,'Mon','23:00:00','00:00:00');
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-19 15:37:53
