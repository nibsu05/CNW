package model.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    // Thêm sản phẩm vào giỏ hàng
    public void addItem(Product product, int quantity) {
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Nếu chưa có, thêm mới
        items.add(new CartItem(product, quantity));
    }
    
    // Thêm sản phẩm vào giỏ hàng (mặc định số lượng = 1)
    public void addItem(Product product) {
        addItem(product, 1);
    }
    
    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }
    
    // Cập nhật số lượng sản phẩm
    public void updateQuantity(String productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                if (quantity <= 0) {
                    removeItem(productId);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }
    
    // Tăng số lượng sản phẩm
    public void increaseQuantity(String productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                item.increaseQuantity();
                return;
            }
        }
    }
    
    // Giảm số lượng sản phẩm
    public void decreaseQuantity(String productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                item.decreaseQuantity();
                if (item.getQuantity() == 0) {
                    removeItem(productId);
                }
                return;
            }
        }
    }
    
    // Lấy tổng số lượng sản phẩm trong giỏ hàng
    public int getTotalItems() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }
    
    // Lấy tổng giá trị giỏ hàng
    public BigDecimal getTotalPrice() {
        return items.stream()
                    .map(CartItem::getTotalPrice) // trả về BigDecimal
                    .reduce(BigDecimal.ZERO, BigDecimal::add); // cộng dồn
    }
    
    // Lấy danh sách items
    public List<CartItem> getItems() {
        return items;
    }
    
    // Kiểm tra giỏ hàng có rỗng không
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    // Xóa toàn bộ giỏ hàng
    public void clear() {
        items.clear();
    }
    
    // Lấy số lượng của một sản phẩm cụ thể
    public int getQuantity(String productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                return item.getQuantity();
            }
        }
        return 0;
    }
    
    // Kiểm tra sản phẩm có trong giỏ hàng không
    public boolean containsProduct(String productId) {
        return items.stream().anyMatch(item -> item.getProduct().getId() == productId);
    }
    
    // Format tổng giá tiền theo định dạng Việt Nam
    public String getFormattedTotalPrice() {
        return String.format("%,.0f₫", getTotalPrice());
    }
    
    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", totalItems=" + getTotalItems() +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
} 