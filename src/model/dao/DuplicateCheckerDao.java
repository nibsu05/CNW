package model.dao;

import java.sql.*;

public class DuplicateCheckerDao {
    private Connection conn;

    public DuplicateCheckerDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cnw", "root", "");
    }

    /**
     * Kiểm tra xem ID đã tồn tại trong bảng chưa
     * @param tableName tên bảng cần kiểm tra (ví dụ: "user", "orders")
     * @param columnId tên cột ID (thường là "Id")
     * @param id giá trị ID cần kiểm tra
     * @return true nếu đã tồn tại, false nếu chưa
     */
    public boolean isIdDuplicate(String tableName, String columnId, String id) {
        String sql = "SELECT 1 FROM `" + tableName + "` WHERE `" + columnId + "` = ? LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // nếu có kết quả tức là đã trùng
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
