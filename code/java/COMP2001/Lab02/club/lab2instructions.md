# Lab 2 instruction COMP 2001 F 2022

The problems are loosely based on the exercises presented in the book.

## Non Credit practice problems
1.  Open a project to access the code pad. 
    *   Create an ArrayList<String> in the Code Pad by typing:
    
            import java.util.ArrayList;
            new ArrayList<String>();
    
    *   If you write the last line without a trailing semicolon, you will see the small red object icon. Drag this icon onto the object bench. Examine its methods and try calling some (such as **add, remove, size, isEmpty**).
    *   Also try calling the same methods from the Code Pad. You can access objects on the object bench from the Code Pad by using their names. For example, if you have an **ArrayList** named **al1** on the object bench, in the Code Pad you can type: **al1.size()**
          

2.  Open the _music-organizer-v3_ project (chapter 4)
    *   Use the debugger to help you understand how the statements in the body of the loop in listAllFiles are repeated. Set a breakpoint just before the loop, and step through the method until the loop has processed all elements and exits.
    *   The for-each loop does not use an explicit integer variable to access successive elements of the list. Thus, if we want to include the index of each file name in the listing, then we would have to declare our own local integer variable (**position**, say) so that we can write in the body of the loop something like:  
                            **System.out.println(position + ": " + filename);**  
        See if you can complete a version of **listAllFiles** to do this. 
3.  Create a new project _while-loop-testing_
    *   Write a class **NumberTestFramework**. Inside this class you will create a number of methods
        *   Write a while loop (for example, in a method called **multiplesOfFive**) that prints out all multiples of 5 between 10 and 95.
        *   Write a while loop to add up the values 1 to 10 and print the sum once the loop has finished.
        *   Write a method called **sum** with a while loop that adds up all numbers between two numbers **a** and **b**. The values for **a** and **b** can be passed to the **sum** method as parameters.
        *   Write a method **isPrime(int n)** that returns true if the parameter **n** is a prime number, and false if it is not. To implement the method, you can write a while loop that divides **n** by all numbers between **2** and **(n–1)** and tests whether the division yields a whole number.
        *   Java provides another type of loop: the **do-while** loop. Find out how this loop works and describe it. Write an example of a **do-while** loop that prints out the numbers from 1 to 10. To find out about this loop, find a description of the Java language (for example, at [http://download.oracle.com/javase/tutorial/java/nutsandbolts/](http://download.oracle.com/javase/tutorial/java/nutsandbolts/) in the section “Control Flow Statements”).  
              
            
4.  Open the _club_ project from chapter 4. Your task is to complete the **Club** class, an outline of which has been provided in the project. The **Club** class is intended to store **Membership** objects in a collection.
    *   Within **Club**, define a field for an **ArrayList**. Use an appropriate **import** statement for this field, and think carefully about the element type of the list. In the constructor, create the collection object and assign it to the field. Make sure that all the files in the project compile before moving on to the next exercise.
    *   Complete the **numberOfMembers** method to return the current size of the collection. Until you have a method to add objects to the collection, this will always return zero, of course, but it will be ready for further testing later.
    *   Membership of a club is represented by an instance of the **Membership** class. A complete version of **Membership** is already provided for you in the club project, and it should not need any modification. An instance contains details of a person’s name and the month and year in which they joined the club. All membership details are filled out when an instance is created. A new **Membership** object is added to a **Club** object’s collection via the **Club** object’s **join** method, which has the following description:  
    
            /**
             * Add a new member to the club’s collection of members.
             *
             *   @param member The member object to be added.
             */ 
             public void join (Membership member)
    * Complete the **join** method
    *   When you wish to add a new **Membership** object to the **Club** object from the object bench, there are two ways you can do this. Either create a new **Membership** object on the object bench, call the join method on the **Club** object, and click on the **Membership** object to supply the parameter or call the join method on the **Club** object and type into the method’s parameter dialog box:  
                                      new **Membership** ("member name ...", month, year)  
      Each time you add one, use the **numberOfMembers** method to check both that the **join** method is adding to the collection and that the **numberOfMembers** method is giving the correct result.
    *   Define a method in the **Club** class with the following description:  

              /**
               *  Determine the number of members who joined in the given month.  
               *
               * @param month: The month we are interested in.  
               * @return The number of members who joined in that month.  
               **/  
               public int joinedInMonth(int month)  
        If the **month** parameter is outside the valid range of 1 to 12, print an error message and return zero.

    *   Define a method in the **Club** class with the following description:  

             /**
              * Remove from the club’s collection all members who joined in the given month and year,  
              * and return them stored in a separate collection object.
              * 
              * @param month The month of the membership.  
              * @param year The year of the membership.  
              * @return The members who joined in the given month and year.  
              */  
              public ArrayList purge(int month, int year)  
    
        If the **month** parameter is outside the valid range of 1 to 12, print an error message and return a collection object with no objects stored in it.  
        Note: The **purge** method is significantly harder to write than any of the others in this class.

## Credit problem

5. Accept the invitation for lab5, and checkout the repository which includes part 4 above with some extensions. The Club interface to implement and some of the tests are provided in th usual grading package.  The remaining tests will be available at the beginning of lab 2. Remember to push your solution before the lab time expires.