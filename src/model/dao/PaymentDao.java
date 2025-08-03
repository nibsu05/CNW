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
        try {
            String sql = "SELECT * FROM payment";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getString("Id"));
                p.setOrderId(rs.getString("OrderId"));
                p.setMethod(rs.getString("Method"));
                p.setStatus(rs.getString("Status"));
                p.setPaidAt(rs.getDate("PaidAt"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Payment getById(String id) {
        try {
            String sql = "SELECT * FROM payment WHERE Id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getString("Id"));
                p.setOrderId(rs.getString("OrderId"));
                p.setMethod(rs.getString("Method"));
                p.setStatus(rs.getString("Status"));
                p.setPaidAt(rs.getDate("PaidAt"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Payment p) {
        try {
            String sql = "INSERT INTO payment(Id, OrderId, Method, Status, PaidAt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getId());
            ps.setString(2, p.getOrderId());
            ps.setString(3, p.getMethod());
            ps.setString(4, p.getStatus());
            ps.setDate(5, new java.sql.Date(p.getPaidAt().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Payment p) {
        try {
            String sql = "UPDATE payment SET OrderId = ?, Method = ?, Status = ?, PaidAt = ? WHERE Id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getOrderId());
            ps.setString(2, p.getMethod());
            ps.setString(3, p.getStatus());
            ps.setDate(4, new java.sql.Date(p.getPaidAt().getTime()));
            ps.setString(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
