import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        List<List<Integer>> graph = new ArrayList<>();
        List<List<Integer>> reversed = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            reversed.add(new ArrayList<>());
        }

        for (int[] dependency : dependencies) {
            int from = dependency[0];
            int to = dependency[1];

            graph.get(from).add(to);
            reversed.get(to).add(from);
        }

        boolean[] visited = new boolean[n];
        int[] nextEdge = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> order = new ArrayDeque<>();

        // First pass: record vertices in finishing order.
        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            visited[start] = true;
            stack.push(start);

            while (!stack.isEmpty()) {
                int current = stack.peek();

                if (nextEdge[current] < graph.get(current).size()) {
                    int next = graph.get(current).get(nextEdge[current]);
                    nextEdge[current]++;

                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(next);
                    }
                } else {
                    stack.pop();
                    order.push(current);
                }
            }
        }

        Arrays.fill(visited, false);
        int groups = 0;

        while (!order.isEmpty()) {
            int start = order.pop();

            if (visited[start]) {
                continue;
            }

            groups++;
            visited[start] = true;
            stack.push(start);

            while (!stack.isEmpty()) {
                int current = stack.pop();

                for (int next : reversed.get(current)) {
                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(next);
                    }
                }
            }
        }

        return groups;
    }
}
