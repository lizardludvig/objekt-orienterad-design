package se.kth.iv1350.repairbike.model;

/**
 * A Data Transfer Object (DTO) containing a read-only snapshot of a bike.
 */
public class BikeDTO {
    private final String model;
    private final String serialNumber;

    /**
     * Creates a new DTO containing a snapshot of the provided bike.
     *
     * @param bike The bike to extract data from.
     */
    public BikeDTO(Bike bike) {
        this.model = bike.getModel();
        this.serialNumber = bike.getSerialNumber();
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
        return "BikeDTO{model='" + model + "', serialNumber='" + serialNumber + "'}";
    }
}
