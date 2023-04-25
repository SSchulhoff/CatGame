import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import java.util.Random;

public class CatGame {
    private int n;
    private int extraNode;

    private boolean[] marked;

    public CatGame(int n){
        this.n = n;
        extraNode = n * n + 1;
        EdgeWeightedGraph G = new EdgeWeightedGraph(extraNode);
        marked = new boolean[n * n];
        for (int i = marked.length - 1; i > 0; i --){
            marked[i] = false;
        }

        for (int row = 1; row < n - 1; row ++){
            for (int col = 1; col < n - 1; col ++){
                int v = index(row, col);
                G.addEdge(new CatEdge(v, v - 1));
                G.addEdge(new CatEdge(v, v + 1));
                G.addEdge(new CatEdge(v, v - n - 1));
                G.addEdge(new CatEdge(v, v - n));
                G.addEdge(new CatEdge(v, v + n));
                G.addEdge(new CatEdge(v, v + n - 1));
            }
        }

        Random rand = new Random();
        int num = rand.nextInt(n / 2);
        while(num > 0){
            int row = rand.nextInt(n * n);
            int col = rand.nextInt(n * n);
            while (marked(row, col)){
                row = rand.nextInt(n * n);
                col = rand.nextInt(n * n);
            }
            markTile(row, col);
            num --;
        }
    }
    
    public void markTile(int row, int col){
        int n = index(row, col);
        marked[n] = true;
    }

    public boolean marked(int row, int col){
        return true;
    }

    // public int[] getCatTile(){
    //     return ;
    // }

    public boolean catHasEscaped(){
        return true;
    }

    public boolean catIsTrapped(){
        return true;
    }

    /*
     * returns index in the array
     */
    private int index(int row, int col){
        return n * row + col;
    }
}