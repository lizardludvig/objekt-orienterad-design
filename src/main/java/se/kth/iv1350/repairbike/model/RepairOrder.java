package se.kth.iv1350.repairbike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents one repair order and all data connected to it.
 */
public class RepairOrder {
    private String orderID;
    private Customer customer;
    private Bike bike;
    private String problemDescription;
    private DiagnosticReport currentReport;
    private List<RepairTask> repairTasks = new ArrayList<>();
    private double totalCostSek;
    private OrderState state = OrderState.CREATED;

    /**
     * Creates a repair order.
     *
     * @param orderID            Unique order id.
     * @param customer           The customer who owns the order.
     * @param bike               The bike to repair.
     * @param problemDescription Description of the initial problem.
     */
    public RepairOrder(String orderID, Customer customer, Bike bike, String problemDescription) {
        this.orderID = orderID;
        this.customer = customer;
        this.bike = bike;
        this.problemDescription = problemDescription;
    }

    /**
     * Gets the order id.
     *
     * @return The order id.
     */
    public String getOrderId() {
        return orderID;
    }

    /**
     * Gets the customer that owns this order.
     *
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the bike for this order.
     *
     * @return The bike.
     */
    public Bike getBike() {
        return bike;
    }

    /**
     * Gets the stated initial problem.
     *
     * @return The problem description.
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Gets the latest diagnostic report.
     *
     * @return The diagnostic report, or null if not set.
     */
    public DiagnosticReport getDiagnosticReport() {
        return currentReport;
    }

    /**
     * Gets all repair tasks.
     *
     * @return An immutable list of tasks.
     */
    public List<RepairTask> getRepairTasks() {
        return Collections.unmodifiableList(repairTasks);
    }

    /**
     * Gets the current total cost.
     *
     * @return Total cost in SEK.
     */
    public double getTotalCost() {
        return totalCostSek;
    }

    /**
     * Gets the current order State.
     *
     * @return The State.
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Adds or replaces the order's diagnostic report.
     *
     * @param currentReport The report to store.
     */
    public void addDiagnostic(DiagnosticReport currentReport) {
        this.currentReport = currentReport;
        this.state = OrderState.IN_PROGRESS;
    }

    /**
     * Adds one repair task and updates total cost.
     *
     * @param task The task to add.
     */
    public void addTask(RepairTask task) {
        this.repairTasks.add(task);
        calculateTotal();
        this.state = OrderState.IN_PROGRESS;
    }

    /**
     * Marks this order as accepted by the customer.
     */
    public void accept() {
        this.state = OrderState.ACCEPTED;
    }

    /**
     * Sets the lifecycle state of this order.
     *
     * @param state New state value.
     */
    public void setState(OrderState state) {
        this.state = state;
    }

    private void calculateTotal() {
        this.totalCostSek = 0;
        for (RepairTask task : repairTasks) {
            this.totalCostSek += task.getCost();
        }
    }

    @Override
    public String toString() {
        return "RepairOrder{orderID="
                + orderID
                + ", customer="
                + customer
                + ", bike="
                + bike
                + ", problemDescription='"
                + problemDescription
                + "', currentReport="
                + currentReport
                + ", repairTasks="
                + repairTasks
                + ", totalCost="
                + totalCostSek
                + ", state="
                + state
                + "}";
    }
}
