package controller;

import database.*;
import model.Holiday;
import model.Price;
import model.Tool;
import model.ToolType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class RentalAgreement {
    static ToolTypeDatabase toolTypeDatabase = new ToolTypeDatabase();
    static ToolDatabase toolDatabase = new ToolDatabase();
    static PriceDatabase priceDatabase = new PriceDatabase();
    static HolidayDatabase holidayDatabase = new HolidayDatabase();
    static RentalAgreementDatabase rentalAgreementDatabase = new RentalAgreementDatabase();
    static int retryMax = 5;

    // Initialize the databases
    public static void initializeDatabase() {
        // Save tool types to the database
        toolTypeDatabase.saveToolType(new ToolType("Chainsaw"));
        toolTypeDatabase.saveToolType(new ToolType("Ladder"));
        toolTypeDatabase.saveToolType(new ToolType("Jackhammer"));

        // Save tools to the database
        toolDatabase.saveTool(new Tool("CHNS", toolTypeDatabase.getToolType("Chainsaw"), "Stihl"));
        toolDatabase.saveTool(new Tool("LADW", toolTypeDatabase.getToolType("Ladder"), "Werner"));
        toolDatabase.saveTool(new Tool("JAKD", toolTypeDatabase.getToolType("Jackhammer"), "DeWalt"));
        toolDatabase.saveTool(new Tool("JAKR", toolTypeDatabase.getToolType("Jackhammer"), "Ridgid"));

        // Save prices to the database
        priceDatabase.savePrice(new Price(toolTypeDatabase.getToolType("Chainsaw"), new BigDecimal("1.99"), true, true, false));
        priceDatabase.savePrice(new Price(toolTypeDatabase.getToolType("Ladder"), new BigDecimal("1.49"), true, false, true));
        priceDatabase.savePrice(new Price(toolTypeDatabase.getToolType("Jackhammer"), new BigDecimal("2.99"), true, false, false));

        // Create Holidays for +/ 50 years from current year
        for (int year = Year.now().getValue() - 50; year < Year.now().getValue() + 50; year++) {
            // Create July 4th model.Holiday
            LocalDate julyFourthDate = LocalDate.of(year, 7, 4);
            if (julyFourthDate.getDayOfWeek().toString().equals("SATURDAY")) {
                julyFourthDate = julyFourthDate.minusDays(1);
            } else if (julyFourthDate.getDayOfWeek().toString().equals("SUNDAY")) {
                julyFourthDate = julyFourthDate.plusDays(1);
            }

            // Save July 4th model.Holiday to the database
            Holiday julyFourth = new Holiday(julyFourthDate, "July 4th");
            holidayDatabase.saveHoliday(julyFourth);

            // Create Labor Day model.Holiday Date
            LocalDate laborDayDate = LocalDate.of(year, 9, 1);
            for (int i = 0; i <= 7; i++) {
                if (laborDayDate.getDayOfWeek().toString().equals("MONDAY")) {
                    break;
                } else  {
                    laborDayDate = laborDayDate.plusDays(1);
                }
            }

            // Save Labor Day model.Holiday to the database
            Holiday laborDay = new Holiday(laborDayDate, "Labor Day");
            holidayDatabase.saveHoliday(laborDay);
        }
    }

    // Gather user input needed to generate Rental Agreement
    public static void gatherRentalAgreementData() throws Exception {
        String toolCode = toolCodeInput();
        int rentalDays = rentalDaysInput();
        int discountPercent = discountPercentInput();
        LocalDate checkOutDate = checkOutDateInput();

        generateRentalAgreement(toolCode, rentalDays, discountPercent, checkOutDate);
    }

    // Prompt user for tool code
    public static String toolCodeInput() throws Exception {
        boolean validToolCodeInput = false;
        String toolCodeInput = null;
        int retryCount = 0;
        Scanner scanner = new Scanner(System.in);

        toolDatabase.listTools();

        do {
            try {
                System.out.print("\nEnter model.Tool Code from list above (eg: CHNS): ");
                toolCodeInput = scanner.nextLine();

                if (Objects.equals(toolDatabase.getTool(toolCodeInput.toUpperCase().trim()), null)) {
                    throw new Exception("Invalid tool code");
                } else {
                    validToolCodeInput = true;
                }
            } catch (Exception e) {
                retryCount++;

                if (retryCount > retryMax) {
                    System.out.print("\nError: Maximum retry count reached. Terminating.");
                    throw new Exception("Max Retry Count Reached");
                }

                System.out.print("\nError: Invalid tool code. Please try again.");
            }
        } while (!validToolCodeInput);

        return toolCodeInput.toUpperCase().trim();
    }

    // Prompt user for number of rental days
    public static int rentalDaysInput() throws Exception {
        boolean validRentalDaysInput = false;
        int rentalDaysInput = 0;
        int retryCount = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.print("\nEnter number of days to rent tool (eg: 4): ");
                rentalDaysInput = scanner.nextInt();

                if (rentalDaysInput < 1) {
                    throw new Exception();
                } else {
                    validRentalDaysInput = true;
                }
            } catch (Exception e) {
                retryCount++;

                if (retryCount > retryMax) {
                    System.out.print("\nError: Maximum retry count reached. Terminating.");
                    throw new Exception("Max Retry Count Reached");
                }

                System.out.print("\nError: Number of days to rent tool must be 1 or greater. Please try again.");
            }
        } while (!validRentalDaysInput);

        return rentalDaysInput;
    }

    // Prompt user for discount percent
    public static int discountPercentInput() throws Exception {
        boolean validDiscountPercentInput = false;
        int discountPercentInput = 0;
        int retryCount = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.print("\nEnter discount percentage (range: 0-100): ");
                discountPercentInput = scanner.nextInt();

                if (discountPercentInput < 0 || discountPercentInput > 100) {
                    throw new Exception();
                } else {
                    validDiscountPercentInput = true;
                }
            } catch (Exception e) {
                retryCount++;

                if (retryCount > retryMax) {
                    System.out.print("\nError: Maximum retry count reached. Terminating.");
                    throw new Exception("Max Retry Count Reached");
                }

                System.out.print("\nError: Discount percent must be 0 to 100. Please try again.");
            }
        } while (!validDiscountPercentInput);

        return discountPercentInput;
    }

    // Prompt user for check out date
    public static LocalDate checkOutDateInput() throws Exception {
        boolean validCheckOutDateInput = false;
        LocalDate checkOutDate = null;
        int retryCount = 0;
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");

        do {
            try {
                System.out.print("\nEnter check out date (eg: " + LocalDate.now().format(formatter) + "): ");
                String checkOutDateInput = scanner.nextLine();

                if (checkOutDateInput.isEmpty()) {
                    checkOutDate = LocalDate.now();
                } else {
                    checkOutDate = LocalDate.parse(checkOutDateInput, formatter);
                }

                if (checkOutDate.getYear() > Year.now().getValue() + 50 || checkOutDate.getYear() < Year.now().getValue() - 50) {
                    throw new Exception();
                }

                validCheckOutDateInput = true;
            } catch (Exception e) {
                retryCount++;

                if (retryCount > retryMax) {
                    System.out.print("\nError: Maximum retry count reached. Terminating.");
                    throw new Exception("Max Retry Count Reached");
                }

                System.out.print("\nError: Invalid Date. Please try again.");
            }
        } while (!validCheckOutDateInput);

        return checkOutDate;
    }

    // Generate Rental Agreement and store in database
    public static void generateRentalAgreement(String toolCode, int rentalDays, int discountPercent, LocalDate checkOutDate) {

        // Calculate Charge Days
        int chargeDays = calculateChargeDays(toolCode, rentalDays, checkOutDate);

        // Get Daily Charge
        BigDecimal dailyCharge = priceDatabase.getPrice(toolDatabase.getTool(toolCode).getType().getToolType()).getDailyCharge();

        // Calculate Pre-Discount Charge
        BigDecimal preDiscountCharge = dailyCharge.multiply(BigDecimal.valueOf(chargeDays));
        preDiscountCharge = preDiscountCharge.setScale(2, RoundingMode.HALF_UP);

        // Calculate Discount Amount
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent)).divide(new BigDecimal(100), RoundingMode.HALF_UP);
        discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);

        // Calculate Final Charge
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);
        finalCharge = finalCharge.setScale(2, RoundingMode.HALF_UP);

        model.RentalAgreement rentalAgreement = new model.RentalAgreement(
                /* Tool Code */ toolCode,
                /* Tool Type */ toolDatabase.getTool(toolCode).getType(),
                /* Tool Brand */ toolDatabase.getTool(toolCode).getBrand(),
                /* Rental Days */ rentalDays,
                /* Check Out Date */ checkOutDate,
                /* Due Date */ checkOutDate.plusDays(rentalDays),
                /* Daily Rental Charge */ dailyCharge,
                /* Charge Days */ chargeDays,
                /* Pre-Discount Charge */ preDiscountCharge,
                /* Discount Percent */ discountPercent,
                /* Discount Amount */ discountAmount,
                /* Final Charge */ finalCharge
        );

        Integer id = rentalAgreementDatabase.getNumberOfRentalAgreements() + 1;

        rentalAgreementDatabase.saveRentalAgreement(id, rentalAgreement);

        System.out.println("\n<< Rental Agreement has been saved successfully. >>");
    }

    // Calculate Charge Days
    public static int calculateChargeDays(String toolCode, int rentalDays, LocalDate checkOutDate) {
        int i = 1;
        int chargeDays = 0;
        boolean weekday;
        boolean weekend;
        boolean holiday;
        boolean weekdayCharge;
        boolean weekendCharge;
        boolean holidayCharge;

        do {
            // Reset variables for loop
            weekday = false;
            weekend = false;
            holiday = false;
            weekdayCharge = priceDatabase.getPrice(toolDatabase.getTool(toolCode).getType().getToolType()).getWeekdayCharge();
            weekendCharge = priceDatabase.getPrice(toolDatabase.getTool(toolCode).getType().getToolType()).getWeekendCharge();
            holidayCharge = priceDatabase.getPrice(toolDatabase.getTool(toolCode).getType().getToolType()).getHolidayCharge();

            // Check if Weekend or Weekday
            if (checkOutDate.plusDays(i).getDayOfWeek().toString().equals("SATURDAY") ||
                    checkOutDate.plusDays(i).getDayOfWeek().toString().equals("SUNDAY")) {
                weekend = true;
            } else {
                weekday = true;
            }

            // Check if model.Holiday
            if (holidayDatabase.getHoliday(checkOutDate.plusDays(i)) != null &&
                    checkOutDate.plusDays(i).equals(holidayDatabase.getHoliday(checkOutDate.plusDays(i)).getDate())) {
                holiday = true;
            }

            if (weekday && weekdayCharge && !holiday) {
                chargeDays++;
            } else if (weekend && weekendCharge && !holiday) {
                chargeDays++;
            }

            if (holiday && holidayCharge) {
                chargeDays++;
            }

            i++;
        } while (i <= rentalDays);

        return chargeDays;
    }

    // View existing Rental Agreements in the database
    public static void viewRentalAgreements() {
        rentalAgreementDatabase.listRentalAgreements();
    }
}
