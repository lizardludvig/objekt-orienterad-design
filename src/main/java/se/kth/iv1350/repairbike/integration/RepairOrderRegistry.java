package se.kth.iv1350.repairbike.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.kth.iv1350.repairbike.model.Bike;
import se.kth.iv1350.repairbike.model.Customer;
import se.kth.iv1350.repairbike.model.RepairOrder;
import se.kth.iv1350.repairbike.model.RepairOrderObserver;

/**
 * Responsible for storing and managing all repair orders in the system.
 */
public class RepairOrderRegistry {
    private static final RepairOrderRegistry INSTANCE = new RepairOrderRegistry();

    private Map<String, RepairOrder> orders = new HashMap<>();
    private int nextOrderID = 1;
    private List<RepairOrderObserver> orderObservers = new ArrayList<>();

    private RepairOrderRegistry() {
    }

    /**
     * Gets the single instance of this registry.
     *
     * @return The single instance of {@link RepairOrderRegistry}.
     */
    public static RepairOrderRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Adds an observer to be notified when a repair order is created or updated.
     *
     * @param obs The observer to add.
     */
    public void addRepairOrderObserver(RepairOrderObserver obs) {
        orderObservers.add(obs);
    }

    /**
     * Creates a new repair order, assigns it a unique ID, and stores it in the
     * registry.
     * Observers are automatically added to the new order.
     *
     * @param problemDescription A description of the problem provided by the
     *                           customer.
     * @param customer           The customer requesting the repair.
     * @param bike               The bike to be repaired.
     * @return The newly created repair order.
     */
    public RepairOrder createRepairOrder(String problemDescription, Customer customer, Bike bike) {
        RepairOrder currentOrder = new RepairOrder(String.valueOf(nextOrderID++), customer, bike, problemDescription);
        currentOrder.addObservers(orderObservers);
        orders.put(currentOrder.getOrderId(), currentOrder);
        return currentOrder;
    }

    /**
     * Finds a repair order using its unique order ID.
     *
     * @param orderID The unique identifier of the order.
     * @return A copy of the matching repair order, or null if no order was found.
     */
    public RepairOrder findOrder(String orderID) {
        RepairOrder order = orders.get(orderID);
        if (order != null) {
            return new RepairOrder(order);
        }
        return null;
    }

    /**
     * Updates an existing repair order in the registry.
     *
     * @param order The repair order containing the updated information.
     */
    public void updateOrder(RepairOrder order) {
        if (order != null) {
            orders.put(order.getOrderId(), order);
        }
    }
}