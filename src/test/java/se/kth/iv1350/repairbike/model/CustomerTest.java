package se.kth.iv1350.repairbike.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Customer.
 */
class CustomerTest {
    private Customer customer;

    /**
     * Creates one customer fixture before each test.
     */
    @BeforeEach
    void setUp() {
        List<Bike> bikes = new ArrayList<>();
        bikes.add(new Bike("VoltBike S2", "VB-700"));
        customer = new Customer("C-77", "Eva Ek", "0701112233", bikes);
    }

    /**
     * Verifies constructor data can be retrieved through getters.
     */
    @Test
    void testConstructorStoresIdentityAndPhone() {
        assertEquals("C-77", customer.getCustomerId(), "Customer ID did not match the constructor argument.");
        assertEquals("Eva Ek", customer.getName(), "Customer name did not match the constructor argument.");
        assertEquals("0701112233", customer.getPhoneNumber(), "Customer phone number did not match the constructor argument.");
    }

    /**
     * Verifies bikes list cannot be modified from outside.
     */
    @Test
    void testGetBikesReturnsUnmodifiableList() {
        List<Bike> bikes = customer.getBikes();
        assertThrows(UnsupportedOperationException.class,
                () -> bikes.add(new Bike("Another", "SN-2")),
                "The returned bike list was not unmodifiable.");
    }

    /**
     * Verifies constructor makes a defensive copy of bikes list.
     */
    @Test
    void testConstructorMakesDefensiveCopyOfBikeList() {
        List<Bike> originalBikes = new ArrayList<>();
        originalBikes.add(new Bike("Model A", "A-1"));
        Customer customerWithSourceList = new Customer("C-88", "Nils Nilsson", "0700000001", originalBikes);

        originalBikes.add(new Bike("Model B", "B-2"));

        assertEquals(1, customerWithSourceList.getBikes().size(),
                "The defensive copy failed: the customer's bike list was affected by external modifications.");
    }

    /**
     * Verifies textual representation includes key customer info.
     */
    @Test
    void testToStringContainsIdAndName() {
        String asText = customer.toString();

        assertTrue(asText.contains("C-77"), "Text representation is missing the customer id.");
        assertTrue(asText.contains("Eva Ek"), "Text representation is missing the customer name.");
    }
}
