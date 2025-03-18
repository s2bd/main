#include <stdio.h>

/* CS1510 LAB PROBLEM 07
*  Author  : Dewan Mukto (dmimukto)
*  Stud ID : 202004321
*/

// This program uses the algorithm provided in the question to multiply 2 numbers

int main(){
  int a, b, i=0, sum_of_a=0;
  scanf("%d",&a);
  scanf("%d",&b);
  printf("Computing product of %d and %d... \n", a, b);
  // The main loop that follows the algorithm
  while (b!=1) {
    i++;
    a*=2;
    b/=2;
    if (b%2!=0){
      sum_of_a += a;
      printf("%d \t %d \n", a, b); // prints only the rows that are not crossed out
    }
  }
  
  printf("Product: %d \n",sum_of_a);
  
  return 0;
}
