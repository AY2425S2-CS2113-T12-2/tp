package bookkeeper;

import java.util.ArrayList;

public class LoanList {
    private ArrayList<Loan> loanList;
    private String listName;

    public LoanList(String listName) {
        this.listName = listName;
        this.loanList = new ArrayList<Loan>();
    }

    public String getlistName() {
        return listName;
    }

    public void addLoan(Loan loan) {
        loanList.add(loan);
    }

    public void deleteLoan(Loan loan, String bookTitle, String borrowerName) {
        //Add Implementation 
    }

    public void viewLoanList() {
        for (int i = 0; i < loanList.size(); i++) {
            System.out.println((i + 1) + ". " + loanList.get(i).toString());
        }
    }
}
