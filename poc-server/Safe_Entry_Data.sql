-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: safe_entry
-- ------------------------------------------------------
-- Server version	5.7.32-log

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
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card` (
  `id` varchar(256) NOT NULL,
  `type` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES ('0490E87A733280','CEPAS'),('0A46B7D3','Member'),('0D80BAD3','Member'),('1.11174E+15','CEPAS'),('1.11175E+15','CEPAS'),('1.1117E+15','CEPAS'),('1747281E','Int'),('1A84B7D3','Member'),('1BA4C421','Member'),('1DEEEED3','Member'),('297B8C9C','Member'),('29966581','Member'),('29A26A82','Member'),('2A47281E','Member'),('2AF89F08','Member'),('3980EEB2','Member'),('39948781','Member'),('39C3A781','Int'),('3D35C6D3','Member'),('3D40C5D3','Member'),('3D80EED3','Member'),('47108336','Member'),('4A53AE08','Member'),('4A85B7D3','Backup'),('4D319DD3','Member'),('5200281E','Member'),('59407D9D','Member'),('59526782','Int'),('596EA681','Member'),('59C4AB82','Int'),('5A78B7D3','Member'),('5A81B7D3','Member'),('5D01EFD3','Member'),('5D2D9DD3','Member'),('5D339DD3','Member'),('5DA6BED3','Member'),('624B281E','Member'),('6449281E','Member'),('645F281E','Member'),('6D2D9DD3','Member'),('6DA9B2D3','Member'),('792EC683','Member'),('7D97CDD3','Member'),('7DE9CDD3','Member'),('893A849C','Member'),('8A55B7D3','Member'),('8ADFB7D3','Member'),('8B80281E','Member'),('991CB881','Int'),('996A7D9C','Int'),('9D6CEED3','Member'),('9D888FD3','Member'),('9DEDEED3','Member'),('A860281E','Member'),('A9FBBA81','Member'),('AA55B7D3','Member'),('AA58B7D3','member'),('AD11EDD3','Int'),('AD6DA3D3','Member'),('AD858FD3','Member'),('B9FB869D','Member'),('BA52B7D3','member'),('BA55B7D3','Member'),('BA58B7D3','Member'),('BA5D7507','Member'),('BA71B208','Member'),('BAD6B7D3','Int'),('BC5D281E','Member'),('BD35C6D3','Member'),('CA75B108','Member'),('CACD6407','Member'),('CD70A3D3','Member'),('D71B7E36','Member'),('DA45B7D3','Member'),('E9298F9C','Int'),('ED349DD3','Member'),('ED369DD3','Int'),('ED4ABCD3','Member'),('F75A8536','Member'),('F9278E9D','Member');
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `id` varchar(256) NOT NULL,
  `location` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES ('1','HCMC'),('2','HN');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_log`
--

