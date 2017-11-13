/*********************************************************************
 * Malcolm Riley
 *
 * CruzID: masriley
 * Assignment: pa4
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

typedef struct Graph {
	List* ADJACENCIES;
	enum VertexColor* COLOR;
	int* PARENTS;
	int* DISTANCE;
	int ORDER;
	int SOURCE;
	int SIZE;
} Graph;

/* Constructors-Destructors */
Graph newGraph(int n);
void freeGraph(Graph* pG);

/* Accessors */
int getOrder(Graph G);
int getSize(Graph G);
int getSource(Graph G);
int getParent(Graph G, int u);
int getDist(Graph G, int u);
void getPath(List L, Graph G, int u);

/* Manipulatiors */
void makeNull(Graph G);
void addEdge(Graph G, int u, int v);
void addArc(Graph G, int u, int v);
void BFS(Graph G, int s);

/* Miscellaneous */
void printGraph(FILE* out, Graph G);

#endif
