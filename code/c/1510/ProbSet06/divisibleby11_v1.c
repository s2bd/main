#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* CS1510 PROBLEM SET 06
*  Author  : Dewan Mukto (dmimukto)
*  Stud ID : 202004321
*/

// This program checks whether the value is divisible by 11 

int main(){
  int numint;
  char number, input[20], lastdigit;
  char * numstr;
  printf("Enter a positive integer:");
  scanf("%s", input); // Inp
  if (input<=0){
    printf("Invalid input!"); // Error message if the input is negative
    printf("Enter a positive integer:");
  }
  
  /* if (input<=0) {
    while (input<=0){
      printf("Enter a positive integer:");
      scanf("%s", input); // Input
      if (input > 0) {
        break; // Escapes the loop if the entered value is in the correct range
      }
      else {
        printf("Invalid input!"); // Error message if the input is negative
      }
    }
  } */
  
  printf("Checking if %s is divisible by 11...", input);
  numstr = input;
  do {
    printf("%s", numstr); // displays current value of number as a string
    lastdigit = numstr[strlen(numstr)-1]; // stores last digit from number
    numint = atoi(lastdigit); // converts the last digit to  integer for subtraction
    numstr[strlen(numstr)-1] = "/0"; // removes last digit from number
    number = atoi(numstr); // converts number to integer
    number = number - numint; // subtracts last digit from number
    sprintf(numstr, "%d", number); // converts number back to string
  } while(number>=0);
  
  if (number==0){
    printf("%s is divisible by 11", input); // Output if divisible
  }
  else {
    printf("%s is not divisible by 11", input); // Output if not divisible
  }
  
  return 0;
}
