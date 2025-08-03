package model.bo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import model.bean.Product;
import model.dao.ProductDao;

public class ProductBo {
    private ProductDao productDao;

    public ProductBo() {
        try {
			productDao = new ProductDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
// lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
//lấy sản phẩm theo id
    public Product getProductById(String id) {
        return productDao.getProductById(id);
    }
// lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(String category, String id) {
        return productDao.getProductsByCategory(category, id);
    }
// lấy sản phẩm khả dụng 
    public List<Product> getAvailableProducts() {
        return productDao.getAvailableProducts();
    }
// lấy sản phẩm theo giá
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productDao.getProductsByPriceRange(min, max);
    }
//thêm sản phẩm
    public boolean insertProduct(Product product) {
        return productDao.insertProduct(product);
    }
//sửa sản phẩm
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }
// xóa sản phẩm
    public boolean deleteProduct(String id) {
        return productDao.deleteProduct(id);
    }
//Phân loại sản phẩm theo loại
    public List<Product> getProductsByTypeSplit(String type){
        return productDao.getProductsByTypeSplit(type);
    }
}
