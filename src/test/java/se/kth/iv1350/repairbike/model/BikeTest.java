package se.kth.iv1350.repairbike.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Bike.
 */
class BikeTest {
    private Bike bike;

    /**
     * Creates one bike before each test.
     */
    @BeforeEach
    void setUp() {
        bike = new Bike("CityCruiser", "CC-1234");
    }

    /**
     * Verifies constructor values are available through getters.
     */
    @Test
    void testConstructorStoresModelAndSerialNumber() {
        assertEquals("CityCruiser", bike.getModel(), "The bike model did not match the constructor argument.");
        assertEquals("CC-1234", bike.getSerialNumber(), "The bike serial number did not match the constructor argument.");
    }

    /**
     * Verifies textual representation contains key bike attributes.
     */
    @Test
    void testToStringContainsModelAndSerialNumber() {
        String asText = bike.toString();

        assertTrue(asText.contains("CityCruiser"), "Text representation is missing the bike model.");
        assertTrue(asText.contains("CC-1234"), "Text representation is missing the serial number.");
    }
}
