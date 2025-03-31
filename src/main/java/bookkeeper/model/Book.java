package bookkeeper.model;

public class Book {
    private String title;
    private String author;
    private String category;
    private Condition condition;
    private boolean onLoan;
    private String location;
    private String note;

    // Constructor with note
    public Book(String title, String author, String category, String condition, String location, String note) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.condition = Condition.fromString(condition);
        this.note = note;
        this.location = location;
        this.onLoan = false;
    }

    // Constructor with optional note
    public Book(String title, String author, String category, String condition, String location) {
        this(title, author, category, condition, location, ""); // Default note is an empty string
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

    public Condition getCondition() {
        return condition;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public String getLocation() {
        return location;
    }

    public String getNote() {
        return note;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCondition(String condition){
        this.condition = Condition.fromString(condition);
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toFileString() {
        String title = getTitle();
        String author = getAuthor();
        String category = getCategory();
        Condition condition = getCondition();
        boolean onLoan = isOnLoan();
        String location = getLocation();
        String note = getNote();
        return title + " | " + author + " | " + category +
                " | " + condition + " | " + onLoan +
                " | " + location + " | " + note;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + System.lineSeparator()
                + "    Author: " + getAuthor() + System.lineSeparator()
                + "    Category: " + getCategory() + System.lineSeparator()
                + "    Condition: " + getCondition() + System.lineSeparator()
                + "    On Loan: " + isOnLoan() + System.lineSeparator()
                + "    Location: " + getLocation() + System.lineSeparator()
                + "    Note: " + (note.isEmpty() ? "No notes available" : getNote());
    }
}
