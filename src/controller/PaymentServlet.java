package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Payment;
import model.bo.PaymentBo;

public class PaymentServlet extends HttpServlet {

    private PaymentBo paymentBo;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            paymentBo = new PaymentBo();
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
                viewPayment(request, response);
                break;
            default:
                listAllPayments(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                addPayment(request, response);
                break;
            case "update":
                updatePayment(request, response);
                break;
            case "delete":
                deletePayment(request, response);
                break;
            default:
                response.sendRedirect("paymentServlet");
        }
    }

    // Hiển thị tất cả payment
    private void listAllPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Payment> payments = paymentBo.getAll();
        request.setAttribute("payments", payments);
        request.getRequestDispatcher("payment_list.jsp").forward(request, response);
    }

    // Xem chi tiết payment
    private void viewPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Payment payment = paymentBo.getById(id);
        request.setAttribute("payment", payment);
        request.getRequestDispatcher("payment_detail.jsp").forward(request, response);
    }

    // Thêm payment
    private void addPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String orderId = request.getParameter("orderId");
        String method = request.getParameter("method");
        String status = request.getParameter("status");
        String paidAtstr = request.getParameter("paidAt");
        Date paidAt = Date.valueOf(paidAtstr);
        Payment payment = new Payment(id, orderId, method, status, paidAt);
        if(payment!=null){
            request.setAttribute("payment", payment);
        request.getRequestDispatcher("payment_detail.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("Lỗi, k thể thêm/ sửa được");
        }
    }

    // Cập nhật payment
    private void updatePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String orderId = request.getParameter("orderId");
        String method = request.getParameter("method");
        String status = request.getParameter("status");
        String paidAtstr = request.getParameter("paidAt");
        Date paidAt = Date.valueOf(paidAtstr);
        Payment payment = new Payment(id, orderId, method, status, paidAt);
        if(payment!=null){
            request.setAttribute("payment", payment);
        request.getRequestDispatcher("payment_detail.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("Lỗi, k thể thêm/ sửa được");
        }
    }

    // Xóa payment
    private void deletePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        boolean success = paymentBo.delete(id);
        // Có thể set message nếu muốn
        response.sendRedirect("paymentServlet");
    }
}