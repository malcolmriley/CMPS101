/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "apint.h"

apint test_function(char* passedFirst, char* passedSecond, char* passedOperation, apint(*passedFunction)(apint, apint)) {
	printf("%s %s %s = ?\n", passedFirst, passedOperation, passedSecond);
	apint first = fromString(passedFirst);
	apint second = fromString(passedSecond);
	return passedFunction(first, second);
}

apint test_add(char* passedFirst, char* passedSecond) {
	return test_function(passedFirst, passedSecond, "+", add);
}

apint test_subtract(char* passedFirst, char* passedSecond) {
	return test_function(passedFirst, passedSecond, "-", subtract);
}

apint test_multiply(char* passedFirst, char* passedSecond) {
	return test_function(passedFirst, passedSecond, "x", multiply);
}

void test(char* passedMessage, char* passedExpectedValue, apint (*passedFunction)()) {
	puts(passedMessage);
	apint instance = passedFunction();
	printf("Expecting: %s, Returned: ", passedExpectedValue);
	print(instance);
	puts("\n");
}

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
	return test_add("0", "0");
}

apint add_small() {
	return test_add("142", "363");
}

apint add_big() {
	return test_add("8390480938194831", "7189478937189578931");
}

apint add_negative() {
	return test_add("512", "-231");
}

apint add_two_negative() {
	return test_add("-123", "-293");
}

apint add_positive_to_negative() {
	return test_add("-472182", "231");
}

apint subtract_small() {
	return test_subtract("5", "3");
}

apint subtract_small_from_large() {
	return test_subtract("45646544867864123187541534875645", "1545132123154");
}

apint subtract_large_from_small() {
	return test_subtract("47398748937189473819", "5893081590381904839018490");
}

apint subtract_positive_from_negative() {
	return test_subtract("-578427598427", "8549859048295");
}

apint subtract_negative_from_positive() {
	return test_subtract("584935", "-78690584790864");
}

apint subtract_negative_from_negative() {
	return test_subtract("-4875645648783", "-878975131784");
}

apint multiply_small() {
	return test_multiply("521", "223");
}

apint multiply_large() {
	return test_multiply("57837489543831", "48789785415212");
}

apint multiply_negative() {
	return test_multiply("-57489375892", "8781215645");
}

apint multiply_two_negative() {
	return test_multiply("-45454512315", "-78971210254");
}



int main(void) {

	puts("********** CONSTRUCTOR TESTS **********");
	test("Default constructor:", "+0", constructor_default);
	test("Integer constructor with zero as parameter:", "+0", constructor_zero);
	test("String constructor with \"positive\" zero parameter:", "+0", constructor_string_zero_positive);
	test("String constructor with \"negative\" zero parameter:", "+0", constructor_string_zero_negative);
	test("Integer constructor with nonzero parameter:", "+53423", constructor_integer);
	test("Integer constructor with negative parameter:", "-14984", constructor_integer_negative);
	test("String constructor with small negative integer:", "-123", constructor_string_small);
	test("String constructor with large positive integer:", "+23154531011545132123958751547812157489398752123", constructor_string_big);

	puts("********** ADDITION TESTS **********");
	test("A + B where A = B = 0", "+0", add_zero);
	test("A + B where 0 < A < B", "+505", add_small);
	test("A + B where 0 << A << B", "+7197869418127773762", add_big);
	test("A + B where A > 0 > B", "+281", add_negative);
	test("A + B where 0 > A > B", "-416", add_two_negative);
	test("A + B where A < 0 < B", "-471951", add_positive_to_negative);

	puts("********** SUBTRACTION TESTS **********");
	test("A - B where A > B > 0", "+2", subtract_small);
	test("A - B where A >> B > 0", "+45646544867864123185996402752491", subtract_small_from_large);
	test("A - B where 0 < A < B", "-5893034191632967649544671", subtract_large_from_small);
	test("A - B where A < 0 < B", "-9128286646722", subtract_positive_from_negative);
	test("A - B where A > 0 > B", "+78690585375799", subtract_negative_from_positive);
	test("A - B where 0 > A > B", "-3996670516999", subtract_negative_from_negative);

	puts("********** MULTIPLICATION TESTS **********");
	test("A * B where A > B > 0", "+116183", multiply_small);
	// TODO: Fix multiplication algorithm
	test("A * B where A > B >> 0", "+2821878703798082274808157172", multiply_large);
	test("A * B where A << 0 << B", "-504826607004116230340", multiply_negative);
	test("A * B where 0 >> A >> B", "3589597849020897278010", multiply_two_negative);

	return EXIT_SUCCESS;
}
