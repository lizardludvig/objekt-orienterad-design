package se.kth.iv1350.repairbike.controller;

import se.kth.iv1350.repairbike.integration.CustomerRegistry;
import se.kth.iv1350.repairbike.integration.Printer;
import se.kth.iv1350.repairbike.integration.RepairOrderRegistry;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.DiagnosticReport;
import se.kth.iv1350.repairbike.model.RepairOrder;
import se.kth.iv1350.repairbike.model.OrderState;
import se.kth.iv1350.repairbike.model.RepairTask;

/**
 * Coordinates use cases between view, integration, and model.
 */
public class Controller {
    private CustomerRegistry custReg;
    private RepairOrderRegistry orderReg;
    private Printer printer;

    /**
     * Creates a controller with all required dependencies.
     *
     * @param custReg  Handles customer lookup.
     * @param orderReg Handles order storage.
     * @param printer  Handles printed output formatting.
     */
    public Controller(
            CustomerRegistry custReg,
            RepairOrderRegistry orderReg,
            Printer printer) {
        this.custReg = custReg;
        this.orderReg = orderReg;
        this.printer = printer;
    }

    /**
     * Checks whether a customer exists.
     *
     * @param phoneNumber The phone number to search.
     * @return The customer, or null if none exists.
     */
    public Customer checkCustomer(String phoneNumber) {
        return custReg.findCustomer(phoneNumber);
    }

    /**
     * Registers a new repair problem and creates an order.
     *
     * @param customer           The owner of the order.
     * @param bike               The bike to repair.
     * @param problemDescription The problem description.
     * @return The created order.
     */
    public RepairOrder registerProblem(Customer customer, Bike bike, String problemDescription) {
        return orderReg.createRepairOrder(problemDescription, customer, bike);
    }

    /**
     * Adds a diagnostic report to an existing order.
     *
     * @param orderID               The order id.
     * @param diagnosticDescription The report findings.
     */
    public void enterDiagnostic(String orderID, String diagnosticDescription) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.addDiagnostic(new DiagnosticReport(diagnosticDescription));
        }
    }

    /**
     * Adds a repair task to an existing order.
     *
     * @param orderID  The order id.
     * @param taskName The task description.
     * @param cost     The task cost in SEK.
     */
    public void addRepairTask(String orderID, String taskName, double cost) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.addTask(new RepairTask(taskName, cost));
        }
    }

    /**
     * Accepts an order and prints a receipt.
     *
     * @param orderID The order id.
     */
    public void acceptOrder(String orderID) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.setState(OrderState.ACCEPTED);
            printer.printReceipt(currentOrder);
        }
    }
}