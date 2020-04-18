package Q3;

import Graph.*;

/**
 * Main class of the Question 3.
 * Undirected Cyclic Graph with no weight with 10
 * Testing DFS and BFS method
 *
 * @author Yasir
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Question 3:");
        System.out.println("Undirected Cyclic Graph with no weight (v:10)");
        Graph g = new Graph(10);

        g.addEdge(0,1); g.addEdge(1,0);
        g.addEdge(0,9); g.addEdge(9,0);

        g.addEdge(1,4); g.addEdge(4,1);
        g.addEdge(1,9); g.addEdge(9,1);
        g.addEdge(1,2); g.addEdge(2,1);
        g.addEdge(1,8); g.addEdge(8,1);

        g.addEdge(2,6); g.addEdge(6,2);

        g.addEdge(3,4); g.addEdge(4,3);

        g.addEdge(5,6); g.addEdge(6,5);

        g.addEdge(7,8); g.addEdge(8,7);
        g.addEdge(7,9); g.addEdge(9,7);

        g.addEdge(8,9); g.addEdge(9,8);

        System.out.println("Plotting Graph:");
        Graph.plot_graph(g);
        System.out.println();

        System.out.println("Is this graph acyclic: " + Graph.is_acyclic(g));
        System.out.println("Is this graph undirected: " + Graph.is_undirected(g));
        System.out.println();

        g.DFS(0);
        g.BFS(0);

        /* GRAPH IN QUESTION 4 */
        /*
        Graph g = new Graph(7);
        g.addEdge(0,0);g.addEdge(0,1);g.addEdge(0,5);
        g.addEdge(1,0);g.addEdge(1,2);
        g.addEdge(2,1);g.addEdge(2,2);g.addEdge(2,4);g.addEdge(2,5);g.addEdge(2,6);
        g.addEdge(3,3);g.addEdge(3,4);g.addEdge(3,5);
        g.addEdge(4,2);g.addEdge(4,3);g.addEdge(4,4);g.addEdge(4,5);
        g.addEdge(5,0);g.addEdge(5,2);g.addEdge(5,3);g.addEdge(5,4);g.addEdge(5,5);
        g.addEdge(6,2);g.addEdge(6,6);
        g.DFS(0);
        g.BFS(0);
        */
    }
}
