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
-- Table structure for table `objednavkaosoby`
--

DROP TABLE IF EXISTS `objednavkaosoby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objednavkaosoby` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `osobaID` int(11) DEFAULT NULL,
  `objID` int(11) DEFAULT NULL,
  `vybavilID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `osobaID` (`osobaID`),
  KEY `objID` (`objID`),
  KEY `vytvorilID_idx` (`vybavilID`),
  CONSTRAINT `objednavkaosoby_ibfk_1` FOREIGN KEY (`osobaID`) REFERENCES `osoba` (`id`),
  CONSTRAINT `objednavkaosoby_ibfk_2` FOREIGN KEY (`objID`) REFERENCES `objednavka` (`id`),
  CONSTRAINT `objednavkaosoby_ibfk_3` FOREIGN KEY (`vybavilID`) REFERENCES `osoba` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objednavkaosoby`
--

LOCK TABLES `objednavkaosoby` WRITE;
/*!40000 ALTER TABLE `objednavkaosoby` DISABLE KEYS */;
INSERT INTO `objednavkaosoby` VALUES (1,6,12,8),(2,7,13,8),(3,7,14,2),(4,6,15,8),(5,7,16,8),(6,10,17,13),(8,7,19,13),(10,23,21,22);
/*!40000 ALTER TABLE `objednavkaosoby` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-10 21:29:53
