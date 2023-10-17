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

1. Download the latest `wedlog.jar` from [here](https://github.com/AY2324S1-CS2103T-F11-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your WedLog.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar wedlog.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window. 
Refer to the [Features](#features) below for details of each command.

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
### Adding a guest: `guest add`
Adds a guest to WedLog.

```text
guest add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [t/TAG...]
```
A guest must have the following tags: `n/NAME`

The following tags are optional: `p/PHONE_NUMBER e/EMAIL a/ADDRESS r/RSVP_STATUS d/DIETARY_REQUIREMENTS t/TAG...`

Acceptable values for `n/NAME`:
- Alphanumeric word with or without spaces

Acceptable values for `n/PHONE_NUMBER`:
- Numbers with no spaces or special characters

Acceptable values for `e/EMAIL`:
- `local-part@domain`
  - the `local-part` must:
    - contain alphanumeric characters and these special characters, excluding the parentheses (+_.-)
    - not start or end with any special characters
  - the `domain` must:
    - consist of domain labels separated by periods
    - end with a domain label at least 2 characters long
    - have each domain label start and end with alphanumeric characters
    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any

Acceptable values for `a/ADDRESS`:
- Word with or without spaces

Acceptable values for `r/RSVP_STATUS`:
- `yes`
- `no`
- `unknown`

Acceptable values for `d/DIETARY_REQUIREMENTS`:
- Word with or without spaces
- Inputs with only spaces signify no dietary requirements  

Acceptable values for `t/tag`:
- Alphanumeric word without spaces

>Tips:
><br>
>- Parameters can be in any order
><br>
>- A guest can have any number of tags (including 0)

Examples:
- `guest add n/Bob p/91234567 a/Blk 123 r/no`
- `guest add n/Keith p/92354567 d/ r/no`
- `guest add n/John Doe p/98765432 e/john@doe.com a/Street 456 r/unknown d/vegetarian t/friend`
- `guest add n/Jane Tan e/jane@example.com r/yes d/halal t/family t/bridesmaid`

Expected behaviour upon success:
- Adds the guest
- Displays a message showing the added guest.

Expected behaviour upon failure:
- No name specified: Displays error message “Please specify the guest’s name using the format `n/NAME`”
- Phone number format invalid: Displays error message “Please specify the guest’s phone number with only numbers with no spaces or special characters”.
- `r/` tag uses an invalid value: Displays error message “RSVP status can only be `yes`, `no` or `unknown`”.

--------------------------------------------------------------------------------------------------------------------
### Deleting a guest: `guest delete`
Deletes the specified guest from WedLog.

```text
guest delete INDEX
```

Acceptable values for `INDEX`:
- A positive integer

Examples:
- `guest delete 2` deletes the 2nd guest on the guest list
- `guest find Betsy` followed by `guest delete 1` deletes the 1st guest in the results of the `find` command

Expected behaviour upon success:
- Deletes the person at the specified `INDEX`
  - If the previous command was `guest filter KEY_WORDS`, the `INDEX` refers to the index number shown in the filtered guest list
  - Otherwise, the `INDEX` refers to the index number on the unfiltered guest list
- Displays a message telling user which guest has been deleted

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index.”
- Number does not correspond to any guest: Displays error message “The index you have provided does not correspond to any guest.”
- No input index: Displays error message “Please input an index.”

--------------------------------------------------------------------------------------------------------------------
### Adding a vendor : `vendor add`

Adds a vendor to WedLog.

```text
vendor add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG...]
```
A guest must have the following tags: `n/NAME`

The following tags are optional: `p/PHONE_NUMBER e/EMAIL a/ADDRESS r/RSVP_STATUS d/DIETARY_REQUIREMENTS t/TAG...`

Acceptable values for `n/NAME`:
- Alphanumeric word with or without spaces

Acceptable values for `n/PHONE_NUMBER`:
- Numbers with no spaces or special characters

Acceptable values for `e/EMAIL`:
- `local-part@domain`
  - the `local-part` must:
    - contain alphanumeric characters and these special characters, excluding the parentheses (+_.-)
    - not start or end with any special characters
  - the `domain` must:
    - consist of domain labels separated by periods
    - end with a domain label at least 2 characters long
    - have each domain label start and end with alphanumeric characters
    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any

Acceptable values for `a/ADDRESS`:
- Word with or without spaces

Acceptable values for `t/tag`:
- Alphanumeric word without spaces

>Tips:
><br>
>- Parameters can be in any order
   ><br>
>- A guest can have any number of tags (including 0)

Examples:
- `vendor add n/Betsy Crowe`
- `vendor add n/John Doe p/91234567`
- `vendor add n/John Doe p/91234567 e/johndflowers@email.com a/123 Flower Lane t/florist t/photographer`

Expected behaviour upon success:
- Adds a vendor to the vendor list
- Displays the vendor that has been added

Expected behaviour upon failure:
- No name: Displays error message "Please specify the vendor’s name using the format n/name."
- Phone number format invalid: Displays error message “Please specify the vendor’s phone number with only numbers with no spaces or special characters”.

--------------------------------------------------------------------------------------------------------------------
### Deleting a vendor : `vendor delete`
Deletes the specified vendor from WedLog.

```text
vendor delete INDEX
```

Acceptable values for INDEX
- The index **must be a positive integer** 1, 2, 3, …​

Examples:
- `vendor list` followed by `vendor delete 2` deletes the 2nd vendor in WedLog.

Expected behaviour upon success:
- Deletes the person at the specified `INDEX`. 
- The index refers to the index number shown in the displayed vendor list.

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message "Please input a positive integer as the index."
- Number does not correspond to any vendor: Displays error message "The number you have provided does not correspond to any vendor."
- No input number: Displays error message "Please input an index"

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
| Action                   | Format                                                                                                        | Example                                                                                       |
|--------------------------|:--------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| **Add a guest**          | `guest add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [t/TAG...]` | `guest add n/John Doe p/98765432 e/john@doe.com a/Street 456 r/unknown d/vegetarian t/friend` |
| **Delete a guest**       | `guest delete INDEX`                                                                                          | `guest delete 1`                                                                              |
| **Add a vendor**         | `vendor add n/NAME [p/PHONE_NUMBER]`                                                                          | `vendor add n/Betsy p/91234567`                                                               |
| **Delete a vendor**      | `vendor delete INDEX`                                                                                         | `vendor delete 2`                                                                             |
| **View all guests**      | `guest list`                                                                                                  |                                                                                               |
| **View specific guest**  | `guest view INDEX`                                                                                            | `guest view 1`                                                                                |
| **View all vendors**     | `vendor list`                                                                                                 |                                                                                               |
| **View specific vendor** | `vendor view INDEX`                                                                                           | `vendor view 1`                                                                               |

--------------------------------------------------------------------------------------------------------------------
## Appendix A: Miscellaneous error messages

User input is completely invalid (e.g. `abc` or `vsdf`):
- Display error message "No such command exists."

User input begins with `vendor` or `guest`, but does not include a valid command word (e.g. `vendor abc` or `guest adddd`):
- Display error message "Please specify a command."
