## Lock Hian - Project Portfolio Page

### Overview

BookKeeper is a Command Line Interface (CLI) library manager application for effective tracking of library loans and inventory. It allows users to manage books, loans, and notes efficiently through a set of commands.

### Summary of Contributions

#### Code contributed: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=phua-lock-hian&breakdown=true)

#### Enhancements Implemented
1. **`remove-book` command**
    * **What it does:** Allows the user to remove books from the inventory.
    * **Justification:** This feature is essential as the app should provide a convenient way to remove books from the inventory.
    * **Highlights:** Provides a straightforward command for users to maintain the accuracy of the inventory.

2. **`help` command**
    * **What it does:** Display a command summary table when the user inputs `help` into the CLI.
    * **Justification:** This feature is essential as the app should provide a convenient way for the user to get help on its usage.
    * **Highlights:** Enhances user experience by providing an easy way to access command information.

3. **Save inventory**
    * **What it does:** Saves the inventory automatically to persistent storage whenever there is a change.
    * **Justification:** This feature is essential as it ensures that the inventory data is always up to date and not lost between sessions.
    * **Highlights:** This enhancement affects existing commands and commands to be added in the future. Ensures data integrity across sessions.
   
4. **Load inventory**
    * **What it does:** Load inventory automatically from persistent storage every time the program begins.
    * **Justification:** This feature ensures that the user always starts with the most recent inventory data.
    * **Highlights:** This enhancement affects existing commands and commands to be added in the future. Ensures that users do not need to manually load data.
   
5. **`update-note` command**
    * **What it does:** Allows user to update note for a particular book.
    * **Justification:** Requested from user feedback through PE-D.
    * **Highlights:** Increases user convenience in updating notes.

6. **Enhancements to existing features**:
    * Consolidated error messages to constants
    * Updated existing features to support new attribute `location` of Book.
    * Wrote JUnit tests for BookList and updated tests in other classes to reflect expected behaviour when new features are added.

### Contributions to User Guide
- Wrote the Introduction and Quick Start sections to help new users get started quickly. 
- Added documentation for features:
    - `remove-book` - Provides detailed usage instructions for removing books.
    - `view-loans` - Explains how users can view current loans.
    - Data storage - User-facing documentation on how data is saved and loaded.
- Added notes on user input constraints and user responsibility after group discussion and agreement.

### Contributions to Developer Guide
- Wrote Appendix A of developer guide.
- Added implementation details and UML sequence diagrams for:
  - `remove-book`
  - `saveInventory`
  - `loadInventory`
- Applied changes spanning across all UML diagrams based on feedback and group discussion.

### Contributions to Team-Based Tasks
- Maintained the issue tracker and managed milestones.
- Assisted in integrating features contributed by team members.
- Set up Developer Guide and User Guide structure.
- Documented target user profile and user stories in Developer Guide.