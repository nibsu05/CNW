package model.bean;

public class Category
{
    private String Id;
    private String name;
    private String description;
    public Category()
    {

    }
    public Category(String Id, String name, String description)
    {
        this.Id = Id;
        this.name = name;
        this.description = description;
    }
    public String getId()
    {
        return this.Id;
    }
    public String getName()
    {
        return this.name;
    }
    public String getDescription()
    {
        return this.description;
    }
    public void setId(String Id)
    {
        this.Id = Id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

}