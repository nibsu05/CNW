package model.bo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import model.bean.CartItem;
import model.bean.Order;
import model.bean.Payment;
import model.bean.Product;
import model.dao.OrderDao;
import model.dao.PaymentDao;
import model.dao.ProductDao;

public class OrderBo {
    private OrderDao orderDAO;

    public OrderBo() {
        try {
			orderDAO = new OrderDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public boolean insertOrder(Order order) {
        return orderDAO.insertOrder(order);
    }

    public boolean updateOrder(Order order) {
        return orderDAO.updateOrder(order);
    }

    public boolean deleteOrder(String id) {
        return orderDAO.deleteOrder(id);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public Order getOrderById(String id) {
        return orderDAO.getOrderById(id);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderDAO.getOrdersByStatus(status);
    }
    
    /**
     * Thêm các sản phẩm vào bảng order_items
     * @param orderId ID của đơn hàng
     * @param items Danh sách các mục trong giỏ hàng
     * @return true nếu thêm thành công, false nếu có lỗi
     */
    public boolean addOrderItems(String orderId, List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        
        try {
            // Thực hiện trong transaction để đảm bảo tính toàn vẹn dữ liệu
            return orderDAO.addOrderItems(orderId, items);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật số lượng tồn kho sau khi đặt hàng
     * @param items Danh sách các mục trong giỏ hàng
     * @return true nếu cập nhật thành công, false nếu có lỗi
     */
    public boolean updateProductStock(List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        
        try {
            ProductDao productDao = new ProductDao();
            for (CartItem item : items) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();
                if (!productDao.updateStock(product.getId(), -quantity)) {
                    return false; // Rollback nếu có lỗi
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Xử lý thanh toán đơn hàng
     * @param paymentId ID thanh toán
     * @param orderId ID đơn hàng
     * @param amount Số tiền thanh toán
     * @param method Phương thức thanh toán
     * @param status Trạng thái thanh toán
     * @return true nếu tạo thanh toán thành công, false nếu có lỗi
     */
    public boolean processPayment(String paymentId, String orderId, 
                                String method, String status) {
        try {
            Payment payment = new Payment(
                paymentId,
                orderId,
                method,
                status,
                new java.sql.Date(System.currentTimeMillis())
            );
            
            PaymentDao paymentDao = new PaymentDao();
            return paymentDao.insert(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
