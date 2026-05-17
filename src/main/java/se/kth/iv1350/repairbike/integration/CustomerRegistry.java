package se.kth.iv1350.repairbike.integration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;

/**
 * Simulates customer lookup in an external data source.
 */
public class CustomerRegistry {
    private Map<String, Customer> customersByPhone = new HashMap<>();

    /**
     * Creates a registry preloaded with sample customers.
     */
    public CustomerRegistry() {
        customersByPhone.put("0701234567", new Customer("C-100", "Alice Andersson", "0701234567",
                Arrays.asList(
                        new Bike("VoltBike S2", "VB-S2-0001"),
                        new Bike("CityCruiser", "CC-9999"))));
        customersByPhone.put("0709876543", new Customer("C-200", "Bob Berg", "0709876543",
                Collections.singletonList(new Bike("MountainKing", "MK-1010"))));
    }

    /**
     * Finds a customer by phone number.
     *
     * @param phoneNumber The phone number to search for.
     * @return The matching customer, or null if no customer was found.
     * @throws CustomerNotFoundException if the customer does not exist.
     * @throws DatabaseFailureException if a database crash is simulated.
     */
    public Customer findCustomer(String phoneNumber) throws CustomerNotFoundException {
   
        if (phoneNumber.equals("0700000000")) {
            throw new DatabaseFailureException("Lost connection to the customer database server.");
        }

        Customer customer = customersByPhone.get(phoneNumber);

        if (customer == null) {
            throw new CustomerNotFoundException(phoneNumber);
        }
        return customer;
    }
}
