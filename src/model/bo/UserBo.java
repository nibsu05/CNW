package model.bo;

import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.List;

import model.bean.User;
import model.dao.UserDao;

public class UserBo {
    private UserDao dao;
    public UserBo() {
    	try {
			dao = new UserDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public User getUserById(String id) {
        return dao.getUserById(id);
    }

    public boolean insertUser(User u) {
        return dao.insertUser(u);
    }

    public boolean updateUser(User u) {
        return dao.updateUser(u);
    }

    public boolean deleteUser(String id) {
        return dao.deleteUser(id);
    }
}
