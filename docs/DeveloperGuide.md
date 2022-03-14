---
layout: page
title: Developer Guide
---
* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `patron delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PatronListPanel`, `BookListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Patron` and `Book` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `LibTaskParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddPatronCommand` or `AddBookCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a Patron or a Book).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("patron delete 1")` API call.

![Interactions Inside the Logic Component for the `patron delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteBookCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `LibTaskParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddPatronCommandParser` or `AddBookCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPatronCommand` or `AddBookCommand`) which the `LibTaskParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddPatronCommandParser`, `AddBookCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the libtask data i.e., all `Patron` objects (which are contained in a `UniquePatronList` object) and `Book` objects (which are contained in a `UniqueBookList` object).
* stores the currently 'selected' `Patron` and `Book` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Patron>` abd `ObservableList<Book>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `LibTask`, which `Patron` references. This allows `LibTask` to only require one `Tag` object per unique tag, instead of each `Patron` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2122S2-CS2103T-W14-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both libTask data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `LibTaskStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire libTask.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `patron delete`, just save the patron being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* a librarian who wants to manage book loans and requests by patrons
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: simplifying how librarians manage the status of library book loans and requests by patrons


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                   | I want to …​                                                                  | So that …​                                                                                                                            |
|----------|-----------------------------------------------------------|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| `*`      | Librarian                                                 | add a patron (student or staff) to my database                                | I can keep track of books borrowed or requested by the patron                                                                         |
| `*`      | Librarian                                                 | delete a patron from my database                                              | I do not get overwhelmed by data of graduated students or retired staff.                                                              |
| `*`      | Admin in charge of updating student records               | update information about patrons in my database                               | I have their latest contact when I need to contact them                                                                               |
| `*`      | Efficient librarian                                       | filter patrons in my database based on books                                  | I can know which patrons are requesting or borrowing the book                                                                         |
| `*`      | Efficient librarian                                       | find patrons based on categories                                              | I do not get overwhelmed by long lists of patrons in uncategorized search results                                                     |
| `*`      | Admin                                                     | view information of a specific patron                                         | I can see the books they borrowed or requested for                                                                                    |
| `*`      | Efficient librarian                                       | view patrons who have not returned overdue books                              | I can automate the process of sending reminders instead of sending reminders individually                                             |
| `*`      | Librarian                                                 | add a book to my database                                                     | I can keep track of details of the book                                                                                               |
| `*`      | Easily overwhelmed librarian                              | delete a book from my database                                                | I do not get overwhelmed by information about books that no longer exist                                                              |
| `*`      | Careless librarian                                        | edit information about a book                                                 | I can correct typos in the details of the books when I first added them to the database                                               |
| `*`      | Book recommender                                          | classify books into categories                                                | I can more easily provide book recommendations                                                                                        |
| `*`      | Request handler                                           | filter books based on authors and keywords                                    | I can quickly navigate the book when a patron is requesting it                                                                        |
| `*`      | Forward looking librarian                                 | filter books based on their borrowing date and expected return date           | I can have an estimate of my incoming workload for processing returned books                                                          |
| `*`      | Librarian                                                 | store details about a book loan by a patron                                   | I can perform tasks such as sorting, searching or categorizing books and patrons later                                                |
| `*`      | Librarian                                                 | update return and order status of books                                       | I can keep track of a book’s availability                                                                                             |
| `*`      | Request handler                                           | take note of requests from students to be notified when a book is returned    | I can easily notify the student when the book under request is returned                                                               |
| `*`      | Request handler                                           | take note of book order requests from professors                              | I can easily inform the professor when the book has arrived                                                                           |
| `*`      | Librarian                                                 | view all books in my database                                                 | I can make use of the list to perform other tasks                                                                                     |
| `*`      | Librarian overwhelmed with lots of book requests          | delete book requests I have                                                   | records of book requests are minimized by not keeping track of cancelled and fulfilled requests                                       |
| `*`      | New user                                                  | be able to access help                                                        | I can learn how to use the application                                                                                                |
| `*`      | User who has done learning how to use the application     | remove all the current entries and sample data                                | I can start a fresh and input my own data                                                                                             |
| `*`      | User                                                      | exit the application                                                          | I can leave the application                                                                                                           |
| `*`      | User with strong personal preference                      | change the background theme of the dashboard                                  | it looks more visually appealing to me and motivates me to use the application                                                        |
| `*`      | User                                                      | be able to adjust the window size                                             | I can see the data more clearly.                                                                                                      |
| `*`      | Efficient user who is familiar with CLI tools             | refer to recent commands using up and down arrows                             | I can save time from retyping past commands                                                                                           |
| `*`      | User with poor eyesight and strong personal preference    | edit font size and style                                                      | I can read contents on the application better                                                                                         |
| `*`      | User who likes to stay on top of tasks                    | have a short note taking session to take down notes                           | I do not need to remember small details.                                                                                              |
| `*`      | Admin in charge of sending emails                         | be able to export a group of emails such as overdue emails based on templates | I can craft personalized emails without the need to draft them individually                                                           |
| `*`      | Efficient librarian                                       | continue where I left off from the previous days                              | I can continue to deliver to my borrowers the books they wanted                                                                       |
| `*`      | Librarian in charge of placing book orders                | view statistics for different book categories                                 | I can make important decisions for the library such as getting more copies of a certain book/getting more books of a certain category |
| `*`      | Book recommender who used the application for a long time | recommend books using stored statistics of most frequently requested books    | I can make better recommendations for students that come to us                                                                        |
| `*`      | Impatient user                                            | be able to experience reasonable response time while using the application    | I can do my job more efficiently                                                                                                      |
| `*`      | Easily overwhelmed request handler                        | be able to prioritize my book requests                                        | I can effectively handle requests                                                                                                     |
| `***`    | Librarian                                                 | be able to view patron and book details through a GUI                         |                                                                                                                                       |
| `***`    | Librarian                                                 | have visual feedback of the commands I executed                               | I will know if my commands have been successfully executed                                                                            |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `LibTask` and the **Actor** is the `user`, unless specified otherwise)

### UC01: Adding a patron to LibTask

**MSS**

1. User requests to add a patron and provides the name and details of the patron.

2. LibTask adds the patron.

   Use case ends.

**Extensions**

* 1a. Compulsory fields are not provided.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

* 1b. The patron details are invalid.

    * 1b1. LibTask shows an error message.

      Use case resumes from step 1.

* 1c. The given name and details are duplicated.

    * 1c1. LibTask shows an error message.

      Use case resume from step 1.


### UC02: List patron's on LibTask

**MSS**

1. User requests to list all patrons

2. LibTask shows the list of all the patrons.

   Use case ends.

**Extensions**

* 2a. LibTask has no stored patrons.

    * 2a1. LibTask shows an empty list.

      Use case ends.

### UC03: Editing a patron on LibTask

**MSS**
1. User list all patrons [UC02](#uc02-list-patrons-on-libtask)

2. User requests to edit a patron and provide the index and the necessary details to be edited.

3. LibTask edits the patron.

   Use case ends.

**Extensions**

* 1a. The index is not provided or is invalid.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

* 1b. The details are not provided or are invalid.

    * 1b1.  LibTask shows an error message.

      Use case resume from step 1.


### UC04: Find a patron on LibTask

**MSS**

1. User request to find patron(s) and provide a number of keywords.

2. LibTask shows the list of patrons that match the search.

   Use case ends.

**Extensions**

* 1a. Keywords not provided or are invalid.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

* 2a.  No patrons fulfil the search criteria.

    * 2a1. LibTask shows an empty list.

      Use case ends.


### UC05: Delete a patron from LibTask

**MSS**

1. User list all patrons [UC02](#uc02-list-patrons-on-libtask).

2. User requests to delete a patron and provide the index.

3. LibTask deletes the patron.

   Use case ends.

**Extensions**

* 1a. The index is not provided or is invalid.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

### UC06: Add book to LibTask

**MSS**

1. User requests to add a book and provides the name and details of the book

2. LibTask adds the module.

   Use case ends.

**Extensions**

* 1a. Compulsory fields are not provided.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

* 1b. The book details are invalid.

    * 1b1. LibTask shows an error message.

      Use case resumes from step 1.

### UC07: List Books on LibTask

**MSS**
1. User requests to list all books.

2. LibTask shows the list of all the books.

   Use case ends.

### UC08: Edit a book on LibTask

**MSS**

1. User requests to edit a book and provides the index of the book and the new details.

2. LibTask edits the book.

   Use case ends.

**Extensions**

* 1a. The given index is invalid.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

* 1b. The new details are invalid.

    * 1b1. LibTask shows an error message.

      Use case resumes from step 1.

### UC09: Delete Book from LibTask

**MSS**

1. User requests to delete a book and provides the index.

2. LibTask deletes the book.

   Use case ends.

**Extensions**

* 1a. The given index is invalid.

    * 1a1. LibTask shows an error message.

      Use case resumes from step 1.

### UC10: Borrow Book

**MSS**

1. User lists all patrons [UC02](#uc02-list-patrons-on-libtask)

2. User lists all books [UC07](#uc07-list-books-on-libtask)

3. User requests to establish a borrow relationship and provides index of the patron and index of the book in lists, as well as return date of the book.

4. LibTask establishes a borrow relationship between the patron and the book.

   Use case ends.

**Extensions**

* 3a. The given index of patron or book is invalid.

    * 3a1. LibTask shows an error message.

      Use case resumes from step 3.

* 3b. The return date of the book is invalid.

    * 3a1. LibTask shows an error message.

      Use case resumes from step 3.

* 3b. The book is already borrowed.

    * 3a1. LibTask shows an error message.

      Use case resumes from step 3.

### UC11: Return Book on LibTask

**MSS**

1. User finds a patron [UC04](#uc04-find-a-patron-on-libtask)

2. User requests to end a borrow relationship and provides index of the patron and index of the book in lists.

3. LibTask removes the borrow relationship between the patron and the book.

Use case ends.

**Extensions**

* 2a. The given index of patron or book is invalid.

    * 2a1. LibTask shows an error message.

      Use case resumes from step 2.

* 2b. The book is not borrowed by the patron.

    * 2a1. LibTask shows an error message.

      Use case resumes from step 2.

### UC12: Asking for Help on LibTask

**MSS**
1. User requests to list all commands

2. LibTask shows the list of all the commands

   Use case ends.

### UC13: Exiting LibTask

**MSS**
1. User requests to exit LibTask

2. LibTask closes.

   Use case ends.

### UC14: Clear database of all Patron's and Book's

**MSS**

1. User requests to clear database

2. LibTask clears the database.

   Use case ends.

Extension

* 1a. The database is already empty

    * 1a1. LibTask shows an error message

      Use case ends

### UC15: Show previously run commands

**MSS**
1. User requests to see previous command

2. LibTask shows the last command used

   Use case ends.

Extension

* 1a. No previous command

    * 1a1. LibTask shows an empty field

      Use case ends.



### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 patrons and books without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. A novice with no coding background should be able to use the Lib Task.
5. The system should respond in 1 second.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Patron**: A user of the library
* **ISBN**: An International Standard Book Number 13 digits in length.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a patron

1. Deleting a patron while all patrons are being shown

    1. Prerequisites: List all patrons using the `patron list` command. Multiple patrons in the list.

    1. Test case: `patron delete 1`<br>
       Expected: First patron is deleted from the list. Details of the deleted patron shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `patron delete 0`<br>
       Expected: No patron is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `patron delete`, `patron delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

2. _{ more test cases …​ }_

### Deleting a book
1. Deleting a book while all books are being shown

   1. Prerequisites: List all books using the `book list` command. Multiple books in the list.

   1. Test case: `book delete 1`<br>
      Expected: First book is deleted from the list. Details of the deleted book shown in the status message. Timestamp in the status bar is updated.
   
   1. Test case: `book delete 0`<br>
      Expected: No book is deleted. Error details shown in the status message. Status bar remains the same.
   
   1. Other incorrect delete commands to try: `book delete`, `book delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
