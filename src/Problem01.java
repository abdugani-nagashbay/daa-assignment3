public class Problem01 {
    public int findCircleNum(int[][] isConnected) {
        boolean[] visited = new boolean[isConnected.length];
        int provinces = 0;

        for (int city = 0; city < isConnected.length; city++) {
            if (!visited[city]) {
                dfs(isConnected, visited, city);
                provinces++;
            }
        }

        return provinces;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int city) {
        visited[city] = true;

        for (int next = 0; next < isConnected.length; next++) {
            if (isConnected[city][next] == 1 && !visited[next]) {
                dfs(isConnected, visited, next);
            }
        }
    }
}