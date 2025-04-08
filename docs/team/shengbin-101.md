# Sheng Bin - Project Portfolio Page

## Overview

BookKeeper is a Command Line Interface (CLI) library manager application for effective tracking of library loans and inventory. It allows users to manage books, loans, and notes efficiently through a set of commands.

### Summary of Contributions

#### **Code Contributed**
- [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=shengbin-101&breakdown=true)

#### **Features Implemented**
1. **`add-loan` command**:
    - **What it does**: Feature to add loans for books, specifying borrower details such as name, return date, phone number, and email.
    - **Justification**: This feature is essential for tracking which books are currently on loan and who borrowed them. It enhances the core functionality of the library management system.
    - **Highlights**: Includes validation to prevent adding loans for books that are already on loan.

2. **`Formatter` class**:
    - **What it does**: Provides a centralized utility for formatting output messages, such as error messages, success messages, and lists.
    - **Justification**: Improves the user experience by ensuring consistent and readable output across all commands.
    - **Highlights**: Supports formatting for book lists, loan lists, and error messages.

3. **Storage Validation**:
    - **What it does**: Validates the consistency of data between the `BookList` and `LoanList` during loading. Removes invalid loans and fixes discrepancies in book statuses (e.g., `onLoan` status).
    - **Justification**: Ensures data integrity when loading from files, preventing crashes and maintaining a consistent state.
    - **Highlights**: Handles invalid entries, duplicate books, invalid loans, and mismatched `onLoan` statuses.

4. **Dynamic Ordering of Arguments in `update-book` and `edit-loan`**:
    - **What it does**: Allows users to input command arguments in any order, as long as all required arguments are provided. For example, the command `edit-loan` accepts arguments like `BORROWER_NAME`, `RETURN_DATE`, `PHONE_NUMBER`, and `EMAIL` in any order.
    - **Justification**: This feature improves the user experience by making commands more flexible and forgiving. Users do not need to memorize a strict order for arguments, reducing the likelihood of input errors.
    - **Highlights**:
      - Implemented InputParser to dynamically map arguments based on prefixes (e.g., n/ for name, d/ for return date).
      - Includes checking of duplicate prefixes/arguments given by user.

#### **Contributions to the User Guide**
- Added documentation for `add-loan`, `delete-loan`, `edit-loan`, `view-loans` commands.
- Added sections for:
    - Data Validation
    - Notes on case sensitivity and unique books

#### **Contributions to the Developer Guide**
- Wrote the implementation details for:
    - Section on Adding Loans
    - Section on Search Title
    - Section on Save/Load Inventory and Loans
- Added Sequence Diagrams for:
    - Sequence Diagram for Adding Loans
    - Sequence Diagram for Save/Load Inventory
    - Sequence Diagram for Save/Load Loans

#### **Contributions to Team-Based Tasks**
- Maintained the issue tracker and managed milestones.
- Assisted in integrating features contributed by team members.
- Conducted testing for features implemented by other team members.

#### **Review/Mentoring Contributions**
- Reviewed and provided help on PRs:
    - [#67](https://github.com/AY2425S2-CS2113-T12-2/tp/pull/67)

---