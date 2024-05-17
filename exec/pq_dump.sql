-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: k10a509.p.ssafy.io    Database: apm
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.20.04.1

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

--
-- Table structure for table `channel`
--

DROP TABLE IF EXISTS `channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `channel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `cur_players` int DEFAULT NULL,
  `max_players` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `channel_id` bigint DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `cur_players` int DEFAULT NULL,
  `cur_round` int DEFAULT NULL,
  `is_team` bit(1) DEFAULT NULL,
  `max_players` int DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `rounds` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `style` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game-user`
--

DROP TABLE IF EXISTS `game-user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game-user` (
  `code` varchar(255) NOT NULL,
  `game_code` varchar(255) DEFAULT NULL,
  `is_host` bit(1) DEFAULT NULL,
  `score` int DEFAULT NULL,
  `team` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game_quiz`
--

DROP TABLE IF EXISTS `game_quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_quiz` (
  `code` varchar(255) NOT NULL,
  `game_code` varchar(255) DEFAULT NULL,
  `is_answer` bit(1) DEFAULT NULL,
  `number` int DEFAULT NULL,
  `quiz_id` bigint DEFAULT NULL,
  `round` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `filepath` varchar(255) DEFAULT NULL,
  `filesize` bigint DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prompt`
--

DROP TABLE IF EXISTS `prompt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prompt` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `eng_adjective` varchar(255) DEFAULT NULL,
  `eng_adverb` varchar(255) DEFAULT NULL,
  `eng_object` varchar(255) DEFAULT NULL,
  `eng_sentence` varchar(255) DEFAULT NULL,
  `eng_subject` varchar(255) DEFAULT NULL,
  `eng_verb` varchar(255) DEFAULT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `kor_adjective` varchar(255) DEFAULT NULL,
  `kor_adverb` varchar(255) DEFAULT NULL,
  `kor_object` varchar(255) DEFAULT NULL,
  `kor_sentence` varchar(255) DEFAULT NULL,
  `kor_subject` varchar(255) DEFAULT NULL,
  `kor_verb` varchar(255) DEFAULT NULL,
  `style` varchar(255) DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=300001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `style` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `eng_object` varchar(255) DEFAULT NULL,
  `eng_sentence` varchar(255) DEFAULT NULL,
  `eng_subject` varchar(255) DEFAULT NULL,
  `eng_verb` varchar(255) DEFAULT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `kor_object` varchar(255) DEFAULT NULL,
  `kor_sentence` varchar(255) DEFAULT NULL,
  `kor_subject` varchar(255) DEFAULT NULL,
  `kor_verb` varchar(255) DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `eng_sub_adjective` varchar(255) DEFAULT NULL,
  `eng_obj_adjective` varchar(255) DEFAULT NULL,
  `kor_sub_adjective` varchar(255) DEFAULT NULL,
  `kor_obj_adjective` varchar(255) DEFAULT NULL,
  `eng_adjective` varchar(255) DEFAULT NULL,
  `eng_adverb` varchar(255) DEFAULT NULL,
  `kor_adjective` varchar(255) DEFAULT NULL,
  `kor_adverb` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `s3file`
--

DROP TABLE IF EXISTS `s3file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `s3file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `filesize` bigint DEFAULT NULL,
  `filetype` varchar(255) DEFAULT NULL,
  `original_filename` varchar(255) DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `solo_score` int DEFAULT NULL,
  `status_message` varchar(255) DEFAULT NULL,
  `team_score` int DEFAULT NULL,
  `total_score` int DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_channel`
--

DROP TABLE IF EXISTS `user_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_channel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `channel_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5455 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-16 16:13:52
