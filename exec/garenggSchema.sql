CREATE TABLE `api_key` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `api_key` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `champion` (
  `champion_key` bigint NOT NULL,
  `blurb` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `id` varchar(255) DEFAULT NULL,
  `full` varchar(255) DEFAULT NULL,
  `image_group` varchar(255) DEFAULT NULL,
  `h` bigint DEFAULT NULL,
  `sprite` varchar(255) DEFAULT NULL,
  `w` bigint DEFAULT NULL,
  `x` bigint DEFAULT NULL,
  `y` bigint DEFAULT NULL,
  `attack` bigint DEFAULT NULL,
  `defense` bigint DEFAULT NULL,
  `difficulty` bigint DEFAULT NULL,
  `magic` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `partype` varchar(255) DEFAULT NULL,
  `armor` double DEFAULT NULL,
  `armorperlevel` double DEFAULT NULL,
  `attackdamage` double DEFAULT NULL,
  `attackdamageperlevel` double DEFAULT NULL,
  `attackrange` double DEFAULT NULL,
  `attackspeed` double DEFAULT NULL,
  `attackspeedperlevel` double DEFAULT NULL,
  `crit` double DEFAULT NULL,
  `critperlevel` double DEFAULT NULL,
  `hp` double DEFAULT NULL,
  `hpperlevel` double DEFAULT NULL,
  `hpregen` double DEFAULT NULL,
  `hpregenperlevel` double DEFAULT NULL,
  `movespeed` double DEFAULT NULL,
  `mp` double DEFAULT NULL,
  `mpperlevel` double DEFAULT NULL,
  `mpregen` double DEFAULT NULL,
  `mpregenperlevel` double DEFAULT NULL,
  `spellblock` double DEFAULT NULL,
  `spellblockperlevel` double DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`champion_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `champion_tags` (
  `champion_champion_key` bigint NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FKbu0kvmj53csw05nr191xk04nv` (`champion_champion_key`),
  CONSTRAINT `FKbu0kvmj53csw05nr191xk04nv` FOREIGN KEY (`champion_champion_key`) REFERENCES `champion` (`champion_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `duo_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `champion1` bigint NOT NULL,
  `champion2` bigint NOT NULL,
  `defeat` bigint DEFAULT NULL,
  `lane1` varchar(255) NOT NULL,
  `lane2` varchar(255) NOT NULL,
  `total_match` bigint DEFAULT NULL,
  `victory` bigint DEFAULT NULL,
  `win_rate` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_champion1` (`champion1`,`lane1`),
  KEY `idx_champion2` (`champion2`,`lane2`)
) ENGINE=InnoDB AUTO_INCREMENT=46969 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `duo_record_match_id` (
  `match_id` varchar(255) NOT NULL,
  `duo_record_id` bigint NOT NULL,
  PRIMARY KEY (`duo_record_id`,`match_id`),
  CONSTRAINT `FKhh2n7elgxdwkvug7nca0ni72w` FOREIGN KEY (`duo_record_id`) REFERENCES `duo_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `player_info` (
  `player_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `api_key_id` bigint DEFAULT NULL,
  `fresh_blood` bit(1) NOT NULL,
  `hot_streak` bit(1) NOT NULL,
  `inactive` bit(1) NOT NULL,
  `league_id` varchar(255) DEFAULT NULL,
  `league_points` int NOT NULL,
  `losses` int NOT NULL,
  `puuid` varchar(255) DEFAULT NULL,
  `queue_type` varchar(255) DEFAULT NULL,
  `rank_num` varchar(255) DEFAULT NULL,
  `summoner_id` varchar(255) DEFAULT NULL,
  `summoner_name` varchar(255) DEFAULT NULL,
  `tag_line` varchar(255) DEFAULT NULL,
  `tier` varchar(255) DEFAULT NULL,
  `veteran` bit(1) NOT NULL,
  `wins` int NOT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE KEY `UKg962olqqvtohx0fswn2eq03bt` (`summoner_name`,`tag_line`)
) ENGINE=InnoDB AUTO_INCREMENT=123666 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ranking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `score` int NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `game_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmv96mw7v3tmga1lwbat4d16qp` (`game_id`),
  CONSTRAINT `FKmv96mw7v3tmga1lwbat4d16qp` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=437 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;