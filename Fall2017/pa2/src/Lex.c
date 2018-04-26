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

	FILE* inputFile;
	FILE* outputFile;

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
			character = fgetc(inputFile);
		}
		// If there is at least one non EOF character in the file, count it as a line.
		if ((newlines == 0) && (maxLineWidth > 0)){
			newlines = 1;
		}
		// If there is at least one line in the file by previous definition
		if (newlines > 0) {
			// Allocate array of pointers to char arrays
			char* stringArray[newlines];

			// Reopen file
			rewind(inputFile);

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
	int stringSize = passedArrayWidth + 1;
	char* iteratedString = malloc(stringSize);
	int lineCounter = 0;
	while (fgets(iteratedString, stringSize, passedFile) != NULL) {
		passedArray[lineCounter] = malloc(stringSize);
		strncpy(passedArray[lineCounter], iteratedString, stringSize);
		lineCounter += 1;
	}
	free(iteratedString);
}

void freeArray(char* passedArray[], int passedArrayLength) {
	for (int ii = 0; ii < passedArrayLength; ii += 1) {
		free(passedArray[ii]);
	}
}

void buildSortedList(List passedList, char* passedArray[], int passedArrayLength) {
	append(passedList, 0);
	for (int ii = 1; ii < passedArrayLength; ii += 1) {
		moveBack(passedList);
		while (index(passedList) >= 0) {
			if (strcmp(passedArray[ii], passedArray[get(passedList)]) > 0) {
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
	Node iteratedNode = NULL;
	if (length(passedList) > 0) {
		iteratedNode = passedList->nodeFront;
	}
	while (iteratedNode != NULL) {
		fprintf(passedOutput, "%s", passedArray[iteratedNode->value]);
		iteratedNode = iteratedNode->nextNode;
	}
}
