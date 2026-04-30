package se.kth.iv1350.repairbike.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.RepairOrder;

/**
 * Unit tests for RepairOrderRegistry.
 */
class RepairOrderRegistryTest {
    private RepairOrderRegistry registry;
    private Customer customer;
    private Bike bike;

    /**
     * Sets up test fixtures.
     */
    @BeforeEach
    void setUp() {
        registry = new RepairOrderRegistry();
        bike = new Bike("Test Bike", "SN-123");
        customer = new Customer("C-1", "Test Person", "0700000000", Collections.singletonList(bike));
    }

    /**
     * Verifies creating an order stores it in registry.
     */
    @Test
    void testCreateOrderStoresAndReturnsOrder() {
        RepairOrder created = registry.createRepairOrder("Broken motor", customer, bike);
        RepairOrder fetched = registry.findOrder(created.getOrderId());

        assertNotNull(created, "Created order was null.");
        assertNotNull(fetched, "Stored order could not be found by ID.");
        assertEquals(created.getOrderId(), fetched.getOrderId(), "Fetched order ID did not match the created order ID.");
        assertEquals("Broken motor", fetched.getProblemDescription(), "Stored problem description did not match the input.");
    }

    /**
     * Verifies searching for unknown order returns null.
     */
    @Test
    void testFindUnknownOrderReturnsNull() {
        RepairOrder missing = registry.findOrder("9999");
        assertNull(missing, "Unknown order ID failed to return null.");
    }
}
