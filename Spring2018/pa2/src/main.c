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
	apint first = fromString("142");
	apint second = fromString("363");
	return add(first, second);
}

apint add_big() {
	apint first = fromString("8390480938194831");
	apint second = fromString("7189478937189578931");
	return add(first, second);
}

apint add_negative() {
	apint first = fromString("512");
	apint second = fromString("-231");
	return add(first, second);
}

apint add_two_negative() {
	apint first = fromString("-123");
	apint second = fromString("-293");
	return add(first, second);
}

apint add_positive_to_negative() {
	apint first = fromString("-472182");
	apint second = fromString("231");
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
	test("Addition of two small positive values:", "+505", add_small);
	test("Addition of two large positive values:", "+7197869418127773762", add_big);
	test("Addition of negative to positive:", "+281", add_negative);
	test("Addition of negative to negative", "-416", add_two_negative);
	test("Addition of positive to negative", "-471951", add_positive_to_negative);

	return EXIT_SUCCESS;
}
