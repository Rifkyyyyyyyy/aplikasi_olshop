package domain.model.product;


import java.util.Objects;

public class ProductModel {
    private final String id;
    private final String name;
    private final String description;
    private final double price;
    private final int stock;
    private final String userId;
    private final String sellerName;  // Added sellerName field

    // Constructor to initialize the ProductModel object
    public ProductModel(String id, String name, String description, double price, int stock, String userId, String sellerName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.userId = userId;
        this.sellerName = sellerName;  // Initialize sellerName
    }

    // Getter methods
    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final double getPrice() {
        return price;
    }

    public final int getStock() {
        return stock;
    }

    public final String getUserId() {
        return userId;
    }

    public final String getSellerName() {
        return sellerName;  // Getter for sellerName
    }

    @Override
    public final String toString() {
        return "ProductModel{id=" + id + ", name='" + name + "', description='" + description + "', price=" + price
                + ", stock=" + stock + ", userId=" + userId + ", sellerName='" + sellerName + "'}";  // Updated toString
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, name, description, price, stock, userId, sellerName);  // Updated hashCode
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;  // Same object reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;  // Different class or null object
        }
        ProductModel that = (ProductModel) obj;
        return Double.compare(that.price, price) == 0 &&
                stock == that.stock &&
                userId.equals(that.userId) &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(sellerName, that.sellerName);  // Updated equals
    }
}
