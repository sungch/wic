-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: wic
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

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
INSERT INTO `category` VALUES (8,'Infant Cereal','category/infantcereal.jpg'),(9,'Baby Food','category/babyfood.jpg'),(10,'Baby Food Meat','category/babyfoodmeat.jpg'),(11,'Infant Formula','category/infantformula.jpg'),(12,'Milk','category/milk.jpg'),(13,'Cheese','category/cheese.jpg'),(14,'Yogurt','category/yogurt.jpg'),(15,'Eggs','category/eggs.jpg'),(16,'Juice','category/juice.jpg'),(17,'Vegetables & Fruits','category/vegetables_fruits.jpg'),(18,'Beans & Lentils','category/beans_lentils.jpg'),(19,'Peanut Butter','category/peanutbutter.jpg'),(20,'Canned Fish','category/cannedfish.jpg'),(21,'Cereal','category/cereal.jpg'),(22,'Hot Cereal','category/hotcereal.jpg'),(23,'Whole Wheat Bread','category/wholewheatbread.jpg'),(24,'Brown Rice','category/brownrice.jpg'),(25,'Whole Wheat Pasta','category/wholewheatpasta.jpg'),(26,'Tortillas','category/tortillas.jpg');
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
  `is_handling` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`id`),
  KEY `IDX_PRODUCT_NAME` (`name`),
  KEY `IDX_PRODUCT_BARCODE` (`barcode`),
  KEY `FK_PRODUCT_CATEGORY_ID` (`category_id`),
  CONSTRAINT `FK_PRODUCT_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (199,8,'prodName_9','desc _9','product/8/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(200,8,'prodName_10','desc _10','product/8/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(201,8,'prodName_11','desc _11','product/8/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(202,8,'prodName_12','desc _12','product/8/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(203,8,'prodName_13','desc _13','product/8/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(204,9,'prodName_9','desc _9','product/9/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(205,9,'prodName_10','desc _10','product/9/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(206,9,'prodName_11','desc _11','product/9/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(207,9,'prodName_12','desc _12','product/9/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(208,9,'prodName_13','desc _13','product/9/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(209,10,'prodName_9','desc _9','product/10/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(210,10,'prodName_10','desc _10','product/10/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(211,10,'prodName_11','desc _11','product/10/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(212,10,'prodName_12','desc _12','product/10/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(213,10,'prodName_13','desc _13','product/10/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(214,11,'prodName_9','desc _9','product/11/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(215,11,'prodName_10','desc _10','product/11/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(216,11,'prodName_11','desc _11','product/11/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(217,11,'prodName_12','desc _12','product/11/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(218,11,'prodName_13','desc _13','product/11/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(219,12,'prodName_9','desc _9','product/12/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(220,12,'prodName_10','desc _10','product/12/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(221,12,'prodName_11','desc _11','product/12/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(222,12,'prodName_12','desc _12','product/12/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(223,12,'prodName_13','desc _13','product/12/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(224,13,'prodName_9','desc _9','product/13/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(225,13,'prodName_10','desc _10','product/13/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(226,13,'prodName_11','desc _11','product/13/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(227,13,'prodName_12','desc _12','product/13/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(228,13,'prodName_13','desc _13','product/13/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(229,14,'prodName_9','desc _9','product/14/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(230,14,'prodName_10','desc _10','product/14/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(231,14,'prodName_11','desc _11','product/14/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(232,14,'prodName_12','desc _12','product/14/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(233,14,'prodName_13','desc _13','product/14/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(234,15,'prodName_9','desc _9','product/15/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(235,15,'prodName_10','desc _10','product/15/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(236,15,'prodName_11','desc _11','product/15/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(237,15,'prodName_12','desc _12','product/15/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(238,15,'prodName_13','desc _13','product/15/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(239,16,'prodName_9','desc _9','product/16/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(240,16,'prodName_10','desc _10','product/16/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(241,16,'prodName_11','desc _11','product/16/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(242,16,'prodName_12','desc _12','product/16/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(243,16,'prodName_13','desc _13','product/16/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(244,17,'prodName_9','desc _9','product/17/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(245,17,'prodName_10','desc _10','product/17/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(246,17,'prodName_11','desc _11','product/17/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(247,17,'prodName_12','desc _12','product/17/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(248,17,'prodName_13','desc _13','product/17/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(249,18,'prodName_9','desc _9','product/18/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(250,18,'prodName_10','desc _10','product/18/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(251,18,'prodName_11','desc _11','product/18/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(252,18,'prodName_12','desc _12','product/18/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(253,18,'prodName_13','desc _13','product/18/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(254,19,'prodName_9','desc _9','product/19/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(255,19,'prodName_10','desc _10','product/19/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(256,19,'prodName_11','desc _11','product/19/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(257,19,'prodName_12','desc _12','product/19/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(258,19,'prodName_13','desc _13','product/19/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(259,20,'prodName_9','desc _9','product/20/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(260,20,'prodName_10','desc _10','product/20/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(261,20,'prodName_11','desc _11','product/20/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(262,20,'prodName_12','desc _12','product/20/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(263,20,'prodName_13','desc _13','product/20/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(264,21,'prodName_9','desc _9','product/21/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(265,21,'prodName_10','desc _10','product/21/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(266,21,'prodName_11','desc _11','product/21/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(267,21,'prodName_12','desc _12','product/21/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(268,21,'prodName_13','desc _13','product/21/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(269,22,'prodName_9','desc _9','product/22/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(270,22,'prodName_10','desc _10','product/22/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(271,22,'prodName_11','desc _11','product/22/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(272,22,'prodName_12','desc _12','product/22/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(273,22,'prodName_13','desc _13','product/22/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(274,23,'prodName_9','desc _9','product/23/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(275,23,'prodName_10','desc _10','product/23/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(276,23,'prodName_11','desc _11','product/23/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(277,23,'prodName_12','desc _12','product/23/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(278,23,'prodName_13','desc _13','product/23/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(279,24,'prodName_9','desc _9','product/24/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(280,24,'prodName_10','desc _10','product/24/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(281,24,'prodName_11','desc _11','product/24/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(282,24,'prodName_12','desc _12','product/24/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(283,24,'prodName_13','desc _13','product/24/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(284,25,'prodName_9','desc _9','product/25/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(285,25,'prodName_10','desc _10','product/25/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(286,25,'prodName_11','desc _11','product/25/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(287,25,'prodName_12','desc _12','product/25/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(288,25,'prodName_13','desc _13','product/25/prodname_13.jpg','barcode_0w3422932989232_13','Y'),(289,26,'prodName_9','desc _9','product/26/prodname_9.jpg','barcode_0w3422932989232_9','Y'),(290,26,'prodName_10','desc _10','product/26/prodname_10.jpg','barcode_0w3422932989232_10','Y'),(291,26,'prodName_11','desc _11','product/26/prodname_11.jpg','barcode_0w3422932989232_11','Y'),(292,26,'prodName_12','desc _12','product/26/prodname_12.jpg','barcode_0w3422932989232_12','Y'),(293,26,'prodName_13','desc _13','product/26/prodname_13.jpg','barcode_0w3422932989232_13','Y');
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

-- Dump completed on 2018-10-26 20:47:48
