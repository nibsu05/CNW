package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.bean.Order;
import model.bean.CartItem;
import java.math.BigDecimal;
import java.util.List;

public class OrderDao {
    private Connection conn;

    public OrderDao() throws SQLException  {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Add useUnicode=true&characterEncoding=UTF-8 to support Vietnamese characters
		String url = "jdbc:mysql://localhost:3306/cnw?useUnicode=true&characterEncoding=UTF-8";
		conn = DriverManager.getConnection(url, "root", "");
	
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

    /**
     * Thêm các sản phẩm vào bảng order_items
     * @param orderId ID của đơn hàng
     * @param items Danh sách các mục trong giỏ hàng
     * @return true nếu thêm thành công, false nếu có lỗi
     */
    public boolean addOrderItems(String orderId, List<CartItem> items) throws SQLException {
        if (items == null || items.isEmpty()) {
            return false;
        }
        
        String sql = "INSERT INTO orderitem (Id, OrderId, ProductId, Quantity, Price, Note) VALUES (?, ?, ?, ?, ?, '')";
        
        try {
            // Tắt auto-commit để thực hiện transaction
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                int itemNumber = 1;
                for (CartItem item : items) {
                    // Generate a unique ID for each order item: OI + last 10 digits of timestamp + item number
                    // Total length: 2 (OI) + 10 (timestamp) + 1 (_) + 1 (item number) = 14 characters
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    String itemId = "OI" + timestamp.substring(timestamp.length() - 10) + "_" + itemNumber++;
                    String productId = item.getProduct().getId();
                    
                    // Debug log
                    System.out.println("Attempting to add order item with product ID: " + productId);
                    
                    stmt.setString(1, itemId);
                    stmt.setString(2, orderId);
                    stmt.setString(3, productId);
                    stmt.setInt(4, item.getQuantity());
                    stmt.setBigDecimal(5, item.getProduct().getPrice());
                    stmt.addBatch();
                }
                
                int[] results = stmt.executeBatch();
                
                // Kiểm tra tất cả các dòng đều được thêm thành công
                for (int result : results) {
                    if (result <= 0) {
                        conn.rollback();
                        return false;
                    }
                }
                
                conn.commit();
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
