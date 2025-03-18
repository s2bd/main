#include <stdio.h>

int main(int argc, char *argv[]){
	double x=3.5, y=1.6, z;
	double *pt1, *pt2;
	pt1 = &y;
	z=*pt1;
	pt2 = pt1;
	*pt2 = 2.2;
	printf("%f %f %f %f %p\n",x, y, z, *pt1, pt2);
	
	return 0;
}
