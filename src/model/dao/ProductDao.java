package model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bean.Product;

public class ProductDao {
	private Connection conn;
	public ProductDao() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Add useUnicode=true&characterEncoding=UTF-8 to support Vietnamese characters
		String url = "jdbc:mysql://localhost:3306/cnw?useUnicode=true&characterEncoding=UTF-8";
		conn = DriverManager.getConnection(url, "root", "");
	
	}
	private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
	    Product p = new Product();
	    p.setId(rs.getString("Id"));
	    p.setName(rs.getString("Name"));
	    p.setDescription(rs.getString("Description"));
	    p.setPrice(rs.getBigDecimal("Price")); 
	    p.setCategory(rs.getString("Category"));
	    p.setImageUrl(rs.getString("ImageUrl"));
	    p.setType(rs.getString("Type"));
	    p.setStock(rs.getInt("Stock"));
	    p.setAvailable(rs.getBoolean("IsAvailable"));
	    return p;
	}
	//Lấy toàn bộ sản phẩm
	public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("Id"));
                p.setName(rs.getString("Name"));
                p.setDescription(rs.getString("Description"));
                p.setPrice(rs.getBigDecimal("Price")); // BigDecimal
                p.setCategory(rs.getString("Category"));
                p.setImageUrl(rs.getString("ImageUrl"));
                p.setType(rs.getString("Type"));
                p.setStock(rs.getInt("Stock"));
                p.setAvailable(rs.getBoolean("IsAvailable"));
                products.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log hoặc dùng Logger tùy dự án
        }
        return products;
    }
	//thêm sản phẩm
	public boolean insertProduct(Product pr){
		String sql = "Insert into product(Id,Name,Description,Price,Category,ImageUrl,Type,Stock,IsAvailable) values (?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, pr.getId());
			ps.setString(2, pr.getName());
			ps.setString(3,pr.getDescription());
			ps.setBigDecimal(4,pr.getPrice());
			ps.setString(5,pr.getCategory());
			ps.setString(6,pr.getImageUrl());
			ps.setString(7,pr.getType());
			ps.setInt(8,pr.getStock());
			ps.setBoolean(9,pr.isAvailable());
			return ps.executeUpdate()>0;
		
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//sửa sản phẩm
    public boolean updateProduct(Product p) {
        String sql = "UPDATE Product SET Name=?, Description=?, Price=?, Category=?, ImageUrl=?, Type=?, Stock=?, IsAvailable=? WHERE Id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setBigDecimal(3, p.getPrice());
            stmt.setString(4, p.getCategory());
            stmt.setString(5, p.getImageUrl());
            stmt.setString(6, p.getType());
            stmt.setInt(7, p.getStock());
            stmt.setBoolean(8, p.isAvailable());
            stmt.setString(9, p.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // xóa sản phẩm
    public boolean deleteProduct(String id) {
        String sql = "DELETE FROM Product WHERE Id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    lấy sản phẩm theo id
    public Product getProductById(String id) {
        String sql = "SELECT * FROM Product WHERE Id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getString("Id"));
                    p.setName(rs.getString("Name"));
                    p.setDescription(rs.getString("Description"));
                    p.setPrice(rs.getBigDecimal("Price"));
                    p.setCategory(rs.getString("Category"));
                    p.setImageUrl(rs.getString("ImageUrl"));
                    p.setType(rs.getString("Type"));
                    p.setStock(rs.getInt("Stock"));
                    p.setAvailable(rs.getBoolean("IsAvailable"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    Lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE Category = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getString("Id"));
                    p.setName(rs.getString("Name"));
                    p.setDescription(rs.getString("Description"));
                    p.setPrice(rs.getBigDecimal("Price"));
                    p.setCategory(rs.getString("Category"));
                    p.setImageUrl(rs.getString("ImageUrl"));
                    p.setType(rs.getString("Type"));
                    p.setStock(rs.getInt("Stock"));
                    p.setAvailable(rs.getBoolean("IsAvailable"));
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
// lấy sản phẩm theo loại ( thiệp hoặc hoa)
    public List<Product> getProductsByType(String type) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE Type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
//    lay sản phẩm được bán
    public List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE IsAvailable = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
//    lấy sản phẩm theo  giá
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE Price BETWEEN ? AND ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, min);
            stmt.setBigDecimal(2, max);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Cập nhật số lượng tồn kho của sản phẩm
     * @param productId ID của sản phẩm cần cập nhật
     * @param quantityChange Số lượng thay đổi (dương để tăng, âm để giảm)
     * @return true nếu cập nhật thành công, false nếu có lỗi hoặc không đủ hàng
     */
    public boolean updateStock(String productId, int quantityChange) {
        String sql = "UPDATE Product SET Stock = Stock + ? WHERE Id = ? AND (Stock + ?) >= 0";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantityChange);
            stmt.setString(2, productId);
            stmt.setInt(3, quantityChange);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
