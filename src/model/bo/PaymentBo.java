package model.bo;

import java.sql.SQLException;
import java.util.List;
import model.bean.Payment;
import model.dao.PaymentDao;

public class PaymentBo {
    private PaymentDao dao;

    public PaymentBo() throws SQLException {
        dao = new PaymentDao();
    }

    public List<Payment> getAll() {
        return dao.getAll();
    }

    public Payment getById(String id) {
        return dao.getById(id);
    }

    public boolean add(Payment p) {
        return dao.insert(p);
    }

    public boolean update(Payment p) {
        return dao.update(p);
    }

    public boolean delete(String id) {
        return dao.delete(id);
    }
}
