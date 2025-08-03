package model.bean;

import java.sql.Date;
import java.math.BigDecimal;

public class Payment
{
    private String Id;
    private String orderId;
    private BigDecimal amount;
    private String method;
    private String status;
    private Date paidAt;
    
    public Payment()
    {
    }
    
    public Payment(String Id, String orderId, BigDecimal amount, String method, String status, Date paidAt)
    {
        this.Id = Id;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.paidAt = paidAt;
    }
    public String getId()
    {
        return this.Id;
    }
    public String getOrderId()
    {
        return this.orderId;
    }
    public BigDecimal getAmount()
    {
        return this.amount;
    }
    public String getMethod()
    {
        return this.method;
    }
    public String getStatus()
    {
        return this.status;
    }
    public Date getPaidAt()
    {
        return this.paidAt;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    public void setMethod(String method)
    {
        this.method = method;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setPaidAt(Date paidAt)
    {
        this.paidAt = paidAt;
    }
}