CREATE DATABASE `mountain_cottage` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mountain_cottage`;
CREATE TABLE `cottage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `user_type` enum('ADMIN','HOST','TOURIST') NOT NULL,
  `status` enum('ACCEPTED','PENDING','REJECTED','DEACTIVATED') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `nonadmin` (
  `address` varchar(255) NOT NULL,
  `credit_card_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `profile_picture_path` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
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
INSERT INTO user(password, username, user_type, status) VALUES('$2a$10$RO6dlD2Mi5Ur76dd9VHz/uKZDNPoUoyqVbCCbuI2K.EJc9kTCzq5.', 'ana_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) SELECT MAX(id) FROM user;
INSERT INTO user(password, username, user_type, status) VALUES('$2a$10$bnAEXqxSwgsiVeS7X4X/HeeR6sE7AaN3FHMIUUpk84ZmgzX5SyCy6', 'pera_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) SELECT MAX(id) FROM user;
INSERT INTO user(password, username, user_type, status) VALUES('$2a$10$4N9c4UBUQGmGWzFa7UwcMekR.gOCoxnrIIdhZ1pQd4qElKU9zH2VS', 'marko_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) SELECT MAX(id) FROM user;
