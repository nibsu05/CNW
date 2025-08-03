package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.bean.Order;
import java.math.BigDecimal;

public class OrderDao {
    private Connection conn;

    public OrderDao() throws SQLException  {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cnw","root","");
	
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `orders`";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean insertOrder(Order order) {
        String sql = "INSERT INTO `orders` (Id, UserId, DeliveryTime, TotalPrice, DeliveryAddress, Status, CreateAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getId());
            stmt.setString(2, order.getUserId());
            stmt.setDate(3, order.getDeliveryTime());
            stmt.setBigDecimal(4, order.getTotalPrice());
            stmt.setString(5, order.getDeliveryAddress());
            stmt.setString(6, order.getStatus());
            stmt.setDate(7, order.getCreateAt());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        return new Order(
            rs.getString("Id"),
            rs.getString("UserId"),
            rs.getDate("DeliveryTime"),
            rs.getBigDecimal("TotalPrice"),
            rs.getString("DeliveryAddress"),
            rs.getString("Status"),
            rs.getDate("CreateAt")
        );
    }



	// UPDATE
	public boolean updateOrder(Order order) {
	    String sql = "UPDATE `orders` SET UserId=?, DeliveryTime=?, TotalPrice=?, DeliveryAddress=?, Status=?, CreateAt=? WHERE Id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, order.getUserId());
	        ps.setDate(2, order.getDeliveryTime());
	        ps.setBigDecimal(3, order.getTotalPrice());
	        ps.setString(4, order.getDeliveryAddress());
	        ps.setString(5, order.getStatus());
	        ps.setDate(6, order.getCreateAt());
	        ps.setString(7, order.getId());
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	// DELETE
	public boolean deleteOrder(String id) {
	    String sql = "DELETE FROM `orders` WHERE Id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, id);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Tìm đơn hàng theo ID
	public Order getOrderById(String id) {
	    String sql = "SELECT * FROM `orders` WHERE Id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return mapResultSetToOrder(rs);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	// Lọc đơn hàng theo trạng thái (Status)
	public List<Order> getOrdersByStatus(String status) {
	    List<Order> list = new ArrayList<>();
	    String sql = "SELECT * FROM `orders` WHERE Status=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, status);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(mapResultSetToOrder(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}

}
