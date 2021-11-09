
import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author best2
 */
public class MST2 {

////////////////// main method////////////////////////////////////////

    public static void main(String[] args) {
        int vertNum = 0;//n
        int EdNum = 0;//m
        Scanner in = new Scanner(System.in);
        System.out.println("\t -------Test and compare Different Minimum Spanning Tree Algorithms-------\n");
        System.out.println("\t1- Kruskal's Algorithm& Prim's Algorithm (based on Priority Queue)\n"
                + "\t2- Prim's Algorithm (based on Min Heap)& Prim's Algorithm(based on Priority Queue)");
        System.out.print(">> Enter your choice (1 or 2): ");
        int Algochoice = in.nextInt();
        System.out.println(">>Test  cases : (where n is the number of vertices and m is the number of edges: ");
        System.out.println(" 1:  n=1,000 , m=10,000");
        System.out.println(" 2:  n=1,000 , m=15,000");
        System.out.println(" 3:  n=1,000 , m=25,000");
        System.out.println(" 4:  n=5,000 , m=15,000");
        System.out.println(" 5:  n=5,000 , m=25,000");
        System.out.println(" 6: n=10,000 , m=15,000");
        System.out.println(" 7:  n=10,000 , m=25,000");
        System.out.println(" 8: n=20,000 , m=200,000");
        System.out.println(" 9: n=20,000 , m=300,000");
        System.out.println("10: n=50,000 , m=1,000,000");
        System.out.print("> Enter a case to test: ");
        int uChoice = in.nextInt();

        if (uChoice == 1) {

            vertNum = 1000;
            EdNum = 10000;
        } else if (uChoice == 2) {
            vertNum = 1000;
            EdNum = 15000;
        } else if (uChoice == 3) {
            vertNum = 1000;
            EdNum = 25000;
        } else if (uChoice == 4) {
            vertNum = 5000;
            EdNum = 15000;
        } else if (uChoice == 5) {
            vertNum = 5000;
            EdNum = 25000;
        } else if (uChoice == 6) {
            vertNum = 10000;
            EdNum = 15000;
        } else if (uChoice == 7) {
            vertNum = 10000;
            EdNum = 25000;
        } else if (uChoice == 8) {
            vertNum = 20000;
            EdNum = 200000;
        } else if (uChoice == 9) {
            vertNum = 20000;
            EdNum = 300000;
        } else if (uChoice == 10) {
            vertNum = 50000;
            EdNum = 100000;
        } else {
            System.out.println("Invalid input!");
            System.out.println("Thank you for comming by!");
        }

       Graph graph = new Graph(vertNum, EdNum);
        System.out.println("Is the graph directed?\n enter (true) or (false) ");
        String isdi = in.next();
        if (isdi.equalsIgnoreCase("true")) {
            System.out.println(" Prim’s and Kruskal’s  MST Algorithms don't work on directed graphs");
            System.out.println(" Would you like to quit(enter quit) ,or continue considering the graph is undirected (enter cont) ");
            String contOrQuit = in.next();
            if (contOrQuit.equalsIgnoreCase("quit")) {
                System.out.println("Thank you for comming by!");
                System.exit(0);
            }

        } else {
            graph.make_graph(graph);
        }
        // to perform Task 1 
        if (Algochoice == 1) {
            graph.kruskalMST();
            graph.primPQ();
        } // to perform Task 2
        else if (Algochoice == 2) {
            graph.primMH();
            graph.primPQ();
        } else {
            System.out.println("Invalid input!");
            System.out.println("Thank you for comming by!");
        }

    }
///////////////////class Edge/////////////////////

    static class Edge {

        int sourceVert;
        int destinationVert;
        int weight;

        // constructor 
        public Edge(int source, int destination, int weight) {
            this.sourceVert = source;
            this.destinationVert = destination;
            this.weight = weight;
        }
//printing the edge info
        public String toString() {
            return sourceVert + "-" + destinationVert + ": " + weight;
        }
    }


    //   the  graph class has all methods needed
    static class Graph {
//data members 
        int verts;
        int edges;
        boolean digraph = false;
        LinkedList<Edge>[] adjList;
//setter if needed to change the graph direction..
        public void setDigraph(boolean isDigraph) {
            this.digraph = isDigraph;
        }

        // Graph constructor 
        Graph(int vertices, int edges) {
            this.verts = vertices;
            this.edges = edges;
            adjList = new LinkedList[vertices];
            //initialize adjacency lists for all the verts
            for (int i = 0; i < vertices; i++) {
                adjList[i] = new LinkedList<>();
            }
        }

        //addEdge is used by make_graph() method to add a new edges to the created graph
        public void addEdge(int srcVert, int destVert, int weight) {
            Edge edge = new Edge(srcVert, destVert, weight);
            adjList[srcVert].addFirst(edge);

        }

        ////////////////////Prim's Algorithm using Priprity Queue/////////////////////
       
          

        /////////////////// Prim's Algorithm using Minheap/////////////
       
        ////////////decreaseKey///////////////////
        
        ////////////printMST///////////////////
  

        ///////////////////////// Kruskal's Algorithm////////////////////////////
      
        ////////////makeSet///////////////////
        
        ////////////find///////////////////
       
        /////////////union///////////////////
      
 //-------------------printGraph()-------------------
        public void printGraph(LinkedList<Edge> edgeList) {
            int cost = 0;
            for (int i = 0; i < edgeList.size(); i++) {
                Edge edge = edgeList.get(i);
                cost += edge.weight;
            }
            System.out.println("Minimum Spanning Tree Cost = " + cost);
        }

        //-------------------make_graph()-------------------
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

        // checks if the edge already exists and the graph is connected.
        public boolean isConnected(int srcVert, int destVert, LinkedList<Edge>[] TotalEdges) {
            for (LinkedList<Edge> i : TotalEdges) {
                for (Edge edge : i) {// for each loop to check the edges
                    if ((edge.sourceVert == srcVert && edge.destinationVert == destVert) || (edge.sourceVert == destVert && edge.destinationVert == srcVert)) {
                        return true;
                    }
                }
            }
            return false;
        }

    }
}




///////////////////class HeapNode/////////////////////
   
 ////////////////////class ResultSet/////////////////////
   
    
///////////// MinHeap class///////////////////
