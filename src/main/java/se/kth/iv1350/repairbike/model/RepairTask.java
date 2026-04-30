package se.kth.iv1350.repairbike.model;

/**
 * Represents one concrete task needed to repair a bike.
 */
public class RepairTask {
    private String taskName;
    private double cost;

    /**
     * Creates a repair task.
     *
     * @param taskName A short task description.
     * @param cost     The task cost in SEK.
     */
    public RepairTask(String taskName, double cost) {
        this.taskName = taskName;
        this.cost = cost;
    }

    /**
     * Gets the task description.
     *
     * @return The description.
     */
    public String getDescription() {
        return taskName;
    }

    /**
     * Gets the task cost.
     *
     * @return The cost.
     */
    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "RepairTask{taskName='" + taskName + "', cost=" + cost + "}";
    }
}
