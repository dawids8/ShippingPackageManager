package delivery;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Package {

    private final StringProperty shippingNumber;
    private final StringProperty deliveryCompany;
    private final StringProperty address;
    private final StringProperty customer;
    private final StringProperty content;
    private final DoubleProperty weight;
    private final DoubleProperty price;
    private final LocalDate shippingDate;
    private final BooleanProperty isDelivered;

    public Package(String shippingNumber, String deliveryCompany, String address, String customer, String content, double weight, double price, LocalDate shippingDate, boolean isDelivered) {
        this.shippingNumber = new SimpleStringProperty(shippingNumber);
        this.deliveryCompany = new SimpleStringProperty(deliveryCompany);
        this.address = new SimpleStringProperty(address);
        this.customer = new SimpleStringProperty(customer);
        this.content = new SimpleStringProperty(content);
        this.weight = new SimpleDoubleProperty(weight);
        this.price = new SimpleDoubleProperty(price);
        this.shippingDate = shippingDate;
        this.isDelivered = new SimpleBooleanProperty(isDelivered);
    }

    public String getDeliveryCompany() {
        return deliveryCompany.get();
    }

    public String getShippingNumber() {
        return shippingNumber.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getCustomer() {
        return customer.get();
    }

    public String getContent() {
        return content.get();
    }

    public double getWeight() {
        return weight.get();
    }

    public double getPrice() {
        return price.get();
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public boolean isIsDelivered() {
        return isDelivered.get();
    }
}
