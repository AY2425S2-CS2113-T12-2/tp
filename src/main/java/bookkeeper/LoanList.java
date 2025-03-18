package bookkeeper;

import java.util.ArrayList;

public class LoanList {
    private ArrayList<Loan> loanList;
    private String listName;

    public LoanList(String listName) {
        this.listName = listName;
        this.loanList = new ArrayList<Loan>();
    }

    public ArrayList<Loan> getLoanList() {
        return loanList;
    }

    public String getlistName() {
        return listName;
    }

    public void addLoan(Loan loan) {
        assert loan != null : "Loan cannot be null";
        loanList.add(loan);
    }

    public void deleteLoan(Loan loan) {
        assert loan != null : "Loan cannot be null";
        loanList.remove(loan);
    }

    public void viewLoanList() {
        if (loanList.isEmpty()) {
            System.out.println("Loan List Empty!");
        }
        for (int i = 0; i < loanList.size(); i++) {
            System.out.println((i + 1) + ". " + loanList.get(i).toString());
        }
    }

    public Loan findLoan(Book book, String borrower) {
        for (int i = 0; i < loanList.size(); i++) {
            Book bookIter = loanList.get(i).getBook();
            String borrowerName = loanList.get(i).getBorrowerName();
            if ((bookIter.equals(book)) && (borrowerName.equals(borrower))) {
                return loanList.get(i);
            }
        }
        return null;
    }
}
