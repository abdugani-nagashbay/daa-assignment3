import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Problem07 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]);
        }

        // 0 = unvisited, 1 = visiting, 2 = finished
        int[] state = new int[numCourses];
        int[] nextEdge = new int[numCourses];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int start = 0; start < numCourses; start++) {
            if (state[start] != 0) {
                continue;
            }

            state[start] = 1;
            stack.push(start);

            while (!stack.isEmpty()) {
                int current = stack.peek();

                if (nextEdge[current] < graph.get(current).size()) {
                    int next = graph.get(current).get(nextEdge[current]);
                    nextEdge[current]++;

                    if (state[next] == 1) {
                        return false;
                    }

                    if (state[next] == 0) {
                        state[next] = 1;
                        stack.push(next);
                    }
                } else {
                    state[current] = 2;
                    stack.pop();
                }
            }
        }

        return true;
    }
}
