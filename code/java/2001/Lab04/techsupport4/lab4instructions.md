# Lab 4 instruction COMP 2001 F 2022

The problems are loosely based on the exercises presented in the book chapter 6.

Objectives are to explore the standard library classes, specifically `String` methods, `Random` and `HashMap` collections.


**Note:** Link to Java 11 API Documentation: <https://docs.oracle.com/javase/11/docs/api/>

## Non Credit practice problems


1.  Investigate the String documentation. Then look at the documentation for some other classes. What is the structure of class documentation? Which sections are common to all class descriptions? What is their purpose?
    1.  Look up the startsWith method in the documentation for String. There are two versions. Describe in your own words what they do and the differences between them.
    2.  Is there a method in the String class that tests whether a string ends with a given suffix? If so, what is it called, and what are its parameters and return type?
    3.  Is there a method in the String class that returns the number of characters in the string? If so, what is it called, and what are its parameters?
    4.  How would you call the split method if you wanted to split a string at either space or tab characters? How might you break up a string in which the words are separated by colon characters (:)?
2.  Random
    1.  Find the class Random in the Java class library documentation. Which package is it in? What does it do? How do you construct an instance? How do you generate a random number? Note that you will probably not understand everything that is stated in the documentation. Just try to find out what you need to know.
    2.  Write some code (in BlueJ) to test the generation of random numbers. To do this, create a new class called RandomTester. In class RandomTester, implement two methods: printOneRandom (which prints out one random number) and printMultiRandom(int howMany) (which has a parameter to specify how many numbers you want, and then prints out the appropriate number of random numbers).
    3.  Write a method called getResponse that randomly returns one of the strings "yes", "no", or "maybe".
    4.  Extend your getResponse method so that it uses an ArrayList to store an arbitrary number of responses and randomly returns one of them.
    5.  Add a method to your RandomTester class that takes two parameters, min and max, and generates a random number in the range min to max (inclusive).
3.  Maps
    1.  How do you check how many entries are contained in a map?
    2.  Create a class MapTester (either in your current project or in a new project). In it, use a HashMap to implement a contacts list similar to the one from the class. (Remember that you must import java.util.HashMap.) In this class, implement two methods: **public void enterNumber(String name, String number)** and **public String lookupNumber(String name)** The methods should use the put and get methods of the HashMap class to implement their functionality.
    3.  What happens when you add an entry to a map with a key that already exists in the map?
    4.  What happens when you add two entries to a map with the same value and two different keys?
    5.  How do you check whether a given key is contained in a map? (Give a Java code example.)
    6.  What happens when you try to look up a value and the key does not exist in the map?
    7.  How do you print out all keys currently stored in a map?
    8.  What does the putIfAbsent method of HashMap do?
4.  What are the similarities and differences between a HashSet and an ArrayList? Use the descriptions of Set, HashSet, List, and ArrayList in the library documentation to find out, because HashSet is a special case of a Set, and ArrayList is a special case of a List.
5.  Use BlueJ’s Project Documentation function to generate documentation for the TechSupport project (chapter 6). Examine it. Is it accurate? Is it complete? Which parts are useful? Which are not? Do you find any errors in the documentation?
6.  Open the _bouncing-balls_ project and find out what it does.
    1.  Create a BallDemo object and execute its bounce method.
    2.  Change the method bounce in class BallDemo to let the user choose how many balls should be bouncing.
    3.  Which type of collection (ArrayList, HashMap, or HashSet) is most suitable for storing the balls for the new bounce method? Discuss in writing, and justify your choice.
    4.  Change the bounce method to place the balls randomly anywhere in the top half of the screen.
    5.  Give the balls random colors.

## Credit problem

7. Accept the invitation for lab4, and checkout the repository which is a variant of the `tech-support-complete` project supplied with the book. The Classes have been modified to make testing easier, so the responseMap response strings are modified so they can be easily split into sentences. Also, the `InputReader` object can now be applied without requiring use of the `SupportSystem.start()` input-response-print loop. However, you can still use the `SupportSystem.start()` input-response-print loop to examine your modifications to the Response object.
   1. There is no `implements interface` provided this time, because all of your changes are to affect one method's behaviour: the existing `Responder.generateResponse()` method.
   2. The test class is in the usual place in the `grading` package.
   3. Your task is to implement four new rules for the behaviour of the `Responder.generateResponse()` method:
      1. **RULE 1.** You cannot repeat a response from `responseMap` until at least 5 input-response events have intervened between repetitions.
      2. **RULE 2.** If an input has more than one words from the `responseMap`, use the word that has appeared in the most input-response events during the entire session, that would not violate rule #1. (A "session" is equivalent to the lifespan of a `Responder` object.)
      3. **RULE 3.** If rules 1 and 2 produces a tie, break the tie at random using `Random`.
      4. **RULE 4.**  If rules 1-3 results in a response from responseMap being repeated during a session, re-order the sentences randomly before giving the same response.
   4. Some implementation suggestions:
      1. Use a Map object to count how often a user uses each word (rule 2)
      2. Use a Map object to track how many responses have intervened since we gave a particular response. (rule 1)
      3. Use `String` methods and `Random` to re-order sentences (Rule 4)
   5. Make sure to commit and push your solution before the lab time expires.