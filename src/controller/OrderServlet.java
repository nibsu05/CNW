package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Cart;
import model.bean.CartItem;
import model.bean.Order;
import model.bean.User;
import model.bo.OrderBo;
import model.dao.OrderDao;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private OrderBo orderBo;

    @Override
    public void init() throws ServletException {
        super.init();
        orderBo = new OrderBo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "view":
                viewOrder(request, response);
                break;
            case "status":
                listOrdersByStatus(request, response);
                break;
            default:
                listAllOrders(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                addOrder(request, response);
                break;
            case "update":
                updateOrder(request, response);
                break;
            case "delete":
                deleteOrder(request, response);
                break;
            default:
                response.sendRedirect("OrderServlet");
        }
    }

    // Hiển thị tất cả đơn hàng
    private void listAllOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Order> orders = orderBo.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    // Hiển thị đơn hàng theo trạng thái
    private void listOrdersByStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Order> orders = orderBo.getOrdersByStatus(status);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    // Xem chi tiết đơn hàng
    private void viewOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Order order = orderBo.getOrderById(id);
        request.setAttribute("order", order);
        request.getRequestDispatcher("order_detail.jsp").forward(request, response);
    }

    // Thêm đơn hàng
    private void addOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        boolean transactionSuccess = false;
        
        try {
            // Lấy thông tin từ session
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            
            if (cart == null || cart.getItems().isEmpty()) {
                request.setAttribute("error", "Giỏ hàng trống!");
                response.sendRedirect("cart.jsp");
                return;
            }
            
            // Lấy thông tin từ form
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String deliveryAddress = request.getParameter("deliveryAddress");
            String paymentMethod = request.getParameter("paymentMethod");
            
            // Chuyển đổi kiểu dữ liệu
            java.sql.Date deliveryTime = java.sql.Date.valueOf(
                request.getParameter("deliveryTime").split("T")[0]);
            java.math.BigDecimal totalPrice = new java.math.BigDecimal(request.getParameter("totalPrice"));
            java.sql.Date createAt = new java.sql.Date(System.currentTimeMillis());
            
            // Tạo đối tượng Order
            String orderId = "ORD" + System.currentTimeMillis();
            String userId = (String) session.getAttribute("userId");
            if (userId == null) userId = "GUEST";
            
            Order order = new Order(
                orderId, 
                userId, 
                deliveryTime, 
                totalPrice, 
                deliveryAddress, 
                "PENDING", 
                createAt
            );
            
            // Bắt đầu transaction
            connection = orderBo.getConnection();
            connection.setAutoCommit(false);
            
            // 1. Thêm đơn hàng vào database
            boolean orderSuccess = orderBo.insertOrder(order);
            
            if (!orderSuccess) {
                throw new Exception("Lỗi khi thêm đơn hàng vào database");
            }
            
            // 2. Thêm các sản phẩm vào order_items
            boolean itemsSuccess = orderBo.addOrderItems(orderId, cart.getItems());
            
            if (!itemsSuccess) {
                throw new Exception("Lỗi khi thêm sản phẩm vào đơn hàng");
            }
            
            // 3. Cập nhật số lượng tồn kho
            boolean stockUpdated = orderBo.updateProductStock(cart.getItems());
            
            if (!stockUpdated) {
                throw new Exception("Lỗi khi cập nhật tồn kho");
            }
            
            // 4. Tạo thanh toán
            String paymentId = "PAY" + System.currentTimeMillis();
            boolean paymentSuccess = orderBo.processPayment(
                paymentId,
                orderId,
                totalPrice,
                paymentMethod,
                "PENDING"
            );
            
            if (!paymentSuccess) {
                throw new Exception("Lỗi khi tạo thanh toán");
            }
            
            // Nếu mọi thứ thành công, commit transaction
            connection.commit();
            transactionSuccess = true;
            
            // Xóa giỏ hàng sau khi đặt hàng thành công
            session.removeAttribute("cart");
            
            // Chuyển hướng đến trang cảm ơn
            response.sendRedirect("order_success.jsp?orderId=" + orderId);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Rollback transaction nếu có lỗi
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            // Ghi log lỗi
            System.err.println("Lỗi trong quá trình đặt hàng: " + e.getMessage());
            
            // Giữ lại thông tin form để người dùng không phải nhập lại
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
            
        } finally {
            // Đặt lại auto-commit và đóng kết nối
            if (connection != null) {
                try {
                    if (!transactionSuccess) {
                        connection.rollback();
                    }
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Cập nhật đơn hàng
    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String id = request.getParameter("id");
        String userId = request.getParameter("userId");
        java.sql.Date deliveryTime = java.sql.Date.valueOf(request.getParameter("deliveryTime"));
        java.math.BigDecimal totalPrice = new java.math.BigDecimal(request.getParameter("totalPrice"));
        String deliveryAddress = request.getParameter("deliveryAddress");
        String status = request.getParameter("status");
        java.sql.Date createAt = java.sql.Date.valueOf(request.getParameter("createAt"));

        Order order = new Order(id, userId, deliveryTime, totalPrice, deliveryAddress, status, createAt);
        boolean success = orderBo.insertOrder(order);

        if (success) {
            request.setAttribute("message", "Thêm đơn hàng thành công!");
        } else {
            request.setAttribute("error", "Thêm đơn hàng thất bại!");
        }
        response.sendRedirect("OrderServlet");

    }

    // Xóa đơn hàng
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        boolean success = orderBo.deleteOrder(id);
        // Có thể set message nếu muốn
        response.sendRedirect("OrderServlet");
    }
}