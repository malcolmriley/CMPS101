/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <ctype.h>
#include <string.h>
#include "apint.h"

/* Internal Function Declarations */
int checkIndex(apint, int);
int get(apint, int);
void set(apint, int, int);
void zero(apint);
void zeroArray(int*, int);
int getCarry(int);
int max(int, int);
int getBlocks(int);
char intToChar(int);
int charToInt(char);
int getSign(char);
int compareMagnitude(apint, apint);
apint addInternal(apint, apint);
apint subtractInternal(apint, apint, int*);

/* Header-Defined Functions */

apint newApintWithSize(int passedSize) {
	apint instance = malloc(sizeof(apint_object) + (sizeof(int) * passedSize));
	instance->SIZE = passedSize;
	instance->VALUE = (int*)(instance + 1);
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
apint fromInteger(int passedValue) {
	// If the passed value is zero, just use default constructor
	int value = abs(passedValue);
	if (passedValue == 0) {
		return newApint();
	}

	// Configure sign
	int sign = 1;
	if (passedValue < 0) {
		sign = -1;
	}

	// Begin Construction
	int size = getBlocks(value);
	apint instance = newApintWithSize(size + 1);
	for (int index = 0; index < size; index += 1) {
		int digit = value % (1 + MAX_PER_BLOCK);
		set(instance, index, digit);
		value /= (1 + MAX_PER_BLOCK);
	}

	instance->SIGN = sign;
	return instance;
}

/**
 * Constructor for conversion of a char array (string) to an apint.
 */
apint fromString(char* passedArray) {
	int digitLength = strlen(passedArray);
	int beginIndex = 0;
	int sign = 1;

	for(int index = 0; index < strlen(passedArray); index += 1) {
		if (!isdigit(passedArray[index])) {
			beginIndex += 1;
			digitLength -= 1;
			sign = getSign(passedArray[0]);
		}
	}

	int size = (digitLength / DIGITS_PER_BLOCK) + 1;
	apint instance = newApintWithSize(size);

	int nonzero = 0;
	for (int index = 0; index < size; index += 1) {
		int value = charToInt(passedArray[(strlen(passedArray) - 1) - index]);
		nonzero |= (value != 0);
		set(instance, index, value);
	}

	if (nonzero) {
		instance->SIGN = sign;
	}
	else {
		sign = 0;
	}

	return instance;
}

/* Frees the passed apint */
void freeApint(apint passedApint) {
	free(passedApint);
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
	apint instance;
	int sign = 0;

	// Case: a + b OR -a + -b
	if (passedFirst->SIGN == passedSecond->SIGN) {
		sign = passedFirst->SIGN;
		instance = addInternal(passedFirst, passedSecond);
	}

	// Case: -a + b -> Result: b - a
	else if (passedFirst->SIGN < passedSecond->SIGN) {
		instance = subtractInternal(passedSecond, passedFirst, &sign);
	}

	// Case: a + -b -> Result: a - b
	else if (passedFirst->SIGN > passedSecond->SIGN) {
		instance = subtractInternal(passedSecond, passedFirst, &sign);
	}

	else {
		instance = newApint();
	}

	instance->SIGN = sign;
	return instance;
}

/**
 * Subtracts (passedFirst - passedSecond), returning the result as a new apint.
 */
apint subtract(apint passedFirst, apint passedSecond) {
	apint instance;
	int sign = 0;

	if (passedFirst->SIGN == passedSecond->SIGN) {
		// Case: a - b
		if (passedFirst->SIGN >= 0) {
			instance = subtractInternal(passedFirst, passedSecond, &sign);
		}
		// Case: -a - - b -> result: -a + b = b - a
		if (passedFirst->SIGN <= 0) {
			instance = subtractInternal(passedSecond, passedFirst, &sign);
		}
	}
	// Case: -a - b -> result: -a + -b
	else if (passedFirst->SIGN < passedSecond->SIGN) {
		sign = -1;
		instance = addInternal(passedFirst, passedSecond);
	}

	// Case: a - - b -> result: a + b
	else if (passedFirst->SIGN > passedSecond->SIGN) {
		sign = 1;
		instance = addInternal(passedFirst, passedSecond);
	}

	else {
		instance = newApint();
	}

	instance->SIGN = sign;
	return instance;
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

		int size = passedFirst->SIZE + passedSecond->SIZE;
		apint result = newApintWithSize(size + 1);

		// Initialize carry array
		int carry[size + 1];
		for (int index = 0; index < (size + 1); index += 1) {
			carry[index] = 0;
		}

		// Perform Multiplication
		for (int indexOuter = 0; indexOuter < size; indexOuter += 1) {
			for (int indexInner = 0; indexInner < size; indexInner += 1) {
				int resultValue = get(passedFirst, indexOuter) * get(passedSecond, indexInner);
				carry[indexOuter] += resultValue;
			}
		}

		// Rectify Carry
		for (int index = 0; index < size; index += 1) {
			int value = carry[index];
			set(result, index, value - (getCarry(value) * (1 + MAX_PER_BLOCK)));
			int offset = 0;
			while (value > 0) {
				carry[index + offset] += value;
				value /= (1 + MAX_PER_BLOCK);
				offset += 1;
			}
		}

		result->SIGN = signum;
		return result;
	}
}

