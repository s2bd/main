

# COMP2001 F2022 Assignment 1 Instructions

## Objectives

Practice ArrayList coding.

## Activity

* The assignment is based on (and extends) exercises 4.56 - 4.60 in the textbook.
* Checkout comp2001-assign1 repository from github classroom for the following credit work
*  Confirm that the **inventory** package and the **grading** package are present in the project
* Open the assign1 project `inventory` package  
* Modify the **StockManager** class to implement the methods as described in **StockManagerInterface** so you can compile the java code in the package.  This will produce compile errors until the method signatures required by the **StockManagerInterface** are satisfied by your code changes to **StockManager**. Note the declaration line of **StockManager** has been modified to indicate the implementation requirement to the compiler.
  * In your solution, make changes to StockManager code but do not alter the class declaration line

           public class StockManager implements StockManagerInterface
  * Do not alter the StockManagerInterface class.
* Modify the **StockManager** class to comply with the method signatures required by the  **StockManagerInterface**. This should allow the **StockManager** class to compile without compile errors.
    * Add an additional ArrayList of Product called onOrder to the StockManager class.  The quantity field of Product in the onOrder ArrayList is used to indicate the number of items on order, instead of the number of items in stock.
* Open the **grading** package to run the JUNIT tests. You should now be able to compile and run the JUNIT tests included in **StockManagerTest** class of the **grading** package to check your lab score based on the run time behaviour of your **StockManager** code. Test scores should appear in the terminal window when you run the JUNIT tests. Results of the tests and the comments in the **StockManagerInterface** will help you figure out how to correctly implement the interface in order to pass the tests.

  * The tests depend on **StockManager.loadSampleData** method so you should implement that early. This method should load the data from a two dimensional array into the stock ArrayList. Note the tests will be using your method on the array in the class **StockData**.  (This is our substitute for loading data from disk, as we haven't covered I/O operations yet.) Do not modify the data in StockData class.
* Modify the **package-info** file to include your name as author and identify any assistance you obtained using javadoc @author and @attribution tags as shown in the file.
* Commit and push your project that includes your modification to the **StockManager** class _before the due date_.  Only the most recent push to the remote git repository at github classroom prior to the end of the due date will count toward the grade. Since you can commit and push as many times as you want, you should consider pushing your changes wherever your score improves.

