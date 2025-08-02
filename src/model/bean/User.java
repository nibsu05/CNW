package model.bean;

import java.sql.Date;

public class User 
{
    private String Id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int role;
    private Date createAt;

    public User()
    {
        
    }
    public User(String Id, String name, String email, String password, String phone, String address, int role, Date createAt)
    {
        this.Id = Id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createAt = createAt;
    }
    public String getId()
    {
        return this.Id;
    }
    public String getName()
    {
        return this.name;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getPhone()
    {
        return this.phone;
    } 
    public String getAddress()
    {
        return this.address;
    }
    public int getRole()
    {
        return this.role;
    }
    public Date getCreateAt()
    {
        return this.createAt;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public void setRole(int role)
    {
        this.role = role;
    }
    public void setCreateAt(Date createAt)
    {
        this.createAt = createAt;
    }
}
