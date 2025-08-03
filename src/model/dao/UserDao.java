package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.User;

public class UserDao {
    private Connection conn;

    public UserDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Add useUnicode=true&characterEncoding=UTF-8 to support Vietnamese characters
        String url = "jdbc:mysql://localhost:3306/cnw?useUnicode=true&characterEncoding=UTF-8";
        conn = DriverManager.getConnection(url, "root", "");
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("Id"),
            rs.getString("Name"),
            rs.getString("Email"),
            rs.getString("Password"),
            rs.getString("Phone"),
            rs.getString("Address"),
            rs.getByte("Role"),
            rs.getDate("CreateAt")
        );
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM `user`";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(String id) {
        String sql = "SELECT * FROM `user` WHERE Id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM `user` WHERE Email=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO `user` (Id, Name, Email, Password, Phone, Address, Role, CreateAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getRole());
            ps.setDate(8, user.getCreateAt());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE `user` SET Name=?, Email=?, Password=?, Phone=?, Address=?, Role=?, CreateAt=? WHERE Id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getRole());
            ps.setDate(7, user.getCreateAt());
            ps.setString(8, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String id) {
        String sql = "DELETE FROM `user` WHERE Id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     // Kiểm tra trùng ID
    public boolean isIdExists(String id) {
        String sql = "SELECT 1 FROM user WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra trùng Email
    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM user WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLoginValid(String email, String password) {
    String sql = "SELECT 1 FROM user WHERE Email = ? AND Password = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Trả về true nếu có kết quả
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    
}
