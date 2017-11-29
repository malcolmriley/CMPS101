/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa5, modified from pa4
 *
 * 11-2017
 *********************************************************************/
#ifndef GRAPH_CONTSTANTS

#include <limits.h>
#define NIL INT_MIN
#define INF INT_MAX

#endif

#ifndef GRAPH_MASRILEY_PA4
#define GRAPH_MASRILEY_PA4

#include <stdio.h>

#include "List.h"

enum VertexColor{WHITE, BLACK, GRAY};

typedef struct GraphObject {
	List* ADJACENCIES;
	enum VertexColor* COLOR;
	int* PARENTS;
	int* DISTANCE;
	int ORDER;
	int SOURCE;
	int SIZE;
} GraphObject;
typedef struct GraphObject* Graph;

/* Constructors-Destructors */
Graph newGraph(int);
void freeGraph(Graph*);

/* Accessors */
int getOrder(Graph);
int getSize(Graph);
int getSource(Graph);
int getParent(Graph, int);
int getDist(Graph, int);
void getPath(List, Graph, int);

/* Manipulatiors */
void makeNull(Graph);
void addEdge(Graph, int, int);
void addArc(Graph, int, int);
void BFS(Graph, int);

/* Miscellaneous */
void printGraph(FILE*, Graph);
void resetVertices(Graph);

#endif
