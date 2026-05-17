package se.kth.iv1350.repairbike.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repairbike.integration.CustomerRegistry;
import se.kth.iv1350.repairbike.integration.Printer;
import se.kth.iv1350.repairbike.integration.RepairOrderRegistry;
import se.kth.iv1350.repairbike.integration.CustomerNotFoundException;
import se.kth.iv1350.repairbike.model.BikeDTO;
import se.kth.iv1350.repairbike.model.CustomerDTO;
import se.kth.iv1350.repairbike.model.OrderState;
import se.kth.iv1350.repairbike.model.RepairOrderDTO;
import se.kth.iv1350.repairbike.model.RepairOrder;

/**
 * Unit tests for Controller.
 */
class ControllerTest {
    private Controller controller;
    private RepairOrderRegistry repairOrderRegistry;

    /**
     * Sets up one controller and dependencies before each test.
     */
    @BeforeEach
    void setUp() {
        CustomerRegistry customerRegistry = new CustomerRegistry();

        repairOrderRegistry = RepairOrderRegistry.getInstance();

        Printer printer = new Printer();

        controller = new Controller(customerRegistry, printer);
    }

    /**
     * Verifies known customer can be found by phone number.
     */
    @Test
    void testCheckCustomerReturnsKnownCustomer() throws CustomerNotFoundException {
        CustomerDTO customer = controller.checkCustomer("0701234567");
        assertNotNull(customer, "Known phone number returned null instead of a customer.");
        assertEquals("Alice Andersson", customer.getName(),
                "Returned customer did not match the expected predefined customer.");
    }

    /**
     * Verifies unknown customer phone throws exception. 
     */
    @Test
    void testCheckCustomerThrowsForUnknownPhone() {
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> controller.checkCustomer("0000000000"),
                "Expected checkCustomer to throw CustomerNotFoundException for a missing customer.");
        assertEquals("0000000000", exception.getPhoneNumber(), "Exception did not contain the expected phone number.");
    }

    /**
     * Verifies registering a problem creates and stores an order.
     */
    @Test
    void testRegisterProblemCreatesStoredOrder() throws CustomerNotFoundException {
        CustomerDTO customer = controller.checkCustomer("0701234567");
        BikeDTO bike = customer.getBikes().get(0);

        RepairOrderDTO created = controller.registerProblem(customer, bike, "Rear brake noise");
        RepairOrder stored = repairOrderRegistry.findOrder(created.getOrderId());

        assertNotNull(created, "Registering a problem returned null instead of a created order.");
        assertEquals(created.getOrderId(), stored.getOrderId(), "Created order was not found in the registry.");
        assertEquals("Rear brake noise", stored.getProblemDescription(),
                "Stored problem description did not match the input.");
    }

    /**
     * Verifies adding diagnostic to missing order does not throw.
     */
    @Test
    void testEnterDiagnosticMissingOrderDoesNotThrow() {
        assertDoesNotThrow(() -> controller.enterDiagnostic("999", "Missing order"));
    }

    /**
     * Verifies adding diagnostic to existing order updates report and status.
     */
    @Test
    void testEnterDiagnosticExistingOrderUpdatesOrder() throws CustomerNotFoundException {
        CustomerDTO customer = controller.checkCustomer("0701234567");
        BikeDTO bike = customer.getBikes().get(0);
        RepairOrderDTO order = controller.registerProblem(customer, bike, "Motor cuts out");

        controller.enterDiagnostic(order.getOrderId(), "Controller connector oxidized.");

        RepairOrder persistedOrder = repairOrderRegistry.findOrder(order.getOrderId());
        assertNotNull(persistedOrder.getDiagnosticReport(), "Diagnostic report was not added to the existing order.");
        assertEquals("Controller connector oxidized.", persistedOrder.getDiagnosticReport().getFindings(),
                "Stored diagnostic description did not match the input.");
        assertEquals(OrderState.IN_PROGRESS, persistedOrder.getState(), "Order state was not updated to IN_PROGRESS.");
    }

    /**
     * Verifies adding repair task to missing order does not throw.
     */
    @Test
    void testAddRepairTaskMissingOrderDoesNotThrow() {
        assertDoesNotThrow(() -> controller.addRepairTask("999", "Missing order task", 100));
    }

    /**
     * Verifies adding repair task to existing order updates total and status.
     */
    @Test
    void testAddRepairTaskExistingOrderUpdatesOrder() throws CustomerNotFoundException {
        CustomerDTO customer = controller.checkCustomer("0701234567");
        BikeDTO bike = customer.getBikes().get(0);
        RepairOrderDTO order = controller.registerProblem(customer, bike, "Display not starting");

        controller.addRepairTask(order.getOrderId(), "Replace display cable", 700);

        RepairOrder persistedOrder = repairOrderRegistry.findOrder(order.getOrderId());
        assertEquals(1, persistedOrder.getRepairTasks().size(), "The repair task was not added to the order.");
        assertEquals(700.0, persistedOrder.getTotalCost(), 0.001, "Total cost did not include the added repair task.");
        assertEquals(OrderState.IN_PROGRESS, persistedOrder.getState(), "Order state was not updated to IN_PROGRESS.");
    }

    /**
     * Verifies accept order for missing order does not throw.
     */
    @Test
    void testAcceptOrderMissingOrderDoesNotThrow() {
        assertDoesNotThrow(() -> controller.acceptOrder("999"));
    }

    /**
     * Verifies accepted completion updates status.
     */
    @Test
    void testAcceptOrderSetsAcceptedStatus() throws CustomerNotFoundException {
        CustomerDTO customer = controller.checkCustomer("0701234567");
        BikeDTO bike = customer.getBikes().get(0);
        RepairOrderDTO order = controller.registerProblem(customer, bike, "Battery issue");
        controller.enterDiagnostic(order.getOrderId(), "Battery cells degraded.");
        controller.addRepairTask(order.getOrderId(), "Replace battery pack", 3500);

        controller.acceptOrder(order.getOrderId());
        RepairOrder persistedOrder = repairOrderRegistry.findOrder(order.getOrderId());

        assertEquals(OrderState.ACCEPTED, persistedOrder.getState(), "Order state was not updated to ACCEPTED.");
    }
}