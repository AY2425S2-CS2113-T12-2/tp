package bookkeeper.list;

import bookkeeper.model.Book;
import bookkeeper.model.Loan;
import bookkeeper.storage.LoggerConfig;
import bookkeeper.ui.Formatter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanList {
    private static final Logger logger = Logger.getLogger(LoanList.class.getName());
    private final ArrayList<Loan> loanList;
    private final String listName;

    public LoanList(String listName, ArrayList<Loan> loanList) {
        LoggerConfig.configureLogger(logger);
        this.listName = listName;
        this.loanList = loanList;
        logger.log(Level.INFO, "LoanList created with name: {0}", listName);
    }

    public ArrayList<Loan> getLoanList() {
        return loanList;
    }

    public String getListName() {
        return listName;
    }

    public void addLoan(Loan loan) {
        assert loan != null : "Loan cannot be null";
        loanList.add(loan);
        loan.getBook().setOnLoan(true);
        logger.log(Level.INFO, "Loan added: {0}", loan);
    }

    public void deleteLoan(Loan loan) {
        assert loan != null : "Loan cannot be null";
        if (loanList.remove(loan)) {
            loan.getBook().setOnLoan(false);
            logger.log(Level.INFO, "Loan removed: {0}", loan);
        } else {
            logger.log(Level.WARNING, "Attempted to remove a loan that does not exist: {0}", loan);
        }
    }

    public void viewLoanList() {
        if (loanList.isEmpty()) {
            Formatter.printBorderedMessage("Loan List Empty!");
            return;
        }
        Formatter.printLoanList(loanList);
    }

    public Loan findLoan(Book book) {
        for (Loan loan : loanList) {
            Book bookIter = loan.getBook();
            if (bookIter.equals(book)) {
                return loan;
            }
        }
        return null;
    }

    public Loan findLoanByIndex(int index) {
        try {
            return (loanList.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void removeLoansByBook(Book book) {
        assert book != null : "Book cannot be null";
        loanList.removeIf(loan -> loan.getBook().equals(book));
        logger.log(Level.INFO, "Removed all loans associated with book: {0}", book.getTitle());
    }
}
