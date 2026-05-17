package se.kth.iv1350.repairbike.integration;

/**
 * Thrown when a customer cannot be found in the registry.
 */
public class CustomerNotFoundException extends Exception {
    private String phoneNumber;

    /**
     * Creates a new instance representing the missing customer.
     *
     * @param phoneNumber The phone number that could not be found.
     */
    public CustomerNotFoundException(String phoneNumber) {
        super("Customer with phone number " + phoneNumber + " could not be found.");
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return The phone number that caused the exception.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}