package se.kth.iv1350.repairbike.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a repair order containing information about the customer, bike, requested repairs, and the order's current state.
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
    private List<RepairOrderObserver> observers = new ArrayList<>();
    private DiscountStrategy discountStrategy;

    /**
     * Creates a new repair order.
     *
     * @param orderID            The unique identifier for this order.
     * @param customer           The customer who owns the bike.
     * @param bike               The bike submitted for repair.
     * @param problemDescription The problem described by the customer.
     */
    public RepairOrder(String orderID, Customer customer, Bike bike, String problemDescription) {
        this.orderID = orderID;
        this.customer = customer;
        this.bike = bike;
        this.problemDescription = problemDescription;
    }

    /**
     * Creates a copy of an existing repair order.
     *
     * @param other The repair order to copy.
     */
    public RepairOrder(RepairOrder other) {
        this.orderID = other.orderID;
        this.customer = other.customer;
        this.bike = other.bike;
        this.problemDescription = other.problemDescription;
        this.currentReport = other.currentReport;
        this.repairTasks = new ArrayList<>(other.repairTasks);
        this.totalCostSek = other.totalCostSek;
        this.state = other.state;
        this.observers = new ArrayList<>(other.observers); 
    }

    /**
     * Adds a list of observers to be notified of changes to this order.
     *
     * @param observers The observers to add.
     */
    public void addObservers(List<RepairOrderObserver> observers) {
        this.observers.addAll(observers);
    }

    private void notifyObservers() {
        RepairOrderDTO dto = new RepairOrderDTO(this);
        for (RepairOrderObserver obs : observers) {
            obs.orderUpdated(dto);
        }
    }

    /**
     * Gets the order's unique ID.
     *
     * @return The order ID.
     */
    public String getOrderId() { return orderID; }

    /**
     * Gets the customer associated with the order.
     *
     * @return The customer.
     */
    public Customer getCustomer() { return customer; }

    /**
     * Gets the bike associated with the order.
     *
     * @return The bike.
     */
    public Bike getBike() { return bike; }

    /**
     * Gets the description of the problem provided by the customer.
     *
     * @return The problem description.
     */
    public String getProblemDescription() { return problemDescription; }

    /**
     * Gets the latest diagnostic report.
     *
     * @return The diagnostic report, or null if none exists.
     */
    public DiagnosticReport getDiagnosticReport() { return currentReport; }

    /**
     * Gets a read-only list of the repair tasks added to this order.
     *
     * @return An unmodifiable list of repair tasks.
     */
    public List<RepairTask> getRepairTasks() { return Collections.unmodifiableList(repairTasks); }

    /**
     * Gets the total calculated cost of the repair order, including discounts.
     *
     * @return The total cost in SEK.
     */
    public double getTotalCost() { return totalCostSek; }

    /**
     * Gets the current lifecycle state of the repair order.
     *
     * @return The current state.
     */
    public OrderState getState() { return state; }

    /**
     * Adds a diagnostic report to the order and updates its state to IN_PROGRESS.
     * Notifies observers of the change.
     *
     * @param currentReport The diagnostic report to add.
     */
    public void addDiagnostic(DiagnosticReport currentReport) {
        this.currentReport = currentReport;
        this.state = OrderState.IN_PROGRESS;
        notifyObservers(); 
    }

    /**
     * Adds a repair task to the order, recalculates the total cost, and updates
     * the state to IN_PROGRESS. Notifies observers of the change.
     *
     * @param task The repair task to add.
     */
    public void addTask(RepairTask task) {
        this.repairTasks.add(task);
        calculateTotal();
        this.state = OrderState.IN_PROGRESS;
        notifyObservers(); 
    }

    /**
     * Sets a discount strategy for this order and recalculates the total cost.
     * Notifies observers of the change.
     *
     * @param strategy The discount strategy to apply.
     */
    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
        calculateTotal();
        notifyObservers();
    }

    /**
     * Accepts the repair order and sets its state to ACCEPTED.
     * Notifies observers of the change.
     */
    public void accept() {
        this.state = OrderState.ACCEPTED;
        notifyObservers(); 
    }

    /**
     * Explicitly sets the state of the repair order.
     * Notifies observers of the change.
     *
     * @param state The new state.
     */
    public void setState(OrderState state) {
        this.state = state;
        notifyObservers(); 
    }

    private void calculateTotal() {
        double baseCost = 0;
        for (RepairTask task : repairTasks) {
            baseCost += task.getCost();
        }
        
        if (discountStrategy != null) {
            double discountAmount = discountStrategy.calculateDiscount(baseCost);
            this.totalCostSek = Math.max(0, baseCost - discountAmount); 
        } else {
            this.totalCostSek = baseCost;
        }
    }

    @Override
    public String toString() {
        return "RepairOrder{orderID=" + orderID + ", customer=" + customer + ", bike=" + bike
                + ", problemDescription='" + problemDescription + "', currentReport=" + currentReport
                + ", repairTasks=" + repairTasks + ", totalCost=" + totalCostSek + ", state=" + state + "}";
    }
}