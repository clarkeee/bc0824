package tests;

import controller.RentalAgreement;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TestRentalAgreement {

    @BeforeEach
    void setUp() {
        RentalAgreement.initializeDatabase();
    }

    @ParameterizedTest
    @ValueSource(strings = {"JAKR", "LADW", "CHNS", "JAKD", "JAKR", "JAKR"})
    @DisplayName("Test Tool Code Input")
    void testToolCodeInput(String toolCodeInput) throws Exception {
        InputStream in = new ByteArrayInputStream(toolCodeInput.getBytes());
        System.setIn(in);

        assertEquals(toolCodeInput, RentalAgreement.toolCodeInput());
    }

    @ParameterizedTest
    @ValueSource(strings = {"9/3/15", "7/2/20", "7/2/15", "9/3/15", "7/2/15", "7/2/20"})
    @DisplayName("Test Check Out Date Input")
    void testCheckOutDateInput(String checkOutDateInput) throws Exception {
        InputStream in = new ByteArrayInputStream(checkOutDateInput.getBytes());
        System.setIn(in);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        LocalDate checkOutDate = LocalDate.parse(checkOutDateInput, formatter);

        assertEquals(checkOutDate, RentalAgreement.checkOutDateInput());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 3, 5, 6, 9, 4})
    @DisplayName("Test Rental Days Input")
    void testRentalDaysInput(int rentalDaysInput) throws Exception {
        InputStream in = new ByteArrayInputStream(Integer.toString(rentalDaysInput).getBytes());
        System.setIn(in);

        assertEquals(rentalDaysInput, RentalAgreement.rentalDaysInput());
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 10, 25, 0, 0, 50})
    @DisplayName("Test Discount Percent Input")
    void testDiscountPercentInput(int discountPercentInput) throws Exception {
        InputStream in = new ByteArrayInputStream(Integer.toString(discountPercentInput).getBytes());
        System.setIn(in);

        if(discountPercentInput > 100 || discountPercentInput < 0) {
            Exception exception = assertThrows(Exception.class, RentalAgreement::discountPercentInput);
            assertEquals("Max Retry Count Reached", exception.getMessage());
        } else {
            assertEquals(discountPercentInput, RentalAgreement.discountPercentInput());
        }
    }
}