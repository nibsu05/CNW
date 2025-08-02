package model.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.math.BigDecimal;

public class FinancialReportDao {
    private Connection conn;

    public FinancialReportDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cnw", "root", "");
    }

    // 1. Tổng doanh thu theo ngày
    public Map<Date, BigDecimal> getDailyRevenue() {
        Map<Date, BigDecimal> result = new LinkedHashMap<>();
        String sql = "SELECT DATE(CreateAt) AS Ngay, SUM(TotalPrice) AS DoanhThu " +
                     "FROM orders WHERE Status = 'completed' GROUP BY DATE(CreateAt) ORDER BY Ngay";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getDate("Ngay"), rs.getBigDecimal("DoanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 2. Tổng doanh thu theo tháng
    public Map<String, BigDecimal> getMonthlyRevenue() {
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        String sql = "SELECT YEAR(CreateAt) AS Nam, MONTH(CreateAt) AS Thang, SUM(TotalPrice) AS DoanhThu " +
                     "FROM orders WHERE Status = 'completed' GROUP BY YEAR(CreateAt), MONTH(CreateAt)";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String key = rs.getInt("Thang") + "/" + rs.getInt("Nam");
                result.put(key, rs.getBigDecimal("DoanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 3. Doanh thu theo sản phẩm
    public Map<String, BigDecimal> getRevenueByProduct() {
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        String sql = "SELECT p.Name, SUM(oi.Quantity * oi.UnitPrice) AS DoanhThu " +
                     "FROM orderitem oi " +
                     "JOIN product p ON oi.ProductId = p.Id " +
                     "JOIN orders o ON oi.OrderId = o.Id " +
                     "WHERE o.Status = 'completed' " +
                     "GROUP BY p.Id, p.Name ORDER BY DoanhThu DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("Name"), rs.getBigDecimal("DoanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 4. Khách hàng chi tiêu nhiều nhất
    public Map<String, BigDecimal> getTopCustomers() {
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        String sql = "SELECT u.FullName, SUM(o.TotalPrice) AS ChiTieu " +
                     "FROM user u " +
                     "JOIN orders o ON u.Id = o.UserId " +
                     "WHERE o.Status = 'completed' " +
                     "GROUP BY u.Id, u.FullName " +
                     "ORDER BY ChiTieu DESC LIMIT 10";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("FullName"), rs.getBigDecimal("ChiTieu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 5. Tổng tiền đã thanh toán
    public BigDecimal getTotalPaid() {
        String sql = "SELECT SUM(Amount) AS TongThanhToan FROM payment WHERE Status = 'paid'";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal("TongThanhToan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
