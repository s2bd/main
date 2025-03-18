#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

/* CS1510 PROBLEM SET 05
*  Author  : Dewan Mukto (dmimukto)
*  Stud ID : 202004321
*/

// The predicate is defined
void isPalindrome(char string[]){
  int i=0, j=strlen(string)-1;
  char *newstring, charac[1]={0};
  newstring=(char *) calloc(30,sizeof(char));
  printf("Received string : %s \n",string); // remove
  // Initial step : Convert all to lowercase
  for(i=0;i<strlen(string)-1;i++){
    string[i] = tolower(string[i]);
  }
  
  // Intermediate step : Remove all non-alphanumeric characters
  for(i=0;i<strlen(string);i++){
    if (isalnum(string[i])){
      charac[0]=string[i];
      strcat(newstring,charac);
    }
    
  }
  printf("New string : %s \n",newstring); // remove
  
  // Final step : Check if it is a palindrome
  do{
    i++;
    j--;
    if(string[i] != string[j]){
      printf("false\n");
      return;
    }
    else{
      printf("true\n");
      return;
    }
  } while(j > i);
  free(newstring);
}

// The main function tests the palindrome predicate
int main(){

  char *s;
  s=(char *) calloc(30,sizeof(char));
  
  printf("Enter a string : \n");
  scanf("%s",s);
  
  isPalindrome(s);
  
  free(s);
  return 0;
}
