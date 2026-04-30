package se.kth.iv1350.repairbike.integration;

import java.util.HashMap;
import java.util.Map;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.RepairOrder;

/**
 * Simulates storage and retrieval of repair orders.
 */
public class RepairOrderRegistry {
    private Map<String, RepairOrder> orders = new HashMap<>();
    private int nextOrderID = 1;

    /**
     * Creates and stores a new repair order.
     *
     * @param customer           The customer that owns the order.
     * @param bike               The bike to repair.
     * @param problemDescription The customer problem description.
     * @return The created order.
     */
    public RepairOrder createRepairOrder(String problemDescription, Customer customer, Bike bike) {
        RepairOrder currentOrder = new RepairOrder(String.valueOf(nextOrderID++), customer, bike, problemDescription);
        orders.put(currentOrder.getOrderId(), currentOrder);
        return currentOrder;
    }

    /**
     * Finds a stored order by id.
     *
     * @param orderID The order id.
     * @return The matching order, or null if missing.
     */
    public RepairOrder findOrder(String orderID) {
        return orders.get(orderID);
    }
}
