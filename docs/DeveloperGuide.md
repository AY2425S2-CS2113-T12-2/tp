# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Setting up, getting started

## Design
{From course website: 
you may omit the Architecture section (no penalty)
if you have not organized the code into clearly divided components (no penalty if you didn't), you can use a single class diagram (if it is not too complicated) or use several class diagrams each describing a different area of the system.}

### Implementation
{TODO BY 02/04/2025: Each member should describe the implementation of at least one enhancement they have added (or planning to add).}

#### Adding Books/Loans

#### Removing Books/Loans

The `remove-book` feature allows the user to remove a book from the inventory using the book title as the identifier.
The system will first check if the book exists, remove all associated loans (if any) before finally removing the book from the inventory.
This prevents orphaned loan records from remaining in the system.

`InputHandler` coordinates with `BookList`, `LoanList` and `Formatter` classes to implement the feature.

The following UML sequence diagram shows how the `remove-book TITLE` command is handled.
![removeBook.png](images/removeBook.png)

1. User issues command
The user inputs the command in the CLI with the book title as an argument, e.g. `remove-book The Hobbit`

2. `InputHandler` handles the command
The `removeBook(commandArgs)` method of `InputHandler` is invoked to extract the book title from the arguments.

3. `BookList` is queried for the book
`InputHandler` calls `BookList.findBookByTitle(bookTitle)` to search for the book.
   - If the book is not found `(toRemove == null)`, `InputHandler` uses `Formatter` to print a "Book not found" message and exits early. 
   - If the book is found, the flow continues.

4. Associated loans are removed
`LoanList.removeLoansByBook(toRemove)` is called to remove all loans associated with the book. This ensures no orphaned loans are left behind.

5. Book is removed from the system
`InputHandler` calls `BookList.removeBook(toRemove)` to remove the book.

6. Success message is displayed
`Formatter` is used to print a message indicating successful removal.

#### Viewing Books/Loans

#### Updating Books/Loans


## Appendix A: Product scope

### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## Appendix B: User Stories

| Version | As a...       | I want to...                                                           | So that I can                                                       |
|---------|---------------|------------------------------------------------------------------------|---------------------------------------------------------------------|
| v1.0    | Librarian     | View inventory, including book count                                   | See my existing books                                               |
| v1.0    | Librarian     | Add new books to the system easily                                     | Update my inventory when acquiring new books                        |
| v1.0    | Librarian     | Remove books when lost or permanently borrowed                         | Maintain an accurate inventory                                      |
| v1.0    | Librarian     | Add book loans, including borrower details and return dates            | Ensure books are returned on time and inform others of availability |
| v1.0    | Librarian     | Delete book loans, including borrower details and return dates         | Maintain accurate loan records                                      |
| v1.0    | Librarian     | View on-going loans                                                    | Keep track of what books are loaned out                             |
| v2.0    | Librarian     | Categorize my inventory                                                | Make searching for books more convenient                            |
| v2.0    | Librarian     | Manage/Update book availability, including borrowed and reserved books | Efficiently allocate books                                          |
| v2.0    | Librarian     | Track book conditions (e.g., damages, special editions)                | Maintain detailed records                                           |
| v2.0    | Librarian     | Add personal notes about individual books                              | Maintain detailed records                                           |
| v2.0    | Librarian     | Edit existing book loans' due dates                                    | Better track by updating book loans                                 |
| v2.0    | Librarian     | Add contact details for borrowers                                      | Easily reach out to borrowers when needed                           |
| v2.0    | Librarian     | Keep track of where available books are in the library                 | Help visitors find books                                            |
| v2.0    | New Librarian | View a list of available commands                                      | Learn how to use the application                                    |

## Appendix C: Non-Functional Requirements

{Give non-functional requirements}

## Appendix D: Glossary

* *glossary item* - Definition

## Appendix E: Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
