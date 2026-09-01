package sample;

public class Row {
    private int num;
    private String Item;
    private double quantity;
    private double price;
    private double discount;
    private double discountPercent;
    private double sum;


    public Row(int num, String item, double quantity, double price, double discount, double discountPercent, double sum) {
        this.Item = item;
        this.quantity = quantity;
        this.price = price;
        this.num = num;
        this.discount = discount;
        this.discountPercent = discountPercent;
        this.sum = sum;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
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
