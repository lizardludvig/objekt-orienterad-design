package se.kth.iv1350.repairbike.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repairbike.model.Customer;

/**
 * Unit tests for CustomerRegistry.
 */
class CustomerRegistryTest {
    private CustomerRegistry registry;

    /**
     * Creates a new registry before each test.
     */
    @BeforeEach
    void setUp() {
        registry = new CustomerRegistry();
    }

    /**
     * Verifies known phone number returns a customer.
     */
    @Test
    void testFindCustomerReturnsKnownCustomer() {
        Customer customer = registry.findCustomer("0701234567");

        assertNotNull(customer, "Known phone number returned null instead of a customer.");
        assertEquals("C-100", customer.getCustomerId(), "Returned customer ID did not match the predefined customer.");
    }

    /**
     * Verifies unknown phone number returns null.
     */
    @Test
    void testFindCustomerReturnsNullForUnknownNumber() {
        Customer customer = registry.findCustomer("0999999999");
        assertNull(customer, "Unknown phone number failed to return null.");
    }
}
