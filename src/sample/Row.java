package sample;

public class Row {
    private String Item;
    private double quantity;
    private double price;


    public Row(String item, double quantity, double price) {
        Item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
