CREATE DATABASE `mountain_cottage` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mountain_cottage`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_type` enum('ADMIN','HOST','TOURIST') NOT NULL,
  `status` enum('ACCEPTED','PENDING','REJECTED','DEACTIVATED') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `nonadmin` (
  `id` bigint NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `profile_picture_path` varchar(255) DEFAULT NULL,
  `credit_card_number` varchar(255) NOT NULL,
  `credit_card_number_last4digits` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2gaakxbb7c6odt4p8s9qiqlgt` (`email`),
  CONSTRAINT `FKgup9dfhoc9lh39uwr3604jbbv` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `tourist` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKdwsk1mbn3asxosudc5xm5nptc` FOREIGN KEY (`id`) REFERENCES `nonadmin` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `host` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK78spe6xwbf8a34fiml3afe06s` FOREIGN KEY (`id`) REFERENCES `nonadmin` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `admin` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK1ja8rua032fgnk9jmq7du3b3a` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `cottage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `capacity` int NOT NULL,
  `photos_folder_path` varchar(255) DEFAULT NULL,
  `services` varchar(500) DEFAULT NULL,
  `summer_price_adult` double NOT NULL,
  `summer_price_child` double NOT NULL,
  `winter_price_adult` double NOT NULL,
  `winter_price_child` double NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKepxc65n0y1pvqin3mc1fmsa65` (`name`),
  KEY `FKelgydbuplspqkmyifxbqrc6yb` (`owner_id`),
  CONSTRAINT `FKelgydbuplspqkmyifxbqrc6yb` FOREIGN KEY (`owner_id`) REFERENCES `host` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `cottage_photo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `photo_path` varchar(255) NOT NULL,
  `position` int NOT NULL,
  `cottage_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjj9i1ygqtgbmu5yfa2y73qfc3` (`cottage_id`),
  CONSTRAINT `FKjj9i1ygqtgbmu5yfa2y73qfc3` FOREIGN KEY (`cottage_id`) REFERENCES `cottage` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tourist_id` bigint NOT NULL,
  `cottage_id` bigint NOT NULL,
  `datetime_start` datetime(6) NOT NULL,
  `datetime_end` datetime(6) NOT NULL,
  `adults_number` int NOT NULL,
  `children_number` int NOT NULL,
  `special_demands` varchar(500) NOT NULL,
  `credit_card_number` varchar(255) NOT NULL,
  `status` enum('ACCEPTED_BY_OWNER','CANCELLED_BY_TOURIST','PENDING','REJECTED_BY_OWNER') NOT NULL,
  `rejection_comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ah0h9lg676oi9f07915yoqhs` (`tourist_id`),
  KEY `FK2oups78q7uaa1qpi4a8h4a0mf` (`cottage_id`),
  CONSTRAINT `FK2oups78q7uaa1qpi4a8h4a0mf` FOREIGN KEY (`cottage_id`) REFERENCES `cottage` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK3ah0h9lg676oi9f07915yoqhs` FOREIGN KEY (`tourist_id`) REFERENCES `tourist` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `feedback` (
  `reservation_id` bigint NOT NULL,
  `rating` int NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`reservation_id`),
  CONSTRAINT `FKsjmfrqsfqj98f23ddsbnf4jhy` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO user(password, username, user_type, status) VALUES('$2a$10$RO6dlD2Mi5Ur76dd9VHz/uKZDNPoUoyqVbCCbuI2K.EJc9kTCzq5.', 'ana_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) SELECT MAX(id) FROM user;
INSERT INTO user(password, username, user_type, status) VALUES('$2a$10$bnAEXqxSwgsiVeS7X4X/HeeR6sE7AaN3FHMIUUpk84ZmgzX5SyCy6', 'pera_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) SELECT MAX(id) FROM user;
INSERT INTO user(password, username, user_type, status) VALUES('$2a$10$4N9c4UBUQGmGWzFa7UwcMekR.gOCoxnrIIdhZ1pQd4qElKU9zH2VS', 'marko_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) SELECT MAX(id) FROM user;
