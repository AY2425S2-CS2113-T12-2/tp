package bookkeeper;

public class Loan {
    private Book book;
    private String loanDate;
    private String borrowerName;

    public Loan(Book book, String loanDate, String borrowerName) {
        this.book = book;
        this.loanDate = loanDate;
        this.borrowerName = borrowerName;
    }

    public Book getBook() {
        return book;
    }

    public String getTitle() {
        return book.getTitle();
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + System.lineSeparator()
                + "    Borrower: " + getBorrowerName() + System.lineSeparator()
                + "    Return Date: " + getLoanDate();
    }
}
