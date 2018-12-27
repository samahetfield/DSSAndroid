-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-05-2018 a las 15:25:52
-- Versión del servidor: 10.1.32-MariaDB
-- Versión de PHP: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `consorcio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `farmacia`
--

CREATE TABLE `farmacia` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LATITUD` float DEFAULT NULL,
  `LONGITUD` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `farmacia`
--

INSERT INTO `farmacia` (`ID`, `NOMBRE`, `LATITUD`, `LONGITUD`) VALUES
(3, 'Farmacia Santa María', 37.1946, -3.62501);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `fk_producto` int(11) NOT NULL,
  `PRECIO` float NOT NULL,
  `fk_farmacia` int(11) NOT NULL,
  `fk_usuario` int(11) NOT NULL,
  `ID_ORDER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `orders`
--

INSERT INTO `orders` (`id`, `fk_producto`, `PRECIO`, `fk_farmacia`, `fk_usuario`, `ID_ORDER`) VALUES
(23, 8, 0, 3, 7, 0),
(24, 8, 0, 3, 7, 0),
(25, 8, 0, 3, 7, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `PRECIO` float DEFAULT NULL,
  `IMAGEN` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`ID`, `NOMBRE`, `CANTIDAD`, `PRECIO`, `IMAGEN`) VALUES
(7, 'Ibuprofeno', 20, 5, NULL),
(8, 'Propalgina', 50, 11, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `NOMBRE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NICK` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ROL` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`NOMBRE`, `NICK`, `PASS`, `ROL`, `EMAIL`, `ID`) VALUES
('Manuel Capel', 'Manuel', '81dc9bdb52d04dc20036dbd8313ed055', 'Usuario', 'manuelcapel@ugr.es', 7),
('isidoro', 'mcapel', '81dc9bdb52d04dc20036dbd8313ed055', 'Farmaceutico', 'mcaopeltu@gmail.com', 8),
('m.isidoro', 'mic', '81dc9bdb52d04dc20036dbd8313ed055', 'Farmaceutico', 'mcapel@ugr.es', 9),
('mic', 'mic2', '81dc9bdb52d04dc20036dbd8313ed055', 'Farmaceutico', 'mcapel@ugr.es', 10),
('mic3', 'micccc', '81dc9bdb52d04dc20036dbd8313ed055', 'Farmaceutico', 'mic3@ugr.es', 11),
('mcapeltu2', 'mcapeltu2', '81dc9bdb52d04dc20036dbd8313ed055', 'Farmaceutico', 'mcapeltu2@gmail.com', 12),
('manuel capel7', 'manu7', '81dc9bdb52d04dc20036dbd8313ed055', 'Farmaceutico', 'manu7', 13);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `farmacia`
--
ALTER TABLE `farmacia`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_producto` (`fk_producto`),
  ADD KEY `fk_farmacia` (`fk_farmacia`),
  ADD KEY `fk_usuario` (`fk_usuario`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `farmacia`
--
ALTER TABLE `farmacia`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `ORDERS_ibfk_1` FOREIGN KEY (`fk_producto`) REFERENCES `producto` (`ID`),
  ADD CONSTRAINT `fk_farmacia` FOREIGN KEY (`fk_farmacia`) REFERENCES `farmacia` (`ID`),
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`fk_usuario`) REFERENCES `user` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
