---
  layout: default.md
  title: "User Guide"
  pageNav: 4
---

# WedLog User Guide

<!-- * Table of Contents -->
<page-nav-print />

WedLog is a desktop app for wedding planning, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). WedLog allows you to view, categorise and edit information about the people involved in your wedding (e.g. vendors, guests). With WedLog, you can easily track multiple streams of person-based information – RSVP status, dietary requirements, and more.

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)<br>
    2.1. [Add Command](#2-1-add-command)<br>
    &emsp; 2.1.1. [Adding a guest: `guest add`](#2-1-1-adding-a-guest-guest-add)<br>
    &emsp; 2.1.2. [Adding a vendor: `vendor add`](#2-1-2-adding-a-vendor-vendor-add)<br>
    2.2. [Delete Command](#2-2-delete-command)<br>
    &emsp; 2.2.1. [Deleting a guest: `guest delete`](#2-2-1-deleting-a-guest-guest-delete)<br>
    &emsp; 2.2.2. [Deleting a vendor: `vendor delete`](#2-2-2-deleting-a-vendor-vendor-delete)<br>
    2.3. [Edit Command](#2-3-edit-command)<br>
    &emsp; 2.3.1. [Editing a guest: `guest edit`](#2-3-1-editing-a-guest-guest-edit)<br>
    &emsp; 2.3.2. [Editing a vendor: `vendor edit`](#2-3-2-editing-a-vendor-vendor-edit)<br>
    2.4. [List Command](#2-4-list-command)<br>
    &emsp; 2.4.1. [Viewing all guests: `guest list`](#2-4-1-viewing-all-guests-guest-list)<br>
    &emsp; 2.4.2. [Viewing all vendors: `vendor list`](#2-4-2-viewing-all-vendors-vendor-list)<br>
    2.5. [Filter Command](#2-5-filter-command)<br>
    &emsp; 2.5.1. [Filtering guests: `guest filter`](#2-5-1-filtering-guests-guest-filter)<br>
    &emsp; 2.5.2. [Filtering vendor: `vendor filter`](#2-5-2-filtering-vendors-vendor-filter)<br>
    2.6. [General Commands](#2-6-general-commands)<br>
    &emsp; 2.6.1 [Viewing help: `help`](#2-6-1-viewing-help-help)<br>
    &emsp; 2.6.2 [Undoing last action: `undo`](#2-6-2-undoing-last-action-undo)<br>
    &emsp; 2.6.3 [Redoing last action: `redo`](#2-6-3-redoing-last-action-redo)<br>
    &emsp; 2.6.4 [Exiting the program: `exit`](#2-6-4-exiting-the-program-exit)<br>
3. [FAQ](#3-faq)
4. [Known Issues](#4-known-issues)
5. [Command Summary](#5-command-summary)<br>
6. [Appendices](#6-appendices)<br>
   6.1. [Appendix A: Acceptable values for parameters](#6-1-appendix-a-acceptable-values-for-parameters)<br>
   6.2. [Appendix B: Miscellaneous error messages](#6-2-appendix-b-miscellaneous-error-messages)

--------------------------------------------------------------------------------------------------------------------

## 1. Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `wedlog.jar` from [here](https://github.com/AY2324S1-CS2103T-F11-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your WedLog.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar wedlog.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.
   Refer to the [Features](#2-features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 2. Features

<box type="info" seamless>

**Notes about the command format:** <br />

* Each parameter takes the form `x/ABC`, where the small letters and backslash (e.g. `x/`) represents the label, 
and the words in upper case (e.g. `ABC`) represents the values.

* Labels should be used in the exact format described in this guide. However, values can be replaced with 
your own information. <br>
  e.g. in `guest add n/NAME`, `NAME` is a value which can be replaced, as in `guest add n/Gina Gan`.

* Parameters in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/Gina Gan t/friend` or as `n/Gina Gan`.

* Items with `…` after them can be used multiple times including zero times.
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extra input for commands that do not take in parameters (such as `help`, `guest list`, `undo` and `exit`) will be ignored.<br>
  e.g. if you input `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines 
as space characters surrounding line-breaks may be omitted when copied over to the application.

</box>

--------------------------------------------------------------------------------------------------------------------

### 2.1 Add Command

#### 2.1.1. Adding a guest: `guest add`

Adds a guest to WedLog.

Format: `guest add n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [tn/TABLE_NUMBER] [d/DIETARY REQUIREMENT]… [t/TAG]…`

>Tips:
><br>
>- Items in square brackets are optional.
>  <br>
>- A guest can have any number of dietary requirements and tags (including 0).
>  <br>
>- Refer to [Appendix A](#6-1-appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `guest add n/Gina p/91234567 a/Blk 123 r/no`: Adds a guest named `Gina` with phone number `91234567`, address `Blk 123`, 
and RSVP status of `No`.
- `guest add n/Gerald d/ r/`: Adds a guest named `Gerald` with no dietary requirements and unknown RSVP status.
- `guest add n/Georgiana Tan t/family t/bridesmaid`: Adds a guest named `Georgiana` with two tags, `family` and `bridesmaid`.


Expected behaviour upon success:
- Adds the guest.
- Displays a message showing the added guest.

Expected behaviour upon failure:
- As `NAME` is a compulsory parameter for guests, not providing this parameter would result
in the error message “Please specify the guest’s name using the format `n/NAME`”.
- Providing invalid values for parameters with input restrictions will also trigger error messages. Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) 
for details on acceptable values for each parameter, as well as the error message for invalid values.

--------------------------------------------------------------------------------------------------------------------

#### 2.1.2. Adding a vendor: `vendor add`

Adds a vendor to WedLog.

Format: `vendor add n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`


>Tips:
><br>
>- Items in square brackets are optional.
><br>
>- A vendor can have any number of tags (including 0).
><br>
>- Refer to [Appendix A](#6-1-appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `vendor add n/Valerie Tan p/91234567 a/12 Buona Vista St`: Adds a vendor named "Valerie Tan" with phone number "91234567"
and address "12 Buona Vista St".
- `vendor add n/Victor Wong e/victorwflowers@email.com t/florist t/photographer`: Adds a vendor named "Victor Wong" with
the email "victorwflowers@email.com" and the tags "florist" and "photographer". 

Expected behaviour upon success:
- Adds a vendor to the vendor list.
- Displays the vendor that has been added.

Expected behaviour upon failure:
- As `NAME` is a compulsory parameter for vendors, not providing this parameter would result
  in the error message “Please specify the vendor’s name using the format `n/NAME`”.
- Providing invalid values for parameters with input restrictions will also trigger error messages. Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters)
  for details on acceptable values for each parameter, as well as the error message for invalid values.

--------------------------------------------------------------------------------------------------------------------

### 2.2. Delete Command

#### 2.2.1. Deleting a guest: `guest delete`

Deletes the specified guest from WedLog.

Format: `guest delete INDEX`

Acceptable values for `INDEX`:
- A positive integer.

Examples:
- `guest delete 2` deletes the 2nd guest on the guest list.
- `guest filter n/Gina` followed by `guest delete 1` deletes the 1st guest in the results of the `filter` command.

Expected behaviour upon success:
- Deletes the guest at the specified `INDEX`.
    - If the previous command was `guest filter KEY_WORDS`, the `INDEX` refers to the index number shown in the filtered guest list.
    - Otherwise, the `INDEX` refers to the index number on the unfiltered guest list.
- Displays a message telling user which guest has been deleted.

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message “Please input a positive integer as the index”.
- Number does not correspond to any guest: Displays error message “The index you have provided does not correspond to any guest”.
- No input index: Displays error message “Please input an index”.

--------------------------------------------------------------------------------------------------------------------

#### 2.2.2. Deleting a vendor: `vendor delete`

Deletes the specified vendor from WedLog.

Format: `vendor delete INDEX`

Acceptable values for INDEX
- A positive integer.

Examples:
- `vendor list` followed by `vendor delete 2` deletes the 2nd vendor on the vendor list.
- `vendor filter n/Valencia` followed by `vendor delete 1` deletes the 1st vendor in the results of the `filter` command.

Expected behaviour upon success:
- Deletes the vendor at the specified `INDEX`.
    - If the previous command was `vendor filter KEY_WORDS`, the `INDEX` refers to the index number shown in the filtered vendor list.
    - Otherwise, the `INDEX` refers to the index number on the unfiltered vendor list.
- Displays a message telling user which vendor has been deleted.

Expected behaviour upon failure:
- Number out of index range, not a number, or no number: Displays error message "Please input a positive integer as the index".
- Number does not correspond to any vendor: Displays error message "The number you have provided does not correspond to any vendor".
- No input number: Displays error message "Please input an index".

--------------------------------------------------------------------------------------------------------------------

### 2.3. Edit Command

#### 2.3.1. Editing a guest : `guest edit`
Edits the specified guest in WedLog.

Format: `guest edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [tn/TABLE_NUMBER] [d/DIETARY_REQUIREMENT]… [t/TAG]…`

>Tips:
><br>
>- Items in square brackets are optional.
   ><br>
>- An edit command can have any number of tags (including 0).
   ><br>
>- Specifying an empty parameter (e.g. `p/`) will delete the parameter's value from the guest.
   ><br>
>- An edit command requires at least 1 parameter.
   ><br>
>- Refer to [Appendix A](#6-1-appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Acceptable values for INDEX
- A positive integer (e.g. 1, 2, 3 ...)

Examples:
- `guest list` followed by `guest edit 2 p/914624435` edits the phone number of the 2nd guest to be `91462435`.
- `guest filter n/Gina` followed by `guest edit 1 n/Ginette` edits the name of the 1st guest in the results of the `filter` command to be `Ginette`.

Expected behaviour upon success:
- Edits the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed guest list.

Expected behaviour upon failure:
- Index is not a number, or no index provided: Displays error message "Please input a positive integer as the index", 
with instruction on the correct input format.
- Index does not correspond to any guest: Displays error message "The index provided does not reference any guest".
- No parameters provided: Displays error message "You must provide at least one parameter to edit".

--------------------------------------------------------------------------------------------------------------------

#### 2.3.2. Editing a vendor : `vendor edit`
Edits the specified vendor in WedLog.

Format: `vendor edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

Acceptable values for INDEX
- A positive integer.

>Tips:
><br>
>- Items in square brackets are optional.
><br>
>- An edit command can have any number of tags (including 0).
><br>
>- Specifying an empty parameter (e.g. `p/`) will delete the parameter's value from the vendor.
><br>
>- An edit command requires at least 1 parameter.
><br>
>- Refer to [Appendix A](#6-1-appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.

Examples:
- `vendor list` followed by `vendor edit 2 p/914624435` edits the phone number of the 2nd vendor to be `91462435`.
- `vendor filter n/Valerie` followed by `vendor edit 1 n/Val` edits the name of the 1st vendor in the results of the `filter` command to be `Val`.

Expected behaviour upon success:
- Edits the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed vendor list.

Expected behaviour upon failure:
- Index is not a number, or no index provided: Displays error message "Please input a positive integer as the index",
  with instruction on the correct input format.
- Index does not correspond to any guest: Displays error message "The index provided does not reference any vendor".
- No parameters provided: Displays error message "You must provide at least one parameter to edit".

--------------------------------------------------------------------------------------------------------------------

### 2.4. List Command

#### 2.4.1. Viewing all guests: `guest list`

View all guests in a list format.

Format: `guest list`

Expected behaviour upon success:
- Displays a list of all guest names and their respective indexes. 
    - Example: 1. Gina Tan, 2. Ginette Lim
- If there is one or more guests, displays the message "Listed all guests". Else, displays the message "No guests recorded".

Expected behaviour upon failure:
- Refer to [Appendix B](#6-2-appendix-b-miscellaneous-error-messages): Expected behaviour upon general failure.

--------------------------------------------------------------------------------------------------------------------

#### 2.4.2. Viewing all vendors: `vendor list`

View all vendors in a list format.

Format: `vendor list`

Expected behaviour upon success:
- Displays a list of all vendor names and their respective indexes.
    - Example: 1. Valerie Tan, 2. Victor Lim
- If there is one or more vendors, displays the message "Listed all vendors". Else, displays the message "No vendors recorded".

Expected behaviour upon failure:
- Refer to [Appendix B](#6-2-appendix-b-miscellaneous-error-messages): Expected behaviour upon general failure.

--------------------------------------------------------------------------------------------------------------------

### 2.5. Filter Command

#### 2.5.1. Filtering guests: `guest filter`

Filters the guest list using values inputted by you.

Format: `guest filter [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [tn/TABLE_NUMBER] [d/DIETARY_REQUIREMENT]… [t/TAG]…`

>Tips:
><br>
>- Parameters in square brackets are optional. However, you must include at least one parameter to filter by.
   ><br>
>- NAME cannot be filtered with empty values.
   ><br>
>- For all parameters aside from NAME, providing an empty value will filter for guests with unfilled values for that parameter.
   ><br>
>- Providing an empty `r/` value would filter for guests with unknown RSVP status.
   ><br>
>- The filter command will only return guests that matches all the input values across different parameters.
   ><br>
>- DIETARY_REQUIREMENT and TAG parameters can be inputted multiple times (e.g. "guest filter d/no beef d/no pork"). 
   However, do take note that these two parameters are filtered via a case-insensitive exact match (i.e. a guest with the tag "friends" would not be a valid result for the input "t/friend").
   ><br>
>- Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.


Examples:
- `guest filter n/Gia r/no`: filters for guests with `Gia` in their names who have RSVP status of `No`. Results might include `Gia Lee, RSVP: No` and `Gianna Tan, RSVP: No`. 
- `guest filter r/`: filters for guests with RSVP status `Unknown`.
- `guest filter d/`: filters for guests with no dietary requirements.
- `guest filter t/`: filters for guests with no tags
- `guest filter d/no beef d/no pork`: filter for all guests who are tagged with both "no beef" and "no pork" in their dietary requirements field.

Expected behaviour upon success:
- Displays a list of guests that match all the inputted values.
- Displays a message showing the total number of results found.

Expected behaviour upon failure:
- Empty name (e.g. `guest filter n/`): Displays error message "Cannot filter for empty name. Guests are not allowed to have empty names".
- No parameter (e.g. `guest filter`): Displays error message "Please input at least one parameter to filter by", followed by an instruction on
  the proper usage of the `guest filter` function.


--------------------------------------------------------------------------------------------------------------------

#### 2.5.2. Filtering vendors: `vendor filter`

Filters the vendor list using values inputted by you.

Format: `vendor filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

>Tips:
><br>
>- Items in square brackets are optional.
   ><br>
>- NAME parameter cannot be filtered using empty values.
   ><br>
>- For all parameters aside from NAME, providing an empty value (e.g. `p/`) will filter for vendors with unfilled values for that parameter.
   ><br>
>- The filter command will only return vendors that matches all the input values across different parameters.
   ><br>
>- TAG parameter can be inputted multiple times (e.g. "vendor filter t/photographer t/dj"). However, do take note that 
TAGs are filtered via a case-insensitive exact match (i.e. a vendor with the tag "djay" would not be a valid result for the input "t/dj").
   ><br>
>- Refer to [Appendix A](#appendix-a-acceptable-values-for-parameters) for more details on the acceptable values for the parameters.


Examples:
- `vendor filter n/Val`: Filter for all vendors with "Val" in their names. Results may include `Val Tan, tag: Photographer`
and `Valerie Lee, tag: Florist`.
- `vendor filter t/photographer t/dj`: Filter for all vendors tagged as both "photographer" and "dj". Results may include 
`Veronica, tags: photographer, dj`, but not `Victor, tags: photographer` and `Valen, tags: photographer, djay`.
- `vendor filter p/`: filter for all vendors with empty phone numbers.

Expected behaviour upon success:
- Displays a list of vendors that match all the inputted values.
- Displays a message showing the total number of results found.

Expected behaviour upon failure:
- Empty name (e.g. `vendor filter n/`): Displays error message "Cannot filter for empty name".
- No parameter (e.g. `vendor filter`): Displays error message "No prefix was found in the command!" followed by instruction on vendor filter usage.

--------------------------------------------------------------------------------------------------------------------

### 2.6. General Commands

#### 2.6.1. Viewing help: `help`

Shows a message explaining how to access the help page.

Format: `help`

--------------------------------------------------------------------------------------------------------------------

### 2.6.2. Undoing last action: `undo`

Undoes the last action.

```text
undo
```

Format: `undo`

>Tips:
><br>
>- The undo command can also be triggered by pressing Control + Z (Windows) or Command + Z (Mac) on the keyboard.

Examples
- `vendor delete 2` followed by `undo` deletes, then restores the 2nd vendor in WedLog.

Expected behaviour upon success:
- Restores WedLog to its previous state.

Expected behaviour upon failure:
- No states to undo: Displays error message “There is no change to undo!”

--------------------------------------------------------------------------------------------------------------------

### 2.6.3. Redoing last action: `redo`

Reverses the last action that was undone.

Format: `redo`
 
>Tips:
><br>
>- The redo command can be triggered by pressing Control + Y (Windows) or Command + Y (Mac) on the keyboard.

Examples
- `vendor delete 2`, followed by `undo`, followed by `redo` deletes, then restores, then re-deletes the 2nd vendor in WedLog.

Expected behaviour upon success:
- Restores WedLog to its previous state before the last undo.

Expected behaviour upon failure:
- No states to redo: Displays error message “There is no change to redo!”

--------------------------------------------------------------------------------------------------------------------

### 2.6.4. Exiting the program: `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## 4. Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## 5. Command summary

| Action             | Format                                                                                                                             | Example                                                                                       |
|--------------------|:-----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| View help          | `help`                                                                                                                             |                                                                                               |
| Add a guest        | `guest add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [tn/TABLE_NUMBER] [d/DIETARY REQUIREMENT]… [t/TAG]…`      | `guest add n/John Doe p/98765432 e/john@doe.com a/Street 456 r/unknown d/vegetarian t/friend` |
| Add a vendor       | `vendor add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`                                                                | `vendor add n/Betsy p/91234567`                                                               |
| Delete a guest     | `guest delete INDEX`                                                                                                               | `guest delete 1`                                                                              |
| Delete a vendor    | `vendor delete INDEX`                                                                                                              | `vendor delete 2`                                                                             |
| Edit a guest       | `guest edit INDEX [n/NAME] [p/PHONE]  [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [tn/TABLE_NUMBER] [d/DIETARY REQUIREMENT]… [t/TAG]…`   | `guest edit 1 p/98765432 r/unknown`                                                           |
| Edit a vendor      | `vendor edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`                                                              | `vendor edit 2 p/914624435`                                                                   |
| View all guests    | `guest list`                                                                                                                       |                                                                                               |
| View all vendors   | `vendor list`                                                                                                                      |                                                                                               |
| Filter guest list  | `guest filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/RSVP_STATUS] [d/DIETARY REQUIREMENTS] [tn/TABLE_NUMBER] [t/TAG]…` | `guest filter n/Keith p/92354567 r/yes`                                                       |
| Filter vendor list | `vendor filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`                                                           | `vendor filter n/John Doe p/91234567 e/johndflowers@email.com a/123 Flower Lane`              |
| Undo last action   | `undo`                                                                                                                             |                                                                                               |
| Redo last action   | `redo`                                                                                                                             |                                                                                               |
| Exit program       | `exit`                                                                                                                             |                                                                                               |

--------------------------------------------------------------------------------------------------------------------

## 6. Appendices

### 6.1. Appendix A: Acceptable values for parameters

Acceptable values for `n/NAME`:
- Alphanumeric word with or without spaces and should not be blank.
- Inputs with no values when filtering guests/vendors (e.g. `n/`) is invalid.
- Error message for invalid name: “Names cannot be blank and should not contain any special characters”.

Acceptable values for `p/PHONE`:
- Numbers with no spaces or special characters.
- At least 3 numbers.
- Error message for invalid phone number: “Phone numbers should contain only numbers, with no spaces or special characters”.


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
- Error message for invalid email: "Emails should contain two segments separated by an @ symbol."

Acceptable values for `a/ADDRESS`:
- Word with or without spaces.

Acceptable values for `r/RSVP_STATUS`:
- `yes`
- `no`
- `unknown`
- Inputs with no values when adding a guest (e.g. `r/`) signify that RSVP status should be stored as `unknown`.
- Inputs with no values when filtering guests (e.g. `r/`) signify that you wish to filter for guests with `unknown` RSVP status.
- Error message for invalid RSVP status: “RSVP status can only be `yes`, `no` or `unknown`”.

Acceptable values for `d/DIETARY_REQUIREMENT`:
- Alphanumeric word with or without spaces.

Acceptable values for `tn/TABLE_NUMBER`:
- Non-negative integer with no spaces or special characters.
- Preceding zeros will be trimmed.
- Error message for invalid table numbers: "Table numbers should be positive numbers with no spaces or special characters."

Acceptable values for `t/TAG`:
- Alphanumeric word without spaces.
- Error message for invalid tags: "Tags should have no spaces or special characters."

--------------------------------------------------------------------------------------------------------------------

### 6.2. Appendix B: Miscellaneous error messages

User input is completely invalid (e.g. `abc` or `vsdf`):
- Display error message "No such command exists".

User input begins with `vendor` or `guest`, but does not include a valid command word (e.g. `vendor abc` or `guest adddd`):
- Display error message "Please specify a command".
