package se.kth.iv1350.repairbike.model;

/**
 * Represents a bike submitted for repair.
 */
public class Bike {
    private String model;
    private String serialNumber;

    /**
     * Creates a new bike.
     *
     * @param model        The bike model.
     * @param serialNumber The unique serial number.
     */
    public Bike(String model, String serialNumber) {
        this.model = model;
        this.serialNumber = serialNumber;
    }

    /**
     * Gets the bike model.
     *
     * @return The model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the bike serial number.
     *
     * @return The serial number.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String toString() {
        return "Bike{model='" + model + "', serialNumber='" + serialNumber + "'}";
    }
}
