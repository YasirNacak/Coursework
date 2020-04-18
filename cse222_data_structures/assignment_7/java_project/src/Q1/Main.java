package Q1;

import Graph.Graph;

import java.util.Random;
import java.util.Vector;

/**
 * Main class of the Question 1.
 * Directed Acyclic Graph with random weight with 10 vertices and 20 edges
 * Testing shortest_path methods
 *
 * @author Yasir
 */
public class Main {
    private static int gen(Random r){
        return r.nextInt(10) + 1;
    }
    public static void main(String[] args) {
        System.out.println("Question 1:");
        System.out.println("Directed Acyclic Graph with random weight (v:10, e:20)");
        Graph g = new Graph(10);

        Random r = new Random();

        g.addEdge(0, 1, gen(r)); g.addEdge(0, 2, gen(r)); g.addEdge(0, 3, gen(r)); g.addEdge(0, 4, gen(r));
        g.addEdge(0, 5, gen(r)); g.addEdge(0, 6, gen(r)); g.addEdge(0, 7, gen(r)); g.addEdge(0, 8, gen(r));
        g.addEdge(0, 9, gen(r));
        g.addEdge(1, 2, gen(r)); g.addEdge(1, 4, gen(r));
        g.addEdge(2, 6, gen(r));
        g.addEdge(3, 7, gen(r));
        g.addEdge(5, 3, gen(r));
        g.addEdge(6, 8, gen(r));
        g.addEdge(7, 4, gen(r));
        g.addEdge(8, 5, gen(r)); g.addEdge(8, 9, gen(r));
        g.addEdge(9, 3, gen(r)); g.addEdge(9, 5, gen(r));

        System.out.println("Plotting Graph:");
        Graph.plot_graph(g);
        System.out.println();

        System.out.println("Is this graph acyclic: " + Graph.is_acyclic(g));
        System.out.println("Is this graph undirected: " + Graph.is_undirected(g));
        System.out.println();

        Vector<Integer> path;

        System.out.println("Shortest path from node 0 to 7");
        path = Graph.shortest_path(g, 0, 7);
        for(Integer i : path)
            System.out.print(i + " ");
        System.out.println();

        System.out.println("Shortest path from node 6 to 4");
        path = Graph.shortest_path(g, 6, 4);
        for(Integer i : path)
            System.out.print(i + " ");
        System.out.println();

        System.out.println("Shortest path from node 1 to 9");
        path = Graph.shortest_path(g, 1, 9);
        for(Integer i : path)
            System.out.print(i + " ");
        System.out.println();
    }
}
