package algoProject;


/*
CPCS 324 project pt1 
section:GAR
team members:
Shather Mohammed Alshubbak
Reem Abdulrhman Alghamdi 
Jumana abdulrahman almadhoun
15/11/2012
*/


import java.util.*;
import javafx.util.Pair;

//-------------------------the graph class has all methods needed---------------

/**
 *Graph class contains all the methods needed 
 * @author shather
 */
class Graph {
//data members 

    /**
     *number of vertices
     */

    private int verts;

    /**
     *number of edges
     */
    private int edges;

    /**
     * The state of the graph in terms if it's directed or not
     * Initially we assume its not directed ,otherwise the MST algorithms wont work.
     */
    private boolean digraph = false;

    /**
     *link list of adjacent edges 
     */
    LinkedList<Edge>[] adjList;


    /**
     *if needed to change the graph direction
     * @param isDigraph 
     */
    //setter if needed to change the graph direction..
    public void setDigraph(boolean isDigraph) {
        this.digraph = isDigraph;
    }

    // Graph constructor 

    /**
     *Graph constructor 
     * @param vertices number of vertices
     * @param edges   number of edges
     */
    Graph(int vertices, int edges) {
        this.verts = vertices;
        this.edges = edges;
        adjList = new LinkedList[vertices];
        //initialize adjacency lists for all the verts
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

//--------used by make_graph() method to add a new edges to the created graph---

    /**
     * addEdge method used by make_graph() to add a new edges to the created graph
     * @param srcVert source vertex 
     * @param destVert  destination vertex
     * @param weight     the edge weight 
     */
    private void addEdge(int srcVert, int destVert, int weight) {
        Edge edge = new Edge(srcVert, destVert, weight);
        adjList[srcVert].addFirst(edge);
         
         edge = new Edge(destVert, srcVert, weight);
        adjList[destVert].addFirst(edge);

    }

//-----------------------Prim's Algorithm using Prioprity Queue------------------

    /**
     *Prim's Algorithm using Prioprity Queue
     */
    public void prim_PQ() {
        //start timing
        double startTime = System.currentTimeMillis();
        boolean[] MST = new boolean[verts];
        ResultSet[] results = new ResultSet[verts];
        int[] key = new int[verts];

        // 1- initialize all keys to infinity
        // 2- initialize ResultSet for all the vertices
        for (int i = 0; i < verts; i++) {
            key[i] = Integer.MAX_VALUE;
            results[i] = new ResultSet();
        }

        // 3- initialize priority queue to implement prim's algorithm
        // 4-override the comparator to do the sorting based keys
        PriorityQueue<Pair<Integer, Integer>> PQ = new PriorityQueue<>(verts,
                (Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) -> {
                    //sort using key values
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1 - key2;
                });

        //create the pair for the first index, 0 key 0 index
        key[0] = 0;
        Pair<Integer, Integer> p0 = new Pair<>(key[0], 0);
        //add it to priority queue
        PQ.offer(p0);
        results[0] = new ResultSet();
        results[0].parent = -1;

        //while priority queue not empty
        while (!PQ.isEmpty()) {
            //extract the minimum value
            Pair<Integer, Integer> pair_extract = PQ.poll();

            //extract vertex
            int exVert = pair_extract.getValue();
            MST[exVert] = true;

            //iterate through all adjacent verices
            //update keys
            LinkedList<Edge> list = adjList[exVert];
            for (int i = 0; i < list.size(); i++) {
                Edge edg = list.get(i);
                //if edge destination is not currently present in mst
                if (MST[edg.getDestinationVert()] == false) {
                    int destination = edg.getDestinationVert();
                    int newKey = edg.getWeight();
                    //check if updated key < current key
                    //if true-> update
                    if (key[destination] > newKey) {
                        //add it to priority queue
                        Pair<Integer, Integer> pq_updated = new Pair<>(newKey, destination);
                        PQ.offer(pq_updated);

                        //update the ResultSet for destination vertex
                        results[destination].parent = exVert;
                        results[destination].weight = newKey;
                        //update key array
                        key[destination] = newKey;

                    }

                }
            }
        }
        //calculate finish time for the algorithm
        double finTime = System.currentTimeMillis();

        //print total time taken by the algorithm
        System.out.println("Total runtime of Prim's Algorithm (Usin Priority Queue): " + (finTime - startTime) + " ms.");
        //print mst
        printMST(results);

    }
//---------------------------Prim's Algorithm using Minheap---------------------

    /**
     *Prim's Algorithm using Minheap
     */
    public void primMinHeap() {
        //start time
        double startTime = System.currentTimeMillis();
        boolean[] Heap = new boolean[verts];
        ResultSet[] resultSet = new ResultSet[verts];
        //keys[] used to check if min heap update is required
        int[] key = new int[verts];
        //create heapNode for all the vertices
       heapNode[] heapNodes = new heapNode[verts];
        for (int i = 0; i < verts; i++) {
            heapNodes[i] = new heapNode();
            heapNodes[i].node = i;
            heapNodes[i].key = Integer.MAX_VALUE;
            resultSet[i] = new ResultSet();
            resultSet[i].parent = -1;
            Heap[i] = true;
            key[i] = Integer.MAX_VALUE;
        }

        //decrease the key for the first index
        heapNodes[0].key = 0;

        //add all the vertices to the MinHeap
        MinHeap minHeap = new MinHeap(verts);
        //add all the vertices to priority queue
        for (int i = 0; i < verts; i++) {
            minHeap.insert(heapNodes[i]);
        }

        do {
            //extract the min
            heapNode extractedNode = minHeap.extractMin();

            //extracted vertex
            int extractedVertex = extractedNode.node;
            Heap[extractedVertex] = false;

            //iterate through all the adjacent vertices
            LinkedList<Edge> list = adjList[extractedVertex];
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge destination is present in heap
                if (Heap[edge.getDestinationVert()]) {
                    int destination = edge.getDestinationVert();
                    int newKey = edge.getWeight();
                    //check if updated key < existing key, if yes, update if
                    if (key[destination] > newKey) {
                        toDecreaseKey(minHeap, newKey, destination);
                        //update the parent node for destination
                        resultSet[destination].parent = extractedVertex;
                        resultSet[destination].weight = newKey;
                        key[destination] = newKey;
                    }
                }
            }
        } while (!minHeap.isEmpty());
        //finish time of the algorithm
        double ftime = System.currentTimeMillis();
        //print the total time consumed by the algorithm
        System.out.println("Total runtime of Prim's Algorithm (Usin Min Heap): " + (ftime - startTime) + " ms.");
        //print mst
        printMST(resultSet);
    }

//------------------------------------------------------------------------------

