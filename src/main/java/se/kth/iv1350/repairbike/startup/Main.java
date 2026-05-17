package se.kth.iv1350.repairbike.startup;

import se.kth.iv1350.repairbike.controller.Controller;
import se.kth.iv1350.repairbike.integration.CustomerRegistry;
import se.kth.iv1350.repairbike.integration.Printer;
import se.kth.iv1350.repairbike.view.View;

/**
 * Starts the application and wires dependencies.
 */
public class Main {

    /**
     * Program entry point, startup.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        CustomerRegistry custReg = new CustomerRegistry();
        Printer printer = new Printer();

        Controller contr = new Controller(custReg, printer);

        View view = new View(contr);
        view.runBasicFlow();
    }
}