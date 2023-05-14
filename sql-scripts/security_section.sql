-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Ned 14. kvě 2023, 15:56
-- Verze serveru: 10.4.17-MariaDB
-- Verze PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `security_section`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `role`
--

CREATE TABLE `role` (
  `id` int(10) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_BASIC');

-- --------------------------------------------------------

--
-- Struktura tabulky `user`
--

CREATE TABLE `user` (
  `id` int(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `password` varchar(80) NOT NULL,
  `last_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Vypisuji data pro tabulku `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `first_name`, `password`, `last_name`) VALUES
(1, 'thorzard', 'MartinVahala@seznam.cz', 'Martin', '$2a$10$rwDxnwY1fTn3fm8QS5PncOCIQ6CsLZvntMxlUjSD5wMasuXuMc75y', 'Vahala'),
(24, 'martin', 'thorzard@gmail.com', 'Martin', '$2a$10$8OZqQJ5xcp/09uHkCXXjsefKUFaeMB4bjeduF0QUwUhC9rS6hDNq2', 'Vahala'),
(43, 'john', 'john.wick@gmail.com', 'john', '$2a$10$6tOVOIOTjF.76esq6QbbLu/FtItjhlIHPOgvGvo9iGatCJm.60o0a', 'wick'),
(44, 'burian', 'burian@email.cz', 'burian', '$2a$10$DAJjFpYEhHJGcheu99U5gutNjVArcsisNFLG0LekqjjQRptKdfk2C', 'burian'),
(45, 'MaryV', 'MarieVahalova@seznam.cz', 'Marie', '$2a$10$xgeHhiqWEpK.3r5SeSmqwepTVR.2qR3SMMDOY3vjxJkp.Z4JOGu8.', 'Vahalová'),
(46, 'zbynek', 'zbynek@email.cz', 'zbynek', '$2a$10$r23aktTlFi3g/KZvZVcgmugMDfcSty.9an/0hpZ7kdNHR3LP2pG1W', 'Analripper'),
(47, 'test123', 'test@gmail.com', 'Test', '$2a$10$RfZ0JHDdt1F9A4btVs.LfutIpNSOfCe3.paMph/eqWPbSfxSRq7H2', 'Test'),
(48, 'gordon11', 'gordon@freeman.com', 'Gordon', '$2a$10$7aiy3MEExoY6XsGbI6c4weoKIlCS.2bqNCy4fOx26kIIkr4pY4KUa', 'Freeman');

-- --------------------------------------------------------

--
-- Struktura tabulky `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Vypisuji data pro tabulku `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 2),
(44, 2),
(45, 2),
(46, 2),
(47, 2),
(48, 2);

--
-- Klíče pro exportované tabulky
--

--
-- Klíče pro tabulku `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Klíče pro tabulku `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Klíče pro tabulku `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FK_ROLE_idx` (`role_id`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `role`
--
ALTER TABLE `role`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pro tabulku `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
