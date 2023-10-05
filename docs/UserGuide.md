# WedLog User Guide


<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

--------------------------------------------------------------------------------------------------------------------
### Viewing all guests
View all guestss in a list format.

<span style="color:dodgerblue">guest</span> <span style="color:goldenrod">list</span>

Expected behaviour upon success:
- Displays a list of all guest names and their respective indexes. (Example: 1. Marcus Tan, 2. Jane Lim)

Expected behaviour upon failure:
<br>(refer to Appendix A : Expected behaviour upon general failure)

--------------------------------------------------------------------------------------------------------------------
### View a specific guest
View a specific guest using a specified index.

<span style="color:dodgerblue">guest</span> <span style="color:goldenrod">view</span> <span style="color:dodgerblue">INDEX</span>

Acceptable values for INDEX
- A positive integer

Examples:
<span style="color:dodgerblue">guest</span> <span style="color:goldenrod">view</span> <span style="color:dodgerblue">1</span>

Expected behaviour upon success:
- Displays a guest and all the information associated with it. (Example: 1. Marcus Tan)

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index.”
- Number does not correspond to any guest: Displays error message “The number you have provided does not correspond to any guest.”
- No input number : Displays error message “Please input an index”


--------------------------------------------------------------------------------------------------------------------
### Viewing all vendors
View all vendors in a list format.

<span style="color:dodgerblue">vendor</span> <span style="color:goldenrod">list</span>

Expected behaviour upon success:
- Displays a list of all vendor names and their respective indexes. (Example: 1. John FLORAL, 2. Sally Anne PHOTOGRAPHER)

Expected behaviour upon failure:
<br>(refer to Appendix A : Expected behaviour upon general failure)

--------------------------------------------------------------------------------------------------------------------
### View a specific vendor
View a specific vendor using a specified index.

<span style="color:dodgerblue">vendor</span> <span style="color:goldenrod">view</span> <span style="color:dodgerblue">INDEX</span>

Acceptable values for INDEX
- A positive integer

Examples:
<span style="color:dodgerblue">vendor</span> <span style="color:goldenrod">view</span> <span style="color:dodgerblue">1</span>

Expected behaviour upon success:
- Displays a vendor and all the information associated with it. (Example: 1. John FLORAL)

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index.”
- Number does not correspond to any vendor: Displays error message “The number you have provided does not correspond to any vendor.”
- No input number : Displays error message “Please input an index”


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                   | Format              | Example         |
|--------------------------|:--------------------|-----------------|
| **View all guests**      | `guest list`        |                 |
| **View specific guest**  | `guest view INDEX`  | `guest view 1`  |
| **View all vendors**     | `vendor list`       |                 |
| **View specific vendor** | `vendor view INDEX` | `vendor view 1` |
