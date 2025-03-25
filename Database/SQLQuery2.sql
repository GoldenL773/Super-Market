USE [master]
GO
/****** Object:  Database [SuperMarket]    Script Date: 3/7/2025 11:36:47 PM ******/
CREATE DATABASE [SuperMarket]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'SuperMarket', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\SuperMarket.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'SuperMarket_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\SuperMarket_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [SuperMarket] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SuperMarket].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [SuperMarket] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [SuperMarket] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [SuperMarket] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [SuperMarket] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [SuperMarket] SET ARITHABORT OFF 
GO
ALTER DATABASE [SuperMarket] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [SuperMarket] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [SuperMarket] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [SuperMarket] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [SuperMarket] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [SuperMarket] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [SuperMarket] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [SuperMarket] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [SuperMarket] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [SuperMarket] SET  ENABLE_BROKER 
GO
ALTER DATABASE [SuperMarket] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [SuperMarket] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [SuperMarket] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [SuperMarket] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [SuperMarket] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [SuperMarket] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [SuperMarket] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [SuperMarket] SET RECOVERY full 
GO
ALTER DATABASE [SuperMarket] SET  MULTI_USER 
GO
ALTER DATABASE [SuperMarket] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [SuperMarket] SET DB_CHAINING OFF 
GO
ALTER DATABASE [SuperMarket] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [SuperMarket] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [SuperMarket] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [SuperMarket] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [SuperMarket] SET QUERY_STORE = ON
GO
ALTER DATABASE [SuperMarket] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [SuperMarket]
GO
/****** Object:  Table [dbo].[Cart]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cart](
	[cart_id] [int] IDENTITY(1,1) NOT NULL,
	[customer_id] [int] NULL,
	[product_id] [int] NULL,
	[quantity] [int] NOT NULL,
	[added_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[cart_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[description] [nvarchar](max) NULL,
	[image] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[customer_id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[full_name] [nvarchar](100) NULL,
	[image] [nvarchar](max) NULL,
	[email] [varchar](100) NOT NULL,
	[phone_number] [varchar](15) NULL,
	[gender] [bit] NOT NULL,
	[address] [nvarchar](255) NULL,
	[role_id] [int] NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[customer_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Inventory]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Inventory](
	[inventory_id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[purchase_date] [date] NULL,
	[purchase_price] [decimal](10, 2) NOT NULL,
	[last_updated] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[inventory_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Order_details]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Order_details](
	[orderd_id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NULL,
	[product_id] [int] NULL,
	[quantity] [int] NOT NULL,
	[price] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[orderd_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[order_id] [int] IDENTITY(1,1) NOT NULL,
	[promotion_id] [int] NULL,
	[customer_id] [int] NOT NULL,
	[staff_id] [int] NULL,
	[total_amount] [decimal](10, 2) NOT NULL,
	[status] [nvarchar](20) NULL,
	[created_at] [datetime] NULL,
 CONSTRAINT [PK__Orders__465962294530511D] PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Payments]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Invoice](
	[invoice_id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NULL,
	[payment_method] [varchar](50) NOT NULL,
	[amount] [decimal](10, 2) NOT NULL,
	[status] [varchar](50) NOT NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[invoice_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Permissions]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Permissions](
	[permission_id] [int] IDENTITY(1,1) NOT NULL,
	[permission_name] [nvarchar](50) NOT NULL,
	[url] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[permission_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[product_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[image] [nvarchar](max) NULL,
	[name] [nvarchar](100) NOT NULL,
	[description] [nvarchar](max) NULL,
	[price] [decimal](10, 2) NOT NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotion_Usage]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotion_Usage](
	[usage_id] [int] IDENTITY(1,1) NOT NULL,
	[promotion_id] [int] NOT NULL,
	[customer_id] [int] NOT NULL,
	[used_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[usage_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotions]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotions](
	[promotion_id] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](50) NOT NULL,
	[discount] [decimal](10, 2) NOT NULL,
	[valid_from] [date] NULL,
	[valid_to] [date] NULL,
	[max_usage] [int] NULL,
	[staff_id] [int] NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[promotion_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reviews]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reviews](
	[review_id] [int] IDENTITY(1,1) NOT NULL,
	[customer_id] [int] NULL,
	[product_id] [int] NULL,
	[rating] [int] NOT NULL,
	[comment] [text] NULL,
	[response] [text] NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[review_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[role_id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role_Permissions]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role_Permissions](
	[role_id] [int] NOT NULL,
	[permission_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC,
	[permission_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Staffs]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Staffs](
	[staff_id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[full_name] [nvarchar](100) NULL,
	[image] [nvarchar](max) NULL,
	[email] [varchar](100) NOT NULL,
	[phone_number] [varchar](15) NULL,
	[gender] [bit] NULL,
	[address] [nvarchar](255) NULL,
	[role_id] [int] NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[staff_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Stock_Entries]    Script Date: 3/7/2025 11:36:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Stock_Entries](
	[stock_entry_id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NOT NULL,
	[inventory_id] [int] NOT NULL,
	[batch_number] [nvarchar](50) NULL,
	[quantity] [int] NOT NULL,
	[purchase_price] [decimal](10, 2) NOT NULL,
	[expiry_date] [date] NULL,
	[received_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[stock_entry_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Categories] ON 

INSERT [dbo].[Categories] ([category_id], [name], [description],[image]) VALUES (1, N'Thực phẩm', N'Danh mục bao gồm các loại thực phẩm tươi sống, đóng gói như rau củ, thịt, cá, gạo, mì, và đồ ăn sẵn.',N'https://image.luatvietnam.vn/uploaded/twebp/images/original/2023/08/31/thuc-pham-la-gi_3108125835.jpeg')
INSERT [dbo].[Categories] ([category_id], [name], [description],[image]) VALUES (2, N'Đồ uống', N'Các loại đồ uống như nước ngọt, bia, sữa, nước đóng chai, trà và cà phê.',N'https://uongnuoc.com/wp-content/uploads/2020/04/Untitled-design-3-2.jpg')
INSERT [dbo].[Categories] ([category_id], [name], [description],[image]) VALUES (3, N'Đồ gia dụng', N'Các sản phẩm dùng trong gia đình như chén dĩa, nồi chảo, dụng cụ vệ sinh, và đồ điện nhỏ.',N'https://locknlockvietnam.com/wp-content/uploads/2023/10/kham-pha-bo-suu-tap-do-gia-dung-bianco-locknlock-2023-1.jpg')
INSERT [dbo].[Categories] ([category_id], [name], [description],[image]) VALUES (4, N'Chăm sóc cá nhân', N'Sản phẩm chăm sóc cá nhân như xà phòng, dầu gội, kem đánh răng, và mỹ phẩm cơ bản.',N'https://fidobox.vn/wp-content/uploads/2024/07/Lam-the-nao-de-tham-gia-chuong-trinh-San-Deal-Headwolf3.png')
INSERT [dbo].[Categories] ([category_id], [name], [description],[image]) VALUES (5, N'Đồ chơi & Văn phòng phẩm', N'Đồ chơi cho trẻ em và các sản phẩm văn phòng như bút, giấy, sổ tay.',N'https://down-vn.img.susercontent.com/file/sg-11134201-7rffh-m4a1menvka4h9c')
SET IDENTITY_INSERT [dbo].[Categories] OFF
GO
SET IDENTITY_INSERT [dbo].[Customer] ON 

INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (1, N'Nam@123', N'password1', N'Nguyễn Văn Nam', NULL, N'namnguyen@example.com', N'0912345700', 0, N'12 Đường Láng Hạ, Phường Láng Hạ, Quận Đống Đa, Hà Nội', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (2, N'Tuan#VN', N'password2', N'Trần Anh Tuấn', NULL, N'tuandran@example.com', N'0912345701', 0, N'45 Đường Nguyễn Huệ, Phường 2, Quận 1, TP. Hồ Chí Minh', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (3, N'Long_789', N'password3', N'Lê Văn Long', NULL, N'longle@example.com', N'0912345702', 0, N'78 Đường Lê Lợi, Phường Hải Châu 1, Quận Hải Châu, Đà Nẵng', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (4, N'Duc$99', N'password4', N'Phạm Đức Đạt', NULL, N'datpham@example.com', N'0912345703', 0, N'101 Đường Cầu Giấy, Phường Dịch Vọng, Quận Cầu Giấy, Hà Nội', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (5, N'Hieu@HN', N'password5', N'Hoàng Minh Hiếu', NULL, N'hieuhoang@example.com', N'0912345704', 0, N'23 Đường Trần Phú, Phường Mộ Lao, Quận Hà Đông, Hà Nội', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (6, N'Kien#2023', N'password6', N'Võ Trung Kiên', NULL, N'kienvo@example.com', N'0912345705', 0, N'56 Đường Nguyễn Văn Linh, Phường Nam Dương, Quận Hải Châu, Đà Nẵng', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (7, N'Phong_88', N'password7', N'Đặng Văn Phong', NULL, N'phongdang@example.com', N'0912345706', 0, N'89 Đường Lý Thường Kiệt, Phường 7, Quận 10, TP. Hồ Chí Minh', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (8, N'Trung@9', N'password8', N'Bùi Quốc Trung', NULL, N'trungbui@example.com', N'0912345707', 0, N'34 Đường Nguyễn Trãi, Phường Thanh Xuân Trung, Quận Thanh Xuân, Hà Nội', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (9, N'Quang$VN', N'password9', N'Đỗ Văn Quang', NULL, N'quangdo@example.com', N'0912345708', 1, N'67 Đường Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP. Hồ Chí Minh', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (10, N'Thanh_HCM', N'password10', N'Hồ Văn Thanh', NULL, N'thanhho@example.com', N'0912345709', 1, N'90 Đường Điện Biên Phủ, Phường Đakao, Quận 1, TP. Hồ Chí Minh', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (11, N'Vinh@22', N'password11', N'Nguyễn Văn Vinh', NULL, N'vinhnguyen@example.com', N'0912345710', 0, N'15 Đường Hùng Vương, Phường Điện Biên, Quận Ba Đình, Hà Nội', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (12, N'Minh#123', N'password12', N'Trần Văn Minh', NULL, N'minhtran@example.com', N'0912345711', 0, N'48 Đường Võ Văn Tần, Phường 6, Quận 3, TP. Hồ Chí Minh', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (13, N'Son_456', N'password13', N'Lê Hồng Sơn', NULL, N'sonle@example.com', N'0912345712', 1, N'71 Đường Nguyễn Văn Cừ, Phường An Hòa, Quận Ninh Kiều, Đà Nẵng', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (14, N'Tai$77', N'password14', N'Phạm Văn Tài', NULL, N'taipham@example.com', N'0912345713', 1, N'24 Đường Giải Phóng, Phường 4, Quận Tân Bình, TP. Hồ Chí Minh', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (15, N'Hung@DN', N'password15', N'Hoàng Văn Hùng', NULL, N'hunghoang@example.com', N'0912345714', 1, N'57 Đường Trần Hưng Đạo, Phường Hải Châu 2, Quận Hải Châu, Đà Nẵng', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
INSERT [dbo].[Customer] ([customer_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (16, N'Linh@123', N'password16', N'Nguyễn Thị Linh', NULL, N'linhnguyen@example.com', N'0912345715', 1, N'13 Phố Huế, Phường Hàng Bài, Quận Hoàn Kiếm, Hà Nội', 4, CAST(N'2025-03-07T22:42:48.240' AS DateTime))
SET IDENTITY_INSERT [dbo].[Customer] OFF
GO
SET IDENTITY_INSERT [dbo].[Inventory] ON 

INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (1, 1, 100, CAST(N'2024-12-01' AS Date), CAST(100000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (2, 2, 50, CAST(N'2024-11-15' AS Date), CAST(140000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (3, 3, 40, CAST(N'2024-12-10' AS Date), CAST(120000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (4, 4, 200, CAST(N'2025-01-01' AS Date), CAST(20000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (5, 5, 500, CAST(N'2024-10-20' AS Date), CAST(4000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (6, 6, 150, CAST(N'2024-11-01' AS Date), CAST(28000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (7, 7, 300, CAST(N'2024-12-15' AS Date), CAST(8000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (8, 8, 200, CAST(N'2024-11-20' AS Date), CAST(16000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (9, 9, 150, CAST(N'2024-12-05' AS Date), CAST(28000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (10, 10, 400, CAST(N'2025-01-10' AS Date), CAST(5500.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (11, 11, 350, CAST(N'2024-10-25' AS Date), CAST(7200.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (12, 12, 80, CAST(N'2024-11-10' AS Date), CAST(52000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (13, 13, 20, CAST(N'2024-12-20' AS Date), CAST(280000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (14, 14, 25, CAST(N'2024-11-25' AS Date), CAST(200000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (15, 15, 30, CAST(N'2024-12-01' AS Date), CAST(144000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (16, 16, 15, CAST(N'2024-10-15' AS Date), CAST(240000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (17, 17, 50, CAST(N'2025-01-05' AS Date), CAST(60000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (18, 18, 40, CAST(N'2024-11-15' AS Date), CAST(72000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (19, 19, 200, CAST(N'2024-12-10' AS Date), CAST(12000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (20, 20, 60, CAST(N'2024-11-01' AS Date), CAST(108000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (21, 21, 150, CAST(N'2024-12-15' AS Date), CAST(24000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (22, 22, 70, CAST(N'2024-10-20' AS Date), CAST(96000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (23, 23, 250, CAST(N'2025-01-01' AS Date), CAST(16000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (24, 24, 80, CAST(N'2024-11-10' AS Date), CAST(68000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (25, 25, 30, CAST(N'2024-12-05' AS Date), CAST(200000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (26, 26, 25, CAST(N'2024-11-20' AS Date), CAST(144000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (27, 27, 200, CAST(N'2024-10-25' AS Date), CAST(20000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (28, 28, 150, CAST(N'2024-12-01' AS Date), CAST(28000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (29, 29, 100, CAST(N'2025-01-10' AS Date), CAST(36000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
INSERT [dbo].[Inventory] ([inventory_id], [product_id], [quantity], [purchase_date], [purchase_price], [last_updated]) VALUES (30, 30, 300, CAST(N'2024-11-15' AS Date), CAST(4000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.267' AS DateTime))
SET IDENTITY_INSERT [dbo].[Inventory] OFF
GO
SET IDENTITY_INSERT [dbo].[Products] ON 

INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (1, 1, NULL, N'Gạo ST25', N'Gạo thơm chất lượng cao từ Việt Nam', CAST(125000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (2, 1, NULL, N'Thịt bò Úc', N'Thịt bò tươi nhập khẩu', CAST(180000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (3, 1, NULL, N'Cá hồi Na Uy', N'Cá hồi phi lê tươi', CAST(150000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (4, 1, NULL, N'Rau cải xanh', N'Rau cải tươi sạch', CAST(25000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (5, 1, NULL, N'Mì gói Hảo Hảo', N'Mì ăn liền vị tôm chua cay, gói ', CAST(5000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (6, 1, NULL, N'Bánh mì sandwich', N'Bánh mì lát đóng gói', CAST(35000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (7, 2, NULL, N'Nước ngọt Coca Cola', N'Nước giải khát có ga, lon 330ml', CAST(10000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (8, 2, NULL, N'Bia Heineken', N'Bia nhập khẩu Hà Lan, lon 330ml', CAST(20000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (9, 2, NULL, N'Sữa tươi Vinamilk', N'Sữa tươi tiệt trùng, hộp 1L', CAST(35000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (10, 2, NULL, N'Nước suối Lavie', N'Nước uống đóng chai, chai 500ml', CAST(7000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (11, 2, NULL, N'Trà xanh C2', N'Trà xanh đóng chai, chai 455ml', CAST(9000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (12, 2, NULL, N'Cà phê Highlands', N'Cà phê hòa tan, gói 16g x 20', CAST(65000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (13, 3, NULL, N'Nồi inox 24cm', N'Nồi inox bền đẹp cho gia đình', CAST(350000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (14, 3, NULL, N'Chảo chống dính 26cm', N'Chảo chống dính cao cấp', CAST(250000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (15, 3, NULL, N'Bộ bát đĩa sứ', N'Bộ 6 bát đĩa sứ trắng', CAST(180000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (16, 3, NULL, N'Bình đun siêu tốc', N'Bình đun nước inox 1.8L', CAST(300000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (17, 3, NULL, N'Chổi lau nhà', N'Chổi lau nhà cán dài tiện lợi', CAST(75000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (18, 3, NULL, N'Thùng rác nhựa', N'Thùng rác 20L có nắp', CAST(90000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (19, 4, NULL, N'Xà phòng Lifebuoy', N'Xà phòng diệt khuẩn, bánh 100g', CAST(15000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (20, 4, NULL, N'Dầu gội Sunsilk', N'Dầu gội mềm mượt, chai 650ml', CAST(135000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (21, 4, NULL, N'Kem đánh răng Colgate', N'Kem đánh răng trắng sáng, tuýp 100g', CAST(30000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (22, 4, NULL, N'Sữa tắm Dove', N'Sữa tắm dưỡng ẩm, chai 500ml', CAST(120000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (23, 4, NULL, N'Bàn chải đánh răng', N'Bàn chải lông mềm, 1 chiếc', CAST(20000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (24, 4, NULL, N'Nước súc miệng Listerine', N'Nước súc miệng kháng khuẩn, chai 250ml', CAST(85000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (25, 5, NULL, N'Xếp hình Lego', N'Bộ xếp hình 100 chi tiết cho trẻ em', CAST(250000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (26, 5, NULL, N'Búp bê Barbie', N'Búp bê thời trang, cao 30cm', CAST(180000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (27, 5, NULL, N'Bút bi Thiên Long', N'Bút bi mực xanh, 10 cây', CAST(25000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (28, 5, NULL, N'Sổ tay A5', N'Sổ tay 100 trang kẻ ngang', CAST(35000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (29, 5, NULL, N'Màu nước 12 màu', N'Bộ màu nước cho học sinh', CAST(45000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
INSERT [dbo].[Products] ([product_id], [category_id], [image], [name], [description], [price], [created_at]) VALUES (30, 5, NULL, N'Tẩy bút chì', N'Tẩy cao su mềm, 1 chiếc', CAST(5000.00 AS Decimal(10, 2)), CAST(N'2025-03-07T22:42:48.253' AS DateTime))
SET IDENTITY_INSERT [dbo].[Products] OFF
GO
SET IDENTITY_INSERT [dbo].[Role] ON 

INSERT [dbo].[Role] ([role_id], [role_name]) VALUES (1, N'Admin')
INSERT [dbo].[Role] ([role_id], [role_name]) VALUES (2, N'Staff')
INSERT [dbo].[Role] ([role_id], [role_name]) VALUES (3, N'Shipper')
INSERT [dbo].[Role] ([role_id], [role_name]) VALUES (4, N'Customer')
SET IDENTITY_INSERT [dbo].[Role] OFF
GO
SET IDENTITY_INSERT [dbo].[Staffs] ON 

INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (1, N'Admin@123', N'password1', N'Nguyễn Văn An', NULL, N'annguyen@example.com', N'0912345678', 0, N'123 Đường Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP. Hồ Chí Minh', 1, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (2, N'Anh#VN', N'password2', N'Trần Văn Anh', NULL, N'anhtran@example.com', N'0912345679', 0, N'456 Đường Lê Duẩn, Phường 7, Quận 1, TP. Hồ Chí Minh', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (3, N'Bao_456', N'password3', N'Lê Văn Bảo', NULL, N'baole@example.com', N'0912345680', 0, N'789 Đường Cách Mạng Tháng Tám, Phường 10, Quận 3, TP. Hồ Chí Minh', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (4, N'Binh$99', N'password4', N'Phạm Văn Bình', NULL, N'binhpham@example.com', N'0912345681', 0, N'101 Đường Pasteur, Phường 6, Quận 3, TP. Hồ Chí Minh', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (5, N'Chi@HCM', N'password5', N'Hoàng Văn Chi', NULL, N'chihoang@example.com', N'0912345682', 0, N'202 Đường Nguyễn Trãi, Phường 8, Quận 5, TP. Hồ Chí Minh', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (6, N'Chí#2023', N'password6', N'Võ Văn Chí', NULL, N'chivo@example.com', N'0912345683', 0, N'303 Đường Nguyễn Văn Cừ, Phường 4, Quận 5, TP. Hồ Chí Minh', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (7, N'Chinh_88', N'password7', N'Đặng Văn Chinh', NULL, N'chinhdang@example.com', N'0912345684', 0, N'404 Đường Thống Nhất, Phường 12, Quận 10, TP. Hồ Chí Minh', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (8, N'Chuong@9', N'password8', N'Bùi Văn Chương', NULL, N'chuongbui@example.com', N'0912345685', 0, N'505 Đường Huỳnh Tấn Phát, Phường Tân Sơn Nhì, Quận Tân Phú, TP. Hồ Chí Minh', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (9, N'Cuong$VN', N'password9', N'Đỗ Văn Cường', NULL, N'cuongdo@example.com', N'0912345686', 0, N'606 Đường Quang Trung, Phường 10, Quận Gò Vấp, TP. Hồ Chí Minh', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (10, N'Dung_HN', N'password10', N'Hồ Văn Dũng', NULL, N'dungho@example.com', N'0912345687', 0, N'707 Đường Lý Thường Kiệt, Phường 1, Quận 11, TP. Hồ Chí Minh', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (11, N'Giang@22', N'password11', N'Ngô Thị Giang', NULL, N'giangngo@example.com', N'0912345688', 1, N'111 Phố Nguyễn Du, Phường Trung Liệt, Quận Đống Đa, Hà Nội', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (12, N'Ha#1234', N'password12', N'Dương Thị Hà', NULL, N'haduong@example.com', N'0912345689', 1, N'222 Phố Trần Quốc Toản, Phường Cống Vị, Quận Ba Đình, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (13, N'Hai_555', N'password13', N'Lý Thị Hải', NULL, N'haily@example.com', N'0912345690', 1, N'333 Phố Hoàng Diệu, Phường Trung Hoà, Quận Cầu Giấy, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (14, N'Hieu$77', N'password14', N'Phan Thị Hiếu', NULL, N'hieuphan@example.com', N'0912345691', 1, N'444 Phố Lê Văn Lương, Phường Nhân Chính, Quận Thanhhóa, Hà Nội', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (15, N'Hoang@VN', N'password15', N'Vương Thị Hoàng', NULL, N'hoangvuong@example.com', N'0912345692', 1, N'555 Phố Đại Cồ Việt, Phường Lê Đại Hành, Quận Hai Bà Trưng, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (16, N'Huy_999', N'password16', N'Trương Thị Huy', NULL, N'huytruong@example.com', N'0912345693', 1, N'666 Phố Nguyễn Chí Than, Phường Láng Hạ, Quận Đống Đa, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (17, N'Khanh#88', N'password17', N'Nguyễn Thị Khánh', NULL, N'khanhnguyen@example.com', N'0912345694', 1, N'777 Phố Trần Phú, Phường Xuân La, Quận Tây Hồ, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (18, N'Kim@2023', N'password18', N'Trần Thị Kim', NULL, N'kimtran@example.com', N'0912345695', 1, N'888 Phố Cầu Giấy, Phường Dịch Vọng Hậu, Quận Cầu Giấy, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (19, N'Lan_123', N'password19', N'Lê Thị Lan', NULL, N'lanle@example.com', N'0912345696', 1, N'999 Phố Tố Hữu, Phường Giãng Võ, Quận Ba Đình, Hà Nội', 3, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
INSERT [dbo].[Staffs] ([staff_id], [username], [password], [full_name], [image], [email], [phone_number], [gender], [address], [role_id], [created_at]) VALUES (20, N'Liem$HCM', N'password20', N'Phạm Thị Liêm', NULL, N'liempham@example.com', N'0912345697', 1, N'1010 Phố Nguyễn Văn Cừ, Phường Mai Dịch, Quận Cầu Giấy, Hà Nội', 2, CAST(N'2025-03-07T22:42:48.233' AS DateTime))
SET IDENTITY_INSERT [dbo].[Staffs] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Customer__A1936A6B1E97FB35]    Script Date: 3/7/2025 11:36:47 PM ******/
ALTER TABLE [dbo].[Customer] ADD UNIQUE NONCLUSTERED 
(
	[phone_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Customer__F3DBC572A0ABE7A9]    Script Date: 3/7/2025 11:36:47 PM ******/
ALTER TABLE [dbo].[Customer] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Promotio__357D4CF92B8D9564]    Script Date: 3/7/2025 11:36:47 PM ******/
ALTER TABLE [dbo].[Promotions] ADD UNIQUE NONCLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Staffs__AB6E6164923A4D18]    Script Date: 3/7/2025 11:36:47 PM ******/
ALTER TABLE [dbo].[Staffs] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Staffs__F3DBC572A8051D2F]    Script Date: 3/7/2025 11:36:47 PM ******/
ALTER TABLE [dbo].[Staffs] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Cart] ADD  DEFAULT (getdate()) FOR [added_at]
GO
ALTER TABLE [dbo].[Customer] ADD  DEFAULT ((4)) FOR [role_id]
GO
ALTER TABLE [dbo].[Customer] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Inventory] ADD  DEFAULT (getdate()) FOR [last_updated]
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF__Orders__status__5BE2A6F2]  DEFAULT ('pending') FOR [status]
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF__Orders__created___5CD6CB2B]  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Invoice] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Promotion_Usage] ADD  DEFAULT (getdate()) FOR [used_at]
GO
ALTER TABLE [dbo].[Promotions] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Reviews] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Staffs] ADD  DEFAULT ((0)) FOR [gender]
GO
ALTER TABLE [dbo].[Staffs] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[Stock_Entries] ADD  DEFAULT (getdate()) FOR [received_at]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD FOREIGN KEY([customer_id])
REFERENCES [dbo].[Customer] ([customer_id])
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[Products] ([product_id])
GO
ALTER TABLE [dbo].[Customer]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([role_id])
GO
ALTER TABLE [dbo].[Inventory]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[Products] ([product_id])
GO
ALTER TABLE [dbo].[Order_details]  WITH CHECK ADD  CONSTRAINT [FK__Order_det__order__628FA481] FOREIGN KEY([order_id])
REFERENCES [dbo].[Orders] ([order_id])
GO
ALTER TABLE [dbo].[Order_details] CHECK CONSTRAINT [FK__Order_det__order__628FA481]
GO
ALTER TABLE [dbo].[Order_details]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[Products] ([product_id])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK__Orders__customer__5DCAEF64] FOREIGN KEY([customer_id])
REFERENCES [dbo].[Customer] ([customer_id])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK__Orders__customer__5DCAEF64]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK__Orders__promotio__5FB337D6] FOREIGN KEY([promotion_id])
REFERENCES [dbo].[Promotions] ([promotion_id])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK__Orders__promotio__5FB337D6]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK__Orders__staff_id__5EBF139D] FOREIGN KEY([staff_id])
REFERENCES [dbo].[Staffs] ([staff_id])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK__Orders__staff_id__5EBF139D]
GO
ALTER TABLE [dbo].[Invoice]  WITH CHECK ADD CONSTRAINT [FK_Invoice_order] FOREIGN KEY([order_id])
REFERENCES [dbo].[Orders] ([order_id]);
GO
ALTER TABLE [dbo].[Invoice] CHECK CONSTRAINT [FK_Invoice_order]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [dbo].[Categories] ([category_id])
GO
ALTER TABLE [dbo].[Promotion_Usage]  WITH CHECK ADD FOREIGN KEY([customer_id])
REFERENCES [dbo].[Customer] ([customer_id])
GO
ALTER TABLE [dbo].[Promotion_Usage]  WITH CHECK ADD FOREIGN KEY([promotion_id])
REFERENCES [dbo].[Promotions] ([promotion_id])
GO
ALTER TABLE [dbo].[Promotions]  WITH CHECK ADD FOREIGN KEY([staff_id])
REFERENCES [dbo].[Staffs] ([staff_id])
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD FOREIGN KEY([customer_id])
REFERENCES [dbo].[Customer] ([customer_id])
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[Products] ([product_id])
GO
ALTER TABLE [dbo].[Role_Permissions]  WITH CHECK ADD FOREIGN KEY([permission_id])
REFERENCES [dbo].[Permissions] ([permission_id])
GO
ALTER TABLE [dbo].[Role_Permissions]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([role_id])
GO
ALTER TABLE [dbo].[Staffs]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [dbo].[Role] ([role_id])
GO
ALTER TABLE [dbo].[Stock_Entries]  WITH CHECK ADD FOREIGN KEY([inventory_id])
REFERENCES [dbo].[Inventory] ([inventory_id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Stock_Entries]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[Products] ([product_id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Inventory]  WITH CHECK ADD CHECK  (([quantity]>=(0)))
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [CK__Orders__status__5AEE82B9] CHECK  (([status]='complete' OR [status]='processing' OR [status]='pending'))
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [CK__Orders__status__5AEE82B9]
GO
ALTER TABLE [dbo].[Invoice]  WITH CHECK ADD CHECK  (([status]='refunded' OR [status]='failed' OR [status]='completed' OR [status]='pending'))
GO
ALTER TABLE [dbo].[Stock_Entries]  WITH CHECK ADD CHECK  (([quantity]>=(0)))
GO
USE [master]
GO
ALTER DATABASE [SuperMarket] SET  READ_WRITE 
GO

