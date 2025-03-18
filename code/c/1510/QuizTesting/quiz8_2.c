#include <stdio.h>

int main(){
  int array[5]= {1,3,2,4,2};
  int i=0;
  void seq(int a, int r, int n){
  	int i;
  	for(i=0;i<n;i++){
  		printf("%d ", a);
  		a*=r;
  	}
  		printf("\n");
  		return;
  	}
  seq(3,2,6);
  return 0;
}
