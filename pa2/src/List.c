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
#include "List.h"

Node newNode(int passedValue) {
	// TODO:
	return NULL;
}

void freeNode(Node passedNode) {
	// TODO:
}

/* Node Functions */

int nodesAreEqual(Node passedFirstNode, Node passedSecondNode) {
	if ((passedFirstNode != NULL) && (passedSecondNode != NULL)) {
		return (passedFirstNode->value == passedSecondNode->value);
	}
	return FALSE;
}

/* List Definition */

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
	if ((passedFirstList != NULL) && (passedSecondList != NULL)) {
		int equals = (passedFirstList->length == passedSecondList->length);
		if (equals) {
			Node firstNode = passedFirstList->nodeFront;
			Node secondNode = passedSecondList->nodeFront;
			while (equals) {
				equals = nodesAreEqual(firstNode, secondNode);
				firstNode = firstNode->nextNode;
				firstNode = firstNode->nextNode;
			}
		}
		return equals;
	}
	return FALSE;
}

void clear(List passedList) {
	if (checkList(passedList, "Error with List when clear was called:", FALSE) != FALSE) {
		while (passedList->length > 0) {
			deleteBack(passedList);
		}
	}
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
			decrementIndex(passedList);
		}
	}
}

void moveNext(List passedList) {
	if (checkList(passedList, "Error with List when moveNext was called:", FALSE) != FALSE) {
		if (isNull(passedList->nodeCursor, "Error with List's Cursor Node:", TRUE) != FALSE) {
			passedList->nodeCursor = passedList->nodeCursor->nextNode;
			incrementIndex(passedList);
		}
	}
}

void prepend(List passedList, int passedValue) {
	if (isNull(passedList, "Cannot prepend to a null List!", TRUE) != FALSE) {
		Node newNode = newNode(passedValue);
		if (insertNodeBefore(passedList->nodeFront, newNode) == TRUE) {
			incrementIndex(passedList);
			passedList->length += 1;
		}
	}
}

void append(List passedList, int passedValue) {
	if (isNull(passedList, "Cannot append to a null List!", TRUE) != FALSE) {
		Node newNode = newNode(passedValue);
		if (insertNodeAfter(passedList->nodeBack, newNode) != FALSE) {
			passedList->length += 1;
		}
	}
}

void insertBefore(List passedList, int passedValue) {
	if (isNull(passedList, "Cannot insert into a null List!", TRUE) != FALSE) {
		if (isCursorValid(passedList, "Cannot insert into a List with an undefined cursor!", TRUE) != FALSE) {
			Node newNode = newNode(passedValue);
			if (insertNodeBefore(passedList->nodeCursor) != FALSE) {
				incrementIndex(passedList);
				passedList->length += 1;
			}
		}
	}
}

void insertAfter(List passedList, int passedValue) {
	if (isNull(passedList, "Cannot insert into a null List!", TRUE) != FALSE) {
		if (isCursorValid(passedList, "Cannot insert into a List with an undefined cursor!", TRUE) != FALSE) {
			Node newNode = newNode(passedValue);
			if (insertNodeAfter(passedList->nodeCursor, newNode) != FALSE) {
				passedList->length += 1;
			}
		}
	}
}

void deleteFront(List passedList) {
	if (checkList(passedList, "Error when deleting the front Node of a List", TRUE) != FALSE) {
		if (removeNode(passedList->nodeFront) != FALSE) {
			passedList->length -= 1;
			decrementIndex(passedList);
		}
	}
}

void deleteBack(List passedList) {
	if (checkList(passedList, "Error when deleting the back Node of a List", TRUE) != FALSE) {
		if (removeNode(passedList->nodeBack) != FALSE) {
			passedList->length -= 1;
		}
	}
}

void delete(List passedList) {
	if (checkList(passedList, "Error when deleting the cursor Node of a List", TRUE) != FALSE) {
		if (removeNode(passedList->nodeCursor) != FALSE) {
			passedList->length -= 1;
			passedList->cursorIndex = UNDEFINED;
		}
	}
}

