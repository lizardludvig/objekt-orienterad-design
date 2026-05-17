package se.kth.iv1350.repairbike.controller;

import se.kth.iv1350.repairbike.integration.CustomerRegistry;
import se.kth.iv1350.repairbike.integration.Printer;
import se.kth.iv1350.repairbike.integration.RepairOrderRegistry;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.DiagnosticReport;
import se.kth.iv1350.repairbike.model.RepairOrder;
import se.kth.iv1350.repairbike.model.BikeDTO;
import se.kth.iv1350.repairbike.model.CustomerDTO;
import se.kth.iv1350.repairbike.model.RepairOrderDTO;
import se.kth.iv1350.repairbike.model.OrderState;
import se.kth.iv1350.repairbike.model.RepairTask;
import se.kth.iv1350.repairbike.integration.CustomerNotFoundException;
import se.kth.iv1350.repairbike.model.RepairOrderObserver;
import se.kth.iv1350.repairbike.model.DiscountStrategy;

/**
 * This is the application's only controller. All calls to the model pass
 * through this class.
 */
public class Controller {
    private CustomerRegistry custReg;
    private RepairOrderRegistry orderReg;
    private Printer printer;

    /**
     * Adds an observer to the repair order registry to be notified of order
     * updates.
     * 
     * @param obs The observer to add.
     */
    public void addRepairOrderObserver(RepairOrderObserver obs) {
        orderReg.addRepairOrderObserver(obs);
    }

    /**
     * Creates a new instance.
     *
     * @param custReg The customer registry to use for customer lookups.
     * @param printer The printer to use for printing receipts.
     */
    public Controller(CustomerRegistry custReg, Printer printer) {
        this.custReg = custReg;
        this.orderReg = RepairOrderRegistry.getInstance();
        this.printer = printer;
    }

    /**
     * Checks if a customer is registered in the system using their phone number.
     *
     * @param phoneNumber The phone number of the customer to search for.
     * @return A {@link CustomerDTO} containing the customer's details. Returns null
     *         if a customer was queried successfully but no data was returned
     *         (though an exception is typically expected for missing customers).
     * @throws CustomerNotFoundException if the customer does not exist in the
     *                                   registry.
     * @throws DatabaseFailureException  if the database connection fails.
     */
    public CustomerDTO checkCustomer(String phoneNumber) throws CustomerNotFoundException {
        Customer customer = custReg.findCustomer(phoneNumber);
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(customer);
    }

    /**
     * Registers a new repair problem for a specified customer and bike.
     *
     * @param customerDTO        The data transfer object representing the customer.
     * @param bikeDTO            The data transfer object representing the bike to
     *                           be repaired.
     * @param problemDescription A text description of the problem provided by the
     *                           customer.
     * @return A {@link RepairOrderDTO} representing the newly created repair order.
     * @throws CustomerNotFoundException if the customer cannot be found during
     *                                   order creation.
     */
    public RepairOrderDTO registerProblem(CustomerDTO customerDTO, BikeDTO bikeDTO, String problemDescription)
            throws CustomerNotFoundException {
        Customer customer = custReg.findCustomer(customerDTO.getPhoneNumber());
        Bike bike = null;
        if (customer != null) {
            for (Bike b : customer.getBikes()) {
                if (b.getSerialNumber().equals(bikeDTO.getSerialNumber())) {
                    bike = b;
                    break;
                }
            }
        }
        RepairOrder order = orderReg.createRepairOrder(problemDescription, customer, bike);
        return new RepairOrderDTO(order);
    }

    /**
     * Adds a diagnostic report to an existing repair order.
     *
     * @param orderID               The unique identifier of the repair order.
     * @param diagnosticDescription A description of the diagnostic findings.
     */
    public void enterDiagnostic(String orderID, String diagnosticDescription) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.addDiagnostic(new DiagnosticReport(diagnosticDescription));
            orderReg.updateOrder(currentOrder);
        }
    }

    /**
     * Adds a repair task to an existing repair order.
     *
     * @param orderID  The unique identifier of the repair order.
     * @param taskName A descriptive name for the repair task.
     * @param cost     The cost of the repair task.
     */
    public void addRepairTask(String orderID, String taskName, double cost) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.addTask(new RepairTask(taskName, cost));
            orderReg.updateOrder(currentOrder);
        }
    }

    /**
     * Applies a discount strategy to an existing repair order.
     *
     * @param orderID  The unique identifier of the repair order.
     * @param discount The discount strategy to apply.
     */
    public void applyDiscount(String orderID, DiscountStrategy discount) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.setDiscountStrategy(discount);
            orderReg.updateOrder(currentOrder);
        }
    }

    /**
     * Accepts a repair order, finalizing its state and printing a receipt.
     *
     * @param orderID The unique identifier of the repair order to accept.
     */
    public void acceptOrder(String orderID) {
        RepairOrder currentOrder = orderReg.findOrder(orderID);
        if (currentOrder != null) {
            currentOrder.setState(OrderState.ACCEPTED);
            orderReg.updateOrder(currentOrder);
            printer.printReceipt(currentOrder);
        }
    }
}