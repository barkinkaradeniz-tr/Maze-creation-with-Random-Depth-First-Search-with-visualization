import java.util.*;

/**
 * Class that represents a maze with N*N junctions.
 *
 * @author Vera Röhr
 */
public class Maze {
    private final int N;
    private final Graph M;    //Maze
    public int startnode;

    public Maze(int N, int startnode) {

        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M = new Graph(N * N);
        this.startnode = startnode;
        buildMaze();
    }

    public Maze(In in) {
        this.M = new Graph(in);
        this.N = (int) Math.sqrt(M.V());
        this.startnode = 0;
    }


    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        // TODO

        if ((0 <= v) && (v < M.V()) && (0 <= w) && (w < M.V())) {
            M.addEdge(v, w);
        } else {
            throw new IllegalArgumentException("{0 <= v < V} and {0 <= w < V} conditions should be fulfilled.");
        }
    }

    /**
     * Returns true if there is an edge between 'v' and 'w'
     *
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge(int v, int w) {
        // TODO

        if (v == w) {
            return true;
        } else {
            if (M.adj(v).contains(w)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Builds a grid as a graph.
     *
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        // TODO

        Graph G = new Graph(N * N);

        for (int in = 0; in < N - 1; in++) {
            for (int i = 0; i < N; i++) {
                G.addEdge(i+(in*N), (in*N)+i+N);
            }
        }
        for (int in = 0; in < N; in++) {
            for (int i = 0; i < N-1; i++) {
                G.addEdge(i+(in*N), i+(in*N)+1);
            }
        }

        return G;
    }

    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
        // TODO

        Graph G = mazegrid();
        RandomDepthFirstPaths RDFP = new RandomDepthFirstPaths(G, 0);

        RDFP.randomDFS(G);
        int[] tempPath = RDFP.edge();

        for (int in3 = 0; in3 < G.V(); in3++) {
            for (int in1 = 1; in1 < tempPath.length; in1++) {
                if (tempPath[in1] == in3) {
                    addEdge(in1, in3);
                }
            }
        }
    }

    /**
     * Find a path from node v to w
     *
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w) {
        // TODO

        DepthFirstPaths DFP = new DepthFirstPaths(M, v);
        DFP.nonrecursiveDFS(M);

        List<Integer> list = DFP.pathTo(w);
        Collections.reverse(list);

        return list;
    }

    /**
     * @return Graph M
     */
    public Graph M() {
        return M;
    }

    public static void main(String[] args) {
        // FOR TESTING

        Maze M = new Maze(100, 0);
        GridGraph vis= new GridGraph(M.M(), M.findWay(0, 9999));

    }
}

