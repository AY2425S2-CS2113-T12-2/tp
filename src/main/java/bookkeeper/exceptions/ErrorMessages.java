package bookkeeper.exceptions;

//class to store error messages as constants for easier editing
public final class ErrorMessages {
    public static final String INVALID_FORMAT_ADD_BOOK = "Invalid format for add-book.\n" +
            "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]";

    public static final String INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX = "Invalid format for add-book. " +
            "Duplicate prefixes! \n" +
            "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]";

    public static final String INVALID_FORMAT_UPDATE_BOOK = "Invalid format for update-book.\n" +
            "Expected format: update-book BOOK_TITLE [a/AUTHOR] [cat/CATEGORY] [cond/CONDITION] [loc/LOCATION] "
            + "[note/NOTE]";

    public static final String INVALID_FORMAT_UPDATE_BOOK_NO_UPDATES = "Invalid format for update-book.\n" +
            "No fields provided to update.";

    public static final String INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX = "Invalid format for update-book. " +
            "Duplicate prefixes! \n" +
            "Expected format: update-book BOOK_TITLE [a/AUTHOR] [cat/CATEGORY] [cond/CONDITION] [loc/LOCATION] " + 
            "[note/NOTE]";

    public static final String INVALID_FORMAT_UPDATE_TITLE = "Invalid format for update-title.\n" +
            "Expected format: update-title BOOK_TITLE new/NEW_TITLE";

    public static final String INVALID_FORMAT_UPDATE_TITLE_DUPLICATE_PREFIX = "Invalid format for update-title. " +
            "Duplicate prefixes! \n" +
            "Expected format: update-title BOOK_TITLE new/NEW_TITLE";

    public static final String INVALID_FORMAT_SAME_TITLE = "Invalid format for update-title.\n" +
            "Titles entered are the same.";

    public static final String INVALID_FORMAT_REMOVE_BOOK = "Invalid format for remove-book.\n" +
            "Expected format: remove-book BOOK_TITLE";

    public static final String INVALID_FORMAT_ADD_LOAN = "Invalid format for add-loan.\n" +
            "Expected format: add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL";

    public static final String INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX = "Invalid format for add-loan. " +
            "Duplicate prefixes! \n" +
            "Expected format: add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL";
            
    public static final String INVALID_PHONE_NUMBER_ADD_LOAN = "Invalid format for add-loan.\n" +
            "Invalid phone number";
            
    public static final String INVALID_EMAIL_ADD_LOAN = "Invalid format for add-loan.\n" +
            "Invalid email";

    public static final String INVALID_FORMAT_EDIT_LOAN = "Invalid format for edit-loan.\n" +
            "Expected format: edit-loan BOOK_TITLE [n/BORROWER_NAME] [d/RETURN_DATE] [p/PHONE_NUMBER] [e/EMAIL]";

    public static final String INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX = "Invalid format for edit-loan. " +
            "Duplicate prefixes! \n" +
            "Expected format: edit-loan BOOK_TITLE [n/BORROWER_NAME] [d/RETURN_DATE] [p/PHONE_NUMBER] " + 
            "[e/EMAIL]";

    public static final String INVALID_FORMAT_EDIT_LOAN_NO_EDITS = "Invalid format for edit-loan.\n" +
            "No fields provided for edits";
            
    public static final String INVALID_PHONE_NUMBER_EDIT_LOAN = "Invalid format for edit-loan.\n" +
            "Invalid phone number";

    public static final String INVALID_EMAIL_EDIT_LOAN = "Invalid format for edit-loan.\n" +
            "Invalid email";
            
    public static final String INVALID_FORMAT_DELETE_LOAN = "Invalid format for delete-loan.\n" +
            "Expected format: delete-loan BOOK_TITLE";

    public static final String INVALID_FORMAT_ADD_NOTE = "Invalid format for add-note.\n" +
            "Expected format: add-note BOOK_TITLE note/NOTE";

    public static final String INVALID_FORMAT_DELETE_NOTE = "Invalid format for delete-note.\n" +
            "Expected format: delete-note BOOK_TITLE";

    public static final String INVALID_FORMAT_SEARCH_BOOK = "Invalid format for search-book.\n" +
            "Expected format: search-book KEYWORD";

    public static final String INVALID_FORMAT_LIST_CATEGORY = "Invalid format for list-category.\n" +
            "Expected format: list-category CATEGORY";
    
    public static final String INVALID_FORMAT_UPDATE_NOTE = "Invalid format for update-note. \n" +
            "Expected format: update-note BOOK_TITLE note/NOTE";

    private ErrorMessages() {
    } //private constructor to prevent instantiation
}
