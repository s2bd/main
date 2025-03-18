#include <stdio.h>

int f() { static int k = 5; return ++k; }
void main() {
 printf("%d",f());
 printf("%d",f());

}

