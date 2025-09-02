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
  `date_time_til_blocked` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKepxc65n0y1pvqin3mc1fmsa65` (`name`),
  KEY `FKelgydbuplspqkmyifxbqrc6yb` (`owner_id`),
  CONSTRAINT `FKelgydbuplspqkmyifxbqrc6yb` FOREIGN KEY (`owner_id`) REFERENCES `host` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `adults_number` int NOT NULL,
  `children_number` int NOT NULL,
  `credit_card_number` varchar(255) NOT NULL,
  `datetime_end` timestamp(6) NOT NULL,
  `datetime_start` timestamp(6) NOT NULL,
  `rejection_comment` varchar(255) DEFAULT NULL,
  `special_demands` varchar(500) DEFAULT NULL,
  `status` enum('ACCEPTED_BY_OWNER','CANCELLED_BY_TOURIST','PENDING','REJECTED_BY_OWNER') NOT NULL,
  `cottage_id` bigint NOT NULL,
  `tourist_id` bigint NOT NULL,
  `cost` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ah0h9lg676oi9f07915yoqhs` (`tourist_id`),
  KEY `FK2oups78q7uaa1qpi4a8h4a0mf` (`cottage_id`),
  CONSTRAINT `FK2oups78q7uaa1qpi4a8h4a0mf` FOREIGN KEY (`cottage_id`) REFERENCES `cottage` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK3ah0h9lg676oi9f07915yoqhs` FOREIGN KEY (`tourist_id`) REFERENCES `tourist` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `feedback` (
  `comment` varchar(1000) DEFAULT NULL,
  `date_time_creation` datetime(6) NOT NULL,
  `rating` int NOT NULL,
  `reservation_id` bigint NOT NULL,
  PRIMARY KEY (`reservation_id`),
  CONSTRAINT `FKsjmfrqsfqj98f23ddsbnf4jhy` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user(id, password, username, user_type, status) VALUES(1, '$2a$10$RO6dlD2Mi5Ur76dd9VHz/uKZDNPoUoyqVbCCbuI2K.EJc9kTCzq5.', 'ana_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) VALUES(1);
INSERT INTO user(id, password, username, user_type, status) VALUES(2, '$2a$10$bnAEXqxSwgsiVeS7X4X/HeeR6sE7AaN3FHMIUUpk84ZmgzX5SyCy6', 'pera_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) VALUES(2);
INSERT INTO user(id, password, username, user_type, status) VALUES(3, '$2a$10$4N9c4UBUQGmGWzFa7UwcMekR.gOCoxnrIIdhZ1pQd4qElKU9zH2VS', 'marko_admin', 'ADMIN', 'ACCEPTED');
INSERT INTO admin(id) VALUES(3);

INSERT INTO user(id, username, password, user_type, status) VALUES(4, 'maja', '$2a$10$86Is5xS5CQ2wHEy7MMr9GOR/TZ63qyaDTCLCYIq0VLI4l9klySlLu', 'TOURIST', 'ACCEPTED');
INSERT INTO nonadmin(id, firstname, lastname, gender, address, phone_number, email, profile_picture_path, credit_card_number, credit_card_number_last4digits)
VALUES(4, 'Maja', 'Savić', 'Ж', 'Kneza Miloša 10', '+381675859404',	'maja@outlook.com',	'uploads/profile-pic/maja1756592959195.jpg', 'A5rfF5/gOMa7E/KX3v0ynQ==', '3333');
INSERT INTO tourist(id) VALUES(4);

INSERT INTO user(id, username, password, user_type, status) VALUES(5, 'uki', '$2a$10$Hwz1TTNCbYQipQGGR1WwaeRK6GPGML3Z7Kdzoh051yoOph8lzD/EG', 'TOURIST', 'ACCEPTED');
INSERT INTO nonadmin(id, firstname, lastname, gender, address, phone_number, email, profile_picture_path, credit_card_number, credit_card_number_last4digits)
VALUES(5, 'Uroš', 'Urošević', 'М', 'Kraljice Marije 5',	'+381658980999', 'uki-7@gmail.com',	'uploads/profile-pic/uki1756593057148.jpg', 'enXSTyaVP5yH1vNqaEWv/m+Lkg7jxM0eu40plKc00vg=', '3333');
INSERT INTO tourist(id) VALUES(5);

INSERT INTO user(id, username, password, user_type, status) VALUES(6, 'sara', '$2a$10$Mhaibqk5qF37XjnjmQeEMusvMI/NZK1UFrFgSjkOQuLjIF9c9FKH.', 'HOST', 'ACCEPTED');
INSERT INTO nonadmin(id, firstname, lastname, gender, address, phone_number, email, profile_picture_path, credit_card_number, credit_card_number_last4digits)
VALUES(6, 'Sara', 'Sandrić', 'Ж', 'Vojislava Ilića 11', '+381607970657', 'sara5@yahoo.com',	'uploads/profile-pic/sara1756593428903.jpg', '2ynENeLPkgTqa3/PH01FSW+Lkg7jxM0eu40plKc00vg=', '3344');
INSERT INTO host(id) VALUES(6);

INSERT INTO user(id, username, password, user_type, status) VALUES(7, 'mika', '$2a$10$NxFTKo3.9tDIBX5jEkL5/uRtCk08ZZLUWk76ac0zH6.RMN.27437e', 'HOST', 'ACCEPTED');
INSERT INTO nonadmin(id, firstname, lastname, gender, address, phone_number, email, profile_picture_path, credit_card_number, credit_card_number_last4digits)
VALUES(7, 'Mika', 'Mikić', 'М', 'Kralja Petra I 6', '+381601123456', 'mika@gmail.com', 'images/male-pic.png', 'ttyin78pu981xJwk/ndvdW+Lkg7jxM0eu40plKc00vg=', '3344');
INSERT INTO host(id) VALUES(7);

INSERT INTO user(id, username, password, user_type, status) VALUES(8, 'laza', '$2a$10$XGa60B0LCw3ZpUSF6yoof.bx1zHOY9W/hA7noPUQnCTlOHpehPSFi', 'TOURIST', 'PENDING');
INSERT INTO nonadmin(id, firstname, lastname, gender, address, phone_number, email, profile_picture_path, credit_card_number, credit_card_number_last4digits)
VALUES(8, 'Lazar', 'Lazarević', 'М', 'Južni bulevar 12', '+381607878080', 'laza@outlook.com', 'uploads/profile-pic/laza1756593669541.jpg', 'MkuiWvNrovWEtXQ8/dhvEw==', '3334');
INSERT INTO tourist(id) VALUES(8);

INSERT INTO cottage(id, name, location, capacity, photos_folder_path, services, summer_price_adult, summer_price_child, winter_price_adult, winter_price_child, phone_number, latitude, longitude, owner_id)
VALUES(1, 'Планинска кућа Aurora', 'Златибор', 6, 'uploads/cottage-photos/sara_Планинска_кућа_Aurora_Златибор', 'Сауна, WiFi, Паркинг',	4000, 2500,	4550, 3000,	'+381601234567', 43.731, 19.701, 6);
INSERT INTO cottage(id, name, location, capacity, photos_folder_path, services, summer_price_adult, summer_price_child, winter_price_adult, winter_price_child, phone_number, latitude, longitude, owner_id)
VALUES(2, 'Викендица Морава', 'Гоч', 4, 'uploads/cottage-photos/sara_Викендица_Морава_Гоч', 'Роштиљ, Клима, ТВ', 4500, 3070, 5000, 3500, '+381641112223', 43.565, 20.753, 6);

INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(1, 'uploads/cottage-photos/sara_Планинска_кућа_Aurora_Златибор/01756597868178.jpg', 0, 1);
INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(2, 'uploads/cottage-photos/sara_Планинска_кућа_Aurora_Златибор/11756597868190.jpg', 1, 1);
INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(3, 'uploads/cottage-photos/sara_Планинска_кућа_Aurora_Златибор/21756597868194.jpg', 2, 1);
INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(4, 'uploads/cottage-photos/sara_Планинска_кућа_Aurora_Златибор/31756597868197.jpg', 3, 1);
INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(5, 'uploads/cottage-photos/sara_Планинска_кућа_Aurora_Златибор/41756597868200.jpg', 4, 1);

INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(6, 'uploads/cottage-photos/sara_Викендица_Морава_Гоч/01756597880742.jpg', 0, 2);
INSERT INTO cottage_photo(id, photo_path, position, cottage_id)
VALUES(7, 'uploads/cottage-photos/sara_Викендица_Морава_Гоч/11756597880746.jpg', 1, 2);

INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(1, 1, 5, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-05-02 07:59:00.000000', '2025-05-01 12:00:00.000000',	'ACCEPTED_BY_OWNER', 1, 5, 16500);
INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(2, 1, 5, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-08-31 07:59:00.000000', '2025-08-28 12:00:00.000000',	'ACCEPTED_BY_OWNER', 1, 5, 16500);
INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(3, 1, 5, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-06-02 07:59:00.000000', '2025-06-01 12:00:00.000000',	'CANCELLED_BY_TOURIST', 1, 4, 16500);
INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, rejection_comment, cottage_id, tourist_id, cost)
VALUES(4, 1, 5, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-06-20 07:59:00.000000', '2025-06-19 12:00:00.000000',	'REJECTED_BY_OWNER', 'Renoviranje vikendice je tada u toku', 1, 4, 16500);
INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(5, 1, 5, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-10-02 07:59:00.000000', '2025-10-01 12:00:00.000000',	'PENDING', 1, 5, 19550);
INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(6, 1, 5, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-11-02 07:59:00.000000', '2025-11-01 12:00:00.000000',	'ACCEPTED_BY_OWNER', 1, 5, 19550);

INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(7, 1, 0, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-07-02 07:59:00.000000', '2025-07-01 12:00:00.000000',	'ACCEPTED_BY_OWNER', 2, 4, 4500);
INSERT INTO reservation(id, adults_number, children_number, credit_card_number, datetime_end, datetime_start, status, cottage_id, tourist_id, cost)
VALUES(8, 1, 0, 'b4uSDuPEzR67jSmUpzTS+A==',	'2025-08-04 07:59:00.000000', '2025-08-03 12:00:00.000000',	'ACCEPTED_BY_OWNER', 1, 4, 4500);

INSERT INTO feedback(reservation_id, rating, comment, date_time_creation)
VALUES(1, 2, 'Sve super', '2025-08-31 12:39:00.000000');
INSERT INTO feedback(reservation_id, rating, comment, date_time_creation)
VALUES(2, 2, NULL, '2025-08-30 12:39:00.000000');

INSERT INTO feedback(reservation_id, rating, comment, date_time_creation)
VALUES(7, 5, 'Odlično', '2025-08-31 12:39:00.000000');
