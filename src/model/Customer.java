package model;

public class Customer {
    private String name, address, phoneNumber, time, productCode;

    public Customer(String name, String address, String phoneNumber, String time, String productCode) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.time = time;
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void showInfo() {
        System.out.printf("Time : %25s ------ Customer : %15s ------ Phone Number : %15s ------ Address : %15s ------ Product : %8s" +
                "\n",time,name,phoneNumber,address,productCode);
    }
}
