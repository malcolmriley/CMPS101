/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4, modified from pa2
 *
 * 11-2017
 *********************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "List.h"

/* Internal Function Declarations */
void exitBadWithMessage(const char* passedCharArray);
void insertIntoEmpty(List passedList, Node passedNode);
int insertNodeBefore(List passedList, Node passedNode, Node passedInsertedNode);
int insertNodeAfter(List passedList, Node passedNode, Node passedInsertedNode);
int removeNode(List passedList, Node passedNode);
int isNull(void* passedPointer, char* passedCharArray, int passedIsTerminal);
int isListEmpty(List passedList, char* passedCharArray, int passedIsTerminal);
int validateIndex(List passedList);
int isCursorValid(List passedList, char* passedCharArray, int passedIsTerminal);

Node newNode(int passedValue) {
	Node newNode = malloc(sizeof(NodeObject));
	newNode->nextNode = NULL;
	newNode->previousNode = NULL;
	newNode->value = passedValue;
	return newNode;
}

void freeNode(Node* passedNode) {
	(*passedNode)->nextNode = NULL;
	(*passedNode)->previousNode = NULL;
	// free(passedNode);
	free(*passedNode);
}

/* Node Functions */

int nodesAreEqual(Node passedFirstNode, Node passedSecondNode) {
	if ((passedFirstNode != NULL) && (passedSecondNode != NULL)) {
		return (passedFirstNode->value == passedSecondNode->value);
	}
	return FALSE;
}

/* List Constructor/Destructor */
List newList(void) {
	List newList = malloc(sizeof(ListObject));
	newList->cursorIndex = UNDEFINED;
	newList->length = 0;
	newList->nodeBack = NULL;
	newList->nodeFront = NULL;
	newList->nodeCursor = NULL;
	return newList;
}

void freeList(List* passedList) {
	clear(*passedList);
	(*passedList)->nodeBack = NULL;
	(*passedList)->nodeFront = NULL;
	(*passedList)->nodeCursor = NULL;
	// free(passedList);
	free(*passedList);
}

/* List Functions */
int length(List passedList) {
	if (!isNull(passedList, "Cannot retrieve the length of a null List.", TRUE)) {
		return passedList->length;
	}
	return UNDEFINED;
}

int index(List passedList) {
	if (!isNull(passedList, "Cannot retrieve the index of a null List.", TRUE)) {
		validateIndex(passedList);
		return passedList->cursorIndex;
	}
	return UNDEFINED;
}

int front(List passedList) {
	if (!isNull(passedList, "Cannot retrieve the front of a null List.", TRUE)) {
		if (!isNull(passedList->nodeFront, "Front Node is NULL.", FALSE)) {
			return passedList->nodeFront->value;
		}
	}
	return UNDEFINED;
}


int back(List passedList) {
	if (!isNull(passedList, "Cannot retrieve the back of a null List.", TRUE)) {
		if (!isNull(passedList->nodeBack, "Back Node is NULL.", FALSE)) {
			return passedList->nodeBack->value;
		}
	}
	return UNDEFINED;
}

int get(List passedList) {
	if (!isNull(passedList, "Cannot retrieve the cursor of a null List.", TRUE)) {
		if (!isNull(passedList->nodeCursor, "Cursor Node is NULL.", FALSE)) {
			return passedList->nodeCursor->value;
		}
	}
	return UNDEFINED;
}

int equals(List passedFirstList, List passedSecondList) {
	if ((!isNull(passedFirstList, "Equals: First list is null.", FALSE)) && (!isNull(passedSecondList, "Equals: Second list is null.", FALSE))) {
		int areEqual = (passedFirstList->length == passedSecondList->length);
		if (areEqual) {
			Node firstNode = passedFirstList->nodeFront;
			Node secondNode = passedSecondList->nodeFront;
			for(int ii = 0; (ii < passedFirstList->length) && (areEqual); ii += 1) {
				areEqual = nodesAreEqual(firstNode, secondNode);
				firstNode = firstNode->nextNode;
				secondNode = secondNode->nextNode;
			}
		}
		return areEqual;
	}
	return FALSE;
}

void clear(List passedList) {
	while (passedList->length > 0) {
		deleteBack(passedList);
	}
}

void moveFront(List passedList) {
	if (!isListEmpty(passedList, "Error with List when moveFront was called:", TRUE)) {
		if (!isNull(passedList->nodeFront, "Error with List's front Node:", FALSE)) {
			passedList->nodeCursor = passedList->nodeFront;
			passedList->cursorIndex = 0;
		}
	}
}

void moveBack(List passedList) {
	if (!isListEmpty(passedList, "Error with List when moveBack was called:", TRUE)) {
		if (!isNull(passedList->nodeBack, "Error with List's back Node:", FALSE)) {
			passedList->nodeCursor = passedList->nodeBack;
			passedList->cursorIndex = (length(passedList) - 1);
		}
	}
}

void movePrev(List passedList) {
	if (!isListEmpty(passedList, "Error with List when movePrev was called:", FALSE)) {
		if (!isNull(passedList->nodeCursor, "Error with List's Cursor Node:", FALSE)) {
			passedList->nodeCursor = passedList->nodeCursor->previousNode;
			passedList->cursorIndex -= 1;
		}
	}
}

