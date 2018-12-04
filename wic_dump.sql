-- MySQL dump 10.13  Distrib 5.7.21, for macos10.13 (x86_64)
--
-- Host: localhost    Database: wic
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `wic_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'5122 woodsmere lane, herriman, UT 84096','customer_-2109212172','801-809-0915','customer_wic_number_-2109212172'),(2,'1-1234 my address 567','2-Johny','801-111-1111','3-my-wic-number-1234'),(3,'5122 woodsmere lane, herriman, UT 84096','customer_-119540144','801-809-0915','customer_wic_number_-119540144');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
-- Table structure for table `missing_product`
--

DROP TABLE IF EXISTS `missing_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `missing_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtttmokiyx6lwmoliy95gi1js` (`wicOrder_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `missing_product`
--

LOCK TABLES `missing_product` WRITE;
/*!40000 ALTER TABLE `missing_product` DISABLE KEYS */;
INSERT INTO `missing_product` VALUES (1,1,2,1);
/*!40000 ALTER TABLE `missing_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `product` VALUES (1,'barcode_0w3422932989232_1','desc _1','./assets/images/products/1/prodname_1.jpg','','prodname_1',1),(2,'barcode_0w3422932989232_2','desc _2','./assets/images/products/2/prodname_2.jpg','','prodname_2',1),(3,'barcode_0w3422932989232_1','desc _1','./assets/images/products/3/prodname_1.jpg','','prodname_3',2),(4,'barcode_0w3422932989232_2','desc _2','./assets/images/products/4/prodname_2.jpg','','prodname_4',2),(5,'barcode_0w3422932989232_1','desc _1','./assets/images/products/5/prodname_1.jpg','','prodname_5',3),(6,'barcode_0w3422932989232_2','desc _2','./assets/images/products/6/prodname_2.jpg','','prodname_6',3),(7,'barcode_0w3422932989232_1','desc _1','./assets/images/products/7/prodname_1.jpg','','prodname_7',4),(8,'barcode_0w3422932989232_2','desc _2','./assets/images/products/8/prodname_2.jpg','','prodname_8',4),(9,'barcode_0w3422932989232_1','desc _1','./assets/images/products/9/prodname_1.jpg','','prodname_9',5),(10,'barcode_0w3422932989232_2','desc _2','./assets/images/products/10/prodname_2.jpg','','prodname_10',5),(11,'barcode_0w3422932989232_1','desc _1','./assets/images/products/11/prodname_1.jpg','','prodname_11',6),(12,'barcode_0w3422932989232_2','desc _2','./assets/images/products/12/prodname_2.jpg','','prodname_12',6),(13,'barcode_0w3422932989232_1','desc _1','./assets/images/products/13/prodname_1.jpg','','prodname_13',7),(14,'barcode_0w3422932989232_2','desc _2','./assets/images/products/14/prodname_2.jpg','','prodname_14',7),(15,'barcode_0w3422932989232_1','desc _1','./assets/images/products/15/prodname_1.jpg','','prodname_15',8),(16,'barcode_0w3422932989232_2','desc _2','./assets/images/products/16/prodname_2.jpg','','prodname_16',8),(17,'barcode_0w3422932989232_1','desc _1','./assets/images/products/17/prodname_1.jpg','','prodname_17',9),(18,'barcode_0w3422932989232_2','desc _2','./assets/images/products/18/prodname_2.jpg','','prodname_18',9),(19,'barcode_0w3422932989232_1','desc _1','./assets/images/products/19/prodname_1.jpg','','prodname_19',10),(20,'barcode_0w3422932989232_2','desc _2','./assets/images/products/20/prodname_2.jpg','','prodname_20',10),(21,'barcode_0w3422932989232_1','desc _1','./assets/images/products/21/prodname_1.jpg','','prodname_21',11),(22,'barcode_0w3422932989232_2','desc _2','./assets/images/products/22/prodname_2.jpg','','prodname_22',11),(23,'barcode_0w3422932989232_1','desc _1','./assets/images/products/23/prodname_1.jpg','','prodname_23',12),(24,'barcode_0w3422932989232_2','desc _2','./assets/images/products/24/prodname_2.jpg','','prodname_24',12),(25,'barcode_0w3422932989232_1','desc _1','./assets/images/products/25/prodname_1.jpg','','prodname_25',13),(26,'barcode_0w3422932989232_2','desc _2','./assets/images/products/26/prodname_2.jpg','','prodname_26',13),(27,'barcode_0w3422932989232_1','desc _1','./assets/images/products/27/prodname_1.jpg','','prodname_27',14),(28,'barcode_0w3422932989232_2','desc _2','./assets/images/products/28/prodname_2.jpg','','prodname_28',14),(29,'barcode_0w3422932989232_1','desc _1','./assets/images/products/29/prodname_1.jpg','','prodname_29',15),(30,'barcode_0w3422932989232_2','desc _2','./assets/images/products/30/prodname_2.jpg','','prodname_30',15),(31,'barcode_0w3422932989232_1','desc _1','./assets/images/products/31/prodname_1.jpg','','prodname_31',16),(32,'barcode_0w3422932989232_2','desc _2','./assets/images/products/32/prodname_2.jpg','','prodname_32',16),(33,'barcode_0w3422932989232_1','desc _1','./assets/images/products/33/prodname_1.jpg','','prodname_33',17),(34,'barcode_0w3422932989232_2','desc _2','./assets/images/products/34/prodname_2.jpg','','prodname_34',17),(35,'barcode_0w3422932989232_1','desc _1','./assets/images/products/35/prodname_1.jpg','','prodname_35',18),(36,'barcode_0w3422932989232_2','desc _2','./assets/images/products/36/prodname_2.jpg','','prodname_36',18),(37,'barcode_0w3422932989232_1','desc _1','./assets/images/products/37/prodname_1.jpg','','prodname_37',19),(38,'barcode_0w3422932989232_2','desc _2','./assets/images/products/38/prodname_2.jpg','','prodname_38',19);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8tbwelrnviudxdaggwr1kd9b` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user','{bcrypt}$2a$10$E14/YCThXgW7S/S9t1O2Zuu4vHCHjRDaD806/xe0AxXe/7wVCWNA.'),(2,'admin','{bcrypt}$2a$10$.3jhYrD1yiIEqeLIG7ZRFeV1jXWvMMoXssMb88N1Qmz4ZGLb8cnDO');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expiration_date` timestamp NULL DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `voucher_number` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54x7dllq1ts557joc0cunvyk9` (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES (1,'2018-11-23 00:00:00','2018-11-20 12:00:00','voucherNum_-2109212172',1),(3,'2018-12-05 06:59:59','2018-12-04 07:00:01','voucherNum_-93161036',3);
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wic_order`
--

DROP TABLE IF EXISTS `wic_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wic_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ordered_time` timestamp NULL DEFAULT NULL,
  `product_and_quantity` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmid1t382gd9qsqdh9rivboocc` (`voucher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wic_order`
--

LOCK TABLES `wic_order` WRITE;
/*!40000 ALTER TABLE `wic_order` DISABLE KEYS */;
INSERT INTO `wic_order` VALUES (1,'2018-12-04 23:02:07','1:4&2:5&3:6&4:7&5:8&6:9&7:10&8:11&9:12&10:13&11:14&12:15&13:16&14:17&15:18&16:19&17:20&18:21&19:22&20:23&21:24&22:25&23:26&24:27&25:28&26:29&27:30&28:31&29:32&30:33&31:34&32:35&33:36&34:37&35:38&36:39&37:40&38:41','ORDER_RECEIVED','2018-12-04 23:02:07',3);
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

-- Dump completed on 2018-12-04 16:30:38
