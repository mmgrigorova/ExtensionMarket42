# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.5-10.3.8-MariaDB)
# Database: market_db
# Generation Time: 2018-08-21 15:47:17 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table extension_tags
# ------------------------------------------------------------

DROP TABLE IF EXISTS `extension_tags`;

CREATE TABLE `extension_tags` (
  `extension_tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `extensionId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  PRIMARY KEY (`extension_tag_id`),
  KEY `ExtensionTags_fk0` (`extensionId`),
  KEY `ExtensionTags_fk1` (`tagId`),
  CONSTRAINT `ExtensionTags_fk0` FOREIGN KEY (`extensionId`) REFERENCES `extensions` (`extensionId`),
  CONSTRAINT `ExtensionTags_fk1` FOREIGN KEY (`tagId`) REFERENCES `tags` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table extensions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `extensions`;

CREATE TABLE `extensions` (
  `extensionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `downloadsCount` int(11) NOT NULL,
  `downloadLink` varchar(255) NOT NULL,
  `repoLink` varchar(255) NOT NULL,
  `openIssues` int(11) NOT NULL,
  `pullRequests` int(11) NOT NULL,
  `lastCommit` date NOT NULL,
  `ownerId` bigint(20) NOT NULL,
  `pending` tinyint(4) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `featured` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`extensionId`),
  KEY `Extensions_fk0` (`ownerId`),
  CONSTRAINT `Extensions_fk0` FOREIGN KEY (`ownerId`) REFERENCES `user_profiles` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table screenshots
# ------------------------------------------------------------

DROP TABLE IF EXISTS `screenshots`;

CREATE TABLE `screenshots` (
  `screenshotId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `extensionId` bigint(20) NOT NULL,
  `screenshotPath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`screenshotId`),
  KEY `Screenshots_fk0` (`extensionId`),
  CONSTRAINT `Screenshots_fk0` FOREIGN KEY (`extensionId`) REFERENCES `extensions` (`extensionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table tags
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tags`;

CREATE TABLE `tags` (
  `tagId` bigint(20) NOT NULL AUTO_INCREMENT,
  `tagTitle` varchar(15) NOT NULL,
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_profiles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_profiles`;

CREATE TABLE `user_profiles` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE `user_roles` (
  `userRoleId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`userRoleId`),
  KEY `UserRoles_fk0` (`username`),
  CONSTRAINT `UserRoles_fk0` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` int(1) NOT NULL DEFAULT 1,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `userId` (`userId`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_profiles` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
