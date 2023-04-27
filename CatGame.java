import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import java.util.Random;

public class CatGame {
    private int n;
    private int ghostNode;
    private int catPos;

    private boolean[] marked;

    public CatGame(int n){
        this.n = n;
        ghostNode = n * n + 1;
        EdgeWeightedGraph G = new EdgeWeightedGraph(ghostNode);
        marked = new boolean[n * n];
        for (int i = marked.length - 1; i > 0; i --){
            marked[i] = false;
        }

        //need to fix it adds edges between vertices multiple times
        for (int row = 0; row < n - 1; row ++){
            for (int col = 0; col < n - 1; col ++){
                int v = index(row, col);
                G.addEdge(new CatEdge(v, v + 1));
                if (row % 2 == 0){
                    G.addEdge(new CatEdge(v, v + n));
                }
                else{
                    G.addEdge(new CatEdge(v, v + n - 1));
                }
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
        marked[index(row, col)] = true;
        //move the cat after this
    }

    public boolean marked(int row, int col){
        if (marked[index(row, col)] == true)
            return true;
        return false;
    }

    public int[] getCatTile(){
        int[] tile = new int[2];
        tile[0] = catPos / n;
        tile[1] = catPos % n;
        return tile;
    }

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
