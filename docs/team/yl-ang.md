---
layout: page
title: Ang Yong Liang's Project Portfolio Page
---


### Project: LibTask

LibTask is a desktop application used by librarians to keep track of books, overdue books, and contact details of patrons who have borrowed them and those who requested them. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on AddressBook - Level 3.

Given below are my contributions to the project.

* **New Feature**:
  * Added the ability to view patron and book details through LibTask GUI [\#49](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/49)
    * What it does:
      * Allows users to view patron and book details quickly via the GUI.
    * Justification:
      * The feature will improve the users' efficiency as they are able to get the details
    * Highlights:
      * This enhancement was challenging as it requires modifying the FlowPane and setting the FXML labels to render the patron and book cards in the main window.
      * In addition, it requires some measurements to ensure that the patron and book cards are rendered in-place.
    * Credits:
      * Using Scene builder to modify FXML files.
      * Understanding GridPane to evenly divide the GUI space for the respective patron and book lists via the [doc oracle guide](https://docs.oracle.com/javafx/2/get_started/fxml_example.fxml.html).
  * Added the ability to find all books related to a particular patron [\#97](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/97), [\#101](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/101)
    * What it does:
      * Allows users to get a list of books related to a particular patron.
    * Justification:
      * The feature will improve the product significantly as it will help users to filter the books that are related to a particular patron of concern.
    * Highlights:
      * This enhancement was rather similar to the `patron find` command. However, the command takes in a patron index instead of keywords.
      * Furthermore, it is challenging as it requires the creation of a predicate to obtain the books that belong to the particular patron of concern.
    * Credits:
      * Worked with [Yu Qi](https://github.com/yuqitanyq) to implement this enhancement.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2022-02-18&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=yl-ang&tabRepo=AY2122S2-CS2103T-W14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Changing product icon
  * Reviewed and approved PRs for merging
  * Proposed design suggestions
  * Created `v1.3`, `v1.4`, `v1.4b` milestones

* **Enhancements to existing features**:
  * Updated the GUI to show book requests [\#111](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/111)
  * Updated the GUI to show book status [\#84](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/84)
  * Updated the GUI to show previous command [\#72](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/72)
  * Updated the GUI to show patron card details [\#63](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/63)
  * Added test cases for Book and Patron commands [\#88](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/88)

* **Documentation**:
  * User Guide:
    * Added documentation for the feature `book related` [\#112](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/112)
    * Added documentation for `quick start` and `introduction` [\#16](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/16)
    * Enhanced documentation for `tutorial`.
    * Added glossary section [\#138](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/138).

  * Developer Guide:
    * Added MSS for related book command [\#112](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/112)
    * Updated hyperlinks and terms [\#75](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/75)
    * Updated UiClassDiagram [\#209](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/209)
    * Added Sequence and activity diagrams for book related command [\#209](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/209)

* **Review/mentoring contributions**:
  * PR reviewed (with non-trivial review comments): [\#230](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/230), [\#214](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/214), [\#148](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/148), [\#144](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/144), [\#121](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/121), [\#100](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/100), [\#96](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/96), [\#95](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/95), [\#83](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/83), [\#79](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/79), [\#78](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/78), [\#73](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/73), [\#69](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/69), [\#66](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/65), [\#62](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/65), [\#60](https://github.com/AY2122S2-CS2103T-W14-1/tp/pull/60)

* **Community**:
  * Bugs reported for other teams can be found [here](https://github.com/yl-ang/ped/issues).

* **Tools**:
  * Assertions
  * Gradle
  * Intellij
  * Scene Builder
