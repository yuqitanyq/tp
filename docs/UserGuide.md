---
layout: page
title: User Guide
---

LibTask is a **desktop application for librarians to manage book loans and requests by patrons, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you can type fast, LibTask can get your book tracking tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Introduction

As a school librarian of a large library, you may already have your own desktop library software. However, existing library softwares are GUI-based and slow to work with. If you prefer to work with CLI commands efficiently while still having the benefits of aesthetic displays, then LibTask is designed just for you! LibTask provides you with a well packaged system of commands for managing book loans and book requests by your patrons. Using LibTask, you can quickly process borrowing and returning books, and view different groups of patrons and books to perform tasks such as notifying patrons with overdue books. The system also maintains two independent lists of books and patrons, allowing you to perform queries more efficiently.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `libtask.jar` from [here](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your LibTask.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds.<br>
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open a help window.<br>
   Here are some sample commands you can try:

   * **`patron list`** : Lists all patrons in LibTask's patron list.

   * **`patron add n/Alice s/S01823283S p/90123212 e/profA@u.nus.edu `** : Adds a patron named `Alice` into LibTask's patron list.

   * **`patron delete 3`** : Deletes the 3rd patron shown in the displayed patron list.

   * **`book add n/Harry Potter i/12398-12398-239 a/J.K.Rowling t/Thriller t/Magic`**: Adds a book titled `Harry Potter`into LibTask's book list.

   * **`book list`** : Lists all books in LibTask's book list.

   * **`book delete 1`** : Deletes the 1st book shown in the displayed book list.

   * **`exit`** : Exits LibTask.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Important Notes

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `patron delete INDEX`, `INDEX` is a parameter which the user can enter and can be used as `patron delete 1`.

* Items in square brackets are optional.<br>
  e.g. `patron add n/NAME s/ID p/PHONE e/EMAIL [t/TAG]…​ ` can be used as `patron add n/John s/A0123456A p/93231222 e/e03482@u.nus.edu t/student` or as `patron add n/John s/A0123456A p/93231222 e/e03482@u.nus.edu`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend` or `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `patron list`, `book list`, `exit` and `clear`) will be ignored.<br>
  e.g. `help 123` will be interpreted as `help`.

</div>

--------------------------------------------------------------------------------------------------------------------

## Features

### A. General Features

--------------------------------------------------------------------------------------------------------------------

### Viewing help : `help`

Opens a help window explaining how to access LibTask's user guide.

![help message](images/helpMessage.png)

Format: `help`

### Clearing all entries : `clear`

Clears all patrons and books from LibTask's patron and book lists.

Format: `clear`

### Exiting LibTask : `exit`

Exits the program.

Format: `exit`

### Retrieving previous command: `u`

Retrieves the previous command entered by the user back to the command box.

Format: `u`

* Loads your previous successfully entered commands back to the command box so you can view and rerun it.
* Only allows each command to be viewed once.

### Saving LibTask's data

LibTask's data is saved in the hard disk automatically after any command that changes it. There is no need to save manually.

### B. Patron Features

--------------------------------------------------------------------------------------------------------------------
### Adding a patron: `patron add`
Adds a patron to LibTask's patron list.

Format: `patron add n/NAME s/ID p/PHONE e/EMAIL [t/TAG]…​`

Examples:
* `patron add n/John s/A02128282A p/93231222 e/e03482@u.nus.edu t/student`
* `patron add n/Alice s/S01823283S p/90123212 e/profA@u.nus.edu`

### Listing all patrons : `patron list`

Shows a list of all patrons in LibTask's patron list.

Format: `patron list`

* If all patrons are already listed, the command will still show a success message as having listed all patrons, but the patrons listed will have no visual change.

### Editing a patron : `patron edit`

Edits the details of a patron at a specified index of the displayed patron list.

Format: `patron edit INDEX [n/NAME] [s/ID] [p/PHONE] [e/EMAIL] [t/TAG]…​`

* Edits the patron at the specified `INDEX`. The index refers to the index number shown in the displayed patron list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the patron will be removed i.e. adding of tags is not cumulative.
* You can remove all the patron’s tags by typing `t/` without
    specifying any tags after it.

