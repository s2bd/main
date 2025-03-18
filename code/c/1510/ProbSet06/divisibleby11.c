#include <stdio.h>

/*  CS1510 PROBLEM SET 05
*   Author  : Dewan Mukto (dmimukto)
*   Stud ID : 202004321
*/

// This program checks if a number is divisible by 11 or not

// The main function of the program
int main(){
  // Declaring variables and types
  int input_number, number, newnumber, lastdigit;
  
  // Main Input
  printf("Enter a positive integer: \n");
  scanf("%d",&input_number);
  // In case the user enters a negative integer, the program prompts for a new value
  while (input_number<=0){
    printf("Invalid input! Please try again. \n");
    printf("Enter a positive integer: \n");
    scanf("%d", &input_number);
    }
  // Creating a copy of the input value
  number = input_number;
  // Main processing part of the program
  printf("Checking if %d is divisible by 11... \n", input_number);
  do {
    printf("%d \n",number);
    newnumber = number/10;
    lastdigit = number%10;
    number = newnumber - lastdigit;
  } while(number>0);
  
  if (number==0) {
    printf("%d \n",number);
    printf("%d is divisible by 11 \n",input_number); // Output if divisible by 11
  }
  else {
    printf("%d is not divisible by 11 \n",input_number); // Output if not divisible by 11
  }
  
  return 0;
}
