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

    public void setLoanFields(String borrowerName, String returnDate, String phoneNumber, String email) {
        if (borrowerName != null && !borrowerName.isEmpty()) {
            setBorrowerName(borrowerName);
        }
        if (returnDate != null) {
            setReturnDate(returnDate);
        }
        if (phoneNumber != null) {
            setPhoneNumber(phoneNumber);
        }
        if (email != null) {
            setEmail(email);
        }
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
            // Validate the format of the date string
            if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
                throw new IllegalArgumentException("Invalid date format. Expected format: DD-MM-YYYY");
            }

            // Split the date string into day, month, and year
            String[] dateParts = date.split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);

            // Validate the day, month, and year
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Invalid month: " + month + ". Month must be between 01 and 12.");
            }
            if (day < 1 || day > 31) {
                throw new IllegalArgumentException("Invalid day: " + day + ". Day must be between 01 and 31.");
            }

            // Check if the day is valid for the given month and year
            if (!isValidDayForMonth(day, month, year)) {
                throw new IllegalArgumentException("Invalid date: " + date + ". Please provide a valid date.");
            }

            // Parse the date into a LocalDate
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);

            // Validate that the date is not in the past
            validateNotPastDate(parsedDate);

            return parsedDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: DD-MM-YYYY");
        }
    }

    private boolean isValidDayForMonth(int day, int month, int year) {
        switch (month) {
        case 2: // February
            if (isLeapYear(year)) {
                return day <= 29; // Leap year
            } else {
                return day <= 28; // Non-leap year
            }
        case 4:
        case 6:
        case 9:
        case 11: // Months with 30 days
            return day <= 30;
        default: // Months with 31 days
            return day <= 31;
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private void validateNotPastDate(LocalDate date) throws IllegalArgumentException {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The return date cannot be in the past. " +
                    "Please provide a valid future date.");
        }
    }
}
