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

/* List Function Definitions */

/* Internal Functions */
void inline checkNull(void* passedListPointer, char* passedCharArray) {
	if (passedListPointer == NULL) {
		puts("Error: Null pointer received.");
		exitBadWithMessage(passedCharArray);
	}
}

void inline checkEmpty(List* passedListPointer, char* passedCharArray) {
	if (length(passedListPointer) <= 0) {
		puts("Error: List is empty.");
		exitBadWithMessage(passedCharArray);
	}
}

void inline checkCursor(List* passedListPointer, char* passedCharArray) {
	if ((index(passedListPointer) < 0) || (index(passedListPointer) > length)) {
		puts("Error: Cursor is invalid.");
		exitBadWithMessage(passedCharArray);
	}
}

void inline exitBadWithMessage(char* passedCharArray) {
	puts(passedCharArray);
	exit(1);
}
