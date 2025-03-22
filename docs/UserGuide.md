# User Guide (v1.0)

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

Format: `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION`

Example:
`add-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good`

### Removing a book: `remove-book`
Removes a book to the library collection.

Format: `remove-book BOOK_TITLE`

Example:
`remove-book Moby Dick`

### View Book Collection: `view-inventory`
View all books currently in the collection.

Format: `view-inventory`

Example:
`view-inventory`

### Adding a Loan: `add-loan`
Adds a loan with the name of the book.

Format: `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE`

Example:
`add-loan The Great Gatsby n/John Doe d/2023-12-01`

### Deleting a Loan: `delete-loan`
Deletes a loan with the name of the book.

Format: `delete-loan BOOK_TITLE n/BORROWER_NAME`

Example:
`add-loan The Great Gatsby n/John Doe`

### View Current Loans: `view-loans`
View all currently ongoing loans.

Format: `view-loans`

Example: `view-loans`

## Command Summary
| Action         | Format                                                      | Examples                                                                 |
|----------------|-------------------------------------------------------------|--------------------------------------------------------------------------|
| Add a Book     | `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION`    | `add-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good`    |
| View Inventory | `view-inventory`                                              | `view-inventory`                                                           |
| Remove a Book  | `remove-book BOOK_TITLE`                                      | `remove-book Moby Dick`                                                    |
| Add a Loan     | `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE`           | `add-loan The Great Gatsby n/John Doe d/2023-12-01`                        |
| Delete a Loan  | `delete-loan BOOK_TITLE n/BORROWER_NAME`                      | `delete-loan The Great Gatsby n/John Doe`                                  |
| View Loans     | `view-loans`                                                  | `view-loans`                                                               |

