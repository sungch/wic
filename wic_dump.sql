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
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_CATEGORY_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (8,'Infant Cereal','8/infantcerealimg000_8.png'),(9,'Baby Food','9/babyfoodimg000_9.png'),(10,'Baby Food Meat','10/babyfoodmeatimg000_10.png'),(11,'Infant Formula','11/infantformulaimg000_11.png'),(12,'Milk','12/milkimg000_12.png'),(13,'Cheese','13/cheeseimg000_13.png'),(14,'Yogurt','14/yogurtimg000_14.png'),(15,'Eggs','15/eggsimg000_15.png'),(16,'Juice','16/juiceimg000_16.png'),(17,'Vegetables & Fruits','17/vegetables&fruitsimg000_17.png'),(18,'Beans & Lentils','18/beans&lentilsimg000_18.png'),(19,'Peanut Butter','19/peanutbutterimg000_19.png'),(20,'Canned Fish','20/cannedfishimg000_20.png'),(21,'Cereal','21/cerealimg000_21.png'),(22,'Hot Cereal','22/hotcerealimg000_22.png'),(23,'Whole Wheat Bread','23/wholewheatbreadimg000_23.png'),(24,'Brown Rice','24/brownriceimg000_24.png'),(25,'Whole Wheat Pasta','25/wholewheatpastaimg000_25.png'),(26,'Tortillas','26/tortillasimg000_26.png');
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
  `wic_number` varchar(255) COLLATE utf8_bin NOT NULL,
  `phone` varchar(255) COLLATE utf8_bin NOT NULL,
  `address` varchar(512) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_CUSTOMER_WIC_ID` (`wic_number`),
  KEY `IDX_CUSTOMER_PHONE` (`phone`),
  KEY `IDX_CUSTOMER_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
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
  `store_id` tinyint(2) NOT NULL,
  `deliverer_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `delivery_completion_time` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_DELIVERY_NAME` (`deliverer_name`),
  KEY `IDX_DELIVERY_TIME` (`delivery_completion_time`),
  KEY `IDX_DELIVERY_ORDER_ID` (`order_id`),
  KEY `FK5hp2ax9ktk3kv209xydb2cuuy` (`wicOrder_id`),
  CONSTRAINT `FK5hp2ax9ktk3kv209xydb2cuuy` FOREIGN KEY (`wicOrder_id`) REFERENCES `wic_order` (`id`),
  CONSTRAINT `FK_DELIVERY_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `wic_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
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
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` tinyint(4) NOT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_MISSING_ORDER_ID` (`order_id`),
  KEY `IDX_MISSING_PRODUCT_ID` (`product_id`),
  KEY `FKrtttmokiyx6lwmoliy95gi1js` (`wicOrder_id`),
  CONSTRAINT `FK_MISSING_PROD_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `wic_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_MISSING_PROD_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKrtttmokiyx6lwmoliy95gi1js` FOREIGN KEY (`wicOrder_id`) REFERENCES `wic_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` varchar(512) COLLATE utf8_bin NOT NULL,
  `image_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `barcode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_PRODUCT_NAME` (`name`),
  KEY `IDX_PRODUCT_BARCODE` (`barcode`),
  KEY `FK_PRODUCT_CATEGORY_ID` (`category_id`),
  CONSTRAINT `FK_PRODUCT_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (9,8,'prodName_9','desc _9','8/9/prodname_9img0019.png','barcode_0w3422932989232_9'),(10,8,'prodName_10','desc _10','8/10/prodname_10img00110.png','barcode_0w3422932989232_10'),(11,8,'prodName_11','desc _11','8/11/prodname_11img00111.png','barcode_0w3422932989232_11'),(12,8,'prodName_12','desc _12','8/12/prodname_12img00112.png','barcode_0w3422932989232_12'),(13,8,'prodName_13','desc _13','8/13/prodname_13img00113.png','barcode_0w3422932989232_13'),(14,9,'prodName_9','desc _9','9/14/prodname_9img00114.png','barcode_0w3422932989232_9'),(15,9,'prodName_10','desc _10','9/15/prodname_10img00115.png','barcode_0w3422932989232_10'),(16,9,'prodName_11','desc _11','9/16/prodname_11img00116.png','barcode_0w3422932989232_11'),(17,9,'prodName_12','desc _12','9/17/prodname_12img00117.png','barcode_0w3422932989232_12'),(18,9,'prodName_13','desc _13','9/18/prodname_13img00118.png','barcode_0w3422932989232_13'),(19,10,'prodName_9','desc _9','10/19/prodname_9img00119.png','barcode_0w3422932989232_9'),(20,10,'prodName_10','desc _10','10/20/prodname_10img00120.png','barcode_0w3422932989232_10'),(21,10,'prodName_11','desc _11','10/21/prodname_11img00121.png','barcode_0w3422932989232_11'),(22,10,'prodName_12','desc _12','10/22/prodname_12img00122.png','barcode_0w3422932989232_12'),(23,10,'prodName_13','desc _13','10/23/prodname_13img00123.png','barcode_0w3422932989232_13'),(24,11,'prodName_9','desc _9','11/24/prodname_9img00124.png','barcode_0w3422932989232_9'),(25,11,'prodName_10','desc _10','11/25/prodname_10img00125.png','barcode_0w3422932989232_10'),(26,11,'prodName_11','desc _11','11/26/prodname_11img00126.png','barcode_0w3422932989232_11'),(27,11,'prodName_12','desc _12','11/27/prodname_12img00127.png','barcode_0w3422932989232_12'),(28,11,'prodName_13','desc _13','11/28/prodname_13img00128.png','barcode_0w3422932989232_13'),(29,12,'prodName_9','desc _9','12/29/prodname_9img00129.png','barcode_0w3422932989232_9'),(30,12,'prodName_10','desc _10','12/30/prodname_10img00130.png','barcode_0w3422932989232_10'),(31,12,'prodName_11','desc _11','12/31/prodname_11img00131.png','barcode_0w3422932989232_11'),(32,12,'prodName_12','desc _12','12/32/prodname_12img00132.png','barcode_0w3422932989232_12'),(33,12,'prodName_13','desc _13','12/33/prodname_13img00133.png','barcode_0w3422932989232_13'),(34,13,'prodName_9','desc _9','13/34/prodname_9img00134.png','barcode_0w3422932989232_9'),(35,13,'prodName_10','desc _10','13/35/prodname_10img00135.png','barcode_0w3422932989232_10'),(36,13,'prodName_11','desc _11','13/36/prodname_11img00136.png','barcode_0w3422932989232_11'),(37,13,'prodName_12','desc _12','13/37/prodname_12img00137.png','barcode_0w3422932989232_12'),(38,13,'prodName_13','desc _13','13/38/prodname_13img00138.png','barcode_0w3422932989232_13'),(39,14,'prodName_9','desc _9','14/39/prodname_9img00139.png','barcode_0w3422932989232_9'),(40,14,'prodName_10','desc _10','14/40/prodname_10img00140.png','barcode_0w3422932989232_10'),(41,14,'prodName_11','desc _11','14/41/prodname_11img00141.png','barcode_0w3422932989232_11'),(42,14,'prodName_12','desc _12','14/42/prodname_12img00142.png','barcode_0w3422932989232_12'),(43,14,'prodName_13','desc _13','14/43/prodname_13img00143.png','barcode_0w3422932989232_13'),(44,15,'prodName_9','desc _9','15/44/prodname_9img00144.png','barcode_0w3422932989232_9'),(45,15,'prodName_10','desc _10','15/45/prodname_10img00145.png','barcode_0w3422932989232_10'),(46,15,'prodName_11','desc _11','15/46/prodname_11img00146.png','barcode_0w3422932989232_11'),(47,15,'prodName_12','desc _12','15/47/prodname_12img00147.png','barcode_0w3422932989232_12'),(48,15,'prodName_13','desc _13','15/48/prodname_13img00148.png','barcode_0w3422932989232_13'),(49,16,'prodName_9','desc _9','16/49/prodname_9img00149.png','barcode_0w3422932989232_9'),(50,16,'prodName_10','desc _10','16/50/prodname_10img00150.png','barcode_0w3422932989232_10'),(51,16,'prodName_11','desc _11','16/51/prodname_11img00151.png','barcode_0w3422932989232_11'),(52,16,'prodName_12','desc _12','16/52/prodname_12img00152.png','barcode_0w3422932989232_12'),(53,16,'prodName_13','desc _13','16/53/prodname_13img00153.png','barcode_0w3422932989232_13'),(54,17,'prodName_9','desc _9','17/54/prodname_9img00154.png','barcode_0w3422932989232_9'),(55,17,'prodName_10','desc _10','17/55/prodname_10img00155.png','barcode_0w3422932989232_10'),(56,17,'prodName_11','desc _11','17/56/prodname_11img00156.png','barcode_0w3422932989232_11'),(57,17,'prodName_12','desc _12','17/57/prodname_12img00157.png','barcode_0w3422932989232_12'),(58,17,'prodName_13','desc _13','17/58/prodname_13img00158.png','barcode_0w3422932989232_13'),(59,18,'prodName_9','desc _9','18/59/prodname_9img00159.png','barcode_0w3422932989232_9'),(60,18,'prodName_10','desc _10','18/60/prodname_10img00160.png','barcode_0w3422932989232_10'),(61,18,'prodName_11','desc _11','18/61/prodname_11img00161.png','barcode_0w3422932989232_11'),(62,18,'prodName_12','desc _12','18/62/prodname_12img00162.png','barcode_0w3422932989232_12'),(63,18,'prodName_13','desc _13','18/63/prodname_13img00163.png','barcode_0w3422932989232_13'),(64,19,'prodName_9','desc _9','19/64/prodname_9img00164.png','barcode_0w3422932989232_9'),(65,19,'prodName_10','desc _10','19/65/prodname_10img00165.png','barcode_0w3422932989232_10'),(66,19,'prodName_11','desc _11','19/66/prodname_11img00166.png','barcode_0w3422932989232_11'),(67,19,'prodName_12','desc _12','19/67/prodname_12img00167.png','barcode_0w3422932989232_12'),(68,19,'prodName_13','desc _13','19/68/prodname_13img00168.png','barcode_0w3422932989232_13'),(69,20,'prodName_9','desc _9','20/69/prodname_9img00169.png','barcode_0w3422932989232_9'),(70,20,'prodName_10','desc _10','20/70/prodname_10img00170.png','barcode_0w3422932989232_10'),(71,20,'prodName_11','desc _11','20/71/prodname_11img00171.png','barcode_0w3422932989232_11'),(72,20,'prodName_12','desc _12','20/72/prodname_12img00172.png','barcode_0w3422932989232_12'),(73,20,'prodName_13','desc _13','20/73/prodname_13img00173.png','barcode_0w3422932989232_13'),(74,21,'prodName_9','desc _9','21/74/prodname_9img00174.png','barcode_0w3422932989232_9'),(75,21,'prodName_10','desc _10','21/75/prodname_10img00175.png','barcode_0w3422932989232_10'),(76,21,'prodName_11','desc _11','21/76/prodname_11img00176.png','barcode_0w3422932989232_11'),(77,21,'prodName_12','desc _12','21/77/prodname_12img00177.png','barcode_0w3422932989232_12'),(78,21,'prodName_13','desc _13','21/78/prodname_13img00178.png','barcode_0w3422932989232_13'),(79,22,'prodName_9','desc _9','22/79/prodname_9img00179.png','barcode_0w3422932989232_9'),(80,22,'prodName_10','desc _10','22/80/prodname_10img00180.png','barcode_0w3422932989232_10'),(81,22,'prodName_11','desc _11','22/81/prodname_11img00181.png','barcode_0w3422932989232_11'),(82,22,'prodName_12','desc _12','22/82/prodname_12img00182.png','barcode_0w3422932989232_12'),(83,22,'prodName_13','desc _13','22/83/prodname_13img00183.png','barcode_0w3422932989232_13'),(84,23,'prodName_9','desc _9','23/84/prodname_9img00184.png','barcode_0w3422932989232_9'),(85,23,'prodName_10','desc _10','23/85/prodname_10img00185.png','barcode_0w3422932989232_10'),(86,23,'prodName_11','desc _11','23/86/prodname_11img00186.png','barcode_0w3422932989232_11'),(87,23,'prodName_12','desc _12','23/87/prodname_12img00187.png','barcode_0w3422932989232_12'),(88,23,'prodName_13','desc _13','23/88/prodname_13img00188.png','barcode_0w3422932989232_13'),(89,24,'prodName_9','desc _9','24/89/prodname_9img00189.png','barcode_0w3422932989232_9'),(90,24,'prodName_10','desc _10','24/90/prodname_10img00190.png','barcode_0w3422932989232_10'),(91,24,'prodName_11','desc _11','24/91/prodname_11img00191.png','barcode_0w3422932989232_11'),(92,24,'prodName_12','desc _12','24/92/prodname_12img00192.png','barcode_0w3422932989232_12'),(93,24,'prodName_13','desc _13','24/93/prodname_13img00193.png','barcode_0w3422932989232_13'),(94,25,'prodName_9','desc _9','25/94/prodname_9img00194.png','barcode_0w3422932989232_9'),(95,25,'prodName_10','desc _10','25/95/prodname_10img00195.png','barcode_0w3422932989232_10'),(96,25,'prodName_11','desc _11','25/96/prodname_11img00196.png','barcode_0w3422932989232_11'),(97,25,'prodName_12','desc _12','25/97/prodname_12img00197.png','barcode_0w3422932989232_12'),(98,25,'prodName_13','desc _13','25/98/prodname_13img00198.png','barcode_0w3422932989232_13'),(99,26,'prodName_9','desc _9','26/99/prodname_9img00199.png','barcode_0w3422932989232_9'),(100,26,'prodName_10','desc _10','26/100/prodname_10img001100.png','barcode_0w3422932989232_10'),(101,26,'prodName_11','desc _11','26/101/prodname_11img001101.png','barcode_0w3422932989232_11'),(102,26,'prodName_12','desc _12','26/102/prodname_12img001102.png','barcode_0w3422932989232_12'),(103,26,'prodName_13','desc _13','26/103/prodname_13img001103.png','barcode_0w3422932989232_13');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_date` bigint(20) NOT NULL,
  `expiration_date` bigint(20) NOT NULL,
  `voucher_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_VOUCHER_VOUCHER_NUMBER` (`voucher_number`),
  KEY `IDX_VOUCHER_START_DATE` (`start_date`),
  KEY `IDX_VOUCHER_EXPIRATION_DATE` (`expiration_date`),
  KEY `IDX_VOUCHER_CUSTOMER_ID` (`customer_id`),
  CONSTRAINT `FK_VOUCHER_CUSTOMER_ID` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
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
  `product_and_quantity` varchar(1024) COLLATE utf8_bin NOT NULL,
  `ordered_time` bigint(20) NOT NULL,
  `is_emergency` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `status` varchar(255) COLLATE utf8_bin NOT NULL,
  `voucher_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_ORDER_STATUS` (`status`),
  KEY `IDX_ORDER_VOUCHER_ID` (`voucher_id`),
  CONSTRAINT `FK_ORDER_VOUCHER_ID` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
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

-- Dump completed on 2018-10-18 15:55:04
