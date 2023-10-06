---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

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

<box type="info" seamless>

**Notes about the command format:** <br />

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

--------------------------------------------------------------------------------------------------------------------
### Viewing help : `help`

Shows a message explaining how to access the help page.

```text
help
```

Expected behaviour upon success:
* Displays a link to the help page

### Adding a vendor : `vendor add`

Adds a vendor to WedLog.

```text
vendor add n/NAME [p/PHONE_NUMBER]
```

Acceptable values for PHONE_NUMBER:
* Numbers with no spaces or special characters

Examples:
* `vendor add n/Betsy Crowe`
* `vendor add n/John Doe Floral p/91234567`

Expected behaviour upon success:
* Adds a vendor to the vendor list
* Displays the vendor that has been added

Expected behaviour upon failure:
* No name: Displays error message "Please specify the vendor’s name using the format n/name."
* Phone number format invalid: Displays error message “Please specify the vendor’s phone number with only numbers with no spaces or special characters”.

--------------------------------------------------------------------------------------------------------------------
### Deleting a vendor : `vendor delete`

Deletes the specified vendor from WedLog.

```text
vendor delete INDEX
```

Acceptable values for INDEX
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `vendor list` followed by `vendor delete 2` deletes the 2nd vendor in WedLog.

Expected behaviour upon success:
* Deletes the person at the specified `INDEX`. 
* The index refers to the index number shown in the displayed vendor list.

Expected behaviour upon failure:
* Number out of index range, not a number, or no number: Displays error message "Please input a positive integer as the index."
* Number does not correspond to any vendor: Displays error message "The number you have provided does not correspond to any vendor." 
* No input number: Displays error message "Please input an index"

--------------------------------------------------------------------------------------------------------------------
### Viewing all guests: `guest list`
View all guests in a list format.

```text
guest list
```

Expected behaviour upon success:
- Displays a list of all guest names and their respective indexes. (Example: 1. Marcus Tan, 2. Jane Lim)

Expected behaviour upon failure:
<br>(refer to Appendix A: Expected behaviour upon general failure)

--------------------------------------------------------------------------------------------------------------------
### Viewing a specific guest: `guest view`
View a specific guest using a specified index.

```text
guest view INDEX
```

Acceptable values for INDEX
- A positive integer

Examples:
`guest view 1`

Expected behaviour upon success:
- Displays a guest and all the information associated with it. (Example: 1. Marcus Tan)

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index.”
- Number does not correspond to any guest: Displays error message “The number you have provided does not correspond to any guest.”
- No input number: Displays error message “Please input an index”

--------------------------------------------------------------------------------------------------------------------
### Viewing all vendors: `vendor list`
View all vendors in a list format.

```text
vendor list
```

Expected behaviour upon success:
- Displays a list of all vendor names and their respective indexes. (Example: 1. John FLORAL, 2. Sally Anne PHOTOGRAPHER)

Expected behaviour upon failure:
<br>(refer to Appendix A: Expected behaviour upon general failure)

--------------------------------------------------------------------------------------------------------------------
### Viewing a specific vendor: `vendor view`
View a specific vendor using a specified index.

```text
vendor view INDEX
```

Acceptable values for INDEX
- A positive integer

Examples:
`vendor view 1`

Expected behaviour upon success:
- Displays a vendor and all the information associated with it. (Example: 1. John FLORAL)

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index.”
- Number does not correspond to any vendor: Displays error message “The number you have provided does not correspond to any vendor.”
- No input number: Displays error message “Please input an index”

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                   | Format                               | Example                         |
|--------------------------|:-------------------------------------|---------------------------------|
| **View help**            | `help`                               |                                 |
| **Add a vendor**         | `vendor add n/NAME [p/PHONE_NUMBER]` | `vendor add n/Betsy p/91234567` |
| **Delete a vendor**      | `vendor delete INDEX`                | `vendor delete 2`               |
| **View all guests**      | `guest list`                         |                                 |
| **View specific guest**  | `guest view INDEX`                   | `guest view 1`                  |
| **View all vendors**     | `vendor list`                        |                                 |
| **View specific vendor** | `vendor view INDEX`                  | `vendor view 1`                 |
