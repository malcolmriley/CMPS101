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

void moveFront(List passedList) {
	if (checkList(passedList, "Error with List when moveFront was called:", TRUE) != FALSE) {
		if (isNull(passedList->nodeFront, "Error with List's front Node:", TRUE) != FALSE) {
			passedList->nodeCursor = passedList->nodeFront;
			passedList->cursorIndex = 0;
		}
	}
}

void moveBack(List passedList) {
	if (checkList(passedList, "Error with List when moveBack was called:", TRUE) != FALSE) {
		if (isNull(passedList->nodeBack, "Error with List's back Node:", TRUE) != FALSE) {
			passedList->nodeCursor = passedList->nodeBack;
			passedList->cursorIndex = (length(passedList) - 1);
		}
	}
}

void movePrev(List passedList) {
	if (checkList(passedList, "Error with List when movePrev was called:", FALSE) != FALSE) {
		if (isNull(passedList->nodeCursor, "Error with List's Cursor Node:", TRUE) != FALSE) {
			passedList->nodeCursor = passedList->nodeCursor->previousNode;
			passedList->cursorIndex = (passedList->cursorIndex - 1);
		}
	}
}

void moveNext(List passedList) {
	if (checkList(passedList, "Error with List when moveNext was called:", FALSE) != FALSE) {
		if (isNull(passedList->nodeCursor, "Error with List's Cursor Node:", TRUE) != FALSE) {
			passedList->nodeCursor = passedList->nodeCursor->nextNode;
			passedList->cursorIndex = (passedList->cursorIndex + 1);
		}
	}
}

void prepend(List passedList, int passedValue) {
	if (isNull(passedList, "Cannot prepend to a null List!", TRUE) != FALSE) {
		Node newNode = newNode(passedValue);

	}
}

void append(List passedList, int passedValue) {
	if (isNull(passedList, "Cannot append to a null List!", TRUE) != FALSE) {
		Node newNode = newNode(passedValue);

	}
}

/* Internal Functions */

int insertNodeBefore(List passedList, Node passedNode, Node passedInsertedNode) {
	int passedNodeValid = isNull(passedNode, "Attempting to insert before null Node", FALSE);
	int insertedNodeValid = isNull(passedNode, "Attempting to insert null Node", FALSE);
	int listValid = isNull(passedList, "Attempting to insert into null List", TRUE);
	if ((passedNodeValid != FALSE) && (insertedNodeValid != FALSE) && (listValid != FALSE)) {
		// Set pointers on inserted Node
		passedInsertedNode->nextNode = passedNode;
		passedInsertedNode->previousNode = passedNode->previousNode;

		// Set pointers on existing previous Node
		if(passedNode->previousNode != NULL) {
			passedNode->previousNode->nextNode = passedInsertedNode;
		}

		// Set pointers on existing Node
		passedNode->previousNode = passedInsertedNode;
		return TRUE;
	}
	return FALSE;
}

int insertNodeAfter(List passedList, Node passedNode, Node passedInsertedNode) {
	int passedNodeValid = isNull(passedNode, "Attempting to insert after null Node", FALSE);
	int insertedNodeValid = isNull(passedNode, "Attempting to insert null Node", FALSE);
	int listValid = isNull(passedList, "Attempting to insert into null List", TRUE);
	if ((passedNodeValid != FALSE) && (insertedNodeValid != FALSE) && (listValid != FALSE)) {
		// Set pointers on inserted Node
		passedInsertedNode->previousNode = passedNode;
		passedInsertedNode->nextNode = passedNode->nextNode;

		// Set pointers on existing next Node
		if(passedNode->nextNode != NULL) {
			passedNode->nextNode->previousNode = passedInsertedNode;
		}

		// Set pointers on existing node
		passedNode->nextNode = passedInsertedNode;
		return TRUE;
	}
	return FALSE;
}

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
 * Checks whether the passed List is empty. Does not perform null check on the passed List.
 */
int inline isListEmpty(List passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if (length(passedListPointer) <= 0) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Error: List is empty.");
		}
		return TRUE;
	}
	return FALSE;
}

/**
 * Checks whether the passed List's cursor is valid. Does not perform null check on the passed List.
 */
int inline isCursorValid(List passedListPointer, char* passedCharArray, int passedIsTerminal) {
	if ((index(passedListPointer) < 0) || (index(passedListPointer) >= length)) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Error: Cursor is invalid.");
		}
		passedListPointer->cursorIndex = UNDEFINED;
		return FALSE;
	}
	return isNull(passedListPointer->nodeCursor);
}

/**
 * Exits with a code of 1 after printing the passed message.
 */
void inline exitBadWithMessage(const char* passedCharArray) {
	puts(passedCharArray);
	exit(1);
}