Examples:
* `patron edit 1 n/John Cena p/91959491 e/johncena@u.nus.edu` Edits the name, phone number and email address of the 1st patron in the displayed patron list to be `John Cena`, `91959491` and `johncena@u.nus.edu` respectively.
* `patron edit 2 n/Alice t/Professor t/Horror ` Edits the name of the 2nd patron in the displayed patron list to be `Alice` and changes tags to `Professor` and `Horror`.

### Finding a patron by name: `patron find`

Finds all patrons with names matching the given keywords in LibTask's patron list.

Format: `patron find n/KEYWORD [n/KEYWORD]…​`

* The search is case-insensitive. e.g. `hans` will match `Hans`
* The order of the keywords does not matter. e.g. results from the keyword `Hans Bo` will match results of the keyword `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. results from the keyword `Han` will not match results from the keyword `Hans`
* Patrons matching at least one part of the keyword will be returned (i.e. `OR` search).
  e.g. keyword `Hans Bo` will return patrons with names `Hans Gruber`, `Bo Yang`

Example:

`patron find n/alex n/david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a patron : `patron delete`

Deletes the patron at a specified index in the displayed patron list.

Format: `patron delete INDEX`

* Deletes the patron at the specified `INDEX`.
* The index refers to the index number shown in the displayed patron list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `patron list` followed by `patron delete 2` deletes the 2nd patron shown in the displayed patron list.
* `patron find n/Betsy` followed by `patron delete 1` deletes the 1st person in the results of the `find` command.

### Listing all patrons with overdue books : `patron overdue`

Lists all patrons in LibTask's patron list with overdue books.

Format: `patron overdue`

### C. Book Features

--------------------------------------------------------------------------------------------------------------------

### Adding a book: `book add`
Adds a book to LibTask's book list.

Format: `book add n/BOOK_NAME i/ISBN [a/AUTHOR] … [t/TAG] … `

<div markdown="span" class="alert alert-primary">

**Notes about the add command:**
* BOOK_NAME must start and end with alphanumeric characters, and can only contain `'` character, `:` character and alphanumeric characters.
* ISBN must be 10 or 13 digits in length, and contain only numbers with at most one `-` character between consecutive numbers. If it is 13 digits long, it must start with 978 or 979. 
* ISBN digits must have valid checksum. Details on calculating checksum can be found here.
* AUTHOR must start with an alphanumeric character, and can only contain alphanumeric characters and `.` character.
* You can add multiple copies the same book with the same isbn. However, all books with the same isbn must also have the same name and written by the same authors.
* If books with the same isbn already exists, and is requested by some patrons, adding this book will also remove all requests for those books, and you will be reminded to notify patrons who are interested in this available book.
</div>

**Example**

To add a book with the name `Introduction to Algorithms`, isbn `9780371888506`, authors `Cormen`, `Leiserson`, `Rivest` and `Stein`, with a tag of `ComputerScience`, you can enter the following command:

`book add book add n/Introduction to Algorithms a/Cormen a/Leiserson a/Rivest a/Stein i/9780371888506 t/ComputerScience`

Before entering the command. A book with the same name already exists in LibTask. However, that copy is currently borrowed by Alex and requested by Bernice and Charlotte.
![book-add-1.png](images/book-add-1.PNG)

After entering the command, a new available copy of the book is added. You will also be reminded to notify Bernice and Charlotte about the availability of this book.
![book-add-2.png](images/book-add-2.PNG)

### Listing all books : `book list`

To show a list of all books in LibTask, you can enter the `list` command with the format shown below.

**Format**: `book list`

<div markdown="span" class="alert alert-primary">

**Notes about the list command:**
* If all books are already listed, the command will still show a success message as having listed all books, but the books listed will have no visual change.
</div>

**Example**: `book list`

Before entering the command, only two books are listed.
![book-list-1.png](images/book-list-1.PNG)

After entering the command, all books will be listed.
![book-list-1.png](images/book-list-2.PNG)

### Finding books : `book find`

To find books in LibTask based on book name, author or tags, you can enter the `find` command with the format shown below.

