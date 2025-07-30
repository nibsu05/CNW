package model.bean;

public class Order
{
    private String Id;
    private String userId;
    private String deliveryTime;
    private long totalPrice;
    private String deliveryAddress;
    private String status;
    private String createAt;
    public Order()
    {

    }
    public Order(String Id, String userId, String deliveryTime, long totalPrice, String deliveryAddress, String status, String createAt)
    {
        this.Id = Id;
        this.userId = userId;
        this.deliveryTime = deliveryTime;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.createAt = createAt;
    }
    public String getId()
    {
        return this.Id;
    }
    public String getUserId()
    {
        return this.userId;
    }
    public String getDeliveryTime()
    {
        return this.deliveryTime;
    }
    public long getTotalPrice()
    {
        return this.totalPrice;
    }
    public String getDeliveryAddress()
    {
        return this.deliveryAddress;
    }
    public String getStatus()
    {
        return this.status;
    }
    public String getCreateAt()
    {
        return this.createAt;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public void setDeliveryTime(String deliveryTime)
    {
        this.deliveryTime = deliveryTime;
    }
    public void setTotalPrice(long totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    public void setDeliveryAddress(String deliveryAddress)
    {
        this.deliveryAddress = deliveryAddress;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setCreateAt(String createAt)
    {
        this.createAt = createAt;
    }
}