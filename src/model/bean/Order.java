package model.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class Order
{
    private String Id;
    private String userId;
    private Date deliveryTime;
    private BigDecimal totalPrice;
    private String deliveryAddress;
    private String status;
    private Date createAt;
    public Order()
    {

    }
    public Order(String Id, String userId, Date deliveryTime, BigDecimal totalPrice, String deliveryAddress, String status, Date createAt)
    {
        this.Id = Id;
        this.userId = userId;
        this.deliveryTime = deliveryTime;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.createAt = createAt;
    }
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}