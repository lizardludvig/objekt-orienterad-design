package se.kth.iv1350.repairbike.view;

import java.util.List;
import se.kth.iv1350.repairbike.controller.Controller;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.RepairOrder;

/**
 * Simulates a user interface with hard-coded calls to the controller.
 */
public class View {

    private Controller contr;

    /**
     * Creates a new view connected to the controller.
     *
     * @param contr The controller to call.
     */
    public View(Controller contr) {
        this.contr = contr;
    }

    /**
     * Runs one complete basic scenario and prints all returned values.
     */
    public void runBasicFlow() {
        Customer customer = contr.checkCustomer("0701234567");
        System.out.println("checkCustomer -> " + customer);

        List<Bike> customerBikes = customer.getBikes();
        if (customerBikes.isEmpty()) {
            System.out.println("Customer has no bikes registered.");
            return;
        }

        Bike bike = customerBikes.get(0);
        System.out.println("selectedBike -> " + bike);

        RepairOrder currentOrder = contr.registerProblem(
                customer,
                bike,
                "Battery does not charge.");
        System.out.println("registerProblem -> " + currentOrder);

        contr.enterDiagnostic(
                currentOrder.getOrderId(),
                "Battery cells are degraded.");
        System.out.println("enterDiagnostic -> done");

        contr.addRepairTask(
                currentOrder.getOrderId(),
                "Replace battery pack",
                3500);
        System.out.println("addRepairTask #1 -> done");

        contr.addRepairTask(currentOrder.getOrderId(), "Update firmware", 500);
        System.out.println("addRepairTask #2 -> done");

        contr.acceptOrder(currentOrder.getOrderId());
        System.out.println("acceptOrder -> done");
    }
}
