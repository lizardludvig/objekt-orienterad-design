package se.kth.iv1350.repairbike.model;

/**
 * A discount strategy that gives 10% off during the winter season.
 */
public class WinterDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double totalCostBeforeDiscount) {
        return totalCostBeforeDiscount * 0.10; 
    }
}