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
#include <string.h>

#include "Graph.h"

FILE* openAndVerify(char*[], char*[], char*);
readPair(FILE*, int*, int*);

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
		inputFile = openAndVerify(passedArguments[1], "input", 'r');
		outputFile = openAndVerify(passedArguments[2], "input", 'w');

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
			fprintf("\n");

			// Get paths and print results
			List tempList = newList();
			while (readPair(inputFile, &firstValue, &secondValue)) {
				// Run BFS from source vertex
				BFS(graph, firstValue);

				// Print distance
				fprintf("The distance from %d to %d is %d\n", firstValue, secondValue, getDist(graph, secondValue));

				// Print path
				clearList(tempList);
				getPath(graph, tempList, secondValue);
				fprintf("A shortest %d-%d path is:", firstValue, secondValue);
				printList(outputFile, tempList);
				fprintf("\n");
			}
			freeList(tempList);
		}
		else {
			puts("Error: Could not initialize graph from file.");
		}

		fclose(inputFile);
		fclose(outputFile);
	}
	exit(0);
}

int readPair(FILE* passedFile, int* passedFirstValue, int* passedSecondValue) {
	int firstIndex, secondIndex;
	int firstCharsRead, secondCharsRead;
	firstCharsRead = fscanf(passedFile, "%d", &firstIndex);
	secondCharsRead = fscanf(passedFile, "%d", &secondIndex);

	if ((firstCharsRead > 0) && (secondCharsRead > 0)) {
		(*passedFirstValue) = firstIndex;
		(*passedSecondValue) = secondIndex;
		return TRUE;
	}
	return FALSE;
}

FILE* openAndVerify(char passedFileName[], char passedFileType[], char passedOpenType) {
	FILE* openedFile;
	openedFile = fopen(passedFileName, passedOpenType);
	if (openedFile == NULL) {
		printf("Error reading %s file: %s\n", passedFileType, passedFileName);
		exit(1);
	}
	return openedFile;
}
