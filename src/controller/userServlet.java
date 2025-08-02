package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Cart;
import model.bean.Order;
import model.bean.Product;
import model.bean.User;
import model.bo.OrderBo;
import model.bo.ProductBo;
import model.bo.UserBo;

public class userServlet {

    // Thêm User
    private void addUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        int role = Integer.parseInt(request.getParameter("role"));
        String createAtstr = request.getParameter("createAt");  // "2025-08-02"
        java.sql.Date createAt = java.sql.Date.valueOf(createAtstr);
        // Các trường khác nếu có

        User user = new User(id,name,email,password,phone,address,role,createAt);
        UserBo userBo = new UserBo();
        boolean success = userBo.insertUser(user);

        if (success) {
            request.setAttribute("message", "Thêm người dùng thành công!");
        } else {
            request.setAttribute("error", "Thêm người dùng thất bại!");
        }
        request.getRequestDispatcher("admin_user.jsp").forward(request, response);
    }

    //Muốn xem các hoá đơn và tổng tiền
    @SuppressWarnings("unused")
    private void manageRevenue(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException{
        OrderBo orderBo = new OrderBo();
        List<Order> orders = orderBo.getAllOrders();
        request.setAttribute("orders", orders); 
        request.getRequestDispatcher("admin_revenue.jsp").forward(request, response);
    }
    
    //Trở về trang chủ
    @SuppressWarnings("unused")
    private void backToHome(HttpServletRequest request, HttpServletResponse response)
    throws  IOException, ServletException{
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    // Xoá User
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String userId = request.getParameter("userId");
        UserBo userBo = new UserBo();
        boolean success = userBo.deleteUser(userId);

        if (success) {
            request.setAttribute("message", "Xóa người dùng thành công!");
        } else {
            request.setAttribute("error", "Xóa người dùng thất bại!");
        }
        request.getRequestDispatcher("admin_user.jsp").forward(request, response);
    }

        // Thêm sản phẩm
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String imageUrl = request.getParameter("imageUrl");
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));
        // Thêm các trường khác nếu có

        Product product = new Product(id, name, description, price, category, imageUrl,type, stock);
        ProductBo productBo = new ProductBo();
        boolean success = productBo.insertProduct(product);

        if (success) {
            request.setAttribute("message", "Thêm sản phẩm thành công!");
        } else {
            request.setAttribute("error", "Thêm sản phẩm thất bại!");
        }
        request.getRequestDispatcher("admin_product.jsp").forward(request, response);
    }

    // Sửa sản phẩm
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String imageUrl = request.getParameter("imageUrl");
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));
        // Thêm các trường khác nếu có

        Product product = new Product(id, name, description, price, category, imageUrl,type, stock);
        ProductBo productBo = new ProductBo();
        boolean success = productBo.insertProduct(product);

        if (success) {
            request.setAttribute("message", "Cập nhật sản phẩm thành công!");
        } else {
            request.setAttribute("error", "Cập nhật sản phẩm thất bại!");
        }
        request.getRequestDispatcher("admin_product.jsp").forward(request, response);
    }

    // Xoá sản phẩm
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String id = request.getParameter("id");
        ProductBo productBo = new ProductBo();
        boolean success = productBo.deleteProduct(id);

        if (success) {
            request.setAttribute("message", "Xóa sản phẩm thành công!");
        } else {
            request.setAttribute("error", "Xóa sản phẩm thất bại!");
        }
        request.getRequestDispatcher("admin_product.jsp").forward(request, response);
    }
    
    // Sửa User
    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        int role = Integer.parseInt(request.getParameter("role"));
        String createAtstr = request.getParameter("createAt");  // "2025-08-02"
        java.sql.Date createAt = java.sql.Date.valueOf(createAtstr);
        // Các trường khác nếu có

        User user = new User(id,name,email,password,phone,address,role,createAt);
        UserBo userBo = new UserBo();
        boolean success = userBo.insertUser(user);

        if (success) {
            request.setAttribute("message", "Cập nhật người dùng thành công!");
        } else {
            request.setAttribute("error", "Cập nhật người dùng thất bại!");
        }
        request.getRequestDispatcher("admin_user.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserBo userBo = new UserBo();
        boolean check = userBo.isLoginValid(email, password); // checkLogin trả về User nếu đúng, null nếu sai

        if (check) {
        User user = userBo.getUserById(email); // Lấy thông tin user theo email
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if (user.getRole() == 1) { // Admin
            response.sendRedirect("admin_dashboard.jsp");
        } else { // Khách hàng
            response.sendRedirect("homepage.jsp");
        }
    } else {
        request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    }

    // Xem danh sách sản phẩm
    private void viewProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductBo productBo = new ProductBo();
        List<Product> productList = productBo.getAllProducts();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("product_list.jsp").forward(request, response);
    }

    // Chọn số lượng và thêm vào giỏ hàng
    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ProductBo productBo = new ProductBo();
        Product product = productBo.getProductById(productId);

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (product != null && quantity > 0) {
            cart.addItem(product, quantity);
            request.setAttribute("message", "Đã thêm vào giỏ hàng!");
        } else {
            request.setAttribute("error", "Sản phẩm không hợp lệ hoặc số lượng không hợp lệ!");
        }
        response.sendRedirect("userServlet?action=viewCart");
    }
        // Loại bỏ sản phẩm khỏi giỏ hàng
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.removeItem(productId);
            request.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
        }
        response.sendRedirect("userServlet?action=viewCart");
    }

    // Huỷ toàn bộ đơn hàng (xoá giỏ hàng)
    private void cancelOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        request.setAttribute("message", "Đã huỷ đơn hàng!");
        response.sendRedirect("userServlet?action=viewCart");
    }

    // Đồng ý đặt hàng (chốt đơn)
    private void confirmOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null && !cart.getItems().isEmpty()) {
            // Xử lý lưu đơn hàng vào database ở đây (tuỳ vào logic của bạn)
            // Sau khi lưu thành công:
            session.removeAttribute("cart");
            request.setAttribute("message", "Đặt hàng thành công!");
            response.sendRedirect("order_success.jsp");
        } else {
            request.setAttribute("error", "Giỏ hàng trống, không thể đặt hàng!");
            response.sendRedirect("userServlet?action=viewCart");
        }
    }
}