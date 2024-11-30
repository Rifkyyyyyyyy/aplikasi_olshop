package domain.model.product;


public class ProductModel {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private int userId;

    // Constructor to initialize the ProductModel object
    public ProductModel(int id, String name, String description, double price, int stock, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.userId = userId;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getUserId() {
        return userId;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProductModel{id=" + id + ", name='" + name + "', description='" + description + "', price=" + price
                + ", stock=" + stock + ", userId=" + userId + "}";
    }
}
