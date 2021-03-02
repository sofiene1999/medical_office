-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: cabinet
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facture` (
  `code` int unsigned NOT NULL AUTO_INCREMENT,
  `total` smallint unsigned DEFAULT NULL,
  `paye` smallint unsigned DEFAULT NULL,
  `fin` date DEFAULT NULL,
  `debut` date DEFAULT NULL,
  `id_patient` int unsigned DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_id_patient` (`id_patient`),
  CONSTRAINT `fk_id_patient` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facture`
--

LOCK TABLES `facture` WRITE;
/*!40000 ALTER TABLE `facture` DISABLE KEYS */;
INSERT INTO `facture` VALUES (1,3000,500,NULL,'2019-02-20',1),(2,3000,500,NULL,'2019-02-21',4),(3,1500,150,'2020-07-21','2018-02-21',5),(4,60,60,NULL,'2017-03-24',2),(5,120,32,'2020-07-21','2019-03-24',3),(6,30,30,'2019-03-24','2019-03-24',7),(7,2900,0,NULL,'2020-08-03',9),(8,250,125,'2020-11-21','2020-01-03',8),(9,3000,50,NULL,'2019-12-06',6),(10,1200,200,'2020-07-22',NULL,10),(11,4500,1200,'2022-05-01','2020-01-20',13),(12,2900,1600,'2019-12-25','2017-04-21',12),(13,120,120,'2018-02-22','2018-02-22',14);
/*!40000 ALTER TABLE `facture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(40) NOT NULL,
  `prenom` varchar(40) NOT NULL,
  `numeroTel` char(8) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'ahmed','kammoun','23569814','ahmedKam@gmail.com'),(2,'nouha','slimen','55698743','nouha122@outlook.com'),(3,'samir','attia','98563214','samir.Atti@yahoo.fr'),(4,'doua','kadri','54782266','douaKadri@gmail.com'),(5,'saida','ben selman','26984137','saida.bensel@gmail.com'),(6,'naima','hamdi','22236222','naima8hamdi@yahoo.fr'),(7,'souha','ben abdellah','55100369','souh22abd@yahoo.fr'),(8,'nidhal','jbeli','91002003','nidhalJbeli1963@gmail.com'),(9,'mourad','saleh','58143628','mourad5858@gmail.coom'),(10,'nouha','mehdi','24258963','nouha@gmail.com'),(12,'doua','kadri','23000814','doudou@gmail.com'),(13,'doua','kadri','55666743','douaPro@outlook.com'),(14,'samir','attia','98003004','samirING@yahoo.fr'),(16,'houssem','khaled','32114225','houssem321@gmail.com'),(17,'f','f','f','f'),(18,'jo','jo','54','jisd');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seance`
--

DROP TABLE IF EXISTS `seance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seance` (
  `temps` datetime NOT NULL,
  `ordonance` text,
  `payement` smallint unsigned DEFAULT NULL,
  `patient_consulte` int unsigned NOT NULL,
  PRIMARY KEY (`temps`),
  KEY `fk_client_id` (`patient_consulte`),
  CONSTRAINT `fk_client_id` FOREIGN KEY (`patient_consulte`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seance`
--

LOCK TABLES `seance` WRITE;
/*!40000 ALTER TABLE `seance` DISABLE KEYS */;
INSERT INTO `seance` VALUES ('2017-03-24 08:45:00','VENTOLINE ',60,2),('2018-02-21 17:20:00',NULL,150,5),('2019-02-20 13:45:00','EFFERALGAN ',200,1),('2019-02-21 08:30:00','DAFALGAN , IMODIUM ,SPEDIFEN ',250,4),('2019-03-24 10:00:00',NULL,30,7),('2019-03-26 10:00:00','VENTOLINE ',31,3),('2019-06-19 16:00:00',NULL,120,1),('2019-06-20 18:00:00',NULL,50,4),('2019-08-19 09:30:00',NULL,130,1),('2019-12-06 14:30:00',NULL,50,6),('2020-01-03 16:00:00','DOLIPRANE',125,8),('2020-08-03 18:45:00','DOLIPRANE',0,9);
/*!40000 ALTER TABLE `seance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-28 19:46:10
