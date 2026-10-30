import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Problem10 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int required = prerequisite[1];

            graph.get(required).add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int course = 0; course < numCourses; course++) {
            if (indegree[course] == 0) {
                queue.offer(course);
            }
        }

        int[] order = new int[numCourses];
        int completed = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order[completed++] = current;

            for (int next : graph.get(current)) {
                indegree[next]--;

                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        if (completed != numCourses) {
            return new int[0];
        }

        return order;
    }
}
