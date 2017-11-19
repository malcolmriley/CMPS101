/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4
 *
 * 11-2017
 *********************************************************************/

#include <stdio.h>
#include <stdlib.h>

/* Test Object Implementation */
typedef struct TestObject {
	int (*test)(void);
	int testNumber;
	char* testName;
} TestObject;
typedef struct TestObject* Test;

Test newTest(char* passedName, int(*passedFunctionPointer)(void), int* passedTestNumber) {
	Test newTest = malloc(sizeof(TestObject));
	newTest->test = passedFunctionPointer;
	newTest->testName = passedName;
	newTest->testNumber = *passedTestNumber;
	(*passedTestNumber) += 1;
	return newTest;
}

/* Method Tests */

/* Tester Test*/
int testFail(void) {
	return 0;
}

/* Main */

void executeTest(Test passedTest) {
	int result = passedTest->test();
	if (result) {
		printf("Test %d: %s\t\tPASSED", passedTest->testNumber, passedTest->testName);
	}
	else {
		printf("Test %d: %s\t\t\tFAILED!", passedTest->testNumber, passedTest->testName);
	}
}

int main(void) {

	int numTests = 0;
	Test tests[] = {
		newTest("This Test Fails Deliberately", &testFail, &numTests),
	};

	for (int ii = 0; ii < numTests; ii += 1) {
		executeTest(tests[ii]);
	}

	return EXIT_SUCCESS;
}
