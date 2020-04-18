package Graph;

/**
 * Represents a weighted connection between two vertices
 * @author Yasir
 */
public class Edge {
    /** If there is no weight specified, this value applies as the weight*/
    private static final int DEFAULT_WEIGHT = 1;

    /** The vertex that the edge starts*/
    Vertex v1;

    /** The vertex that the vertex ends*/
    Vertex v2;

    /** Weight of the connection*/
    int weight;

    /**
     * Constructor without weight specified, makes the weight one
     * @param v1 starting vertex
     * @param v2 ending vertex
     */
    public Edge(Vertex v1, Vertex v2) {
        this(v1, v2, DEFAULT_WEIGHT);
    }

    /**
     * Constructor with weight specified
     * @param v1 starting vertex
     * @param v2 ending vertex
     * @param weight weight of the edge
     */
    public Edge(Vertex v1, Vertex v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    /**
     * Getter for starting vertex
     * @return starting vertex
     */
    public Vertex getV1(){return v1;}

    /**
     * Getter for ending vertex
     * @return ending vertex
     */
    public Vertex getV2(){return v2;}

    /**
     * equals override
     * Compares vertices on the two ends of the edges and
     * the weight of the edges
     * @param obj edge to be compared
     * @return true if the edges have same fields
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;

        Edge edgeObj = (Edge) obj;
        return edgeObj.v1.equals(v1) && edgeObj.v2.equals(v2) &&
                edgeObj.weight == weight;
    }

    /**
     * hashcode override
     * Only because of the equals override
     * @return pseudo hashcode of the edge
     */
    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        result = 31 * result + weight;
        return result;
    }

    /**
     * tostring override
     * Describes the fields of the edge
     * @return Vertices on the ends of the edge and the weight of the edge in a formatted string
     */
    @Override
    public String toString() {
        return "This edge is connecting vertex " +
                v1 + " and vertex " + v2 +
                " with the weight of " + Integer.toString(weight);
    }
}
