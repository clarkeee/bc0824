import controller.RentalAgreement;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        String mainMenuInput;
        RentalAgreement.initializeDatabase();

        System.out.println("\nWelcome to Big Box Store POS!");

        do {
            mainMenuInput = mainMenu();

            if (mainMenuInput.equals("1")) {
                RentalAgreement.gatherRentalAgreementData();
            } else if (mainMenuInput.equals("2")) {
                RentalAgreement.viewRentalAgreements();
            }
        } while (!mainMenuInput.equals("3"));
    }

    // Display Main Menu
    public static String mainMenu() {
        boolean validMainMenuInput = false;
        String mainMenuInput = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n----------------------- Main Menu --------------------------");
        System.out.println("1. Create New Rental Agreement");
        System.out.println("2. View Existing Rental Agreements");
        System.out.println("3. Exit");
        System.out.println("------------------------------------------------------------");

        do {
            try {
                System.out.print("\nEnter Menu Selection (1-3): ");
                mainMenuInput = scanner.nextLine();

                if (mainMenuInput.equals("1") || mainMenuInput.equals("2") || mainMenuInput.equals("3")) {
                    validMainMenuInput = true;
                } else {
                    throw new Exception("Invalid Main Menu Selection");
                }
            } catch (Exception e) {
                System.out.print("\nError: Invalid Main Menu Selection. Please enter a number from 1 through 3.");
            }
        } while (!validMainMenuInput);

        return mainMenuInput;
    }
}