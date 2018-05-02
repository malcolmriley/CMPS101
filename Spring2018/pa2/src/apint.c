/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "apint.h"

/* Internal Function Declarations */
int checkSize(apint, int);
int get(apint, int);
void set(apint, int);
void expand(apint, int);
void zero(apint);

int add_ints(int, int);
int subtract_ints(int, int);
int multiply_ints(int, int);
int getCarry(int);
int max(int, int);
int getBlocks(int);
char intToChar(int);
int getSign(char);
int compareMagnitude(apint, apint);
apint addInternal(apint, apint, int);

/* Header-Defined Functions */

apint newApintWithSize(int passedSize) {
	apint instance = malloc(sizeof(apint_object));
	expand(instance, passedSize);
	zero(instance);
	return instance;
}

/**
 * Default Constructor.
 */
apint newApint(void) {
	return newApintWithSize(2);
}

/**
 * Constructor for conversion of an int to an apint.
 */
apint newApint(int passedValue) {
	// If the passed value is zero, just use default constructor
	if (passedValue == 0) {
		return newApint();
	}

	// Configure sign
	int sign = 1;
	if (passedValue < 0) {
		sign = -1;
		passedValue *= -1;
	}

	// Begin Construction
	int size = getBlocks(passedValue);
	apint instance = newApintWithSize(size);
	for (int index = 0; index < size; index += 1) {
		int digit = abs((passedValue % (1 + MAX_PER_BLOCK)));
		set(instance, index, digit);
		passedValue /= (1 + MAX_PER_BLOCK);
	}

	instance->SIGN = sign;
	return instance;
}

/**
 * Constructor for conversion of a char array (string) to an apint.
 */
apint newApint(char* passedArray, int passedLength) {
	apint instance = newApint();
	int size = (passedLength / DIGITS_PER_BLOCK) + 1;
	expand(instance, size);
	instance->SIGN = getSign(passedArray[0]);
	for (int index = 0; index < (size - 1); index += 1) {
		instance->VALUE[index] = atoi(passedArray[index]);
	}
	return instance;
}

/* Frees the passed apint */
void freeApint(apint* passedApint) {
	free(*(passedApint->CARRY));
	free(*(passedApint->VALUE));
	free(*passedApint);
}

/**
 * Compares the two passed apints for equality;
 *
 * Returns 0 if passedFirst = passedSecond, 1 otherwise.
 */
int equals(apint passedFirst, apint passedSecond) {
	return (compare(passedFirst, passedSecond) == 0);
}

/**
 * Compares the two passed apints.
 *
 * Returns 0 if they are equal, 1 if passedFirst > passedSecond, and -1 if passedFirst < passedSecond.
 */
int compare(apint passedFirst, apint passedSecond) {
	// Simple sign comparison
	if (passedFirst->SIGN > passedSecond->SIGN) {
		return 1;
	}
	else if (passedFirst->SIGN < passedSecond->SIGN) {
		return -1;
	}

	// Compare values
	else {
		int sign = passedFirst->SIGN; // At this point, signs are the same
		return sign * compareMagnitude(passedFirst, passedSecond);
	}
	return 0;
}

/**
 * Adds (passedFirst + passedSecond), returning the result as a new apint.
 */
apint add(apint passedFirst, apint passedSecond) {

	// TODO:
}

/**
 * Subtracts (passedFirst - passedSecond), returning the result as a new apint.
 */
apint subtract(apint passedFirst, apint passedSecond) {

	// TODO:
}

/**
 * Multiplies (passedFirst * passedSecond), returning the result as a new apint.
 */
apint multiply(apint passedFirst, apint passedSecond) {
	// If signum of number is zero, return zero
	int signum = (passedFirst->SIGN * passedSecond->SIGN);
	if (signum == 0) {
		return newApint();
	}
	else {
		apint result = newApintWithSize(passedFirst->SIZE * passedSecond->SIZE);
		// TODO:
		return result;
	}
}

/**
 * Prints the passed apint to the passed FILE.
 */
void print(apint passedValue) {
	char sign = (passedValue->SIGN < 0) ? '-' : '+';
	int length = (passedValue->SIZE * DIGITS_PER_BLOCK);
	char output[length + 1];
	output[length] = sign;
	for (int index = (length - 1); index >= 0; index -= 1) {
		output[index] = intToChar(passedValue->VALUE[index]);
	}
	printf("%s", output);
}

/* Internal Methods */

int compareMagnitude(apint passedFirst, apint passedSecond) {
	for (int index = max(passedFirst->SIZE, passedSecond->SIZE); index >= 0; index -= 1) {
		int first = get(passedFirst, index);
		int second = get(passedSecond, index);
		if (first > second) {
			return 1;
		}
		else if (first < second) {
			return -1;
		}
	}
	return 0;
}

apint addInternal(apint passedFirst, apint passedSecond, int passedSignum) {
	apint result = newApintWithSize(max(passedFirst->SIZE, passedSecond->SIZE));
	// TODO:
	return result;
}

int checkSize(apint passedApint, int passedSize) {
	return (passedSize > 0) || (passedSize < passedApint->SIZE);
}

/**
 * Sets the value at passedIndex to passedValue, expanding passedApint if necessary.
 */
void set(apint passedApint, int passedIndex, int passedValue) {
	if(!checkSize(passedApint, passedIndex)) {
		expand(passedApint, passedIndex);
	}
	passedApint->VALUE[passedIndex] = passedValue;
}

/**
 * Safely returns the int value stored at passedIndex in passedApint.
 *
 * Returns 0 if passedIndex is out of range, and the requested value otherwise.
 */
int get(apint passedApint, int passedIndex) {
	if (checkSize(passedApint, passedIndex)) {
		return passedApint->VALUE[passedIndex];
	}
	return 0;
}

/**
 * Expands passedApint to accommodate passedSize.
 */
void expand(apint passedApint, int passedSize) {
	// TODO:
	passedApint->SIZE = passedSize;
}

/**
 * Zeroes the passed apint.
 */
void zero(apint passedApint) {
	passedApint->SIGN = 0;
	for (int index = 0; index < passedApint->SIZE; index += 1) {
		passedApint->VALUE[index] = 0;
		passedApint->CARRY[index] = 0;
	}
}

/**
 * Returns the greater of the two passed integers
 */
int max(int passedFirst, int passedSecond) {
	return (passedFirst > passedSecond) ? passedFirst : passedSecond;
}

/**
 * Returns number of entries required in an array to contain the passed value.
 */
int getBlocks(int passedValue) {
	return ((passedValue < MAX_PER_BLOCK) ? 1 : (log10((double)passedValue) / log10((double)(MAX_PER_BLOCK + 1)))) + 1;
}

/**
 * Converts the passedValue into its character equivalent.
 */
char intToChar(int passedValue) {
	return (passedValue % 10) + '0';
}

int getSign(char passedValue) {
	return (passedValue == '-') ? -1 : 1;
}

int getCarry(int passedValue) {
	return (passedValue / (1 + MAX_PER_BLOCK));
}

int add_ints(int passedFirst, int passedSecond) {
	return (passedFirst + passedSecond);
}

int subtract_ints(int passedFirst, int passedSecond) {
	return (passedFirst - passedSecond);
}

int multiply_ints(int passedFirst, int passedSecond) {
	return (passedFirst * passedSecond);
}