**Format**: `book find PREFIX/KEYWORD`

<div markdown="span" class="alert alert-primary">

**Notes about the list command:**

* `PREFIX` must be either `t` (for find based on tag), `a` (for finding based on author), or `n` (for finding based on book name).
* All books with a tag, or author, or book name that contains the substring `KEYWORD` will be displayed in the book list. 
</div>

**Example**:

To find all books for computer science students, you can enter the following command:

`book find t/computer`

Before entering the command, all books are displayed.
![book-find-1.png](images/book-find-1.PNG)

After entering the command, only books that have a tag containing the keyword "computer" are displayed.
![book-find-2.png](images/book-find-2.PNG)

To find all books for with a book name containing the keyword `Harry`, you can enter the following command:

`book find n/Harry`

Before entering the command, all books are displayed.
![book-find-3.png](images/book-find-3.PNG)

After entering the command, only one book which name contains `Harry` is displayed.
![book-find-4.png](images/book-find-4.PNG)

### Editing a book : `book edit`

To edit the details of a specific book, you can enter the `edit` command with the format shown below.

**Format**: `book edit INDEX [n/NAME] [i/ISBN] [a/AUTHOR] … [t/CATEGORY_TAG] …`

<div markdown="span" class="alert alert-primary">

**Notes about the edit command:**

* Edits the book at the specified `INDEX`. `INDEX` refers to the index number shown in the displayed book list. The index **must be a positive integer** 1, 2, 3, … 
* At least one of the optional fields (ISBN, AUTHOR, CATEGORY_TAG) must be provided.
* Existing values will be updated to the input values.
* When a book's name, authors or isbn is changed, all copies of books with the same isbn will have their respective fields edited as well. This is because all books with the same isbn should have the same name and authors.
* When editing tags or authors, the existing tags or authors of the book will be removed i.e. adding of tags and authors is not cumulative. However, if all tags are added in a single input, the multiple tags will be added.
* You can remove all the book’s authors and tags by typing `a/` or `t/` respectively without specifying any tags after it.
* You cannot edit a book's isbn into a new isbn if the new isbn already belongs to another book in LibTask.
</div>

**Examples**:

To edit the first book's name from `Harry Potter: Sorcerer's Stone` to `Harry Potter 1`, and its tag to `Thriller` and `Magic`, you can enter the following command:

`book edit 1 n/Harry Potter 1 t/Thriller t/Magic`

Before entering the command, the first book still has its original name and tags.
![book-edit-1.png](images/book-edit-1.PNG)

After entering the command, the first book has its name edited to `Harry Potter 1` and has tags `Thriller` and `Magic`
![book-edit-2.png](images/book-edit-2.PNG)

To remove all authors from and tags from the first book, you can enter the following command:

`book edit 1 a/ t/`

Before entering the command, there are two copies of `Harry Potter and The Philosopher's Stone`, both have different tags but has the same author `J. K. Rowling`.
![book-edit-3.png](images/book-edit-3.PNG)

After entering the command, both copies will have their author removed. However, only the copy at the first index will have its tag removed, because different book copies can have different tags.
![book-edit-4.png](images/book-edit-4.PNG)

### Deleting a book : `book delete`

To delete a specific book, you can enter the `delete` command with the format shown below.

**Format**: `book delete INDEX`

<div markdown="span" class="alert alert-primary">

**Notes about the delete command:**

* Deletes the book at the specified `INDEX`. `INDEX` refers to the index number shown in the displayed book list. The index **must be a positive integer** 1, 2, 3, …
* You cannot delete a book that is borrowed. If you insist on deleting the book, you can first return the book.
</div>

**Example**:
* To delete the first book, you can enter the following command:

`book delete 1`

Before entering the command, there is a copy of `Harry Potter and The Philosopher's Stone` as the first book.
![book-delete-1.png](images/book-delete-1.PNG)

After entering the command, that copy of `Harry Potter and The Philospher's Stone` is deleted.
![book-delete-2.png](images/book-delete-2.PNG)

### Borrowing a book : `borrow`

To keep track that a specific patron is borrowing a specific book, you can enter the `borrow` command with the format shown below.

