public class Problem02 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];
        int[] size = new int[n];
        int provinces = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    int rootI = find(parent, i);
                    int rootJ = find(parent, j);

                    if (rootI != rootJ) {
                        if (size[rootI] < size[rootJ]) {
                            parent[rootI] = rootJ;
                            size[rootJ] += size[rootI];
                        } else {
                            parent[rootJ] = rootI;
                            size[rootI] += size[rootJ];
                        }

                        provinces--;
                    }
                }
            }
        }

        return provinces;
    }

    private int find(int[] parent, int city) {
        if (parent[city] != city) {
            parent[city] = find(parent, parent[city]);
        }

        return parent[city];
    }
}
