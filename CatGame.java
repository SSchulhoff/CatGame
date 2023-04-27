import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import java.util.Random;

public class CatGame {
    private int n;
    private int ghostNode;
    private int catPos;

    private boolean[] marked;

    EdgeWeightedGraph G;

    public CatGame(int n){
        this.n = n;
        ghostNode = n * n;
        G = new EdgeWeightedGraph(ghostNode);
        marked = new boolean[n * n];
        for (int i = marked.length - 1; i > 0; i --){
            marked[i] = false;
        }

        //connects all the interior vertices
        for (int row = 1; row < n - 1; row ++){
            for (int col = 1; col < n - 1; col ++){
                int v = index(row, col);
                G.addEdge(new CatEdge(v, v + 1));
                G.addEdge(new CatEdge(v, v + n));
                if (row % 2 == 0)
                    G.addEdge(new CatEdge(v, v + n - 1));
                else
                    G.addEdge(new CatEdge(v, v + n + 1));
            }
        }

        //connects all exterior vertices
        for (int row = 0; row < n; row ++){
            //top row
            if (row == 0){
                for (int col = 0; col < n; col ++){
                    int v = index(row, col);
                    if (col != n - 1)
                        G.addEdge(new CatEdge(v, v + 1));
                    connectGhost(v);
                    G.addEdge(new CatEdge(v, v + n));
                    if (col != 0)
                        G.addEdge(new CatEdge(v, v + n - 1));
                }
            }
            //bottom row
            else if (row == n - 1){
                for (int col = 0; col < n; col ++){
                    int v = index(row, col);
                    connectGhost(v);
                    if (col != n - 1)
                        G.addEdge(new CatEdge(v, v + 1));
                }
            }
            //other rows
            else{
                if (row % 2 == 0){
                    //left vertex
                    int v = index(row, 0);
                    G.addEdge(new CatEdge(v, v + 1));
                    G.addEdge(new CatEdge(v, v + n));
                    connectGhost(v);

                    //right vertex
                    v = index(row, n - 1);
                    G.addEdge(new CatEdge(v, v + n));
                    G.addEdge(new CatEdge(v, v + n - 1));
                    connectGhost(v);
                }
                else{
                    //left vertex
                    int v = index(row, 0);
                    G.addEdge(new CatEdge(v, v + 1));
                    G.addEdge(new CatEdge(v, v + n));
                    G.addEdge(new CatEdge(v, v + n + 1));
                    connectGhost(v);

                    //right vertex
                    v = index(row, n - 1);
                    G.addEdge(new CatEdge(v, v + n));
                    connectGhost(v);
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

    private void connectGhost(int v){
        G.addEdge(new CatEdge(v, n * n));
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