    /**
     * toDecreaseKey method used by prim's min-heap method 
     * @param mH the min-heap object 
     * @param newKey the new node key 
     * @param vertex vertex of the node
     */
    private void toDecreaseKey(MinHeap mH, int newKey, int vertex) {

        //get the index which key's needs a decrease;
        int index = mH.decreaseKey[vertex];

        //get the node and update its value
        heapNode node = mH.minHeap[index];
        node.key = newKey;
        mH.bubbleUp(index);
    }

//---------------------------to print MST---------------------------------------

    /**
     * print MST
     * @param resultSet the result set of vertices and edges of the minimum spanning tree
     */
    private void printMST(ResultSet[] resultSet) {
        int totalWeight = 0, i = 0;
   
            System.out.println("Minimum Spanning Tree: ");

        while (i < verts) {
            totalWeight += resultSet[i++].weight;
//       
//                System.out.println("Edge: " + i + " - " + resultSet[i].parent
//                        + " weight: " + resultSet[i].weight);
 }
        System.out.println("Minimum Spanning Tree cost: " + totalWeight);
    }

//-------------------------- Kruskal's Algorithm--------------------------------

    /**
     *Kruskal's Algorithm 
     */
    public void kruskalMST() {
        //start time
        double startTime = System.currentTimeMillis();
        // to used in tracing
        String treeV = "";
        // change data type from ArrayList to LinkedList
        LinkedList<Edge>[] Edges = adjList.clone();
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges, Comparator.comparingInt(o -> o.getWeight()));

        //priority queue holds  all the edges
        //sort the edges by its weights
        for (LinkedList<Edge> allEdge : Edges) {
            for (int j = 0; j < allEdge.size(); j++) {
                pq.add(allEdge.get(j));
            }
        }
        /* System.out.println("\nSorted list of Edges:");
            for (Edge edge : pq) {
            System.out.println(edge.toString());
            }*/
        
        //create a parent []
        int[] parent = new int[verts];

        //makeset
        makeSet(parent);

        LinkedList<Edge> mst = new LinkedList<>();

