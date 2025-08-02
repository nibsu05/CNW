-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th8 02, 2025 lúc 04:32 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `cnw`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `Id` varchar(15) NOT NULL,
  `Name` varchar(15) DEFAULT NULL,
  `Description` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`Id`, `Name`, `Description`) VALUES
('C001', 'Hoa sinh nhật', NULL),
('C002', 'Hoa tình yêu', NULL),
('C003', 'Thiệp chúc mừng', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderitem`
--

CREATE TABLE `orderitem` (
  `Id` varchar(15) NOT NULL,
  `OrderId` varchar(15) NOT NULL,
  `ProductId` varchar(30) NOT NULL,
  `Quantity` int(30) NOT NULL,
  `Price` decimal(30,0) NOT NULL,
  `Note` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orderitem`
--

INSERT INTO `orderitem` (`Id`, `OrderId`, `ProductId`, `Quantity`, `Price`, `Note`) VALUES
('I003', 'O003', 'P003', 1, 150000, 'Giao buổi sáng'),
('OI001', 'O001', 'P001', 2, 150000, 'Giao trước 8h'),
('OI002', 'O001', 'P002', 1, 95000, 'Giao kèm thiệp'),
('OI003', 'O002', 'P003', 3, 200000, 'Ghi chú trống');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `Id` varchar(15) NOT NULL,
  `UserId` varchar(15) NOT NULL,
  `DeliveryTime` date DEFAULT current_timestamp(),
  `TotalPrice` decimal(30,0) NOT NULL,
  `DeliveryAddress` varchar(255) DEFAULT NULL,
  `Status` varchar(10) NOT NULL,
  `CreateAt` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`Id`, `UserId`, `DeliveryTime`, `TotalPrice`, `DeliveryAddress`, `Status`, `CreateAt`) VALUES
('O001', 'U001', '2025-08-12', 280000, 'Trung quốc', 'pending', '2025-07-16'),
('O002', 'U002', '2025-08-07', 500000, 'Gia lai', 'shipped', '2025-07-10'),
('O003', 'U001', '2025-08-06', 30000, 'Kontum', 'completed', '2025-07-08'),
('O004', 'U001', '2025-08-01', 280000, '123 Nguyễn Trãi, Hà Nội', 'pending', '2025-07-31'),
('O005', 'U002', '2025-08-01', 600000, '456 Lê Lợi, TP.HCM', 'shipped', '2025-07-30');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payment`
--

CREATE TABLE `payment` (
  `Id` varchar(15) NOT NULL,
  `OrderId` varchar(15) NOT NULL,
  `Method` varchar(30) NOT NULL,
  `Status` varchar(30) NOT NULL,
  `PaidAt` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `payment`
--

INSERT INTO `payment` (`Id`, `OrderId`, `Method`, `Status`, `PaidAt`) VALUES
('PM001', 'O001', 'Momo', 'Paid', '2025-08-01'),
('PM002', 'O002', 'COD', 'Pending', '2025-08-01'),
('PM003', 'O003', 'momo', 'paid', '2025-08-01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `Id` varchar(15) NOT NULL,
  `Name` varchar(15) NOT NULL,
  `Description` varchar(30) NOT NULL,
  `Price` decimal(30,0) NOT NULL,
  `Category` varchar(30) NOT NULL,
  `ImageUrl` varchar(30) NOT NULL,
  `Type` varchar(30) NOT NULL,
  `Stock` int(30) NOT NULL,
  `IsAvailable` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`Id`, `Name`, `Description`, `Price`, `Category`, `ImageUrl`, `Type`, `Stock`, `IsAvailable`) VALUES
('P001', 'Hoa Hồng Đỏ', 'Bó hoa hồng đỏ đẹp rực rỡ', 250000, 'C002', 'img/hoa_hong.jpg', 'flower', 100, 1),
('P002', 'Hoa Cẩm Chướng', 'Hoa cẩm chướng hồng ngọt ngào', 200000, 'C001', 'img/hoa_cam_chuong.jpg', 'flower', 50, 1),
('P003', 'Thiệp Happy Bir', 'Thiệp sinh nhật dễ thương', 30000, 'C003', 'img/thiep_birthday.jpg', 'card', 200, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `Id` varchar(15) NOT NULL,
  `Name` varchar(15) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `Phone` varchar(30) NOT NULL,
  `Address` varchar(30) NOT NULL,
  `Role` tinyint(30) NOT NULL,
  `CreateAt` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`Id`, `Name`, `Email`, `Password`, `Phone`, `Address`, `Role`, `CreateAt`) VALUES
('U001', 'Nguyễn A', 'a@gmail.com', '123456', '0901111111', 'Hà Nội', 0, '2025-08-01'),
('U002', 'Trần B', 'b@gmail.com', 'abcdef', '0902222222', 'TP.HCM', 0, '2025-08-01'),
('U006', 'Ngọc Ánh', 'Nam@gmail.com', 'pass123', '0123456789', 'Hải Phòng', 1, '2025-08-01');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Id`);

--
-- Chỉ mục cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `OrderId` (`OrderId`),
  ADD KEY `ProductId` (`ProductId`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `orders_ibfk_1` (`UserId`);

--
-- Chỉ mục cho bảng `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `OrderId` (`OrderId`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Category` (`Category`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Id`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`Id`),
  ADD CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`ProductId`) REFERENCES `product` (`Id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `user` (`Id`);

--
-- Các ràng buộc cho bảng `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`Id`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`Category`) REFERENCES `category` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
