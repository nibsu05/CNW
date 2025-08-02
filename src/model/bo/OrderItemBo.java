package model.bo;

import java.sql.SQLException;
import java.util.List;

import model.bean.OrderItem;
import model.dao.OrderItemDao;

public class OrderItemBo {
    private OrderItemDao dao;

    public OrderItemBo() throws SQLException {
        dao = new OrderItemDao();
    }

    public List<OrderItem> getAllOrderItems() {
        return dao.getAll();
    }

    public OrderItem getOrderItemById(String id) {
        return dao.getById(id);
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return dao.getByOrderId(orderId);
    }

    public boolean addOrderItem(OrderItem item) {
        return dao.insert(item);
    }

    public boolean updateOrderItem(OrderItem item) {
        return dao.update(item);
    }

    public boolean deleteOrderItem(String id) {
        return dao.delete(id);
    }
}
