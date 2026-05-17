package se.kth.iv1350.repairbike.model;

/**
 * A listener interface for receiving notifications about repair order updates.
 */
public interface RepairOrderObserver {
    /**
     * Invoked when a repair order has been updated in any way.
     *
     * @param repairOrder The updated repair order, encapsulated in a DTO  so the observer cannot modify the model.
     */
    void orderUpdated(RepairOrderDTO repairOrder);
}