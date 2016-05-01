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
-- Table structure for table `adresa`
--

DROP TABLE IF EXISTS `adresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ulica` varchar(50) DEFAULT NULL,
  `mesto` varchar(50) DEFAULT NULL,
  `PSC` varchar(50) DEFAULT NULL,
  `stat` varchar(50) DEFAULT NULL,
  `osobaID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `osobaID` (`osobaID`),
  CONSTRAINT `adresa_ibfk_1` FOREIGN KEY (`osobaID`) REFERENCES `osoba` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adresa`
--

LOCK TABLES `adresa` WRITE;
/*!40000 ALTER TABLE `adresa` DISABLE KEYS */;
INSERT INTO `adresa` VALUES (1,'Štefánikova 41','Dolný Kubín','958 41','\nSlovensko',2),(4,NULL,NULL,NULL,NULL,6),(6,NULL,NULL,NULL,NULL,8),(7,NULL,NULL,NULL,NULL,9),(8,NULL,NULL,NULL,NULL,10),(10,'Bratislavská','Senec','985 21','Slovensko',13),(11,NULL,NULL,NULL,NULL,7),(13,'Vyšná 11','Bratislava','950 55','Slovensko',16),(15,NULL,NULL,NULL,NULL,18),(19,'Konvalinková','Ružičky','02589','Rakúsko',22),(20,'Mechiho','Sjudad de Mechicho','','Mexiko',23);
/*!40000 ALTER TABLE `adresa` ENABLE KEYS */;
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
