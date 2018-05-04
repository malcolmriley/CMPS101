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
	apint instance = newApint();
	print(instance);
	freeApint(instance);
}

void constructor_zero() {
	apint instance = fromInteger(0);
	print(instance);
	freeApint(instance);
}

void constructor_integer() {
	apint instance = fromInteger(53423);
	print(instance);
	freeApint(instance);
}

void constructor_integer_negative() {
	apint instance = fromInteger(-14984);
	print(instance);
	freeApint(instance);
}

void constructor_string_zero() {

}

void test(char* passedMessage, void (*passedFunction)()) {
	puts(passedMessage);
	passedFunction();
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
