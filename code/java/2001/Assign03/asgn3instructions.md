

# COMP2001 F2022 Assignment 3 Instructions

## Objectives

Try unit testing.

The problem concerns a comment list on a salesitem.

## Activity

* Checkout comp2001-assign3 repository from github classroom for the following credit work
* No java packages are needed as part of this project structure.
* You will create your own testcase Class for the project. Your class will contain four tests composed by you. Put your test case Class in the top folder with the other classes.
* We run your testCase against the code in the project to make sure your tests are correctly written. This is worth 4 points.
* We sill also run your test case against three other implementations of the classes SalesItem and Comment. These implementations will have different bugs, which you tests should detect by failing some of the tests you have written.
* The tests of three variations of buggy code will count for an additional 12 points, for a total of 16 points on this assignment.
* You can try it out your tests by introducing bugs into the code yourself and making sure your tests fail them.
* Call your test class "SalesTest".
  1. Write the first test method `testUpVate()`. Use it to test that `Comment.upvote` correctly increments the comment vote.
  2. Write the second test method `testPrice()`  to make sure a price is correctly set by the priceString() method and it returns the correct value.
  3. Write the third test method called `testHelp()` to confirm that the result of `findMostHelpfulComment` is correctly affected when items are upvoted or downvoted. Check both upvoting and downvoting for problems.
  4. Write the fourth test called `testNoComments()` to check that `findMostHelpfulComment()` returns null when there are no comments. (This test will fail in the provided implementation, but pass in some implementations which do not have this bug.)