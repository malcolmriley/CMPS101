/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa
 *
 * 11-2017
 *********************************************************************/
#include <stdio.h>
#include <stdlib.h>

#include "Graph.h"
#include "List.h"

FILE* openAndVerify(char[], char[], char*);
int readPair(FILE*, int*, int*);
void fillList(List, int);
int getAscendant(Graph, int);
void getTrees(List, List[]);

int main(int passedArgumentCount, char* passedArguments[]) {

	if (passedArgumentCount < 3) {
		printf("Incorrect number of arguments received.\nTry: %s [input] [output]\n", passedArguments[0]);
		exit(1);
	}
	else {
		FILE* inputFile;
		FILE* outputFile;

		Graph graph;

		// Verify indicated files
		inputFile = openAndVerify(passedArguments[1], "input", "r");
		outputFile = openAndVerify(passedArguments[2], "output", "w");

		// Initialize Graph using first line of file
		int order = NIL;
		fscanf(inputFile, "%d", &order);
		graph = newGraph(order);

		// Populate graph with edges and print resulting graph
		if (graph != NULL) {
			int firstValue = 0;
			int secondValue = 0;

			// Read Graph from File
			while (readPair(inputFile, &firstValue, &secondValue)) {
				addArc(graph, firstValue, secondValue);
			}

			// Print Adjacency List
			fputs("Adjacency list representation of G:\n", outputFile);
			printGraph(outputFile, graph);
			fputs("\n", outputFile);

			// Run DFS on graph
			List list = newList();
			fillList(list, getOrder(graph));
			DFS(graph, list);

			// Run DFS on transpose of graph
			Graph transposeGraph = transpose(graph);
			DFS(transposeGraph, list);

			// Count roots of DFS forest
			List roots = newList();
			for (moveBack(list); index(list) >= 0; movePrev(list)) {
				int iteratedVertex = get(list);
				if (getParent(transposeGraph, iteratedVertex) < 0) {
					append(roots, iteratedVertex);
				}
			}
			int rootCount = length(roots);
			fprintf(outputFile, "G contains %d strongly connected components:\n", rootCount);

			// Determine components of trees
			List trees[rootCount];
			getTrees(roots, trees);
			for (moveFront(list); index(list) >= 0; moveNext(list)) {
				int iteratedVertex = get(list);
				int ascendant = getAscendant(transposeGraph, iteratedVertex);
				for (int ii = 0; ii < rootCount; ii += 1) {
					if ((front(trees[ii]) == ascendant) && (front(trees[ii]) != iteratedVertex)) {
						append(trees[ii], iteratedVertex);
					}
				}
			}

			// Print Components
			for (int ii = 0; ii < rootCount; ii += 1) {
				fprintf(outputFile, "Component %d: ", (ii + 1));
				printList(outputFile, trees[ii]);
				fprintf(outputFile, "\n");
			}

			// Cleanup
			for (int ii = 0; ii < rootCount; ii += 1) {
				freeList(&trees[ii]);
			}
			freeList(&roots);
			freeGraph(&graph);
			freeGraph(&transposeGraph);
		}
		else {
			puts("Error: Could not initialize graph from file.");
		}

		fclose(inputFile);
		fclose(outputFile);
	}
	exit(0);
}

/**
 * Reads a pair of integer values into the passed integer locations from the passed FILE.
 */
int readPair(FILE* passedFile, int* passedFirstValue, int* passedSecondValue) {
	int firstIndex = NIL, secondIndex = NIL;
	int firstCharsRead = NIL, secondCharsRead = NIL;
	firstCharsRead = fscanf(passedFile, "%d", &firstIndex);
	secondCharsRead = fscanf(passedFile, "%d", &secondIndex);

	if ((firstCharsRead > 0) && (secondCharsRead > 0)) {
		if ((firstIndex > 0) && (secondIndex > 0)) {
			(*passedFirstValue) = firstIndex;
			(*passedSecondValue) = secondIndex;
			return TRUE;
		}
	}
	return FALSE;
}

void fillList(List passedList, int passedMaxValue) {
	for (int ii = 1; ii <= passedMaxValue; ii += 1) {
		append(passedList, ii);
	}
}

void getTrees(List passedList, List passedArray[]) {
	for (moveFront(passedList); index(passedList) >= 0; moveNext(passedList)) {
		passedArray[index(passedList)] = newList();
		append(passedArray[index(passedList)], get(passedList));
	}
}

int getAscendant(Graph passedGraph, int passedVertex) {
	int parent = passedVertex;
	while (getParent(passedGraph, parent) >= 0) {
		parent = getParent(passedGraph, parent);
	}
	return parent;
}

/**
 * Opens and verifies the passed file, printing a message and exiting the program with status 1 if the file fails to load.
 */
FILE* openAndVerify(char passedFileName[], char passedFileType[], char* passedOpenType) {
	FILE* openedFile;
	openedFile = fopen(passedFileName, passedOpenType);
	if (openedFile == NULL) {
		printf("Error reading %s file: %s\n", passedFileType, passedFileName);
		exit(1);
	}
	return openedFile;
}
