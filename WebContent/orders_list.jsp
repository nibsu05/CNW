<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.Order" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f2f2f2; padding: 20px; }
        .container { background: white; padding: 20px; border-radius: 10px; max-width: 1000px; margin: auto; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
        th { background-color: #4ecdc4; color: white; }
        a.btn { padding: 5px 10px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; }
        a.btn:hover { background: #2980b9; }
    </style>
</head>
<body>
<div class="container">
    <h1>Danh sách đơn hàng</h1>
    <table>
        <thead>
            <tr>
                <th>Mã đơn</th>
                <th>Người dùng</th>
                <th>Ngày giao</th>
                <th>Tổng tiền</th>
                <th>Địa chỉ giao</th>
                <th>Trạng thái</th>
                <th>Ngày tạo</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null && !orders.isEmpty()) {
                for (Order o : orders) {
        %>
            <tr>
                <td><%= o.getId() %></td>
                <td><%= o.getUserId() %></td>
                <td><%= o.getDeliveryTime() %></td>
                <td><%= String.format("%,.0f₫", o.getTotalPrice()) %></td>
                <td><%= o.getDeliveryAddress() %></td>
                <td><%= o.getStatus() %></td>
                <td><%= o.getCreateAt() %></td>
                <td>
                    <a class="btn" href="OrderItem?action=byOrder&id=<%= o.getId() %>">Xem</a>

                </td>
            </tr>

        <%
                }
            } else {
        %>
            <tr><td colspan="8">Không có đơn hàng nào.</td></tr>
        <%
            }
        %>
        </tbody>
    </table>
                <a href="admin_dashboard.jsp">← Quay lại </a>
</div>
</body>
</html>
