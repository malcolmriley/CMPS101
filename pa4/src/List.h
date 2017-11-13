/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4
 *
 * 11-2017
 *********************************************************************/
#ifndef CONSTANTS
#define CONSTANTS

#define TRUE 1
#define FALSE 0
#define UNDEFINED -1

#endif

#ifndef LIST_H
#define LIST_H

/* Node Typedef */
typedef struct NodeObject {
	int value;
	struct NodeObject* nextNode;
	struct NodeObject* previousNode;
} NodeObject;
typedef NodeObject* Node;

/* List Typedef */
typedef struct ListObject {
	int length;
	int cursorIndex;
	Node nodeFront;
	Node nodeBack;
	Node nodeCursor;
} ListObject;
typedef struct ListObject* List;

/* Constructor/Destructor */
List newList(void);
void freeList(List* passedlist);

/* Accessors */
int length(List passedList);
int index(List passedList);
int front(List passedList);
int back(List passedList);
int get(List passedList);
int equals(List passedFirstList, List passedSecondList);

/* Manipulators */
void clear(List passedList);
void moveFront(List passedList);
void moveBack(List passedList);
void movePrev(List passedList);
void moveNext(List passedList);
void prepend(List passedList, int passedValue);
void append(List passedList, int passedValue);
void insertBefore(List passedList, int passedValue);
void insertAfter(List passedList, int passedValue);
void deleteFront(List passedList);
void deleteBack(List passedList);
void delete(List passedList);
List concatList(List passedFirstList, List passedSecondList);

/* Miscellaneous */
void printList(FILE* passedOutputFile, List passedList);
List copyList(List passedList);

#endif
