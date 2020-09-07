import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class RandomDepthFirstPaths {
    private final boolean[] marked;    // marked[v] = is there an s-v path?
    private final int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public RandomDepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }

    public void randomDFS(Graph G) {
        randomDFS(G, s);
    }

    // depth first search from v
    private void randomDFS(Graph G, int v) {
        // TODO

        marked[v] = true;
        Collections.shuffle(G.adj(v));

        for (int w : G.adj(v)) {
            if (!marked[w]) {

                edgeTo[w] = v;
                randomDFS(G, w);
            }
        }
    }

    public void randomNonrecursiveDFS(Graph G) {
        // TODO

        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
            Collections.shuffle(G.adj(v));
        }


        // depth-first search using an explicit stack
        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);

        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    // discovered vertex w for the first time
                    edgeTo[w] = v;
                    marked[w] = true;
                    stack.push(w);
                }
            } else {
                stack.pop();
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a path between the source vertex
     * {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     *                                  <p>
     *                                  This method is different compared to the original one.
     */
    public List<Integer> pathTo(int v) {
        // TODO

        validateVertex(v);

        List<Integer> list = new ArrayList<Integer>();
        list.add(v);

        if (!hasPathTo(v)) {
            return null;
        }
        while (edgeTo[v] != s) {
            list.add(edgeTo[v]);
            v = edgeTo[v];
        }
        list.add(s);

        return list;
    }

    public int[] edge() {
        return edgeTo;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}

