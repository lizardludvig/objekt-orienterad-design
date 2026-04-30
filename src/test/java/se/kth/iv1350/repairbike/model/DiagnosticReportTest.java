package se.kth.iv1350.repairbike.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DiagnosticReport.
 */
class DiagnosticReportTest {
    private DiagnosticReport report;

    /**
     * Creates one diagnostic report before each test.
     */
    @BeforeEach
    void setUp() {
        report = new DiagnosticReport("Battery cells degraded");
    }

    /**
     * Verifies findings can be retrieved after creation.
     */
    @Test
    void testGetFindingsReturnsConstructorValue() {
        assertEquals("Battery cells degraded", report.getFindings(),
                "The findings did not match the constructor argument");
    }

    /**
     * Verifies textual representation includes diagnostic text.
     */
    @Test
    void testToStringContainsFindings() {
        String asText = report.toString();
        assertTrue(asText.contains("Battery cells degraded"),
                "Text representation is missing the diagnostic findings.");
    }
}
