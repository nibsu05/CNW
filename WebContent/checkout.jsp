<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.*" %>
<%@ page import="java.util.*" %>
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
    </style>
</head>
<body>


<%-- Lấy giỏ hàng và tổng tiền --%>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    double totalPrice = (cart != null) ? cart.getTotalPrice() : 0;
    String formattedTotal = (cart != null) ? cart.getFormattedTotalPrice() : "0₫";
%>

<div class="container" style="margin-top:40px;">
    <h1>Thông tin thanh toán</h1>

    <form action="OrderServlet" method="POST">

        <div class="form-group">
            <label for="deliveryTime">Thời gian giao hàng</label>
            <input type="datetime-local" id="deliveryTime" name="deliveryTime" required>
        </div>
        <div class="form-group">
            <label for="deliveryAddress">Địa chỉ giao hàng</label>
            <textarea id="deliveryAddress" name="deliveryAddress" rows="3" required></textarea>
        </div>
        <div class="form-group">
            <label for="totalPrice">Tổng tiền</label>
            <input type="text" id="totalPriceDisplay" value="<%= formattedTotal %>" readonly>
            <input type="hidden" id="totalPrice" name="totalPrice" value="<%= totalPrice %>">
        </div>
        <input type="hidden" name="status" value="Pending">
        <input type="hidden" name="createAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) %>">
        <button type="submit" class="btn"><i class="fas fa-check"></i> Xác nhận đặt hàng</button>
    </form>
</div>
</body>
</html>
