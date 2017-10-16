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

/* Node Definition */
typedef struct NodeObject {
	int value;
	struct NodeObject* nextNode;
	struct NodeObject* previousNode;
} NodeObject;
typedef NodeObject* Node;

Node newNode(int passedValue) {
	return NULL;
}

void freeNode(Node passedNode) {

}

/* Node Functions */

int equals(Node passedFirstNode, Node passedSecondNode) {
	return FALSE;
}

/* List Definition */
typedef struct ListObject {
	int length = 0;
	int cursorIndex = UNDEFINED;
	Node* nodeFront;
	Node* nodeBack;
	Node* nodeCursor;
} ListObject;

/* List Constructor/Destructor */
List newList(void) {

}

void freeList(List* passedList) {

}

/* List Functions */
int length(List passedList) {
	return passedList->length;
}

int index(List passedList) {
	if (!checkList(passedList, TRUE) != FALSE) {
		return passedList->cursorIndex;
	}
	return UNDEFINED;
}

int front(List passedList) {
	if(!checkList(passedList, TRUE) != FALSE) {

	}
	return UNDEFINED;
}

/* Internal Functions */
int inline checkList(List passedList, int passedIsTerminal) {
	return isNull(passedList, "List is null.", passedIsTerminal) | isListEmpty(passedList, "List is empty.", passedIsTerminal);
}

int inline isNull(void* passedPointer, char* passedCharArray, int passedIsTerminal) {
	if (passedPointer == NULL) {
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