        //process vertices - 1 edges
        int index = 0;
        while (index < verts - 1 && !pq.isEmpty()) {
            Edge edge = pq.remove();
            //check if adding this edge creates a cycle
            int x_set = findParent(parent, edge.getSourceVert());
            int y_set = findParent(parent, edge.getDestinationVert());

            if (x_set == y_set) {
                //ignore, will create cycle
            } else {
                //add it to our final result
                mst.add(edge);
                treeV += edge.toString() + "\n";
              // System.out.println("\n Tree Vertex:");
              //  System.out.println(treeV);
                index++;
                union(parent, x_set, y_set);
            }
//             System.out.println("list of Edges:");
//             for (Edge e : pq) {
//             System.out.println(e.toString());
//             }

        }

        //finish time of the algorithm
        double ftime = System.currentTimeMillis();
        //print the total time consumed by the algorithm
        System.out.println("Total runtime of Kruskal's Algorithm: " + (ftime - startTime) + " ms.");
        //print MST
        //System.out.println("Minimum Spanning Tree: ");
        printGraph(mst);
    }

    /**
     * makeSet method used in Kruskal's 
     * @param parent
     */
    private void makeSet(int[] parent) {
        //Make set: creating a new element with a parent pointer to itself.
        for (int i = 0; i < verts; i++) {
            parent[i] = i;
        }
    }

    /**
     *union method used by Kruskal's
     * @param parent 
     * @param x first node
     * @param y  second node
     */
    private void union(int[] parent, int x, int y) {
        int setXparent = findParent(parent, x);
        int setYparent = findParent(parent, y);
        //make x as parent of y
        parent[setYparent] = setXparent;
    }

    /**
     *findParent method used by union method 
     * @param parent parent list
     * @param vertex the vertex
     * @return
     */
    private int findParent(int[] parent, int vertex) {
        //chain of parent pointers from x upwards through the tree
        // until an element is reached whose parent is itself (ROOT)
        if (parent[vertex] == vertex) {
            return vertex;
        }
        return findParent(parent, parent[vertex]);
    }

//----------------------------To print Graph------------------------------------

    /**
     *print Graph method 
     * @param edgeList list of the edges in the graph 
     */
    private void printGraph(LinkedList<Edge> edgeList) {
        int cost = 0;
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            cost += edge.getWeight();
        }
        System.out.println("Minimum Spanning Tree Cost = " + cost);
    }

//----------------------------To make graph-------------------------------------

    /**
     *make graph method , will generate and create a random graph 
     * of random verts and edges
     * @param graph graph object 
     */
    public void make_graph(Graph graph) {
        // object of Random class
        Random randm = new Random();
        // ensure that all verts are connected
        for (int i = 0; i < verts - 1; i++) {
            int weight = randm.nextInt(20) + 1;//generate random edge weights between 0-20
            addEdge(i, i + 1, weight); //connect verts
            if (!graph.digraph) {
                addEdge(i + 1, i, weight);
            }
        }

        // generate edges bewteen verts with the remaining edges
        int remEdges = edges - (verts - 1);

        for (int i = 0; i < remEdges; i++) {
            int srcVert = randm.nextInt(graph.verts);
            int destVert = randm.nextInt(graph.verts);
            if (destVert == srcVert || isConnected(srcVert, destVert, graph.adjList)) { // to avoid self loops and duplicate edges
                i--;
                continue;
            }
            // generate random weights in range 0 to 20
            int weight = randm.nextInt(20) + 1;
            // add edge to the graph
            addEdge(srcVert, destVert, weight);

        }

    }
//----------checks if the edge already exists and the graph is connected.-------

    /**
     * isConnected method used by make_graph method to ensure that the tree is connected 
     * @param srcVert  source vertex
     * @param destVert  destination vertex
     * @param TotalEdges  link list of all the edges in the graph
     * @return
     */
    private boolean isConnected(int srcVert, int destVert, LinkedList<Edge>[] TotalEdges) {
        for (LinkedList<Edge> i : TotalEdges) {
            if (i.stream().anyMatch((edge) -> {
                return (edge.getSourceVert() == srcVert && edge.getDestinationVert() == destVert)
                        || (edge.getSourceVert() == destVert && edge.getDestinationVert() == srcVert);
            })) {
                return true;
            } // for each loop to check the edges
        }
        return false;
    }
}
//-------------------------------class ResultSet--------------------------------

/**
 *
 * class ResultSet
 */
class ResultSet {

    
    int parent;

  
    int weight;
}
