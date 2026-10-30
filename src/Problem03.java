import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Problem03 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int distance = Math.abs(points[i][0] - points[j][0])
                        + Math.abs(points[i][1] - points[j][1]);

                edges.add(new int[]{i, j, distance});
            }
        }

        edges.sort(Comparator.comparingInt(edge -> edge[2]));

        int[] parent = new int[n];
        int[] size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int totalCost = 0;
        int usedEdges = 0;

        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);

            if (rootA != rootB) {
                if (size[rootA] < size[rootB]) {
                    parent[rootA] = rootB;
                    size[rootB] += size[rootA];
                } else {
                    parent[rootB] = rootA;
                    size[rootA] += size[rootB];
                }

                totalCost += edge[2];
                usedEdges++;

                if (usedEdges == n - 1) {
                    break;
                }
            }
        }

        return totalCost;
    }

    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]);
        }

        return parent[vertex];
    }
}
