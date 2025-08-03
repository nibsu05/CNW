package controller;

import java.io.IOException;
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
import model.bo.UserBo;

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
        request.getRequestDispatcher("order_list.jsp").forward(request, response);
    }

    // Hiển thị đơn hàng theo trạng thái
    private void listOrdersByStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        List<Order> orders = orderBo.getOrdersByStatus(status);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("order_list.jsp").forward(request, response);
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
        System.out.println("Starting addOrder process...");
        try {
            // Lấy thông tin từ session
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            System.out.println("Cart from session: " + (cart != null ? "exists with " + cart.getItems().size() + " items" : "null"));
            
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
            
            // Debug log form parameters
            System.out.println("Form parameters:");
            System.out.println("- fullName: " + fullName);
            System.out.println("- phone: " + phone);
            System.out.println("- email: " + email);
            System.out.println("- deliveryAddress: " + deliveryAddress);
            System.out.println("- paymentMethod: " + paymentMethod);
            
            // Chuyển đổi kiểu dữ liệu
            String deliveryTimeStr = request.getParameter("deliveryTime");
            System.out.println("Raw deliveryTime: " + deliveryTimeStr);
            java.sql.Date deliveryTime = null;
            try {
                deliveryTime = java.sql.Date.valueOf(deliveryTimeStr.split("T")[0]);
                System.out.println("Parsed deliveryTime: " + deliveryTime);
            } catch (Exception e) {
                System.err.println("Error parsing deliveryTime: " + e.getMessage());
                throw e;
            }
            
            String totalPriceStr = request.getParameter("totalPrice");
            System.out.println("Raw totalPrice: " + totalPriceStr);
            java.math.BigDecimal totalPrice = null;
            try {
                totalPrice = new java.math.BigDecimal(totalPriceStr);
                System.out.println("Parsed totalPrice: " + totalPrice);
            } catch (Exception e) {
                System.err.println("Error parsing totalPrice: " + e.getMessage());
                throw e;
            }
            
            java.sql.Date createAt = new java.sql.Date(System.currentTimeMillis());
            System.out.println("Created at: " + createAt);
            
            // Tạo đối tượng Order
            // Generate a shorter ID using current time in seconds instead of milliseconds
            String orderId = "ORD" + (System.currentTimeMillis() / 1000);
            String userId = (String) session.getAttribute("userId");
            
            // Nếu người dùng chưa đăng nhập, tạo tài khoản khách
            if (userId == null) {
                try {
                    // Tạo ID ngẫu nhiên cho khách
                    String guestId = "GUEST" + System.currentTimeMillis();
                    // Tạo email tạm thời cho khách
                    String guestEmail = "guest_" + System.currentTimeMillis() + "@example.com";
                    
                    // Tạo user mới với vai trò là khách (role = 0 cho khách)
                    UserBo userBo = new UserBo();
                    User guestUser = new User(
                        guestId,           // id
                        fullName,          // name
                        guestEmail,        // email
                        "",                // password rỗng
                        phone,             // phone
                        deliveryAddress,   // address
                        0,                 // role (0 = guest)
                        new java.sql.Date(System.currentTimeMillis()) // createAt
                    );
                    
                    // Lưu thông tin khách vào database
                    if (userBo.insertUser(guestUser)) {
                        userId = guestId;
                        session.setAttribute("userId", userId);
                        session.setAttribute("userEmail", guestEmail);
                        session.setAttribute("userRole", "GUEST");
                        System.out.println("Created guest user with ID: " + userId);
                    } else {
                        throw new Exception("Không thể tạo tài khoản khách");
                    }
                } catch (Exception e) {
                    System.err.println("Error creating guest user: " + e.getMessage());
                    throw new Exception("Lỗi khi tạo tài khoản khách: " + e.getMessage());
                }
            }
            
            System.out.println("Creating order with ID: " + orderId);
            System.out.println("User ID: " + userId);
            
            Order order = new Order(
                orderId, 
                userId, 
                deliveryTime, 
                totalPrice, 
                deliveryAddress, 
                "PENDING", 
                createAt
            );
            
            System.out.println("Order object created, attempting to insert into database...");
            
            // Thêm đơn hàng vào database
            boolean orderSuccess = orderBo.insertOrder(order);
            System.out.println("Order insert result: " + orderSuccess);
            
            if (orderSuccess) {
                System.out.println("Order inserted successfully, adding order items...");
                // Thêm các sản phẩm vào order_items
                boolean itemsSuccess = orderBo.addOrderItems(orderId, cart.getItems());
                System.out.println("Add order items result: " + itemsSuccess);
                
                if (itemsSuccess) {
                    System.out.println("Updating product stock...");
                    // Cập nhật số lượng tồn kho
                    boolean stockUpdated = orderBo.updateProductStock(cart.getItems());
                    System.out.println("Update stock result: " + stockUpdated);
                    
                    if (stockUpdated) {
                        System.out.println("Processing payment...");
                        // Tạo thanh toán - Sử dụng phương thức hiện có trong OrderBo
                        // Lấy 12 chữ số cuối của timestamp để đảm bảo ID không vượt quá 15 ký tự
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        String paymentId = "PAY" + (timestamp.length() > 12 ? timestamp.substring(timestamp.length() - 12) : timestamp);
                        System.out.println("Creating payment with ID: " + paymentId);
                        boolean paymentSuccess = orderBo.processPayment(
                            paymentId,
                            orderId,
                            paymentMethod,
                            "PAID"
                        );
                        System.out.println("Payment processing result: " + paymentSuccess);
                        
                        if (paymentSuccess) {
                            System.out.println("Payment successful, clearing cart and redirecting...");
                            // Xóa giỏ hàng sau khi đặt hàng thành công
                            session.removeAttribute("cart");
                            
                            // Chuyển hướng đến trang cảm ơn
                            response.sendRedirect("order_success.jsp?orderId=" + orderId);
                            return;
                        } else {
                            System.err.println("Payment processing failed");
                        }
                    }
                }
            }
            
            // Nếu có lỗi xảy ra
            String errorMsg = "Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại sau!";
            System.err.println(errorMsg);
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Exception in addOrder: ");
            e.printStackTrace();
            String errorMsg = "Lỗi hệ thống: " + e.getMessage();
            System.err.println(errorMsg);
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
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