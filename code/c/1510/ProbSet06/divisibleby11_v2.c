#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// including the headerfile dependencies

/* 
*   CS1510 PROBLEM SET 05
*   Author  : Dewan Mukto (dmimukto)
*   Stud ID : 202004321
*/


// The main function of the program
int main(){
  // Declaring variables and types
  int input_number, number, lastdigit_int, strsize=0;
  char lastdigit_str;
  char number_str;
  
  // Main Input
  printf("Enter a positive integer:");
  scanf("%d",&input_number);
  // In case the user enters a negative integer, the program prompts for a new value
  while (input_number<=0){
    printf("Invalid input! Please try again.");
    scanf("%d", &input_number);
    }
  // Creating a copy of the input value
  number = input_number;
  // Main processing part of the program
  printf("Checking if %d is divisible by 11", input_number);
  do {
    printf("%d",number);
    number_str=(char)number;
    strsize = strlen(number_str);
    lastdigit_str = number_str[strsize-1];
    lastdigit_int = (int)lastdigit_str;
    number-=lastdigit_int;
  } while(number>=0);
  if (number==0) {
    printf("%d is divisible by 11",input_number); // Output if divisible by 11
  }
  else {
    printf("%d is not divisible by 11",input_number); // Output if not divisible by 11
  }
  return 0;
}
