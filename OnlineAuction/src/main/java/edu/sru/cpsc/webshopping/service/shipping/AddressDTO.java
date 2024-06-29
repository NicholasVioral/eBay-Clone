package edu.sru.cpsc.webshopping.service.shipping;

public class AddressDTO {
    private String city;
    private String state;
    private String zip;

    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return this.city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return this.state;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getZip() {
        return this.zip;
    }
}
