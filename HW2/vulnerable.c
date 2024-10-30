#include <stdio.h>
void f(int a, char b) {
char s[12];
printf(
"_________12345678901234567890\n" );
printf( "Enter s: " );
gets( s );
printf( "You entered: %s\n", s );
return;
}
void main() {
f(6,'D');
}
