package se.kth.iv1350.repairbike.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for RepairOrder.
 */
class RepairOrderTest {
    private RepairOrder order;

    /**
     * Sets up one order instance before each test.
     */
    @BeforeEach
    void setUp() {
        Bike bike = new Bike("Test Bike", "SN-123");
        Customer customer = new Customer("C-1", "Test Person", "0700000000", Collections.singletonList(bike));
        order = new RepairOrder("1", customer, bike, "Will not start");
    }

    /**
     * Verifies diagnostic registration updates state.
     */
    @Test
    void testAddDiagnosticStoresReportAndSetsInProgress() {
        order.addDiagnostic(new DiagnosticReport("Dead battery"));

        assertNotNull(order.getDiagnosticReport(), "Diagnostic report was missing after registration.");
        assertEquals("Dead battery", order.getDiagnosticReport().getFindings(), "Diagnostic text did not match the input.");
        assertEquals(OrderState.IN_PROGRESS, order.getState(), "Order status was incorrect after adding a diagnostic.");
    }

    /**
     * Verifies adding tasks updates total cost.
     */
    @Test
    void testAddTaskAccumulatesTotalCost() {
        order.addTask(new RepairTask("Task A", 1000));
        order.addTask(new RepairTask("Task B", 500));

        assertEquals(1500.0, order.getTotalCost(), "Total cost calculation was incorrect.");
        assertEquals(2, order.getRepairTasks().size(), "Not all repair tasks were stored.");
    }

    /**
     * Verifies accept and reject operations set expected status.
     */
    @Test
    void testAcceptSetFinalStatus() {
        order.accept();
        assertEquals(OrderState.ACCEPTED, order.getState(), "Order state was not updated to ACCEPTED.");
    }

    /**
     * Verifies explicit state setter changes order state.
     */
    @Test
    void testSetStateUpdatesState() {
        order.setState(OrderState.IN_PROGRESS);
        assertEquals(OrderState.IN_PROGRESS, order.getState(), "Explicit state setter failed to update the state.");
    }

    /**
     * Verifies toString contains key order information.
     */
    @Test
    void testToStringContainsOrderIdAndProblemDescription() {
        String asText = order.toString();

        assertTrue(asText.contains("orderID=1"), "Text representation is missing the order id.");
        assertTrue(asText.contains("Will not start"), "Text representation is missing the problem description.");
    }
}
