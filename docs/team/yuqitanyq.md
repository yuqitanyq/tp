---
layout: page
title: Tan Yu Qi's Project Portfolio Page
---

### Project: LibTask

LibTask is a desktop application used by librarians to keep track of books, overdue books, and contact details of patrons who have borrowed them and those who requested them. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on AddressBook - Level 3.

Given below are my contributions to the project.

### Summary of Contributions

* **New Feature**:
  * Added the ability to edit the different fields of a book
    * What it does:
      * Allows users to edit a book currently in the list. It references the book based on the index shown on the list.
      * Fields which can be edited include the book name, ISBN, author and tags.
    * Justification:
      * This feature improves the product significantly because a user can make mistakes when adding a new book and the application should provide a convenient way to rectify them.
      * Instead of asking the user to delete the book that they created wrongly, and add the book again, this feature allows them edit only the wrong details of the book that they added.
    * Highlights:
      * This enhancement affects how users directly interact with the application and adds tremendous value to the user as well. Despite it being challenging to understand the entire system, the command is adapted quite similarly to the existing code base. This command does not affect other commands directly and implementation has been kept to be as simple as possible, requiring little changes to other commands.
      * A parser is used to parse the command before the logic of the command gets executed.

  * Added the ability to list all the books currently in the database
    * What it does:
      * Allows users to list all the books currently in the database.
    * Justification:
      * This feature improves the product significantly as it gives the user an option to list all the books and see everything in one glance.
      * It provides  users a way to transit into viewing all books in the list from another state such from a list of books related to a patron (from the book related command).
    * Highlights:
      * This enhancement is implemented rather similarly as to how the list command is implemented in AB3 originally, but with minor tweaks to accommodate to the book object and book list.

  * Added the ability to find all books related to a particular patron
    * What it does:
      * Allows users to get a list of books related to a particular patron.
    * Justification:
      * The feature will improve the product significantly as it will help users to filter the books that are related to a particular patron of concern.
    * Highlights:
      * A parser is used to parse the command before the logic of the command is executed.
    * Credits:
      * Worked with [Yong Liang](http://github.com/yl-ang) to implement this enhancement.

* **Code contributed**:
  * The code contributed by me can be found in this [link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=yuqitanyq&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18).

* **Project management**:
  * Frequently reviewed pull requests of other team members and wrote commits in a clear and orderly manner.
  * Resolved merge conflicts when it arises before merging.
  * Communicated with team members and ensured deliverables are delivered for each iteration.

* **Enhancements to existing features**:
  * Added Tests cases for EditBookCommand
  * Added Tests cases for ListBookCommand

* **Documentation**:
  * User Guide:
    * FAQ Command Summary
    * Documentation for EditBookCommand
    * Documentation for ListBookCommand

  * Developer Guide:
    * user story for EditBookCommand
    * user story for ListBookCommand
    * logic illustration of EditBookCommand, ListBookCommand and RelatedBookCommand.


* **Community**:
  * Raised issues on the forum if necessary and gave detailed descriptions of problems.


* **Tools**:
  * Gradle
  * Intellij
  * Assertions

