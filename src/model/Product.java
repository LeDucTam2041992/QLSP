package model;

public abstract class Product {
    protected String productCode;
    protected String name;
    protected String designBy;
    protected int price;

    public Product(String productCode, String name, String designBy, int price) {
        this.productCode = productCode;
        this.name = name;
        this.designBy = designBy;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignBy() {
        return designBy;
    }

    public void setDesignBy(String designBy) {
        this.designBy = designBy;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
