/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4
 *
 * 11-2017
 *********************************************************************/

#include "Graph.h"
#include <stdlib.h>

/* Internal Function Declarations */
enum VertexColor getColor(Graph, int);
int validateGraphIndex(Graph, int);
int pop(List);
void addArcInternal(Graph, int, int);
int getOrderInternal(Graph);

/* Constructors-Destructors */
Graph newGraph(int passedOrder) {
	// Verify passedOrder is a positive number
	if (passedOrder <= 0) {
		return NULL;
	}
	int order = (passedOrder + 1);

	// Allocate
	Graph newGraph = malloc(sizeof(GraphObject));

	// Set internal fields
	newGraph->ORDER = order;
	newGraph->SOURCE = NIL;
	newGraph->SIZE = 0;

	// Malloc internal arrays
	newGraph->PARENTS = malloc(sizeof(int) * order);
	newGraph->DISTANCE = malloc(sizeof(int) * order);
	newGraph->COLOR = malloc(sizeof(enum VertexColor) * order);
	newGraph->ADJACENCIES = malloc(sizeof(List) * order);

	// Initialize internal arrays
	for (int ii = 0; ii < order; ii += 1) {
		newGraph->ADJACENCIES[ii] = newList();
		newGraph->DISTANCE[ii] = INF;
		newGraph->COLOR[ii] = WHITE;
		newGraph->PARENTS[ii] = NIL;
	}
	return newGraph;
}

void freeGraph(Graph* passedGraph) {
	for (int ii = 0; ii < getOrderInternal(*passedGraph); ii += 1) {
		List iteratedList = (*passedGraph)->ADJACENCIES[ii];
		freeList(&iteratedList);
	}
	free((*passedGraph)->ADJACENCIES);
	free((*passedGraph)->COLOR);
	free((*passedGraph)->DISTANCE);
	free((*passedGraph)->PARENTS);
	free(*passedGraph);
}

/* Accessors */
int getOrder(Graph passedGraph) {
	return (getOrderInternal(passedGraph) - 1);
}

int getSize(Graph passedGraph) {
	return passedGraph->SIZE;
}


int getSource(Graph passedGraph) {
	return passedGraph->SOURCE;
}

int getParent(Graph passedGraph, int passedIndex) {
	if (validateGraphIndex(passedGraph, passedIndex)) {
		return passedGraph->PARENTS[passedIndex];
	}
	return NIL;
}


int getDist(Graph passedGraph, int passedIndex) {
	if (validateGraphIndex(passedGraph, passedIndex)) {
		return passedGraph->DISTANCE[passedIndex];
	}
	return INF;
}

/**
 * appends to passedList the vertices of a shortest path in the passed graph from the currently-set source to the passedIndex, or NIL if no such path exists.
 */
void getPath(List passedList, Graph passedGraph, int passedIndex) {
	if (getColor(passedGraph, passedIndex) == WHITE) {
		clear(passedList);
	}
	else if (validateGraphIndex(passedGraph, getSource(passedGraph)) && validateGraphIndex(passedGraph, passedIndex)) {
		int parent = getParent(passedGraph, passedIndex);
		if (parent != NIL) {
			getPath(passedList, passedGraph, parent);
		}
		append(passedList, passedIndex);
		return;
	}
	append(passedList, NIL);
}

/* Manipulatiors */

void makeNull(Graph passedGraph) {
	for (int ii = 0; ii < getOrderInternal(passedGraph); ii += 1) {
		clear(passedGraph->ADJACENCIES[ii]);
		passedGraph->DISTANCE[ii] = INF;
		passedGraph->COLOR[ii] = WHITE;
		passedGraph->PARENTS[ii] = NIL;
	}
	passedGraph->SIZE = 0;
}

/**
 * "inserts a new (undirected) edge joining passedFirstIndex to passedSecondIndex"
 */
void addEdge(Graph passedGraph, int passedFirstIndex, int passedSecondIndex) {
	addArcInternal(passedGraph, passedFirstIndex, passedSecondIndex);
	addArcInternal(passedGraph, passedSecondIndex, passedFirstIndex);
	passedGraph->SIZE += 1;
}

