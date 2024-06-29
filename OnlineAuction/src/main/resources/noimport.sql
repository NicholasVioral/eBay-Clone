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
-- Dumping data for table `applicant`
--

LOCK TABLES `applicant` WRITE;
/*!40000 ALTER TABLE `applicant` DISABLE KEYS */;
/*!40000 ALTER TABLE `applicant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (5602,'condition','CONDITION'),(5604,'size (inches)','INTEGER'),(5616,'year','YEAR'),(5619,'make','STRING'),(5621,'model','STRING'),(5652,'type','STRING');
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attribute_recommendation`
--

LOCK TABLES `attribute_recommendation` WRITE;
/*!40000 ALTER TABLE `attribute_recommendation` DISABLE KEYS */;
INSERT INTO `attribute_recommendation` VALUES (5603,4,5602,1),(5605,3,5604,1),(5617,2,5616,5367),(5618,2,5602,5367),(5620,2,5619,5367),(5622,2,5621,5367),(5653,3,5652,1);
/*!40000 ALTER TABLE `attribute_recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (5610,130.00,'2023-10-20 19:03:00.000000','2023-10-15 19:03:57.951888',130.00),(5629,80.00,'2023-10-20 19:07:00.000000','2023-10-15 19:07:34.280425',80.00),(5646,99.00,'2023-10-20 19:16:00.000000','2023-10-15 19:16:49.559158',99.00),(5658,9.00,'2023-10-20 19:19:00.000000','2023-10-15 19:20:00.034751',8.00),(5682,12.00,'2023-10-20 19:27:00.000000','2023-10-15 19:27:25.178286',12.00);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (5688,1.00,5658,5597);
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Animals & Pet Supplies',1,NULL),(5366,'Vehicles & Parts',1,NULL),(5367,'Vehicle Parts & Accessories',1,5366);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `direct_deposit_details`
--

LOCK TABLES `direct_deposit_details` WRITE;
/*!40000 ALTER TABLE `direct_deposit_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `direct_deposit_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `friendship`
--

LOCK TABLES `friendship` WRITE;
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image_packet`
--

LOCK TABLES `image_packet` WRITE;
/*!40000 ALTER TABLE `image_packet` DISABLE KEYS */;
/*!40000 ALTER TABLE `image_packet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `market_listing`
--

LOCK TABLES `market_listing` WRITE;
/*!40000 ALTER TABLE `market_listing` DISABLE KEYS */;
INSERT INTO `market_listing` VALUES (5609,'5597bird-cage.jpg',_binary '\0',_binary '\0',199.00,1,_binary '',5610,5611,5597,5606),(5628,'5597jett-muffler-1.jpg',_binary '\0',_binary '\0',120.00,1,_binary '',5629,5630,5597,5623),(5645,'5635dash1.jpg',_binary '\0',_binary '',109.99,1,_binary '',5646,5647,5635,5640),(5657,'5635filter1.jpg',_binary '\0',_binary '\0',12.00,1,_binary '',5658,5659,5635,5654),(5681,'5664aq1.jpg',_binary '\0',_binary '',26.00,1,_binary '',5682,5683,5664,5677);
/*!40000 ALTER TABLE `market_listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
INSERT INTO `payment_details` VALUES (5599,'$2a$10$5qr/UaoX7x.bQzoGeaQr5.GlK8uuDs3x3jdVDWvBXWIbaAmM.EOcW','American Express','Joe Swaggs','10/2024','0002','$2a$10$QgNfgJmZnSDT07USeeXQ0OGcpLt8O3JFbgDjV9VKkNrz7M3CKveMe',5600,5597),(5637,'$2a$10$tjtOpZ/sqyi3gvRkJAwGWuvDkGJvNZL7w0pUt2IVd.40c2LMA88Oi','American Express','Joe Swaggs','10/2024','0002','$2a$10$9vT2mOzNTh2KevaAzBRZUOMwmnmzfDPeCSfJAacnBrdvw4CL0RCtO',5638,5635),(5666,'$2a$10$z8X7/ZQ3JBam1xYLWFGRxOUXyOqis6iJdqN0Ulli1taLUnwCy23jG','Visa','Joe Swaggs','10/2024','0002','$2a$10$Yn5.AKkrPSgEYS2i1SufT.NlnB/H6bAzTbEbd/bQ9Jvn3dWDkxUom',5667,5664);
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `paypal`
--

LOCK TABLES `paypal` WRITE;
/*!40000 ALTER TABLE `paypal` DISABLE KEYS */;
/*!40000 ALTER TABLE `paypal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `pickup`
--

LOCK TABLES `pickup` WRITE;
/*!40000 ALTER TABLE `pickup` DISABLE KEYS */;
INSERT INTO `pickup` VALUES (5611,5609,5612,NULL),(5630,5628,5631,NULL),(5647,5645,5648,NULL),(5659,5657,5660,NULL),(5683,5681,5684,NULL);
/*!40000 ALTER TABLE `pickup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `pickup_address`
--

LOCK TABLES `pickup_address` WRITE;
/*!40000 ALTER TABLE `pickup_address` DISABLE KEYS */;
INSERT INTO `pickup_address` VALUES (5612,'Slippery Rock','','16057','104 Maltby Avenue','Pennsylvania'),(5631,'','','','','Alabama'),(5648,'Slippery Rock','','16057','104 Maltby Avenue','Pennsylvania'),(5660,'Slippery Rock','','','','Alabama'),(5684,'Slippery Rock','','16057','104 Maltby Avenue','Pennsylvania');
/*!40000 ALTER TABLE `pickup_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `seller_rating`
--

LOCK TABLES `seller_rating` WRITE;
/*!40000 ALTER TABLE `seller_rating` DISABLE KEYS */;
INSERT INTO `seller_rating` VALUES (5598,22,4),(5636,4,3),(5665,3,4);
/*!40000 ALTER TABLE `seller_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shipping`
--

LOCK TABLES `shipping` WRITE;
/*!40000 ALTER TABLE `shipping` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
/*!40000 ALTER TABLE `shipping_address` DISABLE KEYS */;
INSERT INTO `shipping_address` VALUES (5600,'slippery Rock','','16057','Douglas Maxwell','104 Maltby Avenue','Pennsylvania',5597),(5638,'slippery Rock','','16057','Joe Swaggs','104 Maltby Avenue','Pennsylvania',5635),(5667,'slippery Rock','','16057','Tim Baggins','104 Maltby Avenue','Pennsylvania',5664);
/*!40000 ALTER TABLE `shipping_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sidebar`
--

LOCK TABLES `sidebar` WRITE;
/*!40000 ALTER TABLE `sidebar` DISABLE KEYS */;
/*!40000 ALTER TABLE `sidebar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `social_friend_request`
--

LOCK TABLES `social_friend_request` WRITE;
/*!40000 ALTER TABLE `social_friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `social_message`
--

LOCK TABLES `social_message` WRITE;
/*!40000 ALTER TABLE `social_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `state_details`
--



--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
INSERT INTO `statistics` VALUES (5596,2,'2023-10-05 18:55:10.109085','Account with username: userName was created',18,1),(5601,3,'2023-10-05 18:56:10.711130','userName logged in',18,1),(5634,2,'2023-10-05 19:10:55.671210','Account with username: sellerName was created',19,1),(5639,3,'2023-10-05 19:14:46.970991','sellerName logged in',19,1),(5663,2,'2023-10-05 19:21:40.738982','Account with username: sellerName1 was created',19,1),(5668,3,'2023-10-05 19:22:38.870089','sellerName1 logged in',19,1),(5687,3,'2023-10-05 19:28:03.218752','userName logged in',19,1);
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket_message`
--

LOCK TABLES `ticket_message` WRITE;
/*!40000 ALTER TABLE `ticket_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (5597,200,_binary '\0',_binary '\0','US','2023-10-05',_binary '\0',_binary '\0',NULL,'dsm1015@sru.edu','hdbyyelwtu',_binary '','Douglas','Maxwell','$2a$10$xdF7XZKhRiBKle/9cuzE8.ncZ4IOnjBhMO3/K0Gy1RTxsB9FFBj4a','wpulcivkea','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','userName rocks!','5597hack_frog.jpg','joe','rock','coffee','userName',5599,5600,NULL,NULL,5598,NULL),(6000,200,_binary '\0',_binary '\0','US','2023-10-05',_binary '\0',_binary '\0',NULL,'admin@offerly.com','hdbyyelwtu',_binary '','Offerly','SuperAdmin','$2a$10$mg/2t2Sjly6uLkAwAdu3jeAgx/ACDFvkgNoLQJhvmljzHlONqFg0m','wpulcivkea','1231231234','ROLE_SUPERADMIN','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','userName rocks!','5597hack_frog.jpg','joe','rock','coffee','newadminsuper',5599,5600,NULL,NULL,5598,NULL),(5598,200,_binary '\0',_binary '\0','US','2023-10-05',_binary '\0',_binary '\0',NULL,'lmcelligott8802@gmail.com',NULL,_binary '','Offerly','Admin','$2a$10$AfKvajVl0TksJYTSSju2ZOMRPE38SvBGfYMC1Jin04IoykKrGM6Y.','wetwdvlxln','1234567890','ROLE_ADMIN','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','userName rocks!','5597hack_frog.jpg','joe','rock','coffee','newadminwidget',NULL,NULL,NULL,NULL,NULL,NULL), (5599,200,_binary '\0',_binary '\0','US','2023-10-05',_binary '\0',_binary '\0',NULL,'holtzt45@gmail.com',NULL,_binary '','Offerly','ITrole','$2a$10$cTUZfrHHFxTgjRD1IUfAPOeQXy.s84YBZTkgSIjq19CvsfIv7yIc2','kxxifwmrvi','1234567890','ROLE_CUSTOMERSERVICE','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','IT role for tickets','5597hack_frog.jpg','joe','rock','coffee','ITstaff',NULL,NULL,NULL,NULL,NULL,NULL), (5635,200,_binary '\0',_binary '\0','US','2023-10-05',_binary '\0',_binary '\0',NULL,'dmaxwell4848@gmail.com','jzbvxousqm',_binary '','Joe','Swaggs','$2a$10$ZBxxM7zV4dYVTqo62kDjquDvCC.Lu.WxIHdVJ5/Aw7EirUKrn2xZq','sophizdryk','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','yo','5635191dollvatar.png','joe','rock','coffee','sellerName',5637,5638,NULL,NULL,5636,NULL),(5664,200,_binary '\0',_binary '\0','US','2023-10-05',_binary '\0',_binary '\0',NULL,'dmaxwell484@gmail.com','ryqhvhkzpn',_binary '','Tim','Baggins','$2a$10$srHP050BJhyzLVi9pcBlheq0PEQvJJ3eNfaSu0OirkFa0onZgwNRG','kiszzgjoqs','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','','5664planet_space_outer_space_144566_3840x2160.jpg','joe','rock','coffee','sellerName1',5666,5667,NULL,NULL,5665,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_list`
--

LOCK TABLES `user_list` WRITE;
/*!40000 ALTER TABLE `user_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget`
--

LOCK TABLES `widget` WRITE;
/*!40000 ALTER TABLE `widget` DISABLE KEYS */;
INSERT INTO `widget` VALUES (5606,'used bird cage if anyone needs it.','Bird Cage',1),(5623,'VW muffler. Little beat up but gets the job done.','1.4l Rear Exhaust Muffler 5qm253611 OE Fits VOLKSWAGEN JETTA 2019-2021',5367),(5640,'Climate Control for Blazer Dashboard','1999-2001 GMC Jimmy Chevy Blazer Heater A/C Climate Control Switch Rear Defrost',5367),(5654,'A brand-new, unused, unopened, undamaged item (including handmade items). See the seller\'s listing for full details ','Aquarium Fish Tank Internal Filter 10 Gallons Filtration with Air Pump New',1),(5677,'New: A brand-new, unused, unopened, undamaged item (including handmade items).','desktop 8 Liter Pet good fish Aquarium tank',1);
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_attribute`
--

LOCK TABLES `widget_attribute` WRITE;
/*!40000 ALTER TABLE `widget_attribute` DISABLE KEYS */;
INSERT INTO `widget_attribute` VALUES (5607,'Size (inches)','64',NULL,5606),(5608,'Condition','Used',NULL,5606),(5624,'Year','2012',NULL,5623),(5625,'Make','VW',NULL,5623),(5626,'Condition','Used',NULL,5623),(5627,'Model','Jetta',NULL,5623),(5641,'make','Blazer',NULL,5640),(5642,'model','Chevy',NULL,5640),(5643,'condition','Used',NULL,5640),(5644,'year','1999',NULL,5640),(5655,'condition','New - opened box',NULL,5654),(5656,'Type','Filter',NULL,5654),(5678,'type','aquarium',NULL,5677),(5679,'size (inches)','8',NULL,5677),(5680,'condition','New',NULL,5677);
/*!40000 ALTER TABLE `widget_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_image`
--

LOCK TABLES `widget_image` WRITE;
/*!40000 ALTER TABLE `widget_image` DISABLE KEYS */;
INSERT INTO `widget_image` VALUES (5613,'5597bird-cage.jpg',5609),(5614,'5597birdcage3.jpg',5609),(5615,'5597birdcage2.jpg',5609),(5632,'5597jett-muffler-1.jpg',5628),(5633,'5597jetta-muffler-2.jpg',5628),(5649,'5635dash1.jpg',5645),(5650,'5635dash3.jpg',5645),(5651,'5635dash2.jpg',5645),(5661,'5635filter1.jpg',5657),(5662,'5635filter2.jpg',5657),(5685,'5664aq1.jpg',5681),(5686,'5664aq2.jpg',5681);
/*!40000 ALTER TABLE `widget_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_wishlist_entry`
--

LOCK TABLES `widget_wishlist_entry` WRITE;
/*!40000 ALTER TABLE `widget_wishlist_entry` DISABLE KEYS */;
INSERT INTO `widget_wishlist_entry` VALUES (5597,5645),(5597,5657);
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

-- Dump completed on 2023-10-05 19:37:51
SET foreign_key_checks = 1;