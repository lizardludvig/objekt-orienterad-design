package se.kth.iv1350.repairbike.model;

/**
 * Represents a technician diagnostic report for a repair order.
 */
public class DiagnosticReport {
    private String diagnosticDescription;

    /**
     * Creates a diagnostic report.
     *
     * @param diagnosticDescription The diagnostic findings.
     */
    public DiagnosticReport(String diagnosticDescription) {
        this.diagnosticDescription = diagnosticDescription;
    }

    /**
     * Gets the diagnostic findings.
     *
     * @return The findings text.
     */
    public String getFindings() {
        return diagnosticDescription;
    }

    @Override
    public String toString() {
        return "DiagnosticReport{diagnosticDescription='" + diagnosticDescription + "'}";
    }
}
