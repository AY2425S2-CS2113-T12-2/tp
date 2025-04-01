# User Guide (v2.0)

## Table of Contents

- [Introduction](#introduction)
- [Quick Start](#quick-start)
- [Features](#features)
  - [`add-book` - Adding a book](#adding-a-book-add-book)
  - [`remove-book` - Removing a book](#removing-a-book-remove-book)
  - [`update-book` - Updating a book's info](#updating-a-book-update-book)
  - [`search-book` - Searching for a book](#searching-for-a-book-search-book)
  - [`view-inventory` - Viewing the book collection](#view-book-collection-view-inventory)
  - [`add-note` - Adding a note to a book](#adding-a-note-add-note)
  - [`delete-note` - Deleting a note from a book](#deleting-a-note-delete-note)
  - [`list-category` - Listing books by category](#list-category-list-category)
  - [`add-loan` - Adding a book loan](#adding-a-loan-add-loan)
  - [`delete-loan` - Deleting a book loan](#deleting-a-loan-delete-loan)
  - [`edit-loan` - Editing a book loan](#editing-a-loan-edit-loan)
  - [`view-loans` - Viewing all current loans](#view-current-loans-view-loans)
  - [`help` - Displaying help](#displaying-help-help)
  - [`exit` - Exiting the program](#exiting-the-program-exit)
- [Persistent State](#persistent-state)
  - [Manual Editing of Data Files](#editing-data-file)
  - [Data Validation](#data-validation)
- [Notes](#notes)
- [Command Summary](#command-summary)

---

## Introduction

BookKeeper is a Command Line Interface (CLI) library manager application for effective tracking of library loans and inventory.

---

## Quick Start

Welcome to BookKeeper! This guide will help you get started with using the system for managing your library's books and loan records.

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `BookKeeper` from [here](https://github.com/AY2425S2-CS2113-T12-2/tp/releases/tag/Release-v1.0).

---

## Features

This version of the system focuses on key functionalities for managing inventory and book loans. Below is a breakdown of the available features and commands:

### Adding a book: `add-book`

Adds a book to the library collection.

Format: `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`

Example:

`add-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf B1`
<br>or<br>`add-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/Good loc/Shelf B1 note/important`

### Removing a book: `remove-book`

Removes a book from the library collection.

Format: `remove-book BOOK_TITLE`

Example:
`remove-book Moby Dick`

### Updating a Book: `update-book`

Updates the author, category, condition, location and note with the information provided.

Format: `update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`

Example: `update-book The Great Gatsby a/F. Scott Fitzgerald cat/Fiction cond/POOR loc/Shelf B3 note/Replace ASAP`

### Searching for a Book: `search-book`

Search for a book in the inventory based on the keyword.

Format: `search-book KEYWORD`

Example: `search-book Great`

### View Book Collection: `view-inventory`

View all books currently in the collection.

Format: `view-inventory`

Example:
`view-inventory`

### Adding a Note: `add-note`

Add a personal note to a book.

Format: `add-note BOOK_TITLE note/NOTE`

Example: `add-note The Great Gatsby note/Very good book`

### Deleting a Note: `delete-note`

Deletes a note currently attached to a book.

Format: `delete-note BOOK_TITLE`

Example: `delete-note The Great Gatsby`

### List Category: `list-category`

List all books currently in the inventory that belong to the given category.

Format: `list-category CATEGORY`

Example: `list-category Fiction`

### Adding a Loan: `add-loan`

Adds a loan using the book title.

Format: `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`

Example:
`add-loan The Great Gatsby n/John Doe d/2023-12-01 p/98765432 e/abc123@gmail.com`

#### Adding a Loan for a Book Already on Loan

If the book is already on loan, the system will not allow adding another loan for the same book. You will see an error message indicating that the book is unavailable.

Example:
`Error: The book "The Great Gatsby" is already on loan.`

### Deleting a Loan: `delete-loan`

Deletes a loan using the book title.

Format: `delete-loan BOOK_TITLE n/BORROWER_NAME`

Example:
`delete-loan The Great Gatsby n/John Doe`

### Editing a Loan: `edit-loan`

Edits loan return date of the loan, borrower's phone number and email.

Format: `edit-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`

Example: `edit-loan The Great Gatsby n/Mary d/15-March-2025 p/91234567 e/123abc@gmail.com`

### View Current Loans: `view-loans`

View all currently ongoing loans.

Format: `view-loans`

Example: `view-loans`

### Displaying Help: `help`

Displays a list of all available commands and their formats.

Format: `help`

Example:
`help`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

---

## Persistent State

BookKeeper data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually. When you close and start up BookKeeper again, BookKeeper will load all previous data automatically.

### Editing data file

BookKeeper data is saved automatically as .txt files.
Inventory data is stored at `[JAR file location]/data/bookKeeper_bookList.txt`.
Loan data is stored at `[JAR file location]/data/bookKeeper_loanList.txt`.
Advanced users are welcome to update data directly by editing these files.

#### CAUTION: Edits that make the data invalid can cause BookKeeper to behave in unexpected ways. Edit data files only if you are confident that you can update it correctly.

### Data Validation

BookKeeper validates data when loading from files. If invalid data is encountered (e.g., missing fields, incorrect formats), the system will skip the invalid entries and log a warning.

Example:
Invalid Entry in Inventory File:

`The Great Gatsby | F. Scott Fitzgerald | Fiction`

Warning Message:

`Invalid book entry skipped: The Great Gatsby | F. Scott Fitzgerald | Fiction`

### Notes:

- **Commands Are Case-Sensitive**: Ensure that commands and inputs (e.g., book titles, borrower names) match the exact case.
- **Books Are Unique**: Each book in the inventory is unique and identified by its title. Duplicate books are not allowed.

---

## Command Summary

| Action         | Format                                                                                 |
| -------------- | -------------------------------------------------------------------------------------- |
| Add Book       | `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`    |
| Remove Book    | `remove-book BOOK_TITLE`                                                               |
| Update Book    | `update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]` |
| Search Book    | `search-book KEYWORD`                                                                  |
| View Inventory | `view-inventory`                                                                       |
| Add Note       | `add-note BOOK_TITLE note/NOTE`                                                        |
| Delete Note    | `delete-note BOOK_TITLE`                                                               |
| List Category  | `list-category CATEGORY`                                                               |
| Add Loan       | `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`             |
| Delete Loan    | `delete-loan BOOK_TITLE n/BORROWER_NAME`                                               |
| Edit Loan      | `edit-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`            |
| View Loans     | `view-loans`                                                                           |
| Display Help   | `help`                                                                                 |
| Exit Program   | `exit`                                                                                 |
