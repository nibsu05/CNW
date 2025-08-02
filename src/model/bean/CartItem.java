package model.bean;

import java.math.BigDecimal;

public class CartItem {
    private Product product;
    private int quantity;
    
    public CartItem() {
    }
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters và Setters
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // Tính tổng giá của item này
    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
    
    // Tăng số lượng
    public void increaseQuantity() {
        this.quantity++;
    }
    
    // Giảm số lượng
    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }
    
    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
} 