-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: wic
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'category/infantcereal.jpg','Infant Cereal'),(2,'category/babyfood.jpg','Baby Food'),(3,'category/babyfoodmeat.jpg','Baby Food Meat'),(4,'category/infantformula.jpg','Infant Formula'),(5,'category/milk.jpg','Milk'),(6,'category/cheese.jpg','Cheese'),(7,'category/yogurt.jpg','Yogurt'),(8,'category/eggs.jpg','Eggs'),(9,'category/juice.jpg','Juice'),(10,'category/vegetables_fruits.jpg','Vegetables & Fruits'),(11,'category/beans_lentils.jpg','Beans & Lentils'),(12,'category/peanutbutter.jpg','Peanut Butter'),(13,'category/cannedfish.jpg','Canned Fish'),(14,'category/cereal.jpg','Cereal'),(15,'category/hotcereal.jpg','Hot Cereal'),(16,'category/wholewheatbread.jpg','Whole Wheat Bread'),(17,'category/brownrice.jpg','Brown Rice'),(18,'category/wholewheatpasta.jpg','Whole Wheat Pasta'),(19,'category/tortillas.jpg','Tortillas');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `wic_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'5122 woodsmere lane, herriman, UT 84096','customer_-2109212172','801-809-0915','customer_wic_number_-2109212172'),(2,'1-1234 my address 567','2-Johny','801-111-1111','3-my-wic-number-1234');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deliverer_name` varchar(255) DEFAULT NULL,
  `delivery_completion_time` timestamp NULL DEFAULT NULL,
  `delivery_start_time` timestamp NULL DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5hp2ax9ktk3kv209xydb2cuuy` (`wicOrder_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (1,'Chulkee Sung','2018-11-21 12:28:12','2018-11-21 12:28:07',1,1);
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `missing_product`
--

DROP TABLE IF EXISTS `missing_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `missing_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtttmokiyx6lwmoliy95gi1js` (`wicOrder_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `missing_product`
--

