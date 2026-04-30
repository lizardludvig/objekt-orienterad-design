package se.kth.iv1350.repairbike.integration;

import se.kth.iv1350.repairbike.model.RepairOrder;

/**
 * Produces a printout for accepted repair orders.
 */
public class Printer {

    /**
     * Creates a printable receipt for a repair order.
     *
     * @param order The order to print.
     */
    public void printReceipt(RepairOrder order) {
        String receipt = "----- REPAIR RECEIPT -----\n"
                + "Order ID: " + order.getOrderId() + "\n"
                + "Customer: " + order.getCustomer().getName() + "\n"
                + "Bike: " + order.getBike().getModel() + " (" + order.getBike().getSerialNumber() + ")\n"
                + "Problem: " + order.getProblemDescription() + "\n"
                + "Total cost: " + order.getTotalCost() + " SEK\n"
                + "Status: " + order.getState() + "\n"
                + "--------------------------";
        System.out.println(receipt);
    }
}
