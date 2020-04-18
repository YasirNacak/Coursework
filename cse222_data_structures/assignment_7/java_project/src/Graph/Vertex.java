package Graph;

/**
 * Represents a single node in the graph
 * @author Yasir
 */
public class Vertex{
    /** Name of the node*/
    private int label;

    /**
     * Constructor that takes a label
     * @param label label of the node
     */
    public Vertex(int label) {
        this.label = label;
    }

    /**
     * Getter for label
     * @return label of the node
     */
    public int getLabel() {
        return label;
    }

    /**
     * equals override
     * Two vertices are equal if their labels are equal
     * @param obj other Vertex
     * @return true if their labels are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;

        Vertex vertexObj = (Vertex) obj;
        return vertexObj.label == label;
    }

    /**
     * Hashcode override
     * Written only because of equals override
     * @return labels value
     */
    @Override
    public int hashCode() {
        return label;
    }

    /**
     * Tostring override
     * Returns the label
     * @return label of the node as a string
     */
    @Override
    public String toString() {
        return Integer.toString(label);
    }
}
