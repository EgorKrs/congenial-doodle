
--delete from project_test.review;
--delete from project_test.book;
--delete from  project_test.user_role;
--delete from  project_test.user;
--delete from  project_test.hibernate_sequence;


--
-- Dumping data for table `hibernate_sequence`
--


LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES
(8),
(8),
(8);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,_binary '','q@emailhost99.com',NULL,NULL,NULL,'$2a$08$9tVJziGOYukc8rYcYS40NO7h5hOV/tUkTefOIoG71PUOlVMoW6Orm',NULL,'singlton');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_role`
--
LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'USER'),(1,'ADMIN');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book`
--
LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES
(1,'author1',_binary '','horror','book1',20.00,20),
(2,'auhor',_binary '\0','a','book',10.00,2),
(3,'author1',_binary '\0','action','asdas',5.00,20),
(4,'author2',_binary '\0','action','book2',2.00,50),
(5,'author2',_binary '\0','action','book2',6.00,50);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES
(1,'лучшая',NULL,10,1,1),
(2,'худшая',NULL,1,1,2);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES
(8,'2020-05-10 15:57:06.652000',30.00,'CHECKOUT_IN_PROGRESS',1),
(9,'2020-05-10 15:57:06.652000',30.00,'CHECKOUT_IN_PROGRESS',1),
(10,'2020-05-10 15:57:06.652000',30.00,'CHECKOUT_IN_PROGRESS',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `book_orders` WRITE;
/*!40000 ALTER TABLE `book_orders` DISABLE KEYS */;
INSERT INTO `book_orders` VALUES
(8,1),
(8,2),
(9,3),
(10,4);
/*!40000 ALTER TABLE `book_orders` ENABLE KEYS */;
UNLOCK TABLES;



