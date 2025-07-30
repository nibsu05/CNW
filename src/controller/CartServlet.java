package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Cart;
import model.bean.Product;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        if (action != null) {
            switch (action) {
                case "add":
                    addToCart(request, response, cart);
                    break;
                case "remove":
                    removeFromCart(request, response, cart);
                    break;
                case "update":
                    updateCart(request, response, cart);
                    break;
                case "clear":
                    clearCart(request, response, cart);
                    break;
                case "view":
                    viewCart(request, response, cart);
                    break;
                default:
                    response.sendRedirect("homepage.jsp");
                    break;
            }
        } else {
            response.sendRedirect("homepage.jsp");
        }
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response, Cart cart) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Tạo sản phẩm mẫu (trong thực tế sẽ lấy từ database)
            Product product = createSampleProduct(productId);
            
            if (product != null) {
                cart.addItem(product, quantity);
                request.setAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");
            } else {
                request.setAttribute("error", "Không tìm thấy sản phẩm!");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ!");
        }
        
        // Redirect về trang trước đó
        String referer = request.getHeader("Referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("homepage.jsp");
        }
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response, Cart cart) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cart.removeItem(productId);
            request.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ!");
        }
        
        response.sendRedirect("cart.jsp");
    }
    
    private void updateCart(HttpServletRequest request, HttpServletResponse response, Cart cart) 
            throws ServletException, IOException {
        
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            cart.updateQuantity(productId, quantity);
            request.setAttribute("message", "Đã cập nhật giỏ hàng!");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ!");
        }
        
        response.sendRedirect("cart.jsp");
    }
    
    private void clearCart(HttpServletRequest request, HttpServletResponse response, Cart cart) 
            throws ServletException, IOException {
        
        cart.clear();
        request.setAttribute("message", "Đã xóa toàn bộ giỏ hàng!");
        response.sendRedirect("cart.jsp");
    }
    
    private void viewCart(HttpServletRequest request, HttpServletResponse response, Cart cart) 
            throws ServletException, IOException {
        
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
    
    // Tạo sản phẩm mẫu (trong thực tế sẽ lấy từ database)
    private Product createSampleProduct(int productId) {
        switch (productId) {
            case 1:
                return new Product(1, "Bó Hoa Hồng Kem Dâu Sinh Nhật Vui Tươi", "Bó Hoa Hồng Kem Dâu", 380000, "Sinh nhật", "", "flower");
            case 2:
                return new Product(2, "Bó Hoa Hồng Tặng Sinh Nhật Con Gái Dễ Thương", "Bó Hoa Hồng", 400000, "Sinh nhật", "", "flower");
            case 3:
                return new Product(3, "Bó Hoa Hướng Dương Tốt Nghiệp Vươn Cao", "Bó Hoa Hướng Dương", 400000, "Tốt nghiệp", "", "flower");
            case 4:
                return new Product(4, "Peachy Blush", "Peachy Blush", 420000, "Sinh nhật", "", "flower");
            case 5:
                return new Product(5, "Monster Friends", "Thiệp quái vật", 0, "Động vật", "", "card");
            case 6:
                return new Product(6, "YOU'RE MY LOBSTER", "Thiệp tình yêu", 0, "Sinh nhật", "", "card");
            default:
                return null;
        }
    }
} 