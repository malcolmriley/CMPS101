/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "apint.h"

int main(void) {
	apint instance = fromInteger(5);

	print(instance);

	return EXIT_SUCCESS;
}
