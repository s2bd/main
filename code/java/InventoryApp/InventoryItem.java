public class InventoryItem {
    private String name;
    private String category;
    private int quantity;
    private double price;

    public InventoryItem(String name, String category, int quantity, double price) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Category: %s, Quantity: %d, Price: %.2f", 
                              name, category, quantity, price);
    }

    public String toCSV() {
        return name + "," + category + "," + quantity + "," + price;
    }
}
