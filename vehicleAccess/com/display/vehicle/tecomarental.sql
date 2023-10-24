-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 16, 2023 at 10:52 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tecomarental`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart_table`
--

CREATE TABLE `cart_table` (
  `vName` varchar(60) NOT NULL,
  `vPlate` varchar(60) NOT NULL,
  `vPrice` varchar(60) NOT NULL,
  `RentedTo` varchar(60) NOT NULL,
  `rentID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer_cred`
--

CREATE TABLE `customer_cred` (
  `customerID` int(11) NOT NULL,
  `customerName` text NOT NULL,
  `customerDOB` date NOT NULL DEFAULT current_timestamp(),
  `customerEmail` varchar(50) NOT NULL,
  `customerPhone` int(50) NOT NULL,
  `customerPassword` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_cred`
--

INSERT INTO `customer_cred` (`customerID`, `customerName`, `customerDOB`, `customerEmail`, `customerPhone`, `customerPassword`) VALUES
(1, 'victor', '2023-07-16', 'vkimanga@gmail.com ', 717999529, '123456789'),
(2, 'victor kimanga', '2023-07-16', 'vkimanga@gmail.com', 717999529, 'Headglitter761'),
(3, 'victor kimanga', '2023-07-16', 'vkimanga@gmail.com', 717999529, 'Headglitter761'),
(4, 'victor kimanga', '2023-07-16', 'vkimanga@gmail.com', 717999529, 'iamgay');

-- --------------------------------------------------------

--
-- Table structure for table `cust_temp`
--

CREATE TABLE `cust_temp` (
  `nameValue` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff_cred`
--

CREATE TABLE `staff_cred` (
  `staff_ID` int(50) NOT NULL,
  `staffName` varchar(50) NOT NULL,
  `staffPhone` int(50) NOT NULL,
  `staffPassword` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff_cred`
--

INSERT INTO `staff_cred` (`staff_ID`, `staffName`, `staffPhone`, `staffPassword`) VALUES
(1234567, 'victor Kimanga', 123456789, '123456789');

-- --------------------------------------------------------

--
-- Table structure for table `staff_temp`
--

CREATE TABLE `staff_temp` (
  `nameValue` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_table`
--

CREATE TABLE `vehicle_table` (
  `vehicleName` varchar(60) NOT NULL,
  `vehiclePlate` varchar(60) NOT NULL,
  `vehiclePrice` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle_table`
--

INSERT INTO `vehicle_table` (`vehicleName`, `vehiclePlate`, `vehiclePrice`) VALUES
('Aston Martin', 'KBF4765', '47769700'),
('Buggati', 'KCD4765', '300000'),
('Lambogini Aventador', 'KCA5764', '2420309'),
('Pagani Zonda', 'KDA4657', '6869707'),
('Toyota Supra', 'KBR5574', '3454352');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart_table`
--
ALTER TABLE `cart_table`
  ADD PRIMARY KEY (`rentID`);

--
-- Indexes for table `customer_cred`
--
ALTER TABLE `customer_cred`
  ADD PRIMARY KEY (`customerID`);

--
-- Indexes for table `cust_temp`
--
ALTER TABLE `cust_temp`
  ADD PRIMARY KEY (`nameValue`);

--
-- Indexes for table `staff_cred`
--
ALTER TABLE `staff_cred`
  ADD PRIMARY KEY (`staff_ID`);

--
-- Indexes for table `staff_temp`
--
ALTER TABLE `staff_temp`
  ADD PRIMARY KEY (`nameValue`);

--
-- Indexes for table `vehicle_table`
--
ALTER TABLE `vehicle_table`
  ADD PRIMARY KEY (`vehicleName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart_table`
--
ALTER TABLE `cart_table`
  MODIFY `rentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `customer_cred`
--
ALTER TABLE `customer_cred`
  MODIFY `customerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