void moveNext(List passedList) {
	if (!isListEmpty(passedList, "Error with List when moveNext was called:", FALSE)) {
		if (!isNull(passedList->nodeCursor, "Error with List's Cursor Node:", FALSE)) {
			passedList->nodeCursor = passedList->nodeCursor->nextNode;
			passedList->cursorIndex += 1;
		}
	}
}

void prepend(List passedList, int passedValue) {
	if (!isNull(passedList, "Cannot prepend to a null List!", TRUE)) {
		Node allocatedNode = newNode(passedValue);
		if (passedList->length < 1) {
			insertIntoEmpty(passedList, allocatedNode);
		}
		else if (insertNodeBefore(passedList, passedList->nodeFront, allocatedNode)) {
			passedList->cursorIndex += 1;
			passedList->length += 1;
			passedList->nodeFront = allocatedNode;
		}
	}
}

void append(List passedList, int passedValue) {
	if (!isNull(passedList, "Cannot append to a null List!", TRUE)) {
		Node allocatedNode = newNode(passedValue);
		if (passedList->length < 1) {
			insertIntoEmpty(passedList, allocatedNode);
		}
		else if (insertNodeAfter(passedList, passedList->nodeBack, allocatedNode)) {
			passedList->length += 1;
			passedList->nodeBack = allocatedNode;
		}
	}
}

void insertBefore(List passedList, int passedValue) {
	if (!isNull(passedList, "Cannot insert into a null List!", TRUE)) {
		if (isCursorValid(passedList, "Cannot insert before an undefined cursor!", TRUE)) {
			Node allocatedNode = newNode(passedValue);
			if (insertNodeBefore(passedList, passedList->nodeCursor, allocatedNode)) {
				passedList->cursorIndex += 1;
				passedList->length += 1;
			}
		}
	}
}

void insertAfter(List passedList, int passedValue) {
	if (!isNull(passedList, "Cannot insert into a null List!", TRUE)) {
		if (isCursorValid(passedList, "Cannot insert after an undefined cursor!", TRUE)) {
			Node allocatedNode = newNode(passedValue);
			if (insertNodeAfter(passedList, passedList->nodeCursor, allocatedNode)) {
				passedList->length += 1;
			}
		}
	}
}

void deleteFront(List passedList) {
	if (!isListEmpty(passedList, "Error when deleting the front Node of a List", TRUE)) {
		if (removeNode(passedList, passedList->nodeFront)) {
			passedList->cursorIndex -= 1;
			passedList->length -= 1;
		}
	}
}

void deleteBack(List passedList) {
	if (!isListEmpty(passedList, "Error when deleting the back Node of a List", TRUE)) {
		if (removeNode(passedList, passedList->nodeBack)) {
			passedList->length -= 1;
		}
	}
}

void delete(List passedList) {
	if (!isListEmpty(passedList, "Error when deleting the cursor Node of a List", TRUE)) {
		if (removeNode(passedList, passedList->nodeCursor)) {
			passedList->length -= 1;
			passedList->cursorIndex = UNDEFINED;
		}
	}
}

