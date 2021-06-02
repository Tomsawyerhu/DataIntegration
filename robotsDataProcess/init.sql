DROP DATABASE IF EXISTS `robots`;
CREATE DATABASE `robots`;
USE `robots`;
 
DROP TABLE IF EXISTS `logins`;
DROP TABLE IF EXISTS `actions`;

CREATE TABLE `logins` (
    `sessionid` VARCHAR(255) NOT NULL,
    `logintime` DATETIME(6) NOT NULL,
    `ip` VARCHAR(255) NOT NULL,
    `userid` VARCHAR(255) NOT NULL,
    `passwd` VARCHAR(255) NOT NULL,
    `authcode` VARCHAR(255) NOT NULL,
    `success` INT(1) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `actions`(
    `sessionid` VARCHAR(255) NOT NULL,
    `actiontime` DATETIME(6) NOT NULL,
    `actiontype` VARCHAR(255) NOT NULL,
    `userid` VARCHAR(255) NOT NULL,
    `itemid` VARCHAR(255) NOT NULL,
    `categoryid` VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;