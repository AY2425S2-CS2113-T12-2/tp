package bookkeeper.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Loan {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Book book;
    private String borrowerName;
    private LocalDate returnDate; 
    private String phoneNumber;
    private String email;

    public Loan(Book book, String borrowerName, String returnDate, String phoneNumber, String email) {
        this.book = book;
        this.returnDate = parseAndValidateDate(returnDate); // Parse and validate the date
        this.borrowerName = borrowerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Book getBook() {
        return book;
    }

    public String getTitle() {
        return book.getTitle();
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setReturnDate(String returnDate) throws IllegalArgumentException {
        this.returnDate = parseAndValidateDate(returnDate); // Parse and validate the date
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toFileString() {
        String title = getTitle();
        String borrowerName = getBorrowerName();
        String contactNumber = getPhoneNumber();
        String returnDateString = returnDate.format(DATE_FORMATTER); // Format LocalDate to DD-MM-YYYY
        String email = getEmail();
        return title + " | " + borrowerName + " | " + returnDateString +
                " | " + contactNumber + " | " + email;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + System.lineSeparator()
                + "    Borrower: " + getBorrowerName() + System.lineSeparator()
                + "    Return Date: " + returnDate.format(DATE_FORMATTER) + System.lineSeparator()
                + "    Contact Number: " + getPhoneNumber() + System.lineSeparator()
                + "    Email: " + getEmail();
    }

    private LocalDate parseAndValidateDate(String date) throws IllegalArgumentException {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            validateNotPastDate(parsedDate); // Validate that the date is not in the past
            return parsedDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: DD-MM-YYYY");
        }
    }

    private void validateNotPastDate(LocalDate date) throws IllegalArgumentException {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The return date cannot be in the past. " +
                    "Please provide a valid future date.");
        }
    }
}