**Format**: `borrow INDEX1 INDEX2 RETURN_DATE`

<div markdown="span" class="alert alert-primary">

**Notes about the borrow command:**

* `INDEX1` refers to the index number of a patron shown in the displayed patron list.
* `INDEX2` refers to the index number of a book shown in the displayed book list.
* Both `INDEX1` and `INDEX2` **must be a positive integer** 1, 2, 3, …
* `RETURN_DATE` must be in dd-MMM-yyyy format (e.g. 20-May-2022) and must be later than the current date.
* A patron cannot borrow multiple copies of the same book with the same isbn.
* A patron cannot borrow a book that is already borrowed by someone else.
</div>

**Example**:
* To keep track that the first patron is borrowing the first book until a return date of 5th May 2022, you can enter the following command:

`borrow 1 1 05-May-2022`

Before entering the command, the first book is available.
![book-borrow-1.png](images/book-borrow-1.PNG)

After entering the command, that first book is labelled as borrowed, and is borrowed by the first patron Alex Yeoh, until a return data of 05-May-2022.
![book-borrow-2.png](images/book-borrow-2.PNG)

### Returning a book : `return`

To return a specific book, or to return all books by a specific patron, you can enter the `return` command with the format shown below.

**Format**: `return PREFIX/INDEX`

<div markdown="span" class="alert alert-primary">

**Notes about the return command:**

* `PREFIX` must be either `p` for patrons or `b` for books.
* If `PREFIX` is `p`, `INDEX` refers to the index number of the patron who is returning books, as shown in the displayed patron list.
* If `PREFIX` is `b`, `INDEX` refers to the index number of the book to be returned, as shown in the displayed book list.
* `INDEX` **must be a positive integer** 1, 2, 3, …
* If the book at index `INDEX` is not borrowed, or if the patron at index `INDEX` does not borrow any books, nothing happens.
* If there are patrons who requested to be notified about the availability of the returned books, you will be reminded to notify them. Subsequently, all requests for those books will be deleted automatically.
* You cannot return a specific book if it is not borrowed
* You cananot return all books by a patron if the patron did not borrow any books.
</div>

**Examples**:
* To return all books borrowed by the first patron, you can enter the following command:

`return p/1`

Before entering the command, there are three books borrowed by the first patron. There are also two patrons requesting to be notified when the third book becomes available.
![book-return-1.png](images/book-return-1.PNG)

After entering the command, all three books borrowed by the first patron is returned. Furthermore, you are reminded to notify the two patrons who requested for the third book.
![book-return-2.png](images/book-return-2.PNG)

* To return only the third book, you can enter the following command:

`return b/3`

Before entering the command, the third book is labelled as borrowed, and has two requesters requesting to be notified when it becomes available.
![book-return-3.png](images/book-return-3.PNG)

After entering the command, the third book becomes available. The two requesters are also removed as you are reminded to notify both of them about the availability of the returned book.
![book-return-4.png](images/book-return-4.PNG)

### Requesting a book : `book request`

To keep track that a specific patron is requesting for a specific book, you can enter the `request` command with the format shown below.

**Format**: `book request INDEX1 INDEX2`

<div markdown="span" class="alert alert-primary">

**Notes about the request command:**

* `INDEX1` refers to the index number of the patron who is requesting for the book, as shown in the displayed patron list.
* `INDEX2` refers to the index number of the book to be requested, as shown in the displayed book list.
* Both `INDEX1` and `INDEX2` **must be a positive integer** 1, 2, 3, …
* Book requests are associated with books with the same isbn, not a book copy. For example, when patron Alex requests for the first book, LibTask recognizes that Alex is requesting for books with the same isbn as the first book.
* A patron can only request for a book if all copies of the book is borrowed. This is because if there is an available copy of the book, you can let the patron borrow that copy without requesting for it.
* A patron cannot request for the same book again if LibTask is still keeping track of his previous request.
* A patron cannot request for a book if he/she is currently borrowing a copy of that book.
* A book can have a maximum of three book requests. There is no need in having too many book requests because ultimately only one patron can borrow that book.
</div>

