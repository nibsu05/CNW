<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.bean.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng thành công - Thiệp và Hoa</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .success-container {
            background: white;
            padding: 3rem;
            border-radius: 15px;
            text-align: center;
            max-width: 600px;
            width: 90%;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }
        
        .success-icon {
            font-size: 5rem;
            color: #4ecdc4;
            margin-bottom: 1.5rem;
        }
        
        h1 {
            color: #333;
            margin-bottom: 1rem;
        }
        
        .order-id {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 8px;
            margin: 1.5rem 0;
            font-size: 1.2rem;
            font-weight: 600;
            color: #333;
        }
        
        .btn {
            display: inline-block;
            background: linear-gradient(45deg, #4ecdc4, #45b7d1);
            color: white;
            padding: 12px 30px;
            border-radius: 30px;
            text-decoration: none;
            font-weight: 600;
            margin-top: 1.5rem;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            font-size: 1rem;
        }
        
        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }
        
        .btn i {
            margin-right: 8px;
        }
        
        .info-text {
            color: #666;
            line-height: 1.6;
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">
            <i class="fas fa-check-circle"></i>
        </div>
        <h1>Đặt Hàng Thành Công!</h1>
        <p class="info-text">Cảm ơn bạn đã đặt hàng. Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất để xác nhận đơn hàng.</p>
        
        <div class="order-id">
            Mã đơn hàng: <strong>${param.orderId}</strong>
        </div>
        
        <p class="info-text">
            Bạn có thể kiểm tra trạng thái đơn hàng bất cứ lúc nào bằng mã đơn hàng này.
            Chúng tôi sẽ gửi thông tin chi tiết đơn hàng đến email của bạn.
        </p>
        
        <div class="action-buttons">
            <button onclick="closeAndRedirect()" class="btn">
                <i class="fas fa-home"></i> Về Trang Chủ
            </button>
        </div>
        
        <script>
            function closeAndRedirect() {
                // Check if this is in a popup window
                if (window.opener) {
                    // Close the popup
                    window.close();
                    // Redirect the parent window to homepage
                    window.opener.location.href = 'homepage.jsp';
                } else {
                    // If not in a popup, just redirect
                    window.location.href = 'homepage.jsp';
                }
            }
        </script>
    </div>
</body>
</html>
