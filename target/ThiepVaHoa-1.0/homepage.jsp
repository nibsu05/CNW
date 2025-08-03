<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.bean.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thiệp và Hoa</title>
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
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
        }

        .nav-links a:hover {
            color: #ff6b6b;
            transform: translateY(-2px);
        }

        .nav-links a::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 0;
            height: 2px;
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            transition: width 0.3s ease;
        }

        .nav-links a:hover::after {
            width: 100%;
        }

        /* Hero Section */
        .hero {
            height: 60vh;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            position: relative;
            overflow: hidden;
        }

        .hero::before {
            content: "";
            position: absolute;
            top: 0; left: 0; right: 0; bottom: 0;
            width: 100%;
            height: 100%;
            background: url('https://tse4.mm.bing.net/th/id/OIP.1DfC602RanVL_5uUtzZmqwHaEo?r=0&rs=1&pid=ImgDetMain&o=7&rm=3') center center/cover no-repeat;
            filter: blur(3px);
            z-index: 0;
            opacity: 0.7;
        }
        .hero, .hero * {
            position: relative;
            z-index: 1;
        }

        @keyframes float {
            0%, 100% { transform: translateY(0px); }
            50% { transform: translateY(-20px); }
        }

        .hero-content {
            z-index: 1;
            position: relative;
        }

        .hero h1 {
            font-size: 4rem;
            margin-bottom: 1rem;
            animation: fadeInUp 1s ease;
        }

        .hero p {
            font-size: 1.5rem;
            margin-bottom: 2rem;
            opacity: 0.9;
            animation: fadeInUp 1s ease 0.2s both;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .cta-buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
            animation: fadeInUp 1s ease 0.4s both;
        }

        .btn {
            padding: 15px 30px;
            border: none;
            border-radius: 50px;
            font-size: 1.1rem;
            font-weight: 600;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }

        .btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
            transition: left 0.5s;
        }

        .btn:hover::before {
            left: 100%;
        }

        .btn-primary {
            background: linear-gradient(45deg, #ff6b6b, #ff8e53);
            color: white;
            box-shadow: 0 10px 30px rgba(255, 107, 107, 0.4);
        }

        .btn-secondary {
            background: linear-gradient(45deg, #4ecdc4, #44a08d);
            color: white;
            box-shadow: 0 10px 30px rgba(78, 205, 196, 0.4);
        }

        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.3);
        }

        /* Features Section */
        .features {
            padding: 5px 0;
            background: white;
        }

        .section-title {
            text-align: center;
            font-size: 3rem;
            background: linear-gradient(45deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 2rem;
            margin-top: 0;
        }

        .feature-card {
            background: linear-gradient(145deg, #f0f0f0, #ffffff);
            padding: 2.5rem;
            border-radius: 20px;
            text-align: center;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .feature-card::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: linear-gradient(45deg, transparent, rgba(255, 107, 107, 0.05), transparent);
            transform: rotate(45deg);
            transition: all 0.5s ease;
            opacity: 0;
        }

        .feature-card:hover::before {
            opacity: 1;
            transform: rotate(45deg) translate(50%, 50%);
        }

        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 30px 60px rgba(0, 0, 0, 0.15);
        }

        .feature-icon {
            font-size: 4rem;
            margin-bottom: 1.5rem;
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .feature-card h3 {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            color: #333;
        }

        .feature-card p {
            color: #666;
            line-height: 1.6;
        }

        /* Products Section */
        .products {
            padding: 100px 0;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        }

        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 3rem;
        }

        .product-card {
            background: white;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            position: relative;
        }

        .product-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(45deg, rgba(255, 107, 107, 0.1), rgba(78, 205, 196, 0.1));
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .product-card:hover::before {
            opacity: 1;
        }

        .product-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
        }

        .product-image {
            height: 250px;
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 5rem;
            color: white;
            position: relative;
            overflow: hidden;
        }

        .product-image::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: radial-gradient(circle, transparent 30%, rgba(0,0,0,0.1) 70%);
        }

        .product-content {
            padding: 2rem;
            position: relative;
            z-index: 1;
        }

        .product-card h3 {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            color: #333;
        }

        .product-card p {
            color: #666;
            margin-bottom: 1.5rem;
        }

        .product-price {
            font-size: 1.3rem;
            font-weight: bold;
            color: #ff6b6b;
            margin-bottom: 1rem;
        }

        /* Footer */
        footer {
            background: #2c3e50;
            color: white;
            padding: 50px 0 20px;
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
            
            .hero h1 {
                font-size: 2.5rem;
            }
            
            .hero p {
                font-size: 1.2rem;
            }
            
            .cta-buttons {
                flex-direction: column;
                align-items: center;
            }
            
            .section-title {
                font-size: 2rem;
            }
            
            .features-grid {
                grid-template-columns: 1fr;
                gap: 2rem;
            }
        }
        .featured-section{margin-top:100px;margin-bottom:60px;}
        .featured-title{text-align:center;font-size:2rem;margin-bottom:30px;color:#4ecdc4;}
        .featured-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:20px;}
        .featured-item{background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 4px 12px rgba(0,0,0,.05);text-decoration:none;color:inherit;transition:.3s;}
        .featured-item:hover{transform:translateY(-4px);box-shadow:0 6px 18px rgba(0,0,0,.1);}
        .featured-img img{width:100%;height:250px;object-fit:cover;}
        .featured-info{padding:15px;}
        .featured-info h3{font-size:1.1rem;margin:0 0 8px 0;}
        .featured-info .price{color:#e74c3c;font-weight:600;}
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
            <%
    String email = (String) session.getAttribute("email");
    Object roleObj = session.getAttribute("role");
    int role = (roleObj != null) ? (int) roleObj : 0; // Default to 0 (regular user) if not logged in
%>
<ul class="nav-links">
                <% if(email != null && role == 1){ %>
                <li><a href="admin_dashboard.jsp"><i class="fas fa-cogs"></i> Quản lý</a></li>
            <% } %>
            <li><a href="ProductServlet?action=card"><i class="fas fa-envelope"></i> Thiệp</a></li>
                <li><a href="ProductServlet?action=flower"><i class="fas fa-seedling"></i> Hoa</a></li>
                <% if(email == null){ %>
                <li><a href="login.jsp"><i class="fas fa-sign-in-alt"></i> Đăng nhập</a></li>
            <% }else{ %>
                <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Đăng xuất (<%= email %>)</a></li>
            <% } %>
                <li><a href="cart.jsp"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a></li>
            </ul>
        </nav>
    </header>
        <% if (request.getParameter("message") != null) { %>
    <script>
        alert("Thêm vào giỏ hàng thành công!");
    </script>
<% } %>

    <!-- Hero Section -->
    <section id="home" class="hero">
    <div class="container">
        <div class="hero-overlay"></div>
        <div class="hero-content">
            <h1>Cùng lan tỏa sự tích cực</h1>
            <p>Beautiful E-Cards & Fresh Flowers Delivered with Love</p>
            <div class="cta-buttons">
                <a href="ProductServlet?action=card" class="btn btn-primary">
                    <i class="fas fa-envelope"></i> Tìm thiệp 
                </a>
                <a href="ProductServlet?action=flower" class="btn btn-secondary">
                    <i class="fas fa-seedling"></i> Tìm hoa
                </a>
            </div>
        </div>
    </div>
</section>

    <!-- Features Section -->
    <section class="features">
        <div class="container">
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-palette"></i>
                    </div>
                    <h3>Đa dạng thiết kế</h3>
                    <p>Phù hợp cho nhiều dịp lễ khác nhau.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-shipping-fast"></i>
                    </div>
                    <h3>Giao hàng tiết kiệm</h3>
                    <p>Tiết kiệm chi phí, đảm bảo đúng giờ.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-heart"></i>
                    </div>
                    <h3>Sản phẩm chất lượng</h3>
                    <p>Những bông hoa luôn được chăm sóc ở trạng thái tốt nhất.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Products Section -->
    <%@ page import="model.bo.ProductBo" %>
    <%@ page import="java.util.List" %>
    <%
    // Lấy 3 sản phẩm đầu tiên từ database
    ProductBo productBo = new ProductBo();
    List<Product> products = productBo.getAllProducts();
    
    // Giới hạn chỉ lấy tối đa 3 sản phẩm
    int displayCount = Math.min(products.size(), 3);
    %>
    <section id="products" class="products">
        <div class="container">
            <h2 class="section-title">Sản phẩm nổi bật</h2>
            <div class="products-grid">
                <%  
                    if (products != null && !products.isEmpty()) {  
                        for (int i = 0; i < displayCount; i++) {
                            Product product = products.get(i);
                %>  
            <div class="product-card">  
                <div class="product-image">  
                    <img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>" style="width:100%; height:100%; object-fit:cover;">  
                </div>  
                <div class="product-content">  
                    <h3><%= product.getName() %></h3>  
                    <p><%= product.getDescription() %></p>  
                    <div class="product-price"><%= String.format("%,d", product.getPrice().intValue()) %> VNĐ</div>  
                    <a href="ProductServlet?action=view&id=<%= product.getId() %>" class="btn btn-primary">Xem chi tiết</a>  
                </div>  
            </div>  
<%  
                        }  
                    } else {  
%>  
        <p>Hiện chưa có sản phẩm nào.</p>  
<%  
                    }  
%>  

            </div>
        </div>
    </section>


</section>

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
        // Smooth scrolling for navigation links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });

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

        // Add some interactive animations
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, observerOptions);

        // Observe all cards for animation
        document.querySelectorAll('.feature-card, .product-card').forEach(card => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(30px)';
            card.style.transition = 'all 0.6s ease';
            observer.observe(card);
        });
    </script>
</body>
</html>