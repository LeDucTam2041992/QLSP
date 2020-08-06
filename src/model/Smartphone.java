package model;

public class Smartphone extends Product implements Comparable<Smartphone>{
    protected String color;
    protected int quantity;

    public Smartphone(String productCode, String name, String designBy, int price, String color, int quantity) {
        super(productCode, name, designBy, price);
        this.color = color;
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void showInfo() {
        System.out.printf("ProductCode : %-10s ---- %-40s ---- Color %-10s Design by %-10s ---- Price %-15d \n",
                productCode,name,color,designBy,price);
    }

    public void showInfoFull() {
        System.out.printf("ProductCode : %-10s ---- %-40s ---- Color %-10s Design by %-10s ---- Price %-15d ---- " +
                        "Quantity : %4d \n",
                productCode,name,color,designBy,price,quantity);
    }

    @Override
    public int compareTo(Smartphone o) {
        return price - o.getPrice();
    }
}
