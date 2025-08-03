package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

import model.bean.Cart;
import model.bean.Order;
import model.bean.Product;
import model.bean.User;
import model.bo.DuplicateCheckerBo;
import model.bo.OrderBo;
import model.bo.ProductBo;
import model.bo.UserBo;

@WebServlet("/userServlet")
public class userServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            try {
                switch (action) {
                    case "login":
                        login(request, response);
                        break;
                    case "register":
                        register(request, response);
                        break;
                    case "addUser":
                        addUser(request, response);
                        break;
                    case "updateUser":
                        updateUser(request, response);
                        break;
                    case "deleteUser":
                        deleteUser(request, response);
                        break;
                    case "addProduct":
                        addProduct(request, response);
                        break;
                    case "updateProduct":
                        updateProduct(request, response);
                        break;
                    case "deleteProduct":
                        deleteProduct(request, response);
                        break;
                    case "viewProductList":
                        viewProductList(request, response);
                        break;
                    case "addToCart":
                        addToCart(request, response);
                        break;
                    case "removeFromCart":
                        removeFromCart(request, response);
                        break;
                    case "cancelOrder":
                        cancelOrder(request, response);
                        break;
                    case "confirmOrder":
                        confirmOrder(request, response);
                        break;
                    case "manageRevenue":
                        manageRevenue(request, response);
                        break;
                    case "backToHome":
                        backToHome(request, response);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                        break;
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No action specified");
        }
    }

    // Thêm User
    private void addUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, SQLException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        int role = Integer.parseInt(request.getParameter("role"));
        String createAtstr = request.getParameter("createAt");  // "2025-08-02"
        java.sql.Date createAt = java.sql.Date.valueOf(createAtstr);
        DuplicateCheckerBo DCheck = new DuplicateCheckerBo();
        if(DCheck.isIdDuplicate("user", "Id", id) && DCheck.isUserEmailDuplicate(email)){
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
        else{
            request.setAttribute("Error", "Thất bại khi thêm khách hàng, yêu cầu nhập lại!");
        }
    }

    //Muốn xem các hoá đơn và tổng tiền
    @SuppressWarnings("unused")
    protected void manageRevenue(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException{
        OrderBo orderBo = new OrderBo();
        List<Order> orders = orderBo.getAllOrders();
        request.setAttribute("orders", orders); 
        request.getRequestDispatcher("admin_revenue.jsp").forward(request, response);
    }
    
    //Trở về trang chủ
    @SuppressWarnings("unused")
    protected void backToHome(HttpServletRequest request, HttpServletResponse response)
    throws  IOException, ServletException{
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    // Xoá User
    protected void deleteUser(HttpServletRequest request, HttpServletResponse response) 
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
    protected void addProduct(HttpServletRequest request, HttpServletResponse response)
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
    protected void updateProduct(HttpServletRequest request, HttpServletResponse response)
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
        boolean success = productBo.updateProduct(product);

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
    protected void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, SQLException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        int role = Integer.parseInt(request.getParameter("role"));
        String createAtstr = request.getParameter("createAt");  // "2025-08-02"
        java.sql.Date createAt = java.sql.Date.valueOf(createAtstr);
        DuplicateCheckerBo DCheck = new DuplicateCheckerBo();
        if(DCheck.isIdDuplicate("user", "Id", id) && DCheck.isUserEmailDuplicate(email)){
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
        else{
            request.setAttribute("Error", "Bị trùng dữ liệu khi cập nhật khách hàng, yêu cầu nhập lại!");
        }
        // Các trường khác nếu có

        
    }

    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ email và mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        UserBo userBo = new UserBo();
        boolean isValid = userBo.isLoginValid(email, password);

        if (isValid) {
            User user = userBo.getUserByEmail(email);
            
            if (user == null) {
                request.setAttribute("error", "Không tìm thấy thông tin người dùng!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());

            if (user.getRole() == 1) { // Admin
                response.sendRedirect("homepage.jsp");
            } else { // Khách hàng
                response.sendRedirect("homepage.jsp");
            }
        } else {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    protected void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set character encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        
        try {
            // Check if email already exists
            UserBo userBo = new UserBo();
            if (userBo.isEmailDuplicate(email)) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Create new user
            User user = new User();
            // Generate a simple ID (you might want to use UUID or another method in production)
            user.setId("U" + System.currentTimeMillis());
            user.setName(firstName + " " + lastName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password); // Password should be hashed in the BO layer
            user.setRole(0); // Default role: regular user (0 = user, 1 = admin)
            user.setAddress(address != null ? address : ""); // Use provided address or empty string
            user.setCreateAt(new java.sql.Date(System.currentTimeMillis())); // Set current date
            
            // Insert user into database
            boolean isSuccess = userBo.insertUser(user);
            
            if (isSuccess) {
                // Registration successful, redirect to login page with success message
                request.setAttribute("success", "Đăng ký tài khoản thành công! Vui lòng đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Registration failed, show error
                request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại sau.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Xem danh sách sản phẩm
    protected void viewProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductBo productBo = new ProductBo();
        List<Product> productList = productBo.getAllProducts();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("product_list.jsp").forward(request, response);
    }

    // Chọn số lượng và thêm vào giỏ hàng
    protected void addToCart(HttpServletRequest request, HttpServletResponse response)
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
    protected void removeFromCart(HttpServletRequest request, HttpServletResponse response)
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
    protected void cancelOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        request.setAttribute("message", "Đã huỷ đơn hàng!");
        response.sendRedirect("userServlet?action=viewCart");
    }

    // Đồng ý đặt hàng (chốt đơn)
    protected void confirmOrder(HttpServletRequest request, HttpServletResponse response)
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