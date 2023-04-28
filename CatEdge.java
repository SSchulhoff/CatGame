import edu.princeton.cs.algs4.Edge;
public class CatEdge extends Edge{
    private double weight;

    /*
     * Creates a new edge with to, from, and weight of 1
     */
    public CatEdge(int to, int from){
        super(to, from, 1.0);
    }

    /*
     * Sets the weight of this edge to infinity
     */
    public void changeWeight(){
        this.weight = Double.POSITIVE_INFINITY;
    }

    /*
     * Returns the weight of this edge
     */
    public double weight(){
        return this.weight;
    }
}