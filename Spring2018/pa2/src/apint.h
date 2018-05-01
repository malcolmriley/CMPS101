/*******************************************************************************
 * PA2: Arbitrary Precision Integer, in C.
 *
 * Malcolm Riley
 * CMPS 101 Spring 2018
 ******************************************************************************/

#ifndef APINT_H
#define APINT_H

typedef struct apint_object {
	int SIZE;
	int** VALUE;
	int** CARRY;
} apint_object;
typedef apint_object* apint;

/* Constructors - Destructor */
apint newApint(void);
apint newApint(int);
apint newApint(char*);
void freeApint(apint*);

/* Accessors */
int equals(apint);
int compare(apint);

/* Manipulators */
apint add(apint, apint);
apint subtract(apint, apint);
apint multiply(apint, apint);

/* Miscellaneous */
void print(FILE*, apint);
apint copy(apint);

#endif /* APINT_H */
