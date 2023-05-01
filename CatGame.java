import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import java.util.Random;

public class CatGame {
    //Length of board
    private int n;
    private int ghostNode;
    private int catPos;

    private boolean[] marked;

    EdgeWeightedGraph G;

    /*
     * Initializes the EWG G
     * Connects all vertices to adjacent vertices
     * Randomly blocks a random # of vertices
     */
    public CatGame(int n){
        this.n = n;
        ghostNode = n * n;
        catPos = ghostNode / 2;
        G = new EdgeWeightedGraph(n * n + 1);
        marked = new boolean[n * n];

        for (int i = marked.length - 1; i > 0; i --){
            marked[i] = false;
        }

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

        for (int row = 0; row < n; row ++){
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
            } else if (row == n - 1){
                for (int col = 0; col < n; col ++){
                    int v = index(row, col);
                    connectGhost(v);
                    if (col != n - 1)
                        G.addEdge(new CatEdge(v, v + 1));
                }
            } else{
                if (row % 2 == 0){
                    int v = index(row, 0);
                    G.addEdge(new CatEdge(v, v + 1));
                    G.addEdge(new CatEdge(v, v + n));
                    connectGhost(v);

                    v = index(row, n - 1);
                    G.addEdge(new CatEdge(v, v + n));
                    G.addEdge(new CatEdge(v, v + n - 1));
                    connectGhost(v);
                } else{
                    int v = index(row, 0);
                    G.addEdge(new CatEdge(v, v + 1));
                    G.addEdge(new CatEdge(v, v + n));
                    G.addEdge(new CatEdge(v, v + n + 1));
                    connectGhost(v);

                    v = index(row, n - 1);
                    G.addEdge(new CatEdge(v, v + n));
                    connectGhost(v);
                }
            }
        }

        Random rand = new Random();
        int num = rand.nextInt(n / 2);
        num = 20;
        while(num > 0){
            int row = rand.nextInt(n);
            int col = rand.nextInt(n);
            while (marked(row, col) || index(row, col) == n * n / 2){
                row = rand.nextInt(n);
                col = rand.nextInt(n);
            }
            noMoveMark(row, col);
            num --;
        }
    }

    /*
     * Connects the ghostNode to the vertex at v
     */
    private void connectGhost(int v){
        G.addEdge(new CatEdge(v, n * n));
    }
    
    //marks a tile and moves the cat
    public void markTile(int row, int col){
        noMoveMark(row, col);
        DijkstraUndirectedSP SP = new DijkstraUndirectedSP(G, catPos);
        if (!catIsTrapped()){
            CatEdge next = (CatEdge) SP.pathTo(n * n).iterator().next();
            catPos = next.other(catPos);
        }
    }

    /*
     * Marks the tile at row, col 
     * Updates the weight of the edges from that vertex
     */
    private void noMoveMark(int row, int col){
        int v = index(row, col);
        marked[v] = true;
        for (Edge i: G.adj(v)){
            CatEdge c = (CatEdge) i;
            c.changeWeight();
        }
    }

    /*
     * Checks if a vertex at row, col is marked
     */
    public boolean marked(int row, int col){
        if (marked[index(row, col)] == true)
            return true;
        return false;
    }

    /*
     * Returns an iterable of row, column of cat position
     */
    public int[] getCatTile(){
        int[] tiles = {catPos / n, catPos % n};
        return tiles;
    }

    /*
     * Checks if the cat has escaped
     */
    public boolean catHasEscaped(){
        if (catPos == ghostNode)
            return true;
        return false;
    }

    /*
     * Checks if the cat has any available moves
     */
    public boolean catIsTrapped(){
        DijkstraUndirectedSP SP = new DijkstraUndirectedSP(G, catPos);
        if (SP.hasPathTo(ghostNode) == false)
            return true;
        return false;
    }

    //Returns the index of a vertex at row, col
    private int index(int row, int col){
        return n * row + col;
    }
} 