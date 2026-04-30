package se.kth.iv1350.repairbike.startup;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Main startup.
 */
class MainTest {
    /**
     * Verifies application startup can execute without exceptions.
     */
    @Test
    void testMainRunsWithoutThrowingException() {
        assertDoesNotThrow(() -> Main.main(new String[0]),
                "Program startup threw an unexpected exception.");
    }
}
