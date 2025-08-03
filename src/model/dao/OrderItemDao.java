package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.bean.OrderItem;

public class OrderItemDao {
    private Connection conn;

    public OrderItemDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cnw", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        return new OrderItem(
            rs.getString("Id"),
            rs.getString("OrderId"),
            rs.getString("ProductId"),
            rs.getInt("Quantity"),
            rs.getBigDecimal("Price"),
            rs.getString("Note")
            
        );
    }

    public List<OrderItem> getAll() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM orderitem";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToOrderItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderItem getById(String id) {
        String sql = "SELECT * FROM orderitem WHERE Id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSetToOrderItem(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderItem> getByOrderId(String orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT oi.*, p.Name AS ProductName FROM orderitem oi JOIN product p ON oi.ProductId = p.Id WHERE oi.OrderId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToOrderItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(OrderItem item) {
        String sql = "INSERT INTO orderitem (Id, OrderId, ProductId, Quantity, Price, Note) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getId());
            ps.setString(2, item.getOrderId());
            ps.setString(3, item.getproductId());
            ps.setInt(4, item.getQuantity());
            ps.setBigDecimal(5, item.getPrice());
            ps.setString(6, item.getNote());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(OrderItem item) {
        String sql = "UPDATE orderitem SET OrderId=?, ProductId=?, Quantity=?, Price=?, Note=? WHERE Id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getOrderId());
            ps.setString(2, item.getproductId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPrice());
            ps.setString(5, item.getNote());
            ps.setString(6, item.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM orderitem WHERE Id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
