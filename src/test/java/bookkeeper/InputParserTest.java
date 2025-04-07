package bookkeeper;

import bookkeeper.logic.InputParser;
import org.junit.jupiter.api.Test;

import bookkeeper.exceptions.IncorrectFormatException;
import bookkeeper.exceptions.InvalidArgumentException;
import bookkeeper.exceptions.ErrorMessages;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputParserTest {

    // extractAddBookArgs gives us 5 arguments: name, author, category, condition, note("" if not provided)
    @Test
    void extractAddBookArgs_validInput_sixArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf 1 note/Amazing Book");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 1", 
                "Amazing Book"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddBookArgs_inputWithExtraSpace_fiveArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald    cat/Fiction cond/Good   loc/Shelf 2    ");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 2", ""};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddBookArgs_missingAuthor_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractAddBookArgs("The Great Gatsby cat/Fiction cond/Good"));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_BOOK,
                exception.getMessage());
    }

    @Test
    void extractAddBookArgs_missingBookName_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractAddBookArgs("a/F. Scott Fitzgerald cat/Fiction cond/Good"));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_BOOK,
                exception.getMessage());
    }

    // extractUpdateBookArgs gives us 5 arguments: name, author, category, condition, location, note("" if not provided)
    @Test
    void extractUpdateBookArgs_validInput_fiveArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractUpdateBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf 2");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 2", null};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractUpdateBookArgs_inputWithExtraSpace_fiveArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractUpdateBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald    cat/Fiction cond/Good loc/Shelf 2  ");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 2", null};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractUpdateBookArgs_missingBookName_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractUpdateBookArgs("a/F. Scott Fitzgerald cat/Fiction cond/Good"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK,
                exception.getMessage());
    }

    @Test
    void extractAddLoanArgs_validInput_threeArgumentStringArray() throws IncorrectFormatException, 
            InvalidArgumentException {
        String[] arguments = InputParser.extractAddLoanArgs("The Great Gatsby n/Mary d/13-Mar-2025" +
                " p/62345678 e/abc123@gmail.com");
        String[] output = new String[]{"The Great Gatsby", "Mary", "13-Mar-2025", "62345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddLoanArgs_inputWithExtraSpace_threeArgumentStringArray() throws IncorrectFormatException, 
            InvalidArgumentException {
        String[] arguments = InputParser.extractAddLoanArgs("The Great Gatsby    n/Mary d/13-Mar-2025   " +
                "p/92345678  e/abc123@gmail.com");
        String[] output = new String[]{"The Great Gatsby", "Mary", "13-Mar-2025", "92345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddLoanArgs_missingDate_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractAddLoanArgs("The Great Gatsby n/Mary"));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_LOAN, exception.getMessage());
    }

    @Test
    void extractAddLoanArgs_emptyInput_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddLoanArgs("   "));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_LOAN, exception.getMessage());
    }

    @Test
    void extractCommandArgs_validInput_twoArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractCommandArgs("delete-loan The Great Gatsby n/Mary");
        String[] output = new String[]{"delete-loan", "The Great Gatsby n/Mary"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractEditLoanArgs_validInput_twoArgumentArray() throws IncorrectFormatException, InvalidArgumentException {
        String[] arguments = InputParser.extractEditLoanArgs("Great Gatsby n/Mary d/13-03-2025 " +
                "p/62345678 e/abc123@gmail.com");
        String[] output = new String[]{"Great Gatsby", "Mary", "13-03-2025", "62345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractEditLoanArgs_inputWithExtraSpace_twoArgumentArray() throws IncorrectFormatException, 
            InvalidArgumentException {
        String[] arguments = InputParser.extractEditLoanArgs("Great Gatsby    n/Mary d/13-03-2025     " +
                "p/92345678   e/abc123@gmail.com");
        String[] output = new String[]{"Great Gatsby", "Mary", "13-03-2025", "92345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddBookArgs_validInput_success() throws IncorrectFormatException {
        String input = "The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf 1 note/Classic novel";
        String[] result = InputParser.extractAddBookArgs(input);
        String[] expected = {"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 1", "Classic novel"};
        assertArrayEquals(expected, result);
    }

    @Test
    void extractAddBookArgs_validInputWithExtraSpaces_success() throws IncorrectFormatException {
        String input = "The Great Gatsby   a/  F. Scott Fitzgerald   cat/ Fiction   cond/  Good loc/  Shelf 1 note/" +
                "  Classic novel";
        String[] result = InputParser.extractAddBookArgs(input);
        String[] expected = {"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 1", "Classic novel"};
        assertArrayEquals(expected, result);
    }

    @Test
    void extractAddBookArgs_missingFields_exceptionThrown() {
        String input = "The Great Gatsby a/F. Scott Fitzgerald loc/Shelf 1";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddBookArgs(input));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_BOOK, exception.getMessage());
    }

    @Test
    void extractAddBookArgs_duplicatePrefix_exceptionThrown() {
        String input = "The Great Gatsby a/F. Scott Fitzgerald a/Another Author cat/Fiction cond/Good loc/Shelf 1";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddBookArgs(input));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX, exception.getMessage());
    }

    @Test
    void extractUpdateBookArgs_validInput_success() throws IncorrectFormatException {
        String input = "The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf 1 note/Classic novel";
        String[] result = InputParser.extractUpdateBookArgs(input);
        String[] expected = {"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 1", "Classic novel"};
        assertArrayEquals(expected, result);
    }

    @Test
    void extractAddLoanArgs_validInput_success() throws IncorrectFormatException, InvalidArgumentException {
        String input = "The Great Gatsby n/John Doe d/2023-12-01 p/82345678 e/johndoe@example.com";
        String[] result = InputParser.extractAddLoanArgs(input);
        String[] expected = {"The Great Gatsby", "John Doe", "2023-12-01", "82345678", "johndoe@example.com"};
        assertArrayEquals(expected, result);
    }

    @Test
    void extractAddLoanArgs_missingFields_exceptionThrown() {
        String input = "The Great Gatsby n/John Doe d/2023-12-01 p/92345678";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_LOAN, exception.getMessage());
    }

    @Test
    void extractAddLoanArgs_invalidPhoneNumber_exceptionThrown() {
        String input = "The Great Gatsby n/John Doe d/2023-12-01 p/9@3!#49 e/johndoe@example.com";
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, 
                () -> InputParser.extractAddLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_PHONE_NUMBER_ADD_LOAN, exception.getMessage());
    }

    @Test
    void extractAddLoanArgs_invalidEmail_exceptionThrown() {
        String input = "The Great Gatsby n/John Doe d/2023-12-01 p/91222999 e/johndoeexample.com";
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, 
                () -> InputParser.extractAddLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_EMAIL_ADD_LOAN, exception.getMessage());
    }

    @Test
    void extractAddLoanArgs_duplicatePrefix_exceptionThrown() {
        String input = "The Great Gatsby n/John Doe n/Another Borrower d/2023-12-01 p/1234567890 e/johndoe@example.com";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX, exception.getMessage());
    }

    @Test
    void extractEditLoanArgs_validInput_success() throws IncorrectFormatException, InvalidArgumentException {
        String input = "1 n/John Doe d/2023-12-01 p/82345678 e/johndoe@example.com";
        String[] result = InputParser.extractEditLoanArgs(input);
        String[] expected = {"1", "John Doe", "2023-12-01", "82345678", "johndoe@example.com"};
        assertArrayEquals(expected, result);
    }

    @Test
    void extractEditLoanArgs_missingFields_sucessWithoutMissingFields() throws IncorrectFormatException, 
            InvalidArgumentException {
        String input = "1 n/John Doe d/2023-12-01 e/johndoe@example.com";
        String[] result = InputParser.extractEditLoanArgs(input);
        String[] expected = {"1", "John Doe", "2023-12-01", null, "johndoe@example.com"};
        assertArrayEquals(expected, result);
    }

    @Test
    void extractEditLoanArgs_invalidPhoneNumber_exceptionThrown() {
        String input = "1 n/John Doe d/2023-12-01 p/9@3!#49 e/johndoe@example.com";
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, 
                () -> InputParser.extractEditLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_PHONE_NUMBER_EDIT_LOAN, exception.getMessage());
    }

    @Test
    void extractEditLoanArgs_invalidEmail_exceptionThrown() {
        String input = "1 n/John Doe d/2023-12-01 p/91222999 e/johndoeexample.com";
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, 
                () -> InputParser.extractEditLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_EMAIL_EDIT_LOAN, exception.getMessage());
    }

    @Test
    void extractEditLoanArgs_duplicatePrefix_exceptionThrown() {
        String input = "1 n/John Doe n/Another Borrower d/2023-12-01 p/1234567890 e/johndoe@example.com";
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractEditLoanArgs(input));
        assertEquals(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX, exception.getMessage());
    }

    @Test
    void extractUpdateTitleArgs_validInput_success() throws IncorrectFormatException {
        String input = "Cheese Chronicles new/Cheese Adventures";
        String[] expected = {"Cheese Chronicles", "Cheese Adventures"};
        String[] result = InputParser.extractUpdateTitleArgs(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void extractUpdateTitleArgs_validInputExtraSpaces_success() throws IncorrectFormatException {
        String input = "   Cheese Chronicles    new/   Cheese Adventures   ";
        String[] expected = {"Cheese Chronicles", "Cheese Adventures"};
        String[] result = InputParser.extractUpdateTitleArgs(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void extractUpdateTitleArgs_missingNewTitle_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractUpdateTitleArgs("Cheese Chronicles"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_TITLE, exception.getMessage());
    }

    @Test
    void extractUpdateTitleArgs_missingOldTitle_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractUpdateTitleArgs("new/Cheese Adventures"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_TITLE, exception.getMessage());
    }

    @Test
    void extractUpdateTitleArgs_duplicatePrefix_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractUpdateTitleArgs("Cheese Chronicles new/Cheese Adventures new/Extra"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_TITLE_DUPLICATE_PREFIX, exception.getMessage());
    }

    @Test
    void extractAddNoteArgs_validInput_success() throws IncorrectFormatException {
        String input = "The Great Gatsby note/A classic novel";
        String[] expected = {"The Great Gatsby", "A classic novel"};
        String[] result = InputParser.extractAddNoteArgs(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void extractAddNoteArgs_validInputExtraSpaces_success() throws IncorrectFormatException {
        String input = "   The Great Gatsby   note/   A classic novel   ";
        String[] expected = {"The Great Gatsby", "A classic novel"};
        String[] result = InputParser.extractAddNoteArgs(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void extractAddNoteArgs_missingNote_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddNoteArgs("The Great Gatsby"));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_NOTE, exception.getMessage());
    }

    @Test
    void extractAddNoteArgs_blankNote_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddNoteArgs("The Great Gatsby note/   "));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_NOTE, exception.getMessage());
    }

    @Test
    void extractAddNoteArgs_blankTitle_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractAddNoteArgs("  note/A classic novel"));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_NOTE, exception.getMessage());
    }

    @Test
    void extractUpdateNoteArgs_validInput_success() throws IncorrectFormatException {
        String input = "The Great Gatsby note/Updated note";
        String[] expected = {"The Great Gatsby", "Updated note"};
        String[] result = InputParser.extractUpdateNoteArgs(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void extractUpdateNoteArgs_validInputExtraSpaces_success() throws IncorrectFormatException {
        String input = "   The Great Gatsby   note/   Updated note   ";
        String[] expected = {"The Great Gatsby", "Updated note"};
        String[] result = InputParser.extractUpdateNoteArgs(input);
        assertArrayEquals(expected, result);
    }

    @Test
    void extractUpdateNoteArgs_missingNote_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractUpdateNoteArgs("The Great Gatsby"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_NOTE, exception.getMessage());
    }

    @Test
    void extractUpdateNoteArgs_blankNote_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractUpdateNoteArgs("The Great Gatsby note/   "));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_NOTE, exception.getMessage());
    }

    @Test
    void extractUpdateNoteArgs_blankTitle_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () ->
                InputParser.extractUpdateNoteArgs(" note/Updated note"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_NOTE, exception.getMessage());
    }
}
