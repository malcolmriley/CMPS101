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

/* Manipulatiors */

/* Miscellaneous */

/* Internal Functions */
int validateIndex(Graph passedGraph, int passedIndex) {
	if ((passedIndex > getSize(passedGraph)) || (passedIndex < 0)) {
		return FALSE;
	}
	return TRUE;
}
