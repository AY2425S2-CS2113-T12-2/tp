# User Guide (v2.0)

## Introduction
BookKeeper is a Command Line Interface (CLI) library manager application for effective tracking of library loans and inventory.

## Quick Start

Welcome to BookKeeper! This guide will help you get started with using the system for managing your library's books and loan records.

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `BookKeeper` from [here](https://github.com/AY2425S2-CS2113-T12-2/tp/releases/tag/Release-v1.0).

## Features 
This version of the system focuses on key functionalities for managing inventory and book loans. Below is a breakdown of the available features and commands:

### Adding a book: `add-book`
Adds a book to the library collection.

Format: `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`

Example:
`add-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf B1` <br> `add-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf B1 note/important`

### Removing a book: `remove-book`
Removes a book from the library collection.

Format: `remove-book BOOK_TITLE`

Example:
`remove-book Moby Dick`

### Update Book `update-book`
Updates the author, category, condition, location and note with the information provided.

Format: `update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`

Example: `update-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/POOR loc/Shelf B3 note/Replace ASAP`

### Search Book `search-book`
Search for a book in the inventory based on the keyword.

Format: `search-book KEYWORD`

Example: `search-book Great`

### View Book Collection: `view-inventory`
View all books currently in the collection.

Format: `view-inventory`

Example:
`view-inventory`

### Add Note: `add-note`
Add a personal note to a book.

Format: `add-note BOOK_TITLE note/NOTE`

Example: `add-note The Great Gatsby note/Very good book`

### Delete Note `delete-note`
Deletes a note currently attached to a book.

Format: `delete-note BOOK_TITLE`

Example: `delete-note The Great Gatsby`

### List Category `list-category`
List all books currently in the inventory that is of the given category.

Format: `list-category CATEGORY`

Example: `list-category Fiction`

### Adding a Loan: `add-loan`
Adds a loan using the book title.

Format: `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`

Example:
`add-loan The Great Gatsby n/John Doe d/2023-12-01 p/98765432 e/abc123@gmail.com`

### Deleting a Loan: `delete-loan`
Deletes a loan using the book title.

Format: `delete-loan BOOK_TITLE n/BORROWER_NAME`

Example:
`add-loan The Great Gatsby n/John Doe`

### Edit Loan `edit-loan` 
Edits loan return date of the loan, borrower's phone number and email.

Format: `edit-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`

Example: `edit-loan The Great Gatsby n/Mary d/15-March-2025 p/91234567 e/123abc@gmail.com`

### View Current Loans: `view-loans`
View all currently ongoing loans.

Format: `view-loans`

Example: `view-loans`

### Exiting the program : `exit`
Exits the program.

Format: `exit`

### Saving data
BookKeeper data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing data file
BookKeeper data is saved automatically as .txt files. 
Inventory data is stored at `[JAR file location]/data/bookKeeper_bookList.txt`.
Loan data is stored at `[JAR file location]/data/bookKeeper_loanList.txt`.
Advanced users are welcome to update data directly by editing these files.

#### CAUTION: Edits that make the data invalid can cause BookKeeper to behave in unexpected ways. Edit data files only if you are confident that you can update it correctly.

## Command Summary
| Action         | Format                                                                                 |
|----------------|----------------------------------------------------------------------------------------|
| Add Book       | `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`    |
| Remove Book    | `remove-book BOOK_TITLE`                                                               |
| Update Book    | `update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]` |
| Search Book    | `search-book KEYWORD`                                                                  |
| View Inventory | `view-inventory`                                                                       |
| Add note       | `add-note BOOK_TITLE note/NOTE`                                                        |
| Delete note    | `delete-note BOOK_TITLE`                                                               |
| List Category  | `list-category CATEGORY`                                                               |
| Add Loan       | `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`             |
| Delete Loan    | `delete-loan BOOK_TITLE n/BORROWER_NAME`                                               |
| Edit Loan      | `edit-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`            |
| View Loans     | `view-loans`                                                                           |
| Exit Program   | `exit`                                                                                 |


