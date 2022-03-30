---
layout: page
title: Sarthak Pradhan's Project Portfolio Page
---

### Project: LibTask


LibTask is a desktop application used by NUS libraries to keep track of books, overdue books, and contact details of patrons who have borrowed them and those who requested them.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on AddressBook - Level 3.

Given below are my contributions to the project.

* **New Feature**:
  * Added the ability for users to view their previous commands
    * What it does
      * Allows users to make changes to their previous commands if needed. 
    * Justification
      * It will speed up the process should the user wish to key in multiple similar commands
      * Will also allow them to check if any of the fields were incorrectly entered.
    * Highlights
      * This command changes the way users can interact with LibTask, making it more efficient for librarians who have to do similar work repeatedly.
    * Credits
      * Worked with [Ang Yong Liang](http://github.com/yl-ang) to make sure that the previous command would show up in the CommandBox.
  
  * Added the ability for users to search for a book by its category tag, author or name.
    * What it does
      * Allows users to quickly find certain books using a search query
    * Justification
      * It will make it easier for a librarian to find all books that are part of the same category should a student ask for similar books
      * It would also be able to search for multiple editions of the same book if they are not sure which is the one that they need to find. 
    * Highlights
      * This is quite similar to the patron find command however it can now take in multiple arguments rather than just one.
      * Requires more work to ensure that only one argument is parsed into the software since it can be one of 3. 

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2022-02-18&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=Halpfrog&tabRepo=AY2122S2-CS2103T-W14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


* **Project management**:
  * Approved and reviewed PR's for merging


* **Enhancements to existing features**:
  * Added test cases for PreviousCommand
  * Added test cases for BookFindCommand


* **Documentation**:
  * User Guide:
    * Documentation for PreviousCommand
    * Documentation for BookFindCommand
  * Developer Guide:


* **Community**:


* **Tools**:
  * Gradle
  * Intellij
  * Assertions


