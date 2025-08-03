<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng - Thiệp và Hoa</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding-top: 80px;
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

        .logo {
            font-size: 2rem;
            font-weight: bold;
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .logo a {
            text-decoration: none;
            color: inherit;
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

        /* Cart Container */
        .cart-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            margin: 2rem 0;
            overflow: hidden;
        }

        .cart-header {
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            color: white;
            padding: 2rem;
            text-align: center;
        }

        .cart-header h1 {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }

        .cart-header p {
            font-size: 1.1rem;
            opacity: 0.9;
        }

        .cart-content {
            padding: 2rem;
        }

        /* Empty Cart */
        .empty-cart {
            text-align: center;
            padding: 4rem 2rem;
            color: #666;
        }

        .empty-cart i {
            font-size: 4rem;
            color: #ddd;
            margin-bottom: 1rem;
        }

        .empty-cart h2 {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            color: #333;
        }

        .empty-cart p {
            margin-bottom: 2rem;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 25px;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .btn-primary {
            background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(255, 107, 107, 0.3);
        }

        /* Cart Items */
        .cart-items {
            margin-bottom: 2rem;
        }

        .cart-item {
            display: grid;
            grid-template-columns: 100px 2fr 1fr 1fr 50px;
            gap: 1rem;
            align-items: center;
            padding: 1.5rem;
            border-bottom: 1px solid #eee;
            transition: all 0.3s ease;
        }

        .cart-item:hover {
            background: rgba(78, 205, 196, 0.05);
        }

        .item-image {
            width: 80px;
            height: 80px;
            background: #fff; /* bỏ màu để nhìn rõ ảnh */
            border-radius: 10px;
            overflow: hidden; /* cắt phần dư */
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .item-image img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* ảnh che full ô */
        }

        .item-details h3 {
            font-size: 1.1rem;
            margin-bottom: 0.5rem;
            color: #333;
        }

        .item-details p {
            color: #666;
            font-size: 0.9rem;
        }

        .item-price {
            font-weight: bold;
            color: #ff6b6b;
            font-size: 1.1rem;
        }

        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .quantity-btn {
            width: 30px;
            height: 30px;
            border: 1px solid #ddd;
            background: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .quantity-btn:hover {
            background: #4ecdc4;
            color: white;
            border-color: #4ecdc4;
        }

        .quantity-input {
            width: 50px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 5px;
        }

        .remove-btn {
            color: #ff6b6b;
            cursor: pointer;
            font-size: 1.2rem;
            transition: all 0.3s ease;
        }

        .remove-btn:hover {
            color: #e74c3c;
            transform: scale(1.1);
        }

        /* Cart Summary */
        .cart-summary {
            background: #f8f9fa;
            padding: 2rem;
            border-radius: 15px;
            margin-top: 2rem;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 1rem;
            font-size: 1.1rem;
        }

        .summary-row.total {
            font-size: 1.3rem;
            font-weight: bold;
            color: #ff6b6b;
            border-top: 2px solid #ddd;
            padding-top: 1rem;
            margin-top: 1rem;
        }

        .cart-actions {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
            flex-wrap: wrap;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }

        .btn-danger {
            background: #dc3545;
            color: white;
        }

        .btn-danger:hover {
            background: #c82333;
            transform: translateY(-2px);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .cart-item {
                grid-template-columns: 80px 1fr;
                gap: 1rem;
            }

            .item-price, .quantity-controls, .remove-btn {
                grid-column: 2;
                justify-self: start;
                margin-top: 0.5rem;
            }

            .cart-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
        /* Modal styles */
        .modal{
            position:fixed;
            top:0;left:0;width:100%;height:100%;
            background:rgba(0,0,0,0.6);
            display:none;
            justify-content:center;
            align-items:center;
            z-index:2000;
        }
        .modal-content{
            width:90%;
            max-width:900px;
            height:80%;
            background:#fff;
            border-radius:10px;
            overflow:hidden;
            position:relative;
        }
        .close-btn{
            position:absolute;
            top:10px;
            right:15px;
            font-size:24px;
            cursor:pointer;
            z-index:5;
        }
    </style>
</head>

<body>
            <%
    String email = (String) session.getAttribute("email");
    Object roleObj = session.getAttribute("role");
    int role = (roleObj != null) ? (int) roleObj : 0; // Default to 0 (regular user) if not logged in
%>
<header>
    <nav class="container">
        <div class="logo">
            <a href="homepage.jsp" style="text-decoration: none; color: inherit;">
                <i class="fas fa-gift"></i> Thiệp và Hoa
            </a>
        </div>
        <ul class="nav-links">
                <li><a href="ProductServlet?action=card"><i class="fas fa-envelope"></i> Thiệp</a></li>
                <li><a href="ProductServlet?action=flower" class="active"><i class="fas fa-seedling"></i> Hoa</a></li>
            <                               <% if(email==null){ %>
                    <li><a href="login.jsp"><i class="fas fa-sign-in-alt"></i> Đăng nhập</a></li>
                <% } else { %>
                    <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Đăng xuất (<%= email %>)</a></li>
                <% } %>>
            <li><a href="cart.jsp" class="active"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a></li>
        </ul>
    </nav>
</header>

<div class="container">
    <div class="cart-container">
        <div class="cart-header">
            <h1><i class="fas fa-shopping-cart"></i> Giỏ hàng</h1>
            <p>Quản lý sản phẩm của bạn</p>
        </div>

        <div class="cart-content">
            <%
                // Lấy giỏ hàng từ session
                Cart cart = (Cart) session.getAttribute("cart");



                if (cart == null || cart.isEmpty()) {
            %>
            <!-- Giỏ hàng trống -->
            <div class="empty-cart">
                <i class="fas fa-shopping-cart"></i>
                <h2>Giỏ hàng trống</h2>
                <p>Bạn chưa có sản phẩm nào trong giỏ hàng.</p>
                <a href="homepage.jsp" class="btn btn-primary">
                    <i class="fas fa-home"></i> Tiếp tục mua sắm
                </a>
            </div>
            <%
                } else {
            %>
            <!-- Hiển thị sản phẩm -->
            <div class="cart-items">
                <%
                    for (CartItem item : cart.getItems()) {
                        Product p = item.getProduct();  // tránh trùng tên biến
                %>
                <div class="cart-item">
                    <div class="item-image">
                        <% if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) { %>
                            <img src="<%= p.getImageUrl() %>" alt="<%= p.getName() %>" >
                        <% } else { %>
                            <% if ("flower".equals(p.getType())) { %>
                                <i class="fas fa-seedling"></i>
                            <% } else { %>
                                <i class="fas fa-envelope"></i>
                            <% } %>
                        <% } %>
                    </div>
                    <div class="item-details">
                        <h3><%= p.getName() %></h3>
                        <p><%= p.getDescription() %></p>
                    </div>
                    <div class="item-price">
                        <% if (p.getPrice().intValue() == 0) { %>
                            Miễn phí
                        <% } else { %>
                            <%= String.format("%,.0f₫", p.getPrice()) %>
                        <% } %>
                    </div>
                    <div class="quantity-controls">
<button class="quantity-btn" type="button" onclick="updateQuantity('<%= p.getId() %>', parseInt(document.querySelector('input[data-id=<%= p.getId() %>]').value) - 1)">-</button>
                       <input type="number" class="quantity-input"
					       data-id="<%= p.getId() %>"
					       value="<%= item.getQuantity() %>"
					       min="1"
					       onchange="updateQuantity('<%= p.getId() %>', this.value, true)">

                        <button class="quantity-btn" type="button" onclick="updateQuantity('<%= p.getId() %>', parseInt(document.querySelector('input[data-id=<%= p.getId() %>]').value) + 1)">+</button>
                    </div>
                    <div class="remove-btn" onclick="removeItem('<%= p.getId() %>')">
                        <i class="fas fa-trash"></i>
                    </div>
                </div>
                <% } %>
            </div>

            <!-- Tóm tắt -->
            <div class="cart-summary">
                <div class="summary-row">
                    <span>Tổng số sản phẩm:</span>
                    <span><%= cart.getTotalItems() %></span>
                </div>
                <div class="summary-row total">
                    <span>Tổng tiền:</span>
                    <span><%= cart.getFormattedTotalPrice() %></span>
                </div>
            </div>

            <!-- Nút -->
            <div class="cart-actions">
                <a href="homepage.jsp" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Tiếp tục mua sắm
                </a>
                <button class="btn btn-danger" onclick="clearCart()">
                    <i class="fas fa-trash"></i> Xóa giỏ hàng
                </button>
                <button class="btn btn-primary" onclick="checkout()">
                    <i class="fas fa-check"></i> Xác nhận đặt hàng
                </button>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script>
    function clearCart() {
        if (confirm('Bạn có chắc muốn xóa toàn bộ giỏ hàng?')) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'CartServlet';

            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'clear';

            form.appendChild(actionInput);
            document.body.appendChild(form);
            form.submit();
        }
    }

    function checkout() {
        document.getElementById('checkoutModal').style.display = 'flex';
        document.getElementById('checkoutFrame').src = 'checkout.jsp';
    }

    function updateQuantity(productId, value, isDirectInput = false) {
        const selector = `input[data-id="${productId}"]`;
        let input = document.querySelector(selector);

        if (!input) {
            console.error("Không tìm thấy input cho sản phẩm:", productId);
            return;
        }

        let quantity = isDirectInput ? parseInt(value) : value;
        if (isNaN(quantity) quantity = 1;
        if (quantity < 1) quantity = 1;

        // Chuyển hướng để cập nhật
        window.location.href = `CartServlet?action=update&productId=${productId}&quantity=${quantity}`;
    }

    function removeItem(productId) {
        if (confirm("Bạn có chắc muốn xóa sản phẩm này?")) {
            window.location.href = `CartServlet?action=remove&productId=${productId}`;
        }
    }

    function closeCheckout() {
        document.getElementById('checkoutModal').style.display = 'none';
    }
</script>
<!-- Modal for Checkout -->
<div id="checkoutModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeCheckout()">&times;</span>
        <iframe id="checkoutFrame" style="width:100%;height:100%;border:none;"></iframe>
    </div>
</div>
<script>
    function closeCheckout(){
        document.getElementById('checkoutModal').style.display='none';
    }
</script>
</body>
</html> 