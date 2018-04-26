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
#include "Graph.h"

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

void executeTest(Test passedTest) {
	int result = passedTest->test();
	if (result) {
		printf("Test %d: %s\t\tPASSED\n", passedTest->testNumber, passedTest->testName);
	}
	else {
		printf("Test %d: %s\t\t\tFAILED!\n", passedTest->testNumber, passedTest->testName);
	}
}

/* Method Tests */
int testGetOrder(void) {
	Graph graph = newGraph(2);
	return (getOrder(graph) == 2);
}

int testGetSize(void) {
	Graph graph = newGraph(4);
	addEdge(graph, 1, 2);
	addArc(graph, 3, 4);
	return (getSize(graph) == 2);
}

int testSourceNoBFS(void) {
	Graph graph = newGraph(2);
	return(getSource(graph) == NIL);
}

int testSource(void) {
	Graph graph = newGraph(4);
	addEdge(graph, 1, 2);
	addEdge(graph, 2, 3);
	addEdge(graph, 3, 4);
	BFS(graph, 1);
	return(getSource(graph) == 1);
}

int testParentNoBFS(void) {
	Graph graph = newGraph(2);
	return (getParent(graph, 2) == NIL);
}

int testParent(void) {
	Graph graph = newGraph(2);
	addEdge(graph, 1, 2);
	BFS(graph, 1);
	return (getParent(graph, 2) == 1);
}

int testDistNoBFS(void) {
	Graph graph = newGraph(2);
	return(getDist(graph, 2) == INF);
}

int testDist(void) {
	Graph graph = newGraph(2);
	addEdge(graph, 1, 2);
	BFS(graph, 1);
	return(getDist(graph, 2) == 1);
}

int testPathNoBFS(void) {
	Graph graph = newGraph(4);
	List list = newList();
	addEdge(graph, 1, 2);
	addEdge(graph, 2, 3);
	addEdge(graph, 3, 4);
	getPath(list, graph, 4);
	moveFront(list);
	return(get(list) == NIL);
}

int testPath(void) {
	Graph graph = newGraph(4);
	List list = newList();
	addEdge(graph, 1, 3);
	addEdge(graph, 3, 2);
	addEdge(graph, 2, 4);
	BFS(graph, 1);
	getPath(list, graph, 4);
	printf("\tPath: "); // TODO: Should be 1 3 2 4, check console
	for (moveFront(list); get(list) >= 0; moveNext(list)) {
		printf("%d ", get(list));
	}
	printf("\n");
	return(length(list) == 4);
}

int testMakeNull(void) {
	Graph graph = newGraph(4);
	addEdge(graph, 1, 2);
	addEdge(graph, 2, 3);
	addEdge(graph, 3, 4);
	BFS(graph, 1);
	makeNull(graph);
	return(getSize(graph) == 0);
}


/* Tester Test*/
int testFail(void) {
	return 0;
}

/* Main */

int main(void) {

	int numTests = 0;
	Test tests[] = {
		newTest("Get Order\t", &testGetOrder, &numTests),
		newTest("Get Size\t\t", &testGetSize, &numTests),
		newTest("Get Source Before BFS", &testSourceNoBFS, &numTests),
		newTest("Get Source After BFS", &testSource, &numTests),
		newTest("Get Parent Before BFS", &testParentNoBFS, &numTests),
		newTest("Get Parent After BFS", &testParent, &numTests),
		newTest("Get Distance Before BFS", &testDistNoBFS, &numTests),
		newTest("Get Distance After BFS", &testDist, &numTests),
		newTest("Get Path Before BFS", &testPathNoBFS, &numTests),
		newTest("Get Path After BFS", &testPath, &numTests),
		newTest("Make Null\t", &testMakeNull, &numTests),
		newTest("This Test Fails Deliberately", &testFail, &numTests),
	};

	for (int ii = 0; ii < numTests; ii += 1) {
		executeTest(tests[ii]);
		free(tests[ii]);
		tests[ii] = NULL;
	}

	exit(0);
}
