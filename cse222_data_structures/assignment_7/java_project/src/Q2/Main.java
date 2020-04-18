package Q2;

import Graph.Graph;

/**
 * Main class of the Question 2.
 * Undirected Acyclic Graph with no weight with 15 vertices
 * Testing is_connected method
 *
 * @author Yasir
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Question 2:");
        System.out.println("Undirected Acyclic Graph with no weight (v:15)");
        Graph g = new Graph(15);

        g.addEdge(0, 1); g.addEdge(1, 0);
        g.addEdge(1, 2); g.addEdge(2, 1);
        g.addEdge(2, 3); g.addEdge(3, 2);
        g.addEdge(3, 4); g.addEdge(4, 3);
        g.addEdge(4, 5); g.addEdge(5, 4);
        g.addEdge(5, 6); g.addEdge(6, 5);


        g.addEdge(8, 9); g.addEdge(9, 8);
        g.addEdge(9, 10); g.addEdge(10, 9);
        g.addEdge(10, 11); g.addEdge(11, 10);
        g.addEdge(11, 12); g.addEdge(12, 11);
        g.addEdge(12, 13); g.addEdge(13, 12);
        g.addEdge(13, 14); g.addEdge(14, 13);

        System.out.println("Plotting Graph:");
        Graph.plot_graph(g);
        System.out.println();

        System.out.println("Is this graph acyclic: " + Graph.is_acyclic(g));
        System.out.println("Is this graph undirected: " + Graph.is_undirected(g));
        System.out.println();

        System.out.println("Are nodes 0 and 6 connected : " + Graph.is_connected(g, 0, 6));
        System.out.println("Are nodes 1 and 13 connected : " + Graph.is_connected(g, 1, 13));
        System.out.println("Are nodes 7 and 8 connected : " + Graph.is_connected(g, 7, 8));
    }
}
