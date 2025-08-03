package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Cart;
import model.bean.Product;
import model.bo.OrderBo;
import model.bo.ProductBo;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductBo productBo;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
			productBo = new ProductBo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
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

            String productId = request.getParameter("productId");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Đã lấy product từ db
            Product product = createSampleProduct(productId);
            String referer = request.getHeader("Referer");
            if (product != null) {
                cart.addItem(product, quantity);
                if (referer != null) {
                    response.sendRedirect("homepage.jsp?message=1");
                } else {
                    response.sendRedirect("homepage.jsp");
                }
            } 
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ!");
        }
        
        // Redirect về trang trước đó
        

    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response, Cart cart) 
            throws ServletException, IOException {
        
        try {
            String productId = request.getParameter("productId");
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
            String productId = request.getParameter("productId");
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
    private Product createSampleProduct(String productId) {

        return productBo.getProductById(productId);
    }
    
}