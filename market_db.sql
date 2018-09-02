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

-- Data exporting was unselected.
-- Dumping structure for table market_db.users
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` int(1) NOT NULL DEFAULT 1,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `userId` (`userId`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_profiles` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `market_db`.`users`
CHANGE COLUMN `enabled` `enabled` TINYINT(1) NOT NULL DEFAULT 1 ;

-- Data exporting was unselected.
-- Dumping structure for table market_db.user_profiles
CREATE TABLE `user_profiles` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.user_roles
CREATE TABLE `user_roles` (
  `userRoleId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL DEFAULT '',
  `role` varchar(45) NOT NULL DEFAULT '',
  PRIMARY KEY (`userRoleId`),
  KEY `UserRoles_fk0` (`username`),
  CONSTRAINT `UserRoles_fk0` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping structure for table market_db.extensions
CREATE TABLE `extensions` (
  `extensionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(3000) NOT NULL,
  `version` varchar(255) NOT NULL,
  `downloadsCount` int(11) NOT NULL,
  `fileName` varchar(255) NOT NULL DEFAULT '',
  `repoLink` varchar(255) NOT NULL,
  `openIssues` int(11) NOT NULL,
  `pullRequests` int(11) NOT NULL,
  `lastCommit` date NOT NULL,
  `ownerId` bigint(20) NOT NULL,
  `pending` tinyint(4) NOT NULL DEFAULT 1,
  `icon` varchar(255) DEFAULT NULL,
  `featured` tinyint(4) NOT NULL DEFAULT 0,
  `addedOn` date DEFAULT NULL,
  PRIMARY KEY (`extensionId`),
  KEY `Extensions_fk0` (`ownerId`),
  CONSTRAINT `Extensions_fk0` FOREIGN KEY (`ownerId`) REFERENCES `user_profiles` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `market_db`.`extensions` 
CHANGE COLUMN `description` `description` VARCHAR(3000) NOT NULL DEFAULT '' ;

-- Data exporting was unselected.
-- Dumping structure for table market_db.tags
CREATE TABLE IF NOT EXISTS `tags` (
  `tagId` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagTitle` varchar(15) NOT NULL,
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `market_db`.`tags` 
ADD UNIQUE INDEX `tagTitle_UNIQUE` (`tagTitle` ASC);


-- Data exporting was unselected.
-- Dumping structure for table market_db.extension_tags
CREATE TABLE IF NOT EXISTS `extension_tags` (
  `extension_tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `extensionId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  PRIMARY KEY (`extension_tag_id`),
  KEY `ExtensionTags_fk0` (`extensionId`),
  KEY `ExtensionTags_fk1` (`tagId`),
  CONSTRAINT `ExtensionTags_fk0` FOREIGN KEY (`extensionId`) REFERENCES `extensions` (`extensionId`),
  CONSTRAINT `ExtensionTags_fk1` FOREIGN KEY (`tagId`) REFERENCES `tags` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table market_db.screenshots
CREATE TABLE IF NOT EXISTS `screenshots` (
  `screenshotId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `extensionId` bigint(20) NOT NULL,
  `screenshotPath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`screenshotId`),
  KEY `Screenshots_fk0` (`extensionId`),
  CONSTRAINT `Screenshots_fk0` FOREIGN KEY (`extensionId`) REFERENCES `extensions` (`extensionId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
