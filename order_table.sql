DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_and_quantity` varchar(1024) COLLATE utf8_bin NOT NULL,
  `ordered_time` datetime NOT NULL,
  `is_emergency` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `status` varchar(20) COLLATE utf8_bin NOT NULL,
  `voucher_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_ORDER_STATUS` (`status`),
  KEY `IDX_ORDER_VOUCHER_ID` (`voucher_id`),
  CONSTRAINT `FK_ORDER_VOUCHER_ID` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `missing_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `missing_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_MISSING_ORDER_ID` (`order_id`),
  KEY `IDX_MISSING_PRODUCT_ID` (`product_id`),
  CONSTRAINT `FK_MISSING_PROD_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_MISSING_PROD_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;