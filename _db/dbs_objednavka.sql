-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: dbs
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `objednavka`
--

DROP TABLE IF EXISTS `objednavka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objednavka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cislo_obj` varchar(50) DEFAULT NULL,
  `nastup` varchar(50) DEFAULT NULL,
  `ciel` varchar(15) DEFAULT NULL,
  `casVyzdv` time DEFAULT NULL,
  `datum` date DEFAULT NULL,
  `vybavena` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objednavka`
--

LOCK TABLES `objednavka` WRITE;
/*!40000 ALTER TABLE `objednavka` DISABLE KEYS */;
INSERT INTO `objednavka` VALUES (12,'2016001','Starého','Nám. Slobody','10:50:00','2016-03-02',1),(13,'2016002','Nám. Ľ. Štúra','Stranianská 54','14:25:00','2016-04-04',1),(14,'2016004','Ilkovičova 3','Mladá','15:00:00','2016-04-04',1),(15,'2016005','Novanského','Obrancov mieru','15:58:25','2016-04-05',1),(16,'2016006','Stará','Mladá 24/A','21:25:00','2016-04-05',1),(17,'2016007','Milkovičova','Ilkovičova 2','05:00:00','2016-04-09',1),(18,'2016008','Malého 4','Nám. Slobody','17:20:00','2016-04-10',1),(19,'2016009','Nám. Slobody','Malého 4','20:50:00','2016-04-10',0),(20,'2016010','Stromokocúra 21','Budovateľská 9','18:25:20','2016-04-12',1),(21,'2016011','Staré Grunty 53','Karloveská 24','15:54:25','2016-05-12',0);
/*!40000 ALTER TABLE `objednavka` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-10 21:29:54
