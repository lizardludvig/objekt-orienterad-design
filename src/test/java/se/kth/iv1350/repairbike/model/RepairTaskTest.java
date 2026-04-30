package se.kth.iv1350.repairbike.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for RepairTask.
 */
class RepairTaskTest {
    private RepairTask task;

    /**
     * Creates one task fixture before each test.
     */
    @BeforeEach
    void setUp() {
        task = new RepairTask("Replace chain", 650);
    }

    /**
     * Verifies constructor data can be retrieved through getters.
     */
    @Test
    void testConstructorStoresTaskNameAndCost() {
        assertEquals("Replace chain", task.getDescription(),
                "Task description did not match the constructor argument.");
        assertEquals(650.0, task.getCost(),
                "Task cost did not match the constructor argument.");
    }

    /**
     * Verifies textual representation includes name and cost.
     */
    @Test
    void testToStringContainsNameAndCost() {
        String asText = task.toString();

        assertTrue(asText.contains("Replace chain"), "Text representation is missing the task name.");
        assertTrue(asText.contains("650.0"), "Text representation is missing the task cost.");
    }
}
