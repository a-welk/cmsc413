#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

void win() {
	printf( "You are the winner!\n");
}

void lose() {
	printf( "Try again!\n");
}

int lottery() {
	char name[32];
	int token;

	printf( "Please input your name:");
	gets(name);
	
	// Call the random function
	srand(time(NULL));
	token=1000000*rand();
	
	printf( "Hello %s, your token is %d\n", name, token);
	
	return token;
}
int main() {
	int result=lottery();
	
	if(result==1)
		win();
	else
		lose();

	return( 0 );
}

