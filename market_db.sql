-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for market_db
CREATE DATABASE IF NOT EXISTS `market_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `market_db`;

-- Dumping structure for table market_db.extensions
CREATE TABLE IF NOT EXISTS `extensions` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `downloadsCount` int(11) NOT NULL,
  `downloadLink` varchar(255) NOT NULL,
  `repoLink` varchar(255) NOT NULL,
  `openIssues` int(11) NOT NULL,
  `pullReq` int(11) NOT NULL,
  `lastCommit` date NOT NULL,
  `ownerId` int(11) NOT NULL,
  `pending` tinyint(1) NOT NULL,
  `icon` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `Extensions_fk0` (`ownerId`),
  CONSTRAINT `Extensions_fk0` FOREIGN KEY (`ownerId`) REFERENCES `user_profiles` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.extension_tags
CREATE TABLE IF NOT EXISTS `extension_tags` (
  `extensionId` int(11) NOT NULL,
  `tagId` int(11) NOT NULL,
  KEY `ExtensionTags_fk0` (`extensionId`),
  KEY `ExtensionTags_fk1` (`tagId`),
  CONSTRAINT `ExtensionTags_fk0` FOREIGN KEY (`extensionId`) REFERENCES `extensions` (`Id`),
  CONSTRAINT `ExtensionTags_fk1` FOREIGN KEY (`tagId`) REFERENCES `tags` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.screenshots
CREATE TABLE IF NOT EXISTS `screenshots` (
  `photoId` int(11) NOT NULL AUTO_INCREMENT,
  `extensionId` int(11) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`photoId`),
  KEY `Screenshots_fk0` (`extensionId`),
  CONSTRAINT `Screenshots_fk0` FOREIGN KEY (`extensionId`) REFERENCES `extensions` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.tags
CREATE TABLE IF NOT EXISTS `tags` (
  `tagId` int(11) NOT NULL AUTO_INCREMENT,
  `tagTitle` varchar(15) NOT NULL,
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(20) NOT NULL,
  `enabled` int(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.user_profiles
CREATE TABLE IF NOT EXISTS `user_profiles` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `UserProfile_fk0` FOREIGN KEY (`email`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `userRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`userRoleId`),
  KEY `UserRoles_fk0` (`username`),
  CONSTRAINT `UserRoles_fk0` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;