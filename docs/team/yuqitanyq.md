---
layout: page
title: Tan Yu Qi's Project Portfolio Page
---

### Project: LibTask

LibTask is a desktop application used by librarians to keep track of books, overdue books, and contact details of patrons who have borrowed them and those who requested them. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on AddressBook - Level 3.

Given below are my contributions to the project.

* **New Features**:
  * Added the ability to edit the different fields of a book [\#65](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/65)
    * What it does:
      * Allows users to edit a book currently in the list. It references the book based on the index shown on the list.
    * Justification:
      * This feature improves the product significantly because a user can make mistakes when adding a new book and the application should provide a convenient way to rectify them.
    * Highlights:
      * This enhancement was challenging as it required a clear understanding of how the existing AB3 system works, and how to best use the existing code base to create this feature.
      * In addition, it required a separate parser for the command and a robust integration between the command logic and the Book class.

  * Added the ability to list all the books currently in the database [\#69](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/69)
    * What it does:
      * Allows users to list all the books currently in the database.
    * Justification:
      * This feature improves the product significantly as it gives the user an option to list all the books and see everything in one glance.
    * Highlights:
      * This enhancement was challenging because it required a clear understanding of how the Book objects are rendered out.

  * Added the ability to find all books related to a particular patron [\#96](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/96)
    * What it does:
      * Allows users to get a list of books related to a particular patron.
    * Justification:
      * This feature will improve the product significantly as it will help users to filter the books that are related to a particular patron of concern.
    * Highlights:
      * This enhancement was rather similar to the `patron find` command. However, the command takes in a patron index instead of keywords.
      * Furthermore, it is challenging as it requires the creation of a predicate to obtain the books that belong to the particular patron of concern.
    * Credits:
      * Worked with [Yong Liang](http://github.com/yl-ang) to implement this enhancement.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=yuqitanyq&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18)

* **Project management**:
  * Frequently reviewed pull requests of other team members and wrote commits in a clear and orderly manner.
  * Resolved merge conflicts when it arises before merging.
  * Communicated with team members and ensured deliverables are delivered for each iteration.

* **Enhancements to existing features**:
  * Added test cases for EditBookCommand [\#65](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/65) [\#95](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/95)
  * Added test cases for ListBookCommand [\#69](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/69)
  * Added test cases for BookRelatedCommand [\#208](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/208)

* **Documentation**:
  * User Guide:
    * FAQ Command Summary [\#18](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/18) [\#136](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/136)
    * Documentation for EditBookCommand and ListBookCommand [\#113](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/113)
    * Glossary of terms [\#208](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/208)

  * Developer Guide:
    * Addition of user stories [\#19](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/19)
    * Manual Testing description for BookRelatedCommand [\#208](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/208)

* **Community**:
  * Review pull requests extensively (non-trivial review comments): [\#22](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/22) [\#48](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/48) [\#62](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/62) [\#66](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/66) [\#73](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/73) [\#88](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/88) [\#97](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/97) [\#101](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/101)

* **Tools**:
  * Assertions
  * Gradle
  * Intellij

