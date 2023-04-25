import edu.princeton.cs.algs4.Edge;
public class CatEdge extends Edge{
    private double weight;

    public CatEdge(int to, int from){
        super(to, from, 1.0);
    }

    public void changeWeight(){
        this.weight = Double.POSITIVE_INFINITY;
    }

    public double weight(){
        return this.weight;
    }
}