/**
 * Prints the passed apint to the passed FILE.
 */
void print(apint passedValue) {
	char sign = (passedValue->SIGN < 0) ? '-' : '+';
	printf("%c", sign);
	int leadingZeroes = 0;
	for (int index = (passedValue->SIZE - 1); index >= 0; index -= 1) {
		if (get(passedValue, index) != 0) {
			break;
		}
		leadingZeroes += 1;
	}
	if (leadingZeroes >= passedValue->SIZE) {
		printf("%d", 0);
	}
	else {
		for (int index = ((passedValue->SIZE - 1) - leadingZeroes); index >= 0; index -= 1) {
			printf("%d", get(passedValue, index));
		}
	}
}

/* Internal Methods */
/**
 * Adds the first to the second without regard to sign.
 */
apint addInternal(apint passedFirst, apint passedSecond) {
	int size = max(passedFirst->SIZE, passedSecond->SIZE);
	apint result = newApintWithSize(size + 1);
	print(passedFirst);
	puts("");
	print(passedSecond);

	// Initialize carry array
	int carry[size + 1];
	zeroArray(carry, size + 1);

	for (int index = 0; index < (size + 1); index += 1) {
		int resultValue = get(passedFirst, index) + get(passedSecond, index) + carry[index];
		int carryValue = getCarry(resultValue);
		int storedValue = resultValue - (carryValue * (1 + MAX_PER_BLOCK));
		printf("\nINDEX: %d FIRST: %d \t SECOND: %d \t CARRY: %d\n", index, get(passedFirst, index), get(passedSecond, index), carry[index]);
		printf("\nRESULT VALUE: %d", resultValue);
		printf("\nCARRY VALUE: %d", carryValue);
		printf("\nSTORED VALUE: %d\n", storedValue);
		carry[index + 1] = carryValue;
		set(result, index, storedValue);
	}
	return result;
}

/**
 * Subtracts the second from the first without regard to sign.
 */
apint subtractInternal(apint passedFirst, apint passedSecond, int* passedSign) {
	// If the second is greater in magnitude than the first, swap signs and places
	// A < B -> A - B = -(B - A)
	if (compareMagnitude(passedFirst, passedSecond) < 0) {
		(*passedSign) *= -1;
		return subtractInternal(passedSecond, passedFirst, passedSign);
	}

	int size = max(passedFirst->SIZE, passedSecond->SIZE);
	apint result = newApintWithSize(size);

	// Initialize carry array
	int carry[size + 1];
	zeroArray(carry, size + 1);

	for (int index = 0; index < size; index += 1) {
		// Use carry as borrow
		int resultValue = (get(passedFirst, index) - get(passedSecond, index)) - carry[index];
		if (resultValue < 0) {
			carry[index + 1] += 1;
			resultValue += (1 + MAX_PER_BLOCK);
		}
		// No validation needed here as the result should never have more than one digit
		set(result, index, resultValue);
	}

	return result;
}

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

int checkIndex(apint passedApint, int passedIndex) {
	return (passedIndex >= 0) && (passedIndex < passedApint->SIZE);
}

/**
 * Sets the value at passedIndex to passedValue, expanding passedApint if necessary.
 */
void set(apint passedApint, int passedIndex, int passedValue) {
	if ((passedIndex < passedApint->SIZE) && (passedIndex >= 0)) {
		passedApint->VALUE[passedIndex] = passedValue;
	}
}

/**
 * Safely returns the int value stored at passedIndex in passedApint.
 *
 * Returns 0 if passedIndex is out of range, and the requested value otherwise.
 */
int get(apint passedApint, int passedIndex) {
	if (checkIndex(passedApint, passedIndex)) {
		return passedApint->VALUE[passedIndex];
	}
	return 0;
}

/**
 * Zeroes the passed apint.
 */
void zero(apint passedApint) {
	passedApint->SIGN = 0;
	for (int index = 0; index < passedApint->SIZE; index += 1) {
		set(passedApint, index, 0);
	}
}

void zeroArray(int* passedArray, int passedSize) {
	for (int index = 0; index < passedSize; index += 1) {
		passedArray[index] = 0;
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

int charToInt(char passedValue) {
	return (isdigit(passedValue)) ? (int)(passedValue - '0') : 0;
}

int getSign(char passedValue) {
	return (passedValue == '-') ? -1 : 1;
}

int getCarry(int passedValue) {
	return (passedValue / (1 + MAX_PER_BLOCK));
}
