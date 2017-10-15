/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa2
 *
 * 10-2017
 *********************************************************************/
#ifndef LIST_H
#define LIST_H

#include <stdio.h>

typedef struct ListObj* List;

/* Constructors/Destructors */
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
void prepend(List passedList, int passedData);
void append(List passedList, int passedData);
void insertBefore(List passedList, int passedData);
void insertAfter(List passedList, int passedData);
void deleteFront(List passedList);
void deleteBack(List passedList);
void delete(List passedList);

/* Miscellaneous */
void printList(FILE* passedOutputFile, List passedList);
List copyList(List passedList);

#endif;
