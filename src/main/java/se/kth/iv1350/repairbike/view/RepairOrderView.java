package se.kth.iv1350.repairbike.view;

import se.kth.iv1350.repairbike.model.RepairOrderDTO;
import se.kth.iv1350.repairbike.model.RepairOrderObserver;

/**
 * An observer that prints updated repair orders to the console for technicians and receptionists.
 */
public class RepairOrderView implements RepairOrderObserver {
    
    @Override
    public void orderUpdated(RepairOrderDTO repairOrder) {
        System.out.println("\n*** NOTIFICATION: REPAIR ORDER UPDATED ***");
        System.out.println(repairOrder.toString());
        System.out.println("******************************************\n");
    }
}