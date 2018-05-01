/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "apint.h"

/* Internal Function Declarations */
int checkSize(apint, int);
int get(apint, int);
void set(apint, int);
void expand(apint, int);
void zero(apint);

int max(int, int);

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
	apint instance = newApint();
	set(instance, 0, passedValue);
	return instance;
}

/**
 * Constructor for conversion of a char array (string) to an apint.
 */
apint newApint(char* passedArray, int passedLength) {
	apint instance = newApint();
	int size = (passedLength / CARRY_DIGITS) + 1;
	expand(instance, size);
	// TODO:
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
	return (compare(passedFirst, passedSecond)) == 0;
}

/**
 * Compares the two passed apints.
 *
 * Returns 0 if they are equal, 1 if passedFirst > passedSecond, and -1 if passedFirst < passedSecond.
 */
int compare(apint passedFirst, apint passedSecond) {
	if (passedFirst->SIGN > passedSecond->SIGN) {
		return 1;
	}
	else if (passedFirst->SIGN < passedSecond->SIGN) {
		return -1;
	}
	else {
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
	// TODO:
}

/**
 * Prints the passed apint to the passed FILE.
 */
void print(FILE* passedFile, apint passedValue) {
	// TODO:
}

/* Internal Methods */

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
 * Expands passedApint to accomodate passedSize.
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
