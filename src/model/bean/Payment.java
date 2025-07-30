package model.bean;

public class Payment
{
    private String Id;
    private String orderId;
    private String method;
    private String status;
    private String paidAt;
    public Payment()
    {

    }
    public Payment(String Id, String orderId, String method, String status, String paidAt)
    {
        this.Id = Id;
        this.orderId = orderId;
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
    public String getMethod()
    {
        return this.method;
    }
    public String getStatus()
    {
        return this.status;
    }
    public String getPaidAt()
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
    public void setPaidAt(String paidAt)
    {
        this.paidAt = paidAt;
    }
}