package Graph;

import java.util.*;

/**
 * Represents a Graph.
 * Contains methods for adding, removing and searching.
 * As well as the static methods given in the homework
 * @author Yasir
 */
public class Graph {
    /**Vertices of the graph*/
    private Set<Vertex> vertices;
    /**Edges of the graph*/
    private Set<Edge> edges;
    /**Adjacency Hashmap of the graph*/
    private Map<Vertex, Set<Edge>> adjList;

    /**
     * No parameter constructor, sets the vertex count as 0 and calls the proper constructor
     */
    public Graph() {
        this(0);
    }

    /**
     * Constructor with vertex count
     * Initializes the set of vertices and gives them the values from 0 to (vertexCount-1)
     * @param vertexCount number of the vertices to be set
     */
    public Graph(int vertexCount){
        vertices = new HashSet<>();
        edges = new HashSet<>();
        adjList = new HashMap<>();
        for(int i=0;i<vertexCount;i++){
            addVertex(i);
        }
    }

    /**
     * Getter for the vertices field
     * @return vertices field as an unmodifiable set
     */
    public Set<Vertex> getVertices() { return Collections.unmodifiableSet(vertices); }

    /**
     * Getter for the edges field
     * @return edges field as an unmodifiable set
     */
    public Set<Edge> getEdges() { return Collections.unmodifiableSet(edges); }

    /**
     * Getter for the adjacency list
     * @return adjacency list field as an unmodifiable map
     */
    public Map<Vertex, Set<Edge>> getAdjList() { return Collections.unmodifiableMap(adjList); }

    /**
     * Adds a vertex to the graph
     * @param label name of the vertex
     * @return return value of the set.add()
     */
    public boolean addVertex(int label) {
        return vertices.add(new Vertex(label));
    }

    /**
     * Adds a vertex to the graph
     * @param v vertex to be added
     * @return return value of the set.add()
     */
    public boolean addVertex(Vertex v) {
        return vertices.add(v);
    }

    /**
     * Adds an edge to the graph
     * @param e edge to be added
     * @return return value of the set.add()
     */
    private boolean addEdge(Edge e) {
        if (!edges.add(e)) return false;

        adjList.putIfAbsent(e.v1, new HashSet<>());
        adjList.putIfAbsent(e.v2, new HashSet<>());

        adjList.get(e.v1).add(e);
        adjList.get(e.v2).add(e);

        return true;
    }

    /**
     * Adds an edge to the graph with default weight
     * @param v1 starting vertex
     * @param v2 ending vertex
     * @return return value of the Set.add()
     */
    public boolean addEdge(int v1, int v2) {
        return addEdge(new Edge(new Vertex(v1),
                new Vertex(v2)));
    }

    /**
     * Adds an edge to the graph with specified weight
     * @param v1 starting vertex
     * @param v2 ending vertex
     * @param w weight of the edge
     * @return return value of the Set.add()
     */
    public boolean addEdge(int v1, int v2, int w) {
        return addEdge(new Edge(new Vertex(v1),
                new Vertex(v2), w));
    }

    /**
     * Finds out if the graph has an edge that starts from v1 and ends at v2
     * @param v1 starting vertex
     * @param v2 ending vertex
     * @return true if such an edge from v1 - v2 exists, false otherwise
     */
    private boolean isAdjacent(int v1, int v2){
        if(v1 == v2) return true;
        for(Edge e : edges) {
            if(e.getV1().equals(new Vertex(v1)) &&
                    e.getV2().equals(new Vertex(v2)))
                return true;
        }
        return false;
    }

    /**
     * Gets all the vertices that be reached from given vertex
     * @param v starting vertex
     * @return set of vertices that are 1 edge away from v and has a connection from v
     */
    private Set<Vertex> getAdjVertices(Vertex v) {
        Set<Vertex> result = new HashSet<>();
        for(Vertex viter : vertices){
            if(viter.getLabel() != v.getLabel() &&
                    isAdjacent(v.getLabel(), viter.getLabel())){
                result.add(viter);
            }
        }
        return result;
    }

    /**
     * The method that looks if a such path between v1 to v2 exists
     * @param g graph to be searched
     * @param v1 starting node
     * @param v2 ending node
     * @return true if a path exists, false other wise
     * @throws NoSuchElementException if one of the parameters are nonexistent in the graph, throws this
     */
    public static boolean is_connected(Graph g, int v1, int v2) throws NoSuchElementException{
        Vertex start = null, end = null;
        for(Vertex v : g.getVertices()) {
            if(v.getLabel() == v1) start = v;
            if(v.getLabel() == v2) end = v;
        }
        if(start == null || end == null)
            throw new NoSuchElementException();

        if(start.equals(end)) return true;

        int maxLabel = Integer.MIN_VALUE;
        for(Vertex v: g.getVertices())
            if(v.getLabel() > maxLabel)
                maxLabel = v.getLabel();

        boolean visited[] = new boolean[maxLabel + 1];

        visited[start.getLabel()] = true;
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.add(start);

        while(queue.size() != 0){
            start = queue.poll();
            for(Vertex v : g.getAdjVertices(start)){
                if(v.equals(end))
                    return true;
                if(!visited[v.getLabel()]){
                    visited[v.getLabel()] = true;
                    queue.add(v);
                }
            }
        }
        return false;
    }

