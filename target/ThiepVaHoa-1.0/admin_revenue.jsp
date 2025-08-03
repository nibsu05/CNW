<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.bean.Order" %>
<%@ page  import= "java.math.BigDecimal" %>
<%@ page  import= "java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thống kê doanh thu</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;background:#f5f6fa;margin:0;padding:0;}
        .container{max-width:1000px;margin:30px auto;padding:20px;background:#fff;border-radius:10px;box-shadow:0 4px 10px rgba(0,0,0,.05);}        h1{text-align:center;margin-bottom:1.5rem;}
        table{width:100%;border-collapse:collapse;margin-top:1rem;}
        th,td{border:1px solid #ddd;padding:8px;text-align:left;}
        th{background:#4ecdc4;color:#fff;}
        .back-btn{display:inline-block;margin-top:1rem;padding:10px 18px;background:#4ecdc4;color:#fff;border-radius:6px;text-decoration:none;transition:.3s;}
        .back-btn:hover{background:#38b8af;transform:translateY(-2px);}    </style>
</head>
<body>
<%-- Giả lập danh sách đơn hàng --%>
<%
	Map<String, BigDecimal> monthlyRevenue = (Map<String, BigDecimal>) request.getAttribute("monthlyRevenue");
	BigDecimal totalPaid = (BigDecimal) request.getAttribute("totalPaid");
%>
    <div class="container">
        <h1>Thống kê doanh thu</h1>
        <h3>Tổng doanh thu: <%= String.format("%,.0f₫", totalPaid) %></h3>
        <canvas id="revChart" height="120"></canvas>
        <table>
            <thead><tr><th>Tháng</th><th>Doanh thu</th></tr></thead>
            <tbody>
            <% for(Map.Entry<String, BigDecimal> entry: monthlyRevenue.entrySet()){ %>
                <tr><td><%= entry.getKey() %></td><td><%= String.format("%,.0f₫", entry.getValue()) %></td></tr>
            <% } %>
            </tbody>
        </table>
        <a href="admin_dashboard.jsp" class="back-btn"><i class="fas fa-arrow-left"></i> Quay lại</a>
    </div>
<script>
const ctx = document.getElementById('revChart');
const labels = [<% for(String key : monthlyRevenue.keySet()) { %>"<%= key %>", <% } %>];
const data = [<% for(BigDecimal val : monthlyRevenue.values()) { %><%= val %>, <% } %>];

new Chart(document.getElementById('revChart'), {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [{
            label: 'Doanh thu',
            data: data,
            backgroundColor: '#4ecdc4'
        }]
    },
    options: {
        plugins: {
            legend: { display: false }
        }
    }
});
</script>
</body>
</html>
