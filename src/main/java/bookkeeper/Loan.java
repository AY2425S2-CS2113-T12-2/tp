package bookkeeper;

public class Loan {
    private Book book;
    private String loanDate;
    private String borrowerName;
    private String returnDate;

    public Loan(Book book, String borrowerName, String returnDate, String phoneNumber, String email) {
        this.book = book;
        this.returnDate = returnDate;
        this.borrowerName = borrowerName;
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

    public void setReturnDate(String loanDate) {
        this.returnDate = loanDate;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + System.lineSeparator()
                + "    Borrower: " + getBorrowerName() + System.lineSeparator()
                + "    Return Date: " + getReturnDate();
    }
}
