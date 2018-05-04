/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "apint.h"

apint constructor_default() {
	return newApint();
}

apint constructor_zero() {
	return fromInteger(0);
}

apint constructor_integer() {
	return fromInteger(53423);
}

apint constructor_integer_negative() {
	return fromInteger(-14984);
}

apint constructor_string_zero() {
	return fromString("0");
}

void test(char* passedMessage, apint (*passedFunction)()) {
	puts(passedMessage);
	apint instance = passedFunction();
	print(instance);
	freeApint(instance);
	puts("");
}

int main(void) {

	// Constructor Tests
	test("Testing default constructor:", constructor_default);
	test("Testing integer constructor with zero as parameter:", constructor_zero);
	test("Testing integer constructor with nonzero parameter:", constructor_integer);
	test("Testing integer constructor with negative parameter:", constructor_integer_negative);
	test("Testing string constructor with zero parameter:", constructor_string_zero);

	return EXIT_SUCCESS;
}
