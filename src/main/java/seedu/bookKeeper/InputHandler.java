package seedu.bookKeeper;

import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler {
    private ArrayList<Book> bookList;
    private ArrayList<Loan> loanList;

    public InputHandler() {
        this.bookList = new ArrayList<Book>();
        this.loanList = new ArrayList<Loan>();
    }

    public void askInput() {
        // Add Implementation
        boolean isAskingInput = true;
        String userInputLine;
        Scanner scanner = new Scanner(System.in);
    
        while (isAskingInput) {
            if (!scanner.hasNextLine()) {  // Prevents NoSuchElementException
                break;
            }
            userInputLine = scanner.nextLine();
            if (userInputLine.isEmpty()) {
                System.out.println("Please enter a command");
            } else {
                String[] commandArgs = InputParser.extractCommandArgs(userInputLine);
                switch (commandArgs[0]) {
                case "add-book":
                    break;
                case "view-inventory":
                    break;
                case "remove-book":
                    break;
                case "add-loan":
                    break;
                case "delete-loan":
                    break;
                case "view-loans":
                    break;
                default:
                    break;
                }
            }
        }   	
    }
}