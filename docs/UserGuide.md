# User Guide (v2.1)

## Table of Contents

- [User Guide (v2.1)](#user-guide-v21)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Quick Start](#quick-start)
  - [Features](#features)
    - [Adding a book: `add-book`](#adding-a-book-add-book)
      - [Adding a Title that already exists in inventory](#adding-a-title-that-already-exists-in-inventory)
    - [Removing a book: `remove-book`](#removing-a-book-remove-book)
    - [Updating a Book: `update-book`](#updating-a-book-update-book)
    - [Updating a Title: `update-title`](#updating-a-title-update-title)
    - [Searching for a Book: `search-book`](#searching-for-a-book-search-book)
    - [View Book Collection: `view-inventory`](#view-book-collection-view-inventory)
    - [List Category: `list-category`](#list-category-list-category)
    - [Adding a Loan: `add-loan`](#adding-a-loan-add-loan)
      - [Adding a Loan for a Book Already on Loan](#adding-a-loan-for-a-book-already-on-loan)
    - [Deleting a Loan: `delete-loan`](#deleting-a-loan-delete-loan)
    - [Editing a Loan: `edit-loan`](#editing-a-loan-edit-loan)
    - [View Current Loans: `view-loans`](#view-current-loans-view-loans)
    - [Displaying Help: `help`](#displaying-help-help)
    - [Exiting the program: `exit`](#exiting-the-program-exit)
  - [Persistent State](#persistent-state)
    - [Editing data file](#editing-data-file)
      - [CAUTION: Edits that make the data invalid can cause BookKeeper to behave in unexpected ways. Edit data files only if you are confident that you can update it correctly.](#caution-edits-that-make-the-data-invalid-can-cause-bookkeeper-to-behave-in-unexpected-ways-edit-data-files-only-if-you-are-confident-that-you-can-update-it-correctly)
    - [Data Validation](#data-validation)
    - [Notes:](#notes)
  - [Command Summary](#command-summary)

<div style="page-break-after: always;"></div>

## Introduction

BookKeeper is a Command Line Interface (CLI) library manager application for effective tracking of library loans and inventory.

---

## Quick Start

Welcome to BookKeeper! This guide will help you get started with using the system for managing your library's books and loan records.

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `BookKeeper` from [here](https://github.com/AY2425S2-CS2113-T12-2/tp/releases/tag/Release-v2.1).
3. Copy the file to the folder you want to use as the home folder for BookKeeper.
4. Open a command prompt/terminal and navigate to the folder where you placed the jar file.
5. Run the application using: `java -jar BookKeeper.jar`
6. Type a command in the command box and press Enter to execute it.
7. Follow the interactive prompts to complete the command.

---

## Features

This version of the system focuses on key functionalities for managing inventory and book loans. Below is a breakdown of the available features and commands:

### Adding a book: `add-book`

Adds a book to the library collection.

Format: `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`

Notes:

- Valid categories are: romance, adventure, action, horror, mystery, fiction, nonfiction, scifi, education.

Example:

```
add-book Great Gatsby a/Fitzgerald cat/Fiction cond/Good loc/Shelf B1
```

or

```
add-book Great Gatsby a/Fitzgerald cat/Fiction cond/Good loc/Shelf B1 note/important
```

<div style="page-break-after: always;"></div>

Expected Outcome:

```
New book added: Great Gatsby
```

#### Adding a Title that already exists in inventory

BookKeeper does not support multiple copies of books with the same title. If the the user attempts to add a duplicate title, the system will not permit the addition. You will see an error message indicating that the book already exists.

Example:
`Book already exists in inventory: Great Gatsby`

### Removing a book: `remove-book`

Removes a book from the library collection.

Format: `remove-book BOOK_TITLE`

Example:

```
remove-book Great Gatsby
```

Expected Outcome:

```
Removed book: Great Gatsby
```

### Updating a Book: `update-book`

Updates the author, category, condition, location and note with the information provided. While the 4 fields are optional, `update-book` expects at least one field to be updated.

Format: `update-book BOOK_TITLE [a/AUTHOR] [cat/CATEGORY] [cond/CONDITION] [loc/LOCATION] [note/NOTE]`

Example:

```
update-book Great Gatsby a/Fitzgerald cat/Fiction cond/POOR loc/B3 note/Replace ASAP
```

Expected Outcome:

```
Book Updated:
Title: Great Gatsby
    Author: Fitzgerald
    Category: Fiction
    Condition: POOR
    On Loan: false
    Location: Shelf B3
    Note: Replace ASAP
```

### Updating a Title: `update-title`

Updates the title of a book.

Format: `update-title BOOK_TITLE new/NEW_TITLE`

Example:

```
update-book Great Gatsby new/ The Great Gatsby
```

Expected Outcome:

```
Book Updated:
Title: The Great Gatsby
    Author: Fitzgerald
    Category: Fiction
    Condition: POOR
    On Loan: false
    Location: Shelf B3
    Note: Replace ASAP
```

### Searching for a Book: `search-book`

Search for a book in the inventory based on the keyword.

Format: `search-book KEYWORD`

Example:

```
search-book Great
```

Expected Outcome:

```
Here are the books in your inventory:
1. Title: Great Gatsby
    Author: Fitzgerald
    Category: Fiction
    Condition: GOOD
    On Loan: false
    Location: Shelf B1
    Note: No notes available
2.
    ...
```

### View Book Collection: `view-inventory`

View all books currently in the collection.

Format: `view-inventory`

Example:

```
view-inventory
```

Expected Outcome:

```
Here are the books in your inventory:
1. Title: Great Gatsby
    Author: Fitzgerald
    Category: Fiction
    Condition: GOOD
    On Loan: false
    Location: Shelf B1
    Note: No notes available

2. Title: Cheese Chronicles
    Author: Jerry
    ...
```

### List Category: `list-category`

List all books currently in the inventory that belong to the given category.

Format: `list-category CATEGORY`

Example:

```
list-category Fiction
```

Expected Outcome:

```
Here are the books in your inventory:
    1. Title: Great Gatsby
        Author: Fitzgerald
        Category: Fiction
        Condition: GOOD
        On Loan: false
        Location: Shelf B1
        Note: No notes available
```

### Adding a Loan: `add-loan`

Adds a loan using the book title.

Format: `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`

Notes:

- The RETURN_DATE must be in the format DD-MM-YYYY.
- The RETURN_DATE cannot be in the past.
- The PHONE_NUMBER will only accept numeric strings to cater for different countries phone number

Example:

```
add-loan Great Gatsby n/John Doe d/01-12-2025 p/98765432 e/abc123@gmail.com
```

Expected Output:

```
Loan added successfully for book: Great Gatsby
```

#### Adding a Loan for a Book Already on Loan

If the book is already on loan, the system will not allow adding another loan for the same book. You will see an error message indicating that the book is unavailable.

Example:
`Error: The book "Great Gatsby" is already on loan.`

### Deleting a Loan: `delete-loan`

Deletes a loan using the book title.

Format: `delete-loan BOOK_TITLE`

Example:

```
delete-loan Great Gatsby
```

Expected Outcome:

```
Loan deleted successfully for book: Great Gatsby
```

### Editing a Loan: `edit-loan`

Edits loan borrower name, return date of the loan, borrower's phone number and email. While the 4 fields are optional, `edit-loan` expects at least one field to be updated.

Format: `edit-loan BOOK_TITLE [n/BORROWER_NAME] [d/RETURN_DATE] [p/PHONE_NUMBER] [e/EMAIL]`

Notes:

- The RETURN_DATE must be in the format DD-MM-YYYY.
- The RETURN_DATE cannot be in the past.
- The PHONE_NUMBER will accept all numeric strings
- Blank Fields will not be updated

Example:

```
edit-loan Great Gatsby n/John Doe d/01-11-2025 p/91234567 e/123abc@gmail.com
```

Expected Outcome:

```
Loan Updated:
Title: Great Gatsby
    Borrower: John Doe
    Return Date: 01-11-2025
    Contact Number: 91234567
    Email: 123abc@gmail.com
```

### View Current Loans: `view-loans`

View all currently ongoing loans.

Format: `view-loans`

Example:

```
view-loans
```

Expected Outcome:

```
Here are the active loans:
1. Title: Great Gatsby
    Borrower: John Doe
    Return Date: 2023-12-01
    Contact Number: 98765432
    Email: abc123@gmail.com

2. Title: Cheese Chronicles
    Borrower: Gerald
    Return Date: 2043-11-04
  	...
```

### Displaying Help: `help`

Displays a list of all available commands and their formats.

Format: `help`

Example:

```
help
```

### Exiting the program: `exit`

Exits the program.

Format: `exit`

Example:

```
exit
```

Expected Outcome:

```
Exiting BookKeeper...
```

<div style="page-break-after: always;"></div>

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

`Great Gatsby | Fitzgerald | Fiction`

Warning Message:

`Invalid book entry skipped: Great Gatsby | Fitzgerald | Fiction`

### Notes:

- **Commands Are Case-Sensitive**: Ensure that commands and inputs (e.g., book titles, borrower names) match the exact case.
- **Books Are Unique**: Each book in the inventory is unique and identified by its title. Duplicate books are not allowed.
- **Input Character Limitations**: We guarantee support for the English keyboard only. Please do not use the character `|` in your inputs. 
- **Data Size/Length Limitations**: Inventory size, loan list length and no. of characters in user input should not exceed `2147483647` (>2 billion).
- **User Responsibility**: User is responsible for text between flags (demarcated by `/`), e.g. `" "` is considered a valid book title if user follows proper command syntax.

<div style="page-break-after: always;"></div>

## Command Summary

| Action         | Format                                                                                 |
| -------------- | -------------------------------------------------------------------------------------- |
| Add Book       | `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`    |
| Remove Book    | `remove-book BOOK_TITLE`                                                               |
| Update Book    | `update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]` |
| Search Book    | `search-book KEYWORD`                                                  |
| View Inventory | `view-inventory`                                                       |
| Add Note       | `add-note BOOK_TITLE note/NOTE`                                        |
| Update note    | `update-note BOOK_TITLE note/NOTE`                                     |
| Delete Note    | `delete-note BOOK_TITLE`                                               |
| List Category  | `list-category CATEGORY`                                               |
| Add Loan       | `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL` |
| Delete Loan    | `delete-loan BOOK_TITLE`                                               |
| Edit Loan      | `edit-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL` |
| View Loans     | `view-loans`                                                           |
| Display Help   | `help`                                                                 |
| Exit Program   | `exit`                                                                 |

