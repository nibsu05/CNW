<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.bean.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sản phẩm - Thiệp và Hoa</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            background: #f5f5f5;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        /* Header */
        header {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
            transition: all 0.3s ease;
        }

        nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1.5rem 0;
        }

        .logo {
            font-size: 2rem;
            font-weight: bold;
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .nav-links {
            display: flex;
            list-style: none;
            gap: 2rem;
        }

        .nav-links a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
            transition: all 0.3s ease;
            position: relative;
            padding: 8px 16px;
            border-radius: 5px;
        }

        .nav-links a:hover {
            color: #ff6b6b;
            transform: translateY(-2px);
        }

        .nav-links a.active {
            background: #4ecdc4;
            color: white;
        }

        /* Promotional Banner */
        .promo-banner {
            background: url('https://png.pngtree.com/thumb_back/fh260/background/20190223/ourmid/pngtree-propaganda-image_86931.jpg') center center/cover no-repeat;
            padding: 40px 0;
            margin-top: 80px;
            position: relative;
            overflow: hidden;
        }

        .promo-banner::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.3);
        }

        .promo-content {
            display: flex;
            align-items: center;
            justify-content: space-between;
            position: relative;
            z-index: 1;
        }

        .promo-left {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .envelope-stack {
            position: relative;
            width: 120px;
            height: 80px;
        }

        .envelope {
            position: absolute;
            font-size: 2rem;
            color: white;
        }

        .envelope:nth-child(1) { top: -10px; left: 0; }
        .envelope:nth-child(2) { top: 0; left: 20px; }
        .envelope:nth-child(3) { top: 10px; left: 40px; }

        .laptop {
            width: 80px;
            height: 60px;
            border: 2px solid white;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-left: 20px;
        }

        .promo-right {
            text-align: right;
        }

        .free-text {
            font-size: 4rem;
            font-weight: bold;
            color: white;
            margin-bottom: 10px;
        }

        .ecards-flags {
            display: flex;
            gap: 5px;
            justify-content: flex-end;
        }

        .flag {
            background: white;
            color: #ff6b9d;
            padding: 8px 12px;
            border-radius: 4px;
            font-weight: bold;
            font-size: 1.2rem;
        }

        /* Main Content */
        .main-content {
            display: flex;
            gap: 30px;
            margin-top: 30px;
            margin-bottom: 50px;
        }

        /* Sidebar */
        .sidebar {
            width: 200px;
            flex-shrink: 0;
        }

        .filter-box {
            background: #2c3e50;
            color: white;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        .filter-box h3 {
            margin-bottom: 12px;
            font-size: 1rem;
        }

        .filter-list {
            list-style: none;
        }

        .filter-list li {
            margin-bottom: 6px;
        }

        .filter-list a {
            color: #333;
            text-decoration: none;
            padding: 6px 10px;
            display: block;
            background: #ecf0f1;
            border-radius: 4px;
            transition: all 0.3s ease;
            font-size: 0.9rem;
        }

        .filter-list a:hover,
        .filter-list a.active {
            background: #4ecdc4;
            color: white;
        }

        /* Products Grid */
        .products-container {
            flex: 1;
        }

        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 25px;
        }

        .product-item {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .product-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        }

        .product-image {
            height: 350px;
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 4rem;
            color: white;
            position: relative;
            overflow: hidden;
        }

        .product-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .product-image i {
            z-index: 1;
        }

        .product-info {
            padding: 15px;
            text-align: center;
        }

        .product-title {
            font-size: 1.1rem;
            font-weight: 600;
            margin-bottom: 8px;
            color: #333;
            line-height: 1.3;
        }

        .product-price {
            font-weight: bold;
            color: #ff6b6b;
            font-size: 1.2rem;
        }

        /* Footer */
        footer {
            background: #2c3e50;
            color: white;
            padding: 50px 0 20px;
            margin-top: 50px;
        }

        .footer-content {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
            margin-bottom: 2rem;
        }

        .footer-section h3 {
            margin-bottom: 1rem;
            color: #4ecdc4;
        }

        .footer-section ul {
            list-style: none;
        }

        .footer-section ul li {
            margin-bottom: 0.5rem;
        }

        .footer-section ul li a {
            color: #bdc3c7;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .footer-section ul li a:hover {
            color: #4ecdc4;
        }

        .footer-bottom {
            text-align: center;
            padding-top: 2rem;
            border-top: 1px solid #34495e;
            color: #bdc3c7;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .nav-links {
                display: none;
            }
            
            .promo-content {
                flex-direction: column;
                text-align: center;
            }
            
            .main-content {
                flex-direction: column;
            }
            
            .sidebar {
                width: 100%;
            }
            
            .products-grid {
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            }
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header>
        <nav class="container">
            
            <div class="logo">
                <a href="homepage.jsp" style="text-decoration: none; color: inherit;">
                    <i class="fas fa-gift"></i> Thiệp và Hoa
                </a>
            </div>
            <ul class="nav-links">
                <li><a href="ecards.jsp"><i class="fas fa-envelope"></i> Thiệp</a></li>
                <li><a href="flowers.jsp" class="active"><i class="fas fa-seedling"></i> Hoa</a></li>
                <li><a href="login.jsp"><i class="fas fa-sign-in-alt"></i> Đăng nhập</a></li>
                <li><a href="cart.jsp"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a></li>
            </ul>
        </nav>
    </header>

    <!-- Promotional Banner -->
    <section class="promo-banner">
        <div class="container">
            <div class="promo-content">
                <div class="promo-left">
                    <div class="free-text">Các bó hoa</div>

                </div>
                <div class="promo-right">
                    <div class="ecards-flags">
                        <div class="flag">F</div>
                        <div class="flag">L</div>
                        <div class="flag">O</div>
                        <div class="flag">W</div>
                        <div class="flag">E</div>
                        <div class="flag">R</div>
                        <div class="flag">S</div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Main Content -->
    <div class="container">
        <div class="main-content">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="filter-box">
                    <h3>Chủ đề</h3>
                </div>
                <ul class="filter-list">
                    <li><a href="#" class="active">Tất cả</a></li>
                    <li><a href="#">Động vật</a></li>
                    <li><a href="#">Đồ ăn</a></li>
                    <li><a href="#">Tốt nghiệp</a></li>
                    <li><a href="#">Sinh nhật</a></li>
                </ul>
            </div>

            <%@ page import="model.bo.ProductBo"%>
            <!-- Products Grid -->
            <div class="products-container">
                <div class="products-grid">
                    <%
                    ProductBo bo = new ProductBo();
                        // Tạo danh sách sản phẩm mẫu
                        List<Product> products = bo.getProductsByTypeSplit("flower");
                        
                        // Hiển thị sản phẩm
                        for(Product product : products) {
                    %>
                    <div class="product-item">
                        <div class="product-image">
                            <% if(product.getImageUrl() != null && !product.getImageUrl().isEmpty()) { %>
                                <img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>">
                            <% } else { %>
                                <% if(product.getType().equals("flower")) { %>
                                    <i class="fas fa-seedling"></i>
                                <% } else { %>
                                    <i class="fas fa-envelope"></i>
                                <% } %>
                            <% } %>
                        </div>
                        <div class="product-info">
                            <div class="product-title"><%= product.getName() %></div>
                            <div class="product-price">
                                <% if(product.getPrice() == 0) { %>
                                    Miễn phí
                                <% } else { %>
                                    <%= product.getFormattedPrice() %>
                                <% } %>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <div class="container">
            <div class="footer-content">
                <div class="footer-section">
                    <h3>Thiệp và Hoa</h3>
                    <p>Công nghệ Web Hè 2025.</p>
                </div>
                <div class="footer-section">
                    <h3>Đường dẫn nhanh</h3>
                    <ul>
                        <li><a href="#ecards">E-Card Gallery</a></li>
                        <li><a href="#flowers">Flower Catalog</a></li>
                        <li><a href="#delivery">Delivery Info</a></li>
                        <li><a href="#custom">Custom Orders</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Chăm sóc khách hàng</h3>
                    <ul>
                        <li><a href="#faq">FAQ</a></li>
                        <li><a href="#support">Support</a></li>
                        <li><a href="#returns">Returns</a></li>
                        <li><a href="#tracking">Order Tracking</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Liên hệ với chúng tôi</h3>
                    <ul>
                        <li><a href="#"><i class="fab fa-facebook"></i> Facebook</a></li>
                        <li><a href="#"><i class="fab fa-instagram"></i> Instagram</a></li>
                    </ul>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2025 Thiệp và Hoa | Đại học Bách khoa Đà Nẵng</p>
            </div>
        </div>
    </footer>

    <script>
        // Header scroll effect
        window.addEventListener('scroll', () => {
            const header = document.querySelector('header');
            if (window.scrollY > 100) {
                header.style.background = 'rgba(255, 255, 255, 0.98)';
                header.style.boxShadow = '0 4px 30px rgba(0, 0, 0, 0.15)';
            } else {
                header.style.background = 'rgba(255, 255, 255, 0.95)';
                header.style.boxShadow = '0 4px 20px rgba(0, 0, 0, 0.1)';
            }
        });

        // Filter functionality
        document.querySelectorAll('.filter-list a').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                // Remove active class from all links
                document.querySelectorAll('.filter-list a').forEach(l => {
                    l.classList.remove('active');
                });
                // Add active class to clicked link
                this.classList.add('active');
            });
        });
    </script>
</body>
</html> 