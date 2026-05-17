package se.kth.iv1350.repairbike.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import se.kth.iv1350.repairbike.model.RepairOrderDTO;
import se.kth.iv1350.repairbike.model.RepairOrderObserver;

/**
 * An observer that logs updated repair orders to a text file.
 */
public class RepairOrderLogger implements RepairOrderObserver {
    private static final String LOG_FILE_NAME = "repairorder-updates-log.txt";
    private PrintWriter logFile;

    public RepairOrderLogger() {
        try {
            logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
        } catch (IOException ex) {
            System.out.println("Could not create repair order update logger file.");
            ex.printStackTrace();
        }
    }

    @Override
    public void orderUpdated(RepairOrderDTO repairOrder) {
        StringBuilder logMsgBuilder = new StringBuilder();
        logMsgBuilder.append(createTime());
        logMsgBuilder.append(" - Order Updated: ");
        logMsgBuilder.append(repairOrder.toString());
        
        logFile.println(logMsgBuilder);
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}