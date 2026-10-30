### DAA Assignment3 - Report and Defense (50 points)

##### Problem 1 and 2 - Number of Provinces (10 points)

**Problem:** Count connected components in an undirected graph represented by an adjacency matrix. Each component is one province.

**Problem 1 — DFS**

I start DFS from every unvisited city. DFS marks all cities reachable from that city as visited. Each new traversal identifies one province, so I increase the province count once per traversal.

Every city belongs to exactly one connected component. DFS visits the entire component, and visited cities are skipped. Therefore, every province is counted exactly once.

- Time complexity: O(n²), because each city's matrix row is scanned once.
- Extra space: O(n) for the visited array and recursion stack.

**Problem 2 — Disjoint Set Union**

Initially, each city belongs to a separate set, and the province count is n. For every connected pair, I find their representatives. If they differ, I merge the sets and decrease the count.

I use path compression and union by size to make DSU operations efficient. Since the matrix is symmetric, only its upper triangle is checked.

Each successful union joins two distinct connected components. After processing all connections, the remaining sets correspond exactly to the provinces.

- Time complexity: O(n² × α(n)) amortized, where α is the inverse Ackermann function.
- Extra space: O(n).

**Comparison:** DFS explores connected components directly. DSU maintains components by merging sets. Both return the same province count.


##### Problem 3 and 4 - Min Cost Spanning Tree (10 points)

**Problem:** Connect all points with minimum total cost. The cost between two points is their Manhattan distance:

|x₁ - x₂| + |y₁ - y₂|.

The points form a complete weighted graph. A minimum spanning tree connects all vertices without cycles using n - 1 edges.

**Problem 3 — Kruskal's Algorithm**

I generate every possible edge and sort the edges by increasing weight. I use DSU to track connected components.

An edge is selected only if its endpoints belong to different components. I add its weight to the total and merge the components. The algorithm stops after selecting n - 1 edges.

Each selected edge is a minimum-weight edge connecting different current components. By the cut property, it is safe to include in a minimum spanning tree.

- Time complexity: O(n² log n), dominated by sorting O(n²) edges.
- Extra space: O(n²) for the edge list, sorting, and DSU arrays.

**Problem 4 — Prim's Algorithm**

I start from point 0. For each unvisited point, minDistance stores the cheapest connection to the current tree.

At each step, I select the unvisited point with the smallest connection cost, add it to the tree, and update the remaining distances.

The selected edge is a minimum-weight edge crossing the cut between visited and unvisited points. By the cut property, each selection is safe.

- Time complexity: O(n²).
- Extra space: O(n).

**Comparison:** Kruskal selects edges globally and merges components. Prim grows one tree. For this complete graph, the implemented Prim solution uses less memory and avoids sorting all edges.


##### Problem 5 and 6 - Software Dependency Cycles (10 points)

**Problem:** Count strongly connected components in a directed dependency graph. Within each component, every module can reach every other module. A single module also forms a component.

**Problem 5 — Kosaraju's Algorithm**

I build the original graph and its reversed graph.

The first DFS pass records vertices in finishing order. The second pass processes vertices in decreasing finishing order and explores the reversed graph. Each new traversal identifies one strongly connected component.

The finishing order ensures that a second-pass traversal cannot enter another unvisited component. Reversing edges preserves connectivity within each strongly connected component, so each traversal finds exactly one group.

Both passes use explicit stacks to avoid recursion depth problems.

- Time complexity: O(n + m).
- Extra space: O(n + m).

Here, n is the number of modules and m is the number of dependencies.

**Problem 6 — Tarjan's Algorithm**

I use one DFS traversal with discovery indices, low-link values, and a component stack.

The low-link value tracks connections to earlier vertices that remain on the component stack. After processing a vertex v, if low[v] equals index[v], v is the root of a strongly connected component.

I pop vertices from the component stack through v and count one group. Each vertex is assigned to exactly one component.

An explicit DFS stack replaces recursion.

- Time complexity: O(n + m).
- Extra space: O(n + m).

**Comparison:** Kosaraju uses two DFS passes and a reversed graph. Tarjan uses one DFS pass and low-link values. Both identify the same strongly connected components.


##### Problem 7 and 8 - Course Schedule (10 points)

**Problem:** Determine whether all courses can be completed. For each prerequisite pair [a, b], I add an edge b → a because course b must be completed before course a.

All courses can be completed exactly when the directed graph has no cycle.

**Problem 7 — DFS**

I use three states:

- 0: unvisited.
- 1: on the active DFS path.
- 2: fully processed.

An edge to a vertex with state 1 closes a directed cycle, so the algorithm returns false. If every traversal finishes without detecting a cycle, it returns true.

DFS starts from every unvisited course, ensuring that disconnected parts of the graph are checked. An explicit stack simulates recursive DFS.

- Time complexity: O(V + E).
- Extra space: O(V + E), including the graph.

**Problem 8 — Kahn's Algorithm**

I calculate each course's indegree and add all courses with indegree zero to a queue.

When a course is removed, I count it as completed and decrease the indegree of its neighbors. A neighbor enters the queue when all its prerequisites have been processed.

If all V courses are processed, the algorithm returns true. Otherwise, a cycle prevents some courses from reaching indegree zero.

- Time complexity: O(V + E).
- Extra space: O(V + E).

Here, V is the number of courses and E is the number of prerequisite pairs.

**Comparison:** DFS detects a cycle through an edge to an active vertex. Kahn's algorithm detects a cycle when fewer than V courses can be processed.


##### Problem 9 and 10 - Course Schedule II (10 points)

**Problem:** Return an order in which all courses can be completed. If a cycle makes this impossible, return an empty array. Any valid topological ordering is acceptable.

**Problem 9 — DFS**

I use the same three-state DFS cycle detection as in Problem 7.

When a vertex finishes, I place it in the result array from right to left. This creates reverse finishing order.

For every edge u → v in an acyclic graph, v finishes before u. Therefore, reverse finishing order places u before v, ensuring that prerequisites appear before dependent courses.

If DFS detects a cycle, the algorithm returns an empty array.

- Time complexity: O(V + E).
- Extra space: O(V + E).

**Problem 10 — Kahn's Algorithm**

I calculate indegrees and place every course with no remaining prerequisites into a queue.

Each removed course is appended to the result array. I then decrease its neighbors' indegrees and enqueue any that become zero.

A course is added to the result only after all its prerequisites have been processed. Therefore, if all courses are processed, the result is a valid topological ordering.

If fewer than V courses are processed, the graph contains a cycle, and the algorithm returns an empty array.

- Time complexity: O(V + E).
- Extra space: O(V + E).

**Comparison:** DFS builds the order from finishing times. Kahn's algorithm builds it by repeatedly selecting available courses. Their outputs may differ, but both are valid when every prerequisite appears before its dependent course.