package bookkeeper;

import org.junit.jupiter.api.Test;

import bookkeeper.exceptions.IncorrectFormatException;
import bookkeeper.exceptions.ErrorMessages;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputParserTest {

    // extractAddBookArgs gives us 5 arguments: name, author, category, condition, note("" if not provided)
    @Test
    void extractAddBookArgs_validInput_fiveArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf 1");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 1", ""};
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

    // extractUpdateBookArgs gives us 5 arguments: name, author, category, condition, note("" if not provided)
    @Test
    void extractUpdateBookArgs_validInput_fiveArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractUpdateBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald cat/Fiction cond/Good");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", ""};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractUpdateBookArgs_inputWithExtraSpace_fiveArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractUpdateBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald    cat/Fiction cond/Good   ");
        String[] output = new String[]{"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", ""};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractUpdateBookArgs_missingAuthor_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractUpdateBookArgs("The Great Gatsby cat/Fiction cond/Good"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK,
                exception.getMessage());
    }

    @Test
    void extractUpdateBookArgs_missingBookName_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractUpdateBookArgs("a/F. Scott Fitzgerald cat/Fiction cond/Good"));
        assertEquals(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK,
                exception.getMessage());
    }

    @Test
    void extractAddLoanArgs_validInput_threeArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddLoanArgs("The Great Gatsby n/Mary d/13-Mar-2025" +
                " p/12345678 e/abc123@gmail.com");
        String[] output = new String[]{"The Great Gatsby", "Mary", "13-Mar-2025", "12345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddLoanArgs_inputWithExtraSpace_threeArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddLoanArgs("The Great Gatsby    n/Mary d/13-Mar-2025   " +
                "p/12345678  e/abc123@gmail.com");
        String[] output = new String[]{"The Great Gatsby", "Mary", "13-Mar-2025", "12345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddLoanArgs_missingDate_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractAddLoanArgs("The Great Gatsby n/Mary"));
        assertEquals(ErrorMessages.INVALID_FORMAT_ADD_LOAN, exception.getMessage());
    }

    @Test
    void extractCommandArgs_validInput_twoArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractCommandArgs("delete-loan The Great Gatsby n/Mary");
        String[] output = new String[]{"delete-loan", "The Great Gatsby n/Mary"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractDeleteLoanArgs_validInput_twoArgumentArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractDeleteLoanArgs("The Great Gatsby n/Mary");
        String[] output = new String[]{"The Great Gatsby", "Mary"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractDeleteLoanArgs_inputWithExtraSpace_twoArgumentArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractDeleteLoanArgs("The Great Gatsby   n/Mary  ");
        String[] output = new String[]{"The Great Gatsby", "Mary"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractDeleteLoanArgs_missingArguments_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractDeleteLoanArgs("The Great Gatsby"));
        assertEquals(ErrorMessages.INVALID_FORMAT_DELETE_LOAN, exception.getMessage());
    }

    @Test
    void extractEditLoanArgs_validInput_twoArgumentArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractEditLoanArgs("The Great Gatsby n/Mary d/13-Mar-2025 " +
                "p/12345678 e/abc123@gmail.com");
        String[] output = new String[]{"The Great Gatsby", "Mary", "13-Mar-2025", "12345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractEditLoanArgs_inputWithExtraSpace_twoArgumentArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractEditLoanArgs("The Great Gatsby    n/Mary d/13-Mar-2025     " +
                "p/12345678   e/abc123@gmail.com");
        String[] output = new String[]{"The Great Gatsby", "Mary", "13-Mar-2025", "12345678", "abc123@gmail.com"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractEditLoanArgs_missingArguments_exceptionThrown() {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
                -> InputParser.extractEditLoanArgs("The Great Gatsby"));
        assertEquals(ErrorMessages.INVALID_FORMAT_EDIT_LOAN, exception.getMessage());
    }
}

//    @Test
//    void extractCommandArgs_missingArguments_exceptionThrown() {
//        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, ()
//                        -> InputParser.extractCommandArgs("delete-loan "));
//        assertEquals("Invalid command format.\nExpected: COMMAND [ARGUMENTS]", exception.getMessage());
//    }
