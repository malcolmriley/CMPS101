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

FILE* openAndVerify(char[], char[], char*);
int readPair(FILE*, int*, int*);

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

		if (graph != NULL) {
			int firstValue = 0;
			int secondValue = 0;

			// Populate graph with edges and print resulting graph
			while (readPair(inputFile, &firstValue, &secondValue)) {
				addEdge(graph, firstValue, secondValue);
			}
			printGraph(outputFile, graph);
			fputs("\n", outputFile);

			// Get paths and print results
			List tempList = newList();
			while (readPair(inputFile, &firstValue, &secondValue)) {
				// Run BFS from source vertex
				BFS(graph, firstValue);

				// Print distance
				int distance = getDist(graph, secondValue);
				if (distance != INF) {
					fprintf(outputFile, "The distance from %d to %d is %d\n", firstValue, secondValue, getDist(graph, secondValue));
				}
				else {
					fprintf(outputFile, "The distance from %d to %d is infinity\n", firstValue, secondValue);
				}

				// Print path
				clear(tempList);
				getPath(tempList, graph, secondValue);
				moveFront(tempList);
				if (get(tempList) != NIL) {
					fprintf(outputFile, "A shortest %d-%d path is: ", firstValue, secondValue);
					printList(outputFile, tempList);
				}
				else {
					fprintf(outputFile, "No %d-%d path exists", firstValue, secondValue);
				}
				fputs("\n\n", outputFile);
			}
			freeList(&tempList);
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
