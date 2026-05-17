package se.kth.iv1350.repairbike.model;

/**
 * A discount strategy that gives 20% off for returning loyal customers.
 */
public class LoyaltyDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double totalCostBeforeDiscount) {
        return totalCostBeforeDiscount * 0.20; 
    }
}