/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa2
 *
 * 10-2017
 *********************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "List.h"

void populateArray(FILE* passedFile, char* passedArray[], int passedArrayWidth);
void freeArray(char* passedArray[], int passedArrayLength);
void buildSortedList(List passedList, char* passedArray[], int passedArrayLength);
void printListToStream(List passedList, char* passedArray[], FILE* passedOutput);

int main(int passedArgumentCount, char* passedArguments[]) {

	FILE *inputFile, *outputFile;

	// Verify argument count
	if (passedArgumentCount < 3) {
		printf("Incorrect number of arguments received.\nTry: %s [input] [output]\n", passedArguments[0]);
		exit(1);
	} else {

		// Verify indicated files
		inputFile = fopen(passedArguments[1], "r");
		outputFile = fopen(passedArguments[2], "w");
		if (inputFile == NULL) {
			printf("Error reading input file: %s\n", passedArguments[1]);
			exit(1);
		}
		if (outputFile == NULL) {
			printf("Error reading output file: %s\n", passedArguments[2]);
			exit(1);
		}

		// Count lines in file and max line width
		int newlines = 0;
		int maxLineWidth = 0;
		int currentLineWidth = 0;
		char character = fgetc(inputFile);
		while(character != EOF) {
			currentLineWidth += 1;
			if (character == '\n') {
				newlines += 1;
				if (currentLineWidth > maxLineWidth) {
					maxLineWidth = currentLineWidth;
				}
				currentLineWidth = 0;
			}
		}
		// If there is at least one non EOF character in the file, count it as a line.
		if ((newlines == 0) && (character != EOF)){
			newlines = 1;
		}
		// If there is at least one line in the file by previous definition
		if (newlines > 0) {
			// Allocate array of pointers to char arrays
			char* stringArray[newlines];

			// Reopen file
			fclose(inputFile);
			inputFile = fopen(passedArguments[1], "r");

			populateArray(inputFile, stringArray, maxLineWidth);

			List allocatedList = newList();
			buildSortedList(allocatedList, stringArray, newlines);
			printListToStream(allocatedList, stringArray, outputFile);
			freeArray(stringArray, newlines);
			freeList(&allocatedList);
		}

		fclose(inputFile);
		fclose(outputFile);
	}
	exit(0);
}

/* Internal Functions */
void populateArray(FILE* passedFile, char* passedArray[], int passedArrayWidth) {
	char* iteratedString = NULL;
	iteratedString = fgets(iteratedString, passedArrayWidth, passedFile);
	int lineCounter = 0;
	while (iteratedString != NULL) {
		passedArray[lineCounter] = malloc(strlen(iteratedString) + 1);
		strcpy(passedArray[lineCounter], iteratedString);
		iteratedString = fgets(iteratedString, passedArrayWidth, passedFile);
		lineCounter += 1;
	}
	free(iteratedString);
}

void freeArray(char* passedArray[], int passedArrayLength) {
	for (int ii = 0; ii < passedArrayLength; ii += 1) {
		free(passedArray[ii]);
	}
	free(passedArray);
}

void buildSortedList(List passedList, char* passedArray[], int passedArrayLength) {
	append(passedList, 0);
	char* currentString;
	char* cursorString;
	for (int ii = 1; ii < passedArrayLength; ii += 1) {
		moveBack(passedList);
		currentString = passedArray[ii];
		while (index(passedList) >= 0) {
			cursorString = passedArray[get(passedList)];
			if (strcmp(currentString, cursorString)) {
				insertAfter(passedList, ii);
				break;
			}
			if (index(passedList) == 0) {
				prepend(passedList, ii);
				break;
			}
			movePrev(passedList);
		}
	}
}

void printListToStream(List passedList, char* passedArray[], FILE* passedOutput) {
	Node iteratedNode = passedList->nodeFront;
	while (iteratedNode != NULL) {
		fprintf(passedOutput, "%s\n", passedArray[iteratedNode->value]);
		iteratedNode = iteratedNode->nextNode;
	}
}