/**
 * "inserts a new directed edge from passedFirstIndex to passedSecondIndex"
 */
void addArc(Graph passedGraph, int passedFirstIndex, int passedSecondIndex) {
	addArcInternal(passedGraph, passedFirstIndex, passedSecondIndex);
	passedGraph->SIZE += 1;
}

/*
 * "runs the BFS algorithm on the Graph G with source s, setting the color, distance, parent, and source fields of G accordingly."
 */
void BFS(Graph passedGraph, int passedSourceIndex) {
	if (validateGraphIndex(passedGraph, passedSourceIndex) && (getSource(passedGraph) != passedSourceIndex)) {
		resetVertices(passedGraph);

		// Initialize starting index
		passedGraph->SOURCE = passedSourceIndex;
		passedGraph->DISTANCE[passedSourceIndex] = 0;
		passedGraph->COLOR[passedSourceIndex] = GRAY;

		List tempList = newList();
		append(tempList, passedSourceIndex);

		for (int iteratedVertex = passedSourceIndex; length(tempList) > 0; iteratedVertex = pop(tempList)) {
			List adjacencies = passedGraph->ADJACENCIES[iteratedVertex];
			for (moveFront(adjacencies); index(adjacencies) >= 0; moveNext(adjacencies)) {
				int neighbor = get(adjacencies);
				if (passedGraph->COLOR[neighbor] != BLACK) {
					prepend(tempList, neighbor);
				}
				if (passedGraph->COLOR[neighbor] == WHITE) {
					passedGraph->PARENTS[neighbor] = iteratedVertex;
					passedGraph->DISTANCE[neighbor] = (passedGraph->DISTANCE[iteratedVertex]) + 1;
					passedGraph->COLOR[neighbor] = GRAY;
				}
			}
			passedGraph->COLOR[iteratedVertex] = BLACK;
		}
		freeList(&tempList);
	}
}

/* Miscellaneous */
void printGraph(FILE* passedOutputFile, Graph passedGraph) {
	for (int ii = 1; ii < getOrderInternal(passedGraph); ii += 1) {
		List iteratedList = passedGraph->ADJACENCIES[ii];
		fprintf(passedOutputFile, "%d: ", ii);
		printList(passedOutputFile, iteratedList);
		fputs("\n", passedOutputFile);
	}
}

/**
 * Resets the vertices of the graph to the untraversed state (distance = inf, color = white, parent = nil) without removing any edges.
 */
void resetVertices(Graph passedGraph) {
	for (int ii = 0; ii < getOrderInternal(passedGraph); ii += 1) {
		passedGraph->DISTANCE[ii] = INF;
		passedGraph->COLOR[ii] = WHITE;
		passedGraph->PARENTS[ii] = NIL;
	}
}

/* Internal Functions */
int getOrderInternal(Graph passedGraph) {
	return passedGraph->ORDER;
}

void addArcInternal(Graph passedGraph, int passedFirstIndex, int passedSecondIndex) {
	if (validateGraphIndex(passedGraph, passedFirstIndex) && validateGraphIndex(passedGraph, passedSecondIndex)) {
		insertSorted(passedGraph->ADJACENCIES[passedFirstIndex], passedSecondIndex);
	}
}

int validateGraphIndex(Graph passedGraph, int passedIndex) {
	if ((passedIndex > getOrderInternal(passedGraph)) || (passedIndex < 0)) {
		return FALSE;
	}
	return TRUE;
}

enum VertexColor getColor(Graph passedGraph, int passedIndex) {
	if (validateGraphIndex(passedGraph, passedIndex)) {
		return passedGraph->COLOR[passedIndex];
	}
	return WHITE;
}

/**
 * Retrieves the value of the node at the back of the passed list, additionally removing the back node of the passed list.
 */
int pop(List passedList) {
	int value = back(passedList);
	deleteBack(passedList);
	return value;
}
