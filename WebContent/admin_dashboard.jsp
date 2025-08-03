<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lý - Thiệp và Hoa</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;background:#f5f6fa;margin:0;padding:0;}
        .container{max-width:1000px;margin:40px auto;padding:20px;background:#fff;border-radius:10px;box-shadow:0 4px 10px rgba(0,0,0,.05);}        h1{text-align:center;margin-bottom:2rem;}
        ul{list-style:none;padding:0;display:flex;gap:2rem;justify-content:center;}
        li a{display:block;padding:1rem 2rem;background:#4ecdc4;color:#fff;border-radius:8px;text-decoration:none;transition:.3s;}
        li a:hover{background:#38b8af;transform:translateY(-2px);}    </style>
</head>
<body>
    <div class="container">
        <h1>Bảng điều khiển quản trị</h1>
        <ul>
            <li><a href="${pageContext.request.contextPath}/FinancialReport"><i class="fas fa-chart-line"></i> Thống kê doanh thu</a></li>
            <li><a href="ProductServlet?action=list"><i class="fas fa-database"></i> Quản lý CRUD</a></li>
            <li><a href="Order"><i class="fas fa-database"></i> Đơn hàng</a></li>
            <li><a href="homepage.jsp"><i class="fas fa-home"></i> Về trang chủ</a></li>
        </ul>
    </div>
</body>
</html>
