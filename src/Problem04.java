public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        boolean[] visited = new boolean[n];
        int[] minDistance = new int[n];

        java.util.Arrays.fill(minDistance, Integer.MAX_VALUE);
        minDistance[0] = 0;

        int totalCost = 0;

        for (int count = 0; count < n; count++) {
            int current = -1;

            for (int i = 0; i < n; i++) {
                if (!visited[i]
                        && (current == -1
                        || minDistance[i] < minDistance[current])) {
                    current = i;
                }
            }

            visited[current] = true;
            totalCost += minDistance[current];

            for (int next = 0; next < n; next++) {
                if (!visited[next]) {
                    int distance =
                            Math.abs(points[current][0] - points[next][0])
                                    + Math.abs(points[current][1] - points[next][1]);

                    minDistance[next] =
                            Math.min(minDistance[next], distance);
                }
            }
        }

        return totalCost;
    }
}
