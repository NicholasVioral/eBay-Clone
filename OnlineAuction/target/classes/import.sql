-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: sellingwidgets
-- ------------------------------------------------------
-- Server version	8.0.36

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

SET FOREIGN_KEY_CHECKS = 0;

--
-- Table structure for table `applicant`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applicant` (
  `id` bigint NOT NULL,
  `answer1` varchar(255) DEFAULT NULL,
  `answer2` varchar(255) DEFAULT NULL,
  `answer3` varchar(255) DEFAULT NULL,
  `answer4` varchar(255) DEFAULT NULL,
  `application_date` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `reviewed` bit(1) DEFAULT NULL,
  `role_appliedfor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicant`
--

LOCK TABLES `applicant` WRITE;
/*!40000 ALTER TABLE `applicant` DISABLE KEYS */;
/*!40000 ALTER TABLE `applicant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicant_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applicant_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicant_seq`
--

LOCK TABLES `applicant_seq` WRITE;
/*!40000 ALTER TABLE `applicant_seq` DISABLE KEYS */;
INSERT INTO `applicant_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `applicant_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attribute_key` varchar(255) DEFAULT NULL,
  `data_type` enum('BOOLEAN','CONDITION','DATE','DECIMAL','INTEGER','KEYWORDS','NUMBER','STRING','TEXTAREA','WEIGHT','YEAR') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,'Year','YEAR'),(2,'Condition','CONDITION'),(3,'Make','STRING'),(4,'Model','STRING'),(5,'Keywords','KEYWORDS'),(6,'Submodel','STRING'),(7,'Engine','STRING');
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_recommendation`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_recommendation` (
  `id` bigint NOT NULL,
  `recommendation` int DEFAULT NULL,
  `attribute_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7enq5wex49hteaaux6yqpt0fk` (`attribute_id`),
  KEY `FKcsyl7clkabs6bj2op3ip3t16x` (`category_id`),
  CONSTRAINT `FK7enq5wex49hteaaux6yqpt0fk` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`),
  CONSTRAINT `FKcsyl7clkabs6bj2op3ip3t16x` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_recommendation`
--

LOCK TABLES `attribute_recommendation` WRITE;
/*!40000 ALTER TABLE `attribute_recommendation` DISABLE KEYS */;
INSERT INTO `attribute_recommendation` VALUES (1,2,1,1),(2,2,2,1),(3,2,3,1),(4,2,4,1),(5,1,5,1),(6,1,6,1),(7,1,7,1);
/*!40000 ALTER TABLE `attribute_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_recommendation_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_recommendation_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_recommendation_seq`
--

LOCK TABLES `attribute_recommendation_seq` WRITE;
/*!40000 ALTER TABLE `attribute_recommendation_seq` DISABLE KEYS */;
INSERT INTO `attribute_recommendation_seq` VALUES (1),(101);
/*!40000 ALTER TABLE `attribute_recommendation_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_suggestion`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_suggestion` (
  `id` bigint NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `attribute_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK35umxprpadvp9gkdfhdu2kk84` (`attribute_id`),
  CONSTRAINT `FK35umxprpadvp9gkdfhdu2kk84` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_suggestion`
--

LOCK TABLES `attribute_suggestion` WRITE;
/*!40000 ALTER TABLE `attribute_suggestion` DISABLE KEYS */;
INSERT INTO `attribute_suggestion` VALUES (1,'1951',1),(2,'Buick',3),(3,'Roadmaster',4),(4,'Base',6),(5,'8 Cyl 5.2L',7),(6,'Special',4),(7,'8 Cyl 4.3L',7),(8,'Super',4),(9,'Cadillac',3),(10,'Commercial Chassis',4),(11,'8 Cyl 5.4L',7),(12,'Series 60 Fleetwood',4),(13,'Series 61',4),(14,'Series 62',4),(15,'DeVille',6),(16,'Series 75 Fleetwood',4),(17,'Chevrolet',3),(18,'Bel Air',4),(19,'6 Cyl 3.5L',7),(20,'6 Cyl 3.8L',7),(21,'6 Cyl 3.9L',7),(22,'Fleetline',4),(23,'Deluxe',6),(24,'Fleetline Truck',4),(25,'Sedan Delivery',4),(26,'Styleline Deluxe',4),(27,'Styleline Special',4),(28,'Suburban',4),(29,'Truck',4),(30,'3100',6),(31,'3600',6),(32,'3700',6),(33,'3800',6),(34,'3900',6),(35,'Chrysler',3),(36,'Imperial',4),(37,'Crown',6),(38,'New Yorker',4),(39,'Newport',4),(40,'6 Cyl 4.1L',7),(41,'Saratoga',4),(42,'Town & Country',4),(43,'Windsor',4),(44,'Daimler',3),(45,'DB18',4),(46,'Consort',6),(47,'6 Cyl 2.5L',7),(48,'DE27',4),(49,'DE36',4),(50,'8 Cyl 5.5L',7),(51,'Special Sports',4),(52,'Dodge',3),(53,'B-3',4),(54,'B',6),(55,'6 Cyl 3.6L',7),(56,'C',6),(57,'D',6),(58,'FL',6),(59,'Power Wagon',6),(60,'B-3 Van',4),(61,'DU',6),(62,'DUF',6),(63,'DUL',6),(64,'DULF',6),(65,'EU',6),(66,'EUL',6),(67,'EULF',6),(68,'Coronet',4),(69,'Meadowbrook',4),(70,'6 Cyl 3.7L',7),(71,'Wayfarer',4),(72,'Fiat',3),(73,'1100',4),(74,'4 Cyl 1.1L',7),(75,'1400',4),(76,'4 Cyl 1.4L',7),(77,'500',4),(78,'4 Cyl 0.6L',7),(79,'Ford',3),(80,'Anglia',4),(81,'4 Cyl 1.2L',7),(82,'Consul',4),(83,'4 Cyl 1.5L',7),(84,'Country Sedan',4),(85,'8 Cyl 3.9L',7),(86,'Country Squire',4),(87,'Crestline',4),(88,'Custom',4),(89,'F1',4),(90,'F2',4),(91,'F3',4),(92,'F4',4),(93,'Prefect',4),(94,'Ranch Wagon',4),(95,'Taunus',4),(96,'Type 48',6),(97,'Victoria',4),(98,'GMC',3),(99,'100',4),(100,'100-22',4),(101,'150',4),(102,'150-22',4),(103,'250',4),(104,'250-22',4),(105,'280-22',4),(106,'300',4),(107,'350-24',4),(108,'350-27',4),(109,'370',4),(110,'F350-24',4),(111,'F350-27',4),(112,'F370',4),(113,'FC100',4),(114,'FC150',4),(115,'M350-24',4),(116,'M350-27',4),(117,'P150-22',4),(118,'P152',4),(119,'P252',4),(120,'P253',4),(121,'S300-24',4),(122,'S370',4),(123,'International',3),(124,'L110',4),(125,'L111',4),(126,'L112',4),(127,'L120',4),(128,'L121',4),(129,'L122',4),(130,'L130',4),(131,'L150',4),(132,'L151',4),(133,'L153',4),(134,'LB140',4),(135,'Metro',6),(136,'LM120',4),(137,'LM121',4),(138,'LM122',4),(139,'LM150',4),(140,'LM153',4),(141,'Jaguar',3),(142,'C-Type',4),(143,'6 Cyl 3.4L',7),(144,'Mark VII',4),(145,'XK120',4),(146,'Jeep',3),(147,'Willys',4),(148,'1-Ton',6),(149,'4 Cyl 2.2L',7),(150,'473',6),(151,'CJ-3',6),(152,'CJ-3 Chassis',6),(153,'FJ',6),(154,'Jeepster',6),(155,'6 Cyl 2.6L',7),(156,'6 Cyl 2.4L',7),(157,'Land Rover',3),(158,'4 Cyl 1.6L',7),(159,'Lincoln',3),(160,'Cosmopolitan',4),(161,'MG',3),(162,'TD',4),(163,'4 Cyl 1.3L',7),(164,'TDC',4),(165,'YB',4),(166,'Mercedes Benz',3),(167,'170',4),(168,'4 Cyl 1.8L',7),(169,'170 D',4),(170,'180',4),(171,'219',4),(172,'6 Cyl 2.2L',7),(173,'220',4),(174,'220Sb',4),(175,'220b',4),(176,'6 Cyl 3.0L',7),(177,'300S',4),(178,'Mercury',3),(179,'8 Cyl 4.2L',7),(180,'Monterey',4),(181,'Oldsmobile',3),(182,'98',4),(183,'8 Cyl 5.0L',7),(184,'Deluxe 88',4),(185,'Standard 88',4),(186,'Super 88',4),(187,'Plymouth',3),(188,'Cambridge',4),(189,'Concord',4),(190,'Special Savoy',6),(191,'Cranbrook',4),(192,'Pontiac',3),(193,'Catalina',4),(194,'8 Cyl 4.4L',7),(195,'Chieftain',4),(196,'Super Deluxe',6),(197,'Silver Streak',4),(198,'Streamliner',4),(199,'Torpedo',4),(200,'Porsche',3),(201,'356',4),(202,'Rover',3),(203,'75',4),(204,'6 Cyl 2.1L',7),(205,'Saab',3),(206,'92',4),(207,'2 Cyl 0.8L',7),(208,'Volkswagen',3),(209,'Beetle',4),(210,'Transporter',4),(211,'Volvo',3),(212,'444',4),(213,'1952',1),(214,'BMW',3),(215,'501',4),(216,'6 Cyl 2.0L',7),(217,'6 Cyl 4.3L',7),(218,'Empress',4),(219,'MK II',6),(220,'Regency',4),(221,'Courier Sedan Delivery',4),(222,'Customline',4),(223,'Mainline',4),(224,'P-350',4),(225,'Sunliner',4),(226,'12M',6),(227,'Zephyr',4),(228,'6 Cyl 2.3L',7),(229,'102-22',4),(230,'253-22',4),(231,'FC100-22',4),(232,'PM150-22',4),(233,'PM150-24',4),(234,'PM152',4),(235,'PM152-22',4),(236,'PM252',4),(237,'PM253',4),(238,'LM151',4),(239,'M',6),(240,'4 Cyl 2.0L',7),(241,'Capri',4),(242,'1953',1),(243,'8 Cyl 5.3L',7),(244,'Eldorado',4),(245,'Corvette',4),(246,'One-Fifty Series',4),(247,'Two-Ten Series',4),(248,'Conquest',4),(249,'B-4',4),(250,'B-4 Truck',4),(251,'8 Cyl 4.0L',7),(252,'TV',6),(253,'1900',4),(254,'4 Cyl 1.9L',7),(255,'600',4),(256,'8V',4),(257,'8 Cyl 2.0L',7),(258,'Club',4),(259,'8 Cyl 3.7L',7),(260,'F-100',4),(261,'F-250',4),(262,'F-350',4),(263,'100-24',4),(264,'150-24',4),(265,'250-24',4),(266,'300-24',4),(267,'F350',4),(268,'PM150',4),(269,'R100',4),(270,'R102',4),(271,'R110',4),(272,'R111',4),(273,'R112',4),(274,'R120',4),(275,'R121',4),(276,'R122',4),(277,'R130',4),(278,'R131',4),(279,'R132',4),(280,'RA120',4),(281,'RA121',4),(282,'RA122',4),(283,'RM120',4),(284,'RM121',4),(285,'RM122',4),(286,'S132',4),(287,'Fiesta',4),(288,'Belvedere',4),(289,'Savoy',4),(290,'92B',4),(291,'1954',1),(292,'501A',4),(293,'501B',4),(294,'502',4),(295,'8 Cyl 2.6L',7),(296,'Century',4),(297,'Skylark',4),(298,'1500',4),(299,'Del Ray',6),(300,'Roadster',6),(301,'C-1',4),(302,'DU6',6),(303,'DUL6',6),(304,'EUL6',6),(305,'C-1 Truck',4),(306,'C-3',4),(307,'Royal',4),(308,'Sierra',4),(309,'Skyliner',4),(310,'M300',4),(311,'PM250-24',4),(312,'D-Type',4),(313,'TF',4),(314,'ZA',4),(315,'170S-V',4),(316,'220A',4),(317,'220S',4),(318,'300B',4),(319,'300SL',4),(320,'Sun Valley',6),(321,'88',4),(322,'Starfire',4),(323,'Holiday',6),(324,'Plaza',4),(325,'Star Chief',4),(326,'60',4),(327,'90',4),(328,'1955',1),(329,'8 Cyl 3.2L',7),(330,'Isetta',4),(331,'1 Cyl 0.2L',7),(332,'Beauville',6),(333,'Nomad',6),(334,'3200',6),(335,'3400',6),(336,'3500',6),(337,'Nassau',4),(338,'8 Cyl 4.9L',7),(339,'St Regis',4),(340,'Regina',4),(341,'6 Cyl 4.6L',7),(342,'Sportsman',4),(343,'Lancer',4),(344,'8 Cyl 5.8L',7),(345,'8 Cyl 4.8L',7),(346,'Fairlane',4),(347,'15M',6),(348,'Thunderbird',4),(349,'Zodiac',4),(350,'8 Cyl 4.7L',7),(351,'350-8',4),(352,'FM340',4),(353,'FM350',4),(354,'M340',4),(355,'PM151',4),(356,'PM153',4),(357,'PM250',4),(358,'S300',4),(359,'2',4),(360,'XK140',4),(361,'MC',6),(362,'CJ-5',6),(363,'CJ-6',6),(364,'Utility Wagon',6),(365,'8 Cyl 5.6L',7),(366,'180D',4),(367,'190',4),(368,'190SL',4),(369,'Montclair',4),(370,'Safari',6),(371,'Laurentian',4),(372,'Pathfinder',4),(373,'356A',4),(374,'Karmann Ghia',4),(375,'1956',1),(376,'503',4),(377,'507',4),(378,'1 Cyl 0.3L',7),(379,'8 Cyl 6.0L',7),(380,'104',4),(381,'4.5-Liter',4),(382,'DK400',4),(383,'C-4',4),(384,'8 Cyl 5.1L',7),(385,'Texan',4),(386,'MK I',6),(387,'4 Cyl 1.7L',7),(388,'Escort',4),(389,'Park Lane Wagon',4),(390,'Squire',4),(391,'6 Cyl 4.4L',7),(392,'PM251',4),(393,'CO180',4),(394,'6 Cyl 5.0L',7),(395,'S100',4),(396,'S102',4),(397,'S110',4),(398,'S112',4),(399,'S120',4),(400,'S122',4),(401,'S130',4),(402,'SA120',4),(403,'SA122',4),(404,'SM120',4),(405,'SM122',4),(406,'SM130',4),(407,'SM132',4),(408,'475',4),(409,'6-226',4),(410,'DJ-3',6),(411,'Mark II',4),(412,'Premier',4),(413,'MGA',4),(414,'180A',4),(415,'300C',4),(416,'300SC',4),(417,'Medalist',4),(418,'8 Cyl 4.5L',7),(419,'Fury',4),(420,'Sport',6),(421,'Carrera',4),(422,'93',4),(423,'3 Cyl 0.7L',7),(424,'445',4),(425,'1957',1),(426,'Brougham',6),(427,'8 Cyl 4.6L',7),(428,'8 Cyl 6.4L',7),(429,'D100 Pickup',4),(430,'D200 Pickup',4),(431,'D300 Pickup',4),(432,'P300 Series',4),(433,'P400 Series',4),(434,'W100 Pickup',4),(435,'W200 Pickup',4),(436,'W300 Pickup',4),(437,'2 Cyl 0.5L',7),(438,'Custom 300',4),(439,'Del Rio Wagon',4),(440,'P-100',4),(441,'Ranchero',4),(442,'8 Cyl 5.7L',7),(443,'8 Cyl 6.1L',7),(444,'A100 Truck',4),(445,'A102',4),(446,'A110',4),(447,'A112',4),(448,'A120',4),(449,'A122',4),(450,'A130',4),(451,'A132',4),(452,'AB120',4),(453,'Metroette',6),(454,'AM120',4),(455,'AM130',4),(456,'AM132',4),(457,'AM80',4),(458,'Metro-Mite',6),(459,'3',4),(460,'Mark VIII',4),(461,'XKSS',4),(462,'F-134',4),(463,'F4-134',4),(464,'FC170',4),(465,'4 Cyl 2.1L',7),(466,'4 Cyl 2.3L',7),(467,'ZB',4),(468,'Colony Park',4),(469,'Commuter',4),(470,'Turnpike Cruiser',4),(471,'Voyager',4),(472,'Golden Rocket 88',4),(473,'Bonneville',4),(474,'Custom Safari',6),(475,'Custom Safari Transcontinental',6),(476,'Super Chief',4),(477,'105',4),(478,'1958',1),(479,'2 Cyl 0.6L',7),(480,'Limited',4),(481,'Series 70 Fleetwood Eldorado',4),(482,'Biscayne',4),(483,'Brookwood',4),(484,'Impala',4),(485,'Yeoman',4),(486,'LeBaron',6),(487,'8 Cyl 5.9L',7),(488,'P310 Series',4),(489,'P320 Series',4),(490,'P410 Series',4),(491,'P420 Series',4),(492,'Wm300 Power Wagon',4),(493,'1200',4),(494,'17M',6),(495,'XK150',4),(496,'S',6),(497,'FA-134',4),(498,'8 Cyl 7.0L',7),(499,'Continental',4),(500,'190DB',4),(501,'220SE',4),(502,'300D',4),(503,'8 Cyl 6.3L',7),(504,'Park Lane',4),(505,'Nissan',3),(506,'1000',4),(507,'4 Cyl 1.0L',7),(508,'Strato-Chief',4),(509,'93B',4),(510,'GT750',4),(511,'Toyota',3),(512,'544',4),(513,'1959',1),(514,'700',6),(515,'2 Cyl 0.7L',7),(516,'Electra',4),(517,'225',6),(518,'8 Cyl 6.6L',7),(519,'Estate Wagon',4),(520,'Invicta',4),(521,'LeSabre',4),(522,'3B',4),(523,'3C',4),(524,'3D',4),(525,'3E',4),(526,'3F',4),(527,'3G',4),(528,'El Camino',4),(529,'Kingswood',4),(530,'Parkwood',4),(531,'8 Cyl 6.7L',7),(532,'8 Cyl 6.8L',7),(533,'P450 Series',4),(534,'Galaxie',4),(535,'B100',4),(536,'B102',4),(537,'B110',4),(538,'B112',4),(539,'B120',4),(540,'B122',4),(541,'B130',4),(542,'B132',4),(543,'Mark IX',4),(544,'CJ3',4),(545,'CJ5',4),(546,'CJ6',4),(547,'DJ3',4),(548,'Twin Cam',6),(549,'Magnette',4),(550,'180B',4),(551,'190B',4),(552,'Country Cruiser',4),(553,'Bluebird',4),(554,'8 Cyl 6.5L',7),(555,'Parisienne',4),(556,'3-Litre',4),(557,'80',4),(558,'95',4),(559,'3 Cyl 0.8L',7),(560,'122',4),(561,'1960',1),(562,'Fleetmaster',6),(563,'C10 Panel',4),(564,'C10 Pickup',4),(565,'C20 Pickup',4),(566,'C30 Panel',4),(567,'C30 Pickup',4),(568,'C40',4),(569,'C50',4),(570,'C60',4),(571,'C70',4),(572,'C80',4),(573,'Corvair',4),(574,'Monza',6),(575,'K10 Pickup',4),(576,'K20 Pickup',4),(577,'P10 Series',4),(578,'P20 Series',4),(579,'P30 Series',4),(580,'SP250',4),(581,'8 Cyl 2.5L',7),(582,'D100 Series',4),(583,'D200 Series',4),(584,'D300 Series',4),(585,'Dart',4),(586,'6 Cyl 2.8L',7),(587,'Phoenix',6),(588,'Pioneer',6),(589,'Matador',4),(590,'P300 Van',4),(591,'Polara',4),(592,'Seneca',4),(593,'W100 Series',4),(594,'W200 Series',4),(595,'W300 Series',4),(596,'2100',4),(597,'Falcon',4),(598,'Starliner',4),(599,'12M Super',6),(600,'1000 Series',4),(601,'1500 Series',4),(602,'2500 Series',4),(603,'3000',4),(604,'PV1000',4),(605,'V3000 Forward Control',4),(606,'AB140',4),(607,'AM150',4),(608,'M151',4),(609,'Lincoln Series',4),(610,'Midget',4),(611,'4 Cyl 0.9L',7),(612,'180C',4),(613,'190C',4),(614,'190D',4),(615,'300SE',4),(616,'Comet',4),(617,'Fleet Special',4),(618,'Sport Wagon',4),(619,'Valiant',4),(620,'200',6),(621,'Ventura',4),(622,'356B',4),(623,'Super 90',6),(624,'93F',4),(625,'Land Cruiser',4),(626,'Tiara',4),(627,'1961',1),(628,'2600',4),(629,'700 Sport',6),(630,'8 Cyl 3.5L',7),(631,'Lakewood',6),(632,'Corvair Truck',4),(633,'770',6),(634,'P100 Van',4),(635,'P200',4),(636,'P200 Van',4),(637,'P300',4),(638,'125',4),(639,'Econoline',4),(640,'Falcon Sedan Delivery',4),(641,'Club Victoria',6),(642,'Town Victoria',6),(643,'PB1000 Series',4),(644,'C100',4),(645,'C102',4),(646,'C110',4),(647,'C112',4),(648,'C120',4),(649,'C122',4),(650,'C130',4),(651,'C132',4),(652,'M Series Van',4),(653,'4 Cyl 2.5L',7),(654,'Scout',4),(655,'Isuzu',3),(656,'Bellel',4),(657,'XKE',4),(658,'Dispatcher',4),(659,'FJ3A',4),(660,'Universal',4),(661,'Universal Truck',4),(662,'Utility',4),(663,'S-22',6),(664,'Meteor',4),(665,'320',4),(666,'Patrol',4),(667,'6 Cyl 4.0L',7),(668,'Classic 98',4),(669,'Cutlass',4),(670,'F85',4),(671,'8 Cyl 6.9L',7),(672,'Tempest',4),(673,'4 Cyl 3.2L',7),(674,'LeMans',6),(675,'96',4),(676,'96 GT',4),(677,'96 Sport',4),(678,'1800',4),(679,'1962',1),(680,'6 Cyl 3.2L',7),(681,'Wildcat',4),(682,'6 Cyl 7.8L',7),(683,'Chevy II',4),(684,'Nova',6),(685,'Monza Spyder',6),(686,'880',4),(687,'330',6),(688,'440',6),(689,'P100',4),(690,'600D',4),(691,'4 Cyl 0.8L',7),(692,'Club Wagon',4),(693,'8 Cyl 3.6L',7),(694,'Galaxie 500',4),(695,'XL',6),(696,'Station Bus',4),(697,'CM110',4),(698,'CM75',4),(699,'CM80',4),(700,'Mark X',4),(701,'6-230',4),(702,'Gladiator',4),(703,'Tornado',4),(704,'MGB',4),(705,'GT',6),(706,'S-33',6),(707,'Custom S-55',6),(708,'Villager',4),(709,'Jetfire',4),(710,'Signet',6),(711,'Acadian',4),(712,'Grand Prix',4),(713,'Shelby',3),(714,'Cobra',4),(715,'1963',1),(716,'700CS',6),(717,'Riviera',4),(718,'Biarritz',6),(719,'6 Cyl 4.8L',7),(720,'270',6),(721,'1100D',4),(722,'6 Cyl 3.3L',7),(723,'Cortina',4),(724,'Futura',6),(725,'Futura Sprint',6),(726,'4 Cyl 3.6L',7),(727,'Sprint',4),(728,'MK III',6),(729,'G1000 Series',4),(730,'PB1500 Series',4),(731,'PB2500 Series',4),(732,'C1000',4),(733,'C1100',4),(734,'C1200',4),(735,'C1300',4),(736,'FC170HD',4),(737,'J-100',4),(738,'J-200',4),(739,'J-210',4),(740,'J-220',4),(741,'J-230',4),(742,'J-300',4),(743,'J-310',4),(744,'J-320',4),(745,'J-330',4),(746,'Wagoneer',4),(747,'190DC',4),(748,'230SL',4),(749,'300SEL',4),(750,'Marauder',4),(751,'S-55',6),(752,'410',4),(753,'411',4),(754,'2000',4),(755,'TC',6),(756,'GT850',4),(757,'1964',1),(758,'1600',4),(759,'1800ti',4),(760,'3200CS',4),(761,'Sportwagon',4),(762,'Chevelle',4),(763,'SS',6),(764,'Nova SS',6),(765,'6 Cyl 2.7L',7),(766,'G10 Van',4),(767,'Malibu',4),(768,'A100',4),(769,'GTS',6),(770,'Custom 500',4),(771,'GT40',4),(772,'Mustang',4),(773,'B3500',4),(774,'G1500',4),(775,'I1000',4),(776,'C900',4),(777,'6 Cyl 4.2L',7),(778,'FJ3',4),(779,'FJ6',4),(780,'Pullman',6),(781,'Caliente',4),(782,'202',6),(783,'404',6),(784,'Cyclone',4),(785,'Sedan',4),(786,'442',4),(787,'Jetstar 88',4),(788,'Jetstar I',4),(789,'Vista Cruiser',4),(790,'Barracuda',4),(791,'Beaumont',4),(792,'2+2',6),(793,'GTO',4),(794,'356C',4),(795,'356SC',4),(796,'Monte Carlo',4),(797,'1965',1),(798,'2000c',4),(799,'2000cs',4),(800,'Gran Sport',4),(801,'Calais',4),(802,'Fleetwood',4),(803,'60 Special',6),(804,'Corsa',6),(805,'3 Cyl 2.6L',7),(806,'Majestic Major',4),(807,'Sovereign',4),(808,'Monaco',4),(809,'6 Cyl 4.9L',7),(810,'LTD',4),(811,'Shelby GT-350',6),(812,'6 Cyl 5.7L',7),(813,'I1500',4),(814,'I2500',4),(815,'L3500',4),(816,'AB1200',4),(817,'AB1400',4),(818,'CO4000 Transtar',4),(819,'8 Cyl 7.8L',7),(820,'8 Cyl 9.0L',7),(821,'D1000',4),(822,'D1100',4),(823,'D1200',4),(824,'D1300',4),(825,'D300',4),(826,'D900',4),(827,'M1100',4),(828,'M1200',4),(829,'M1400',4),(830,'M700',4),(831,'M800',4),(832,'DJ5',4),(833,'DJ6',4),(834,'J-2500',4),(835,'J-2600',4),(836,'J-2700',4),(837,'J-2800',4),(838,'J-3500',4),(839,'J-3600',4),(840,'J-3700',4),(841,'J-3800',4),(842,'520 Pickup',4),(843,'Delta 88',4),(844,'Peterbilt',3),(845,'282',4),(846,'6 Cyl 14.0L',7),(847,'6 Cyl 7.0L',7),(848,'Belvedere II',4),(849,'Fury II',4),(850,'Fury III',4),(851,'Satellite',4),(852,'911',4),(853,'912',4),(854,'Stout',4),(855,'1966',1),(856,'1600-2',4),(857,'1600ti',4),(858,'2000ti',4),(859,'Lux',6),(860,'2002',4),(861,'Caprice',4),(862,'8 Cyl 7.2L',7),(863,'Charger',4),(864,'124',4),(865,'Bronco',4),(866,'Lotus',6),(867,'500XL',6),(868,'Shelby GT-350H',6),(869,'2500',4),(870,'I3500',4),(871,'K1000',4),(872,'K1000 Pickup',4),(873,'K15/K1500 Pickup',4),(874,'K25/K2500 Pickup',4),(875,'LI3500',4),(876,'P1000',4),(877,'PB15 Series',4),(878,'PB25 Series',4),(879,'PB2500 Van',4),(880,'PB3500 Van',4),(881,'1000A',4),(882,'Travelall Custom',6),(883,'1100A',4),(884,'1200A',4),(885,'1300A',4),(886,'1500A',4),(887,'900A',4),(888,'CJ5A',4),(889,'CJ6A',4),(890,'Commando',4),(891,'8 Cyl 7.6L',7),(892,'200D',4),(893,'230',4),(894,'230S',4),(895,'250S',4),(896,'250SE',4),(897,'250SEC',4),(898,'250SL',4),(899,'Toronado',4),(900,'8 Cyl 9.3L',7),(901,'VIP',6),(902,'Executive',6),(903,'Pickup',4),(904,'Fastback',4),(905,'Squareback',4),(906,'1967',1),(907,'1602',4),(908,'C10 Suburban',4),(909,'C20 Panel',4),(910,'C20 Suburban',4),(911,'Camaro',4),(912,'RS',6),(913,'Z28',6),(914,'300 Deluxe',6),(915,'Concours',6),(916,'G20 Van',4),(917,'K10 Suburban',4),(918,'K20 Panel',4),(919,'K20 Suburban',4),(920,'P10 Van',4),(921,'A100 Pickup',4),(922,'A108 Van',4),(923,'R/T',6),(924,'Wm300 Pickup',4),(925,'850',4),(926,'Spider',6),(927,'Shelby GT-500',6),(928,'500 XL',6),(929,'C15/C1500 Pickup',4),(930,'C15/C1500 Suburban',4),(931,'C25/C2500 Pickup',4),(932,'C25/C2500 Suburban',4),(933,'C35/C3500 Pickup',4),(934,'G15/G1500 Van',4),(935,'G25/G2500 Van',4),(936,'G35/G3500 Van',4),(937,'K15/K1500 Suburban',4),(938,'K25/K2500 Suburban',4),(939,'P15/P1500 Van',4),(940,'P25/P2500 Van',4),(941,'P35/P3500 Van',4),(942,'1000B',4),(943,'1100B',4),(944,'1200B',4),(945,'1300B',4),(946,'1500B',4),(947,'908B',4),(948,'MA1200',4),(949,'240',4),(950,'340',4),(951,'420',4),(952,'G',6),(953,'Mazda',3),(954,'Cougar',4),(955,'XR-7',6),(956,'Marquis',4),(957,'Cutlass Supreme',4),(958,'Delmont 88',4),(959,'GTX',4),(960,'Firebird',4),(961,'326',6),(962,'400',6),(963,'HO',6),(964,'L',6),(965,'Shrike',4),(966,'Sonett',4),(967,'2000GT',4),(968,'Corona',4),(969,'142',4),(970,'144',4),(971,'1968',1),(972,'Cabriolet',6),(973,'2002ti',4),(974,'2800',4),(975,'2800CS',4),(976,'225 Custom',6),(977,'GS 350',4),(978,'GS 400',4),(979,'8 Cyl 7.7L',7),(980,'Sportvan',6),(981,'K30 Pickup',4),(982,'P20 Van',4),(983,'P30 Van',4),(984,'Super Bee',6),(985,'M375',4),(986,'Post Office',6),(987,'P375',4),(988,'Wagon',6),(989,'Shelby GT-500KR',6),(990,'Torino',4),(991,'Rally',6),(992,'Vandura',6),(993,'K35/K3500 Pickup',4),(994,'1000C',4),(995,'Travelall',6),(996,'1100C',4),(997,'1200C',4),(998,'1300C',4),(999,'1500C',4),(1000,'908C',4),(1001,'XJ',4),(1002,'4 Cyl 3.1L',7),(1003,'8 Cyl 7.5L',7),(1004,'Mark III',4),(1005,'1300',4),(1006,'MGC',4),(1007,'6 Cyl 2.9L',7),(1008,'220D',4),(1009,'280S',4),(1010,'280SE',4),(1011,'280SEL',4),(1012,'280SL',4),(1013,'Montego',4),(1014,'MX',6),(1015,'510',4),(1016,'521 Pickup',4),(1017,'Custom Suburban',6),(1018,'Sport Suburban',6),(1019,'Fury I',4),(1020,'Road Runner',4),(1021,'350',6),(1022,'Subaru',3),(1023,'360',4),(1024,'2 Cyl 0.4L',7),(1025,'Corolla',4),(1026,'Campmobile',4),(1027,'145',4),(1028,'1969',1),(1029,'Audi',3),(1030,'100 Series',4),(1031,'LS',6),(1032,'2000tii',4),(1033,'Blazer',4),(1034,'Concours Estate',6),(1035,'Greenbrier',6),(1036,'Estate',4),(1037,'Townsman',4),(1038,'Daytona',6),(1039,'SE',6),(1040,'Swinger',6),(1041,'Swinger 340',6),(1042,'E-100 Econoline',4),(1043,'E-200 Econoline',4),(1044,'E-300 Econoline',4),(1045,'Boss 302',6),(1046,'Boss 429',6),(1047,'Honda',3),(1048,'1000D',4),(1049,'1200D',4),(1050,'1300D',4),(1051,'1500D',4),(1052,'M800 Navy',4),(1053,'M800 Post Office',4),(1054,'250C',4),(1055,'Cobra Jet',6),(1056,'CJ',6),(1057,'Spoiler',6),(1058,'X-100',6),(1059,'Royale',6),(1060,'350 HO',6),(1061,'Trans Am',6),(1062,'E',6),(1063,'T',6),(1064,'3500S',4),(1065,'99',4),(1066,'Hi-Lux',4),(1067,'164',4),(1068,'1970',1),(1069,'GS',4),(1070,'GS 455',4),(1071,'GSX',6),(1072,'8 Cyl 8.2L',7),(1073,'8 Cyl 7.4L',7),(1074,'G30 Van',4),(1075,'Challenger',4),(1076,'T/A',6),(1077,'MB300',4),(1078,'Racer',6),(1079,'Custom Wagon',6),(1080,'Chateau Wagon',6),(1081,'F600',4),(1082,'F700',4),(1083,'F7000',4),(1084,'Maverick',4),(1085,'Grande',6),(1086,'Mach 1',6),(1087,'Police Cruiser',6),(1088,'Super Cobra Jet',6),(1089,'Jimmy',4),(1090,'CO190',4),(1091,'6 Cyl 6.7L',7),(1092,'6 Cyl 7.4L',7),(1093,'6 Cyl 8.2L',7),(1094,'CO200',4),(1095,'J-4500',4),(1096,'J-4600',4),(1097,'J-4700',4),(1098,'J-4800',4),(1099,'Range Rover',4),(1100,'616',4),(1101,'2 Cyl 1.1L',7),(1102,'MX Brougham',6),(1103,'240Z',4),(1104,'Gran Coupe',6),(1105,'Cuda',4),(1106,'Duster',4),(1107,'S-23',6),(1108,'Sport GT',6),(1109,'Superbird',4),(1110,'Esprit',6),(1111,'Formula',6),(1112,'The Judge',6),(1113,'914',4),(1114,'4',6),(1115,'6',6),(1116,'Star',4),(1117,'1971',1),(1118,'Touring',6),(1119,'1802',4),(1120,'2.8 Bavaria',4),(1121,'2002tii',4),(1122,'3.0CS',4),(1123,'3.0CSL',4),(1124,'3.0CSi',4),(1125,'3.0S',4),(1126,'3.0Si',4),(1127,'Centurion',4),(1128,'Vega',4),(1129,'Panel',6),(1130,'B100 Van',4),(1131,'B200 Van',4),(1132,'B300 Van',4),(1133,'Special Edition',6),(1134,'Colt',4),(1135,'Crestwood',6),(1136,'Demon',6),(1137,'Demon 340',6),(1138,'RM300',4),(1139,'Motor Home',6),(1140,'RM350',4),(1141,'128',4),(1142,'C7000',4),(1143,'G-100',4),(1144,'Postal',6),(1145,'M-400',4),(1146,'Boss 351',6),(1147,'Pinto',4),(1148,'1010',4),(1149,'1110',4),(1150,'1210',4),(1151,'1310',4),(1152,'CO220',4),(1153,'Scout II',4),(1154,'12 Cyl 5.3L',7),(1155,'RX-2',4),(1156,'Custom Cruiser',4),(1157,'Cricket',4),(1158,'Regent',6),(1159,'Sebring',6),(1160,'Sebring Plus',6),(1161,'Scamp',4),(1162,'Grand Safari',4),(1163,'Grandville',4),(1164,'T37',6),(1165,'Carina',4),(1166,'Celica',4),(1167,'ST',6),(1168,'Super Beetle',4),(1169,'211',6),(1170,'215',6),(1171,'261',6),(1172,'265',6),(1173,'1972',1),(1174,'GL',6),(1175,'Bavaria',4),(1176,'Luv Pickup',4),(1177,'Maxi',6),(1178,'Maxi Wagon',6),(1179,'Rallye',6),(1180,'R300',4),(1181,'Courier',4),(1182,'Gran Torino',4),(1183,'MS1210',4),(1184,'XJ6',4),(1185,'Mark IV',4),(1186,'618',4),(1187,'808',4),(1188,'B1600',4),(1189,'RX-3',4),(1190,'350SL',4),(1191,'620 Pickup',4),(1192,'Cruiser',6),(1193,'Gran Sedan',6),(1194,'Gran Fury',4),(1195,'Luxury',6),(1196,'EMS',6),(1197,'ES',6),(1198,'1973',1),(1199,'Fox',4),(1200,'Turbo',6),(1201,'Apollo',4),(1202,'Luxus',6),(1203,'Regal',4),(1204,'60 Special Brougham',6),(1205,'6 Cyl 6.2L',7),(1206,'6 Cyl 7.1L',7),(1207,'LT',6),(1208,'Classic',6),(1209,'H70',4),(1210,'H80',4),(1211,'H90',4),(1212,'J70',4),(1213,'J80',4),(1214,'Laguna',4),(1215,'Landau',6),(1216,'CB300',4),(1217,'Sport 340',6),(1218,'RM400',4),(1219,'SL',6),(1220,'Transmode',4),(1221,'Civic',4),(1222,'1310M',4),(1223,'XJ12',4),(1224,'280',4),(1225,'280C',4),(1226,'450SE',4),(1227,'450SEL',4),(1228,'450SL',4),(1229,'450SLC',4),(1230,'610',4),(1231,'B210',4),(1232,'Omega',4),(1233,'Grand Am',4),(1234,'LE',6),(1235,'DL',4),(1236,'412',4),(1237,'Thing',4),(1238,'1974',1),(1239,'225 Limited',6),(1240,'Type S-3',6),(1241,'Sport 360',6),(1242,'M350',4),(1243,'M400',4),(1244,'R400',4),(1245,'Ramcharger',4),(1246,'X-1/9',4),(1247,'Elite',6),(1248,'M-450',4),(1249,'Mustang II',4),(1250,'Ghia',6),(1251,'MHC1310',4),(1252,'V12',4),(1253,'Cherokee',4),(1254,'J10',4),(1255,'J20',4),(1256,'RX-4',4),(1257,'2 Cyl 1.3L',7),(1258,'Rotary Pickup',4),(1259,'240D',4),(1260,'4 Cyl 2.4L',7),(1261,'260Z',4),(1262,'710',4),(1263,'8 Cyl 14.8L',7),(1264,'Police',6),(1265,'PB100 Van',4),(1266,'PB200 Van',4),(1267,'Voyager EX',6),(1268,'Voyager EX Wagon',6),(1269,'PB300 Van',4),(1270,'Trailduster',4),(1271,'Safari Luxury',6),(1272,'Dasher',4),(1273,'1975',1),(1274,'530i',4),(1275,'SR',6),(1276,'Skyhawk',4),(1277,'d\'Elegance',6),(1278,'C10',4),(1279,'Cheyenne',6),(1280,'Custom Deluxe',6),(1281,'Scottsdale',6),(1282,'Silverado',6),(1283,'C20',4),(1284,'C30',4),(1285,'Classic Sport',6),(1286,'Stingray',6),(1287,'G10',4),(1288,'Chevy Van',6),(1289,'G20',4),(1290,'G30',4),(1291,'Hi-Cube',6),(1292,'K10',4),(1293,'K20',4),(1294,'K5 Blazer',4),(1295,'LUV',4),(1296,'Mikado',6),(1297,'S3',6),(1298,'Classic Estate',6),(1299,'LN',6),(1300,'P10',4),(1301,'Step-Van',6),(1302,'P20',4),(1303,'P30',4),(1304,'Cosworth',6),(1305,'LX',6),(1306,'Panel Express',6),(1307,'Cordoba',4),(1308,'B200',4),(1309,'B300',4),(1310,'Carousel',6),(1311,'D100',4),(1312,'D200',4),(1313,'Swinger Special',6),(1314,'Royal Brougham',6),(1315,'Royal Monaco',4),(1316,'W100',4),(1317,'W200',4),(1318,'W300',4),(1319,'131',4),(1320,'Northland',6),(1321,'Ranger',6),(1322,'Chateau',6),(1323,'E-100 Econoline Club Wagon',4),(1324,'E-150 Econoline',4),(1325,'E-150 Econoline Club Wagon',4),(1326,'E-250 Econoline',4),(1327,'E-250 Econoline Club Wagon',4),(1328,'E-350 Econoline',4),(1329,'Ranger XLT',6),(1330,'F-150',4),(1331,'F-500',4),(1332,'Granada',4),(1333,'Grabber',6),(1334,'P-400',4),(1335,'P-500',4),(1336,'C15',4),(1337,'High Sierra',6),(1338,'Sierra Classic',6),(1339,'Sierra Grande',6),(1340,'C15 Suburban',4),(1341,'C25',4),(1342,'C25 Suburban',4),(1343,'C35',4),(1344,'G15',4),(1345,'Rally STX',6),(1346,'G25',4),(1347,'G35',4),(1348,'Magnavan',6),(1349,'Rally Camper Special',6),(1350,'Vandura Special',6),(1351,'K15',4),(1352,'K15 Suburban',4),(1353,'K25',4),(1354,'K25 Suburban',4),(1355,'P15',4),(1356,'Value Van',6),(1357,'P25',4),(1358,'P35',4),(1359,'SP',6),(1360,'CVCC',6),(1361,'MS',4),(1362,'XLC',6),(1363,'Renegade',6),(1364,'Wide Track Chief',6),(1365,'MK IV',6),(1366,'V-8',6),(1367,'5 Cyl 3.0L',7),(1368,'Bobcat',4),(1369,'Grand Marquis',4),(1370,'Monarch',4),(1371,'280Z',4),(1372,'620',4),(1373,'Cutlass Salon',4),(1374,'Hurst',6),(1375,'Salon',6),(1376,'PB100',4),(1377,'PB200',4),(1378,'PB300',4),(1379,'Astre',4),(1380,'GT Safari',6),(1381,'S Safari',6),(1382,'SJ',6),(1383,'SJ Safari',6),(1384,'Grand LeMans',4),(1385,'LJ',6),(1386,'GF',4),(1387,'DLX',6),(1388,'SR5',6),(1389,'Rabbit',4),(1390,'Scirocco',4),(1391,'Kombi',6),(1392,'242',4),(1393,'244',4),(1394,'245',4),(1395,'1976',1),(1396,'Indianapolis 500 Pace Car',6),(1397,'Opel',4),(1398,'Bicentenial Edition',6),(1399,'Brougham Talisman',6),(1400,'Brougham d\'Elegance',6),(1401,'Seville',4),(1402,'LT Rally Sport',6),(1403,'Rally Sport',6),(1404,'Classic Landau',6),(1405,'Chevette',4),(1406,'Scooter',6),(1407,'Woody',6),(1408,'Custom Landau',6),(1409,'2+2 Spyder',6),(1410,'Towne Coupe',6),(1411,'Towne Coupe Cabriolet',6),(1412,'Towne Coupe Sport',6),(1413,'Towne Coupe Spyder',6),(1414,'Aspen',4),(1415,'Warlock',6),(1416,'Pony',6),(1417,'Sierra Madre Del Sur',6),(1418,'Accord',4),(1419,'Terra',6),(1420,'Traveler',6),(1421,'XJS',4),(1422,'CJ7',4),(1423,'Cosmo',4),(1424,'Mizer',4),(1425,'MPG Runabout',6),(1426,'Runabout',6),(1427,'S Runabout',6),(1428,'Grand Ghia',6),(1429,'MX Villager',6),(1430,'F10',4),(1431,'Colonnade S',6),(1432,'Colonnade',6),(1433,'Cutlass Tiara',4),(1434,'SX',6),(1435,'6 Cyl 9.0L',7),(1436,'Arrow',4),(1437,'Volare',4),(1438,'BR',6),(1439,'Sport Coupe',6),(1440,'Sunbird',4),(1441,'Turbo Carrera',6),(1442,'GLE',6),(1443,'Standard',4),(1444,'262',4),(1445,'264',4),(1446,'1977',1),(1447,'320i',4),(1448,'630CSi',4),(1449,'Custom Sport',6),(1450,'75 Formal',6),(1451,'Formal',6),(1452,'Opera',6),(1453,'Sandpiper',6),(1454,'Conquista',6),(1455,'K30',4),(1456,'Mighty Mike',6),(1457,'Mirage',6),(1458,'Medallion',6),(1459,'D150',4),(1460,'Diplomat',4),(1461,'W150',4),(1462,'E-350 Econoline Club Wagon',4),(1463,'XLT',6),(1464,'LTD II',4),(1465,'Heavy Half',6),(1466,'Indy Hauler',6),(1467,'K35',4),(1468,'Golden Eagle',6),(1469,'Mark V',4),(1470,'Versailles',4),(1471,'B1800',4),(1472,'GLC',4),(1473,'DX',6),(1474,'280E',4),(1475,'200SX',4),(1476,'810',4),(1477,'Firenza',6),(1478,'XS',6),(1479,'XSR',6),(1480,'924',4),(1481,'1978',1),(1482,'5000',4),(1483,'5 Cyl 2.2L',7),(1484,'633CSi',4),(1485,'733i',4),(1486,'Park Avenue',6),(1487,'LXXV Anniversary Edition',6),(1488,'Phaeton',6),(1489,'Elegante',6),(1490,'Big Ten',6),(1491,'Silver Anniversary Edition',6),(1492,'2+2 Sport',6),(1493,'4 Cyl 2.6L',7),(1494,'Premium',6),(1495,'Li\'l Red Express',6),(1496,'D400',4),(1497,'D450',4),(1498,'Magnum',4),(1499,'XE',6),(1500,'Omni',4),(1501,'RD200',4),(1502,'Brava',4),(1503,'Ranger Lariat',6),(1504,'Fairmont',4),(1505,'ESS',6),(1506,'Diamond Jubilee',6),(1507,'Town Landau',6),(1508,'Street Coupe',6),(1509,'Caballero',4),(1510,'Diablo',6),(1511,'Laredo',6),(1512,'SS2',6),(1513,'Wide Track',6),(1514,'280CE',4),(1515,'300CD',4),(1516,'300SD',4),(1517,'Z7',6),(1518,'Cutlass Calais',4),(1519,'Brougham LS',6),(1520,'6 Cyl 18.8L',7),(1521,'8 Cyl 12.1L',7),(1522,'Caravelle',4),(1523,'Horizon',4),(1524,'Sapporo',4),(1525,'SA',6),(1526,'SC',6),(1527,'928',4),(1528,'930',4),(1529,'Brat',4),(1530,'Cressida',4),(1531,'1979',1),(1532,'5 Cyl 2.0L',7),(1533,'528i',4),(1534,'Road Hawk',6),(1535,'Bruin',4),(1536,'J8C042',6),(1537,'J8C064',6),(1538,'J9C042',6),(1539,'J9C064',6),(1540,'C5D042',6),(1541,'8 Cyl 10.4L',7),(1542,'C6D042',6),(1543,'4 Cyl 3.5L',7),(1544,'C7D042',6),(1545,'C7D064',6),(1546,'Berlinetta',6),(1547,'Royal Knight',6),(1548,'P40',4),(1549,'Warlock II',6),(1550,'D50',4),(1551,'24',6),(1552,'St. Regis',4),(1553,'Strada',4),(1554,'Heritage',6),(1555,'C1500',4),(1556,'C1500 Suburban',4),(1557,'C2500',4),(1558,'C2500 Suburban',4),(1559,'C3500',4),(1560,'C5000',4),(1561,'C6000',4),(1562,'G2500',4),(1563,'G3500',4),(1564,'K1500',4),(1565,'K1500 Suburban',4),(1566,'K2500',4),(1567,'K2500 Suburban',4),(1568,'K3500',4),(1569,'P1500',4),(1570,'P2500',4),(1571,'P3500',4),(1572,'P4500',4),(1573,'Prelude',4),(1574,'1724',4),(1575,'8 Cyl 7.3L',7),(1576,'1754',4),(1577,'6 Cyl 7.6L',7),(1578,'1824',4),(1579,'1854',4),(1580,'1924 S',4),(1581,'1925 S',4),(1582,'2155',4),(1583,'2275',4),(1584,'2554',4),(1585,'2574',4),(1586,'2575',4),(1587,'2674',4),(1588,'F1924',4),(1589,'F1954',4),(1590,'F2275',4),(1591,'F2554',4),(1592,'F2574',4),(1593,'F2575',4),(1594,'F2674',4),(1595,'Wide Track Golden Eagle',6),(1596,'626',4),(1597,'B2000',4),(1598,'RX-7',4),(1599,'300TD',4),(1600,'210',4),(1601,'280ZX',4),(1602,'310',4),(1603,'GX',6),(1604,'Vista Cruiser Brougham',6),(1605,'Arrow Pickup',4),(1606,'Champ',4),(1607,'TC3',6),(1608,'900',4),(1609,'FE',4),(1610,'Supra',6),(1611,'1980',1),(1612,'4000',4),(1613,'S Turbo',6),(1614,'Designers\' Accent Edition',6),(1615,'B60',4),(1616,'B6P042',6),(1617,'C6D062',6),(1618,'8 Cyl 9.1L',7),(1619,'Citation',4),(1620,'X-11',6),(1621,'Spyder',6),(1622,'Mirada',4),(1623,'Special Coupe',6),(1624,'B600',4),(1625,'B700',4),(1626,'C600',4),(1627,'C700',4),(1628,'C800',4),(1629,'C8000',4),(1630,'F800',4),(1631,'FT800',4),(1632,'L800',4),(1633,'LN600',4),(1634,'LN700',4),(1635,'LN800',4),(1636,'Crown Victoria',6),(1637,'Silver Anniversary',6),(1638,'B6000',4),(1639,'1300 DX',6),(1640,'1500 DX',6),(1641,'1500 GL',6),(1642,'1654',4),(1643,'Cargostar',4),(1644,'CO1610B',6),(1645,'CO1710B',6),(1646,'CO1810B',6),(1647,'CO1910B',6),(1648,'8 Cyl 8.8L',7),(1649,'COF1810B',6),(1650,'F2654',4),(1651,'S1624',4),(1652,'S1723',4),(1653,'S1724',4),(1654,'S1823',4),(1655,'S1824',4),(1656,'S1924',4),(1657,'S1925',4),(1658,'S2125',4),(1659,'S2524',4),(1660,'Golden Hawk',6),(1661,'Wide Track Laredo',6),(1662,'Wide Track Limited',6),(1663,'Mark VI',4),(1664,'Mack',3),(1665,'MS200P Mid-Liner',4),(1666,'6 Cyl 5.5L',7),(1667,'Sundowner',6),(1668,'Sundowner Sport',6),(1669,'2+2 GL',6),(1670,'720',4),(1671,'Cutlass Cruiser',4),(1672,'Royale Brougham',6),(1673,'XSC',6),(1674,'GLI',6),(1675,'GLF',4),(1676,'Suzuki',3),(1677,'SJ410',4),(1678,'3 Cyl 1.0L',7),(1679,'Tercel',4),(1680,'STD',6),(1681,'Jetta',4),(1682,'Rabbit Convertible',4),(1683,'Rabbit Pickup',4),(1684,'Vanagon',4),(1685,'1981',1),(1686,'Coupe',4),(1687,'T-Type',6),(1688,'C7D044',6),(1689,'Bonaventure',6),(1690,'P4T042',6),(1691,'Aries',4),(1692,'B150',4),(1693,'B250',4),(1694,'B350',4),(1695,'D250',4),(1696,'D350',4),(1697,'024 Miser',6),(1698,'Miser',6),(1699,'Royal SE',6),(1700,'W250',4),(1701,'W350',4),(1702,'CL9000',4),(1703,'6 Cyl 14.6L',7),(1704,'CLT9000',4),(1705,'CT8000',4),(1706,'CT800D',4),(1707,'GLX',6),(1708,'L8000',4),(1709,'L900',4),(1710,'L9000',4),(1711,'LN7000',4),(1712,'LN8000',4),(1713,'LN900',4),(1714,'LN9000',4),(1715,'LNT800',4),(1716,'LNT8000',4),(1717,'LNT900',4),(1718,'LNT9000',4),(1719,'LT800',4),(1720,'LT8000',4),(1721,'LT9000',4),(1722,'LTS8000',4),(1723,'LTS9000',4),(1724,'Freightliner',3),(1725,'FLC120',4),(1726,'FLC12042ST',6),(1727,'FLC12062ST',6),(1728,'FLC12064S',6),(1729,'FLC12064ST',6),(1730,'8 Cyl 18.0L',7),(1731,'FLC12084ST',6),(1732,'FLC120GL',6),(1733,'FLT086',4),(1734,'FLT08642S',6),(1735,'FLT08642ST',6),(1736,'FLT08662S',6),(1737,'FLT08662ST',6),(1738,'FLT08664S',6),(1739,'FLT08664ST',6),(1740,'FLT086GL',6),(1741,'Astro',4),(1742,'D9K042',6),(1743,'D9K064',6),(1744,'D9L020',6),(1745,'D9L042',6),(1746,'D9L064',6),(1747,'Brigadier',4),(1748,'6 Cyl 5.2L',7),(1749,'J9C020',6),(1750,'C6D044',6),(1751,'Amarillo',6),(1752,'Rally Custom',6),(1753,'General',4),(1754,'N9E020',6),(1755,'N9E042',6),(1756,'N9E064',6),(1757,'N9F020',6),(1758,'N9F042',6),(1759,'N9F064',6),(1760,'Transit Bus',4),(1761,'5070',4),(1762,'CO9670',4),(1763,'COF9670',4),(1764,'CO1850B',6),(1765,'CO1950B',6),(1766,'F5070',4),(1767,'F5070-SF',4),(1768,'G1954',4),(1769,'G2504',4),(1770,'G2604',4),(1771,'S1754',4),(1772,'S1853',4),(1773,'S1854',4),(1774,'S1954',4),(1775,'S1955',4),(1776,'S2554',4),(1777,'I-Mark',4),(1778,'Chief',6),(1779,'Scrambler',4),(1780,'Kenworth',3),(1781,'C500',4),(1782,'6 Cyl 10.5L',7),(1783,'L700',4),(1784,'W900',4),(1785,'Town Car',4),(1786,'DM',4),(1787,'DM600S',6),(1788,'6 Cyl 11.0L',7),(1789,'DM600SX',6),(1790,'DM600X',6),(1791,'DM800SX',6),(1792,'DMC',4),(1793,'DMC800SX',6),(1794,'DMM',4),(1795,'DMM6006EX',6),(1796,'DMM6006S',6),(1797,'DMM600EX',6),(1798,'DMM600S',6),(1799,'MC400F',6),(1800,'MC400fC',6),(1801,'MC600F',6),(1802,'MC600FC',6),(1803,'MC600FCS',6),(1804,'MC600P',6),(1805,'MC600S',6),(1806,'MS250P Mid-Liner',4),(1807,'MS300P Mid-Liner',4),(1808,'6 Cyl 8.8L',7),(1809,'MS300T Mid-Liner',4),(1810,'R',4),(1811,'R600ST',6),(1812,'R600T',6),(1813,'R700S',6),(1814,'R700ST',6),(1815,'RD',4),(1816,'RD600P',6),(1817,'RD600S',6),(1818,'RD600SX',6),(1819,'RD800SX',6),(1820,'RM',4),(1821,'RM6004X',6),(1822,'RM6006S',6),(1823,'RM600EX',6),(1824,'RM600S',6),(1825,'RM600SX',6),(1826,'RS600LS',6),(1827,'RS600LST',6),(1828,'RS600LT',6),(1829,'RWL',4),(1830,'RWL700LS',6),(1831,'RWL700LST',6),(1832,'8 Cyl 16.4L',7),(1833,'RWS',4),(1834,'RWS700L',6),(1835,'RWS700LS',6),(1836,'RWS700LST',6),(1837,'RWS700LT',6),(1838,'U',4),(1839,'U600T',6),(1840,'U700T',6),(1841,'WS',4),(1842,'WS700LS',6),(1843,'WS700LST',6),(1844,'Custom L',6),(1845,'GSL',6),(1846,'380SEL',4),(1847,'8 Cyl 3.8L',7),(1848,'380SL',4),(1849,'380SLC',4),(1850,'L1013',4),(1851,'L1113',4),(1852,'L1116',4),(1853,'L1316',4),(1854,'Lynx',4),(1855,'GL Turbo',6),(1856,'Maxima',4),(1857,'Supreme',6),(1858,'348',4),(1859,'352',4),(1860,'359',4),(1861,'362',4),(1862,'387',4),(1863,'TC3 Miser',6),(1864,'PB150',4),(1865,'PB250',4),(1866,'PB350',4),(1867,'Reliant',4),(1868,'T1000',4),(1869,'LJ81',4),(1870,'BJ42',6),(1871,'BJ60',6),(1872,'BJ60LV-KK',6),(1873,'4 Cyl 3.4L',7),(1874,'Starlet',4),(1875,'L Custom',6),(1876,'Ls Deluxe',6),(1877,'GLT',6),(1878,'1982',1),(1879,'528e',4),(1880,'Limited Edition F/E',6),(1881,'Limited Turbo',6),(1882,'Limited Edition',6),(1883,'Cimarron',4),(1884,'8 Cyl 4.1L',7),(1885,'8 Cyl 6.2L',7),(1886,'6 Cyl 6.1L',7),(1887,'Z28 Indianapolis 500 Pace Car',6),(1888,'Cavalier',4),(1889,'CL',6),(1890,'Cadet',6),(1891,'Celebrity',4),(1892,'CS',6),(1893,'Diesel',6),(1894,'Collector\'s Edition',6),(1895,'S10',4),(1896,'Durango',6),(1897,'Tahoe',6),(1898,'024 Charger 2.2',6),(1899,'E-Type',6),(1900,'Rampage',4),(1901,'High Line',6),(1902,'XLS',6),(1903,'XLT Lariat',6),(1904,'EXP',4),(1905,'LTL9000',4),(1906,'S15',4),(1907,'1300 FE',6),(1908,'Wagovan',6),(1909,'Vanden Plas',4),(1910,'T400',4),(1911,'Givenchy',6),(1912,'Signature',6),(1913,'Bill Blass',6),(1914,'Pucci',6),(1915,'Cartier',6),(1916,'MRE',4),(1917,'MRE600S',6),(1918,'R400P',6),(1919,'R600FC',6),(1920,'R600SX',6),(1921,'WL',4),(1922,'WL700LST',6),(1923,'WS700LT',6),(1924,'Sundowner Base',6),(1925,'Sundowner DLX',6),(1926,'B2200',4),(1927,'380SEC',4),(1928,'Black Magic',6),(1929,'LN7',4),(1930,'MPG',6),(1931,'2+2 Turbo',6),(1932,'Sentra',4),(1933,'Stanza',4),(1934,'Regency Brougham',6),(1935,'Cutlass Ciera',4),(1936,'TC3 Custom',6),(1937,'TC3 Turismo',6),(1938,'Turismo',6),(1939,'6000',4),(1940,'S/E',6),(1941,'J2000',4),(1942,'Quantum',4),(1943,'1983',1),(1944,'Quattro',4),(1945,'533i',4),(1946,'XX Anniversary Edition',6),(1947,'Type-10',6),(1948,'P60',4),(1949,'P6T042',6),(1950,'S10 Blazer',4),(1951,'E Class',4),(1952,'Executive Sedan',4),(1953,'Fifth Avenue',4),(1954,'Mark Cross',6),(1955,'Power Ram 50',4),(1956,'Ram 50',4),(1957,'B7000',4),(1958,'F8000',4),(1959,'FT8000',4),(1960,'Turbo GT',6),(1961,'FLT08684S',6),(1962,'FLT08684ST',6),(1963,'P6000',4),(1964,'Gypsy',6),(1965,'S15 Jimmy',4),(1966,'1500 S',6),(1967,'Hyundai',3),(1968,'GL Deluxe',6),(1969,'GLS Glory',6),(1970,'2276 S',4),(1971,'2375 S',4),(1972,'6 Cyl 10.0L',7),(1973,'F2276 S',4),(1974,'F2375 S',4),(1975,'Impulse',4),(1976,'Valentino',6),(1977,'MC400P',6),(1978,'MH',4),(1979,'MH600',6),(1980,'R600FCS',6),(1981,'R600S',6),(1982,'RM6006SX',6),(1983,'U700ST',6),(1984,'WL700LS',6),(1985,'Crimson Cat',6),(1986,'Grand Sport',6),(1987,'LTS',6),(1988,'Z7 GS',6),(1989,'Mitsubishi',3),(1990,'Cordia',4),(1991,'Mighty Max',4),(1992,'SPX',6),(1993,'Montero',4),(1994,'Starion',4),(1995,'Tredia',4),(1996,'Pulsar',4),(1997,'Pulsar NX',4),(1998,'Cruiser LX',6),(1999,'Cruiser S',6),(2000,'Cruiser SX',6),(2001,'ESC',6),(2002,'Turismo 2.2',4),(2003,'STE',6),(2004,'944',4),(2005,'Camry',4),(2006,'Wolfsburg Edition',6),(2007,'GTI',6),(2008,'760',4),(2009,'1984',1),(2010,'4000 Quattro',4),(2011,'318i',4),(2012,'325e',4),(2013,'Olympia Limited',6),(2014,'Grand National',6),(2015,'d\'Oro',6),(2016,'Olympic',6),(2017,'Eurosport',6),(2018,'Citation II',4),(2019,'W7500 Tiltmaster',4),(2020,'W7R042',6),(2021,'6 Cyl 5.8L',7),(2022,'Laser',4),(2023,'Caravan',4),(2024,'Vista',6),(2025,'Mini Ram',4),(2026,'GLH',6),(2027,'Bronco II',4),(2028,'Eddie Bauer',6),(2029,'GT Turbo',6),(2030,'GT-350 20th Anniversary',6),(2031,'SVO',6),(2032,'Tempo',4),(2033,'Elan',6),(2034,'Fila',6),(2035,'FLC12062S',6),(2036,'FLC12084S',6),(2037,'W7500 Forward',4),(2038,'Hino',3),(2039,'FE17',4),(2040,'FF17',4),(2041,'FF19',4),(2042,'6 Cyl 6.5L',7),(2043,'FG22',4),(2044,'CRX',6),(2045,'CRX 1.5',6),(2046,'CO1650B',6),(2047,'CO1750B',6),(2048,'F5070-RHD',4),(2049,'F9370',4),(2050,'G1904',4),(2051,'G2105',4),(2052,'S1654',4),(2053,'S1753',4),(2054,'S2155',4),(2055,'S2375',4),(2056,'Trooper',4),(2057,'Grand Wagoneer',4),(2058,'LSC',6),(2059,'Versace',6),(2060,'MH600K',6),(2061,'R600F',6),(2062,'RD400S',6),(2063,'RD700SX',6),(2064,'RD800S',6),(2065,'Sundowner SE-5',6),(2066,'GSL-SE',6),(2067,'190E',4),(2068,'380SE',4),(2069,'500SEC',4),(2070,'500SEL',4),(2071,'L1117',4),(2072,'L1317',4),(2073,'RS Turbo',6),(2074,'Topaz',4),(2075,'300ZX',4),(2076,'Micra',4),(2077,'Cruiser LS',6),(2078,'Royale Brougham LS',6),(2079,'Brougham LX',6),(2080,'Brougham SX',6),(2081,'349',4),(2082,'Fiero',4),(2083,'J2000 Sunbird',4),(2084,'4Runner',4),(2085,'Sport DLX',6),(2086,'Sport SR5',6),(2087,'BJ60LG-MK',6),(2088,'LN56',6),(2089,'Van',4),(2090,'Wagon Crg',6),(2091,'Wagon DX',6),(2092,'Wagon LE',6),(2093,'1985',1),(2094,'CS Turbo',6),(2095,'524td',4),(2096,'535i',4),(2097,'635CSi',4),(2098,'735i',4),(2099,'Somerset Regal',4),(2100,'Brougham Commercial Chassis',6),(2101,'Iroc-Z',6),(2102,'Spectrum',4),(2103,'Executive Limousine',4),(2104,'FT900',4),(2105,'LX Brougham',6),(2106,'FLC112',4),(2107,'FLC11264ST',6),(2108,'FLC12042S',6),(2109,'D9K020',6),(2110,'SLE',6),(2111,'SLX',6),(2112,'FE19',4),(2113,'FG19',4),(2114,'SEi',6),(2115,'CRX HF',6),(2116,'CRX Si',6),(2117,'2.0 Si',6),(2118,'Stellar',4),(2119,'GSL Super',6),(2120,'SL Deluxe',6),(2121,'9370',4),(2122,'COF5870 Paystar',4),(2123,'F5070-SF/RHD',4),(2124,'MR',4),(2125,'548CH',4),(2126,'C550',4),(2127,'K100E',4),(2128,'DM800ST',6),(2129,'DMM6006SX',6),(2130,'DMM600SX',6),(2131,'MC600K',6),(2132,'RD400P',6),(2133,'RW',4),(2134,'RW600',6),(2135,'RW700',6),(2136,'XR3',6),(2137,'Galant',4),(2138,'ESi',6),(2139,'Sport Truck',6),(2140,'379',4),(2141,'Firefly',4),(2142,'Sunburst',4),(2143,'GL-10',4),(2144,'RX',4),(2145,'XT',4),(2146,'GL-10 Turbo',6),(2147,'Forsa',4),(2148,'SA310',4),(2149,'SJ413',4),(2150,'Samurai',4),(2151,'LE Limited',6),(2152,'Sport GTS',6),(2153,'BJ70',6),(2154,'MR2',4),(2155,'DLX Turbo',6),(2156,'RN60',6),(2157,'SR5 Turbo',6),(2158,'Golf',4),(2159,'740',4),(2160,'745',4),(2161,'1986',1),(2162,'Acura',3),(2163,'Integra',4),(2164,'Legend',4),(2165,'5000 Quattro',4),(2166,'325',4),(2167,'325es',4),(2168,'L7',4),(2169,'Somerset',4),(2170,'Classic Brougham',6),(2171,'Z24',6),(2172,'ER',6),(2173,'Plus',6),(2174,'W4500 Tiltmaster',4),(2175,'W4S042',6),(2176,'4 Cyl 3.9L',7),(2177,'6 Cyl 8.4L',7),(2178,'Vista Custom',6),(2179,'Turbo Z',6),(2180,'Aerostar',4),(2181,'CF6000',4),(2182,'6 Cyl 6.6L',7),(2183,'CF7000',4),(2184,'Country Squire LX',6),(2185,'Crown Victoria LX',6),(2186,'STX',6),(2187,'Taurus',4),(2188,'MT5',6),(2189,'FLC11242S',6),(2190,'FLC11242ST',6),(2191,'FLC11264S',6),(2192,'FLT086104S',6),(2193,'6 Cyl 12.7L',7),(2194,'MC Line',4),(2195,'FD16',4),(2196,'FE16',4),(2197,'FFC1',4),(2198,'SG19',4),(2199,'SG22',4),(2200,'6 Cyl 9.4L',7),(2201,'SG23',4),(2202,'LXi',6),(2203,'Si',6),(2204,'Excel',4),(2205,'GLS',6),(2206,'1652SC',4),(2207,'6 Cyl 5.9L',7),(2208,'F5070-SF/WW',4),(2209,'NPR',4),(2210,'Comanche',4),(2211,'X',6),(2212,'T600A',4),(2213,'CS200P Mid-Liner',4),(2214,'CS250P Mid-Liner',4),(2215,'CS300P Mid-Liner',4),(2216,'CS300T Mid-Liner',4),(2217,'323',4),(2218,'SE-5',6),(2219,'GXL',6),(2220,'5 Cyl 2.5L',7),(2221,'2.3-16',6),(2222,'300E',4),(2223,'300SDL',4),(2224,'420SEL',4),(2225,'560SEC',4),(2226,'560SEL',4),(2227,'560SL',4),(2228,'LP1419',4),(2229,'LPS1525',4),(2230,'5 Cyl 10.0L',7),(2231,'5',6),(2232,'Sable',4),(2233,'ESi-R',6),(2234,'Mitsubishi Fuso',3),(2235,'FK',4),(2236,'FK415',6),(2237,'FK455',6),(2238,'FM',4),(2239,'FM555',6),(2240,'D21',4),(2241,'Multi',4),(2242,'LC',6),(2243,'357',4),(2244,'Value Leader',6),(2245,'SPG',6),(2246,'9000',4),(2247,'JA',6),(2248,'JX',6),(2249,'JX SE',6),(2250,'HJ60',6),(2251,'Base Turbo',6),(2252,'LN55 DLX',6),(2253,'LN76',6),(2254,'RN63',6),(2255,'Syncro',6),(2256,'16-Valve',6),(2257,'Bus',6),(2258,'1987',1),(2259,'LS Special Edition',6),(2260,'5 Cyl 2.3L',7),(2261,'325i',4),(2262,'325is',4),(2263,'L6',4),(2264,'M6',4),(2265,'GNX',6),(2266,'Allante',4),(2267,'Beretta',4),(2268,'Classic LS Brougham',6),(2269,'Corsica',4),(2270,'LLV',4),(2271,'R10',4),(2272,'R10 Suburban',4),(2273,'R20',4),(2274,'R20 Suburban',4),(2275,'R30',4),(2276,'High Country',6),(2277,'S7',4),(2278,'S7T042',6),(2279,'CL Plus',6),(2280,'Express',6),(2281,'V10',4),(2282,'V10 Suburban',4),(2283,'V20',4),(2284,'V20 Suburban',4),(2285,'V30',4),(2286,'W6500 Tiltmaster',4),(2287,'W6R042',6),(2288,'Pacifica',6),(2289,'Shelby Z',6),(2290,'Dakota',4),(2291,'Grand Caravan',4),(2292,'America',6),(2293,'Expo',6),(2294,'Raider',4),(2295,'Shadow',4),(2296,'CF8000',4),(2297,'L8000F',4),(2298,'LL9000',4),(2299,'LNT8000F',4),(2300,'LS8000',4),(2301,'LS8000F',4),(2302,'LS9000',4),(2303,'LT8000F',4),(2304,'LTD Crown Victoria',4),(2305,'LTS8000F',4),(2306,'AWD',6),(2307,'6 Cyl 11.1L',7),(2308,'FLC112GL',6),(2309,'FLC120104S',6),(2310,'R1500',4),(2311,'R1500 Suburban',4),(2312,'R2500',4),(2313,'R2500 Suburban',4),(2314,'R3500',4),(2315,'EL',6),(2316,'SLT',6),(2317,'V1500',4),(2318,'V1500 Suburban',4),(2319,'V2500',4),(2320,'V2500 Suburban',4),(2321,'V3500',4),(2322,'W6500 Forward',4),(2323,'FB14',4),(2324,'4 Cyl 3.8L',7),(2325,'EXI',6),(2326,'CL Deluxe',6),(2327,'CXL Super',6),(2328,'1452SC',4),(2329,'2675',4),(2330,'F2675',4),(2331,'F5070-WW',4),(2332,'M1400 Metro II',4),(2333,'M1600 Metro II',4),(2334,'S1853FC',4),(2335,'S2276',4),(2336,'EVR',4),(2337,'FSR',4),(2338,'Wrangler',4),(2339,'K130',4),(2340,'6 Cyl 8.3L',7),(2341,'T800',4),(2342,'B2600',4),(2343,'Base 2+2',6),(2344,'GXL 2+2',6),(2345,'LX 2+2',6),(2346,'2.5 Turbo',6),(2347,'260E',4),(2348,'6 Cyl 6.0L',7),(2349,'L1319',4),(2350,'LP1219',4),(2351,'GS AWD',6),(2352,'LS AWD',6),(2353,'Sport AWD',6),(2354,'XR5',6),(2355,'Tracer',4),(2356,'1 Ton',6),(2357,'Precis',4),(2358,'GXE',6),(2359,'Regency Grande',6),(2360,'Regency Touring',6),(2361,'Trofeo',6),(2362,'224',4),(2363,'12 Cyl 14.0L',7),(2364,'375',4),(2365,'377',4),(2366,'397',4),(2367,'Grand Voyager',4),(2368,'Sundance',4),(2369,'S4',6),(2370,'Justy',4),(2371,'3 Cyl 1.2L',7),(2372,'V',6),(2373,'FX',6),(2374,'FX16',6),(2375,'FX16 GTS',6),(2376,'LN67',6),(2377,'GTI 16-Valve',6),(2378,'GLI 16-Valve',6),(2379,'780',4),(2380,'1988',1),(2381,'80 Quattro',4),(2382,'90 Quattro',4),(2383,'325iX',4),(2384,'535is',4),(2385,'735iL',4),(2386,'750iL',4),(2387,'12 Cyl 5.0L',7),(2388,'M3',4),(2389,'M5',4),(2390,'Reatta',4),(2391,'STS',6),(2392,'VL',6),(2393,'35th Anniversary Edition',6),(2394,'W5500 Tiltmaster',4),(2395,'W5S042',6),(2396,'W7R042HV',6),(2397,'TSi',6),(2398,'Dynasty',4),(2399,'Daihatsu',3),(2400,'Charade',4),(2401,'CLS',6),(2402,'CLX',6),(2403,'CSX',6),(2404,'Eagle',3),(2405,'XL Sport',6),(2406,'CFT8000',4),(2407,'F Super Duty',4),(2408,'F53',4),(2409,'F59',4),(2410,'Festiva',4),(2411,'LA9000',4),(2412,'LTA9000',4),(2413,'6 Cyl 12.0L',7),(2414,'FLD120',4),(2415,'FLD12042ST',6),(2416,'FLD12062ST',6),(2417,'FLD12064S',6),(2418,'FLD12064ST',6),(2419,'FLL086',4),(2420,'FLL08642S',6),(2421,'FLL08684S',6),(2422,'Sierra SLE',6),(2423,'Sierra SLX',6),(2424,'Timberline',6),(2425,'W5500 Forward',4),(2426,'FD17',4),(2427,'SF17',4),(2428,'HF',6),(2429,'RT 4WD',6),(2430,'2.0 S',6),(2431,'2.0 Si 4WS',6),(2432,'8300',4),(2433,'9300',4),(2434,'6 Cyl 10.3L',7),(2435,'S1654LP',4),(2436,'FTR',4),(2437,'FVR',4),(2438,'NRR',4),(2439,'Eliminator',6),(2440,'Islander',6),(2441,'Sahara',6),(2442,'13-210',4),(2443,'C540',4),(2444,'K220',4),(2445,'40th Anniversary Edition',6),(2446,'CM',4),(2447,'CM400',6),(2448,'6 Cyl 9.8L',7),(2449,'Turbo 4WS',6),(2450,'929',4),(2451,'MX-6',4),(2452,'10th Anniversary',6),(2453,'Convertible',6),(2454,'GTU',6),(2455,'SE 2+2',6),(2456,'300CE',4),(2457,'300TE',4),(2458,'Sigma',6),(2459,'FH',4),(2460,'FH100',6),(2461,'Indy 500 Pace Car',6),(2462,'376',4),(2463,'378',4),(2464,'6 Cyl 3.1L',7),(2465,'SSE',6),(2466,'Aerocoupe',6),(2467,'Aerocoupe YL',6),(2468,'Optima',4),(2469,'Turbo S',6),(2470,'XT6',6),(2471,'DLX All Trac',6),(2472,'LE All Trac',6),(2473,'All Trac',6),(2474,'SR5 All Trac',6),(2475,'Super Charged',6),(2476,'EZ',6),(2477,'Van Wagon',4),(2478,'YR32 DX',6),(2479,'Bestseller',6),(2480,'Boutique',6),(2481,'Carat',6),(2482,'1989',1),(2483,'100 Quattro',4),(2484,'200 Quattro',4),(2485,'525i',4),(2486,'Park Avenue Ultra',6),(2487,'LTZ',6),(2488,'Tracker',4),(2489,'W5R042',6),(2490,'GTC',6),(2491,'TC Maserati',4),(2492,'CES',6),(2493,'2000 GTX',4),(2494,'C/V',6),(2495,'100 DL',6),(2496,'100 E',6),(2497,'100 LX',6),(2498,'200 DL',6),(2499,'200 E',6),(2500,'200 LX',6),(2501,'ES Turbo',6),(2502,'Spirit',4),(2503,'Summit',4),(2504,'XL Plus',6),(2505,'XLT Plus',6),(2506,'Probe',4),(2507,'SHO',6),(2508,'Super Coupe',6),(2509,'FLA086',4),(2510,'FLA08642S',6),(2511,'FLA08642ST',6),(2512,'FLA08662S',6),(2513,'FLA08662ST',6),(2514,'FLA08664S',6),(2515,'FLA08664ST',6),(2516,'FLA08684S',6),(2517,'FLA08684ST',6),(2518,'FLA086GL',6),(2519,'FLC11262ST',6),(2520,'FLD12042S',6),(2521,'FLD12062S',6),(2522,'FLD12084S',6),(2523,'FLD12084ST',6),(2524,'FLD120GL',6),(2525,'FLL08642ST',6),(2526,'FLL08662S',6),(2527,'FLL08664S',6),(2528,'FLL08684ST',6),(2529,'Geo',3),(2530,'LSi',6),(2531,'XFi',6),(2532,'Prizm',4),(2533,'FA14',4),(2534,'SG231',4),(2535,'CX',6),(2536,'Sonata',4),(2537,'Sport Deluxe',6),(2538,'2654',4),(2539,'9600',4),(2540,'9700',4),(2541,'G2554',4),(2542,'G2654',4),(2543,'Amigo',4),(2544,'Majestic',6),(2545,'Collection Rouge',6),(2546,'22-210',4),(2547,'County',6),(2548,'Hunter',6),(2549,'Signature SE',6),(2550,'CH',4),(2551,'CH600',6),(2552,'6 Cyl 11.9L',7),(2553,'DM600K',6),(2554,'R600K',6),(2555,'RB',4),(2556,'RB600S',6),(2557,'RB600SX',6),(2558,'RD600K',6),(2559,'RD800K',6),(2560,'RS700LT',6),(2561,'RW600K',6),(2562,'RW700K',6),(2563,'MPV',4),(2564,'Cargo',6),(2565,'Passenger',6),(2566,'GT 4WS',6),(2567,'GTUs',6),(2568,'240SX',4),(2569,'372',4),(2570,'Acclaim',4),(2571,'Trans Am GTA',6),(2572,'Trans Am GTA SE',6),(2573,'GSE',6),(2574,'Carrera 4',6),(2575,'Speedster',6),(2576,'4 Cyl 2.7L',7),(2577,'S2',6),(2578,'4 Cyl 3.0L',7),(2579,'CD',6),(2580,'Sidekick',4),(2581,'JLX',6),(2582,'Swift',4),(2583,'UD',3),(2584,'2300',4),(2585,'6 Cyl 6.9L',7),(2586,'550T',4),(2587,'GL Sport',6),(2588,'1990',1),(2589,'20v',6),(2590,'Coupe Quattro',4),(2591,'V8 Quattro',4),(2592,'Luxury Edition',6),(2593,'GTZ',6),(2594,'454 SS',6),(2595,'WT',6),(2596,'C5500 Kodiak',4),(2597,'C5H042',6),(2598,'C60 Kodiak',4),(2599,'C6H042',6),(2600,'C70 Kodiak',4),(2601,'C7H042',6),(2602,'C7H064',6),(2603,'ZR-1',6),(2604,'Lumina',4),(2605,'Euro',6),(2606,'Lumina APV',4),(2607,'Rocky',4),(2608,'ES Limited',6),(2609,'Talon',4),(2610,'4x4',6),(2611,'LX Sport',6),(2612,'F600 LPO',4),(2613,'F700 LPO',4),(2614,'L Plus',6),(2615,'S Plus',6),(2616,'L AWD',6),(2617,'LX AWD',6),(2618,'FLL08664ST',6),(2619,'FLL086GL',6),(2620,'C5000 Topkick',4),(2621,'C6000 Topkick',4),(2622,'C7000 Topkick',4),(2623,'GSi',6),(2624,'Storm',4),(2625,'2+2 GSi',6),(2626,'GC17',4),(2627,'EX',6),(2628,'Si 4WS',6),(2629,'Si ALB',6),(2630,'Infiniti',3),(2631,'M30',4),(2632,'Q45',4),(2633,'1652UPS',4),(2634,'4600',4),(2635,'4600LP',4),(2636,'4600UH',4),(2637,'4700',4),(2638,'4700LP',4),(2639,'4800',4),(2640,'4900',4),(2641,'7100',4),(2642,'8100',4),(2643,'8200',4),(2644,'9400',4),(2645,'G4900',4),(2646,'Classic Collection',6),(2647,'K150',4),(2648,'Lexus',3),(2649,'ES250',4),(2650,'LS400',4),(2651,'Cypress',6),(2652,'Touring Edition',6),(2653,'CF',4),(2654,'CF600FAPS',6),(2655,'CF600FC',6),(2656,'CH600K',6),(2657,'FDM',4),(2658,'FDM700',6),(2659,'MR600K',6),(2660,'MR600P',6),(2661,'MR600S',6),(2662,'RB600K',6),(2663,'RD8006S',6),(2664,'LE-5',6),(2665,'Miata',4),(2666,'Protege',4),(2667,'4WD',6),(2668,'4Matic',6),(2669,'350SDL',4),(2670,'500SL',4),(2671,'LTS AWD',6),(2672,'XR5 AWD',6),(2673,'Eclipse',4),(2674,'GST',6),(2675,'FE434',6),(2676,'4 Cyl 3.3L',7),(2677,'FE444',6),(2678,'FG',4),(2679,'FG434',6),(2680,'FM557',6),(2681,'6 Cyl 7.5L',7),(2682,'Axxess',4),(2683,'Silhouette',4),(2684,'227',4),(2685,'Mclaren',6),(2686,'Trans Sport',4),(2687,'Carrera 2',6),(2688,'Sterling',3),(2689,'827',4),(2690,'SLi',6),(2691,'Legacy',4),(2692,'Loyale',4),(2693,'JL',6),(2694,'JS',6),(2695,'GA',6),(2696,'3300',4),(2697,'Corrado',4),(2698,'G60',6),(2699,'GTI 8-Valve',6),(2700,'GTX 16-V',6),(2701,'Passat',4),(2702,'1991',1),(2703,'NSX',4),(2704,'20v Avant',6),(2705,'Avant',6),(2706,'318is',4),(2707,'850i',4),(2708,'Ultra',6),(2709,'Spring Edition',6),(2710,'C3500HD',4),(2711,'Classic LTZ',6),(2712,'Z34',6),(2713,'Baja',6),(2714,'Tahoe LT',6),(2715,'200 GT',6),(2716,'Iroc',6),(2717,'Canyon Sport',6),(2718,'Stealth',4),(2719,'R/T Turbo',6),(2720,'XLT Sport',6),(2721,'Explorer',4),(2722,'LA8000F',4),(2723,'FL60',4),(2724,'FL70',4),(2725,'FL80',4),(2726,'FLC11284S',6),(2727,'FLC11284ST',6),(2728,'FLD112',4),(2729,'FLD11242S',6),(2730,'FLD11242ST',6),(2731,'FLD11262ST',6),(2732,'FLD11264S',6),(2733,'FLD11264ST',6),(2734,'FLD11284S',6),(2735,'FLD112GL',6),(2736,'SLS',6),(2737,'Sonoma',4),(2738,'Syclone',4),(2739,'EX-R',6),(2740,'Scoupe',4),(2741,'A',6),(2742,'3900FC',4),(2743,'Rodeo',4),(2744,'Stylus',4),(2745,'Briarwood',6),(2746,'County SE',6),(2747,'Great Divide',6),(2748,'CF600FCS',6),(2749,'CL600',6),(2750,'Navajo',4),(2751,'350SD',4),(2752,'XR2',6),(2753,'Trio',6),(2754,'3000GT',4),(2755,'VR-4',6),(2756,'GSR',6),(2757,'NX',4),(2758,'SE-R',6),(2759,'Regency Elite',6),(2760,'Bravada',4),(2761,'SE Turbo',6),(2762,'CD Turbo',6),(2763,'Saturn',3),(2764,'SL1',4),(2765,'SL2',4),(2766,'Previa',4),(2767,'Etienne Aigner',6),(2768,'940',4),(2769,'1992',1),(2770,'GS-R',6),(2771,'Vigor',4),(2772,'RS Heritage Edition',6),(2773,'Z28 Heritage Edition',6),(2774,'P6S042',6),(2775,'W4T042',6),(2776,'Iroc R/T',6),(2777,'Viper',4),(2778,'10 Cyl 8.0L',7),(2779,'XLT Nite',6),(2780,'Touring Sedan',6),(2781,'Chateau HD',6),(2782,'Custom HD',6),(2783,'XLT HD',6),(2784,'LX-E',6),(2785,'LLA9000',4),(2786,'LTLA9000',4),(2787,'LTLS9000',4),(2788,'FL6042S',6),(2789,'FL6042ST',6),(2790,'FL7042S',6),(2791,'FL8042S',6),(2792,'FL8042ST',6),(2793,'FL8064S',6),(2794,'FL8064ST',6),(2795,'FLA086104S',6),(2796,'FLA086106ST',6),(2797,'FLA086124S',6),(2798,'MB60',4),(2799,'MB6042S',6),(2800,'MB70',4),(2801,'MB7042S',6),(2802,'P6500',4),(2803,'Typhoon',4),(2804,'W4500 Forward',4),(2805,'Yukon',4),(2806,'FA15',4),(2807,'FB15',4),(2808,'FD20',4),(2809,'FE20',4),(2810,'FF20',4),(2811,'FF23',4),(2812,'GC20',4),(2813,'SG20',4),(2814,'VX',6),(2815,'Elantra',4),(2816,'CXL',6),(2817,'LSE',6),(2818,'ES300',4),(2819,'SC300',4),(2820,'SC400',4),(2821,'MX-3',4),(2822,'6 Cyl 1.8L',7),(2823,'400E',4),(2824,'400SE',4),(2825,'500E',4),(2826,'600SEL',4),(2827,'12 Cyl 6.0L',7),(2828,'Diamante',4),(2829,'Expo LRV',4),(2830,'FE439',6),(2831,'FE449',6),(2832,'FG439',6),(2833,'FK417',6),(2834,'FK457',6),(2835,'Royale LS',6),(2836,'Achieva',4),(2837,'SCX',6),(2838,'Vista SE',6),(2839,'SSEi',6),(2840,'GTP',6),(2841,'LE Value Leader',6),(2842,'RS America',6),(2843,'968',4),(2844,'CD Griffin Edition',6),(2845,'L 4x4',6),(2846,'LS 4x4',6),(2847,'SVX',4),(2848,'LSL',6),(2849,'XLE',6),(2850,'LSX',6),(2851,'Paseo',4),(2852,'3000HD',4),(2853,'3000SD',4),(2854,'600T',4),(2855,'SLC',6),(2856,'EuroVan',4),(2857,'CV',6),(2858,'960',4),(2859,'FE42',6),(2860,'FE42T',6),(2861,'FE64',6),(2862,'1993',1),(2863,'525iT',4),(2864,'740i',4),(2865,'740iL',4),(2866,'850Ci',4),(2867,'B7',4),(2868,'B7T042',6),(2869,'Classic LS',6),(2870,'GP3500',4),(2871,'Concorde',4),(2872,'Intrepid',4),(2873,'Vision',4),(2874,'Police Interceptor',6),(2875,'Lightning',6),(2876,'SVT Cobra',6),(2877,'SVT Cobra R',6),(2878,'P600',4),(2879,'Splash',6),(2880,'FLD11262S',6),(2881,'FLD11284ST',6),(2882,'FLD11286S',6),(2883,'Sierra Sport',6),(2884,'FA1415',4),(2885,'FB1715',4),(2886,'FD2218',4),(2887,'FD2218LP',4),(2888,'FE2618',4),(2889,'FF3018',4),(2890,'FF3020',4),(2891,'SG3320',4),(2892,'SG3323',4),(2893,'SG5523',4),(2894,'Civic del Sol',4),(2895,'VTEC',6),(2896,'J30',4),(2897,'9200 SBA',4),(2898,'XJRS',4),(2899,'Country',6),(2900,'Grand Cherokee',4),(2901,'K200',4),(2902,'K300',4),(2903,'6 Cyl 10.8L',7),(2904,'T450',4),(2905,'Defender 110',4),(2906,'County LWB',6),(2907,'GS300',4),(2908,'Jack Nicklaus',6),(2909,'CL700',6),(2910,'CL700K',6),(2911,'R1',6),(2912,'400SEL',4),(2913,'600SEC',4),(2914,'600SL',4),(2915,'Altima',4),(2916,'Quest',4),(2917,'Turbo 3.6',6),(2918,'Turbo Commemorative Edition',6),(2919,'Aero',6),(2920,'CDE',6),(2921,'CDE Turbo',6),(2922,'CSE',6),(2923,'CSE Turbo',6),(2924,'SC1',4),(2925,'SC2',4),(2926,'SW1',4),(2927,'SW2',4),(2928,'Impreza',4),(2929,'GTS All Trac',6),(2930,'6 Cyl 4.5L',7),(2931,'RN02',6),(2932,'Twin Turbo',6),(2933,'T100',4),(2934,'One-Ton',6),(2935,'V6',6),(2936,'5 Cyl 2.4L',7),(2937,'MV',6),(2938,'1994',1),(2939,'8 Cyl 3.0L',7),(2940,'540i',4),(2941,'840Ci',4),(2942,'850CSi',4),(2943,'12 Cyl 5.6L',7),(2944,'Z26',6),(2945,'Silverado Sport',6),(2946,'ZR2',6),(2947,'LHS',4),(2948,'Ram 1500',4),(2949,'Ram 2500',4),(2950,'Ram 3500',4),(2951,'Aspire',4),(2952,'XL HD',6),(2953,'LLS9000',4),(2954,'FL106',4),(2955,'FL10642S',6),(2956,'4 Cyl 8.5L',7),(2957,'FL10642ST',6),(2958,'FL10664S',6),(2959,'FL10664ST',6),(2960,'FL10666S',6),(2961,'FL8044S',6),(2962,'FL8062S',6),(2963,'FL8066S',6),(2964,'FLB',4),(2965,'FLB42S',6),(2966,'FLB42ST',6),(2967,'FLB62S',6),(2968,'FLB62ST',6),(2969,'FLB64S',6),(2970,'FLB64ST',6),(2971,'FLB84S',6),(2972,'FLB84ST',6),(2973,'FLBGL',6),(2974,'FLD11266S',6),(2975,'FLD12066S',6),(2976,'Sierra SL',6),(2977,'Sierra SLT',6),(2978,'Sierra Special',6),(2979,'Passport',4),(2980,'6 Cyl 8.7L',7),(2981,'NQR',4),(2982,'Kia',3),(2983,'Sephia',4),(2984,'Defender 90',4),(2985,'Discovery',4),(2986,'Tournament Edition',6),(2987,'LC600',6),(2988,'RD8006SX',6),(2989,'B2300',4),(2990,'B3000',4),(2991,'B4000',4),(2992,'M Edition',6),(2993,'R2',6),(2994,'C220',4),(2995,'C280',4),(2996,'E320',4),(2997,'E420',4),(2998,'E500',4),(2999,'S320',4),(3000,'LWB',6),(3001,'SWB',6),(3002,'S350',4),(3003,'S420',4),(3004,'S500',4),(3005,'S600',4),(3006,'SL320',4),(3007,'SL500',4),(3008,'SL600',4),(3009,'Nautica',6),(3010,'Trans Am GT',6),(3011,'Sunrunner',4),(3012,'Targa',6),(3013,'Alpine Sport',6),(3014,'Outdoor',6),(3015,'Sun Sport',6),(3016,'1995',1),(3017,'TL',4),(3018,'A6',4),(3019,'A6 Quattro',4),(3020,'S6',4),(3021,'318ti',4),(3022,'12 Cyl 5.4L',7),(3023,'Lightweight',6),(3024,'ETC',6),(3025,'W3500 Tiltmaster',4),(3026,'W3S042',6),(3027,'Cirrus',4),(3028,'Avenger',4),(3029,'B1500',4),(3030,'B2500',4),(3031,'Neon',4),(3032,'Stratus',4),(3033,'B800',4),(3034,'B800F',4),(3035,'Contour',4),(3036,'Expedition',6),(3037,'F800 LPO',4),(3038,'P800',4),(3039,'Windstar',4),(3040,'FL10644S',6),(3041,'FL10662ST',6),(3042,'FL50',4),(3043,'FL5042S',6),(3044,'6 Cyl 7.2L',7),(3045,'FL8084S',6),(3046,'FLB104S',6),(3047,'FLB106S',6),(3048,'FLD11244S',6),(3049,'FLL08686S',6),(3050,'MB Line',4),(3051,'XB Line',4),(3052,'Sierra SLS',6),(3053,'W3500 Forward',4),(3054,'Odyssey',4),(3055,'Accent',4),(3056,'4700LPX',4),(3057,'FRR',4),(3058,'XJR',4),(3059,'Orvis',6),(3060,'Rio Grande',6),(3061,'T300',4),(3062,'Sportage',4),(3063,'25th Anniversary Edition',6),(3064,'County Classic',6),(3065,'Diamond Anniversary',6),(3066,'Spinnaker Edition',6),(3067,'LE600',6),(3068,'LXE',6),(3069,'Precedia',6),(3070,'Millenia',4),(3071,'C36 AMG',4),(3072,'E300',4),(3073,'Mystique',4),(3074,'GS Spree',6),(3075,'LS Young America',6),(3076,'Spyder SL',6),(3077,'Spyder VR-4',6),(3078,'Royale LSS',6),(3079,'Aurora',4),(3080,'385',4),(3081,'Sunfire',4),(3082,'Outback',6),(3083,'Brighton',6),(3084,'Esteem',4),(3085,'GLX Plus',6),(3086,'Avalon',4),(3087,'One-Ton DLX',6),(3088,'Tacoma',4),(3089,'Cabrio',4),(3090,'Camper',6),(3091,'Transport',6),(3092,'Celebration Edition',6),(3093,'City',6),(3094,'GTI VR6',6),(3095,'GLX VR6',6),(3096,'T-5R',6),(3097,'1996',1),(3098,'RL',4),(3099,'A4',4),(3100,'A4 Quattro',4),(3101,'328i',4),(3102,'328is',4),(3103,'Z3',4),(3104,'Olympic Gold Edition',6),(3105,'Estate Wagon Collector\'s Edition',6),(3106,'Limited Collector\'s Edition',6),(3107,'Express 1500',4),(3108,'Express 2500',4),(3109,'Express 3500',4),(3110,'JXi',6),(3111,'A9513',4),(3112,'AT9513',4),(3113,'Econoline Super Duty',4),(3114,'L8513',4),(3115,'L9513',4),(3116,'LT8513',4),(3117,'LT9513',4),(3118,'8 Cyl 3.4L',7),(3119,'Century Class',4),(3120,'FL112',4),(3121,'FL11242S',6),(3122,'FL11242ST',6),(3123,'FL11262S',6),(3124,'FL11264S',6),(3125,'FL11264ST',6),(3126,'FLC112104S',6),(3127,'FLD112104S',6),(3128,'FLD120104S',6),(3129,'FLL086106S',6),(3130,'FS65',4),(3131,'V Line',4),(3132,'Savana 1500',4),(3133,'Savana 2500',4),(3134,'Savana 3500',4),(3135,'Value Package',6),(3136,'HX',6),(3137,'I30',4),(3138,'1552SC',4),(3139,'3000RE',4),(3140,'9800 SBA',4),(3141,'G4700',4),(3142,'Genesis RE',4),(3143,'Hombre',4),(3144,'Oasis',4),(3145,'SD',6),(3146,'SE7',6),(3147,'HSE',6),(3148,'LX450',4),(3149,'Anniversary',6),(3150,'Spyder GS',6),(3151,'Spyder GST',6),(3152,'FE639',6),(3153,'FE-HD',4),(3154,'FE-SP',4),(3155,'FE649',6),(3156,'FH211',6),(3157,'FK617',6),(3158,'FM-HR',4),(3159,'FM617',6),(3160,'FM-MR',4),(3161,'FM-SP',4),(3162,'FM657',6),(3163,'FM-SR',4),(3164,'LSS',4),(3165,'Breeze',4),(3166,'Carrera 4S',6),(3167,'JLX Sport',6),(3168,'JX Sport',6),(3169,'X-90',4),(3170,'RAV4',4),(3171,'TDI',6),(3172,'Trek',6),(3173,'ACL',4),(3174,'ACL42',6),(3175,'ACL64',6),(3176,'6 Cyl 12.1L',7),(3177,'ACL64T',6),(3178,'ACL84',6),(3179,'WAH',4),(3180,'WAH62T',6),(3181,'WAH64',6),(3182,'WC',4),(3183,'WCA42',6),(3184,'WCA42T',6),(3185,'WCA62T',6),(3186,'WCA64',6),(3187,'WCA64T',6),(3188,'WG',4),(3189,'WG42',6),(3190,'WG42T',6),(3191,'WG64',6),(3192,'WG64T',6),(3193,'WH',4),(3194,'WHEB64T',6),(3195,'WHL42T',6),(3196,'WHL64T',6),(3197,'WHR42T',6),(3198,'WHR64',6),(3199,'WHR64T',6),(3200,'WI',4),(3201,'WIA42T',6),(3202,'WIA62T',6),(3203,'WIA64',6),(3204,'WIA64T',6),(3205,'WX',4),(3206,'WX42',6),(3207,'WX42T',6),(3208,'WX62',6),(3209,'WX64',6),(3210,'WX64T',6),(3211,'WX84',6),(3212,'1997',1),(3213,'Type R',6),(3214,'A8',4),(3215,'A8 Quattro',4),(3216,'Catera',4),(3217,'C6500 Kodiak',4),(3218,'C7500 Kodiak',4),(3219,'T6500',4),(3220,'F6B042',6),(3221,'T7500',4),(3222,'F7B042',6),(3223,'F7B064',6),(3224,'Venture',4),(3225,'Laramie',6),(3226,'SS/T 5.9L',6),(3227,'A9522',4),(3228,'AT9522',4),(3229,'10 Cyl 6.8L',7),(3230,'Lariat',6),(3231,'F-250 HD',4),(3232,'L8501',4),(3233,'L9501',4),(3234,'LT8501',4),(3235,'LT9501',4),(3236,'LT9522',4),(3237,'CST112',6),(3238,'FL11262ST',6),(3239,'FL11284S',6),(3240,'FL11284ST',6),(3241,'FL60GL',6),(3242,'FL8062ST',6),(3243,'FL80GL',6),(3244,'MT35',4),(3245,'Sierra GT',6),(3246,'C6500 Topkick',4),(3247,'C7500 Topkick',4),(3248,'CR-V',4),(3249,'Type SH',6),(3250,'Tiburon',4),(3251,'QX4',4),(3252,'3000FE',4),(3253,'Ltd',6),(3254,'XK8',4),(3255,'TJ',4),(3256,'T2000',4),(3257,'XD',6),(3258,'Kensington',6),(3259,'Vitesse',6),(3260,'STO',6),(3261,'C230',4),(3262,'Mountaineer',4),(3263,'DE',6),(3264,'Montero Sport',4),(3265,'Prowler',4),(3266,'Boxster',4),(3267,'Outback Limited',6),(3268,'JS Sport',6),(3269,'CE',6),(3270,'Jazz',6),(3271,'K2',6),(3272,'T-5',6),(3273,'S90',4),(3274,'V90',4),(3275,'WAH42',6),(3276,'WHL64',6),(3277,'WHLB64T',6),(3278,'1998',1),(3279,'323i',4),(3280,'323is',4),(3281,'M Roadster',6),(3282,'Z28 SS',6),(3283,'ACR',6),(3284,'GT2',6),(3285,'R/T-10',6),(3286,'SVT',6),(3287,'ZX2 Cool Coupe',6),(3288,'ZX2 Hot Coupe',6),(3289,'L8511',4),(3290,'L9511',4),(3291,'L9522',4),(3292,'LT8511',4),(3293,'LT9511',4),(3294,'EV',6),(3295,'SE Comfort',6),(3296,'3.0L',6),(3297,'FL6044S',6),(3298,'FLD11244ST',6),(3299,'FLD11286ST',6),(3300,'FLD120124S',6),(3301,'FLD12044ST',6),(3302,'FLD12066ST',6),(3303,'MT45',4),(3304,'Envoy',6),(3305,'FA1517',4),(3306,'4 Cyl 5.3L',7),(3307,'FB1817',4),(3308,'FD2220',4),(3309,'6 Cyl 8.0L',7),(3310,'FE2620',4),(3311,'SG3325',4),(3312,'EV Plus',4),(3313,'9100 SBA',4),(3314,'4 Cyl 4.8L',7),(3315,'XJ8',4),(3316,'5.9 Limited',6),(3317,'50th Anniversary Edition',6),(3318,'GS400',4),(3319,'LX470',4),(3320,'Navigator',4),(3321,'CX600',6),(3322,'FDM700R',6),(3323,'C43 AMG',4),(3324,'CL500',4),(3325,'CLK320',4),(3326,'E430',4),(3327,'ML320',4),(3328,'SLK230',4),(3329,'Kompressor',6),(3330,'LS Premium',6),(3331,'FG639',6),(3332,'Frontier',4),(3333,'Intrigue',4),(3334,'Premiere',6),(3335,'6 Cyl 15.0L',7),(3336,'Expresso',6),(3337,'Style',6),(3338,'Montana',6),(3339,'Carrera S',6),(3340,'Forester',4),(3341,'GT Limited',6),(3342,'VE',6),(3343,'Sienna',4),(3344,'Pre Runner',6),(3345,'FE64T',6),(3346,'S70',4),(3347,'T5',6),(3348,'V70',4),(3349,'X/C AWD',6),(3350,'VN',4),(3351,'VN42T',6),(3352,'VN64T',6),(3353,'WAH64T',6),(3354,'WG86',6),(3355,'1999',1),(3356,'Alex Zanardi Edition',6),(3357,'M Coupe',6),(3358,'Escalade',4),(3359,'Trailblazer',6),(3360,'Xtreme',6),(3361,'Silverado 1500',4),(3362,'Silverado 2500',4),(3363,'300M',4),(3364,'Daewoo',3),(3365,'Lanos',4),(3366,'Leganza',4),(3367,'CDX',6),(3368,'Nubira',4),(3369,'SLT Plus',6),(3370,'Ram 1500 Van',4),(3371,'Ram 2500 Van',4),(3372,'Ram 3500 Van',4),(3373,'E-350 Super Duty',4),(3374,'E-450 Econoline Super Duty',4),(3375,'ZX2 S/R',6),(3376,'F-250 Super Duty',4),(3377,'F-350 Super Duty',4),(3378,'F-450 Super Duty',4),(3379,'F-550 Super Duty',4),(3380,'SEL',6),(3381,'Argosy',4),(3382,'FC70',4),(3383,'FC80',4),(3384,'FLC11244S',6),(3385,'FLD120106S',6),(3386,'FLD12044S',6),(3387,'MT55',4),(3388,'Sierra 1500',4),(3389,'Sierra 2500',4),(3390,'Denali',6),(3391,'5600i',4),(3392,'9900',4),(3393,'9900 SFA',4),(3394,'NPR-HD',4),(3395,'VehiCROSS',4),(3396,'6 Cyl 14.9L',7),(3397,'Series II',6),(3398,'Callaway',6),(3399,'RX300',4),(3400,'Troy Lee',6),(3401,'CLK430',4),(3402,'E55 AMG',4),(3403,'ML430',4),(3404,'Altra EV',4),(3405,'Alero',4),(3406,'45538',4),(3407,'Viggen',6),(3408,'45540',4),(3409,'Series 1',4),(3410,'L 30th Anniversary',6),(3411,'Limited 30th Anniversary',6),(3412,'SUS 30th Anniversary',6),(3413,'Grand Vitara',4),(3414,'JLX Plus',6),(3415,'JS Plus',6),(3416,'Vitara',4),(3417,'JX Plus',6),(3418,'Solara',4),(3419,'1800CS',4),(3420,'ACL44',6),(3421,'ACL66',6),(3422,'S80',4),(3423,'T6',6),(3424,'R AWD',6),(3425,'TT',4),(3426,'TT Quattro',4),(3427,'323Ci',4),(3428,'328Ci',4),(3429,'X5',4),(3430,'4.4i',6),(3431,'Z8',4),(3432,'DHS',6),(3433,'DTS',6),(3434,'Suburban 1500',4),(3435,'Suburban 2500',4),(3436,'Z71',6),(3437,'Value',6),(3438,'Warner Bros.',6),(3439,'W5T042',6),(3440,'E-450 Econoline Super Duty Stripped',4),(3441,'ZX2',6),(3442,'Excursion',4),(3443,'XL Fleet',6),(3444,'Harley-Davidson Edition',6),(3445,'F650',4),(3446,'F750',4),(3447,'Focus',4),(3448,'Sony Limited Edition',6),(3449,'ZTS',6),(3450,'ZX3',6),(3451,'SES',6),(3452,'SVG',6),(3453,'Columbia',4),(3454,'120',6),(3455,'FL112GL',6),(3456,'6 Cyl 6.4L',7),(3457,'4 Cyl 4.3L',7),(3458,'Diamond Edition',6),(3459,'Yukon XL 1500',4),(3460,'Yukon XL 2500',4),(3461,'Insight',4),(3462,'S2000',4),(3463,'Anniversary Edition',6),(3464,'3000IC',4),(3465,'5500i',4),(3466,'5900i',4),(3467,'9100i SBA',4),(3468,'9200i SBA',4),(3469,'9400i SBA',4),(3470,'9900i',4),(3471,'9900i SFA',4),(3472,'9900ix',4),(3473,'G5070',4),(3474,'S-Type',4),(3475,'XKR',4),(3476,'6 Cyl 15.8L',7),(3477,'Spectra',4),(3478,'HSK',6),(3479,'Millennium Edition',6),(3480,'ML55 AMG',4),(3481,'S430',4),(3482,'Guard',6),(3483,'Xterra',4),(3484,'GT1',6),(3485,'SE1',6),(3486,'SE2',6),(3487,'Roadster S',6),(3488,'2.3t',6),(3489,'Gary Fisher',6),(3490,'LS1',4),(3491,'LS2',4),(3492,'LW1',4),(3493,'LW2',4),(3494,'JLS',6),(3495,'JLS Plus',6),(3496,'Echo',4),(3497,'MR2 Spyder',4),(3498,'Tundra',4),(3499,'GTI GLS',6),(3500,'GTI GLX',6),(3501,'4 Motion',6),(3502,'S40',4),(3503,'GLT SE',6),(3504,'V40',4),(3505,'X/C AWD SE',6),(3506,'VNL',4),(3507,'VNM',4),(3508,'2001',1),(3509,'Type-S',6),(3510,'MDX',4),(3511,'Allroad Quattro',4),(3512,'S8',4),(3513,'325Ci',4),(3514,'325xi',4),(3515,'330Ci',4),(3516,'330i',4),(3517,'330xi',4),(3518,'3.0i',6),(3519,'8 Cyl 8.1L',7),(3520,'HD',6),(3521,'Z06',6),(3522,'Silverado 1500 HD',4),(3523,'Silverado 2500 HD',4),(3524,'Silverado 3500',4),(3525,'PT Cruiser',4),(3526,'Escape',4),(3527,'Explorer Sport',4),(3528,'Explorer Sport Trac',4),(3529,'King Ranch',6),(3530,'Street',6),(3531,'GT Bullitt',6),(3532,'Edge',6),(3533,'SE Sport',6),(3534,'6 Cyl 8.9L',7),(3535,'6 Cyl 12.8L',7),(3536,'CST120',6),(3537,'Classic XL',4),(3538,'Condor',4),(3539,'Coronado',4),(3540,'132',6),(3541,'FB65',4),(3542,'FL5044S',6),(3543,'FL8044ST',6),(3544,'FLD11266ST',6),(3545,'FLD12086S',6),(3546,'FLD12086ST',6),(3547,'FLD132',4),(3548,'Sport Chassis',4),(3549,'XC',4),(3550,'XC Straight Rail',4),(3551,'C3',6),(3552,'Sierra 1500 HD',4),(3553,'Sierra 2500 HD',4),(3554,'Sierra 3500',4),(3555,'Sierra C3',4),(3556,'Santa Fe',4),(3557,'XG300',4),(3558,'9900ix SFA',4),(3559,'S V6',6),(3560,'Rodeo Sport',4),(3561,'Silverstone',6),(3562,'Magentis',4),(3563,'Rio',4),(3564,'Series II LE',6),(3565,'Series II SD',6),(3566,'Series II SE',6),(3567,'GS430',4),(3568,'IS300',4),(3569,'LS430',4),(3570,'Cartier L',6),(3571,'Executive L',6),(3572,'CV500',6),(3573,'DS',6),(3574,'MP3',6),(3575,'Tribute',4),(3576,'C240',4),(3577,'C320',4),(3578,'CL55 AMG',4),(3579,'12 Cyl 5.8L',7),(3580,'CLK55 AMG',4),(3581,'S55 AMG',4),(3582,'SLK320',4),(3583,'Spyder GT',6),(3584,'FE-CA',4),(3585,'FE640',6),(3586,'4 Cyl 4.9L',7),(3587,'SE 20th Anniversary Edition',6),(3588,'CA',6),(3589,'Aztek',4),(3590,'L100',4),(3591,'L200',4),(3592,'L300',4),(3593,'LW200',4),(3594,'LW300',4),(3595,'L.L. Bean',6),(3596,'VDC',6),(3597,'JLS Plus SE',6),(3598,'JLX Plus SE',6),(3599,'XL-7',6),(3600,'XL-7 Limited',6),(3601,'XL-7 Plus',6),(3602,'XL-7 Touring',6),(3603,'Highlander',4),(3604,'Prius',4),(3605,'Sequoia',4),(3606,'S-Runner',6),(3607,'GLS 4 Motion',6),(3608,'GLX 4 Motion',6),(3609,'S60',4),(3610,'2.4T',6),(3611,'X/C',6),(3612,'VHD',4),(3613,'RSX',4),(3614,'ALMS Edition',6),(3615,'745Li',4),(3616,'745i',4),(3617,'4.6is',6),(3618,'Rendezvous',4),(3619,'CX Plus',6),(3620,'CXL Plus',6),(3621,'Escalade EXT',4),(3622,'Avalanche 1500',4),(3623,'North Face',6),(3624,'On Road Edition',6),(3625,'Avalanche 2500',4),(3626,'LS Sport',6),(3627,'SS 35th Anniversary Edition',6),(3628,'SS Dale Earnhardt Signature Edition',6),(3629,'ZR5',6),(3630,'Trailblazer EXT',4),(3631,'Dream',6),(3632,'Ec',6),(3633,'SXT',6),(3634,'SE Plus',6),(3635,'E-550 Econoline Super Duty',4),(3636,'ZTW',6),(3637,'ZX5',6),(3638,'Neiman Marcus',6),(3639,'FL112104S',6),(3640,'FL8084ST',6),(3641,'M2 100',4),(3642,'M2 106',4),(3643,'Sprinter 2500',4),(3644,'5 Cyl 2.7L',7),(3645,'Sprinter 3500',4),(3646,'Envoy XL',4),(3647,'HT',6),(3648,'SiR',6),(3649,'EX-L',6),(3650,'Hummer',3),(3651,'H1',4),(3652,'XG350',4),(3653,'I35',4),(3654,'4200',4),(3655,'4300',4),(3656,'4300LP',4),(3657,'4400',4),(3658,'4400LP',4),(3659,'7300',4),(3660,'7400',4),(3661,'7500',4),(3662,'8500',4),(3663,'Axiom',4),(3664,'X-Type',4),(3665,'Overland',6),(3666,'Liberty',4),(3667,'Cinco',6),(3668,'RX-V',6),(3669,'Sedona',4),(3670,'Freelander',4),(3671,'Sportcross',6),(3672,'SC430',4),(3673,'Blackwood',4),(3674,'CV700',6),(3675,'MV200',6),(3676,'MV300',6),(3677,'Protege5',4),(3678,'C32 AMG',4),(3679,'G500',4),(3680,'ML500',4),(3681,'SLK32 AMG',4),(3682,'Mini',3),(3683,'Cooper',4),(3684,'VR-X',6),(3685,'OZ Rally',6),(3686,'FE640W',6),(3687,'FG649',6),(3688,'SVE',6),(3689,'SE-R Spec V',6),(3690,'Arc',6),(3691,'Linear',6),(3692,'Saleen',3),(3693,'Vue',4),(3694,'TS',6),(3695,'WRX',6),(3696,'H6',6),(3697,'Aerio',4),(3698,'GTI 1.8T',6),(3699,'GTI 337',6),(3700,'W8',6),(3701,'2003',1),(3702,'RS6',4),(3703,'760Li',4),(3704,'Z4',4),(3705,'2.5i',6),(3706,'Alpina',6),(3707,'CTS',4),(3708,'Luxury Sport',6),(3709,'Escalade ESV',4),(3710,'WBH',6),(3711,'C4500 Kodiak',4),(3712,'C4C042',6),(3713,'C4E042',6),(3714,'C4U042',6),(3715,'C4V042',6),(3716,'C5C042',6),(3717,'C5E042',6),(3718,'C5U042',6),(3719,'C5V042',6),(3720,'C6C042',6),(3721,'C6V042',6),(3722,'C7C042',6),(3723,'C7E042',6),(3724,'C7V042',6),(3725,'C8500',4),(3726,'C8C042',6),(3727,'C8C064',6),(3728,'50th Anniversary Edition Pace Car',6),(3729,'LS Competition',6),(3730,'SS High Sport',6),(3731,'SS Pace Car',6),(3732,'SSR',4),(3733,'Pro-Am',6),(3734,'SRT-4',6),(3735,'SX 2.0',4),(3736,'SRT-10',6),(3737,'10 Cyl 8.3L',7),(3738,'E-150',4),(3739,'E-150 Club Wagon',4),(3740,'E-250',4),(3741,'E-350 Club Wagon',4),(3742,'E-450 Super Duty',4),(3743,'E-450 Super Duty Stripped Chassis',4),(3744,'E-550 Super Duty',4),(3745,'SVT Cobra 10th Anniversary',6),(3746,'James Bond Edition',6),(3747,'112',6),(3748,'FL11286S',6),(3749,'FLD120SD',4),(3750,'M2 112',4),(3751,'C4500 Topkick',4),(3752,'C5500 Topkick',4),(3753,'C6E042',6),(3754,'C8500 Topkick',4),(3755,'C8E042',6),(3756,'FD2320',4),(3757,'Hybrid',6),(3758,'Element',4),(3759,'Pilot',4),(3760,'H2',4),(3761,'HLD 150',4),(3762,'HMD 230',4),(3763,'HMD 260',4),(3764,'FX35',4),(3765,'FX45',4),(3766,'M45',4),(3767,'4200LP',4),(3768,'7600',4),(3769,'8600 SBA',4),(3770,'Ascender',4),(3771,'Rubicon',6),(3772,'Sorento',4),(3773,'SE3',6),(3774,'GX470',4),(3775,'Aviator',4),(3776,'Executive Protection Series',6),(3777,'i',6),(3778,'Shinsen',6),(3779,'Mazdaspeed',6),(3780,'12 Cyl 5.5L',7),(3781,'CLK500',4),(3782,'G55 AMG',4),(3783,'ML350',4),(3784,'SL55 AMG',4),(3785,'Spyder GTS',6),(3786,'Evolution',6),(3787,'Outlander',4),(3788,'FH210',6),(3789,'350Z',4),(3790,'Enthusiast',6),(3791,'Performance',6),(3792,'Track',6),(3793,'Murano',4),(3794,'6 Cyl 12.5L',7),(3795,'Vibe',4),(3796,'Cayenne',4),(3797,'Vector',6),(3798,'Ion',4),(3799,'1',6),(3800,'L SE',6),(3801,'Matrix',4),(3802,'XR',6),(3803,'XRS',6),(3804,'GTI 20th Anniversary',6),(3805,'XC70',4),(3806,'XC90',4),(3807,'2004',1),(3808,'TSX',4),(3809,'545i',4),(3810,'645Ci',4),(3811,'760i',4),(3812,'X3',4),(3813,'4.8is',6),(3814,'Rainier',4),(3815,'Protection Series',6),(3816,'SRX',4),(3817,'XLR',4),(3818,'Z66',6),(3819,'Aveo',4),(3820,'Colorado',4),(3821,'4 Cyl 2.8L',7),(3822,'5 Cyl 3.5L',7),(3823,'Sport LS',6),(3824,'Z71 LS',6),(3825,'Z85',6),(3826,'Z85 LS',6),(3827,'Epica',4),(3828,'Maxx',6),(3829,'Maxx LS',6),(3830,'Maxx LT',6),(3831,'Optra',4),(3832,'T6F042',6),(3833,'T7F042',6),(3834,'T8500',4),(3835,'F8B042',6),(3836,'F8B064',6),(3837,'W5500HD Tiltmaster',4),(3838,'Crossfire',4),(3839,'Sport Plus',6),(3840,'NBX',6),(3841,'XLS Sport',6),(3842,'FX4',6),(3843,'F-150 Heritage',4),(3844,'SVT Lightning',6),(3845,'Freestar',4),(3846,'Tremor',6),(3847,'Pacific Coast Roadster',6),(3848,'Business Class M2',4),(3849,'FL8066ST',6),(3850,'Canyon',4),(3851,'Z71 Fleet',6),(3852,'Z71 SL',6),(3853,'Z71 SLE',6),(3854,'Z85 SL',6),(3855,'Z85 SLE',6),(3856,'Envoy XUV',4),(3857,'W5500HD Forward',4),(3858,'QX56',4),(3859,'6 Cyl 9.3L',7),(3860,'6 Cyl 15.2L',7),(3861,'FXR',4),(3862,'Unlimited',6),(3863,'Amanti',4),(3864,'ES330',4),(3865,'RX330',4),(3866,'Ultimate',6),(3867,'Ultimate L',6),(3868,'CHN',4),(3869,'CHN600',6),(3870,'CXN',4),(3871,'CXN600',6),(3872,'RD600ST',6),(3873,'RX-8',4),(3874,'Endeavor',4),(3875,'Ralliart',6),(3876,'Sportback',6),(3877,'Sportback Ralliart',6),(3878,'Pathfinder Armada',4),(3879,'Titan',4),(3880,'GXP',6),(3881,'GT3',6),(3882,'S Special Edition',6),(3883,'Carrera GT',4),(3884,'10 Cyl 5.7L',7),(3885,'Red Line',6),(3886,'Scion',3),(3887,'xA',4),(3888,'xB',4),(3889,'WRX STI',6),(3890,'L 35th Anniversary',6),(3891,'H6 35th Anniversary Edition',6),(3892,'H6 VDC',6),(3893,'Forenza',4),(3894,'Swift+',4),(3895,'Verona',4),(3896,'XLE Limited',6),(3897,'GL TDI',6),(3898,'GLS TDI',6),(3899,'V8',6),(3900,'W12',6),(3901,'R32',4),(3902,'Touareg',4),(3903,'10 Cyl 4.9L',7),(3904,'2.5T',6),(3905,'2.5T AWD',6),(3906,'2005',1),(3907,'Allure',4),(3908,'CXS',6),(3909,'LaCrosse',4),(3910,'Terraza',4),(3911,'Special Value',6),(3912,'C4C044',6),(3913,'C4E044',6),(3914,'C5C044',6),(3915,'C5E044',6),(3916,'C8V042',6),(3917,'Cobalt',4),(3918,'Equinox',4),(3919,'Uplander',4),(3920,'4 Cyl 5.2L',7),(3921,'C SRT8',6),(3922,'SRT-6',6),(3923,'Dream Cruiser',6),(3924,'Street Cruiser',6),(3925,'SXT Special Edition',6),(3926,'XLT No Boundaries',6),(3927,'Adrenalin',6),(3928,'Five Hundred',4),(3929,'ZX4',6),(3930,'ZX4 ST',6),(3931,'ZXW',6),(3932,'Freestyle',4),(3933,'B2',4),(3934,'FLD120104ST',6),(3935,'XB Raised Rail',4),(3936,'XB Straight Rail',4),(3937,'XC Formed Rail',4),(3938,'XC Raised Rail',4),(3939,'4 Cyl 4.7L',7),(3940,'165',4),(3941,'185',4),(3942,'238',4),(3943,'6 Cyl 7.7L',7),(3944,'268',4),(3945,'308',4),(3946,'338',4),(3947,'EX Special Edition',6),(3948,'LX Special Edition',6),(3949,'Reverb',6),(3950,'Tucson',4),(3951,'7700',4),(3952,'CF500',4),(3953,'CF600',4),(3954,'CXT',4),(3955,'RXT',4),(3956,'HTR',4),(3957,'HVR',4),(3958,'Super V8',4),(3959,'Rocky Mountain Edition',6),(3960,'Unlimited Rubicon',6),(3961,'LX Anniversary Edition',6),(3962,'Spectra5',4),(3963,'LR3',4),(3964,'Appearance',6),(3965,'Signature L',6),(3966,'Signature Limited',6),(3967,'CT',4),(3968,'CT700',6),(3969,'SP23',6),(3970,'Shinka',6),(3971,'C55 AMG',4),(3972,'CL65 AMG',4),(3973,'CDI',6),(3974,'SL65 AMG',4),(3975,'SLK350',4),(3976,'SLK55 AMG',4),(3977,'SLR McLaren',4),(3978,'Ultimate Edition',6),(3979,'Mariner',4),(3980,'Convenience',6),(3981,'Platinum Edition',6),(3982,'Evolution MR',6),(3983,'Evolution RS',6),(3984,'FE120',4),(3985,'FE83D',6),(3986,'FE145',4),(3987,'FE84D',6),(3988,'FE145CC',4),(3989,'FE84W',6),(3990,'FE180',4),(3991,'FE85D',6),(3992,'FG140',4),(3993,'FG84D',6),(3994,'FK200',4),(3995,'FK61F',6),(3996,'FM260',4),(3997,'FM61F',6),(3998,'FM64F',6),(3999,'FM330',4),(4000,'FM65F',6),(4001,'Grand Touring',6),(4002,'Armada',4),(4003,'SE Off-Road',6),(4004,'Nismo Off-Road',6),(4005,'X-Trail',4),(4006,'Off-Road',6),(4007,'335',4),(4008,'G6',4),(4009,'SV6',6),(4010,'Pursuit',4),(4011,'Wave',4),(4012,'Wave5',4),(4013,'9-2X',4),(4014,'9-7x',4),(4015,'Relay',4),(4016,'tC',4),(4017,'Smart',3),(4018,'Fortwo',4),(4019,'Passion',6),(4020,'Pulse',6),(4021,'Pure',6),(4022,'XS L.L. Bean Edition',6),(4023,'XT Premium',6),(4024,'Outback Sport',6),(4025,'R L.L. Bean Edition',6),(4026,'VDC Limited',6),(4027,'XT Limited',6),(4028,'Reno',4),(4029,'X-Runner',6),(4030,'1800HD',4),(4031,'2300DH',4),(4032,'2300LP',4),(4033,'10 Cyl 5.0L',7),(4034,'T6 Premier',6),(4035,'V50',4),(4036,'2006',1),(4037,'A3',4),(4038,'A3 Quattro',4),(4039,'525xi',4),(4040,'530xi',4),(4041,'550i',4),(4042,'650i',4),(4043,'750Li',4),(4044,'750i',4),(4045,'Coupe 3.0si',6),(4046,'Roadster 3.0i',6),(4047,'Roadster 3.0si',6),(4048,'Lucerne',4),(4049,'Aveo5',4),(4050,'C8V064',6),(4051,'HHR',4),(4052,'Maxx LTZ',6),(4053,'Maxx SS',6),(4054,'Daytona R/T',6),(4055,'SRT8',6),(4056,'Adventurer',6),(4057,'TRX4',6),(4058,'Fleet',6),(4059,'Fusion',4),(4060,'LCF',4),(4061,'C450',6),(4062,'258',4),(4063,'258LP',4),(4064,'Ridgeline',4),(4065,'RT',6),(4066,'RTL',6),(4067,'RTS',6),(4068,'Alpha',6),(4069,'H3',4),(4070,'Azera',4),(4071,'M35',4),(4072,'4100',4),(4073,'4100 SBA',4),(4074,'HXR',4),(4075,'i-280',4),(4076,'i-350',4),(4077,'VDP Edition',6),(4078,'Portfolio',6),(4079,'Victory Edition',6),(4080,'Commander',4),(4081,'65th Anniversary Edition',6),(4082,'Rio5',4),(4083,'Supercharged',6),(4084,'Range Rover Sport',4),(4085,'IS250',4),(4086,'IS350',4),(4087,'RX400h',4),(4088,'Mark LT',4),(4089,'Designer Series',6),(4090,'CTP',4),(4091,'CTP700',6),(4092,'CXP',4),(4093,'CXP600',6),(4094,'LX-SV',6),(4095,'MX-5 Miata',4),(4096,'Club Spec',6),(4097,'C350',4),(4098,'CLK350',4),(4099,'CLS500',4),(4100,'CLS55 AMG',4),(4101,'E350',4),(4102,'R350',4),(4103,'R500',4),(4104,'S65 AMG',4),(4105,'SLK280',4),(4106,'Milan',4),(4107,'Evolution SE',6),(4108,'DuroCross',6),(4109,'FE140',4),(4110,'386',4),(4111,'Solstice',4),(4112,'Torrent',4),(4113,'Cayman',4),(4114,'2.0T',6),(4115,'4.2i',6),(4116,'5.3i',6),(4117,'B9 Tribeca',4),(4118,'X L.L. Bean Edition',6),(4119,'WRX Limited',6),(4120,'WRX TR',6),(4121,'GT spec.B',6),(4122,'2.5i Limited',6),(4123,'R VDC Limited',6),(4124,'SX Premium',6),(4125,'XSport',6),(4126,'Hybrid Limited',6),(4127,'Yaris',4),(4128,'Value Edition',6),(4129,'2.4i',6),(4130,'6 Cyl 16.1L',7),(4131,'VT',4),(4132,'Ocean Race',6),(4133,'V8 Ocean Race',6),(4134,'2007',1),(4135,'RDX',4),(4136,'Q7',4),(4137,'RS4',4),(4138,'10 Cyl 5.2L',7),(4139,'328xi',4),(4140,'335i',4),(4141,'335xi',4),(4142,'Alpina B7',4),(4143,'3.0si',6),(4144,'4.8i',6),(4145,'Avalanche',4),(4146,'4 Cyl 2.9L',7),(4147,'5 Cyl 3.7L',7),(4148,'LS Panel',6),(4149,'LT Panel',6),(4150,'Silverado 1500 Classic',4),(4151,'Silverado 1500 HD Classic',4),(4152,'Silverado 2500 HD Classic',4),(4153,'Silverado 3500 Classic',4),(4154,'Silverado 3500 HD',4),(4155,'Caliber',4),(4156,'Nitro',4),(4157,'SEL Plus',6),(4158,'EL Eddie Bauer',6),(4159,'EL Limited',6),(4160,'EL XLT',6),(4161,'Max Eddie Bauer',6),(4162,'Max Limited',6),(4163,'FX2',6),(4164,'Shelby GT',6),(4165,'Shelby GT500',6),(4166,'FLD120SDGL',6),(4167,'XC Lowered Rail',4),(4168,'Acadia',4),(4169,'Sierra 1500 Classic',4),(4170,'Sierra 1500 HD Classic',4),(4171,'Sierra 2500 HD Classic',4),(4172,'Sierra 3500 Classic',4),(4173,'Sierra 3500 HD',4),(4174,'Fit',4),(4175,'RTX',6),(4176,'Entourage',4),(4177,'Veracruz',4),(4178,'Journey',6),(4179,'MXT',4),(4180,'i-290',4),(4181,'i-370',4),(4182,'XK',4),(4183,'Compass',4),(4184,'Patriot',4),(4185,'Unlimited Sahara',6),(4186,'Unlimited X',6),(4187,'Rondo',4),(4188,'ES350',4),(4189,'GS350',4),(4190,'GS450h',4),(4191,'LS460',4),(4192,'RX350',4),(4193,'MKX',4),(4194,'MKZ',4),(4195,'L Luxury',6),(4196,'L Ultimate',6),(4197,'CX-7',4),(4198,'CX-9',4),(4199,'SV',6),(4200,'CL550',4),(4201,'CLK550',4),(4202,'CLK63 AMG',4),(4203,'CLS550',4),(4204,'CLS63 AMG',4),(4205,'E280',4),(4206,'Bluetec',6),(4207,'E550',4),(4208,'E63 AMG',4),(4209,'GL320',4),(4210,'GL450',4),(4211,'ML63 AMG',4),(4212,'R320',4),(4213,'R63 AMG',4),(4214,'S550',4),(4215,'SL550',4),(4216,'Nismo',6),(4217,'Versa',4),(4218,'G5',4),(4219,'GT3 RS',6),(4220,'Targa 4',6),(4221,'Targa 4S',6),(4222,'Aura',4),(4223,'Green Line',6),(4224,'Outlook',4),(4225,'Sky',4),(4226,'Spec',6),(4227,'WRX STI Limited',6),(4228,'2.5i Special Edition',6),(4229,'2.5i Basic',6),(4230,'SX4',4),(4231,'FJ Cruiser',4),(4232,'TRD Special Edition',6),(4233,'Eos',4),(4234,'Fahrenheit',6),(4235,'Golf City',4),(4236,'Jetta City',4),(4237,'V8 Sport',6),(4238,'2008',1),(4239,'Technology',6),(4240,'A5 Quattro',4),(4241,'R8',4),(4242,'S5',4),(4243,'128i',4),(4244,'135i',4),(4245,'528xi',4),(4246,'535xi',4),(4247,'X6',4),(4248,'xDrive35i',6),(4249,'xDrive50i',6),(4250,'Enclave',4),(4251,'Platinum',6),(4252,'427 Limited Edition Z06',6),(4253,'Classic LT',6),(4254,'TRX',6),(4255,'Ram 4500',4),(4256,'Ram 5500',4),(4257,'10 Cyl 8.4L',7),(4258,'SRT-10 ACR',6),(4259,'EL King Ranch',6),(4260,'Funkmaster Flex Edition',6),(4261,'THE 60TH ANNIVERSARY EDITION',6),(4262,'XTR',6),(4263,'L45',6),(4264,'L55',6),(4265,'Bullitt',6),(4266,'Shelby GT500KR',6),(4267,'Taurus X',4),(4268,'Cascadia',4),(4269,'6 Cyl 14.8L',7),(4270,'XC Modular Rail',4),(4271,'258ALP',4),(4272,'268A',4),(4273,'338CT',4),(4274,'358',4),(4275,'LX-P',6),(4276,'LX-S',6),(4277,'DX-G',6),(4278,'MUGEN Si',6),(4279,'SE-L',6),(4280,'CR',6),(4281,'Adventure',6),(4282,'GLS Sport',6),(4283,'EX35',4),(4284,'X Sport',6),(4285,'G37',4),(4286,'8500 TranStar',4),(4287,'8600 TranStar',4),(4288,'6 Cyl 12.4L',7),(4289,'ProStar',4),(4290,'Pro LA617',6),(4291,'Pro LA627 Premium',6),(4292,'Pro LA637 Limited',6),(4293,'Pro LF617',6),(4294,'Pro LF627 Premium',6),(4295,'Pro LF637 Limited',6),(4296,'WorkStar 7600',4),(4297,'WorkStar 7700',4),(4298,'North Edition',6),(4299,'T170',4),(4300,'T270',4),(4301,'T370',4),(4302,'T660',4),(4303,'LX Premium',6),(4304,'EX Convenience',6),(4305,'EX Sport',6),(4306,'EX Luxury',6),(4307,'EX Premium',6),(4308,'EX Power',6),(4309,'LX Convenience',6),(4310,'LX Luxury',6),(4311,'LR2',4),(4312,'Westminster Edition',6),(4313,'GS460',4),(4314,'IS F',4),(4315,'LS600h',4),(4316,'LX570',4),(4317,'CHU',4),(4318,'CHU600',6),(4319,'CXU',4),(4320,'CXU600',6),(4321,'GU7',4),(4322,'GU700',6),(4323,'GU8',4),(4324,'GU800',6),(4325,'LEU',4),(4326,'LEU600',6),(4327,'MRU',4),(4328,'MRU600',6),(4329,'C300',4),(4330,'4Matic Luxury',6),(4331,'4Matic Sport',6),(4332,'C63 AMG',4),(4333,'CL63 AMG',4),(4334,'Black Series',6),(4335,'GL550',4),(4336,'ML550',4),(4337,'R550',4),(4338,'S450',4),(4339,'S63 AMG',4),(4340,'Clubman',6),(4341,'S Clubman',6),(4342,'GT-P',6),(4343,'Spyder GT-P',6),(4344,'Evolution GSR',6),(4345,'FE125',4),(4346,'FK260',4),(4347,'FK62F',6),(4348,'Rogue',4),(4349,'PRO-4X',6),(4350,'HE',6),(4351,'365',4),(4352,'367',4),(4353,'384',4),(4354,'388',4),(4355,'389',4),(4356,'G8',4),(4357,'RS 60 Spyder',6),(4358,'S Limited Edition',6),(4359,'Aero XWD',6),(4360,'Turbo X',6),(4361,'Astra',4),(4362,'xD',4),(4363,'Passion Cabrio',6),(4364,'2.5 X',6),(4365,'2.5 XS',6),(4366,'2.5 XS Premium',6),(4367,'2.5 XT',6),(4368,'Sports 2.5 X',6),(4369,'Sports 2.5 XT',6),(4370,'3.0 R Limited',6),(4371,'2.5i L.L. Bean Edition',6),(4372,'2.5i Limited L.L. Bean Edition',6),(4373,'3.0 R',6),(4374,'Tribeca',4),(4375,'Tesla',3),(4376,'Trail Teams Special Edition',6),(4377,'Komfort',6),(4378,'VR6',6),(4379,'T5 R-Design',6),(4380,'3.2 Sport',6),(4381,'2009',1),(4382,'SH-AWD',6),(4383,'Q5',4),(4384,'Premium Plus',6),(4385,'Prestige',6),(4386,'TTS Quattro',4),(4387,'328i xDrive',4),(4388,'335d',4),(4389,'335i xDrive',4),(4390,'528i xDrive',4),(4391,'535i xDrive',4),(4392,'xDrive30i',6),(4393,'xDrive35d',6),(4394,'xDrive48i',6),(4395,'sDrive30i',6),(4396,'sDrive35i',6),(4397,'ZR1',6),(4398,'Express 4500',4),(4399,'SS Panel',6),(4400,'Traverse',4),(4401,'Signature Series',6),(4402,'Limited Hybrid',6),(4403,'Big Horn',6),(4404,'Lone Star',6),(4405,'Max King Ranch',6),(4406,'Cabela\'s',6),(4407,'Flex',4),(4408,'113',6),(4409,'Savana 4500',4),(4410,'Hybrid-L',6),(4411,'VP',6),(4412,'Championship Series',6),(4413,'H3T',4),(4414,'Genesis',4),(4415,'FX50',4),(4416,'LoneStar',4),(4417,'Pro LA647',6),(4418,'Pro LA657 Premium',6),(4419,'Pro LF647',6),(4420,'Pro LF657 Premium',6),(4421,'XF',4),(4422,'Premium Luxury',6),(4423,'X-S',6),(4424,'Borrego',4),(4425,'HSE Lux',6),(4426,'MKS',4),(4427,'3 Sport',4),(4428,'R3',6),(4429,'G550',4),(4430,'Bluetec 4Matic',6),(4431,'SL63 AMG',4),(4432,'SLK300',4),(4433,'John Cooper Works',6),(4434,'John Cooper Works Clubman',6),(4435,'Sport Edition',6),(4436,'Sport V6',6),(4437,'370Z',4),(4438,'Cube',4),(4439,'Krom',6),(4440,'GT-R',4),(4441,'1.6 Base',6),(4442,'G3',4),(4443,'G3 Wave',4),(4444,'S Porsche Design 2',6),(4445,'Griffin',6),(4446,'Brabus',6),(4447,'X Limited',6),(4448,'X Premium',6),(4449,'X Touring',6),(4450,'2.5 GT',6),(4451,'WRX Premium',6),(4452,'2.5i Touring',6),(4453,'3.0 R Premier',6),(4454,'Equator',4),(4455,'RMZ-4',6),(4456,'Venza',4),(4457,'Comfortline',6),(4458,'Trendline',6),(4459,'CC',4),(4460,'VR6 4Motion',6),(4461,'VR6 Sport',6),(4462,'Highline',6),(4463,'Passat CC',4),(4464,'Highline V6',6),(4465,'Sportline',6),(4466,'Routan',4),(4467,'Execline',6),(4468,'Tiguan',4),(4469,'2010',1),(4470,'iTech',6),(4471,'ZDX',4),(4472,'A5',4),(4473,'535i GT',4),(4474,'550i GT',4),(4475,'550i GT xDrive',4),(4476,'750Li xDrive',4),(4477,'750i xDrive',4),(4478,'xDrive28i',6),(4479,'ActiveHybrid',6),(4480,'CXL Special Edition',6),(4481,'Livery',6),(4482,'Hybrid Platinum',6),(4483,'LT Team Canada',6),(4484,'XFE',6),(4485,'Couture Edition',6),(4486,'Heat',6),(4487,'Mainstreet',6),(4488,'Rush',6),(4489,'Uptown',6),(4490,'Crew',6),(4491,'Detonator',6),(4492,'Shock',6),(4493,'SVT Raptor',6),(4494,'Transit Connect',4),(4495,'Terrain',4),(4496,'Denali Hybrid',6),(4497,'Accord Crosstour',4),(4498,'NHL SE',6),(4499,'Blue',6),(4500,'Touring GL',6),(4501,'Touring GLS',6),(4502,'Touring L',6),(4503,'Touring SE',6),(4504,'Genesis Coupe',4),(4505,'2.0T GT',6),(4506,'2.0T Premium',6),(4507,'2.0T R-Spec',6),(4508,'2.0T Track',6),(4509,'3.8 GT',6),(4510,'3.8 Grand Touring',6),(4511,'3.8 Track',6),(4512,'Pro LA667 Limited',6),(4513,'Pro LF667 Limited',6),(4514,'TerraStar',4),(4515,'XFR',4),(4516,'L Supercharged',6),(4517,'L Supersport',6),(4518,'Supersport',6),(4519,'S Limited',6),(4520,'Unlimited Sport',6),(4521,'Forte',4),(4522,'Forte Koup',4),(4523,'Soul',4),(4524,'!',6),(4525,'+',6),(4526,'2u',6),(4527,'4u',6),(4528,'4u Burner',6),(4529,'4u Retro',6),(4530,'4u SX',6),(4531,'LR4',4),(4532,'GX460',4),(4533,'Ultra Premium',6),(4534,'HS250h',4),(4535,'RX450h',4),(4536,'EcoBoost',6),(4537,'MKT',4),(4538,'TD700',4),(4539,'C250',4),(4540,'GL350',4),(4541,'GLK350',4),(4542,'ML450',4),(4543,'Hybrid 4Matic',6),(4544,'S400',4),(4545,'GS Sport',6),(4546,'Evolution MR Touring',6),(4547,'GTS Sportback',6),(4548,'Ralliart Sportback',6),(4549,'Titanium',6),(4550,'337',4),(4551,'S Transsyberia',6),(4552,'Panamera',4),(4553,'4S',6),(4554,'9-3X',4),(4555,'2.5i Premium',6),(4556,'WRX STI Special Edition',6),(4557,'2.5GT',6),(4558,'3.6R',6),(4559,'3.6R Limited',6),(4560,'3.6R Premium',6),(4561,'2.5i Sport',6),(4562,'Kizashi',4),(4563,'Crossover',6),(4564,'Sport S',6),(4565,'Sport SE',6),(4566,'Trail',6),(4567,'TDI Cup Edition',6),(4568,'SEL Premium',6),(4569,'XC60',4),(4570,'T6 R-Design',6),(4571,'2011',1),(4572,'TDI Premium',6),(4573,'TDI Premium Plus',6),(4574,'TDI Prestige',6),(4575,'1 Series M',4),(4576,'335is',4),(4577,'535i GT xDrive',4),(4578,'550i xDrive',4),(4579,'740Li',4),(4580,'Alpina B7 xDrive',4),(4581,'Alpina B7L',4),(4582,'Alpina B7L xDrive',4),(4583,'sDrive35is',6),(4584,'PPV',6),(4585,'Z06 Carbon',6),(4586,'Cruze',4),(4587,'Eco',6),(4588,'Volt',4),(4589,'SE Canada Value Package',6),(4590,'SXT Plus',6),(4591,'Canada Value Package',6),(4592,'R/T Road and Track',6),(4593,'Citadel',6),(4594,'EL XL',6),(4595,'Max XL',6),(4596,'Lariat Limited',6),(4597,'ELECTRIC',6),(4598,'XLT Premium',6),(4599,'6 Cyl 15.6L',7),(4600,'MCL',4),(4601,'198',4),(4602,'CR-Z',4),(4603,'Touring Elite',6),(4604,'Equus',4),(4605,'3.8 R-Spec',6),(4606,'GL Premium',6),(4607,'2.0T Limited',6),(4608,'IPL',6),(4609,'M37',4),(4610,'M56',4),(4611,'Pro LA677',6),(4612,'Pro LA687',6),(4613,'Pro LF677',6),(4614,'Pro LF687',6),(4615,'Reach',4),(4616,'Latitude',6),(4617,'Latitude X',6),(4618,'70th Anniversary',6),(4619,'Unlimited 70th Anniversary',6),(4620,'6 Cyl 12.9L',7),(4621,'T700',4),(4622,'Autobiography',6),(4623,'GT Limited Edition',6),(4624,'CT200h',4),(4625,'Yozora',6),(4626,'Sport GS',6),(4627,'Sport GX',6),(4628,'GS-SKY',6),(4629,'SLS AMG',4),(4630,'Cooper Countryman',4),(4631,'S ALL4',6),(4632,'ES Sportback',6),(4633,'GT Sportback',6),(4634,'SE Sportback',6),(4635,'Outlander Sport',4),(4636,'RVR',4),(4637,'Juke',4),(4638,'Leaf',4),(4639,'CrossCabriolet',6),(4640,'Silver Edition',6),(4641,'S Krom',6),(4642,'382',4),(4643,'587',4),(4644,'Carrera GTS',6),(4645,'GT2 RS',6),(4646,'GT3 RS 4.0',6),(4647,'S Hybrid',6),(4648,'Ram',3),(4649,'Laramie Longhorn',6),(4650,'Outdoorsman',6),(4651,'Tradesman',6),(4652,'4500',4),(4653,'5500',4),(4654,'Turbo4',6),(4655,'9-4X',4),(4656,'Turbo6',6),(4657,'Brabus Cabrio',6),(4658,'Electric Drive',6),(4659,'XT Touring',6),(4660,'JLX-L',6),(4661,'Sport SLS',6),(4662,'LE Anniversary Edition',6),(4663,'Sport 2.5',6),(4664,'R-Line',6),(4665,'TDI Comfortline',6),(4666,'TDI Highline',6),(4667,'Execline TDI',6),(4668,'VAH',4),(4669,'3.2 R-Design',6),(4670,'2012',1),(4671,'A-Spec',6),(4672,'A7 Quattro',4),(4673,'L W12',6),(4674,'12 Cyl 6.3L',7),(4675,'GT Spyder',6),(4676,'TT RS Quattro',4),(4677,'640i',4),(4678,'650i xDrive',4),(4679,'ActiveHybrid 5',4),(4680,'X1',4),(4681,'sDrive28i',6),(4682,'Leather',6),(4683,'Verano',4),(4684,'3.6L',6),(4685,'ZL1',6),(4686,'Captiva Sport',4),(4687,'Orlando',4),(4688,'Sonic',4),(4689,'SSV',6),(4690,'C Luxury',6),(4691,'R/T Classic',6),(4692,'Enforcer',6),(4693,'Crew Plus',6),(4694,'Special Service',6),(4695,'AVP',6),(4696,'Abarth',6),(4697,'Gucci',6),(4698,'Lounge',6),(4699,'Pop',6),(4700,'c Gucci',6),(4701,'c Lounge',6),(4702,'c Pop',6),(4703,'108SD',4),(4704,'114SD',4),(4705,'155',4),(4706,'4 Cyl 5.0L',7),(4707,'195',4),(4708,'195h',4),(4709,'HFP',6),(4710,'Si HFP',6),(4711,'Crosstour',4),(4712,'DX-A',6),(4713,'5.0 R-Spec',6),(4714,'Hybrid Premium',6),(4715,'Veloster',4),(4716,'M35h',4),(4717,'L Portfolio',6),(4718,'XKR-S',4),(4719,'Laredo E',6),(4720,'Laredo X',6),(4721,'Overland Summit',6),(4722,'Jet',6),(4723,'Limited Jet',6),(4724,'SX Luxury',6),(4725,'Forte5',4),(4726,'LX+',6),(4727,'4u Luxury',6),(4728,'Range Rover Evoque',4),(4729,'Dynamic',6),(4730,'GT Limited Edition 3',6),(4731,'LFA',4),(4732,'10 Cyl 4.8L',7),(4733,'GU4',4),(4734,'GU400',6),(4735,'GU5',4),(4736,'GU500',6),(4737,'Avantgarde',6),(4738,'Grand Edition',6),(4739,'SLK250',4),(4740,'Coupe John Cooper Works',6),(4741,'Coupe S',6),(4742,'Roadster John Cooper Works',6),(4743,'Spyder GS Sport',6),(4744,'Spyder SE',6),(4745,'i-MiEV',4),(4746,'Canter FE125',4),(4747,'FEC52S',6),(4748,'Canter FE160',4),(4749,'FEC72S',6),(4750,'FEC72W',6),(4751,'Canter FE180',4),(4752,'FEC92S',6),(4753,'Canter FG4X4',4),(4754,'FGB72S',6),(4755,'Black Edition',6),(4756,'NV1500',4),(4757,'NV2500',4),(4758,'NV3500',4),(4759,'1.6 S',6),(4760,'1.6 SL',6),(4761,'1.6 SV',6),(4762,'1.8 S',6),(4763,'1.8 SL',6),(4764,'Carrera 4 GTS',6),(4765,'S Black Edition',6),(4766,'Tradesman HD',6),(4767,'iQ',4),(4768,'Sport Limited',6),(4769,'Sport Premium',6),(4770,'Ultimate Adventure Edition',6),(4771,'Urban',6),(4772,'JE',6),(4773,'SX4 Crossover',4),(4774,'Model S',4),(4775,'Hybrid LE',6),(4776,'Hybrid XLE',6),(4777,'Five',6),(4778,'Four',6),(4779,'Three',6),(4780,'Two',6),(4781,'Prius C',4),(4782,'One',6),(4783,'Prius Plug-In',4),(4784,'Prius V',4),(4785,'AWD V6',6),(4786,'Lux Limited',6),(4787,'Lux Plus',6),(4788,'Comfortline TDI',6),(4789,'Highline TDI',6),(4790,'Golf R',4),(4791,'TDI SE',6),(4792,'TDI SEL',6),(4793,'Trendline TDI',6),(4794,'TDI Executive',6),(4795,'TDI Lux',6),(4796,'TDI Sport',6),(4797,'V6 Executive',6),(4798,'V6 Lux',6),(4799,'V6 Sport',6),(4800,'2013',1),(4801,'ILX',4),(4802,'Tech',6),(4803,'V6 Tech',6),(4804,'A4 allroad',4),(4805,'Allroad',4),(4806,'Hybrid Prestige',6),(4807,'RS5',4),(4808,'135is',4),(4809,'320i xDrive',4),(4810,'640i Gran Coupe',4),(4811,'650i Gran Coupe',4),(4812,'650i xDrive Gran Coupe',4),(4813,'740Li xDrive',4),(4814,'ActiveHybrid 3',4),(4815,'ActiveHybrid 7',4),(4816,'Encore',4),(4817,'ATS',4),(4818,'XTS',4),(4819,'Black Diamond LS',6),(4820,'Black Diamond LT',6),(4821,'Black Diamond LTZ',6),(4822,'427',6),(4823,'Spark',4),(4824,'Trax',4),(4825,'SRT8 Core',6),(4826,'R/T Rallye',6),(4827,'Abarth Cabrio',6),(4828,'C-Max',4),(4829,'Energi SEL',6),(4830,'Hybrid SE',6),(4831,'Hybrid SEL',6),(4832,'Energi SE',6),(4833,'Energi Titanium',6),(4834,'SE Hybrid',6),(4835,'Titanium Hybrid',6),(4836,'Police Interceptor Sedan',4),(4837,'Police Interceptor Utility',4),(4838,'122SD',4),(4839,'S2C',4),(4840,'Natural Gas',6),(4841,'Elantra Coupe',4),(4842,'Elantra GT',4),(4843,'Santa Fe Sport',4),(4844,'Santa Fe XL',4),(4845,'EX37',4),(4846,'FX37',4),(4847,'JX35',4),(4848,'Durastar',4),(4849,'3.0L AWD',6),(4850,'XFR-S',4),(4851,'North',6),(4852,'SRT',6),(4853,'K270',4),(4854,'K370',4),(4855,'T680',4),(4856,'LX Plus',6),(4857,'Hybrid EX',6),(4858,'Hybrid LX',6),(4859,'SXL',6),(4860,'ES300h',4),(4861,'F Sport',6),(4862,'CX-5',4),(4863,'E400',4),(4864,'G63 AMG',4),(4865,'GL63 AMG',4),(4866,'GLK250',4),(4867,'John Cooper Works GP',6),(4868,'John Cooper Works ALL4',6),(4869,'Cooper Paceman',4),(4870,'Desert Runner',6),(4871,'NV200',4),(4872,'FE+S',6),(4873,'FE+SV',6),(4874,'1.6 S Plus',6),(4875,'579',4),(4876,'HFE',6),(4877,'FR-S',4),(4878,'BRZ',4),(4879,'Sport-tech',6),(4880,'X Convenience',6),(4881,'2.5i Convenience',6),(4882,'XV Crosstrek',4),(4883,'XLE Premium',6),(4884,'XLE Touring',6),(4885,'VR6 4Motion Executive',6),(4886,'Hybrid Comfortline',6),(4887,'Hybrid Highline',6),(4888,'Hybrid Trendline',6),(4889,'2014',1),(4890,'RLX',4),(4891,'Sport Hybrid SH-AWD',6),(4892,'L TDI',6),(4893,'3.0T',6),(4894,'RS7',4),(4895,'SQ5',4),(4896,'Progressiv',6),(4897,'Technik',6),(4898,'228i',4),(4899,'328d',4),(4900,'328d xDrive',4),(4901,'328i GT xDrive',4),(4902,'335i GT xDrive',4),(4903,'428i',4),(4904,'428i xDrive',4),(4905,'435i',4),(4906,'435i xDrive',4),(4907,'535d',4),(4908,'535d xDrive',4),(4909,'640i xDrive',4),(4910,'640i xDrive Gran Coupe',4),(4911,'M235i',4),(4912,'M6 Gran Coupe',4),(4913,'i3',4),(4914,'Range Extender',6),(4915,'i8',4),(4916,'3 Cyl 1.5L',7),(4917,'Vsport',6),(4918,'Vsport Premium',6),(4919,'ELR',4),(4920,'Vsport Platinum',6),(4921,'Z/28',6),(4922,'Impala Limited',4),(4923,'Spark EV',4),(4924,'Rallye Redline',6),(4925,'30th Anniversary Edition',6),(4926,'American Value Package',6),(4927,'SE 30th Anniversary Edition',6),(4928,'SXT 30th Anniversary Edition',6),(4929,'Crossroad',6),(4930,'500L',4),(4931,'Easy',6),(4932,'Trekking',6),(4933,'Energi SE Luxury',6),(4934,'Special Service Police Sedan',4),(4935,'S2G',4),(4936,'195DC',4),(4937,'195h DC',4),(4938,'Hybrid EX-L',6),(4939,'Hybrid Touring',6),(4940,'Plug-In',6),(4941,'3.8 Ultimate',6),(4942,'RE:FLEX',6),(4943,'Turbo R-Spec',6),(4944,'Q50',4),(4945,'Hybrid Sport',6),(4946,'Q60',4),(4947,'Premier Edition',6),(4948,'Premium Sport',6),(4949,'Q70',4),(4950,'QX50',4),(4951,'QX60',4),(4952,'QX70',4),(4953,'QX80',4),(4954,'F-Type',4),(4955,'V8 S',6),(4956,'Trailhawk',6),(4957,'T880',4),(4958,'Cadenza',4),(4959,'Hybrid EX Premium',6),(4960,'SX Turbo',6),(4961,'SXL Turbo',6),(4962,'Autobiography Black',6),(4963,'C F Sport',6),(4964,'Sportdesign',6),(4965,'I Sport',6),(4966,'I Touring',6),(4967,'i Grand Touring',6),(4968,'i SV',6),(4969,'s Grand Touring',6),(4970,'s Touring',6),(4971,'B Electric Drive',4),(4972,'CLA250',4),(4973,'CLA45 AMG',4),(4974,'CLS63 AMG S',4),(4975,'E250',4),(4976,'E63 AMG S',4),(4977,'Track Edition',6),(4978,'Nismo RS',6),(4979,'Taxi',6),(4980,'Platinum Hybrid',6),(4981,'SL Hybrid',6),(4982,'SV Hybrid',6),(4983,'Rogue Select',4),(4984,'Versa Note',4),(4985,'567',4),(4986,'Diesel Platinum Edition',6),(4987,'4S Executive',6),(4988,'S E-Hybrid',6),(4989,'Turbo Executive',6),(4990,'Turbo S Executive',6),(4991,'Laramie Limited',6),(4992,'ProMaster 1500',4),(4993,'ProMaster 2500',4),(4994,'ProMaster 3500',4),(4995,'2.0XT Limited',6),(4996,'2.0XT Premium',6),(4997,'2.0XT Touring',6),(4998,'Tsurugi Edition',6),(4999,'LE Eco',6),(5000,'Le Eco Plus',6),(5001,'LE Plus',6),(5002,'1794 Edition',6),(5003,'Driver\'s Edition',6),(5004,'Comfort',6),(5005,'Comfort TDI',6),(5006,'Hybrid SEL Premium',6),(5007,'TDI SEL Premium',6),(5008,'TDI R-Line',6),(5009,'V6 R-Line',6),(5010,'VNX',4),(5011,'2015',1),(5012,'TLX',4),(5013,'TDI Komfort',6),(5014,'TDI Progressiv',6),(5015,'TDI Technik',6),(5016,'Q3',4),(5017,'Q3 Quattro',4),(5018,'TDI Vorsprung',6),(5019,'Vorsprung',6),(5020,'228i xDrive',4),(5021,'428i Gran Coupe',4),(5022,'428i xDrive Gran Coupe',4),(5023,'435i Gran Coupe',4),(5024,'435i xDrive Gran Coupe',4),(5025,'740Ld xDrive',4),(5026,'Alpina B6 xDrive Gran Coupe',4),(5027,'M235i xDrive',4),(5028,'M4',4),(5029,'xDrive28d',6),(5030,'X4',4),(5031,'City Express',4),(5032,'C Platinum',6),(5033,'R/T Plus',6),(5034,'SRT 392',6),(5035,'SRT Hellcat',6),(5036,'Scat Pack',6),(5037,'R/T Road & Track',6),(5038,'R/T Scat Pack',6),(5039,'Urbana',6),(5040,'EL Platinum',6),(5041,'EcoBoost Premium',6),(5042,'GT 50 Years Limited Edition',6),(5043,'GT Premium',6),(5044,'Shelby GT350',6),(5045,'Shelby GT350R',6),(5046,'Transit-150',4),(5047,'5 Cyl 3.2L',7),(5048,'Transit-250',4),(5049,'Transit-350',4),(5050,'Transit-350 HD',4),(5051,'XBA',4),(5052,'ARBOC',6),(5053,'Yukon XL',4),(5054,'Limited 2.0T',6),(5055,'Sport 2.0T',6),(5056,'Ultimate 2.0T',6),(5057,'Fuel Cell',6),(5058,'Q40',4),(5059,'Q70L',4),(5060,'NPR-XD',4),(5061,'2.0T Luxury',6),(5062,'3.0L AWD Luxury',6),(5063,'3.0L AWD Sport',6),(5064,'Altitude',6),(5065,'High Altitude',6),(5066,'K900',4),(5067,'V8 Elite',6),(5068,'V8 Luxury',6),(5069,'V8 Premium',6),(5070,'Soul EV',4),(5071,'Discovery Sport',4),(5072,'Autobiography Dynamic',6),(5073,'SVR',6),(5074,'NX200t',4),(5075,'NX300h',4),(5076,'RC F',4),(5077,'RC350',4),(5078,'MKC',4),(5079,'Black Label',6),(5080,'Black Label Hybrid',6),(5081,'C400',4),(5082,'Edition 507',6),(5083,'C63 AMG S',4),(5084,'CLS400',4),(5085,'GLA250',4),(5086,'GLA45 AMG',4),(5087,'ML250',4),(5088,'ML400',4),(5089,'S550e',4),(5090,'SL400',4),(5091,'Evolution Final Edition',6),(5092,'SE Limited Edition',6),(5093,'SE Limited Edition Sportback',6),(5094,'RF',6),(5095,'Nismo Tech',6),(5096,'Sport Tech',6),(5097,'Touring Sport',6),(5098,'918 Spyder',4),(5099,'Macan',4),(5100,'Rebel',6),(5101,'ProMaster City',4),(5102,'Tradesman SLT',6),(5103,'Aozora Edition',6),(5104,'Series.Blue',6),(5105,'3.6R Touring',6),(5106,'Launch Edition',6),(5107,'TRD Pro',6),(5108,'Hybrid XLE Premium',6),(5109,'Hybrid XLE Touring',6),(5110,'XLE Touring SE',6),(5111,'XSE',6),(5112,'Persona Series',6),(5113,'Fleet Edition',6),(5114,'Executive 4 Motion',6),(5115,'Final Edition',6),(5116,'Autobahn',6),(5117,'TDI S',6),(5118,'Golf Sportwagen',4),(5119,'GLI Autobahn',6),(5120,'GLI SE',6),(5121,'GLI SEL',6),(5122,'Sportline TDI',6),(5123,'e-Golf',4),(5124,'T5 Platinum',6),(5125,'T5 Premier',6),(5126,'T5 Premier Plus',6),(5127,'T6 Platinum',6),(5128,'T6 Polestar',6),(5129,'T6 Premier Plus',6),(5130,'T6 R-Design Platinum',6),(5131,'V60',4),(5132,'V60 Cross Country',4),(5133,'T6 Ocean Race',6),(5134,'2016',1),(5135,'A3 Sportback e-tron',4),(5136,'TDI Premium Plus S-Line',6),(5137,'TDI Prestige S-Line',6),(5138,'330e',4),(5139,'340i',4),(5140,'340i xDrive',4),(5141,'Alpina B6 Gran Coupe',4),(5142,'M2',4),(5143,'M40i',6),(5144,'xDrive40e',6),(5145,'Cascada',4),(5146,'Sport Touring',6),(5147,'Envision',4),(5148,'CT6',4),(5149,'Cruze Limited',4),(5150,'LCF 3500',4),(5151,'LCF 3500HD',4),(5152,'LCF 4500',4),(5153,'Malibu Limited',4),(5154,'Suburban 3500 HD',4),(5155,'392 HEMI Scat Pack Shaker',6),(5156,'R/T Plus Shaker',6),(5157,'R/T Shaker',6),(5158,'SXT Sport',6),(5159,'c Easy',6),(5160,'Urbana Trekking',6),(5161,'500X',4),(5162,'Trekking Plus',6),(5163,'EL SSV',6),(5164,'PTV',6),(5165,'EX-T',6),(5166,'HR-V',4),(5167,'Plug-In Hybrid',6),(5168,'Plug-In Hybrid Limited',6),(5169,'Plug-In Hybrid Ultimate',6),(5170,'Rally Edition',6),(5171,'Red Sport 400',6),(5172,'Project 7',6),(5173,'R-Sport',6),(5174,'75th Anniversary',6),(5175,'Dawn Of Justice',6),(5176,'Unlimited 75th Anniversary',6),(5177,'Unlimited Sport S',6),(5178,'V6 Luxury',6),(5179,'V6 Premium',6),(5180,'LX 1.6T',6),(5181,'SX Limited',6),(5182,'SV Autobiography',6),(5183,'HSE Dynamic',6),(5184,'SE Premium',6),(5185,'HST',6),(5186,'Supercharged Dynamic',6),(5187,'GS F',4),(5188,'GS200t',4),(5189,'IS200t',4),(5190,'RC200t',4),(5191,'RC300',4),(5192,'Reserve',6),(5193,'Select',6),(5194,'L Reserve',6),(5195,'L Select',6),(5196,'LR600',4),(5197,'CX-3',4),(5198,'GS-L',6),(5199,'AMG GT S',4),(5200,'B250e',4),(5201,'C350e',4),(5202,'C450 AMG',4),(5203,'G65 AMG',4),(5204,'GLC300',4),(5205,'GLE300d',4),(5206,'GLE350',4),(5207,'GLE350d',4),(5208,'GLE400',4),(5209,'GLE450 AMG',4),(5210,'GLE550',4),(5211,'GLE550e',4),(5212,'GLE63 AMG',4),(5213,'GLE63 AMG S',4),(5214,'Maybach S600',4),(5215,'Metris',4),(5216,'Cooper Clubman',4),(5217,'Platinum Reserve',6),(5218,'Titan XD',4),(5219,'Carrera 4 Black Edition',6),(5220,'Carrera Black Edition',6),(5221,'Targa 4 GTS',6),(5222,'GT4',6),(5223,'4 Edition',6),(5224,'Edition',6),(5225,'Exclusive',6),(5226,'iA',4),(5227,'iM',4),(5228,'3 Cyl 0.9L',7),(5229,'Prime',6),(5230,'Proxy',6),(5231,'Series.HyperBlue',6),(5232,'Crosstrek',4),(5233,'70',6),(5234,'70D',6),(5235,'85',6),(5236,'85D',6),(5237,'90D',6),(5238,'P85D',6),(5239,'P90D',6),(5240,'Model X',4),(5241,'P90D Founder',6),(5242,'P90D Signature',6),(5243,'Hybrid XLE Plus',6),(5244,'XLE Plus',6),(5245,'Mirai',4),(5246,'Four Touring',6),(5247,'Three Touring',6),(5248,'Two Eco',6),(5249,'TRD Off-Road',6),(5250,'TRD Sport',6),(5251,'Denim',6),(5252,'Dune',6),(5253,'R-Line S',6),(5254,'R-Line SE',6),(5255,'R-Line SEL',6),(5256,'R-Line Executive',6),(5257,'Trend',6),(5258,'V6 Executive 4Motion',6),(5259,'T5 Inscription Platinum',6),(5260,'T5 Inscription Premier',6),(5261,'T5 R-Design Special Edition',6),(5262,'S60 Cross Country',4),(5263,'T5 Special Edition',6),(5264,'T5 Inscription',6),(5265,'T5 Momentum',6),(5266,'T6 First Edition',6),(5267,'T6 Inscription',6),(5268,'T6 Momentum',6),(5269,'T8',6),(5270,'T8 Inscription',6),(5271,'T8 Momentum',6),(5272,'T8 R-Design',6),(5273,'2017',1),(5274,'Sport Hybrid',6),(5275,'Competition',6),(5276,'L Sport',6),(5277,'RS3',4),(5278,'Dynamic Edition',6),(5279,'230i',4),(5280,'230i xDrive',4),(5281,'iPerformance',6),(5282,'330i GT xDrive',4),(5283,'330i xDrive',4),(5284,'340i GT xDrive',4),(5285,'430i',4),(5286,'430i Gran Coupe',4),(5287,'430i xDrive',4),(5288,'430i xDrive Gran Coupe',4),(5289,'440i',4),(5290,'440i Gran Coupe',4),(5291,'440i xDrive',4),(5292,'440i xDrive Gran Coupe',4),(5293,'530i xDrive',4),(5294,'540i xDrive',4),(5295,'740Le xDrive',4),(5296,'740e xDrive',4),(5297,'740i xDrive',4),(5298,'xDrive',6),(5299,'M240i',4),(5300,'M240i xDrive',4),(5301,'M760Li xDrive',4),(5302,'12 Cyl 6.6L',7),(5303,'M760i xDrive',4),(5304,'Essence',6),(5305,'Preferred',6),(5306,'Preferred II',6),(5307,'Premium Performance',6),(5308,'Vsport Premium Luxury',6),(5309,'XT5',4),(5310,'Bolt EV',4),(5311,'LCF 4500HD',4),(5312,'LCF 4500XD',4),(5313,'LCF 5500HD',4),(5314,'LCF 5500XD',4),(5315,'Activ',6),(5316,'Touring L Plus',6),(5317,'R/T 392',6),(5318,'T/A 392',6),(5319,'Daytona 392',6),(5320,'124 Spider',4),(5321,'Classica',6),(5322,'Lusso',6),(5323,'Hybrid Titanium',6),(5324,'Raptor',6),(5325,'Energi Platinum',6),(5326,'126',6),(5327,'4 Cyl 5.1L',7),(5328,'Acadia Limited',4),(5329,'Sport Special Edition',6),(5330,'Clarity',4),(5331,'LX-AS',6),(5332,'RTL-E',6),(5333,'RTL-T',6),(5334,'Ioniq',4),(5335,'Electric Limited',6),(5336,'Electric SE',6),(5337,'Hybrid Blue',6),(5338,'Limited Ultimate',6),(5339,'SE Ultimate',6),(5340,'2.0T Ultimate',6),(5341,'Hybrid Ultimate',6),(5342,'Premium Select Edition',6),(5343,'QX30',4),(5344,'HX515',4),(5345,'HX615',4),(5346,'HX620',4),(5347,'LT625',4),(5348,'F-Pace',4),(5349,'First Edition',6),(5350,'British Design Edition',6),(5351,'Niro',4),(5352,'SX Touring',6),(5353,'Touring Launch Edition',6),(5354,'Plug-In Hybrid EX',6),(5355,'HSE Luxury',6),(5356,'SE Dynamic',6),(5357,'RC Turbo',4),(5358,'Premiere Hybrid',6),(5359,'Reserve Hybrid',6),(5360,'Select Hybrid',6),(5361,'Grand Select',6),(5362,'RF Club',6),(5363,'RF GS',6),(5364,'RF GT',6),(5365,'RF Grand Touring',6),(5366,'AMG GT',4),(5367,'E43 AMG',4),(5368,'G550 4x4',4),(5369,'GLC43 AMG',4),(5370,'GLE43 AMG',4),(5371,'GLS350d',4),(5372,'GLS450',4),(5373,'GLS550',4),(5374,'GLS63 AMG',4),(5375,'Maybach S550',4),(5376,'SL450',4),(5377,'SLC300',4),(5378,'SLC43 AMG',4),(5379,'ALL4',6),(5380,'ES Plus',6),(5381,'Mirage G4',4),(5382,'Qashqai',4),(5383,'Rogue Sport',4),(5384,'SR Turbo',6),(5385,'520',4),(5386,'718 Boxster',4),(5387,'718 Cayman',4),(5388,'S E-Hybrid Platinum Edition',6),(5389,'4 Executive',6),(5390,'Series.Yellow',6),(5391,'Model 3',4),(5392,'Long Range',6),(5393,'60D',6),(5394,'75D',6),(5395,'P100D',6),(5396,'86',4),(5397,'50th Anniversary Special Edition',6),(5398,'Corolla iM',4),(5399,'Prius Prime',4),(5400,'Advanced',6),(5401,'Hybrid LE+',6),(5402,'Yaris iA',4),(5403,'#PinkBeetle',6),(5404,'Golf Alltrack',4),(5405,'Trendline+',6),(5406,'Tiguan Limited',4),(5407,'V6 Wolfsburg',6),(5408,'Polestar',6),(5409,'T5 Dynamic',6),(5410,'V90 Cross Country',4),(5411,'T6 Dynamic',6),(5412,'T8 Excellence',6),(5413,'2018',1),(5414,'A5 Sportback',4),(5415,'Plus Spyder',6),(5416,'S5 Sportback',4),(5417,'530e',4),(5418,'530e xDrive',4),(5419,'540d xDrive',4),(5420,'640i xDrive Gran Turismo',4),(5421,'M550i xDrive',4),(5422,'X2',4),(5423,'i3s',4),(5424,'Avenir',6),(5425,'Regal Sportback',4),(5426,'Regal TourX',4),(5427,'LCF 6500XD',4),(5428,'Hybrid Touring L',6),(5429,'Hybrid Touring Plus',6),(5430,'Touring Plus',6),(5431,'SRT Demon',6),(5432,'SRT Hellcat Widebody',6),(5433,'GT Plus',6),(5434,'SXT Plus Leather',6),(5435,'Blacktop',6),(5436,'SXT Premium Plus',6),(5437,'EcoSport',4),(5438,'Max Platinum',6),(5439,'Max SSV',6),(5440,'Max XLT',6),(5441,'Police Responder',6),(5442,'116',6),(5443,'S2RV',4),(5444,'106',6),(5445,'Sprinter 3500XD',4),(5446,'All Terrain',6),(5447,'Plug-In Hybrid Touring',6),(5448,'GL SE',6),(5449,'Sport Ultimate',6),(5450,'Electric Plus Limited',6),(5451,'Electric Plus Preferred',6),(5452,'Electric Plus SE',6),(5453,'Electric Plus Ultimate',6),(5454,'Kona',4),(5455,'Essential',6),(5456,'2.0T Sport',6),(5457,'2.4 Sport',6),(5458,'Hybrid GL',6),(5459,'Hybrid GLS',6),(5460,'Noir',6),(5461,'Hybrid Luxe',6),(5462,'Luxe',6),(5463,'3.7 Luxe',6),(5464,'5.6 Luxe',6),(5465,'HX520',4),(5466,'RH613',4),(5467,'E-Pace',4),(5468,'R-Dynamic HSE',6),(5469,'R-Dynamic S',6),(5470,'R-Dynamic SE',6),(5471,'400 Sport',6),(5472,'R-Dynamic',6),(5473,'Sportbrake First Edition',6),(5474,'Sportbrake S',6),(5475,'XJR575',4),(5476,'Latitude Plus',6),(5477,'Trackhawk',6),(5478,'Wrangler JK',4),(5479,'Unlimited Willys Wheeler',6),(5480,'Wrangler JL',4),(5481,'Unlimited Moab',6),(5482,'Plug-In Hybrid EX Premium',6),(5483,'Plug-In Hybrid LX',6),(5484,'EX Tech',6),(5485,'Stinger',4),(5486,'SV Autobiography Dynamic',6),(5487,'Landmark Edition',6),(5488,'Range Rover Velar',4),(5489,'LC500',4),(5490,'LC500h',4),(5491,'LS500',4),(5492,'LS500h',4),(5493,'NX300',4),(5494,'RX350L',4),(5495,'RX450hL',4),(5496,'L Black Label',6),(5497,'Anthem',4),(5498,'Granite',4),(5499,'Pinnacle',4),(5500,'Grand Touring Reserve',6),(5501,'AMG GT C',4),(5502,'AMG GT R',4),(5503,'GLC350e',4),(5504,'GLC63 AMG',4),(5505,'GLC63 AMG S',4),(5506,'Maybach S560',4),(5507,'Maybach S650',4),(5508,'S560',4),(5509,'S E ALL4',6),(5510,'Eclipse Cross',4),(5511,'Outlander PHEV',4),(5512,'Heritage Edition',6),(5513,'Kicks',4),(5514,'Carrera T',6),(5515,'Turbo S Exclusive Series',6),(5516,'4 E-Hybrid',6),(5517,'4 E-Hybrid Executive',6),(5518,'4 E-Hybrid Sport Turismo',6),(5519,'4 Sport Turismo',6),(5520,'4S Sport Turismo',6),(5521,'Turbo S E-Hybrid',6),(5522,'Turbo S E-Hybrid Executive',6),(5523,'Turbo S E-Hybrid Sport Turismo',6),(5524,'Turbo Sport Turismo',6),(5525,'Sport-tech RS',6),(5526,'2.5i Premier',6),(5527,'3.6R Premier',6),(5528,'Type RA',6),(5529,'100D',6),(5530,'SR5 Premium',6),(5531,'TRD Off-Road Premium',6),(5532,'C-HR',4),(5533,'Limited Premium',6),(5534,'Atlas',4),(5535,'Coast',6),(5536,'VNR',4),(5537,'2019',1),(5538,'Ultra sport',6),(5539,'A7 Sportback',4),(5540,'Q8',4),(5541,'RS5 Sportback',4),(5542,'e-tron Quattro',4),(5543,'M850i xDrive',4),(5544,'M35i',6),(5545,'xDrive40i',6),(5546,'X7',4),(5547,'M40i First Edition',6),(5548,'Premium II',6),(5549,'XT4',4),(5550,'True North',6),(5551,'Custom Trail Boss',6),(5552,'LT Trail Boss',6),(5553,'RST',6),(5554,'Silverado 1500 LD',4),(5555,'Silverado 4500 HD',4),(5556,'Silverado 5500 HD',4),(5557,'Silverado 6500 HD',4),(5558,'R/T Scat Pack Widebody',6),(5559,'SRT Hellcat Redeye',6),(5560,'SRT Hellcat Redeye Widebody',6),(5561,'Scat Pack 392',6),(5562,'Citadel Platinum',6),(5563,'1957 Retro Edition',6),(5564,'E-Sport',6),(5565,'ST Line',6),(5566,'SEL Hybrid',6),(5567,'Police Responder Hybrid',4),(5568,'SSV Plug-In Hybrid',4),(5569,'8 Cyl 8.0L',7),(5570,'Sprinter 1500',4),(5571,'Sprinter 4500',4),(5572,'AT4',6),(5573,'Elevation',6),(5574,'Sierra 1500 Limited',4),(5575,'N Line',6),(5576,'Electric Preferred',6),(5577,'Electric Ultimate',6),(5578,'Hybrid Essential',6),(5579,'Hybrid Luxury',6),(5580,'Hybrid Preferred',6),(5581,'Iron Man Edition',6),(5582,'Kona Electric',4),(5583,'Nexo',4),(5584,'Preferred Turbo',6),(5585,'Turbo Tech',6),(5586,'Turbo Ultimate',6),(5587,'Veloster N',4),(5588,'Signature Edition',6),(5589,'Red Sport I-Line',6),(5590,'Autograph',6),(5591,'ProACTIVE',6),(5592,'Sensory',6),(5593,'CV515',4),(5594,'HV507',4),(5595,'HV513',4),(5596,'HV607',4),(5597,'HV613',4),(5598,'MV607',4),(5599,'I-Pace',4),(5600,'300 Sport',6),(5601,'SV Project 8',6),(5602,'Sportbrake Prestige',6),(5603,'XJ50',6),(5604,'Trailhawk Elite',6),(5605,'Upland',6),(5606,'Limited X',6),(5607,'W990',4),(5608,'EX Limited',6),(5609,'EX+',6),(5610,'LXS',6),(5611,'Plug-In Hybrid SX',6),(5612,'Niro EV',4),(5613,'GT-Line',6),(5614,'HSE PHEV',6),(5615,'Ultra Luxury',6),(5616,'UX200',4),(5617,'UX250h',4),(5618,'80th Anniversary Coach Door Edition',6),(5619,'Reserve I',6),(5620,'Reserve I Hybrid',6),(5621,'Reserve II',6),(5622,'Reserve II Hybrid',6),(5623,'Nautilus',4),(5624,'LR',4),(5625,'TerraPro',4),(5626,'GS-P',6),(5627,'RF GS-P',6),(5628,'A220',4),(5629,'A250',4),(5630,'AMG GT 53',4),(5631,'AMG GT 63',4),(5632,'AMG GT 63 S',4),(5633,'CLS450',4),(5634,'CLS53 AMG',4),(5635,'E450',4),(5636,'E53 AMG',4),(5637,'S560e',4),(5638,'SE Touring',6),(5639,'SE Black Edition',6),(5640,'FEC7TW',6),(5641,'Edition ONE',6),(5642,'SL Plus',6),(5643,'SV Plus',6),(5644,'E-Hybrid',6),(5645,'GTS Sport Turismo',6),(5646,'1500 Classic',4),(5647,'EQ fortwo',4),(5648,'Ascent',4),(5649,'Series.Gray',6),(5650,'Raui Edition',6),(5651,'S209',6),(5652,'Hybrid XSE',6),(5653,'Hybrid Limited Platinum',6),(5654,'Limited Platinum',6),(5655,'L Eco',6),(5656,'Prius AWD-e',4),(5657,'Arteon',4),(5658,'SEL Premium R-Line',6),(5659,'SEL R-Line',6),(5660,'SE R-Line',6),(5661,'Final Edition SE',6),(5662,'Final Edition SEL',6),(5663,'Rabbit Edition',6),(5664,'GLI 35th Anniversary Edition',6),(5665,'GLI S',6),(5666,'XC40',4),(5667,'T4 Inscription',6),(5668,'T4 Momentum',6),(5669,'T4 R-Design',6),(5670,'2020',1),(5671,'A6 allroad',4),(5672,'L Plug-In Hybrid',6),(5673,'S-Line Premium',6),(5674,'S-Line Premium Plus',6),(5675,'S-Line Prestige',6),(5676,'Q5 PHEV',4),(5677,'Performance Spyder',6),(5678,'RS Q8',4),(5679,'S7 Sportback',4),(5680,'SQ7',4),(5681,'SQ8',4),(5682,'e-tron Sportback',4),(5683,'228i xDrive Gran Coupe',4),(5684,'745Le xDrive',4),(5685,'745e xDrive',4),(5686,'840i',4),(5687,'840i Gran Coupe',4),(5688,'840i xDrive',4),(5689,'840i xDrive Gran Coupe',4),(5690,'M235i xDrive Gran Coupe',4),(5691,'M340i',4),(5692,'M340i xDrive',4),(5693,'Edition 35 Years',6),(5694,'M8',4),(5695,'M8 Gran Coupe',4),(5696,'M850i xDrive Gran Coupe',4),(5697,'M Competition',6),(5698,'xDrive30e',6),(5699,'M50i',6),(5700,'sDrive40i',6),(5701,'sDrive M40i',6),(5702,'Encore GX',4),(5703,'3 Cyl 1.3L',7),(5704,'CT4',4),(5705,'CT5',4),(5706,'LT1',6),(5707,'AWD Launch Edition',6),(5708,'SRT Super Stock',6),(5709,'Scat Pack 392 Widebody',6),(5710,'SE Value',6),(5711,'ST-Line',6),(5712,'SE Sport Hybrid',6),(5713,'F-600 Super Duty',4),(5714,'XBA ARBOC',4),(5715,'Plug-In Hybrid Preferred',6),(5716,'Plug-In Hybrid SE',6),(5717,'Plug-In Hybrid SEL',6),(5718,'Palisade',4),(5719,'Venue',4),(5720,'Edition 30',6),(5721,'Red Sport I-Line ProACTIVE',6),(5722,'Red Sport ProACTIVE',6),(5723,'Sport ProACTIVE',6),(5724,'ProASSIST',6),(5725,'Checkered Flag',6),(5726,'Mojave',6),(5727,'Plug-In Hybrid LXS',6),(5728,'Plug-In Hybrid SX Touring',6),(5729,'Touring Special Edition',6),(5730,'SX Tech',6),(5731,'Black Line',6),(5732,'GT-Line Limited',6),(5733,'GT-Line Premium',6),(5734,'GT-Line Turbo',6),(5735,'X-Line',6),(5736,'EX Premium S',6),(5737,'EX S',6),(5738,'LX S',6),(5739,'Telluride',4),(5740,'Black Label Grand Touring',6),(5741,'Coach Door Edition',6),(5742,'Corsair',4),(5743,'L Livery',6),(5744,'CX-30',4),(5745,'100th Anniversary Special Edition',6),(5746,'RF 100th Anniversary Special Edition',6),(5747,'A35 AMG',4),(5748,'AMG GT R Pro',4),(5749,'CLA35 AMG',4),(5750,'GLB250',4),(5751,'GLE450',4),(5752,'GLE580',4),(5753,'GLS580',4),(5754,'BE',6),(5755,'718 Spyder',4),(5756,'E-Hybrid Coupe',6),(5757,'S Coupe',6),(5758,'Turbo Coupe',6),(5759,'Turbo S E-Hybrid Coupe',6),(5760,'Taycan',4),(5761,'Limited GT',6),(5762,'Limited XT',6),(5763,'Premier GT',6),(5764,'Touring XT',6),(5765,'Onyx Edition XT',6),(5766,'Outdoor XT',6),(5767,'Premier XT',6),(5768,'Kanrai Edition',6),(5769,'Standard Plus',6),(5770,'Long Range Plus',6),(5771,'Model Y',4),(5772,'Nightshade',6),(5773,'Hakone Edition',6),(5774,'TRD',6),(5775,'SE Nightshade',6),(5776,'GR Supra',4),(5777,'Atlas Cross Sport',4),(5778,'IQ Drive',6),(5779,'SE R-Line Black',6),(5780,'T8 Polestar',6),(5781,'2021',1),(5782,'Q5 Sportback',4),(5783,'RS6 Avant',4),(5784,'RS7 Sportback',4),(5785,'SQ5 Sportback',4),(5786,'330e xDrive',4),(5787,'Alpina XB7',4),(5788,'M440i',4),(5789,'M440i xDrive',4),(5790,'xDrive45e',6),(5791,'Premium Luxury Platinum',6),(5792,'Sport Platinum',6),(5793,'Commercial',6),(5794,'Hybrid Pinnacle',6),(5795,'R/T Scat Pack Shaker Widebody',6),(5796,'T/A 392 Widebody',6),(5797,'Badlands',6),(5798,'Big Bend',6),(5799,'Black Diamond',6),(5800,'Outer Banks',6),(5801,'Wildtrak',6),(5802,'Bronco Sport',4),(5803,'SE Plug-In Hybrid',6),(5804,'SEL Plug-In Hybrid',6),(5805,'Titanium Plug-In Hybrid',6),(5806,'Mustang Mach-E',4),(5807,'Elevation Standard',6),(5808,'Type R Limited Edition',6),(5809,'Preferred Hybrid',6),(5810,'Ultimate Hybrid',6),(5811,'Plug-In Hybrid Essential',6),(5812,'Night',6),(5813,'Calligraphy',6),(5814,'Ultimate Calligraphy',6),(5815,'Urban Edition',6),(5816,'Essential Tech',6),(5817,'Premium Select',6),(5818,'80th Anniversary',6),(5819,'Latitude Lux',6),(5820,'Willys Sport',6),(5821,'Grand Cherokee L',4),(5822,'Freedom',6),(5823,'Unlimited 80th Anniversary',6),(5824,'Unlimited Freedom',6),(5825,'Unlimited High Altitude',6),(5826,'Unlimited High Altitude 4xe',6),(5827,'Unlimited Islander',6),(5828,'Unlimited Rubicon 392',6),(5829,'Unlimited Rubicon 4xe',6),(5830,'Unlimited Sahara 4xe',6),(5831,'Unlimited Sahara Altitude',6),(5832,'Unlimited Sahara High Altitude',6),(5833,'Unlimited Sport Altitude',6),(5834,'Unlimited Willys',6),(5835,'Unlimited Willys Sport',6),(5836,'K5',4),(5837,'Seltos',4),(5838,'Hybrid S',6),(5839,'X-Dynamic HSE',6),(5840,'X-Dynamic SE',6),(5841,'X-Dynamic S',6),(5842,'Autobiography Fifty Edition',6),(5843,'SV Autobiography Dynamic Black Edition',6),(5844,'HSE Silver Edition',6),(5845,'SVR Carbon Edition',6),(5846,'Black Line Edition',6),(5847,'Fuji Speedway Edition',6),(5848,'100th Anniversary Edition',6),(5849,'2.5 S',6),(5850,'Carbon Edition',6),(5851,'Kuro Edition',6),(5852,'2.5 Turbo Premium',6),(5853,'2.5 Turbo Premium Plus',6),(5854,'Carbon Edition Turbo',6),(5855,'AMG GT 43',4),(5856,'GLA35 AMG',4),(5857,'GLB35 AMG',4),(5858,'GLE53 AMG',4),(5859,'Maybach GLS600',4),(5860,'Carbonite Edition',6),(5861,'Midnight Edition',6),(5862,'SR Premium',6),(5863,'25 Years',6),(5864,'GTS 4.0',6),(5865,'Targa 4S Heritage Design Edition',6),(5866,'GTS Coupe',6),(5867,'Limited Longhorn',6),(5868,'Longhorn',6),(5869,'TRX Launch Edition',6),(5870,'Night Edition',6),(5871,'XSE Nightshade',6),(5872,'SE Apex',6),(5873,'XSE Apex',6),(5874,'A91 Edition',6),(5875,'2020 Edition',6),(5876,'RAV4 Prime',4),(5877,'TRD Sport Premium',6),(5878,'ID.4',4),(5879,'United',6),(5880,'Recharge R-Design',6),(5881,'T8 Recharge Inscription',6),(5882,'T8 Recharge Polestar',6),(5883,'T8 Recharge R-Design',6),(5884,'T8 Recharge R-Design Expression',6),(5885,'Recharge',6),(5886,'T8 Recharge Inscription Expression',6),(5887,'2022',1),(5888,'Bolt EUV',4),(5889,'Blue Hybrid',6),(5890,'Blue Plug-In Hybrid',6),(5891,'Limited Plug-In Hybrid',6),(5892,'Luxury Hybrid',6),(5893,'SEL Convenience Hybrid',6),(5894,'QX55',4),(5895,'Carnival',4),(5896,'Nightfall Edition',6),(5897,'GT Elite',6),(5898,'Wilderness',6),(5899,'Taos',4);
/*!40000 ALTER TABLE `attribute_suggestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_suggestion_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attribute_suggestion_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_suggestion_seq`
--

LOCK TABLES `attribute_suggestion_seq` WRITE;
/*!40000 ALTER TABLE `attribute_suggestion_seq` DISABLE KEYS */;
INSERT INTO `attribute_suggestion_seq` VALUES (5951);
/*!40000 ALTER TABLE `attribute_suggestion_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `current_bid` decimal(38,2) DEFAULT NULL,
  `end_auction_date` datetime(6) DEFAULT NULL,
  `start_auction_date` datetime(6) DEFAULT NULL,
  `starting_bid` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (1,1.00,'2099-02-20 13:27:39.154224','2024-02-13 13:27:39.154224',NULL),(2,1.00,'2099-02-20 13:28:51.238522','2099-02-13 13:28:51.238522',NULL),(3,1.00,'2024-04-02 16:05:04.308738','2024-03-26 16:05:04.308738',NULL),(4,1.00,'2024-04-02 16:08:36.633162','2024-03-26 16:08:36.633162',NULL),(5,1.00,'2024-04-02 16:11:09.466177','2024-03-26 16:11:09.466177',NULL);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auto_bid`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auto_bid` (
  `id` bigint NOT NULL,
  `max_bid` decimal(38,2) DEFAULT NULL,
  `auction_id` bigint DEFAULT NULL,
  `bidder_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmg50u98ynknvggs9lm6n87mvj` (`auction_id`),
  KEY `FK5t9193unhj941onefoplm3pd4` (`bidder_id`),
  CONSTRAINT `FK5t9193unhj941onefoplm3pd4` FOREIGN KEY (`bidder_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKmg50u98ynknvggs9lm6n87mvj` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto_bid`
--

LOCK TABLES `auto_bid` WRITE;
/*!40000 ALTER TABLE `auto_bid` DISABLE KEYS */;
/*!40000 ALTER TABLE `auto_bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auto_bid_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auto_bid_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto_bid_seq`
--

LOCK TABLES `auto_bid_seq` WRITE;
/*!40000 ALTER TABLE `auto_bid_seq` DISABLE KEYS */;
INSERT INTO `auto_bid_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `auto_bid_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_address`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_address` (
  `id` bigint NOT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `extra_location_info` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4gutkk0tn4xlap8fbkjldluo3` (`state_name`),
  CONSTRAINT `FK4gutkk0tn4xlap8fbkjldluo3` FOREIGN KEY (`state_name`) REFERENCES `state_details` (`state_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_address`
--

LOCK TABLES `bank_address` WRITE;
/*!40000 ALTER TABLE `bank_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_address_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_address_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_address_seq`
--

LOCK TABLES `bank_address_seq` WRITE;
/*!40000 ALTER TABLE `bank_address_seq` DISABLE KEYS */;
INSERT INTO `bank_address_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `bank_address_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid` (
  `id` bigint NOT NULL,
  `bid_amount` decimal(38,2) DEFAULT NULL,
  `auction_id` bigint DEFAULT NULL,
  `bidder_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhexc6i4j8i0tmpt8bdulp6g3g` (`auction_id`),
  KEY `FKlv3wvxwx62go8g98owtwcbf7k` (`bidder_id`),
  CONSTRAINT `FKhexc6i4j8i0tmpt8bdulp6g3g` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`),
  CONSTRAINT `FKlv3wvxwx62go8g98owtwcbf7k` FOREIGN KEY (`bidder_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid_seq`
--

LOCK TABLES `bid_seq` WRITE;
/*!40000 ALTER TABLE `bid_seq` DISABLE KEYS */;
INSERT INTO `bid_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `bid_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card_type`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card_type` (
  `card_type` varchar(255) NOT NULL,
  PRIMARY KEY (`card_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card_type`
--

LOCK TABLES `card_type` WRITE;
/*!40000 ALTER TABLE `card_type` DISABLE KEYS */;
INSERT INTO `card_type` VALUES ('American Express'),('Discover'),('Mastercard'),('Visa');
/*!40000 ALTER TABLE `card_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `version` int NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2y94svpmqttx80mshyny85wqr` (`parent_id`),
  CONSTRAINT `FK2y94svpmqttx80mshyny85wqr` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Vehicle Parts & Accessories',1,1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_seq`
--

LOCK TABLES `category_seq` WRITE;
/*!40000 ALTER TABLE `category_seq` DISABLE KEYS */;
INSERT INTO `category_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `category_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(5000) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_us_ticket`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_us_ticket` (
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `processed` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_us_ticket`
--

LOCK TABLES `contact_us_ticket` WRITE;
/*!40000 ALTER TABLE `contact_us_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_us_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_us_ticket_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_us_ticket_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_us_ticket_seq`
--

LOCK TABLES `contact_us_ticket_seq` WRITE;
/*!40000 ALTER TABLE `contact_us_ticket_seq` DISABLE KEYS */;
INSERT INTO `contact_us_ticket_seq` VALUES (1);
/*!40000 ALTER TABLE `contact_us_ticket_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direct_deposit_details`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direct_deposit_details` (
  `id` bigint NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `accountholder_name` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `routing_number` varchar(255) DEFAULT NULL,
  `bank_address_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5xddkw50k3oqds4o0wo4xyf79` (`bank_address_id`),
  KEY `FKite8abxb6g14gfaf42c83ubl9` (`user_id`),
  CONSTRAINT `FK5xddkw50k3oqds4o0wo4xyf79` FOREIGN KEY (`bank_address_id`) REFERENCES `bank_address` (`id`),
  CONSTRAINT `FKite8abxb6g14gfaf42c83ubl9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direct_deposit_details`
--

LOCK TABLES `direct_deposit_details` WRITE;
/*!40000 ALTER TABLE `direct_deposit_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `direct_deposit_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direct_deposit_details_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direct_deposit_details_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direct_deposit_details_seq`
--

LOCK TABLES `direct_deposit_details_seq` WRITE;
/*!40000 ALTER TABLE `direct_deposit_details_seq` DISABLE KEYS */;
INSERT INTO `direct_deposit_details_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `direct_deposit_details_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_request`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_request` (
  `id` bigint NOT NULL,
  `status` int NOT NULL,
  `time_sent` datetime(6) DEFAULT NULL,
  `request_receiver_id` bigint DEFAULT NULL,
  `request_sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ydw6pu2949hdgp8smemc7b77` (`request_receiver_id`),
  KEY `FKq9rfr0f14h05s11ke4h8heyew` (`request_sender_id`),
  CONSTRAINT `FK9ydw6pu2949hdgp8smemc7b77` FOREIGN KEY (`request_receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKq9rfr0f14h05s11ke4h8heyew` FOREIGN KEY (`request_sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_request_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_request_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_request_seq`
--

LOCK TABLES `friend_request_seq` WRITE;
/*!40000 ALTER TABLE `friend_request_seq` DISABLE KEYS */;
INSERT INTO `friend_request_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `friend_request_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendship`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friendship` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user1_id` bigint DEFAULT NULL,
  `user2_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3uos929jau1n37onhk405wbse` (`user1_id`),
  KEY `FKq71du22g31mjeagf9uwks6hj` (`user2_id`),
  CONSTRAINT `FK3uos929jau1n37onhk405wbse` FOREIGN KEY (`user1_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKq71du22g31mjeagf9uwks6hj` FOREIGN KEY (`user2_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendship`
--

LOCK TABLES `friendship` WRITE;
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_user`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_user` (
  `group_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`group_id`,`user_id`),
  KEY `FK6u7jb50qa69gr3505uttxm86x` (`user_id`),
  CONSTRAINT `FK6u7jb50qa69gr3505uttxm86x` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKop3ikmx928d6ce9wdr6m08nh6` FOREIGN KEY (`group_id`) REFERENCES `user_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_user`
--

LOCK TABLES `group_user` WRITE;
/*!40000 ALTER TABLE `group_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_sequence`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `id_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_sequence`
--

LOCK TABLES `id_sequence` WRITE;
/*!40000 ALTER TABLE `id_sequence` DISABLE KEYS */;
INSERT INTO `id_sequence` VALUES (11);
/*!40000 ALTER TABLE `id_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` bigint NOT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `image_packet_packet_id` bigint DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FKrlmbfb4gxdkvu71i8fjfn94yi` (`image_packet_packet_id`),
  CONSTRAINT `FKrlmbfb4gxdkvu71i8fjfn94yi` FOREIGN KEY (`image_packet_packet_id`) REFERENCES `image_packet` (`packet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image_packet`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image_packet` (
  `packet_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`packet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image_packet`
--

LOCK TABLES `image_packet` WRITE;
/*!40000 ALTER TABLE `image_packet` DISABLE KEYS */;
/*!40000 ALTER TABLE `image_packet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image_seq`
--

LOCK TABLES `image_seq` WRITE;
/*!40000 ALTER TABLE `image_seq` DISABLE KEYS */;
INSERT INTO `image_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `image_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `market_listing`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `market_listing` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accepted_offer_id` bigint NOT NULL DEFAULT -1,
  `cover_image` varchar(255) DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `has_accepted_offer` bit(1) NOT NULL DEFAULT 0,
  `is_deleted` bit(1) NOT NULL,
  `is_local_pickup_only` bit(1) NOT NULL,
  `page_views` bigint NOT NULL,
  `price_per_item` decimal(10,2) DEFAULT NULL,
  `qty_available` bigint NOT NULL,
  `set_automatically` bit(1) NOT NULL,
  `auction_id` bigint DEFAULT NULL,
  `local_pickup_id` bigint DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  `widget_sold_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dkqojlu2jw0qn7eyw0ag77nqy` (`auction_id`),
  UNIQUE KEY `UK_1jqs1j615rkq03cqjj8d04nxb` (`local_pickup_id`),
  KEY `FKj77ex4y8ywq29uphgbf1o5ixy` (`seller_id`),
  KEY `FKarx832bifmicpnuhv8mov8uhx` (`widget_sold_id`),
  CONSTRAINT `FK5pajhmd6weflh53hcnx7to4xc` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`id`),
  CONSTRAINT `FKarx832bifmicpnuhv8mov8uhx` FOREIGN KEY (`widget_sold_id`) REFERENCES `widget` (`id`),
  CONSTRAINT `FKcarhtk6ilfakmw25kppb25ihv` FOREIGN KEY (`local_pickup_id`) REFERENCES `pickup` (`id`),
  CONSTRAINT `FKj77ex4y8ywq29uphgbf1o5ixy` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `market_listing`
--

INSERT INTO
  `market_listing` (
    id,
    cover_image,
    creation_date,
    is_deleted,
    is_local_pickup_only,
    page_views,
    price_per_item,
    qty_available,
    set_automatically,
    auction_id,
    local_pickup_id,
    seller_id,
    widget_sold_id
  )
VALUES
  (
    1,
    '71corvetesbautokit.jpg',
    '2024-03-26 16:08:36.666000',
    _binary '\0',
    _binary '\0',
    0,
    1500.00,
    1,
    _binary '',
    1,
    1,
    4,
    1
  ),
(
    2,
    'hummer h3.jpg',
    '2024-03-26 16:08:36.666000',
    _binary '\0',
    _binary '\0',
    0,
    500.00,
    1,
    _binary '',
    2,
    2,
    4,
    2
  ),
(
    3,
    '42020-chevrolet-corvette-lt2-engine-1563399415.jpg',
    '2024-03-26 16:05:04.339000',
    _binary '\0',
    _binary '\0',
    0,
    5000.00,
    1,
    _binary '',
    3,
    3,
    4,
    3
  ),
(
    4,
    '4MT82-Gen-3-1__25921.jpg',
    '2024-03-26 16:08:36.666000',
    _binary '\0',
    _binary '\0',
    0,
    4000.00,
    1,
    _binary '',
    4,
    4,
    4,
    4
  ),
(
    5,
    '4s-l400.jpg',
    '2024-03-26 16:11:09.496000',
    _binary '\0',
    _binary '\0',
    0,
    500.00,
    1,
    _binary '',
    5,
    5,
    4,
    5
  );

/*!40000 ALTER TABLE `market_listing` ENABLE KEYS */

UNLOCK TABLES;

--
-- Table structure for table `message`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date_sent_to_trash_owner` datetime(6) DEFAULT NULL,
  `date_sent_to_trash_receiver` datetime(6) DEFAULT NULL,
  `expire_date_owner` datetime(6) DEFAULT NULL,
  `expire_date_receiver` datetime(6) DEFAULT NULL,
  `message_type` enum('SPAM','TICKET') DEFAULT NULL,
  `msg_date` varchar(255) DEFAULT NULL,
  `offender` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `reviewer_name` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `spam_reporter` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `user_feedback` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  `receiver_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpxb0wq1oxjlpdr5hsqr4fmblp` (`owner_id`),
  KEY `FK86f0kc2mt26ifwupnivu6v8oa` (`receiver_id`),
  CONSTRAINT `FK86f0kc2mt26ifwupnivu6v8oa` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpxb0wq1oxjlpdr5hsqr4fmblp` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_seq`
--

LOCK TABLES `message_seq` WRITE;
/*!40000 ALTER TABLE `message_seq` DISABLE KEYS */;
INSERT INTO `message_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `message_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `is_read` bit(1) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_details`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_details` (
  `id` bigint NOT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `card_type` varchar(255) DEFAULT NULL,
  `cardholder_name` varchar(255) DEFAULT NULL,
  `expiration_date` varchar(255) DEFAULT NULL,
  `last4digits` varchar(255) DEFAULT NULL,
  `security_code` varchar(255) DEFAULT NULL,
  `billing_address_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK45s4epymg70i2nnfmd1vrtffm` (`billing_address_id`),
  KEY `FKl8p3c4lta5278p86b9cjjju8q` (`user_id`),
  CONSTRAINT `FK45s4epymg70i2nnfmd1vrtffm` FOREIGN KEY (`billing_address_id`) REFERENCES `shipping_address` (`id`),
  CONSTRAINT `FKl8p3c4lta5278p86b9cjjju8q` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
INSERT INTO `payment_details` VALUES (1,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',1,1),(2,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',2,2),(3,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',3,3),(4,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',4,4),(5,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',5,5),(6,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',6,6),(7,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',7,7),(8,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',8,8),(9,'$2a$10$62yxApW2UMwrxJ4oLBXjqu4blWYTn7B1g2WY3PYaSzvqjK.yy910e','American Express','Test User','02/2033','6789','$2a$10$WwTygIDMAGvTSdPzE/n4CeVRKLxfw6CVP7oJtu6MU8Rvt.dvazk2W',9,9);
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paypal` (
  `id` bigint NOT NULL,
  `paypal_login` varchar(255) DEFAULT NULL,
  `paypal_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal`
--

LOCK TABLES `paypal` WRITE;
/*!40000 ALTER TABLE `paypal` DISABLE KEYS */;
/*!40000 ALTER TABLE `paypal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paypal_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal_seq`
--

LOCK TABLES `paypal_seq` WRITE;
/*!40000 ALTER TABLE `paypal_seq` DISABLE KEYS */;
INSERT INTO `paypal_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `paypal_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pickup`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pickup` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `listing_id` bigint DEFAULT NULL,
  `location_id` bigint DEFAULT NULL,
  `transaction_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_peuxdibmwm6ovnj96iixfx92y` (`listing_id`),
  UNIQUE KEY `UK_h1vecfwqq0ds1xaaqapw2uy3p` (`location_id`),
  UNIQUE KEY `UK_fx6cjqd2egci5jkm7g7a3tta3` (`transaction_id`),
  CONSTRAINT `FK37vws73ox9u1vw0ytlse8d2qw` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`),
  CONSTRAINT `FK5bj4s48w5gf6lt83j8hbhmuk4` FOREIGN KEY (`location_id`) REFERENCES `pickup_address` (`id`),
  CONSTRAINT `FKc1827hmtjqwim6odeptor1jy2` FOREIGN KEY (`listing_id`) REFERENCES `market_listing` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pickup`
--

LOCK TABLES `pickup` WRITE;
/*!40000 ALTER TABLE `pickup` DISABLE KEYS */;
INSERT INTO `pickup` VALUES (1,1,1,NULL),(2,2,2,NULL),(3,3,3,NULL),(4,4,4,NULL),(5,5,5,NULL);
/*!40000 ALTER TABLE `pickup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pickup_address`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pickup_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `extra_location_info` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpu7k23r970fdj0tyhnmxv8htv` (`state_name`),
  CONSTRAINT `FKpu7k23r970fdj0tyhnmxv8htv` FOREIGN KEY (`state_name`) REFERENCES `state_details` (`state_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pickup_address`
--

LOCK TABLES `pickup_address` WRITE;
/*!40000 ALTER TABLE `pickup_address` DISABLE KEYS */;
INSERT INTO `pickup_address` VALUES (1,'Slippery Rock','','16057','100 Central Loop','Pennsylvania'),(2,'Slippery Rock','','16057','100 Central Loop','Pennsylvania'),(3,'Slippery Rock','','16057','100 Central Loop','Pennsylvania'),(4,'Slippery Rock','','16057','100 Central Loop','Pennsylvania'),(5,'Slippery Rock','','16057','100 Central Loop','Pennsylvania');
/*!40000 ALTER TABLE `pickup_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(5000) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_ticket`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund_ticket` (
  `id` bigint NOT NULL,
  `assigned_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `market_listing_id` bigint DEFAULT NULL,
  `resolved_at` datetime(6) DEFAULT NULL,
  `state` enum('ANSWERED','APPROVED','PENDING','REJECTED','RESOLVED','UNANSWERED','UNASSIGNED') DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `total_price_after_taxes` double DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `assigned_to_id` bigint DEFAULT NULL,
  `created_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8jld9doijw1ojq28yracvs56g` (`assigned_to_id`),
  KEY `FKph5ixvtbrvmem59mwg9cmae6n` (`created_by_id`),
  CONSTRAINT `FK8jld9doijw1ojq28yracvs56g` FOREIGN KEY (`assigned_to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKph5ixvtbrvmem59mwg9cmae6n` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_ticket`
--

LOCK TABLES `refund_ticket` WRITE;
/*!40000 ALTER TABLE `refund_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_ticket_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund_ticket_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_ticket_seq`
--

LOCK TABLES `refund_ticket_seq` WRITE;
/*!40000 ALTER TABLE `refund_ticket_seq` DISABLE KEYS */;
INSERT INTO `refund_ticket_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `refund_ticket_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revenue`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `revenue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `revenue_amount` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revenue`
--

LOCK TABLES `revenue` WRITE;
/*!40000 ALTER TABLE `revenue` DISABLE KEYS */;
/*!40000 ALTER TABLE `revenue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_rating`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller_rating` (
  `id` bigint NOT NULL,
  `num_ratings` int NOT NULL,
  `rating` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_rating`
--

LOCK TABLES `seller_rating` WRITE;
/*!40000 ALTER TABLE `seller_rating` DISABLE KEYS */;
INSERT INTO `seller_rating` VALUES (1,0,0),(2,0,0),(3,0,0),(4,0,0),(5,0,0),(6,0,0),(7,0,0),(8,0,0),(9,0,0);
/*!40000 ALTER TABLE `seller_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `carrier` varchar(255) DEFAULT NULL,
  `tracking_number` varchar(255) DEFAULT NULL,
  `shipping_address_id` bigint DEFAULT NULL,
  `transaction_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cf07hnt5hnpkit8sft72v3mhy` (`transaction_id`),
  KEY `FKjdy5t5asbskyrojgw19vps94q` (`shipping_address_id`),
  CONSTRAINT `FKbabhan3c6w96cl51g2ff39vky` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`),
  CONSTRAINT `FKjdy5t5asbskyrojgw19vps94q` FOREIGN KEY (`shipping_address_id`) REFERENCES `shipping_address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping`
--

LOCK TABLES `shipping` WRITE;
/*!40000 ALTER TABLE `shipping` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_address`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_address` (
  `id` bigint NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `extra_location_info` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsps182o483ky7569kt5obwu83` (`state_name`),
  KEY `FKqijab83dlbj00gytfswvh7ri9` (`user_id`),
  CONSTRAINT `FKqijab83dlbj00gytfswvh7ri9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsps182o483ky7569kt5obwu83` FOREIGN KEY (`state_name`) REFERENCES `state_details` (`state_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
/*!40000 ALTER TABLE `shipping_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_address_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_address_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_address_seq`
--

LOCK TABLES `shipping_address_seq` WRITE;
/*!40000 ALTER TABLE `shipping_address_seq` DISABLE KEYS */;
INSERT INTO `shipping_address_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `shipping_address_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sidebar`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sidebar` (
  `tabid` bigint NOT NULL AUTO_INCREMENT,
  `display_text` varchar(255) DEFAULT NULL,
  `link_to` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tabid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sidebar`
--

LOCK TABLES `sidebar` WRITE;
/*!40000 ALTER TABLE `sidebar` DISABLE KEYS */;
/*!40000 ALTER TABLE `sidebar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_friend_request`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `social_friend_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` enum('ACCEPTED','DECLINED','PENDING') DEFAULT NULL,
  `receiver_id` bigint DEFAULT NULL,
  `sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3l61efm8c654gcmch5wvwrhui` (`receiver_id`),
  KEY `FK1edqpjb49pshovb58ksjs9nmg` (`sender_id`),
  CONSTRAINT `FK1edqpjb49pshovb58ksjs9nmg` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK3l61efm8c654gcmch5wvwrhui` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_friend_request`
--

LOCK TABLES `social_friend_request` WRITE;
/*!40000 ALTER TABLE `social_friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_message`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `social_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `is_delivered` bit(1) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `read_timestamp` datetime(6) DEFAULT NULL,
  `sent_timestamp` datetime(6) DEFAULT NULL,
  `group_id` bigint DEFAULT NULL,
  `receiver_id` bigint DEFAULT NULL,
  `sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdvpfjc1f777rwgnw1f67f07gc` (`group_id`),
  KEY `FKth0me0spjqtdl88ho9idoot8b` (`receiver_id`),
  KEY `FKc6qjxyb6ekc5ckl1b0dy85nj8` (`sender_id`),
  CONSTRAINT `FKc6qjxyb6ekc5ckl1b0dy85nj8` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKdvpfjc1f777rwgnw1f67f07gc` FOREIGN KEY (`group_id`) REFERENCES `user_groups` (`id`),
  CONSTRAINT `FKth0me0spjqtdl88ho9idoot8b` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_message`
--

LOCK TABLES `social_message` WRITE;
/*!40000 ALTER TABLE `social_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state_details`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `state_details` (
  `state_name` varchar(255) NOT NULL,
  `sales_tax_rate` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`state_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state_details`
--

LOCK TABLES `state_details` WRITE;
/*!40000 ALTER TABLE `state_details` DISABLE KEYS */;
INSERT INTO `state_details` VALUES ('Alabama',4.00),('Alaska',0.00),('Alberta',5.00),('Arizona',5.60),('Arkansas',6.50),('British Columbia',12.00),('California',7.25),('Colorado',2.90),('Connecticut',6.35),('Delaware',0.00),('Florida',6.00),('Georgia',4.00),('Hawaii',4.00),('Idaho',6.00),('Illinois',6.25),('Indiana',7.00),('Iowa',6.00),('Kansas',6.50),('Kentucky',6.00),('Louisiana',4.45),('Maine',5.50),('Manitoba',12.00),('Maryland',6.00),('Massachusetts',6.25),('Michigan',6.00),('Minnesota',6.88),('Mississippi',7.00),('Missouri',4.23),('Montana',0.00),('Nebraska',5.50),('Nevada',6.85),('New Brunswick',15.00),('New Hampshire',0.00),('New Jersey',6.63),('New Mexico',5.13),('New York',4.00),('Newfoundland and Labrador',15.00),('North Carolina',4.75),('North Dakota',5.00),('Northwest Territories',5.00),('Nova Scotia',15.00),('Nunavut',5.00),('Ohio',5.75),('Oklahoma',4.50),('Ontario',13.00),('Oregon',0.00),('Pennsylvania',6.00),('Prince Edward Island',15.00),('Quebec',14.98),('Rhode Island',7.00),('Saskatchewan',11.00),('South Carolina',6.00),('South Dakota',4.50),('Tennessee',7.00),('Texas',6.25),('Utah',5.95),('Vermont',6.00),('Virginia',5.30),('Washington',6.50),('West Virginia',6.00),('Wisconsin',5.00),('Wyoming',4.00),('Yukon',5.00);
/*!40000 ALTER TABLE `state_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statistics`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics` (
  `id` bigint NOT NULL,
  `category` tinyint DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hour` int NOT NULL,
  `value` float NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `statistics_chk_1` CHECK ((`category` between 0 and 16))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
INSERT INTO `statistics` VALUES (1,6,'2024-03-26 16:03:20.644002','sellerName logged in',16,1);
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statistics_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statistics_seq`
--

LOCK TABLES `statistics_seq` WRITE;
/*!40000 ALTER TABLE `statistics_seq` DISABLE KEYS */;
INSERT INTO `statistics_seq` VALUES (51),(101);
/*!40000 ALTER TABLE `statistics_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` bigint NOT NULL,
  `assigned_at` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `resolved_at` varchar(255) DEFAULT NULL,
  `state` enum('ANSWERED','APPROVED','PENDING','REJECTED','RESOLVED','UNANSWERED','UNASSIGNED') DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `assigned_to_id` bigint DEFAULT NULL,
  `created_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfatbq9qkusl0yex88521x9fk6` (`assigned_to_id`),
  KEY `FKbot3adlibnkqwfrh38v72jjo1` (`created_by_id`),
  CONSTRAINT `FKbot3adlibnkqwfrh38v72jjo1` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKfatbq9qkusl0yex88521x9fk6` FOREIGN KEY (`assigned_to_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_message`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_message` (
  `ticket_id` bigint DEFAULT NULL,
  `message_id` bigint NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `FKmk0m6f9h58ho377ycgdocotwe` (`ticket_id`),
  CONSTRAINT `FK3iu6bcuba9nivwynd1qk8vwf1` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FKmk0m6f9h58ho377ycgdocotwe` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_message`
--

LOCK TABLES `ticket_message` WRITE;
/*!40000 ALTER TABLE `ticket_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_seq`
--

LOCK TABLES `ticket_seq` WRITE;
/*!40000 ALTER TABLE `ticket_seq` DISABLE KEYS */;
INSERT INTO `ticket_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `ticket_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL,
  `is_local_pickup` bit(1) NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `qty_bought` int NOT NULL,
  `refund_status` enum('APPROVED','NOT_REQUESTED','PENDING','REJECTED') DEFAULT NULL,
  `seller_profit` decimal(10,2) DEFAULT NULL,
  `total_price_after_taxes` decimal(10,2) DEFAULT NULL,
  `total_price_before_taxes` decimal(10,2) DEFAULT NULL,
  `buyer_id` bigint DEFAULT NULL,
  `deposit_details_id` bigint DEFAULT NULL,
  `local_pickup_id` bigint DEFAULT NULL,
  `market_listing_id` bigint DEFAULT NULL,
  `payment_details_id` bigint DEFAULT NULL,
  `refund_ticket_id` bigint DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  `shipping_entry_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8lalugal33u5i8d731wwxv56d` (`local_pickup_id`),
  UNIQUE KEY `UK_359tprhkdbjidcyj0y74wjl8j` (`refund_ticket_id`),
  UNIQUE KEY `UK_eobsdp3xqbuhn9f36g0fk611q` (`shipping_entry_id`),
  KEY `FKosd6qqlkyqp8gk4gjisggqev0` (`buyer_id`),
  KEY `FKxx5phgq7wl3rumsl7ipnm7gn` (`deposit_details_id`),
  KEY `FKmoxh3homji0gvlfcw51kuc5w5` (`market_listing_id`),
  KEY `FK4v8i3wqtpv6b2b0u6heymi5kp` (`payment_details_id`),
  KEY `FKs37irexq9hyvl7pqyqya2i0dn` (`seller_id`),
  CONSTRAINT `FK4v8i3wqtpv6b2b0u6heymi5kp` FOREIGN KEY (`payment_details_id`) REFERENCES `payment_details` (`id`),
  CONSTRAINT `FK8rnfpr6qicuuf25jjw937dxp0` FOREIGN KEY (`local_pickup_id`) REFERENCES `pickup` (`id`),
  CONSTRAINT `FKaasul5qbkvi7jujotj7rgst7l` FOREIGN KEY (`shipping_entry_id`) REFERENCES `shipping` (`id`),
  CONSTRAINT `FKmoxh3homji0gvlfcw51kuc5w5` FOREIGN KEY (`market_listing_id`) REFERENCES `market_listing` (`id`),
  CONSTRAINT `FKosd6qqlkyqp8gk4gjisggqev0` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKs37irexq9hyvl7pqyqya2i0dn` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtror5xo1l3mcvwmxc6nvd9jwr` FOREIGN KEY (`refund_ticket_id`) REFERENCES `refund_ticket` (`id`),
  CONSTRAINT `FKxx5phgq7wl3rumsl7ipnm7gn` FOREIGN KEY (`deposit_details_id`) REFERENCES `direct_deposit_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_seq`
--

LOCK TABLES `transaction_seq` WRITE;
/*!40000 ALTER TABLE `transaction_seq` DISABLE KEYS */;
INSERT INTO `transaction_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `transaction_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `max` int NOT NULL,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `creation_date` varchar(255) DEFAULT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `disabled_account` bit(1) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email_verification` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_two_factor_enabled` bit(1) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `passwordconf` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `secret1` varchar(255) DEFAULT NULL,
  `secret2` varchar(255) DEFAULT NULL,
  `secret3` varchar(255) DEFAULT NULL,
  `two_factor_authentication_code` varchar(255) DEFAULT NULL,
  `user_description` varchar(255) DEFAULT NULL,
  `user_image` varchar(255) DEFAULT NULL,
  `user_secret1` varchar(255) DEFAULT NULL,
  `user_secret2` varchar(255) DEFAULT NULL,
  `user_secret3` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `default_payment_details_id` bigint DEFAULT NULL,
  `default_shipping_id` bigint DEFAULT NULL,
  `direct_deposit_details_id` bigint DEFAULT NULL,
  `paypal_id` bigint DEFAULT NULL,
  `seller_rating_id` bigint DEFAULT NULL,
  `user_role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bkp84rokeuerh5xekwh9c94pl` (`default_payment_details_id`),
  UNIQUE KEY `UK_bn73bnk5cgw078g09xoe7i22d` (`default_shipping_id`),
  UNIQUE KEY `UK_npvm4owxhl9gvqwt8g2qlg6q5` (`direct_deposit_details_id`),
  UNIQUE KEY `UK_ryilgl7fyuf2vtpe9ep5l3eo` (`paypal_id`),
  UNIQUE KEY `UK_2fle9ry8tjhvu0m4ltfxt0t0n` (`seller_rating_id`),
  KEY `FKh2wc2dtfdo8maylne7mgubowq` (`user_role_id`),
  CONSTRAINT `FK8gx4h2hmvvmma3pe1vfg7etk3` FOREIGN KEY (`direct_deposit_details_id`) REFERENCES `direct_deposit_details` (`id`),
  CONSTRAINT `FKh2wc2dtfdo8maylne7mgubowq` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `FKh4ud7jq3bnx4pbtkl1bp1pgba` FOREIGN KEY (`default_payment_details_id`) REFERENCES `payment_details` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKhufqq16v6gpibkrlbie53nd84` FOREIGN KEY (`paypal_id`) REFERENCES `paypal` (`id`),
  CONSTRAINT `FKktx9d9kb7i5wgkeutmh361fjl` FOREIGN KEY (`seller_rating_id`) REFERENCES `seller_rating` (`id`),
  CONSTRAINT `FKony999y9ladt0njw7sw9rnx2x` FOREIGN KEY (`default_shipping_id`) REFERENCES `shipping_address` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$tBLTvJ2Wyu6MkC0FqkchuOH6XxKtggK5P2YyKCeVX67TriM3IKvX.','',NULL,'ROLE_ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'newadminwidget',1,NULL,NULL,1,1,NULL),(2,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$vu9epNaeY1ypTzwOXBIGRO8PNeUerpbszy1QfrNgMACoxlEmwmxZe','',NULL,'ROLE_SUPERADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'newadminsuper',2,NULL,NULL,2,2,NULL),(3,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$34WUQn6kcMYnTZJDiszfAerB0z.fEU/GE2DZtShgtffh.tDhNh.ma','',NULL,'ROLE_USER',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'userName',3,NULL,NULL,3,3,NULL),(4,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$0Hsaoe0cZXvbObDS9WBVoeA1h.pc5EAH8j3pMP9fCSEZ9APga3dFa','',NULL,'ROLE_USER',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'sellerName',4,NULL,NULL,4,4,NULL),(5,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$jbOJOUKUmagYYXUHdSjlkOdjlOWqAk0wnxQFEWmLGh28HGRaNovfu','',NULL,'ROLE_USER',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'sellerName1',5,NULL,NULL,5,5,NULL),(6,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$cU5wQxAMf.olVmeaxvFAZeMS/ou0X/9TDDbn3fUHjIjUwgUgXJ/2K','',NULL,'ROLE_HELPDESK_ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ITadmin',6,NULL,NULL,6,6,NULL),(7,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$ytGqN.zfoMScbPxK6U94lukxZicyel5ysLmOj682qwR543S.tfCPS','',NULL,'ROLE_HELPDESK_REGULAR',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ITstaff',7,NULL,NULL,7,7,NULL),(8,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$34WUQn6kcMYnTZJDiszfAerB0z.fEU/GE2DZtShgtffh.tDhNh.ma','',NULL,'ROLE_USER',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'userName2',8,NULL,NULL,8,8,NULL),(9,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$34WUQn6kcMYnTZJDiszfAerB0z.fEU/GE2DZtShgtffh.tDhNh.ma','',NULL,'ROLE_USER',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'userName3',9,NULL,NULL,9,9,NULL), (10,200,_binary '\0',_binary '\0',NULL,'2024-01-30',_binary '\0',_binary '\0',NULL,'',NULL,_binary '',NULL,_binary '\0',NULL,'$2a$10$34WUQn6kcMYnTZJDiszfAerB0z.fEU/GE2DZtShgtffh.tDhNh.ma','',NULL,'ROLE_CUSTOMERSERVICE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'CustomerService',10,NULL,NULL,10,10,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_groups`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `group_image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfq2beakgvujhi2hgo65m4k2u3` (`owner_id`),
  CONSTRAINT `FKfq2beakgvujhi2hgo65m4k2u3` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_groups`
--

LOCK TABLES `user_groups` WRITE;
/*!40000 ALTER TABLE `user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_list`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_list` (
  `id` bigint NOT NULL,
  `block_id` bigint DEFAULT NULL,
  `friend_id` bigint DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfx13elm6u0iw6ouneqqaco3d2` (`block_id`),
  KEY `FKs9oojc9bse8up7fldfel23mae` (`friend_id`),
  KEY `FKcm5v2h26bync5k8lryy4f62gq` (`owner_id`),
  CONSTRAINT `FKcm5v2h26bync5k8lryy4f62gq` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKfx13elm6u0iw6ouneqqaco3d2` FOREIGN KEY (`block_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKs9oojc9bse8up7fldfel23mae` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_list`
--

LOCK TABLES `user_list` WRITE;
/*!40000 ALTER TABLE `user_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_list_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_list_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_list_seq`
--

LOCK TABLES `user_list_seq` WRITE;
/*!40000 ALTER TABLE `user_list_seq` DISABLE KEYS */;
INSERT INTO `user_list_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `user_list_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` bigint NOT NULL,
  `authority_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_seq`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_seq`
--

LOCK TABLES `user_role_seq` WRITE;
/*!40000 ALTER TABLE `user_role_seq` DISABLE KEYS */;
INSERT INTO `user_role_seq` VALUES (1),(1);
/*!40000 ALTER TABLE `user_role_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_sequence`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_sequence`
--

LOCK TABLES `user_sequence` WRITE;
/*!40000 ALTER TABLE `user_sequence` DISABLE KEYS */;
INSERT INTO `user_sequence` VALUES (10);
/*!40000 ALTER TABLE `user_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `widget`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `widget` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjvjhmno1tt6vpdgodr85se58q` (`category_id`),
  CONSTRAINT `FKjvjhmno1tt6vpdgodr85se58q` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `widget`
--

LOCK TABLES `widget` WRITE;
/*!40000 ALTER TABLE `widget` DISABLE KEYS */;
INSERT INTO `widget` VALUES (1,'Exhaust Kit for 1971 Corvette Stingray','1971 Corvette Stingray Exhaust Kit',1),(2,'Wheel replacement for Hummer H3','Hummer H3 Wheels',1),(3,'Engine for a 2020 C8 Corvette.','2020 C8 Corvette LT2 Engine',1),(4,'6-Speed Manual Transmission for Mustang GT.','Ford Mustang GT Gen 3 Getrag MT82 6-Speed Manual Transmission',1),(5,'Camshaft For Dodge Charger 3.6L V6','Camshaft For Dodge Charger 3.6L V6',1);
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
UNLOCK TABLES;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `widget_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_data` mediumblob,
  `image_name` varchar(255) DEFAULT NULL,
  `market_listing_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgnevxtd49beiajiiobsexlexj` (`market_listing_id`),
  CONSTRAINT `FKgnevxtd49beiajiiobsexlexj` FOREIGN KEY (`market_listing_id`) REFERENCES `market_listing` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `widget_attribute`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `widget_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attribute_key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `attribute_id` bigint DEFAULT NULL,
  `widget_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9g1am96holp84ceqkkdmyr9ye` (`attribute_id`),
  KEY `FK1mncfhgmfxu1gh2wwdwjj6py6` (`widget_id`),
  CONSTRAINT `FK1mncfhgmfxu1gh2wwdwjj6py6` FOREIGN KEY (`widget_id`) REFERENCES `widget` (`id`),
  CONSTRAINT `FK9g1am96holp84ceqkkdmyr9ye` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `widget_attribute`
--

LOCK TABLES `widget_attribute` WRITE;
/*!40000 ALTER TABLE `widget_attribute` DISABLE KEYS */;
INSERT INTO `widget_attribute` VALUES (1,'Condition','New - opened box',2,1),(2,'Submodel','Stingray',6,1),(3,'Engine','8 Cyl 7.4L',7,1),(4,'Make','Chevrolet',3,1),(5,'Year','1971',1,1),(6,'Model','Corvette',4,1),(7,'Keywords','Antique',5,1),(8,'Make','GM',3,2),(9,'Keywords','Wheels',5,2),(10,'Submodel','H3',6,2),(11,'Engine','5 Cyl 3.7L',7,2),(12,'Condition','New - opened box',2,2),(13,'Year','2006',1,2),(14,'Model','Hummer',4,2),(15,'Year','2020',1,3),(16,'Engine','8 Cyl 6.2L',7,3),(17,'Make','Chevrolet',3,3),(18,'Keywords','Engine',5,3),(19,'Model','Corvette',4,3),(20,'Submodel','Stingray',6,3),(21,'Condition','Used',2,3),(22,'Model','Mustang',4,4),(23,'Keywords','Transmission',5,4),(24,'Year','2024',1,4),(25,'Condition','Used',2,4),(26,'Make','Ford',3,4),(27,'Engine','8 Cyl 5.0L',7,4),(28,'Submodel','GT',6,4),(29,'Make','Dodge',3,5),(30,'Condition','New - opened box',2,5),(31,'Submodel','GT',6,5),(32,'Model','Charger',4,5),(33,'Year','2015',1,5),(34,'Engine','6 Cyl 3.6L',7,5),(35,'Keywords','Camshaft',5,5);
/*!40000 ALTER TABLE `widget_attribute` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `widget_wishlist_entry`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `widget_wishlist_entry` (
  `user_id` bigint NOT NULL,
  `market_listing_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`market_listing_id`),
  KEY `FK21hag7wi0kuboorcqc1t5iq66` (`market_listing_id`),
  CONSTRAINT `FK21hag7wi0kuboorcqc1t5iq66` FOREIGN KEY (`market_listing_id`) REFERENCES `market_listing` (`id`),
  CONSTRAINT `FKkfdnao9k2btmv3uusccdot60d` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `widget_wishlist_entry`
--

LOCK TABLES `widget_wishlist_entry` WRITE;
/*!40000 ALTER TABLE `widget_wishlist_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `widget_wishlist_entry` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

SET FOREIGN_KEY_CHECKS = 1;

-- Dump completed on 2024-03-26 16:13:40
