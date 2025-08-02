package model.bo;

import java.sql.SQLException;
import java.util.List;
import model.bean.Category;
import model.dao.CategoryDao;

public class CategoryBo {
    private CategoryDao dao;
    public CategoryBo(){
    	 try {
			dao = new CategoryDao();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
    }
    public List<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    public Category getCategoryById(String id) {
        return dao.getCategoryById(id);
    }

    public boolean insertCategory(Category category) {
        return dao.insertCategory(category);
    }

    public boolean updateCategory(Category category) {
        return dao.updateCategory(category);
    }

    public boolean deleteCategory(String id) {
        return dao.deleteCategory(id);
    }

}
