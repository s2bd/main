#include <stdio.h>
#include <string.h>

/* CS1510 PROBLEM SET 05
*  Author  : Dewan Mukto (dmimukto)
*  Stud ID : 202004321
*/

// The predicate is defined, using a simple way
int isPalindrome(char * givenString){
  int len = strlen(givenString);
  int i,j=(len-1);
  char reverseString[20]; // defines an empty string array of size equal to 'givenString'
  for (i=0; i <= (len-1); i++) {
    do { reverseString[j] = givenString[i];
     	 j--;
     } while (j >= 0);
  }
  printf("givenString is %s \n", givenString);
  printf("reverseString is %s \n", givenString);
  if (reverseString==givenString) {
    return 1; 
    }
  else if(reverseString!=givenString) {
    return 0;
    }
  else {
    return 9;
    }
  
}

// The main function tests the palindrome predicate
int main(){
  char string[20];
  int checkPalindrome;
  scanf("%s", string);
  checkPalindrome = isPalindrome(string);
  if (checkPalindrome==1) {
    printf("True");
    }
  else if (checkPalindrome==0) {
    printf("False");
    }
  else {
    printf("Unknown");
    }
    
  return 0;
}
