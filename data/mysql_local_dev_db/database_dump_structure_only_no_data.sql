-- MySQL dump 10.13  Distrib 9.6.0, for macos26.3 (arm64)
--
-- Host: localhost    Database: baemtli
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '7573d468-e4ee-11f0-bcb7-38560825a1b9:1-412';

--
-- Table structure for table `choreassignment`
--

DROP TABLE IF EXISTS `choreassignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choreassignment` (
  `ID_Choreassignment` int NOT NULL AUTO_INCREMENT,
  `Workday_ID` date NOT NULL,
  `Monthassignment_ID` int NOT NULL,
  `Trainee_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID_Choreassignment`),
  KEY `Workday_ID` (`Workday_ID`),
  KEY `Monthassignment_ID` (`Monthassignment_ID`),
  KEY `Trainee_ID` (`Trainee_ID`),
  CONSTRAINT `choreassignment_ibfk_1` FOREIGN KEY (`Workday_ID`) REFERENCES `workday` (`ID_Workday`) ON DELETE CASCADE,
  CONSTRAINT `choreassignment_ibfk_2` FOREIGN KEY (`Monthassignment_ID`) REFERENCES `monthassignment` (`ID_Monthassignment`) ON DELETE CASCADE,
  CONSTRAINT `choreassignment_ibfk_3` FOREIGN KEY (`Trainee_ID`) REFERENCES `trainee` (`ID_Trainee`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chorecategory`
--

DROP TABLE IF EXISTS `chorecategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chorecategory` (
  `ID_Chorecategory` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Description` longtext,
  PRIMARY KEY (`ID_Chorecategory`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `ID_Login` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `PasswordHash` varchar(255) DEFAULT NULL,
  `Role` enum('Coach','Teamresponsible','Teammember') NOT NULL,
  `AuthSource` varchar(10) NOT NULL DEFAULT 'LOCAL',
  `Team_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID_Login`),
  UNIQUE KEY `Username` (`Username`),
  KEY `Team_ID` (`Team_ID`),
  CONSTRAINT `login_ibfk_1` FOREIGN KEY (`Team_ID`) REFERENCES `team` (`ID_Team`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `month`
--

DROP TABLE IF EXISTS `month`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `month` (
  `ID_Month` int NOT NULL AUTO_INCREMENT,
  `Month` int NOT NULL,
  `Year` year NOT NULL,
  PRIMARY KEY (`ID_Month`),
  CONSTRAINT `month_chk_1` CHECK ((`Month` between 1 and 12))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monthassignment`
--

DROP TABLE IF EXISTS `monthassignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthassignment` (
  `ID_Monthassignment` int NOT NULL AUTO_INCREMENT,
  `Team_ID` int NOT NULL,
  `Chorecategory_ID` int NOT NULL,
  `Month_ID` int NOT NULL,
  PRIMARY KEY (`ID_Monthassignment`),
  KEY `Team_ID` (`Team_ID`),
  KEY `Chorecategory_ID` (`Chorecategory_ID`),
  KEY `Month_ID` (`Month_ID`),
  CONSTRAINT `monthassignment_ibfk_1` FOREIGN KEY (`Team_ID`) REFERENCES `team` (`ID_Team`) ON DELETE CASCADE,
  CONSTRAINT `monthassignment_ibfk_2` FOREIGN KEY (`Chorecategory_ID`) REFERENCES `chorecategory` (`ID_Chorecategory`) ON DELETE CASCADE,
  CONSTRAINT `monthassignment_ibfk_3` FOREIGN KEY (`Month_ID`) REFERENCES `month` (`ID_Month`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `ID_Team` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID_Team`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trainee`
--

DROP TABLE IF EXISTS `trainee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainee` (
  `ID_Trainee` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(30) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `Team_ID` int NOT NULL,
  PRIMARY KEY (`ID_Trainee`),
  KEY `Team_ID` (`Team_ID`),
  CONSTRAINT `trainee_ibfk_1` FOREIGN KEY (`Team_ID`) REFERENCES `team` (`ID_Team`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID_User` int NOT NULL AUTO_INCREMENT,
  `AuthSource` varchar(10) NOT NULL,
  `PasswordHash` varchar(255) NOT NULL,
  `Role` enum('Coach','Teammember','Teamresponsible') NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Team_ID` int NOT NULL,
  PRIMARY KEY (`ID_User`),
  UNIQUE KEY `UK6at3cjx9irg4k6tyo0ga2nh9w` (`Username`),
  KEY `FKan0xlx8wnbrafxkihwjspqybw` (`Team_ID`),
  CONSTRAINT `FKan0xlx8wnbrafxkihwjspqybw` FOREIGN KEY (`Team_ID`) REFERENCES `team` (`ID_Team`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `workday`
--

DROP TABLE IF EXISTS `workday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workday` (
  `ID_Workday` date NOT NULL,
  PRIMARY KEY (`ID_Workday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-15  9:40:16
