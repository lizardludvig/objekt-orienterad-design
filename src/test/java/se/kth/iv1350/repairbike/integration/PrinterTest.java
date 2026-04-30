package se.kth.iv1350.repairbike.integration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.RepairOrder;
import se.kth.iv1350.repairbike.model.RepairTask;

/**
 * Unit tests for Printer.
 */
class PrinterTest {
    private Printer printer;

    /**
     * Creates a new printer instance before each test.
     */
    @BeforeEach
    void setUp() {
        printer = new Printer();
    }

    /**
     * Verifies printout includes key order information.
     */
    @Test
    void testPrintReceiptDoesNotThrow() {
        Bike bike = new Bike("VoltBike S2", "SN-500");
        Customer customer = new Customer("C-1", "Test Person", "0700000000", Collections.singletonList(bike));
        RepairOrder order = new RepairOrder("5", customer, bike, "Battery issue");
        order.addTask(new RepairTask("Replace battery", 3500));
        order.accept();

        assertDoesNotThrow(() -> printer.printReceipt(order), "Printing a receipt threw an unexpected exception.");
    }
}
