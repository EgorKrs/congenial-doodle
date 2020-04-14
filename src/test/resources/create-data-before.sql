
delete from project_test.review;
delete from project_test.book;
delete from  project_test.user_role;
delete from  project_test.user;
delete from  project_test.hibernate_sequence;


--
-- Dumping data for table `hibernate_sequence`
--


LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES
(6),
(6),
(6);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Dumping data for table `user`
--
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary '','ekrasouski@gmail.com','NULL','107510623782968017062','2020-04-14 21:06:07.186752','ru','$2a$08$G7hLOltBOtgDBqfp1/mf8OZ.uSk8JhUUnXPwXVF/boUwv4Ca1UVUK','https://lh5.googleusercontent.com/-OgmV1cz8oIA/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJOysRMYo2UYcP70a_vHB8CfBO694w/photo.jpg','Ekrasouski Krasouski');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_role`
--
LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'USER',NULL),(1,'ADMIN',NULL);
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









