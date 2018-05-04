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

apint constructor_string_zero_positive() {
	return fromString("+0");
}

apint constructor_string_zero_negative() {
	return fromString("-0");
}

apint constructor_string_small() {
	return fromString("-123");
}

apint constructor_string_big() {
	return fromString("23154531011545132123958751547812157489398752123");
}

void test(char* passedMessage, char* passedExpectedValue, apint (*passedFunction)()) {
	puts(passedMessage);
	apint instance = passedFunction();
	printf("Expecting: %s, Returned: ", passedExpectedValue);
	print(instance);
	puts("\n");
	freeApint(instance);
}

int main(void) {

	// Constructor Tests
	test("Testing default constructor:", "+0", constructor_default);
	test("Testing integer constructor with zero as parameter:", "+0", constructor_zero);
	test("Testing integer constructor with nonzero parameter:", "+53423", constructor_integer);
	test("Testing integer constructor with negative parameter:", "-14984", constructor_integer_negative);
	test("Testing string constructor with \"positive\" zero parameter:", "+0", constructor_string_zero_positive);
	test("Testing string constructor with \"negative\" zero parameter:", "+0", constructor_string_zero_negative);
	test("Testing string constructor with small negative integer:", "-123", constructor_string_small);
	test("Testing string constructor with large positive integer:", "23154531011545132123958751547812157489398752123", constructor_string_big);

	return EXIT_SUCCESS;
}