LOCK TABLES `missing_product` WRITE;
/*!40000 ALTER TABLE `missing_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `missing_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_handling` bit(1) DEFAULT b'1',
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'barcode_0w3422932989232_1','desc _1','product/1/prodname_1.jpg',_binary '','prodName_1',1),(2,'barcode_0w3422932989232_2','desc _2','product/1/prodname_2.jpg',_binary '','prodName_2',1),(3,'barcode_0w3422932989232_1','desc _1','product/2/prodname_1.jpg',_binary '','prodName_1',2),(4,'barcode_0w3422932989232_2','desc _2','product/2/prodname_2.jpg',_binary '','prodName_2',2),(5,'barcode_0w3422932989232_1','desc _1','product/3/prodname_1.jpg',_binary '','prodName_1',3),(6,'barcode_0w3422932989232_2','desc _2','product/3/prodname_2.jpg',_binary '','prodName_2',3),(7,'barcode_0w3422932989232_1','desc _1','product/4/prodname_1.jpg',_binary '','prodName_1',4),(8,'barcode_0w3422932989232_2','desc _2','product/4/prodname_2.jpg',_binary '','prodName_2',4),(9,'barcode_0w3422932989232_1','desc _1','product/5/prodname_1.jpg',_binary '','prodName_1',5),(10,'barcode_0w3422932989232_2','desc _2','product/5/prodname_2.jpg',_binary '','prodName_2',5),(11,'barcode_0w3422932989232_1','desc _1','product/6/prodname_1.jpg',_binary '','prodName_1',6),(12,'barcode_0w3422932989232_2','desc _2','product/6/prodname_2.jpg',_binary '','prodName_2',6),(13,'barcode_0w3422932989232_1','desc _1','product/7/prodname_1.jpg',_binary '','prodName_1',7),(14,'barcode_0w3422932989232_2','desc _2','product/7/prodname_2.jpg',_binary '','prodName_2',7),(15,'barcode_0w3422932989232_1','desc _1','product/8/prodname_1.jpg',_binary '','prodName_1',8),(16,'barcode_0w3422932989232_2','desc _2','product/8/prodname_2.jpg',_binary '','prodName_2',8),(17,'barcode_0w3422932989232_1','desc _1','product/9/prodname_1.jpg',_binary '','prodName_1',9),(18,'barcode_0w3422932989232_2','desc _2','product/9/prodname_2.jpg',_binary '','prodName_2',9),(19,'barcode_0w3422932989232_1','desc _1','product/10/prodname_1.jpg',_binary '','prodName_1',10),(20,'barcode_0w3422932989232_2','desc _2','product/10/prodname_2.jpg',_binary '','prodName_2',10),(21,'barcode_0w3422932989232_1','desc _1','product/11/prodname_1.jpg',_binary '','prodName_1',11),(22,'barcode_0w3422932989232_2','desc _2','product/11/prodname_2.jpg',_binary '','prodName_2',11),(23,'barcode_0w3422932989232_1','desc _1','product/12/prodname_1.jpg',_binary '','prodName_1',12),(24,'barcode_0w3422932989232_2','desc _2','product/12/prodname_2.jpg',_binary '','prodName_2',12),(25,'barcode_0w3422932989232_1','desc _1','product/13/prodname_1.jpg',_binary '','prodName_1',13),(26,'barcode_0w3422932989232_2','desc _2','product/13/prodname_2.jpg',_binary '','prodName_2',13),(27,'barcode_0w3422932989232_1','desc _1','product/14/prodname_1.jpg',_binary '','prodName_1',14),(28,'barcode_0w3422932989232_2','desc _2','product/14/prodname_2.jpg',_binary '','prodName_2',14),(29,'barcode_0w3422932989232_1','desc _1','product/15/prodname_1.jpg',_binary '','prodName_1',15),(30,'barcode_0w3422932989232_2','desc _2','product/15/prodname_2.jpg',_binary '','prodName_2',15),(31,'barcode_0w3422932989232_1','desc _1','product/16/prodname_1.jpg',_binary '','prodName_1',16),(32,'barcode_0w3422932989232_2','desc _2','product/16/prodname_2.jpg',_binary '','prodName_2',16),(33,'barcode_0w3422932989232_1','desc _1','product/17/prodname_1.jpg',_binary '','prodName_1',17),(34,'barcode_0w3422932989232_2','desc _2','product/17/prodname_2.jpg',_binary '','prodName_2',17),(35,'barcode_0w3422932989232_1','desc _1','product/18/prodname_1.jpg',_binary '','prodName_1',18),(36,'barcode_0w3422932989232_2','desc _2','product/18/prodname_2.jpg',_binary '','prodName_2',18),(37,'barcode_0w3422932989232_1','desc _1','product/19/prodname_1.jpg',_binary '','prodName_1',19),(38,'barcode_0w3422932989232_2','desc _2','product/19/prodname_2.jpg',_binary '','prodName_2',19);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_epk9im9l9q67xmwi4hbed25do` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'ADMIN'),(1,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8tbwelrnviudxdaggwr1kd9b` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'admin','{bcrypt}$2a$10$.3jhYrD1yiIEqeLIG7ZRFeV1jXWvMMoXssMb88N1Qmz4ZGLb8cnDO'),(1,'user','{bcrypt}$2a$10$E14/YCThXgW7S/S9t1O2Zuu4vHCHjRDaD806/xe0AxXe/7wVCWNA.');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1),(2,2,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expiration_date` timestamp NULL DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `voucher_number` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54x7dllq1ts557joc0cunvyk9` (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES (1,'2018-11-23 00:00:00','2018-11-20 12:00:00','voucherNum_-2109212172',1);
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wic_order`
--

DROP TABLE IF EXISTS `wic_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `wic_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ordered_time` timestamp NULL DEFAULT NULL,
  `product_and_quantity` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmid1t382gd9qsqdh9rivboocc` (`voucher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wic_order`
--

LOCK TABLES `wic_order` WRITE;
/*!40000 ALTER TABLE `wic_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `wic_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-02 18:57:25
