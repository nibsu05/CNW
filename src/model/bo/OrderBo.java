package model.bo;

import java.sql.SQLException;
import java.util.List;

import model.bean.Order;
import model.dao.OrderDao;

public class OrderBo {
    private OrderDao orderDAO;

    public OrderBo() {
        try {
			orderDAO = new OrderDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public boolean insertOrder(Order order) {
        return orderDAO.insertOrder(order);
    }

    public boolean updateOrder(Order order) {
        return orderDAO.updateOrder(order);
    }

    public boolean deleteOrder(String id) {
        return orderDAO.deleteOrder(id);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public Order getOrderById(String id) {
        return orderDAO.getOrderById(id);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderDAO.getOrdersByStatus(status);
    }
}
