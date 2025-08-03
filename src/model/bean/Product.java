package model.bean;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private String type; // "flower" hoặc "card"
    private int stock;
    private boolean isAvailable = stock > 0;
    
    // Constructor mặc định
    public Product() {
    }
    
    // Constructor với tham số
    public Product(String id, String name, String description, BigDecimal price, String category, String imageUrl, String type,int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.type = type;
        this.stock = stock;
        this.isAvailable = stock > 0;
    }
    
    public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
		this.isAvailable = stock > 0;
	}

	// Getters và Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    // Method để format giá tiền theo định dạng Việt Nam
    public String getFormattedPrice() {
        return String.format("%,.0f₫", price);
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", stock='" + type + '\'' +
                '}';
    }
}