    /**
     * Looks for pairs in edges in the graph and finds out
     * if the graph is undirected or not
     * @param g graph to be searched
     * @return true if the graph undirected, false otherwise
     */
    public static boolean is_undirected(Graph g){
        for(Edge ei : g.getEdges()){
            boolean isReverseDir = false;
            for (Edge ej : g.getEdges()){
                if(ei.v1.getLabel() == ej.v2.getLabel() &&
                        ei.v2.getLabel() == ej.v1.getLabel() &&
                        ei.weight == ej.weight){
                    isReverseDir = true;
                }
            }
            if(!isReverseDir)
                return false;
        }
        return true;
    }

    /**
     * Finds if the graph is acyclic, first finds if the graph is
     * directed or undirected and calls the required method based on that
     * @param g graph to be searched
     * @return true if the graph is acyclic, false otherwise
     */
    public static boolean is_acyclic(Graph g) {
        if(Graph.is_undirected(g)){
            return !Graph.isCyclicUndirected(g);
        } else {
            return !Graph.isCyclicDirected(g);
        }
    }

    /**
     * Setup method to find cycles in a directed graph
     * @param g graph to be searched
     * @return true if the graph is cyclic, false otherwise
     */
    private static boolean isCyclicDirected(Graph g) {
        Set<Vertex> whiteSet = new HashSet<Vertex>();
        Set<Vertex> graySet = new HashSet<Vertex>();
        Set<Vertex> blackSet = new HashSet<Vertex>();

        for(Vertex v : g.getVertices()){
            if(g.isCyclicDirectedRec(v, whiteSet, graySet, blackSet))
                return true;
        }
        return false;
    }

    /**
     * Recursive method that looks for a cycle in a directed graph
     * @param current current vertex
     * @param whiteSet never looked items
     * @param graySet items that are currently being looked at
     * @param blackSet already looked items
     * @return true if the graph has cycles, false otherwise
     */
    private boolean isCyclicDirectedRec(Vertex current, Set<Vertex> whiteSet, Set<Vertex> graySet, Set<Vertex> blackSet) {
        moveVertex(current, whiteSet, graySet);
        for(Vertex adj : getAdjVertices(current)){
            if(blackSet.contains(adj))
                continue;
            if(graySet.contains(adj))
                return true;
            if(isCyclicDirectedRec(adj, whiteSet, graySet, blackSet))
                return true;
        }
        moveVertex(current, graySet, blackSet);
        return false;
    }

    /**
     * Utility method that moves one vertex from a set to another
     * @param vertex vertex to be moved
     * @param src source set
     * @param dst destination set
     */
    private void moveVertex(Vertex vertex, Set<Vertex> src, Set<Vertex> dst){
        src.remove(vertex);
        dst.add(vertex);
    }

    /**
     * Setup method for finding cycles in an undirected graph
     * @param g graph to be searched
     * @return true if the graph has cycles, false otherwise
     */
    private static boolean isCyclicUndirected(Graph g){
        Boolean visited[] = new Boolean[g.getVertices().size()];
        for (int i = 0; i < g.vertices.size(); i++)
            visited[i] = false;
        for (Vertex v : g.getVertices())
            if (!visited[v.getLabel()])
                if (g.isCyclicUndirectedRec(v, visited, -1))
                    return true;

        return false;
    }

    /**
     * Recursive method that looks for a cycle in an undirected graph
     * @param v current vertex
     * @param visited list of visited vertices
     * @param parent parent of the current vertex
     * @return true if cyclic, false otherwise
     */
    private boolean isCyclicUndirectedRec(Vertex v, Boolean visited[], int parent) {
        visited[v.getLabel()] = true;

        for(Vertex vi : getAdjVertices(v)){
            if (!visited[vi.getLabel()]) {
                if (isCyclicUndirectedRec(vi, visited, v.getLabel()))
                    return true;
            }
            else if (vi.getLabel() != parent)
                return true;
        }
        return false;
    }

