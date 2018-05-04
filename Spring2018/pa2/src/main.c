/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "apint.h"

void constructor_integer() {
	apint instance = fromInteger(1235343);
	print(instance);
	freeApint(&instance);
}

void constructor_integer_negative() {
	apint instance = fromInteger(-56454);
	print(instance);
	freeApint(&instance);
}

int main(void) {

	// Constructor Tests
	constructor_integer();
	constructor_integer_negative();

	return EXIT_SUCCESS;
}
