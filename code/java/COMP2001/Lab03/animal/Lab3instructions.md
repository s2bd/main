# Lab 3 instruction COMP 2001 F 2022

The problems are loosely based on the exercises presented in the book. 

Note that the code in this submission is to be written in functional style, with no explicit while or for loops. You will fail unit tests if you user for or while in your code submission.

## Non Credit practice problems
1.  Open the _animal-monitoring-v1_ project
    1.  Create an instance of the **AnimalMonitor** class. The file sightings.csv in the project directory contains a small sample of sighting records in comma-separated values (CSV) format. Pass the name of this file (“sightings.csv”) to the **addSightings** method of the **AnimalMonitor** object and then call **printList** to show details of the data that has been read.
    2.  Try out the other methods of the **AnimalMonitor** object, using animal names, spotter and area IDs shown in the output from **printList**.
    3.  Read through the full source code of the **AnimalMonitor** class to understand how the methods work.
    4.  Why does the **removeZeroCounts** method use a while loop with an iterator, instead of a for-each loop as used in the other methods? Could it be written with a for-each loop? Could the other methods be written using a while loop?  
          
        
2.  Continue working on the _animal-monitoring-v1_ project (now you will be using **lambdas** and **streams**)
    1.  Rewrite the **printList** method in your version of the **AnimalMonitor** class to use a lambda, as shown in class.
    2.  Write a method in the **AnimalMonitor** class to print details of all the sightings recorded on a particular reporting period, which is passed as a parameter to the method.
    3.  Write a method that uses two **filter** calls to print details of all the sightings of a particular animal made on a particular reporting period—the method takes the animal name and reporting period as parameters.
    4.  Does the order of the two filter calls matter in your solution? Justify your answer.
    5.  Rewrite the **printSightingsOf** method in your **Animal­Monitor** class to use streams and lambdas. Test to make sure the project still works as before.
    6.  If a pipeline contains a filter operation and a map operation, does the order of the operations matter to the final result? Justify your answer.
    7.  Rewrite the **printEndangered** method in your project to use streams. Test. (To test this method, it may be easiest to write a test method that creates an **ArrayList** of animal names and calls the **printEndangered** method with it.)
    8.  Rewrite your **getCount** method using streams
    9.  Add a method to **AnimalMonitor** that takes three parameters: **animal**, **spotterID**, and **reporting period**, and returns a count of how many sightings of the given animal were made by the spotter on a particular reporting period.
    10.  Add a method to **AnimalMonitor** that takes two parameters—**spotterID** and **reporting period**—and returns a **String** containing the names of the animals seen by the spotter on a particular reporting period. You should include only animals whose sighting count is greater than zero, but don’t worry about excluding duplicate names if multiple non-zero sighting records were made of a particular animal. Hint: The principles of using reduce with String elements and a String result are very similar to those when using integers. Decide on the correct identity and formulate a two-parameter lambda that combines the running “sum” with the next element of the stream.
    11.  Rewrite the **removeZeroCounts** method using the **removeIf** method of the ArrayList collection.
    12.  Write a method **removeSpotter** that removes all records reported by a given spotter.  
          
        
3.  Open the _music-organizer-v5 project_ (chapter 4)
    *   Rewrite the **listAllTracks** and **listByArtist** methods of the MusicOrganizer class to use streams and lambdas.  
          
        
4.  Find out about Java’s **switch-case** statement. What is its purpose? How is it used? Write an example. (This is also a control flow statement, so you will find information in similar locations as for the do-while loop.)

## Credit problem

5. Accept the invitation for lab3, and checkout the repository which includes elements from part 2 above with some extensions. The AnimalMonitor interface to implement and some of the tests are provided in the usual grading package.  The remaining tests will be available at the beginning of lab 3. Remember to push your solution before the lab time expires.