    /**
     * Depth First Search algorithm that prints out the
     * spanning tree of the search
     * @param start label of the node that starts the DFS
     */
    public void DFS(int start) {
        Vertex vs = new Vertex(start);

        int maxLabel = Integer.MIN_VALUE;
        for(Vertex v: getVertices())
            if(v.getLabel() > maxLabel)
                maxLabel = v.getLabel();
        boolean visited[] = new boolean[maxLabel + 1];

        Stack<Vertex> stack = new Stack<>();
        stack.push(vs);
        SpanningTree spn = new SpanningTree(vs.getLabel());
        while(!stack.empty()){
            vs = stack.pop();
            if(!visited[vs.getLabel()]){
                visited[vs.getLabel()] = true;
            }
            for(Vertex v : getAdjVertices(vs))
                if(!visited[v.getLabel()]) {
                    spn.addChildren(vs.getLabel(), v.getLabel());
                    stack.push(v);
                }
        }

        System.out.println("Spanning Tree of DFS:");
        spn.print();
    }

    /**
     * Breadth First Search algorithm that prints out the
     * spanning tree of the search
     * @param start label of the node that starts the BFS
     */
    public void BFS(int start){
        Vertex vs = new Vertex(start);

        int maxLabel = Integer.MIN_VALUE;
        for(Vertex v: getVertices())
            if(v.getLabel() > maxLabel)
                maxLabel = v.getLabel();
        boolean visited[] = new boolean[maxLabel + 1];

        LinkedList<Vertex> queue = new LinkedList<>();

        visited[vs.getLabel()]=true;
        queue.add(vs);
        SpanningTree spn = new SpanningTree(vs.getLabel());
        while (queue.size() != 0) {
            vs = queue.poll();

            for(Vertex v : getAdjVertices(vs)){
                if(!visited[v.getLabel()]) {
                    spn.addChildren(vs.getLabel(), v.getLabel());
                    visited[v.getLabel()] = true;
                    queue.add(v);
                }
            }
        }

        System.out.println("Spanning Tree of BFS:");
        spn.print();
    }

    /**
     * Finds the shortest path from given two nodes using the Djikstra algorithm
     * @param g Graph that the search is being done
     * @param v1 starting vertex
     * @param v2 ending vertex
     * @return list of vertices in the given graph that describes the path from v1 to v2
     */
    public static Vector<Integer> shortest_path(Graph g, int v1, int v2){
        Vector<Integer> result = new Vector<>();

        Integer[] dist = new Integer[g.getVertices().size()];
        Set<Integer> visited = new HashSet<>();

        for (int i=0; i<dist.length; i++)
            dist[i] = Integer.MAX_VALUE;
        dist[v1] = 0;
        visited.add(v1);
        Vertex v = new Vertex(v1);
        while (!visited.contains(v2)) {
            for (Vertex vi : g.getAdjVertices(v)) {
                int edgeW = Integer.MAX_VALUE;
                for (Edge ei : g.getEdges()) {
                    if (ei.getV1().equals(v) && ei.getV2().equals(vi)) {
                        edgeW = ei.weight;
                    }
                }
                if (dist[vi.getLabel()] > edgeW + dist[v.getLabel()])
                    dist[vi.getLabel()] = edgeW + dist[v.getLabel()];
            }

            int minDist = Integer.MAX_VALUE;
            int newIndex = v1;
            for (int i=0; i<dist.length; i++) {
                if (dist[i] < minDist && !visited.contains(i)) {
                    minDist = dist[i];
                    newIndex = i;
                }
            }
            visited.add(newIndex);
            v = new Vertex(newIndex);
        }

        int minDistToDest = dist[v2];
        int eCpy = v2;

        Stack<Integer> s = new Stack<>();
        s.push(v2);

        while (minDistToDest > 0) {
            for (Edge e : g.getEdges()) {
                if (e.getV2().getLabel() == eCpy && minDistToDest - e.weight == dist[e.getV1().getLabel()]) {
                    minDistToDest -= e.weight;
                    s.push(e.getV1().getLabel());
                    eCpy = e.getV1().getLabel();
                }
            }
        }

        while (!s.empty())
            result.add(s.pop());

        return result;
    }

    /**
     * Describes every adjacent vertex of each node in the graph
     * with their weights
     * @param g graph to be plotted
     */
    public static void plot_graph(Graph g){
        for(Vertex v : g.getVertices()){
            System.out.print("adjacent vertices of " + v + ":\n\t");
            for(Vertex vN: g.getAdjVertices(v)) {
                System.out.print(vN + " ");
                for(Edge e : g.getEdges()){
                    if(e.v1.equals(v) && e.v2.equals(vN)){
                        System.out.print("(weight:" + e.weight + ")\n\t");
                    }
                }
            }
            System.out.println();
        }
    }
}
