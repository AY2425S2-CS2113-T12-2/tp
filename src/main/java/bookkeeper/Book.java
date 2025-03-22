package bookkeeper;

public class Book {
    private String title;
    private String author;
    private String category;
    private String condition;
    private boolean onLoan;
    private String note;

    // Constructor with note
    public Book(String title, String author, String category, String condition, String note) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.condition = condition;
        this.note = note;
        this.onLoan = false;
    }

    // Constructor with optional note
    public Book(String title, String author, String category, String condition) {
        this(title, author, category, condition, ""); // Default note is an empty string
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public boolean getOnLoan() {
        return onLoan;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + System.lineSeparator()
                + "    Author: " + getAuthor() + System.lineSeparator()
                + "    Category: " + getCategory() + System.lineSeparator()
                + "    Condition: " + getCondition() + System.lineSeparator()
                + "    On Loan: " + getOnLoan() + System.lineSeparator()
                + "    Note: " + (note.isEmpty() ? "No notes available" : getNote());
    }
}
