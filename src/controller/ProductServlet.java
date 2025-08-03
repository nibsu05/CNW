package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.bean.Product;
import model.bo.CategoryBo;
import model.bo.DuplicateCheckerBo;
import model.bo.ProductBo;
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	

    private ProductBo productBo;
    private CategoryBo categoryBo;
    private DuplicateCheckerBo dBo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            productBo = new ProductBo();
            categoryBo = new CategoryBo();
            dBo = new  DuplicateCheckerBo();
        } catch (SQLException e) {
            throw new ServletException("Không thể khởi tạo ProductBo", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");

    	response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "view":
                viewProduct(request, response);
                break;
            case "byCategory":
                listProductsByCategory(request, response);
                break;
            case "available":
                listAvailableProducts(request, response);
                break;
            case "byPrice":
                listProductsByPriceRange(request, response);
                break;
            default:
                listAllProducts(request, response);
        }
        doPost(request, response); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");

    	response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
        	case "list":
        		listAllProducts(request,response);
        		break;
        		
            case "add":
                addProduct(request, response);
                break;
            case "update":
                updateProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                response.sendRedirect("productServlet");
        }
    }

    // Hiển thị tất cả sản phẩm
    private void listAllProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productBo.getAllProducts();
        request.setAttribute("products", products);	
        
    	List<Category> categories = categoryBo.getAllCategories(); 
    	request.setAttribute("categories", categories);
        request.getRequestDispatcher("admin_manage.jsp").forward(request, response);
    }

    // Hiển thị sản phẩm theo danh mục
    private void listProductsByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        List<Product> products = productBo.getProductsByCategory(category);
        request.setAttribute("products", products);
        request.getRequestDispatcher("product_list.jsp").forward(request, response);
    }

    // Hiển thị sản phẩm khả dụng
    private void listAvailableProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productBo.getAvailableProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("product_list.jsp").forward(request, response);
    }

    // Hiển thị sản phẩm theo khoảng giá
    private void listProductsByPriceRange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BigDecimal min = new BigDecimal(request.getParameter("min"));
        BigDecimal max = new BigDecimal(request.getParameter("max"));
        List<Product> products = productBo.getProductsByPriceRange(min, max);
        request.setAttribute("products", products);
        request.getRequestDispatcher("product_list.jsp").forward(request, response);
    }

    // Xem chi tiết sản phẩm
    private void viewProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Product product = productBo.getProductById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher("product_detail.jsp").forward(request, response);
    }

    // Thêm sản phẩm
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");

    	response.setCharacterEncoding("UTF-8");
    	
    	String prefix = "SP"; 
    	String id = prefix + System.currentTimeMillis();

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");
        String imageUrl = request.getParameter("imageUrl");
        String type = request.getParameter("type");
        int stock = Integer.parseInt(request.getParameter("stock"));
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));
        Product product = new Product(id, name, description,price,category, imageUrl,type,stock);
        if(dBo.isIdDuplicate("Product", "Id", id)) {
        	request.setAttribute("Error", "Id bị trùng");
        	request.setAttribute("openModal", true);
            request.getRequestDispatcher("admin_manage.jsp").forward(request, response);
            return;
        }
        if(product!=null){
            productBo.insertProduct(product);
            request.getRequestDispatcher("ProductServlet?action=list").forward(request, response);
            return;
        }
        else{
            request.setAttribute("Error", "Sai thông tin, nhập lại");
            request.setAttribute("openModal", true);
            request.getRequestDispatcher("admin_manage.jsp").forward(request, response);
        }
    }

    // Sửa sản phẩm
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");

		response.setCharacterEncoding("UTF-8");
    	
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");
        String imageUrl = request.getParameter("imageUrl");
        String type = request.getParameter("type");
        int stock = Integer.parseInt(request.getParameter("stock"));
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("isAvailable"));
        Product product = new Product(id, name, description,price,category, imageUrl,type,stock);

        if(product!=null){
            productBo.updateProduct(product);

        }
        else{
            request.setAttribute("Error", "Sai thông tin, nhập lại");
            request.setAttribute("openModal", true);
        }
        request.getRequestDispatcher("ProductServlet?action=list").forward(request, response);
    }

    // Xóa sản phẩm
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println("ID truyền vào xóa: [" + id + "]");
        boolean success = productBo.deleteProduct(id);
        // Có thể set message nếu muốn
        request.getRequestDispatcher("ProductServlet?action=list").forward(request, response);
    }
}