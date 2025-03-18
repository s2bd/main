#include <stdio.h>

int main(){
  int array[5]= {1,3,2,4,2};
  int i=0;
  for (i=0; i<5; i++){
    array[i]=i%2;
  }
  i--;
  do{
  	array[i]+=i;
  	i-=2;
  }while (i>=0);
  printf("%d %d %d %d %d",array[0],array[1],array[2],array[3],array[4]);
  return 0;
}
