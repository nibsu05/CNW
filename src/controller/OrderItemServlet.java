package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.OrderItem;
import model.bo.OrderItemBo;

public class OrderItemServlet extends HttpServlet {

    private OrderItemBo orderItemBo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            orderItemBo = new OrderItemBo();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "view":
                viewOrderItem(request, response);
                break;
            case "byOrder":
                listOrderItemsByOrderId(request, response);
                break;
            default:
                listAllOrderItems(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                addOrderItem(request, response);
                break;
            case "update":
                updateOrderItem(request, response);
                break;
            case "delete":
                deleteOrderItem(request, response);
                break;
            default:
                response.sendRedirect("orderItemServlet");
        }
    }

    // Hiển thị tất cả order item
    private void listAllOrderItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OrderItem> items = orderItemBo.getAllOrderItems();
        request.setAttribute("orderItems", items);
        request.getRequestDispatcher("orderitem_list.jsp").forward(request, response);
    }

    // Hiển thị order item theo orderId
    private void listOrderItemsByOrderId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        List<OrderItem> items = orderItemBo.getOrderItemsByOrderId(orderId);
        request.setAttribute("orderItems", items);
        request.getRequestDispatcher("orderitem_list.jsp").forward(request, response);
    }

    // Xem chi tiết order item
    private void viewOrderItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        OrderItem item = orderItemBo.getOrderItemById(id);
        request.setAttribute("orderItem", item);
        request.getRequestDispatcher("orderitem_detail.jsp").forward(request, response);
    }

    // Thêm order item
    private void addOrderItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
            String id = request.getParameter("id");
            String orderId = request.getParameter("orderId");
            String productId = request.getParameter("productId");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            java.math.BigDecimal price = new java.math.BigDecimal(request.getParameter("price"));
            String note = request.getParameter("note");

            OrderItem item = new OrderItem(id, orderId, productId, quantity, price,note);
            boolean success = orderItemBo.addOrderItem(item);

            if (success) {
                request.setAttribute("message", "Thêm OrderItem thành công!");
            } else {
                request.setAttribute("error", "Thêm OrderItem thất bại!");
            }
            response.sendRedirect("orderItemServlet");
    }

    // Cập nhật order item
    private void updateOrderItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String id = request.getParameter("id");
            String orderId = request.getParameter("orderId");
            String productId = request.getParameter("productId");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            java.math.BigDecimal price = new java.math.BigDecimal(request.getParameter("price"));
            String note = request.getParameter("note");

            OrderItem item = new OrderItem(id, orderId, productId, quantity, price,note);
            boolean success = orderItemBo.addOrderItem(item);

            if (success) {
                request.setAttribute("message", "Thêm OrderItem thành công!");
            } else {
                request.setAttribute("error", "Thêm OrderItem thất bại!");
            }
            response.sendRedirect("orderItemServlet");
    }

    // Xóa order item
    private void deleteOrderItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        boolean success = orderItemBo.deleteOrderItem(id);
        // Có thể set message nếu muốn
        response.sendRedirect("orderItemServlet");
    }
}