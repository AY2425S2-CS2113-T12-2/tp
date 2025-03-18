package bookkeeper;

import org.junit.jupiter.api.Test;

import bookkeeper.exceptions.IncorrectFormatException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputParserTest {

    @Test
    void extractAddBookArgs_validInput_fourArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald cat/Fiction cond/Good");
        String[] output = new String[] {"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddBookArgs_inputWithExtraSpace_fourArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractAddBookArgs("The Great Gatsby " +
                "a/F. Scott Fitzgerald    cat/Fiction cond/Good   ");
        String[] output = new String[] {"The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddBookArgs_missingAuthor_exceptionThrown()  {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () 
        -> InputParser.extractAddBookArgs("The Great Gatsby cat/Fiction cond/Good"));
        assertEquals(exception.getMessage(), "Invalid format for add-book. " +
                "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION");
    }

    @Test
    void extractAddBookArgs_missingBookName_exceptionThrown()  {
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () 
                -> InputParser.extractAddBookArgs("a/F. Scott Fitzgerald cat/Fiction cond/Good"));
        assertEquals(exception.getMessage(), "Invalid format for add-book. " +
                "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION");
    }

    @Test
    void extractAddLoanArgs_validInput_threeArgumentStringArray() throws IncorrectFormatException{
        String[] arguments = InputParser.extractAddLoanArgs("The Great Gatsby n/Mary d/12-Mar-2025");
        String[] output = new String[] {"The Great Gatsby", "Mary", "12-Mar-2025"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddLoanArgs_inputWithExtraSpace_threeArgumentStringArray() throws IncorrectFormatException{
        String[] arguments = InputParser.extractAddLoanArgs("The Great Gatsby   n/Mary d/12-Mar-2025 ");
        String[] output = new String[] {"The Great Gatsby", "Mary", "12-Mar-2025"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractAddLoanArgs_missingDate_exceptionThrown(){
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () 
                -> InputParser.extractAddLoanArgs("The Great Gatsby n/Mary"));
        assertEquals(exception.getMessage(), "Invalid format for add-loan. " +
                "Expected format: add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE");
    }

    @Test
    void extractCommandArgs_validInput_twoArgumentStringArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractCommandArgs("delete-loan The Great Gatsby n/Mary");
        String[] output = new String[] {"delete-loan", "The Great Gatsby n/Mary"};
        assertArrayEquals(arguments, output);
    }

    @Test
    void extractCommandArgs_missingArguments_exceptionThrown() throws IncorrectFormatException {
        try {
            InputParser.extractCommandArgs("delete-loan ");
        } catch (IncorrectFormatException e) {
            assertEquals(e.getMessage(), "Invalid command format. Expected: COMMAND [ARGUMENTS]");
        }  
    }

    @Test
    void extractDeleteLoanArgs_validInput_twoArgumentArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractDeleteLoanArgs("The Great Gatsby n/Mary");
        String[] output = new String[] {"The Great Gatsby", "Mary"};
        assertArrayEquals(arguments, output);        
    }

    @Test
    void extractDeleteLoanArgs_inputWithExtraSpace_twoArgumentArray() throws IncorrectFormatException {
        String[] arguments = InputParser.extractDeleteLoanArgs("The Great Gatsby   n/Mary  ");
        String[] output = new String[] {"The Great Gatsby", "Mary"};
        assertArrayEquals(arguments, output);        
    }

    @Test
    void extractDeleteLoanArgs_missingArguments_exceptionThrown(){
        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () 
                -> InputParser.extractDeleteLoanArgs("The Great Gatsby"));
        assertEquals(exception.getMessage(), "Invalid format for delete-loan. " +
                        "Expected format: delete-loan BOOK_TITLE n/BORROWER_NAME");
    }
}
