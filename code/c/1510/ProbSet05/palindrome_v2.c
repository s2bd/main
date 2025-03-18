#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
  int maxsize, i, j;
  char *s[20];
  char *t;
  // remove below
  printf("Enter s=");
  // remove above
  scanf("%s",&s);
  //maxsize = strlen(s)-1;
  maxsize = 20;
  j = maxsize;
  do {
    t[j] = (char)s[i];
    j--;
  } while(i=0, i<maxsize, i++);
  
  printf("s = %s", s);
  printf("t = %s", t);
  
  return 0;
}
