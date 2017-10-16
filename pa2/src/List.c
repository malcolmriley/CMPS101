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
	//TODO:
	return NULL;
}

void freeList(List* passedList) {
	// TODO:
}

/* List Functions */
int length(List passedList) {
	if (!checkList(passedList, FALSE) != FALSE) {
		return passedList->length;
	}
	return UNDEFINED;
}

int index(List passedList) {
	if (!checkList(passedList, FALSE) != FALSE) {
		return passedList->cursorIndex;
	}
	return UNDEFINED;
}

int front(List passedList) {
	if(!checkList(passedList, TRUE) != FALSE) {
		if (!isNull(passedList->nodeFront, "Front Node is NULL.", FALSE) != FALSE) {
			return passedList->nodeFront->value;
		}
	}
	return UNDEFINED;
}


int back(List passedList) {
	if(!checkList(passedList, TRUE) != FALSE) {
		if (!isNull(passedList->nodeBack, "Back Node is NULL.", FALSE) != FALSE) {
			return passedList->nodeBack->value;
		}
	}
	return UNDEFINED;
}

int get(List passedList) {
	if(!checkList(passedList, TRUE) != FALSE) {
		if (!isNull(passedList->nodeCursor, "Cursor Node is NULL.", FALSE) != FALSE) {
			return passedList->nodeCursor->value;
		}
	}
	return UNDEFINED;
}

int equals(List passedFirstList, List passedSecondList) {
	int isFirstNull = isNull(passedFirstList, "", FALSE);
	int isSecondNull = isNull(passedSecondList, "", FALSE);
	if ((isFirstNull | isSecondNull) != FALSE) {
		int firstLength = length(passedFirstList);
		int secondLength = length(passedSecondList);
		if (firstLength == secondLength) {
			if (firstLength == 0) {
				return TRUE;
			}
			else {
				// TODO: Iterate over and compare
			}
		}
	}
	return FALSE;
}

void clear(List passedList) {
	// TODO: Iterate over, free nodes
}

/* Internal Functions */

/**
 * Checks whether the passed List is NULL or empty, and prints the passed message in those cases.
 *
 * If passedIsTerminal is specifically 1, calls exitBadWithMessage using a relevant error message.
 */
int inline checkList(List passedList, char* passedCharArray, int passedIsTerminal) {
	return isNull(passedList, passedCharArray, passedIsTerminal) | isListEmpty(passedList, passedCharArray, passedIsTerminal);
}

/**
 * Checks whether the passed pointer is null, and prints the passed message in that case.
 *
 * If passedIsTerminal is specifically 1, calls exitBadWithMessage with an error message.
 */
int inline isNull(void* passedPointer, char* passedCharArray, int passedIsTerminal) {
	if (passedPointer == NULL) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Error: Null pointer received.");
		}
		return TRUE;
	}
	return FALSE;
}

/**
 * Checks whether the passed List is null
 */
int inline isListEmpty(List* passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if (length(passedListPointer) <= 0) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Error: List is empty.");
		}
		return TRUE;
	}
	return FALSE;
}

int inline isCursorValid(List* passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if ((index(passedListPointer) < 0) || (index(passedListPointer) > length)) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Error: Cursor is invalid.");
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
