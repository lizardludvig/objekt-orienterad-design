package se.kth.iv1350.repairbike.model;

/**
 * Encapsulates the algorithm for calculating a discount.
 */
public interface DiscountStrategy {
    /**
     * Calculates the amount to be deducted from the total cost.
     *
     * @param totalCostBeforeDiscount The cost before any discounts are applied.
     * @return The discount amount in SEK.
     */
    double calculateDiscount(double totalCostBeforeDiscount);
}