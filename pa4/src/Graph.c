/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4
 *
 * 11-2017
 *********************************************************************/

#include "Graph.h"

/* Internal Function Declarations */
int validateIndex(Graph, int);

/* Constructors-Destructors */
Graph newGraph(int passedOrder) {
	Graph newGraph = malloc(sizeof(Graph));
	// Set internal fields
	newGraph.ORDER = passedOrder;
	newGraph.SOURCE = NIL;
	newGraph.SIZE = 0;

	// Malloc internal arrays
	newGraph.PARENTS = malloc(sizeof(int) * passedOrder);
	newGraph.DISTANCE = malloc(sizeof(int) * passedOrder);
	newGraph.COLOR = malloc(sizeof(enum VertexColor) * passedOrder);
	newGraph.ADJACENCIES = malloc(sizeof(List*) * passedOrder);

	// Initialize internal arrays
	for (int ii = 0; ii < passedOrder; ii += 1) {
		newGraph.ADJACENCIES[ii] = newList();
		newGraph.DISTANCE[ii] = INF;
		newGraph.COLOR[ii] = WHITE;
		newGraph.PARENTS[ii] = NIL;
	}
	return newGraph;
}

void freeGraph(Graph* passedGraph) {
	for (int ii = 0; ii < passedGraph->ORDER; ii += 1) {
		freeList(&(passedGraph->ADJACENCIES[ii]));
	}
	free(passedGraph->ADJACENCIES);
	free(passedGraph->COLOR);
	free(passedGraph->DISTANCE);
	free(passedGraph->PARENTS);
	free(*passedGraph);
}

/* Accessors */
int getOrder(Graph passedGraph) {
	return passedGraph.ORDER;
}

int getSize(Graph passedGraph) {
	return passedGraph.SIZE;
}


int getSource(Graph passedGraph) {
	return passedGraph.SOURCE;
}

int getParent(Graph passedGraph, int passedIndex) {
	if (validateIndex(passedIndex)) {
		return passedGraph.PARENTS[passedIndex];
	}
	return NIL;
}


int getDist(Graph passedGraph, int passedIndex) {
	if (validateIndex(passedIndex)) {
		return passedGraph.DISTANCE[passedIndex];
	}
	return INF;
}

/**
 * appends to passedList the vertices of a shortest path in the passed graph from the currently-set source to the passedIndex, or NIL if no such path exists.
 */
void getPath(List passedList, Graph passedGraph, int passedIndex) {
	// TODO:
}

/* Manipulatiors */

void makeNull(Graph passedGraph) {
	for (int ii = 0; ii < getSize(passedGraph); ii += 1) {
		clear(passedGraph.ADJACENCIES[ii]);
		passedGraph.DISTANCE[ii] = INF;
		passedGraph.COLOR[ii] = WHITE;
		passedGraph.PARENTS[ii] = NIL;
	}
}

/**
 * inserts a new (undirected) edge joining passedFirstIndex to passedSecondIndex
 */
void addEdge(Graph passedGraph, int passedFirstIndex, int passedSecondIndex) {
	addArc(passedGraph, passedFirstIndex, passedSecondIndex);
	addArc(passedGraph, passedSecondIndex, passedFirstIndex);
}

/**
 * inserts a new directed edge from passedFirstIndex to passedSecondIndex
 */
void addArc(Graph passedGraph, int passedFirstIndex, int passedSecondIndex) {
	if (validateIndex(passedGraph, passedFirstIndex) && validateIndex(passedGraph, passedSecondIndex)) {
		insertSorted(passedGraph.ADJACENCIES[passedFirstIndex], passedSecondIndex);
	}
	else {
		// TODO: Error?
	}
}

/* Miscellaneous */
void printGraph(FILE* passedOuptutFile, Graph passedGraph) {
	// TODO!
}

/* Internal Functions */
int validateIndex(Graph passedGraph, int passedIndex) {
	if ((passedIndex > getSize(passedGraph)) || (passedIndex < 0)) {
		return FALSE;
	}
	return TRUE;
}
