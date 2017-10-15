/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa2
 *
 * 10-2017
 *********************************************************************/

#include "List.h"

/* Node Type Definition */
typedef struct NodeObj {
	int value;
	struct NodeObj* nextNode;
	struct NodeObj* previousNode;
} NodeObj;
typedef NodeObj* Node;

Node newNode(int passedValue) {

}
void freeNode(Node* passedNode) {

}

/* List Globals */

/* List Function Definitions */

/* Internal Functions */
void checknull(void* passedPointer, char* passedCharArray) {
	if (passedPointer == NULL) {
		printf("Null pointer exception: List is null.\n %s", passedCharArray);
		exit(1);
	}
}