DROP TABLE IF EXISTS `device_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(256) NOT NULL,
  `device_id` varchar(256) NOT NULL,
  `card_type` varchar(256) DEFAULT NULL,
  `temperature` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `constraint_usertimestamp` (`user_id`,`timestamp`),
  KEY `device_id` (`device_id`),
  CONSTRAINT `device_log_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`),
  CONSTRAINT `device_log_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_log`
--

LOCK TABLES `device_log` WRITE;
/*!40000 ALTER TABLE `device_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_config`
--

DROP TABLE IF EXISTS `email_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(256) DEFAULT NULL,
  `temperature_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `temperature_id` (`temperature_id`),
  CONSTRAINT `email_config_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `email_config_ibfk_2` FOREIGN KEY (`temperature_id`) REFERENCES `temperature` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_config`
--

LOCK TABLES `email_config` WRITE;
/*!40000 ALTER TABLE `email_config` DISABLE KEYS */;
INSERT INTO `email_config` VALUES (2,'0490E87A733280',1),(3,'stylis2027',1);
/*!40000 ALTER TABLE `email_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'USER'),(2,'ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temperature`
--

DROP TABLE IF EXISTS `temperature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temperature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temperature` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temperature`
--

LOCK TABLES `temperature` WRITE;
/*!40000 ALTER TABLE `temperature` DISABLE KEYS */;
INSERT INTO `temperature` VALUES (1,37);
/*!40000 ALTER TABLE `temperature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(256) NOT NULL,
  `username` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0490E87A733280',NULL,'Terry','0490E87A733280@molisys.com',NULL),('Backup',NULL,'Jennifer','Backup@molisys.com',NULL),('styl',NULL,'NGUYEN PHUOC CAM DUY','styl@molisys.com',NULL),('STYL170602',NULL,'QUACH TRAN THIEN HUYNH','STYL170602@molisys.com',NULL),('STYL170712',NULL,'TRAN THI HANG NGA','STYL170712@molisys.com',NULL),('stylis2016',NULL,'BUI VAN BINH','stylis2016@molisys.com',NULL),('stylis2018',NULL,'NGUYEN VAN HUYNH Y','stylis2018@molisys.com',NULL),('stylis2021',NULL,'DOAN THI NHU NGUYET','stylis2021@molisys.com',NULL),('stylis2022',NULL,'DUONG QUANG TUAN','stylis2022@molisys.com',NULL),('stylis2023',NULL,'CHAU THI NGUYET','stylis2023@molisys.com',NULL),('stylis2025',NULL,'NGUYEN TRUONG GIANG','stylis2025@molisys.com',NULL),('stylis2026',NULL,'NGUYEN THI ANH','stylis2026@molisys.com',NULL),('stylis2027',NULL,'LAM THANH PHAT','phatlambl@gmail.com',NULL),('stylis2028',NULL,'TRAN QUOC TUAN','stylis2028@molisys.com',NULL),('stylis2029',NULL,'NGUYEN THI THU VIET','stylis2029@molisys.com',NULL),('stylis2030',NULL,'TRAN HOANG THONG','stylis2030@molisys.com',NULL),('stylsg001',NULL,'Hong Kiat Ong','stylsg001@molisys.com',NULL),('stylsg003',NULL,'CS Koh','stylsg003@molisys.com',NULL),('stylvn001',NULL,'NGO MINH QUAN','stylvn001@molisys.com',NULL),('stylvn002',NULL,'DANG DOAN PHUONG VY','stylvn002@molisys.com',NULL),('stylvn004',NULL,'LAM QUOC THANH','stylvn004@molisys.com',NULL),('stylvn005',NULL,'NGUYEN VAN DONG','stylvn005@molisys.com',NULL),('stylvn006',NULL,'NGUYEN THI BICH','stylvn006@molisys.com',NULL),('stylvn007',NULL,'TRAN LE NGOC PHUOC','stylvn007@molisys.com',NULL),('stylvn008',NULL,'PHAM TIEN THANH','stylvn008@molisys.com',NULL),('stylvn010',NULL,'DOAN VU HAI','stylvn010@molisys.com',NULL),('stylvn012',NULL,'NGUYEN VIET DANG','stylvn012@molisys.com',NULL),('stylvn015',NULL,'TRAN TAN TIEN','stylvn015@molisys.com',NULL),('stylvn016',NULL,'NGUYEN HUU CUONG','stylvn016@molisys.com',NULL),('stylvn018',NULL,'NGUYEN TRUNG TIN','stylvn018@molisys.com',NULL),('stylvn020',NULL,'PHAM DUC DOAN TRANG','stylvn020@molisys.com',NULL),('stylvn021',NULL,'DUONG HUYNH NGHIA','stylvn021@molisys.com',NULL),('stylvn022',NULL,'NGUYEN TRONG THINH','stylvn022@molisys.com',NULL),('stylvn027',NULL,'LE HOANG DUY','stylvn027@molisys.com',NULL),('stylvn029',NULL,'Pham Thanh Tuyen','stylvn029@molisys.com',NULL),('stylvn035',NULL,'LAM VINH THAI','stylvn035@molisys.com',NULL),('stylvn036',NULL,'DO MINH PHUOC','stylvn036@molisys.com',NULL),('stylvn039',NULL,'HO NGUYEN TRAM THANH','stylvn039@molisys.com',NULL),('stylvn041',NULL,'LE THI MY NHUNG','stylvn041@molisys.com',NULL),('stylvn042',NULL,'NGUYEN HOANG AN','stylvn042@molisys.com',NULL),('stylvn043',NULL,'NGUYEN VAN TRUNG','stylvn043@molisys.com',NULL),('stylvn046',NULL,'LE VU TRIET','stylvn046@molisys.com',NULL),('stylvn048',NULL,'NGUYEN TRUNG QUOC','stylvn048@molisys.com',NULL),('stylvn049',NULL,'NGUYEN THAI MINH SON','stylvn049@molisys.com',NULL),('stylvn050',NULL,'NGO THI THANH TRANG','stylvn050@molisys.com',NULL),('stylvn051',NULL,'NGUYEN THI BAO NGAN','stylvn051@molisys.com',NULL),('stylvn052',NULL,'NGUYEN TRUNG THAI','stylvn052@molisys.com',NULL),('stylvn057',NULL,'NGUYEN THI KIM THU','stylvn057@molisys.com',NULL),('stylvn059',NULL,'DO VAN THO','stylvn059@molisys.com',NULL),('stylvn062',NULL,'NGUYEN THI THANH HANG','stylvn062@molisys.com',NULL),('stylvn064',NULL,'LE TAN THUAN','stylvn064@molisys.com',NULL),('stylvn071',NULL,'LE TRAN KHANH HUY','stylvn071@molisys.com',NULL),('stylvn072',NULL,'DUONG DUY TRONG','stylvn072@molisys.com',NULL),('stylvn073',NULL,'TRUONG DAI HAI RUNG','stylvn073@molisys.com',NULL),('stylvn074',NULL,'NGUYEN TUAN DAT','stylvn074@molisys.com',NULL),('stylvn076',NULL,'NGUYEN NGOC HAI AU','stylvn076@molisys.com',NULL),('stylvn077',NULL,'LE XUAN CONG','stylvn077@molisys.com',NULL),('stylvn081',NULL,'TRAN THI HOANH','stylvn081@molisys.com',NULL),('stylvn082',NULL,'TRINH THI KIM TUYEN','stylvn082@molisys.com',NULL),('stylvn083',NULL,'PHAM BA NGOC','stylvn083@molisys.com',NULL),('stylvn084',NULL,'LE PHUONG NAM','stylvn084@molisys.com',NULL),('stylvn085',NULL,'HOANG VAN LOC','stylvn085@molisys.com',NULL),('stylvn086',NULL,'DINH ZA HUAN','stylvn086@molisys.com',NULL),('stylvn087',NULL,'NGUYEN THU HANG','stylvn087@molisys.com',NULL),('stylvn089',NULL,'LUU DUC HUY','stylvn089@molisys.com',NULL),('stylvn090',NULL,'TRAN MAI THAO NHI','stylvn090@molisys.com',NULL),('stylvn091',NULL,'TA NHUT MINH','stylvn091@molisys.com',NULL),('stylvn093',NULL,'HUYNH DUY NHAT QUANG','stylvn093@molisys.com',NULL),('stylvn095',NULL,'NGUYEN THANH BINH','stylvn095@molisys.com',NULL),('stylvn096',NULL,'LUONG PHAT THINH','stylvn096@molisys.com',NULL),('stylvn097',NULL,'HOANG MINH TUAN','stylvn097@molisys.com',NULL),('stylvn098',NULL,'NGUYEN VIET CUONG','stylvn098@molisys.com',NULL),('stylvn099',NULL,'VU HOANG HY','stylvn099@molisys.com',NULL),('stylvn100',NULL,'TRAN XUAN TIEN DAT','stylvn100@molisys.com',NULL),('stylvn101',NULL,'DO HONG QUANG','stylvn101@molisys.com',NULL),('stylvn102',NULL,'NGUYEN PHUONG TRINH','stylvn102@molisys.com',NULL),('stylvn103',NULL,'TRAN CONG DU','stylvn103@molisys.com',NULL),('stylvnG01',NULL,'Guest01','stylvnG01@molisys.com',NULL),('stylvnG02',NULL,'Guest02','stylvnG02@molisys.com',NULL),('stylvnG03',NULL,'Guest03','stylvnG03@molisys.com',NULL),('stylvnG04',NULL,'Guest04','stylvnG04@molisys.com',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_card`
--

DROP TABLE IF EXISTS `user_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(256) DEFAULT NULL,
  `card_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `card_id` (`card_id`),
  CONSTRAINT `user_card_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_card_ibfk_2` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_card`
--

LOCK TABLES `user_card` WRITE;
/*!40000 ALTER TABLE `user_card` DISABLE KEYS */;
INSERT INTO `user_card` VALUES (1,'0490E87A733280','0490E87A733280'),(2,'stylvn086','0A46B7D3'),(3,'stylvn015','0D80BAD3'),(4,'stylvn005','1.1117E+15'),(5,'stylsg001','1.11174E+15'),(6,'stylvn004','1.11174E+15'),(7,'stylsg003','1.11174E+15'),(8,'0490E87A733280','1.11175E+15'),(9,'stylis2030','1747281E'),(10,'stylvn029','1A84B7D3'),(11,'stylvn016','1BA4C421'),(12,'stylvn001','1DEEEED3'),(13,'stylvn012','297B8C9C'),(14,'stylvn087','29966581'),(15,'stylvn097','29A26A82'),(16,'stylvn057','2A47281E'),(17,'stylvn091','2AF89F08'),(18,'styl','3980EEB2'),(19,'stylvn096','39948781'),(20,'stylis2027','39C3A781'),(21,'stylvn072','3D35C6D3'),(22,'stylvn007','3D40C5D3'),(23,'stylvn002','3D80EED3'),(24,'STYL170602','47108336'),(25,'stylvn093','4A53AE08'),(26,'Backup','4A85B7D3'),(27,'stylvn084','4D319DD3'),(28,'stylvn036','5200281E'),(29,'stylvn101','59407D9D'),(30,'stylis2025','59526782'),(31,'stylvn100','596EA681'),(32,'stylis2021','59C4AB82'),(33,'stylvn073','5A78B7D3'),(34,'stylvn098','5A81B7D3'),(35,'stylvn022','5D01EFD3'),(36,'stylvn085','5D2D9DD3'),(37,'stylvn039','5D339DD3'),(38,'stylvn064','5DA6BED3'),(39,'stylvn020','624B281E'),(40,'stylvn027','6449281E'),(41,'stylvn008','645F281E'),(42,'stylvn043','6D2D9DD3'),(43,'STYL170712','6DA9B2D3'),(44,'stylvn083','792EC683'),(45,'stylvn081','7D97CDD3'),(46,'stylvnG04','7DE9CDD3'),(47,'stylvn048','893A849C'),(48,'stylvn042','8A55B7D3'),(49,'stylvnG03','8ADFB7D3'),(50,'stylvn006','8B80281E'),(51,'stylis2022','991CB881'),(52,'stylis2023','996A7D9C'),(53,'stylvn021','9D6CEED3'),(54,'stylvnG01','9D888FD3'),(55,'stylvn052','9DEDEED3'),(56,'stylvn089','A860281E'),(57,'stylvn099','A9FBBA81'),(58,'stylvn050','AA55B7D3'),(59,'stylvn062','AA58B7D3'),(60,'stylis2029','AD11EDD3'),(61,'stylvn077','AD6DA3D3'),(62,'stylvn076','AD858FD3'),(63,'stylvn102','B9FB869D'),(64,'stylvn059','BA52B7D3'),(65,'stylvn041','BA55B7D3'),(66,'stylvn049','BA58B7D3'),(67,'stylvn090','BA5D7507'),(68,'stylvn046','BA71B208'),(69,'stylis2028','BAD6B7D3'),(70,'stylvn018','BC5D281E'),(71,'stylvn082','BD35C6D3'),(72,'stylis2016','CA75B108'),(73,'stylvn095','CACD6407'),(74,'stylvnG02','CD70A3D3'),(75,'stylvn071','D71B7E36'),(76,'stylvn074','DA45B7D3'),(77,'stylis2026','E9298F9C'),(78,'stylvn051','ED349DD3'),(79,'stylis2018','ED369DD3'),(80,'stylvn035','ED4ABCD3'),(81,'stylvn010','F75A8536'),(82,'stylvn103','F9278E9D');
/*!40000 ALTER TABLE `user_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(256) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-20 17:30:08
