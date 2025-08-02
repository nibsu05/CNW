package model.bo;

import model.dao.FinancialReportDao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class FinancialReportBo {
    private FinancialReportDao reportDao;

    public FinancialReportBo() throws SQLException {
        reportDao = new FinancialReportDao();
    }

    // 1. Tổng doanh thu theo ngày
    public Map<Date, BigDecimal> getDailyRevenue() {
        return reportDao.getDailyRevenue();
    }

    // 2. Tổng doanh thu theo tháng
    public Map<String, BigDecimal> getMonthlyRevenue() {
        return reportDao.getMonthlyRevenue();
    }

    // 3. Doanh thu theo sản phẩm
    public Map<String, BigDecimal> getRevenueByProduct() {
        return reportDao.getRevenueByProduct();
    }

    // 4. Khách hàng chi tiêu nhiều nhất
    public Map<String, BigDecimal> getTopCustomers() {
        return reportDao.getTopCustomers();
    }

    // 5. Tổng tiền đã thanh toán
    public BigDecimal getTotalPaid() {
        return reportDao.getTotalPaid();
    }
}
