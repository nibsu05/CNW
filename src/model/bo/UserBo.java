package model.bo;

import java.sql.SQLException;
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

    public User getUserByEmail(String email){
        return dao.getUserByEmail(email);
    }

    public boolean insertUser(User user) {
        try {
            // Ensure all string fields are properly encoded
            if (user.getName() != null) {
                user.setName(new String(user.getName().getBytes("UTF-8"), "UTF-8"));
            }
            if (user.getAddress() != null) {
                user.setAddress(new String(user.getAddress().getBytes("UTF-8"), "UTF-8"));
            }
            return dao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User u) {
        return dao.updateUser(u);
    }

    public boolean deleteUser(String id) {
        return dao.deleteUser(id);
    }
    
        public boolean isIdDuplicate(String id) {
        return dao.isIdExists(id);
    }

    // Kiểm tra Email đã tồn tại chưa
    public boolean isEmailDuplicate(String email) {
        return dao.isEmailExists(email);
    }

    public boolean isLoginValid(String email, String password) {
        // TODO Auto-generated method stub
        return dao.isLoginValid(email, password);
    }
}
