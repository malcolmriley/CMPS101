/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa5, modified from pa4
 *
 * 11-2017
 *********************************************************************/
#ifndef LIST_CONSTANTS
#define LIST_CONSTANTS

#define TRUE 1
#define FALSE 0
#define UNDEFINED -1

#endif

#ifndef LIST_MASRILEY_PA4
#define LIST_MASRILEY_PA4

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
void freeList(List*);

/* Accessors */
int length(List);
int index(List);
int front(List);
int back(List);
int get(List);
int equals(List, List);

/* Manipulators */
void clear(List);
void moveFront(List);
void moveBack(List);
void movePrev(List);
void moveNext(List);
void prepend(List, int);
void append(List, int);
void insertBefore(List, int);
void insertAfter(List, int);
void deleteFront(List);
void deleteBack(List);
void delete(List);
List concatList(List, List);

/* Miscellaneous */
void printList(FILE*, List);
List copyList(List);

/* New for pa4 */
void insertSorted(List, int);

#endif
