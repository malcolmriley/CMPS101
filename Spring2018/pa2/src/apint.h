/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#ifndef APINT_H
#define APINT_H

#define CARRY_DIGITS 1
#define MAX_PER_BLOCK 9

typedef struct apint_object {
	int SIZE;
	int SIGN;
	unsigned int* VALUE;
	unsigned int* CARRY;
} apint_object;
typedef apint_object* apint;

/* Constructors - Destructor */
apint newApint(void);
apint newApint(int);
apint newApint(char*, int);
void freeApint(apint*);

/* Accessors */
int equals(apint, apint);
int compare(apint, apint);

/* Manipulators */
apint add(apint, apint);
apint subtract(apint, apint);
apint multiply(apint, apint);

/* Miscellaneous */
void print(apint);
apint copy(apint);

#endif /* APINT_H */
