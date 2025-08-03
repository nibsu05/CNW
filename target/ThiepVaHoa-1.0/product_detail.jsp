<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.bean.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;background:#f5f6fa;margin:0;padding:0;}
        .container{max-width:1100px;margin:80px auto 40px;padding:20px;background:#fff;border-radius:12px;box-shadow:0 4px 15px rgba(0,0,0,.06);display:grid;grid-template-columns:1fr 1fr;gap:30px;}
        .product-img{width:100%;border-radius:10px;overflow:hidden;position:relative;}
        .product-img img{width:100%;display:block;}
        .discount{position:absolute;top:10px;left:10px;background:#ff6b6b;color:#fff;padding:6px 10px;border-radius:6px;font-weight:600;}
        h1{margin-top:0;}
        .old-price{text-decoration:line-through;color:#888;font-size:1.1rem;margin-right:10px;}
        .new-price{color:#e74c3c;font-size:1.6rem;font-weight:700;}
        .qty-select{display:flex;align-items:center;margin:20px 0;}
        .qty-select input{width:60px;text-align:center;padding:8px;border:1px solid #ccc;border-radius:6px;margin:0 8px;}
        .add-btn{background:#007bff;color:#fff;border:none;padding:12px 22px;border-radius:6px;font-size:1rem;cursor:pointer;transition:.3s;}
        .add-btn:hover{background:#0066d1;transform:translateY(-2px);}        
        .commit-list{list-style:none;padding:0;margin-top:20px;}
        .commit-list li{display:flex;align-items:start;margin-bottom:8px;}
        .commit-list li i{color:#4ecdc4;margin-right:8px;margin-top:4px;}
        @media(max-width:768px){.container{grid-template-columns:1fr;}}
    
        /* Header & Footer styles copied from homepage */
        header{background:rgba(255,255,255,.95);backdrop-filter:blur(10px);box-shadow:0 4px 20px rgba(0,0,0,.1);position:fixed;width:100%;top:0;z-index:1000;transition:.3s;}
        nav{display:flex;justify-content:space-between;align-items:center;padding:1.5rem 0;max-width:1200px;margin:0 auto;}
        .logo{font-size:2rem;font-weight:bold;background:linear-gradient(45deg,#ff6b6b,#4ecdc4);-webkit-background-clip:text;-webkit-text-fill-color:transparent;}
        .nav-links{display:flex;list-style:none;gap:2rem;}
        .nav-links a{text-decoration:none;color:#333;font-weight:500;position:relative;transition:.3s;}
        .nav-links a:hover{color:#ff6b6b;transform:translateY(-2px);}        
        .nav-links a::after{content:'';position:absolute;bottom:-5px;left:0;width:0;height:2px;background:linear-gradient(45deg,#ff6b6b,#4ecdc4);transition:width .3s;}
        .nav-links a:hover::after{width:100%;}
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
        @media(max-width:768px){.nav-links{display:none;}}
    </style>
</head>
<body>
    <!-- Header -->
    <header>
        <nav>
            <div class="logo">
                <a href="homepage.jsp" style="text-decoration:none;color:inherit;">
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
                <li><a href="ProductServlet?action=flower" class="active"><i class="fas fa-seedling"></i> Hoa</a></li>
                <% if(email==null){ %>
                    <li><a href="login.jsp"><i class="fas fa-sign-in-alt"></i> Đăng nhập</a></li>
                <% } else { %>
                    <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Đăng xuất (<%= email %>)</a></li>
                <% } %>
                <li><a href="cart.jsp"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a></li>
            </ul>
        </nav>
    </header>
    <div style="margin-top:100px;"></div>

<%-- lấy id sản phẩm --%>
<%
    String idParam = request.getParameter("id");

    Product product = (Product) request.getAttribute("product");

    if(product==null){out.println("<h2>Không tìm thấy sản phẩm</h2>");return;}
    BigDecimal oldPrice = product.getPrice().multiply(new BigDecimal("1.2"));; // giả lập giá gốc cao hơn 20%
%>
    <div class="container">
        <!-- Left: image -->
        <div class="product-img">
            <% if(product.getImageUrl()!=null && !product.getImageUrl().isEmpty()){ %>
                <img src="<%= product.getImageUrl() %>" alt="<%= product.getName()%>">
            <% } else { %>
                <img src="https://via.placeholder.com/500x500?text=No+Image" alt="no image">
            <% } %>
            <div class="discount">-16%</div>
        </div>
        <!-- Right: info -->
        <div>
            <h1><%= product.getName() %></h1>
            <div style="height:3px;width:40px;background:#4ecdc4;margin:10px 0;"></div>
            <p class="old-price"><%= String.format("%,.0f₫", oldPrice) %></p>
            <span class="new-price"><%= String.format("%,.0f₫", product.getPrice()) %></span>
            <form action="<%=request.getContextPath()%>/CartServlet" method="post" style="margin-top:20px;">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="<%= product.getId() %>">
                <div class="qty-select">
                    <label>Số lượng: </label>
                    <button type="button" onclick="decQty()">-</button>
                    <input type="number" name="quantity" id="qty" value="1" min="1" max="<%= product.getStock() %>" onchange="validateQty(this)">
                    <button type="button" onclick="incQty()">+</button>
                    <span id="qty-error" style="color:red; margin-left:10px; display:none;">Số lượng vượt quá tồn kho</span>
                </div>
                <button type="submit" class="add-btn" id="add-to-cart-btn"><i class="fas fa-cart-plus"></i> Thêm vào giỏ hàng</button>
            </form>
            <p style="margin-top:20px;"><b>Kho: </b><%= product.getStock() %> </p> 
            <p style="margin-top:20px;"><b>Mô tả: </b><%= product.getDescription() %> </p> 
            <h3>Cam kết của Thiệp và Hoa</h3>
            <ul class="commit-list">
                <li><i class="fas fa-check"></i> Cam kết hoa tươi trên 3 ngày</li>
                <li><i class="fas fa-check"></i> Chụp ảnh thực tế trước và sau khi giao hàng</li>
                <li><i class="fas fa-check"></i> Miễn phí thiệp & banner trị giá 20,000đ</li>
                <li><i class="fas fa-check"></i> Giao hoa nhanh nhất trong 1 giờ</li>
                <li><i class="fas fa-check"></i> Hoàn tiền 100% nếu không hài lòng</li>
                <li><i class="fas fa-check"></i> Cập nhật mẫu hoa mới liên tục</li>
                <li><i class="fas fa-check"></i> Hoa tươi mới, chọn lọc kỹ từng bông</li>
                <li><i class="fas fa-check"></i> Thiết kế theo mẫu đã chọn</li>
            </ul>
        </div>
    </div>
<script>
    const maxStock = <%= product.getStock() %>;
    const qtyInput = document.getElementById('qty');
    const qtyError = document.getElementById('qty-error');
    const addToCartBtn = document.getElementById('add-to-cart-btn');

    function validateQty(input) {
        const value = parseInt(input.value);
        if (value > maxStock) {
            qtyError.style.display = 'inline';
            addToCartBtn.disabled = true;
            return false;
        } else {
            qtyError.style.display = 'none';
            addToCartBtn.disabled = false;
            return true;
        }
    }

    function incQty() {
        const currentValue = parseInt(qtyInput.value);
        if (currentValue < maxStock) {
            qtyInput.stepUp();
            qtyError.style.display = 'none';
            addToCartBtn.disabled = false;
        } else {
            qtyError.style.display = 'inline';
            addToCartBtn.disabled = true;
        }
    }

    function decQty() {
        qtyInput.stepDown();
        qtyError.style.display = 'none';
        addToCartBtn.disabled = false;
    }

    // Validate on form submit
    document.querySelector('form').addEventListener('submit', function(e) {
        if (!validateQty(qtyInput)) {
            e.preventDefault();
            return false;
        }
    });
</script>
    <!-- Footer -->
    <footer>
        <div class="footer-content container">
            <div class="footer-section">
                <h3>Thiệp và Hoa</h3>
                <p>Công nghệ Web Hè 2025.</p>
            </div>
            <div class="footer-section">
                <h3>Đường dẫn nhanh</h3>
                <ul>
                    <li><a href="ecards.jsp">E-Card Gallery</a></li>
                    <li><a href="flowers.jsp">Flower Catalog</a></li>
                    <li><a href="#delivery">Delivery Info</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Chăm sóc khách hàng</h3>
                <ul>
                    <li><a href="#faq">FAQ</a></li>
                    <li><a href="#support">Support</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Kết nối</h3>
                <ul>
                    <li><a href="#"><i class="fab fa-facebook"></i> Facebook</a></li>
                    <li><a href="#"><i class="fab fa-instagram"></i> Instagram</a></li>
                </ul>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2025 Thiệp và Hoa | Đại học Bách khoa Đà Nẵng</p>
        </div>
    </footer>

    <script>
    // header scroll effect
    window.addEventListener('scroll',()=>{
        const header=document.querySelector('header');
        if(window.scrollY>100){header.style.background='rgba(255,255,255,0.98)';header.style.boxShadow='0 4px 30px rgba(0,0,0,0.15)';}
        else{header.style.background='rgba(255,255,255,0.95)';header.style.boxShadow='0 4px 20px rgba(0,0,0,0.1)';}
    });
    </script>
</body>
</html>
