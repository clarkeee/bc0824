package database;

import model.RentalAgreement;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RentalAgreementDatabase {
    private final Map<Integer, RentalAgreement> rentalAgreementDatabase;

    public RentalAgreementDatabase() {
        rentalAgreementDatabase = new HashMap<>();
    }

    public void saveRentalAgreement(Integer id, RentalAgreement rentalAgreement) {
        rentalAgreementDatabase.put(id, rentalAgreement);
    }

    public RentalAgreement getRentalAgreement(Integer id) {
        return rentalAgreementDatabase.get(id);
    }

    public int getNumberOfRentalAgreements() {
        return rentalAgreementDatabase.size();
    }

    public void listRentalAgreements() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        String format = "%-23s%s%n";

        if (rentalAgreementDatabase.isEmpty()) {
            System.out.println("\n<< Rental Agreement Database is Empty >>");
        } else {
            for (RentalAgreement rentalAgreement : rentalAgreementDatabase.values()) {
                System.out.println("\n------------------- Rental ID: " + rentalAgreement.hashCode() + " -------------------");
                System.out.printf(format, "Tool Code:", rentalAgreement.getToolCode());
                System.out.printf(format, "Tool Type:", rentalAgreement.getToolType().getToolType());
                System.out.printf(format, "Tool Brand:", rentalAgreement.getToolBrand());
                System.out.printf(format, "Rental Days:", rentalAgreement.getRentalDays());
                System.out.printf(format, "Check Out Date:", rentalAgreement.getCheckOutDate().format(formatter));
                System.out.printf(format, "Due Date:", rentalAgreement.getDueDate().format(formatter));
                System.out.printf(format, "Daily Rental Charge:", "$" + rentalAgreement.getDailyRentalCharge());
                System.out.printf(format, "Charge Days:", rentalAgreement.getChargeDays());
                System.out.printf(format, "Pre-Discount Charge:", "$" + rentalAgreement.getPreDiscountCharge());
                System.out.printf(format, "Discount Percent:", rentalAgreement.getDiscountPercent() + "%");
                System.out.printf(format, "Discount Amount:", "$" + rentalAgreement.getDiscountAmount());
                System.out.printf(format, "Final Charge:", "$" + rentalAgreement.getFinalCharge());
            }
        }
        System.out.println("------------------------------------------------------------");
    }
}