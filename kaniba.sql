-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 31. Mai 2016 um 17:03
-- Server-Version: 10.1.9-MariaDB
-- PHP-Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `kaniba`
--
CREATE DATABASE IF NOT EXISTS `kaniba` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `kaniba`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `answers`
--

DROP TABLE IF EXISTS `answers`;
CREATE TABLE `answers` (
  `answerID` int(11) NOT NULL,
  `questionID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `answerString` text COLLATE utf8_german2_ci NOT NULL,
  `isText` tinyint(1) NOT NULL,
  `answerBool` tinyint(1) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bars`
--

DROP TABLE IF EXISTS `bars`;
CREATE TABLE `bars` (
  `barID` int(11) UNSIGNED NOT NULL,
  `generalRating` int(11) NOT NULL,
  `pprRating` int(11) NOT NULL,
  `musicRating` int(11) NOT NULL,
  `peopleRating` int(11) NOT NULL,
  `atmosphereRating` int(11) NOT NULL,
  `ratingCount` int(11) NOT NULL DEFAULT '0',
  `lastUpdated` datetime(6) NOT NULL,
  `userID` int(11) NOT NULL,
  `city` text COLLATE utf8_german2_ci NOT NULL,
  `street` text COLLATE utf8_german2_ci NOT NULL,
  `number` text COLLATE utf8_german2_ci NOT NULL,
  `zip` varchar(10) COLLATE utf8_german2_ci NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COLLATE utf8_german2_ci,
  `name` text COLLATE utf8_german2_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `messageID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `barID` int(11) NOT NULL,
  `message` text COLLATE utf8_german2_ci NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `questions`
--

DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `questionID` int(11) NOT NULL,
  `barID` int(11) NOT NULL,
  `text` tinyint(1) NOT NULL,
  `message` text COLLATE utf8_german2_ci NOT NULL,
  `asked` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ratings`
--

DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
  `ratingID` int(11) NOT NULL,
  `userID` bigint(20) NOT NULL,
  `barID` int(11) NOT NULL,
  `generalRating` int(11) NOT NULL,
  `pprRating` smallint(6) NOT NULL,
  `musicRating` smallint(6) NOT NULL,
  `peopleRating` smallint(6) NOT NULL,
  `atmosphereRating` smallint(6) NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `specials`
--

DROP TABLE IF EXISTS `specials`;
CREATE TABLE `specials` (
  `specialID` int(11) NOT NULL,
  `barID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `message` text COLLATE utf8_german2_ci NOT NULL,
  `begin` datetime NOT NULL,
  `end` datetime NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tags`
--

DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `tagID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `barID` int(11) NOT NULL,
  `name` varchar(10) COLLATE utf8_german2_ci NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `name` text COLLATE utf8_german2_ci NOT NULL,
  `firstname` text COLLATE utf8_german2_ci NOT NULL,
  `email` text COLLATE utf8_german2_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_german2_ci NOT NULL,
  `sessionID` varchar(32) COLLATE utf8_german2_ci NOT NULL,
  `birthdate` date NOT NULL,
  `city` text COLLATE utf8_german2_ci NOT NULL,
  `street` text COLLATE utf8_german2_ci NOT NULL,
  `number` varchar(10) COLLATE utf8_german2_ci NOT NULL,
  `zip` varchar(10) COLLATE utf8_german2_ci NOT NULL,
  `isAdmin` tinyint(4) NOT NULL DEFAULT '0',
  `lastLogin` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`userID`, `name`, `firstname`, `email`, `password`, `sessionID`, `birthdate`, `city`, `street`, `number`, `zip`, `isAdmin`, `lastLogin`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', 'admin', '2015-11-26', 'admin', 'admin', 'admin', 'admin', 1, '2015-11-26 00:00:00');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`answerID`),
  ADD KEY `answerID` (`answerID`);

--
-- Indizes für die Tabelle `bars`
--
ALTER TABLE `bars`
  ADD PRIMARY KEY (`barID`);

--
-- Indizes für die Tabelle `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`messageID`);

--
-- Indizes für die Tabelle `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`questionID`);

--
-- Indizes für die Tabelle `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`ratingID`);

--
-- Indizes für die Tabelle `specials`
--
ALTER TABLE `specials`
  ADD PRIMARY KEY (`specialID`);

--
-- Indizes für die Tabelle `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`tagID`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `answers`
--
ALTER TABLE `answers`
  MODIFY `answerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT für Tabelle `bars`
--
ALTER TABLE `bars`
  MODIFY `barID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT für Tabelle `message`
--
ALTER TABLE `message`
  MODIFY `messageID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT für Tabelle `ratings`
--
ALTER TABLE `ratings`
  MODIFY `ratingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT für Tabelle `specials`
--
ALTER TABLE `specials`
  MODIFY `specialID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `tags`
--
ALTER TABLE `tags`
  MODIFY `tagID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
