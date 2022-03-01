---
layout: page
title: User Guide
---

LibTask is a **desktop application for librarians to manage book loans and requests by patrons, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your book tracking tasks done faster than traditional GUI apps. 

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

# 1. Introduction

As a school librarian of a large library, you may already have your own desktop library software. However, existing library softwares are GUI-based and slow to work with. If you prefer to work with CLI commands efficiently while still having the benefits of aesthetic displays, then LibTask is designed just for you! LibTask provides you with a well packaged system of commands for managing book loans and book requests by your patrons. Using LibTask, you can quickly process borrowing and returning books, and view different groups of patrons and books to perform tasks such as notifying patrons with overdue books. The system also maintains two independent lists of books and patrons, allowing you to perform queries more efficiently.

--------------------------------------------------------------------------------------------------------------------

# 2. Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `libtask.jar` from [here](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your LibTask.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`patron list`** : Lists all the patrons in libTask.

   * **`patron add`**`n/Alice s/S01823283S p/90123212 e/profA@u.nus.edu ` : Adds a patron named `Alice` into LibTask.

   * **`patron delete`**`3` : Deletes the 3rd patron shown in the current patron list.

   * **`book add`**` n/Harry Potter i/12398-12398-239 a/J.K.Rowling t/Thriller t/Magic`: Adds a book titled `Harry Potter`.

   * **`book list`** : Lists all the books in libTask.

   * **`book delete`**`1` : Deletes the 1st book shown in the current book list.

   * **`exit`** : Exits libTask.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a book: `add`

Adds a book to LibTask database.

Format: `book add n/NAME i/ISBN [a/AUTHOR …] [t/CATEGORY_TAG …] `

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
* ISBN must be 10 or 13 digits in length
</div>

Examples:
* `book add n/Harry Potter i/978-7-783828-15-1 a/J.K.Rowling t/Thriller t/Magic`
* `book add n/Heads You Lose i/979-381-26-8943-3 a/Lisa Lutz a/David Hayward`

### Listing all books : `list`

List all books in LibTask database.

Format: `book list`

### Editing a book : `edit`

Edit details of the book at the specified index.

Format: `book edit INDEX [n/NAME] [i/ISBN] [a/AUTHOR …] [t/CATEGORY_TAG …]`

* Edits the book at the specified `INDEX`. The index refers to the index number shown in the displayed book list. The index **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of books in the displayed list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags or authors, the existing tags or authors of the book will be removed i.e. adding of tags and authors is not cumulative.
* You can remove all the book’s authors and tags by typing `a/` or `t/` respectively without
    specifying any tags after it.

Examples:
*  `book edit 1 n/Harry Potter: Sorcerer's Stone t/Adventure` Edits the name of the 1st book to be `Harry Potter: Sorceror's Stone` and edit its category tag to be `Adventure`.
*  `book edit 2 i/978-79317-3-542-3 a/Another Rowling t/` Edits the ISBN of the 2nd book to be `978-79317-3-542-3`, changes the author to `Another Rowling` and clears all existing tags.

### Deleting a book : `delete`

Deletes the book at the specified index.

Format: `book delete INDEX`

* Deletes the book at the specified `INDEX`.
* The index refers to the index number shown in the displayed book list.
* The index **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of books in the displayed list.

Examples:
* `book list` followed by `book delete 2` deletes the 2nd book from LibTask's database.

### Borrowing a book : `borrow`

Establishes a relationship that patron at index INDEX1 borrows a book at index INDEX2.

Format: `borrow INDEX1 INDEX2`

* `INDEX1` refers to the index number shown in the displayed patron list.
* `INDEX2` refers to the index number shown in the displayed book list.
* `INDEX1` **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of patrons in the displayed list.
* `INDEX2` **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of books in the displayed list.
* The book at `INDEX2` must not be already borrowed.

Examples:
* `patron list` and `book list` followed by `borrow 2 3` establishes a relationship that the 2nd patron borrows the 3rd book.

### Returning a book : `return`

Depending on the exact command, return all books borrowed by a patron at the specified index, or return a book at the specified index.

Format: `return PREFIX/INDEX`

* `PREFIX` must be either `p` for patrons or `b` for books.
* If `PREFIX` is `p`, `INDEX` refers to the index number shown in the displayed patron list.
* If `PREFIX` is `b`, `INDEX` refers to the index number shown in the displayed book list.
* `INDEX` **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of patrons or books in the displayed list.
* If the book at index `INDEX` is not borrowed, or if the patron at index `INDEX` does not borrow any books, nothing happens.

Examples:
* `patron list` followed by `return p/3` will return all books borrowed by the 3rd patron, if any.
* `book list` followed by `return b/2` will return the 2nd book, if it is borrowed.

### Requesting a book : `request`

Establishes a relationship that patron at index INDEX1 is requesting to be notified when the book at index INDEX2 is available.

Format: `request INDEX1 INDEX2`

* `INDEX1` **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of patrons in the displayed list.
* `INDEX2` **must be a positive integer** 1, 2, 3, … and smaller than or equal to the number of books in the displayed list.
* If the book at index `INDEX2` is currently available, a message will be displayed.

Examples:
* `patron list` and `book list` followed by `request 1 2` keeps a record that the 1st patron would like to be notified when the 2nd book is available.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