**Example**:
* To keep track that the second patron is requesting for first book, you can enter the following command:

`book request 1 1`

Before entering the command, there are two books with the same isbn as the first book. Both copies of the book are already borrowed.
![book-request-1.png](images/book-request-1.PNG)

After entering the command, both books are labelled as requested by Bernice Yu (the name of the second patron).
![book-request-2.png](images/book-request-2.PNG)

When a copy of this book becomes available later, you will be reminded to notify Bernice Yu automatically.

### Listing all books related to a patron : `book related`

To list all books borrowed by or requested by a specific patron, you can enter the `related` command with the format shown below.

**Format**: `book related INDEX`

<div markdown="span" class="alert alert-primary">

**Notes about the related command:**

* Lists all books borrowed by or requested by a patron at `INDEX`. `INDEX` refers to the index number of the patron of interest, as shown in the displayed patron list.
* If the patron of interest did not borrow any book or request for any book, an empty book list will be shown.
</div>

**Example**:
* To list all books borrowed by or requested by the second patron, you can enter the following command:

`book related 2`

Before entering the command, the book list displays all books in LibTask.
![book-related-1.png](images/book-related-1.PNG)

After entering the command, the book list displays only books that are borrowed by or requested by Bernice Yu (the second patron). In this case, only one book is shown because Bernice Yu did not borrow any book, and requested for one book.
![book-related-2.png](images/book-related-2.PNG)

This command is typically used after `patron overdue` command. After listing all patrons with overdue books, you can use this command to check which books are overdue by each patron.

### Editing the data file

LibTask's data is saved as a JSON file `[JAR file location]/data/libtask.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation:
**Caution:**
If your changes to the data file makes its format invalid, LibTask will discard all data and start with an empty data file at the next run.
</div>

## FAQ

**Q**: How does this app help current librarians?<br>
**A**: The app helps librarians manage the statuses of books borrowed along with their borrowers.

--------------------------------------------------------------------------------------------------------------------

## Command summary

### Category: General Commands

| Function                                   | Format Of Command                                                          |
|--------------------------------------------|----------------------------------------------------------------------------|
| **Show message to help page**              | `help`                                                                     |
| **Clear all entries**                      | `clear`                                                                    |
| **Exit the program**                       | `exit`                                                                     |
| **Show previous commands**                 | `u`                                                                        |
| **Navigating the different patrons/books** | **Pressing the :arrow_up: and :arrow_down: arrows on keyboard :keyboard:** |

### Category: Patron Commands

| Function                            | Format Of Command                                                 |
|-------------------------------------|-------------------------------------------------------------------|
| **Add a new patron**                | `patron add n/NAME s/ID p/PHONE e/EMAIL [t/TAG]…​`                |
| **List all patrons**                | `patron list`                                                     |
| **Edit a patron**                   | `patron edit INDEX [n/NAME] [s/ID] [p/PHONE] [e/EMAIL] [t/TAG]…​` |
| **Find a patron**                   | `patron find n/KEYWORD [n/KEYWORD]…​`                             |
| **Delete a patron**                 | `patron delete INDEX`                                             |
| **List patrons with overdue books** | `patron overdue`                                                  |

### Category: Book Commands

| Function                               | Format Of Command                                                   |
|----------------------------------------|---------------------------------------------------------------------|
| **Add a book**                         | `book add n/NAME i/ISBN [a/AUTHOR]…​ [t/CATEGORY_TAG]…​`            |
| **List all books**                     | `book list`                                                         |
| **Find a book**                        | `book find [n/Name] [t/Tag] [a/Author]`                             |
| **Edit a book**                        | `book edit INDEX [n/NAME] [i/ISBN] [a/AUTHOR]…​ [t/CATEGORY_TAG]…​` |
| **Delete a book**                      | `book delete INDEX`                                                 |
| **Borrow a book**                      | `borrow INDEX1 INDEX2 RETURN_DATE`                                  |
| **Return a book**                      | `return PREFIX/INDEX`                                               |
| **Request a book**                     | `book request INDEX1 INDEX2`                                        |
| **List all books related to a patron** | `book related INDEX`                                                |
