package se.kth.iv1350.repairbike.model;

/**
 * A Data Transfer Object (DTO) containing a read-only snapshot of a repair order.
 * Used to pass data to the view without exposing the model object itself.
 */
public class RepairOrderDTO {
    private final String orderID;
    private final String problemDescription;
    private final double totalCostSek;
    private final OrderState state;

    /**
     * Creates a new DTO containing a snapshot of the provided repair order.
     *
     * @param order The repair order to extract data from.
     */
    public RepairOrderDTO(RepairOrder order) {
        this.orderID = order.getOrderId();
        this.problemDescription = order.getProblemDescription();
        this.totalCostSek = order.getTotalCost();
        this.state = order.getState();
    }

    /**
     * Gets the unique identifier for the repair order.
     *
     * @return The order ID.
     */
    public String getOrderId() {
        return orderID;
    }

    /**
     * Gets the initial description of the problem provided by the customer.
     *
     * @return The problem description.
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Gets the current total cost of the repair order.
     *
     * @return The total cost in SEK.
     */
    public double getTotalCost() {
        return totalCostSek;
    }

    /**
     * Gets the current lifecycle state of the repair order.
     *
     * @return The order state.
     */
    public OrderState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "RepairOrderDTO{orderID=" + orderID + ", problemDescription='" + problemDescription + "', totalCost=" + totalCostSek + ", state=" + state + "}";
    }
}