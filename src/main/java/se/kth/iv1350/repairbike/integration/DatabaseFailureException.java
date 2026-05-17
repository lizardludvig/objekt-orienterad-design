package se.kth.iv1350.repairbike.integration;

/**
 * Thrown when a severe technical failure occurs in the database/registry.
 */
public class DatabaseFailureException extends RuntimeException {
    
    /**
     * Creates a new instance representing a technical failure.
     *
     * @param message The technical error message.
     */
    public DatabaseFailureException(String message) {
        super(message);
    }
}