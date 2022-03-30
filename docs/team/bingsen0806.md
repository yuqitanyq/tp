---
layout: page
title: Lim Bing Sen's Project Portfolio Page
---

### Project: LibTask

LibTask is a desktop application used by librarians to keep track of books, overdue books, and contact details of patrons who have borrowed them and those who requested them. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on AddressBook - Level 3.

### Summary of contributions

* **Code contributed**: I personally contributed almost 4 KLoC to Mod Manager. All my code contributions can be found [here](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=bingsen&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=bingsen0806&tabRepo=AY2122S2-CS2103T-W14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false).

* **Enhancements implemented**: Idealise and design the `Book` component of `LibTask`. The `Book` component includes
  * What it does: every book has a book name, isbn, list of authors and set of tags (e.g. category tag such as _SciFi_, or location tag such as _Central_). It also contains a status, which can be _Available_ or _Borrowed_. If the status is _Borrowed_, then the `Book` stores a patron as borrower, and also stores the borrow and return date. Furthermore, a `Book` can also stores a list of requesters who are requesting to be notified about its availability, if it is currently borrowed. The introduction of `Book` component allows librarians to manage book related tasks easily, such as CRUD commands, and tracking of book statuses and patron's request for books.

  * Justification:This enhancement improves the product significantly and is a vital component of `LibTask` because it provides a data representation for librarians to keep track of books. Without this `Book` component, a librarian cannot store persistent data of books in `LibTask`. Furthermore, some subcomponents of `Book`, such as tags and requesters blends in well with `LibTask`'s use case. For example, tags allow the librarian to find books base on tags and requesters allows busy librarians to be reminded to notify patrons when the book of interest is available.

  * Highlights: The implementation of this enhancement is tedious and very time-consuming. After putting in much effort to understand the original AddressBook (Level 3) code, each of the minor subcomponents requires a set of classes, checks, JUnit tests, documentation and design thought process. For example, to implement only one `Isbn` subcomponent, research on conventional Isbn formats is needed, followed by implementation of a rigorous checking algorithm to ensure validity of the Isbn, and a new class JsonAdaptedIsbn to convert Isbn into json format, and lastly unit tests and Javadocs for all classes. The same level of work is required for other subcomponents such as `Author`, `BookName`, `Requester`, `Status`, `StatusType` etc, and a total of 4kLoC can be attributed to all these changes alone. Aside from tediousness, careful planning and design considerations are also made. For example, much consideration is made before deciding to let `Book` class depend on `Patron` class instead of having two-way cyclic dependency. This design has a downside of requiring all related book objects to be updated whenever a related patron is modified. However, it solves the problem of cyclic updates and not being able to store objects with cyclic references as json format. Good communication skills is required as I need to constantly update the team about high-level design changes, especially the UI team, so that my teammates can have the correct assumption of the `Book` abstraction provided to them when implementing the UI and commands.

* **New Command Features implemented**:
  * Add book command:
  * Delete book command:
  * Borrow command:
  * Return command:
  * Request book command:

* **User Guide contribution**:

* **Developer Guide contribution**:

* **Team-based tasks contributions**:
  * Created team organization and setup CI/CD and workflow.
  * Setup team repo, CodeCov, GitHub actions.
  * Set target and deadlines for milestone v1.2, v1.2b, and v1.3. Noticeably led team discussions and provided the team with a direction on features to be prioritized and features to be cut down.
  * Contributed to more than 100 PR review comments as shown over [this dashboard](https://nus-cs2103-ay2122s2.github.io/dashboards/contents/tp-comments.html).
  * Initiated multiple zoom sessions to help teammates debug code

* **Project management**:
  * Ensure that all milestone progress are reached (which includes team-based and individual-based tasks). We managed to achieve 100% of the recommended milestone tasks, including early wrap up for both v1.2 (only achieved by 11% of the cohort) and v1.3. 
  * Managed releases [v1.2](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases/tag/v1.2) and [v1.3.trial](https://github.com/AY2122S2-CS2103T-W14-1/tp/releases/tag/v1.3.trial) on GitHub 
  * Maintained the issue tracker and milestones for team repo

* **Community**:
  * Authored multiple helpful forum posts to help peers. For example, [this post on how to resolve GitHub action issues](https://github.com/nus-cs2103-AY2122S2/forum/issues/115)

