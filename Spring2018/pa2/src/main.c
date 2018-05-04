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
	return fromString("+23154531011545132123958751547812157489398752123");
}

apint add_zero() {
	apint first = newApint();
	apint second = newApint();
	return add(first, second);
}

apint add_small() {
	apint first = fromString("12");
	apint second = fromString("33");
	return add(first, second);
}

void test(char* passedMessage, char* passedExpectedValue, apint (*passedFunction)()) {
	puts(passedMessage);
	apint instance = passedFunction();
	printf("Expecting: %s, Returned: ", passedExpectedValue);
	print(instance);
	puts("\n");
}

int main(void) {

	// Constructor Tests
	puts("********** CONSTRUCTOR TESTS **********");
	test("Default constructor:", "+0", constructor_default);
	test("Integer constructor with zero as parameter:", "+0", constructor_zero);
	test("Integer constructor with nonzero parameter:", "+53423", constructor_integer);
	test("Integer constructor with negative parameter:", "-14984", constructor_integer_negative);
	test("String constructor with \"positive\" zero parameter:", "+0", constructor_string_zero_positive);
	test("String constructor with \"negative\" zero parameter:", "+0", constructor_string_zero_negative);
	test("String constructor with small negative integer:", "-123", constructor_string_small);
	test("String constructor with large positive integer:", "+23154531011545132123958751547812157489398752123", constructor_string_big);

	// Addition Tests
	puts("********** ADDITION TESTS **********");
	test("Addition of two zero values:", "+0", add_zero);
	test("Addition of two small values:", "+45", add_small);

	return EXIT_SUCCESS;
}
