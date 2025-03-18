#include <stdio.h>
#include <stdlib.h>

/*  CS1510 PROBLEM SET 05
*   Author  : Dewan Mukto (dmimukto)
*   Stud ID : 202004321
*/


// This program outputs all the prime numbers from 2 to 102

// The main function of the program
int main(){
  // Declaring variables and types
  int i, j,array[103];
  
  // Defining an array of all 1s to indicate assumption that all are primes
  for (i=0;i<=103;i++){
    array[i] = 1;
  }
  
  // Main loop that continues till it reaches a non-crossed number whose square is greater than N(=102)
  for (j=0;j*j <= 102;j++){
    for (i=0;i<= 103;i++){
      if (i%j==0) array[i]=0; // If any multiple is found, its index value is set to 0 to indicate "PRIME=FALSE"
    }
  }
  
  // Prints out all the numbers (represented by their indices) that remain uncrossed
  for (i=0;i<=103;i++){
    if (array[i]!=0) printf("%d \n",i);
  }
  
  return 0;
}
