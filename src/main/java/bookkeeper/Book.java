package bookkeeper;

public class Book {
    private String title;
    private String author;
    private String category;
    private String condition;
    private boolean onLoan;

    public Book(String title, String author, String category, String condition) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.condition = condition;
        onLoan = false;
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

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }
}