List concatList(List passedFirstList, List passedSecondList) {
	int firstListValid = !isListEmpty(passedFirstList, "Error with prepended List during concatenation:", FALSE);
	int secondListValid = !isListEmpty(passedFirstList, "Error with appended List during concatenation:", FALSE);
	List allocatedList = newList();
	// If both Lists are valid, concatenate
	if ((firstListValid != FALSE) && (secondListValid != FALSE)) {
		List allocatedList = copyList(passedFirstList);
		Node iteratedNode = passedSecondList->nodeFront;
		while (iteratedNode != NULL) {
			append(allocatedList, iteratedNode->value);
			iteratedNode = iteratedNode->nextNode;
		}
		return allocatedList;
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
	return allocatedList;
}

void printList(FILE* passedOutputFile, List passedList) {
	if(!isNull(passedOutputFile, "Cannot print to a null file reference.", FALSE)) {
		if (!isListEmpty(passedList, "Error with List during print:", FALSE)) {
			Node iteratedNode = passedList->nodeFront;
			while (iteratedNode != NULL) {
				fprintf(passedOutputFile, "%d", iteratedNode->value);
				iteratedNode = iteratedNode->nextNode;
				if (iteratedNode != NULL) {
					fprintf(passedOutputFile, "%s", " ");
				}
			}
		}
	}
}

List copyList(List passedList) {
	List allocatedList = newList();
	if (!isListEmpty(passedList, "Cannot copy a null List.", FALSE)) {
		Node iteratedNode = passedList->nodeFront;
		while (iteratedNode != NULL) {
			append(allocatedList, iteratedNode->value);
			iteratedNode = iteratedNode->nextNode;
		}
	}
	return allocatedList;
}

/* New for pa4 */

/**
 * Inserts the passed value in sorted order into the passed List.
 *
 * This does not SORT the List, merely inserts into sorted order, so if the
 * List is not already sorted, this only has the effect of inserting the value
 * at the first place that it is less than the next value.
 */
void insertInto(List passedList, int passedValue) {
	if (!isNull(passedList, "Cannot insert into a null List!", TRUE)) {
		if (passedList->length == 0) {
			append(passedList, passedValue);
		}
		else {
			int value = -1;
			for (moveFront(passedList); get(passedList) > 0; moveNext(passedList)) {
				value = get(passedList);
				if (passedValue < value) {
					insertBefore(passedList, passedValue);
					return;
				}
			}
		}
	}
}

/* Internal Functions */

/**
 * If the passedList is empty, inserts into it, setting the front and back nodes.
 */
void insertIntoEmpty(List passedList, Node passedNode) {
	passedList->nodeFront = passedNode;
	passedList->nodeBack = passedNode;
	passedList->length += 1;
}

/**
 * Removes a Node from passedList, freeing it.
 */
int removeNode(List passedList, Node passedNode) {
	if (!isNull(passedList, "Cannot remove a Node from a List that is null.", FALSE)) {
		if (!isNull(passedNode, "Cannot remove a Node that is null.", FALSE)) {
			Node nextNode = passedNode->nextNode;
			Node previousNode = passedNode->previousNode;
			if (nextNode != NULL) {
				nextNode->previousNode = passedNode->previousNode;
				if (passedNode == passedList->nodeFront) {
					passedList->nodeFront = nextNode;
				}
			}
			if (previousNode != NULL) {
				previousNode->nextNode = passedNode->nextNode;
				if (passedNode == passedList->nodeBack) {
					passedList->nodeBack = previousNode;
				}
			}
			freeNode(&passedNode);
			return TRUE;
		}
	}
	return FALSE;
}

/**
 * Inserts passedInsertedNode before passedNode.
 */
int insertNodeBefore(List passedList, Node passedNode, Node passedInsertedNode) {
	int passedNodeValid = !isNull(passedNode, "Attempting to insert before null Node", FALSE);
	int insertedNodeValid = !isNull(passedNode, "Attempting to insert null Node", FALSE);
	if ((passedNodeValid) && (insertedNodeValid)) {
		// Set existing links
		Node previous = passedNode->previousNode;
		if (previous != NULL) {
			previous->nextNode = passedInsertedNode;
		}
		passedNode->previousNode = passedInsertedNode;

		// Set new links
		passedInsertedNode->nextNode = passedNode;
		passedInsertedNode->previousNode = previous;

		// Set new front if applicable
		if (passedList->nodeFront == passedNode) {
			passedList->nodeFront = passedInsertedNode;
		}
		return TRUE;
	}
	return FALSE;
}

/**
 * Inserts passedInsertedNode after the passedNode.
 */
int insertNodeAfter(List passedList, Node passedNode, Node passedInsertedNode) {
	int passedNodeValid = !isNull(passedNode, "Attempting to insert after null Node", FALSE);
	int insertedNodeValid = !isNull(passedNode, "Attempting to insert null Node", FALSE);
	if ((passedNodeValid) && (insertedNodeValid)) {
		// Set existing links
		Node next = passedNode->nextNode;
		if (next != NULL) {
			next->previousNode = passedInsertedNode;
		}
		passedNode->nextNode = passedInsertedNode;

		// Set new links
		passedInsertedNode->previousNode = passedNode;
		passedInsertedNode->nextNode = next;

		// Set new back if applicable
		if (passedList->nodeBack == passedNode) {
			passedList->nodeBack = passedInsertedNode;
		}
		return TRUE;
	}
	return FALSE;
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
			exitBadWithMessage("Fatal Error: Null pointer received.");
		}
		return TRUE;
	}
	return FALSE;
}

/**
 * Checks whether the passed List is empty. Does not perform null check on the passed List.
 */
int inline isListEmpty(List passedList, char* passedCharArray, int passedIsTerminal) {
	if (length(passedList) <= 0) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Fatal Error: List is empty.");
		}
		return TRUE;
	}
	return FALSE;
}

/**
 * Validates whether the passedList's cursor is within a valid range (c > 0) && (c < length)
 */
int inline validateIndex(List passedList) {
	if ((passedList->cursorIndex < 0) || (passedList->cursorIndex >= passedList->length)) {
		passedList->cursorIndex = UNDEFINED;
	}
	return (passedList->cursorIndex >= 0);
}

/**
 * Checks whether the passed List's cursor is valid. Does not perform null check on the passed List.
 */
int inline isCursorValid(List passedList, char* passedCharArray, int passedIsTerminal) {
	if (!validateIndex(passedList)) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Fatal Error: Cursor index is invalid.");
		}
		return FALSE;
	}
	if(passedList->nodeCursor == NULL) {
		puts(passedCharArray);
		if (passedIsTerminal == TRUE) {
			exitBadWithMessage("Fatal Error: Cursor is null.");
		}
		return FALSE;
	}
	return TRUE;
}

/**
 * Exits with a code of 1 after printing the passed message.
 */
void inline exitBadWithMessage(const char* passedCharArray) {
	puts(passedCharArray);
	exit(1);
}
