package model.bean;

import java.math.BigDecimal;

public class OrderItem
{
    private String Id;
    private String orderId;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String note;
    public OrderItem()
    {

    }
    public OrderItem(String Id, String orderId, String productId, int quantity, BigDecimal price, String note)
    {
        this.Id = Id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
    }
    public String getId()
    {
        return this.Id;
    }
    public String getOrderId()
    {
        return this.orderId;
    }
    public String getproductId()
    {
        return this.productId;
    }
    public int getQuantity()
    {
        return this.quantity;
    }
    public BigDecimal getPrice()
    {
        return this.price;
    }
    public String getNote()
    {
        return this.note;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
    public void setNote(String note)
    {
        this.note = note;
    }
}