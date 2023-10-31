---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# WedLog User Guide

<!-- * Table of Contents -->
<page-nav-print />

WedLog is a desktop app for wedding planning, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). WedLog allows you to view, categorise and edit information about the people involved in your wedding (e.g. vendors, guests). With WedLog, you can easily track multiple streams of person-based information – RSVP status, dietary requirements, and more.

- [Quick Start](#quick-start)
- [Features](#features)
  - [Viewing help: `help`](#viewing-help-help)
  - [Adding a guest: `guest add`](#adding-a-guest-guest-add)
  - [Adding a vendor: `vendor add`](#adding-a-vendor-vendor-add)
  - [Deleting a guest: `guest delete`](#deleting-a-guest-guest-delete)
  - [Deleting a vendor: `vendor delete`](#deleting-a-vendor-vendor-delete)
  - [Viewing all guests: `guest list`](#viewing-all-guests-guest-list)
  - [Viewing all vendors: `vendor list`](#viewing-all-vendors-vendor-list)
  - [Filtering guests: `guest filter`](#filtering-guests-guest-filter)
  - [Filtering vendor: `vendor filter`](#filtering-vendors-vendor-filter)
  - [Undoing last action: `undo`](#undoing-last-action-undo)
  - [Redoing last action: `redo`](#redoing-last-action-redo)
  - [Exiting the program: `exit`](#exiting-the-program-exit)
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)
- [Appendix A: Acceptable values for parameters](#appendix-a-acceptable-values-for-parameters)
- [Appendix B: Miscellaneous error messages](#appendix-b-miscellaneous-error-messages)

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
e.g. in `guest add n/NAME`, `NAME` is a parameter which can be used as `guest add n/John Doe`.

* Items in square brackets are optional.<br>
e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `...` after them can be used multiple times including zero times.
e.g. `[t/TAG...]` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `guest list`, `undo` and `exit`) will be ignored.<br>
e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

--------------------------------------------------------------------------------------------------------------------

### Viewing help: `help`

Shows a message explaining how to access the help page.

```text
help
```

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Adding a guest: `guest add`

Adds a guest to WedLog.

```text
guest add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [tn/TABLE_NUMBER] [t/TAG...]
```

A guest must have the following parameter: `n/NAME`

The following parameters are optional: `p/PHONE_NUMBER e/EMAIL a/ADDRESS r/RSVP_STATUS d/DIETARY_REQUIREMENTS tn/TABLE_NUMBER t/TAG...`

>Tips:
><br>
>- Parameters can be in any order
><br>
>- A guest can have any number of tags (including 0)
><br>
>- Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `guest add n/Bob p/91234567 a/Blk 123 r/no`
- `guest add n/Keith p/92354567 d/ r/`: Will be interpreted as Keith having no dietary requirements and unknown RSVP status.
- `guest add n/Jane Tan t/family t/bridesmaid`
- `guest add n/John Doe p/98765432 e/john@doe.com a/Street 456 r/unknown d/vegetarian tn/13 t/friend`

Expected behaviour upon success:
- Adds the guest.
- Displays a message showing the added guest.

Expected behaviour upon failure:
- No name specified: Displays error message “Please specify the guest’s name using the format `n/NAME`”.
- Phone number format invalid: Displays error message “Please specify the guest’s phone number with only numbers with no spaces or special characters”.
- `r/` tag uses an invalid value: Displays error message “RSVP status can only be `yes`, `no` or `unknown`”.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Adding a vendor: `vendor add`

Adds a vendor to WedLog.

```text
vendor add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG...]
```

A guest must have the following parameter: `n/NAME`

The following parameters are optional: `p/PHONE_NUMBER e/EMAIL a/ADDRESS r/RSVP_STATUS d/DIETARY_REQUIREMENTS t/TAG...`

>Tips:
><br>
>- Parameters can be in any order.
><br>
>- A vendor can have any number of tags (including 0).
><br>
>- Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `vendor add n/Betsy Crowe`
- `vendor add n/John Doe p/91234567`
- `vendor add n/John Doe p/91234567 e/johndflowers@email.com a/123 Flower Lane t/florist t/photographer`

Expected behaviour upon success:
- Adds a vendor to the vendor list.
- Displays the vendor that has been added.

Expected behaviour upon failure:
- No name: Displays error message "Please specify the vendor’s name using the format n/name"
- Phone number format invalid: Displays error message “Please specify the vendor’s phone number with only numbers with no spaces or special characters”

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Deleting a guest: `guest delete`

Deletes the specified guest from WedLog.

```text
guest delete INDEX
```

Acceptable values for `INDEX`:
- A positive integer.

Examples:
- `guest delete 2` deletes the 2nd guest on the guest list.
- `guest filter n/Betsy` followed by `guest delete 1` deletes the 1st guest in the results of the `filter` command.

Expected behaviour upon success:
- Deletes the guest at the specified `INDEX`.
  - If the previous command was `guest filter KEY_WORDS`, the `INDEX` refers to the index number shown in the filtered guest list.
  - Otherwise, the `INDEX` refers to the index number on the unfiltered guest list.
- Displays a message telling user which guest has been deleted.

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index”.
- Number does not correspond to any guest: Displays error message “The index you have provided does not correspond to any guest”.
- No input index: Displays error message “Please input an index”.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Deleting a vendor: `vendor delete`

Deletes the specified vendor from WedLog.

```text
vendor delete INDEX
```

Acceptable values for INDEX
- A positive integer.

Examples:
- `vendor list` followed by `vendor delete 2` deletes the 2nd vendor on the vendor list.
- `vendor filter n/Anne` followed by `vendor delete 1` deletes the 1st vendor in the results of the `filter` command.

Expected behaviour upon success:
- Deletes the vendor at the specified `INDEX`.
  - If the previous command was `vendor filter KEY_WORDS`, the `INDEX` refers to the index number shown in the filtered vendor list.
  - Otherwise, the `INDEX` refers to the index number on the unfiltered vendor list.
- Displays a message telling user which vendor has been deleted.

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message "Please input a positive integer as the index".
- Number does not correspond to any vendor: Displays error message "The number you have provided does not correspond to any vendor".
- No input number: Displays error message "Please input an index".

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Editing a vendor : `vendor edit`
Edits the specified vendor in WedLog.

```text
vendor edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...
```

Acceptable values for INDEX
- A positive integer.

Examples:
- `vendor list` followed by `vendor edit 2 p/914624435` edits the phone number of the 2nd vendor to be `91462435`.

Expected behaviour upon success:
- Edits the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed vendor list.

Expected behaviour upon failure:
- Index does not correspond to any vendor: Displays error message "The vendor index provided is invalid".
- No index: Displays error message "Invalid command format!" with message usage.
- No fields updated: Displays error message "At least one field to edit must be provided".

--------------------------------------------------------------------------------------------------------------------

### Viewing all guests: `guest list`

View all guests in a list format.

```text
guest list
```

Expected behaviour upon success:
- Displays a list of all guest names and their respective indexes.
  - Example: 1. Marcus Tan, 2. Jane Lim

Expected behaviour upon failure:
- Refer to [Appendix B](#appendix-b-miscellaneous-error-messages): Expected behaviour upon general failure.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Viewing all vendors: `vendor list`

View all vendors in a list format.

```text
vendor list
```

Expected behaviour upon success:
- Displays a list of all vendor names and their respective indexes.
  - Example: 1. John FLORAL, 2. Sally Anne PHOTOGRAPHER

Expected behaviour upon failure:
- Refer to [Appendix B](#appendix-b-miscellaneous-error-messages): Expected behaviour upon general failure.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Filtering guests: `guest filter`

Filters the guest list using values inputted by you.

```text
guest filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [tn/TABLE_NUMBER] [t/TAG...]
```

Parameters in square brackets are optional. However, you must include at least one parameter to filter by.

>Tips:
><br>
>- Parameters can be in any order.
   ><br>
>- NAME cannot be filtered with empty values.
   ><br>
>- For all parameters aside from NAME, providing an empty value will filter for guests with unfilled values for that parameter.
   ><br>
>- The filter command will only return guests that matches all the input values across different parameters.
   ><br>
>- DIETARY_REQUIREMENT and TAG parameters can be inputted multiple times (e.g. "guest filter d/no beef d/no pork"). However, do take note that these two parameters are filtered via a case-insensitive exact match (i.e. a guest with the tag "friends" would not be a valid result for the input "t/friend").
   ><br>
>- Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `guest filter n/Jane tn/9`: filter for all guests with "Jane" in their names who are seated at table 9. Results might include "Jane Lee, table 9" and "Janet Tan, table 9". 
- `guest filter d/no beef d/no pork`: filter for all guests who are tagged with both "no beef" and "no pork" in their dietary requirements field.
- `guest filter p/`: filter for all guests with empty phone numbers.
- `guest filter r/`: filter for all guests with unknown rsvp status.
- `guest filter d/`: filter for all guests with no dietary requirements.

Expected behaviour upon success:
- Displays a list of guests with parameters that match all the inputted values.

Expected behaviour upon failure:
- Empty name (e.g. `guest filter n/`): Displays error message "Cannot filter for empty name field".
- No parameter (e.g. `guest filter`): Displays error message "No prefix was found in the command!" followed by an instruction on the proper usage of the `guest filter` function.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Filtering vendors: `vendor filter`

Filters the vendor list using values inputted by you.

```text
vendor filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG...]
```

Parameters in square brackets are optional. However, you must include at least one parameter to filter by.

>Tips:
><br>
>- Parameters can be in any order.
   ><br>
>- NAME parameter cannot be filtered using empty values.
   ><br>
>- For all parameters aside from NAME, providing an empty value will filter for vendors with unfilled values for that parameter.
   ><br>
>- The filter command will only return vendors that matches all the input values across different parameters.
   ><br>
>- TAG parameter can be inputted multiple times (e.g. "vendor filter t/photographer t/dj"). However, do take note that TAGs are filtered via a case-insensitive exact match (i.e. a vendor with the tag "djay" would not be a valid result for the input "t/dj").
   ><br>
>- Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `vendor filter n/Val p/91234567`: Filter for all vendors with "Val" in their names and "91234567" in their phone numbers.
- `vendor filter n/Vick`: Filter for all vendors with "Vick" in their names. Results may include "Vicky Tan" and "Vick Lee".
- `vendor filter t/photographer t/dj`: Filter for all vendors tagged as both "photographer" and "dj". Results may include "Veronica, tags: photographer, dj", but not "Victor, tags: photographer" and "Valen, tags: photographer, djay".
- `vendor filter p/`: filter for all vendors with empty phone numbers.

Expected behaviour upon success:
- Displays a list of vendors with parameters that match all the inputted values.

Expected behaviour upon failure:
- Empty name (e.g. `vendor filter n/`): Displays error message "Cannot filter for empty name".
- No parameter (e.g. `vendor filter`): Displays error message "No prefix was found in the command!" followed by instruction on vendor filter usage.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Undoing last action: `undo`

Undoes the last action.

```text
undo
```

Examples
- `vendor delete 2` followed by `undo` deletes, then restores the 2nd vendor in WedLog.

Expected behaviour upon success:
- Restores WedLog to its previous state.

Expected behaviour upon failure:
- No states to undo: Displays error message “There is no change to undo!”

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Redoing last action: `redo`

Reverses the last action that was undone.

```text
redo
```

Examples
- `vendor delete 2`, followed by `undo`, followed by `redo` deletes, then restores, then re-deletes the 2nd vendor in WedLog.

Expected behaviour upon success:
- Restores WedLog to its previous state before the last undo.

Expected behaviour upon failure:
- No states to redo: Displays error message “There is no change to redo!”

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

### Exiting the program: `exit`

Exits the program.

```text
exit
```

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action             | Format                                                                                                                               | Example                                                                                       |
|--------------------|:-------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| View help          | `help`                                                                                                                               |                                                                                               |
| Add a guest        | `guest add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [tn/TABLE_NUMBER] [t/TAG...]`      | `guest add n/John Doe p/98765432 e/john@doe.com a/Street 456 r/unknown d/vegetarian t/friend` |
| Add a vendor       | `vendor add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG...]`                                                                | `vendor add n/Betsy p/91234567`                                                               |
| Delete a guest     | `guest delete INDEX`                                                                                                                 | `guest delete 1`                                                                              |
| Delete a vendor    | `vendor delete INDEX`                                                                                                                | `vendor delete 2`                                                                             |
| Edit a guest       | `guest edit INDEX [n/NAME] [p/PHONE]  [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [t/TAG]... [tn/TABLE_NUMBER]`   | `guest edit 1 p/98765432 r/unknown`                                                           |
| Edit a vendor      | `vendor edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`                                                              | `vendor edit 2 p/914624435`                                                                   |
| View all guests    | `guest list`                                                                                                                         |                                                                                               |
| View all vendors   | `vendor list`                                                                                                                        |                                                                                               |
| Filter guest list  | `guest filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [tn/TABLE_NUMBER] [t/TAG...]` | `guest filter n/Keith p/92354567 r/yes`                                                       |
| Filter vendor list | `vendor filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG...]`                                                           | `vendor filter n/John Doe p/91234567 e/johndflowers@email.com a/123 Flower Lane`              |
| Undo last action   | `undo`                                                                                                                               |                                                                                               |
| Redo last action   | `redo`                                                                                                                               |                                                                                               |
| Exit program       | `exit`                                                                                                                               |                                                                                               |


<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

## Appendix A: Acceptable values for parameters 

Acceptable values for `n/NAME`:
- Alphanumeric word with or without spaces and should not be blank.
- Inputs with no values when filtering guests/vendors (e.g. `n/`) is invalid.

Acceptable values for `n/PHONE_NUMBER`:
- Numbers with no spaces or special characters.
- At least 3 numbers.

Acceptable values for `e/EMAIL`:
- `local-part@domain`
  - the `local-part` must:
    - contain alphanumeric characters and these special characters, excluding the parentheses (+_.-)
    - not start or end with any special characters.
  - the `domain` must:
    - consist of domain labels separated by periods.
    - end with a domain label at least 2 characters long.
    - have each domain label start and end with alphanumeric characters.
    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.

Acceptable values for `a/ADDRESS`:
- Word with or without spaces.

Acceptable values for `r/RSVP_STATUS`:
- `yes`
- `no`
- `unknown`
- Inputs with no values when adding a guest (e.g. `r/`) signify that RSVP status should be stored as `unknown`.
- Inputs with no values when filtering guests (e.g. `r/`) is invalid.

Acceptable values for `d/DIETARY_REQUIREMENTS`:
- Alphanumeric word with or without spaces.
- Inputs with no values (e.g. `d/`) signify no dietary requirements.

Acceptable values for `tn/TABLE_NUMBER`:
- Non-negative integer with no spaces or special characters.
- Preceding zeros will be trimmed.

Acceptable values for `t/tag`:
- Alphanumeric word without spaces.

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</span>
--------------------------------------------------------------------------------------------------------------------

## Appendix B: Miscellaneous error messages

User input is completely invalid (e.g. `abc` or `vsdf`):
- Display error message "No such command exists".

User input begins with `vendor` or `guest`, but does not include a valid command word (e.g. `vendor abc` or `guest adddd`):
- Display error message "Please specify a command".

<span style="font-size: 0.4em;">[Back to Top](#wedlog-user-guide)</ssanpan>
