package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.bean.Payment;

public class PaymentDao {
    private Connection conn;

    public PaymentDao() throws SQLException {
        // Add useUnicode=true&characterEncoding=UTF-8 to support Vietnamese characters
        String url = "jdbc:mysql://localhost:3306/cnw?useUnicode=true&characterEncoding=UTF-8";
        conn = DriverManager.getConnection(url, "root", "");
    }

    public List<Payment> getAll() {
        List<Payment> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM payment");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Payment p = new Payment(
                    rs.getString("Id"),
                    rs.getString("OrderId"),
                    rs.getBigDecimal("Amount"),
                    rs.getString("Method"),
                    rs.getString("Status"),
                    rs.getDate("PaidAt")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Payment getById(String id) {
        String sql = "SELECT * FROM payment WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Payment(
                        rs.getString("Id"),
                        rs.getString("OrderId"),
                        rs.getBigDecimal("Amount"),
                        rs.getString("Method"),
                        rs.getString("Status"),
                        rs.getDate("PaidAt")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Payment p) {
        return insertPayment(p);
    }
    
    public boolean insertPayment(Payment p) {
        try {
            String sql = "INSERT INTO payment(Id, OrderId, Amount, Method, Status, PaidAt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getId());
                ps.setString(2, p.getOrderId());
                ps.setBigDecimal(3, p.getAmount());
                ps.setString(4, p.getMethod());
                ps.setString(5, p.getStatus());
                ps.setDate(6, new java.sql.Date(p.getPaidAt().getTime()));
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Payment p) {
        String sql = "UPDATE payment SET OrderId = ?, Amount = ?, Method = ?, Status = ?, PaidAt = ? WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getOrderId());
            ps.setBigDecimal(2, p.getAmount());
            ps.setString(3, p.getMethod());
            ps.setString(4, p.getStatus());
            ps.setDate(5, new java.sql.Date(p.getPaidAt().getTime()));
            ps.setString(6, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            String sql = "DELETE FROM payment WHERE Id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
