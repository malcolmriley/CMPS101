/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa2
 *
 * 10-2017
 *********************************************************************/

#include <stdio.h>
#include "List.h"

/* Node Type Definition */
typedef struct NodeObj {
	int value;
	struct NodeObj* nextNode;
	struct NodeObj* previousNode;
} NodeObj;
typedef NodeObj* Node;

Node newNode(int passedValue) {
	return NULL;
}

void freeNode(Node* passedNode) {

}

/* List Globals */
const int TRUE = 1;
const int FALSE = 0;
const int INDEX_UNDEFINED = -1;

/* List Function Definitions */

/* Internal Functions */
int inline isNull(void* passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if (passedListPointer == NULL) {
		puts("Error: Null pointer received.");
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage(passedCharArray);
		}
		return TRUE;
	}
	return FALSE;
}

int inline isListEmpty(List* passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if (length(passedListPointer) <= 0) {
		puts("Error: List is empty.");
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage(passedCharArray);
		}
		return TRUE;
	}
	return FALSE;
}

int inline isCursorValid(List* passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if ((index(passedListPointer) < 0) || (index(passedListPointer) > length)) {
		puts("Error: Cursor is invalid.");
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage(passedCharArray);
		}
		return FALSE;
	}
	// TODO: Change to !isNull(cursor)
	return TRUE;
}

void inline exitBadWithMessage(char* passedCharArray) {
	puts(passedCharArray);
	exit(1);
}
