package bookkeeper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private static final String FOLDER_PATH = "./data";
    private static final String FILE_PATH = FOLDER_PATH + "/bookKeeper.txt";

    /**
     * Saves the given loan list to the file.
     * Each loan is saved as a line in the file.
     *
     * @param loans The list of loans to save.
     */
    public static void saveLoans(LoanList loanList) {
        try {
            // Ensure the directory exists
            File directory = new File(FOLDER_PATH);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Create a FileWriter to write to the file
            FileWriter fw = new FileWriter(FILE_PATH);

            // Write each loan to the file
            ArrayList<Loan> loans = loanList.getLoanList();
            for (Loan loan : loans) {
                fw.write(loan.toFileString() + System.lineSeparator());
            }

            fw.close(); // Close the FileWriter to complete the writing process
        } catch (IOException e) {
            Formatter.printBorderedMessage("Something went wrong while saving tasks: " + e.getMessage());
        }
    }
}
