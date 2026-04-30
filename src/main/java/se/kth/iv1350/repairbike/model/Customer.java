package se.kth.iv1350.repairbike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer who can submit repair orders.
 */
public class Customer {
    private String customerId;
    private String name;
    private String phoneNumber;
    private List<Bike> bikes;

    /**
     * Creates a new customer instance.
     *
     * @param customerId  The internal customer id.
     * @param name        The customer name.
     * @param phoneNumber The customer phone number.
     * @param bikes       The customer's registered bikes.
     */
    public Customer(String customerId, String name, String phoneNumber, List<Bike> bikes) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bikes = new ArrayList<>(bikes);
    }

    /**
     * Gets the customer id.
     *
     * @return The id.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Gets the customer name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the customer phone number.
     *
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the customer's registered bikes.
     *
     * @return An unmodifiable list of bikes.
     */
    public List<Bike> getBikes() {
        return Collections.unmodifiableList(bikes);
    }

    @Override
    public String toString() {
        return "Customer{id='" + customerId + "', name='" + name + "', phone='" + phoneNumber + "', bikes=" + bikes
                + "}";
    }
}
