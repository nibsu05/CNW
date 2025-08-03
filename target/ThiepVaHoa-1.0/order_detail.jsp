<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.OrderItem" %>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
</head>
<body>
<%
	List<OrderItem> items = (List<OrderItem>) request.getAttribute("orderItems");
%>
<h1>Chi tiết đơn hàng #<%= items.getFirst().getOrderId() %></h1>
<table border="1" cellpadding="8">
    <thead>
    <tr>
        <th>ID</th>
        <th>Sản phẩm</th>
        <th>Số lượng</th>
        <th>Đơn giá</th>
        <th>Ghi chú</th>
    </tr>
    </thead>
    <tbody>
    <%

        for (OrderItem item : items) {
    %>
        <tr>
            <td><%= item.getId() %></td>
            <td><%= item.getproductId() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getPrice() %></td>
            <td><%= item.getNote() %></td>
        </tr>
    <%
        }
    %>
    </tbody>
</table>
<a href="Order">← Quay lại danh sách đơn hàng</a>
</body>
</html>
