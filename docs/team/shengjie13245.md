# Sheng Jie - Project Portfolio Page

## Overview

BookKeeper is a Command Line Interface (CLI) library manager application for effective tracking of library loans and inventory. It allows users to manage books, loans, and notes efficiently through a set of commands.

## Summary of Contributions

### Code Contributed
[RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=shengjie13245&breakdown=true)

### Enhancement Implemented
1. Loan Deletion
    * **What it does**: Allows the user to delete loans that are currently saved.
    * **Justification**: This is a key feature in the product, as a librarian will constantly need to delete loans for books that have been returned.
    * **Highlights**: This enhancement work hand in hand with all other basic functions, thus requiring coordination to ensure that there is consistency in the implementation especially when parsing the input by the user.

2. Search-Book
    * **What it does**: Allows the user to get the list of all books that contains the provided keyword in its title.
    * **Justification**: This serves as a convenient way for the user to get a list of all the books that is in the inventory without having to look through the whole list. This is an important feature if there is a large amount of books. 

3. Listing Books by Category
    * **What it doesv: Allows the user to get the list of books that belongs to the provided category
    * **Justification**: Provides a convenient way for user to search for books when having a large invenotry of books.

4. Edit Loans
    * **What it does**: Allows the user to edit previously saved loans.
    * **Justification**: The features improves the products as it provides a convenient way for users to edit their previously saved loans without first deleting and adding a new loan.
    * **Highlights**: Ensures that the loan being edited it actually present and the book is actually on loan. Allows for optional inputs of the different fields which allows the user to only input the fields that is needed to be updatd

5. Save Loans
    * **What it does**: Automatically saves all loans to a txt whenever there is a change to the LoanList.
    * **Justification**: Removes the need for user to manually save the loans in order to add it whenever they start the program.
    * **Highlights**: Works hand in hand with load loans to provide convenience to the user. It required an analysis on how to loans should be saved as the book object cannot be saved in plain text.

6. Load Loans
    * **What it does**: Automaticallty loads all loans from a txt file into the program when it starts.
    * **ustification**: Removes the need for user to manually load the loans whenever they start the program.
    * **Highlights**: Works hand in hand with save loans to provide convenience to the user. The implementation was challenging as it require to ensure that the book object is the same as what is saved in the book list.

### Enhancement to existing features:
- Wrote JUnit tests for inputParser class.

### Contributions to the UG:
- Added documentation for the following features:
    - `delete-loan`
    - `search-book`
    - `list-category`
    - `add-note`
    - `delete-note`
    - `update-note`

## Contributions to the DG:
- Added documentation and UML diagram for the following sections:
    - `delete-loan`
    - `update-note`

## Contribution to team-based tasks:
- Maintained the issue tracker and managed milestones.
- Assisted in integrating features contributed by team members.
- Managed release for v1.0.