List concatList(List passedFirstList, List passedSecondList) {
	int firstListValid = checkList(passedFirstList, "Error with prepended List during concatenation:", FALSE);
	int secondListValid = checkList(passedFirstList, "Error with appended List during concatenation:", FALSE);
	List newList = newList();
	// If both Lists are valid, concatenate
	if ((firstListValid != FALSE) && (secondListValid != FALSE)) {
		List newList = copyList(passedFirstList);
		Node iteratedNode = passedSecondList->nodeFront;
		while (iteratedNode != NULL) {
			append(newList, iteratedNode->value);
			iteratedNode = iteratedNode->nextNode;
		}
		return newList;
	}
	// If only first List is valid, use that one
	else if (firstListValid != FALSE) {
		return copyList(passedFirstList);
	}

	// If only second List is valid, use that one
	else if (secondListValid != FALSE) {
		return copyList(passedSecondList);
	}

	// Otherwise, return empty List
	return newList;
}

void printList(FILE* passedOutputFile, List passedList) {
	if(isNull(passedOutputFile, "Cannot print to a null file reference.", FALSE) != FALSE) {
		if (checkList(passedList, "Error with List during print:", FALSE) != FALSE) {
			Node iteratedNode = passedList->nodeFront;
			while (iteratedNode != NULL) {
				fprintf(passedOutputFile, "%d", iteratedNode->value);
				iteratedNode = iteratedNode->nextNode;
				if (iteratedNode != NULL) {
					fprintf(passedOutputFile, " ");
				}
			}
		}
	}
	fclose(passedOutputFile);
}

List copyList(List passedList) {
	List newList = newList();
	if (checkList(passedList, "Cannot copy a null List.", FALSE) != FALSE) {
		Node iteratedNode = passedList->nodeFront;
		while (iteratedNode != NULL) {
			append(newList, iteratedNode->value);
			iteratedNode = iteratedNode->nextNode;
		}
	}
	return newList;
}

/* Internal Functions */

/**
 * Inserts passedInsertedNode before passedNode.
 */
int insertNodeBefore(Node passedNode, Node passedInsertedNode) {
	int passedNodeValid = isNull(passedNode, "Attempting to insert before null Node", FALSE);
	int insertedNodeValid = isNull(passedNode, "Attempting to insert null Node", FALSE);
	if ((passedNodeValid != FALSE) && (insertedNodeValid != FALSE)) {
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

/**
 * Inserts passedInsertedNode after the passedNode.
 */
int insertNodeAfter(Node passedNode, Node passedInsertedNode) {
	int passedNodeValid = isNull(passedNode, "Attempting to insert after null Node", FALSE);
	int insertedNodeValid = isNull(passedNode, "Attempting to insert null Node", FALSE);
	if ((passedNodeValid != FALSE) && (insertedNodeValid != FALSE)) {
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
 * Removes the passedNode, freeing it.
 */
int removeNode(Node passedNode) {
	if (isNull(passedNode, "Cannot remove a Node that is null.", FALSE) != FALSE) {
		Node nextNode = passedNode->nextNode;
		Node prevNode = passedNode->previousNode;
		if (nextNode != NULL) {
			nextNode->previousNode = passedNode->previousNode;
		}
		if (prevNode != NULL) {
			prevNode->nextNode = passedNode->nextNode;
		}
		freeNode(passedNode);
		return TRUE;
	}
	return FALSE;
}

/**
 * Increments the cursor index of passedList. Does not perform null check.
 */
void inline incrementIndex(List passedList) {
	passedList->cursorIndex += 1;
}

/**
 * Decrements the cursor index of passedList. Does not perform null check.
 */
void inline decrementIndex(List passedList) {
	passedList->cursorIndex -= 1;
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
	return (isNull(passedListPointer->nodeCursor) != FALSE);
}

/**
 * Exits with a code of 1 after printing the passed message.
 */
void inline exitBadWithMessage(const char* passedCharArray) {
	puts(passedCharArray);
	exit(1);
}
