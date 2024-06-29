-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: sellingwidgets
-- ------------------------------------------------------
-- Server version	8.0.34

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
SET foreign_key_checks = 0;


--
-- Dumping data for table `card_type`
--

LOCK TABLES `card_type` WRITE;
/*!40000 ALTER TABLE `card_type` DISABLE KEYS */;
INSERT INTO `card_type` VALUES ('American Express'),('Discover'),('Mastercard'),('Visa');
/*!40000 ALTER TABLE `card_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
--/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Vehicle Parts & Accessories', 1, 1);
--/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--INSERT ALL ACCOUNTS
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES
(1, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$tBLTvJ2Wyu6MkC0FqkchuOH6XxKtggK5P2YyKCeVX67TriM3IKvX.', '', NULL, 'ROLE_ADMIN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'newadminwidget', NULL, NULL, NULL, 1, 1, NULL),
(2, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$vu9epNaeY1ypTzwOXBIGRO8PNeUerpbszy1QfrNgMACoxlEmwmxZe', '', NULL, 'ROLE_SUPERADMIN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'newadminsuper', NULL, NULL, NULL, 2, 2, NULL),
(3, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$34WUQn6kcMYnTZJDiszfAerB0z.fEU/GE2DZtShgtffh.tDhNh.ma', '', NULL, 'ROLE_USER', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'userName', NULL, NULL, NULL, 3, 3, NULL),
(4, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$0Hsaoe0cZXvbObDS9WBVoeA1h.pc5EAH8j3pMP9fCSEZ9APga3dFa', '', NULL, 'ROLE_USER', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'sellerName', NULL, NULL, NULL, 4, 4, NULL),
(5, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$jbOJOUKUmagYYXUHdSjlkOdjlOWqAk0wnxQFEWmLGh28HGRaNovfu', '', NULL, 'ROLE_USER', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'sellerName1', NULL, NULL, NULL, 5, 5, NULL),
(6, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$cU5wQxAMf.olVmeaxvFAZeMS/ou0X/9TDDbn3fUHjIjUwgUgXJ/2K', '', NULL, 'ROLE_HELPDESK_ADMIN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ITadmin', NULL, NULL, NULL, 6, 6, NULL),
(7, 200, 0, 0, NULL, '2024-01-30', 0, 0, NULL, '', NULL, 1, NULL, 0, NULL, '$2a$10$ytGqN.zfoMScbPxK6U94lukxZicyel5ysLmOj682qwR543S.tfCPS', '', NULL, 'ROLE_HELPDESK_REGULAR', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ITstaff', NULL, NULL, NULL, 7, 7, NULL);
UNLOCK TABLES;

LOCK TABLES `seller_rating` WRITE;
INSERT INTO `seller_rating` VALUES (1,0,0),(2,0,0),(3,0,0),(4,0,0),(5,0,0),(6,0,0),(7,0,0);
UNLOCK TABLES;

SET foreign_key_checks = 1;
-- Dump completed on 2023-11-09 20:59:03
