package model.bo;

import java.sql.SQLException;

import model.dao.DuplicateCheckerDao;

public class DuplicateCheckerBo {
    private DuplicateCheckerDao checkerDao;

    public DuplicateCheckerBo() throws SQLException {
        checkerDao = new DuplicateCheckerDao();
    }

    /**
     * Kiểm tra trùng ID cho bảng bất kỳ.
     * 
     * @param tableName Tên bảng
     * @param columnId  Tên cột ID (thường là "Id")
     * @param id        ID cần kiểm tra
     * @return true nếu đã tồn tại, false nếu chưa
     */
    public boolean isIdDuplicate(String tableName, String columnId, String id) {
        return checkerDao.isIdDuplicate(tableName, columnId, id);
    }

    /**
     * Riêng bảng user kiểm tra thêm email.
     * 
     * @param email Email cần kiểm tra
     * @return true nếu email đã tồn tại, false nếu chưa
     */
    public boolean isUserEmailDuplicate(String email) {
        return checkerDao.isIdDuplicate("user", "Email", email);
    }
}
