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
) ENGINE=MyISAM AUTO_INCREMENT==2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (20,'category/infantcereal.jpg','Infant Cereal'),(21,'category/babyfood.jpg','Baby Food'),(22,'category/babyfoodmeat.jpg','Baby Food Meat'),(23,'category/infantformula.jpg','Infant Formula'),(24,'category/milk.jpg','Milk'),(25,'category/cheese.jpg','Cheese'),(26,'category/yogurt.jpg','Yogurt'),(27,'category/eggs.jpg','Eggs'),(28,'category/juice.jpg','Juice'),(29,'category/vegetables_fruits.jpg','Vegetables & Fruits'),(30,'category/beans_lentils.jpg','Beans & Lentils'),(31,'category/peanutbutter.jpg','Peanut Butter'),(32,'category/cannedfish.jpg','Canned Fish'),(33,'category/cereal.jpg','Cereal'),(34,'category/hotcereal.jpg','Hot Cereal'),(35,'category/wholewheatbread.jpg','Whole Wheat Bread'),(36,'category/brownrice.jpg','Brown Rice'),(37,'category/wholewheatpasta.jpg','Whole Wheat Pasta'),(38,'category/tortillas.jpg','Tortillas');
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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'5122 woodsmere lane, herriman, UT 84096','customer_1350328970','801-809-0915','customer_wic_number_1350328970'),(2,'1.1-1234 my address 567','1.1-Johny','801-111-1111','1.1-my-wic-number-1234'),(3,'1.2-1234 my address 567','1.2-Johny','801-111-1111','1.2-my-wic-number-1234'),(5,'5122 woodsmere lane, herriman, UT 84096','customer_-263654235','801-809-0915','customer_wic_number_-263654235'),(6,'5122 woodsmere lane, herriman, UT 84096','customer_9272297','801-809-0915','customer_wic_number_9272297'),(7,'5122 woodsmere lane, herriman, UT 84096','customer_1453124361','801-809-0915','customer_wic_number_1453124361');
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
  `delivery_completion_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `store_id` int(11) DEFAULT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  `delivery_start_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5hp2ax9ktk3kv209xydb2cuuy` (`wicOrder_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (1,'Chulkee Sung','2018-11-12 23:46:01',1,1,'2018-11-12 16:45:23');
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
  `quantity` int(11) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `wicOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjv8eyubdunsxe5eu8e8xdun77` (`product_id`),
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_handling` varchar(1) DEFAULT '1',
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (39,'barcode_0w3422932989232_1','desc _1','product/20/prodname_1.jpg','1','prodName_1',20),(40,'barcode_0w3422932989232_2','desc _2','product/20/prodname_2.jpg','1','prodName_2',20),(41,'barcode_0w3422932989232_1','desc _1','product/21/prodname_1.jpg','1','prodName_1',21);
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
  `expiration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `voucher_number` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54x7dllq1ts557joc0cunvyk9` (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES (1,'2018-11-09 07:00:00','2018-11-06 19:00:00','voucherNum_1350328970',1),(2,'2018-11-23 07:00:00','2018-11-11 07:00:00','1.1-1234-voucher-num-567',2),(3,'2018-11-23 07:00:00','2018-11-11 07:00:00','1.2-1234-voucher-num-567',3),(6,'2018-11-22 07:00:00','2018-11-19 19:00:00','voucherNum_-263654235',5),(7,'2018-11-22 07:00:00','2018-11-19 19:00:00','voucherNum_9272297',6),(8,'2018-11-22 07:00:00','2018-11-19 19:00:00','voucherNum_1453124361',7);
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
  `is_emergency` varchar(1) DEFAULT '0',
  `ordered_time` timestamp DEFAULT NULL,
  `status_update_time` timestamp DEFAULT NULL,
  `product_and_quantity` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `voucher_id` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKmid1t382gd9qsqdh9rivboocc` (`voucher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
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

-- Dump completed on 2018-11-20 17:22:27
