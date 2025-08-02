import model.bo.*;
import model.bean.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        // USER
    	Date today = new Date(System.currentTimeMillis());
        UserBo userBO = new UserBo();
        userBO.getUserById("U001");
        System.out.println("All users: " + userBO.getAllUsers());

        // PRODUCT
        ProductBo productBO = new ProductBo();
//        Product product = new Product("P003", "Hoa hồng trắng", "C003", new BigDecimal("150000"),"C003",  "hoa.jpg","hoa",5);
//        productBO.insertProduct(product);
        System.out.println("All products: " + productBO.getAllProducts());

        // CATEGORY
        CategoryBo categoryBO = new CategoryBo();
//        Category category = new Category("C003", "Hoa cưới", "Các loại hoa cưới");
//        categoryBO.insertCategory(category);
        System.out.println("All categories: " + categoryBO.getAllCategories());

        // ORDER
        OrderBo orderBO = new OrderBo();
//        Order order = new Order("O003", "U003",today, new BigDecimal("150000"), "789 Trần Phú, Đà Nẵng", "pending",  today);
//        orderBO.insertOrder(order);
        System.out.println("All orders: " + orderBO.getAllOrders());

        // ORDER ITEM
        OrderItemBo itemBO = null;
		try {
			itemBO = new OrderItemBo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        OrderItem item = new OrderItem("I003", "O003", "P003", 1, new BigDecimal("150000"), "Giao buổi sáng");
//        itemBO.addOrderItem(item);
        System.out.println("All order items: " + itemBO.getAllOrderItems());

        // PAYMENT
        PaymentBo paymentBO = null;
		try {
			paymentBO = new PaymentBo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        Payment payment = new Payment("PM003", "O003", "momo", "paid", today);
//        paymentBO.add(payment);
        System.out.println("All payments: " + paymentBO.getAll());
        try {
            FinancialReportBo bo = new FinancialReportBo();

            System.out.println("=== Doanh thu theo ngày ===");
            for (Map.Entry<Date, BigDecimal> entry : bo.getDailyRevenue().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\n=== Doanh thu theo tháng ===");
            for (Map.Entry<String, BigDecimal> entry : bo.getMonthlyRevenue().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\n=== Doanh thu theo sản phẩm ===");
            for (Map.Entry<String, BigDecimal> entry : bo.getRevenueByProduct().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\n=== Top khách hàng ===");
            for (Map.Entry<String, BigDecimal> entry : bo.getTopCustomers().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\n=== Tổng tiền đã thanh toán ===");
            System.out.println(bo.getTotalPaid());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
