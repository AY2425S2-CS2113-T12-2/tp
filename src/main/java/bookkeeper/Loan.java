package bookkeeper;

public class Loan {
    private Book book;
    private String borrowerName;
    private String returnDate;
    private String phoneNumber;
    private String email;

    public Loan(Book book, String borrowerName, String returnDate, String phoneNumber, String email) {
        this.book = book;
        this.returnDate = returnDate;
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

    public String getReturnDate() {
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

    public void setReturnDate(String loanDate) {
        this.returnDate = loanDate;
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
        String returnDate = getReturnDate();
        String email = getEmail();
        return title + " | " + borrowerName + " | " + contactNumber + 
                " | " + returnDate + " | " + email;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + System.lineSeparator()
                + "    Borrower: " + getBorrowerName() + System.lineSeparator()
                + "    Return Date: " + getReturnDate() + System.lineSeparator()
                + "    Contact Number: " + getPhoneNumber() + System.lineSeparator()
                + "    Email: " + getEmail();
    }
}
