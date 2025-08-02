package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.FinancialReportBo;

public class FinancialReportServlet {

    // 1. Tổng doanh thu theo ngày
    private void showDailyRevenue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FinancialReportBo reportBo = new FinancialReportBo();
            Map<Date, BigDecimal> dailyRevenue = reportBo.getDailyRevenue();
            request.setAttribute("dailyRevenue", dailyRevenue);
            request.getRequestDispatcher("report_daily.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể lấy dữ liệu doanh thu theo ngày!");
            request.getRequestDispatcher("report_error.jsp").forward(request, response);
        }
    }

    // 2. Tổng doanh thu theo tháng
    private void showMonthlyRevenue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FinancialReportBo reportBo = new FinancialReportBo();
            Map<String, BigDecimal> monthlyRevenue = reportBo.getMonthlyRevenue();
            request.setAttribute("monthlyRevenue", monthlyRevenue);
            request.getRequestDispatcher("report_monthly.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể lấy dữ liệu doanh thu theo tháng!");
            request.getRequestDispatcher("report_error.jsp").forward(request, response);
        }
    }

    // 3. Doanh thu theo sản phẩm
    private void showRevenueByProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FinancialReportBo reportBo = new FinancialReportBo();
            Map<String, BigDecimal> revenueByProduct = reportBo.getRevenueByProduct();
            request.setAttribute("revenueByProduct", revenueByProduct);
            request.getRequestDispatcher("report_product.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể lấy dữ liệu doanh thu theo sản phẩm!");
            request.getRequestDispatcher("report_error.jsp").forward(request, response);
        }
    }

    // 4. Khách hàng chi tiêu nhiều nhất
    private void showTopCustomers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FinancialReportBo reportBo = new FinancialReportBo();
            Map<String, BigDecimal> topCustomers = reportBo.getTopCustomers();
            request.setAttribute("topCustomers", topCustomers);
            request.getRequestDispatcher("report_top_customers.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể lấy dữ liệu khách hàng chi tiêu nhiều nhất!");
            request.getRequestDispatcher("report_error.jsp").forward(request, response);
        }
    }

    // 5. Tổng tiền đã thanh toán
    private void showTotalPaid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FinancialReportBo reportBo = new FinancialReportBo();
            BigDecimal totalPaid = reportBo.getTotalPaid();
            request.setAttribute("totalPaid", totalPaid);
            request.getRequestDispatcher("report_total_paid.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể lấy dữ liệu tổng tiền đã thanh toán!");
            request.getRequestDispatcher("report_error.jsp").forward(request, response);
        }
    }
}