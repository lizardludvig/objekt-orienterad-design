package se.kth.iv1350.repairbike.view;

import java.util.List;
import se.kth.iv1350.repairbike.controller.Controller;
import se.kth.iv1350.repairbike.integration.CustomerNotFoundException;
import se.kth.iv1350.repairbike.integration.DatabaseFailureException;
import se.kth.iv1350.repairbike.model.BikeDTO;
import se.kth.iv1350.repairbike.model.CustomerDTO;
import se.kth.iv1350.repairbike.model.RepairOrderDTO;
import se.kth.iv1350.repairbike.util.LogHandler;
import se.kth.iv1350.repairbike.util.RepairOrderLogger;
import se.kth.iv1350.repairbike.model.WinterDiscount; 

/**
 * Represents the main user interface or simulation of the application.
 * In this implementation, it runs a hardcoded execution of the system operations.
 */
public class View {

    private Controller contr;
    private ErrorMessageHandler errorMsgHandler = new ErrorMessageHandler();
    private LogHandler logger = new LogHandler();

    /**
     * Creates a new instance, configuring the view with a controller and attaching
     * relevant observers to the system.
     *
     * @param contr The controller used to communicate with the model layer.
     */
    public View(Controller contr) {
        this.contr = contr;
        this.contr.addRepairOrderObserver(new RepairOrderView());
        this.contr.addRepairOrderObserver(new RepairOrderLogger());
    }

    /**
     * Runs a pre-defined simulation of basic application flows, demonstrating
     * successful operations as well as exception handling scenarios.
     */
    public void runBasicFlow() {
        System.out.println("--- NORMAL FLOW ---");
        executeScenario("0701234567");

        System.out.println("\n--- SIMULATING EXCEPTION 1: CUSTOMER NOT FOUND ---");
        executeScenario("0709999999");

        System.out.println("\n--- SIMULATING EXCEPTION 2: DATABASE CRASH ---");
        executeScenario("0700000000");
    }

    /**
     * Executes a specific repair scenario for a given customer phone number.
     *
     * @param phoneNumber The phone number of the customer initiating the repair.
     */
    private void executeScenario(String phoneNumber) {
        try {
            CustomerDTO customer = contr.checkCustomer(phoneNumber);
            System.out.println("checkCustomer -> " + customer);

            List<BikeDTO> customerBikes = customer.getBikes();
            if (customerBikes.isEmpty()) {
                System.out.println("Customer has no bikes registered.");
                return;
            }

            BikeDTO bike = customerBikes.get(0);
            System.out.println("selectedBike -> " + bike);

            RepairOrderDTO currentOrder = contr.registerProblem(
                    customer, bike, "Battery does not charge.");
            System.out.println("registerProblem -> " + currentOrder);

            contr.enterDiagnostic(currentOrder.getOrderId(), "Battery cells are degraded.");
            System.out.println("enterDiagnostic -> done");

            contr.addRepairTask(currentOrder.getOrderId(), "Replace battery pack", 3500);
            System.out.println("addRepairTask #1 -> done");

            contr.addRepairTask(currentOrder.getOrderId(), "Update firmware", 400);
            System.out.println("addRepairTask #2 -> done");

            System.out.println("applyDiscount -> applying Winter Discount (10% off)");
            contr.applyDiscount(currentOrder.getOrderId(), new WinterDiscount());

            contr.acceptOrder(currentOrder.getOrderId());
            System.out.println("acceptOrder -> done");

        } catch (CustomerNotFoundException exc) {
            errorMsgHandler.showErrorMessage("Could not find a customer with phone number " + exc.getPhoneNumber() + ". Please check the number and try again.");
        } catch (DatabaseFailureException exc) {
            errorMsgHandler.showErrorMessage("The system is currently experiencing technical difficulties. Please try again later.");
            logger.logException(exc);
        }
    }
}