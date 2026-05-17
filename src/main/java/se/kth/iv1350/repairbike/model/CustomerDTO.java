package se.kth.iv1350.repairbike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Data Transfer Object (DTO) containing a read-only snapshot of a customer.
 */
public class CustomerDTO {
    private final String customerId;
    private final String name;
    private final String phoneNumber;
    private final List<BikeDTO> bikes;

    /**
     * Creates a new DTO containing a snapshot of the provided customer.
     *
     * @param customer The customer to extract data from.
     */
    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
        
        List<BikeDTO> bikeDTOs = new ArrayList<>();
        for (Bike bike : customer.getBikes()) {
            bikeDTOs.add(new BikeDTO(bike));
        }
        this.bikes = bikeDTOs;
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
     * Gets the customer's registered bikes as DTOs.
     *
     * @return An unmodifiable list of bike DTOs.
     */
    public List<BikeDTO> getBikes() {
        return Collections.unmodifiableList(bikes);
    }

    @Override
    public String toString() {
        return "CustomerDTO{id='" + customerId + "', name='" + name + "', phone='" + phoneNumber + "', bikes=" + bikes + "}";
    }
}
