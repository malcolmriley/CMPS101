/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "apint.h"

void constructor_default() {
	puts("Testing default constructor:");
	apint instance = newApint();
	print(instance);
	freeApint(instance);
	puts("");
}

void constructor_zero() {
	puts("Testing integer constructor with zero as parameter:");
	apint instance = fromInteger(0);
	print(instance);
	freeApint(instance);
	puts("");
}

void constructor_integer() {
	puts("Testing integer constructor with nonzero parameter:");
	apint instance = fromInteger(53423);
	print(instance);
	freeApint(instance);
	puts("");
}

void constructor_integer_negative() {
	puts("Testing integer constructor with negative parameter:");
	apint instance = fromInteger(-14984);
	print(instance);
	freeApint(instance);
	puts("");
}

void constructor_string_zero() {

}

int main(void) {

	// Constructor Tests
	constructor_default();
	constructor_zero();
	constructor_integer();
	constructor_integer_negative();

	return EXIT_SUCCESS;
}
