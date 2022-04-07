---
layout: page
title: Lim Bing Sen's Project Portfolio Page
---

### Project: LibTask

LibTask is a desktop application used by librarians to keep track of books, overdue books, and contact details of patrons who have borrowed them and those who requested them. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 14 kLoC. This project is based on AddressBook - Level 3.

### Summary of contributions

**Code contributed**: I personally contributed almost 8 KLoC to LibTask. All my code contributions can be found [here](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=bingsen&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=bingsen0806&tabRepo=AY2122S2-CS2103T-W14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false).

**Enhancements implemented**: Idealise and design the `Book` component of `LibTask`.
  * What it does: Every book has a book name, isbn, list of authors and set of tags. It also contains a status, which can be _Available_ or _Borrowed_. If the status is _Borrowed_, then the `Book` stores a patron as borrower, and also stores the borrow and return date. Furthermore, a `Book` can also store a list of requesters who are requesting to be notified about its availability. The introduction of `Book` component allows librarians to manage book related tasks easily, such as CRUD commands, and tracking of book statuses and patron's request for books.

  * Justification: This enhancement improves the product significantly and is a vital component of `LibTask` because it provides a data representation for librarians to keep track of books. Without this component, a librarian cannot store persistent data of books in `LibTask`.

  * Highlights: The implementation of this enhancement is tedious and very time-consuming. Each of the minor subcomponents requires a set of classes, checks, JUnit tests, documentation and design thought process. For example, to implement only one `Isbn` subcomponent, research on conventional Isbn formats is needed, followed by implementation of a rigorous checking algorithm to ensure validity of the Isbn, and a new class JsonAdaptedIsbn to convert Isbn into json format, and lastly unit tests and Javadocs for all classes. The same level of work is required for other subcomponents such as `Author`, `BookName`, `Requester`, `Status`, `StatusType` etc, and a total of 3kLoC can be attributed to all these changes alone. Aside from tediousness, careful planning and design considerations are also made. For example, much consideration is made before deciding to let `Book` class depend on `Patron` class instead of having two-way reference. This design has disadvantages but more advantages as documented in the Model implementation section of Developer's Guide. Good communication skills is required as I need to constantly update the team about high-level design changes, especially the UI team, so that my teammates can have the correct assumption of the `Book` abstraction provided to them when implementing the UI and commands.
  
**New Command Features implemented**:
  * Add book command - A feature to add a book to LibTask's storage to keep track of books in the library.
  * Delete book command - A feature to delete book from LibTask's storage.
  * Borrow command - A feature to keep track of book loan information such as borrower and return date.
  * Return command - A feature to return borrowed books. This feature also allows the librarian to return all books by a patron in just one command.
  * Request book command - A feature for librarians to keep track of book requests by patrons. Whenever a requested book is added or returned, the librarian will receive a reminder message to notify the requesters.

#### Team-based tasks contributions + Project Management

* Created team organization and setup team repo, CodeCov, GitHub actions and workflow.

* Set target and deadlines for milestone v1.2, v1.2b, v1.3, v1.3b, v1.4 and v1.4b. Noticeably led team discussions and provided the team with a direction on features to be prioritized and features to be cut down. Also managed team milestone release.

* Accepted, rejected and assigned 50 bug reports to team members during Practical Exam Dry Run and decided on bugs to be prioritized.

* Contributed to more than 100 PR review comments as shown over [this dashboard](https://nus-cs2103-ay2122s2.github.io/dashboards/contents/tp-comments.html).

* Initiated multiple zoom sessions to help teammates debug code

* Ensured that all milestone progress are reached (which includes team-based and individual-based tasks). We managed to achieve 100% of the recommended milestone tasks, including early wrap up for both v1.2 (only achieved by 11% of the cohort) and v1.3.

* Managed releases [v1.2](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases/tag/v1.2), [v1.3.trial](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases/tag/v1.3.trial) and [v.1.3](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases/tag/v1.3) on GitHub.

#### Community

* Authored multiple helpful forum posts to help peers. For example, [this post on how to resolve GitHub action issues](https://github.com/nus-cs2103-AY2122S2/forum/issues/115)

* Reported significant number of bugs (top 10% of cohort) in other team's software product, thus helping the other team to improve their product. Bugs reported can be found [here](https://github.com/bingsen0806/ped/issues)

#### User Guide contribution

Given below are sections I contributed to the User Guide. They showcase my ability to write technical documentation targeting end users.

1. The entire Book Features section (e.g. section 3.3). This includes writing instructions, examples, screenshots and explanation of rationale behind why a certain feature is provided in a certain way, for the following commands:
   1. `book add` command
   2. `book delete` command
   3. `book edit` command
   4. `book find` command
   5. `book list` command
   6. `book request` command
   7. `book related` command
   8. `borrow` command
   9. `return` command

#### Developer Guide contribution

Given below are sections I contributed to the Developer Guide. They showcase my ability to write technical documentation and the technical depth of my contributions to the project.

* Design: Logic Component Section
  * Edited class diagrams and sequence diagrams for logic component.
  * Update explanations to execution of logic component

* Design: Model Component Section
  * Edited class diagrams and sequence diagrams for model component.
  * Update explanations to execution of model component.

* Design: Storage Component Section
  * Edited class diagrams and sequence diagrams for storage component.
  * Update explanations to execution of storage component.

* Implementation: Model Section
  * Added implementation details and design considerations of Model component, with the help fo class diagrams.

* Implementation: Saving books and patrons to json format
  * Added implementation details and design considerations on how Patron and Book objects are converted to json format for storage, with the help of class diagrams.
   
* Implementation: Borrow Feature
  * Added text explanation and sequence diagram to illustrate implementation details for borrow feature, as well as design considerations.
  * Added activity diagram to illustrate execution logic and flow of borrow feature.

* Implementation: Return Feature
  * Added text explanation and sequence diagram to illustrate implementation details for return feature, as well as design considerations.
  * Added activity diagram to illustrate execution logic and flow of return feature.

* Implementation: Request Feature
  * Added text explanation and sequence diagram to illustrate implementation details for request feature, as well as design considerations.
  * Added activity diagram to illustrate execution logic and flow of request feature.

* Manual Testing Section:
  * Added manual testing instructions for delete book, borrow book, return book, and request book commands.

