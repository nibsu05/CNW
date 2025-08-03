<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.BigDecimal" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán - Thiệp và Hoa</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding-top: 20px;
            margin: 0;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: rgba(255,255,255,0.95);
            backdrop-filter: blur(20px);
            padding: 2rem 3rem;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 1.5rem;
            background: linear-gradient(45deg,#ff6b6b,#4ecdc4);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip:text;
        }
        .form-group {
            margin-bottom: 1.2rem;
            display: flex;
            flex-direction: column;
        }
        label {
            margin-bottom: 0.4rem;
            font-weight: 600;
        }
        input, textarea, select {
            padding: 10px 14px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            resize: vertical;
        }
        input[readonly] {
            background: #f1f1f1;
        }
        .btn {
            width: 100%;
            padding: 12px;
            font-size: 1rem;
            border: none;
            border-radius: 25px;
            color: #fff;
            background: linear-gradient(45deg,#ff6b6b,#4ecdc4);
            cursor: pointer;
            transition: all .3s ease;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(255,107,107,.3);
        }
        nav {
            max-width:1200px;
            margin:0 auto;
            padding:1rem 0;
            display:flex;
            justify-content:space-between;
            align-items:center;
        }
        .logo{
            font-size:1.5rem;font-weight:bold;
            background:linear-gradient(45deg,#ff6b6b,#4ecdc4);
            -webkit-background-clip:text;
            -webkit-text-fill-color:transparent;
            background-clip:text;
        }
        .logo a{text-decoration:none;color:inherit;}
        .nav-links{display:flex;gap:1.5rem;list-style:none;}
        .nav-links a{text-decoration:none;color:#333;font-weight:500;transition:.3s;}
        .nav-links a:hover{color:#ff6b6b;transform:translateY(-2px);}
        .nav-links a.active{background:#4ecdc4;color:#fff;padding:6px 14px;border-radius:6px;}
        
        /* Checkout specific styles */
        h2 {
            font-size: 1.3rem;
            color: #444;
            margin: 1.5rem 0 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid #eee;
        }
        
        .required {
            color: #ff6b6b;
        }
        
        .payment-method {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }
        
        .payment-option {
            display: flex;
            align-items: center;
            padding: 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        .payment-option:hover {
            border-color: #4ecdc4;
            background-color: #f8f9fa;
        }
        
        .payment-option i {
            margin-right: 10px;
            font-size: 1.2rem;
            color: #4ecdc4;
        }
        
        .order-summary {
            border: 1px solid #eee;
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 1.5rem;
        }
        
        .order-item {
            display: flex;
            justify-content: space-between;
            padding: 0.8rem 0;
            border-bottom: 1px solid #f5f5f5;
        }
        
        .order-item:last-child {
            border-bottom: none;
        }
        
        .item-name {
            font-weight: 600;
            margin-bottom: 0.3rem;
        }
        
        .item-quantity {
            font-size: 0.9rem;
            color: #666;
        }
        
        .item-price {
            font-weight: 600;
            color: #ff6b6b;
        }
        
        .order-total {
            display: flex;
            justify-content: space-between;
            margin-top: 1rem;
            padding-top: 1rem;
            border-top: 1px solid #eee;
            font-size: 1.1rem;
            font-weight: 600;
        }
        
        .total-amount {
            color: #ff6b6b;
            font-size: 1.2rem;
        }
        
        .terms {
            margin: 1.5rem 0;
        }
        
        .checkbox-container {
            display: flex;
            align-items: center;
            cursor: pointer;
            position: relative;
            padding-left: 35px;
            user-select: none;
        }
        
        .checkbox-container input {
            position: absolute;
            opacity: 0;
            cursor: pointer;
            height: 0;
            width: 0;
        }
        
        .checkmark {
            position: absolute;
            top: 0;
            left: 0;
            height: 25px;
            width: 25px;
            background-color: #eee;
            border-radius: 4px;
        }
        
        .checkbox-container:hover input ~ .checkmark {
            background-color: #ddd;
        }
        
        .checkbox-container input:checked ~ .checkmark {
            background-color: #4ecdc4;
        }
        
        .checkmark:after {
            content: "";
            position: absolute;
            display: none;
        }
        
        .checkbox-container input:checked ~ .checkmark:after {
            display: block;
        }
        
        .checkbox-container .checkmark:after {
            left: 9px;
            top: 5px;
            width: 5px;
            height: 10px;
            border: solid white;
            border-width: 0 3px 3px 0;
            transform: rotate(45deg);
        }
        
        @media (max-width: 576px) {
            .container {
                padding: 1.5rem;
                margin: 1rem;
            }
            
            .nav-links {
                gap: 0.8rem;
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>


<%-- Lấy giỏ hàng và tổng tiền --%>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    BigDecimal totalPrice = (cart != null) ? cart.getTotalPrice() : BigDecimal.ZERO;
    String formattedTotal = (cart != null) ? cart.getFormattedTotalPrice() : "0₫";
%>

<div class="container" style="margin-top:40px;">
    <h1>Thông tin thanh toán</h1>

    <form action="OrderServlet" method="POST" onsubmit="return validateCheckoutForm()" id="checkoutForm">
        <h2>Thông tin khách hàng</h2>
        <div class="form-group">
            <label for="fullName">Họ và tên <span class="required">*</span></label>
            <input type="text" id="fullName" name="fullName" required>
        </div>
        <div class="form-group">
            <label for="phone">Số điện thoại <span class="required">*</span></label>
            <input type="tel" id="phone" name="phone" pattern="[0-9]{10,11}" required>
        </div>
        <div class="form-group">
            <label for="email">Email <span class="required">*</span></label>
            <input type="email" id="email" name="email" value="<%= session.getAttribute("email") != null ? session.getAttribute("email") : "" %>" <%= session.getAttribute("email") != null ? "readonly" : "" %> required>
        </div>

        <h2>Địa chỉ giao hàng</h2>
        <div class="form-group">
            <label for="deliveryAddress">Địa chỉ <span class="required">*</span></label>
            <textarea id="deliveryAddress" name="deliveryAddress" rows="3" required></textarea>
        </div>
        <div class="form-group">
            <label for="deliveryTime">Thời gian giao hàng <span class="required">*</span></label>
            <input type="datetime-local" id="deliveryTime" name="deliveryTime" required 
               min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>T00:00"
               step="60">
        </div>

        <h2>Phương thức thanh toán</h2>
        <div class="form-group">
            <div class="payment-method">
                <label class="payment-option">
                    <input type="radio" name="paymentMethod" value="COD" checked>
                    <i class="fas fa-money-bill-wave"></i> Thanh toán khi nhận hàng (COD)
                </label>
                <label class="payment-option">
                    <input type="radio" name="paymentMethod" value="BANK_TRANSFER">
                    <i class="fas fa-university"></i> Chuyển khoản ngân hàng
                </label>
            </div>
        </div>

        <h2>Đơn hàng của bạn</h2>
        <div class="order-summary">
            <% if (cart != null && !cart.getItems().isEmpty()) { 
                for (CartItem item : cart.getItems()) { 
                    Product product = item.getProduct();
            %>
                <div class="order-item">
                    <div class="item-info">
                        <div class="item-name"><%= product.getName() %></div>
                        <div class="item-quantity">Số lượng: <%= item.getQuantity() %></div>
                    </div>
                    <div class="item-price"><%= String.format("%,d₫", product.getPrice().multiply(new BigDecimal(item.getQuantity())).intValue()) %></div>
                </div>
            <% } 
            } %>
            <div class="order-total">
                <span>Tổng cộng:</span>
                <span class="total-amount"><%= formattedTotal %></span>
            </div>
        </div>

        <div class="form-group terms">
            <label class="checkbox-container">
                <input type="checkbox" id="agreeTerms" required>
                <span class="checkmark"></span>
                Tôi đồng ý với <a href="#" style="color: #4ecdc4;">điều khoản và điều kiện</a> của cửa hàng
            </label>
        </div>

        <!-- Hidden fields -->
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="userId" value="<%= session.getAttribute("userId") != null ? session.getAttribute("userId") : "GUEST" %>">
        <input type="hidden" name="totalPrice" id="totalPriceInput" value="<%= totalPrice.toString() %>">
        <input type="hidden" name="status" value="PENDING">
        <input type="hidden" name="createAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
        
        <button type="submit" class="btn"><i class="fas fa-check"></i> Xác nhận đặt hàng</button>
    </form>
    
    <script>
    function validateCheckoutForm() {
        const deliveryTime = new Date(document.getElementById('deliveryTime').value);
        const now = new Date();
        
        // Kiểm tra thời gian giao hàng phải trong tương lai
        if (deliveryTime <= now) {
            alert('Vui lòng chọn thời gian giao hàng trong tương lai');
            return false;
        }
        
        // Kiểm tra điều khoản
        if (!document.getElementById('agreeTerms').checked) {
            alert('Vui lòng đồng ý với điều khoản và điều kiện');
            return false;
        }
        
        return true;
    }
    
    // Đặt giá trị mặc định cho thời gian giao hàng (2 giờ sau thời điểm hiện tại)
    document.addEventListener('DOMContentLoaded', function() {
        // Set default delivery time to 2 hours from now
        const now = new Date();
        now.setHours(now.getHours() + 2);
        now.setMinutes(0, 0, 0); // Round to nearest hour
        
        // Format as YYYY-MM-DDThh:mm
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        
        const formattedDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
        document.getElementById('deliveryTime').value = formattedDateTime;
        
        // Ensure total price is properly formatted
        const totalPriceInput = document.getElementById('totalPriceInput');
        if (totalPriceInput) {
            totalPriceInput.value = parseFloat(totalPriceInput.value).toFixed(2);
        }
    });
    </script>
</div>
</body>
</html>
