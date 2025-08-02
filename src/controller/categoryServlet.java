package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.bo.CategoryBo;

public class categoryServlet {

    // Xem danh sách category
    private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryBo categoryBo = new CategoryBo();
        List<Category> categories = categoryBo.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }

    // Thêm category
    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Category category = new Category(id, name, description);
        CategoryBo categoryBo = new CategoryBo();
        boolean success = categoryBo.insertCategory(category);

        if (success) {
            request.setAttribute("message", "Thêm danh mục thành công!");
        } else {
            request.setAttribute("error", "Thêm danh mục thất bại!");
        }
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }

    // Sửa category
    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Category category = new Category(id, name, description);
        CategoryBo categoryBo = new CategoryBo();
        boolean success = categoryBo.updateCategory(category);

        if (success) {
            request.setAttribute("message", "Cập nhật danh mục thành công!");
        } else {
            request.setAttribute("error", "Cập nhật danh mục thất bại!");
        }
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }

    // Xoá category
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        CategoryBo categoryBo = new CategoryBo();
        boolean success = categoryBo.deleteCategory(id);

        if (success) {
            request.setAttribute("message", "Xóa danh mục thành công!");
        } else {
            request.setAttribute("error", "Xóa danh mục thất bại!");
        }
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }
    private void getAllCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryBo categoryBo = new CategoryBo();
        List<Category> categoryList = categoryBo.getAllCategories();
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("checkout.jsp");
    } 
    private void getCategoryByID(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        String id = request.getParameter("id");
    CategoryBo categoryBo = new CategoryBo();
    Category category = categoryBo.getCategoryById(id);

    if (category != null) {
        request.setAttribute("category", category);
        request.getRequestDispatcher("category_detail.jsp").forward(request, response);
    } else {
        request.setAttribute("error", "Không tìm thấy danh mục!");
        request.getRequestDispatcher("category_list.jsp").forward(request, response);
    }

    }
}