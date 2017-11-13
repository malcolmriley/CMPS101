/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4
 *
 * 11-2017
 *********************************************************************/

#include "Graph.h"

/* Constructors-Destructors */
Graph newGraph(int passedOrder) {
	Graph newGraph = malloc(sizeof(Graph));
	newGraph.ORDER = passedOrder;
	newGraph.SOURCE = -1;
	newGraph.PARENTS = malloc(sizeof(int) * passedOrder);
	newGraph.DISTANCE = malloc(sizeof(int) * passedOrder);
	newGraph.COLOR = malloc(sizeof(enum VertexColor) * passedOrder);
	newGraph.ADJACENCIES = malloc(sizeof(*List) * passedOrder);
	for (int ii = 0; ii < passedOrder; ii += 1) {
		newGraph.ADJACENCIES[ii] = newList();
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

/* Manipulatiors */

/* Miscellaneous */
