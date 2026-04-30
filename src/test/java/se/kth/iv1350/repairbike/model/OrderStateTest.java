package se.kth.iv1350.repairbike.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for OrderState.
 */
class OrderStateTest {
    /**
     * Verifies all expected enum values exist in defined order.
     */
    @Test
    void testValuesContainAllExpectedStates() {
        OrderState[] expected = {
                OrderState.CREATED,
                OrderState.IN_PROGRESS,
                OrderState.ACCEPTED,
        };

        assertArrayEquals(expected, OrderState.values(), "Order states did not match expected lifecycle.");
    }

    /**
     * Verifies valueOf resolves declared state name.
     */
    @Test
    void testValueOfReturnsExpectedState() {
        assertEquals(OrderState.ACCEPTED, OrderState.valueOf("ACCEPTED"),
                "valueOf does not resolve existing enum constants.");
    }
}
