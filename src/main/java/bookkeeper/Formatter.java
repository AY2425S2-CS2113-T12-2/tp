package bookkeeper;

import java.util.ArrayList;

public class Formatter {

    private static final int NORMAL_INDENT = 5;
    private static final int MINOR_INDENT = 4;

    /**
     * Prints a horizontal line with minor indentation.
     */
    public static void printLine() {
        System.out.println("____________________________________________________________________".indent(MINOR_INDENT));
    }

    /**
     * Prints a bordered message with normal indentation.
     *
     * @param message The message to print.
     */
    public static void printBorderedMessage(String message) {
        printLine();
        System.out.print(message.indent(NORMAL_INDENT));
        printLine();
    }

    /**
     * Prints a simple message with normal indentation.
     *
     * @param message The message to print.
     */
    public static void printSimpleMessage(String message) {
        System.out.print(message.indent(NORMAL_INDENT));
    }

    /**
     * Prints a list of books with normal indentation.
     *
     * @param books The list of books to print.
     */
    public static void printBookList(ArrayList<Book> books) {
        printLine();
        printSimpleMessage("Here are the books in your inventory:");
        int count = 0;
        for (Book book : books) {
            count += 1;
            printSimpleMessage(count + ". " + book.toString());
            System.out.println();
        }
        printLine();
    }

    /**
     * Prints a list of loans with normal indentation.
     *
     * @param loans The list of loans to print.
     */
    public static void printLoanList(ArrayList<Loan> loans) {
        printLine();
        printSimpleMessage("Here are the active loans:");
        int count = 0;
        for (Loan loan : loans) {
            count += 1;
            printSimpleMessage(count + ". " + loan.toString());
            System.out.println();
        }
        printLine();
    }
}
