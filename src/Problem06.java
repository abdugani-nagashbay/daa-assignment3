import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Problem06 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] dependency : dependencies) {
            graph.get(dependency[0]).add(dependency[1]);
        }

        int[] index = new int[n];
        int[] low = new int[n];
        int[] nextEdge = new int[n];
        boolean[] onStack = new boolean[n];

        Arrays.fill(index, -1);

        Deque<Integer> dfsStack = new ArrayDeque<>();
        Deque<Integer> componentStack = new ArrayDeque<>();

        int timer = 0;
        int groups = 0;

        for (int start = 0; start < n; start++) {
            if (index[start] != -1) {
                continue;
            }

            index[start] = low[start] = timer++;
            dfsStack.push(start);
            componentStack.push(start);
            onStack[start] = true;

            while (!dfsStack.isEmpty()) {
                int current = dfsStack.peek();

                if (nextEdge[current] < graph.get(current).size()) {
                    int next = graph.get(current).get(nextEdge[current]);
                    nextEdge[current]++;

                    if (index[next] == -1) {
                        index[next] = low[next] = timer++;
                        dfsStack.push(next);
                        componentStack.push(next);
                        onStack[next] = true;
                    } else if (onStack[next]) {
                        low[current] = Math.min(low[current], index[next]);
                    }
                } else {
                    dfsStack.pop();

                    if (low[current] == index[current]) {
                        int vertex;

                        do {
                            vertex = componentStack.pop();
                            onStack[vertex] = false;
                        } while (vertex != current);

                        groups++;
                    }

                    if (!dfsStack.isEmpty()) {
                        int parent = dfsStack.peek();
                        low[parent] = Math.min(low[parent], low[current]);
                    }
                }
            }
        }

        return groups;
    }
}
