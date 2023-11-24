package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraShortestPath {
    private static final double EPS = 1e-6;
    public static class Edge {
        double cost;
        int from, to;

        public Edge(int from, int to, double cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }


    public static class Node {
        int id;
        double value;

        public Node(int id, double value) {
            this.id = id;
            this.value = value;
        }
    }

    private final int n;
    private Integer[] prev;
    private List<List<Edge>> graph;

    private final Comparator<Node> comparator = (node1, node2) -> {
        if (Math.abs(node1.value - node2.value) < EPS) return 0;
        return (node1.value - node2.value) > 0 ? +1 : -1;
    };


    public DijkstraShortestPath(int n) {
        this.n = n;
        createEmptyGraph();
    }

    public void addEdge(int from, int to, int cost) {
        graph.get(from).add(new Edge(from, to, cost));
    }

    public List<Integer> reconstructPath(int start, int end) {
        if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
        if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");
        double dist = dijkstra(start, end);
        List<Integer> path = new ArrayList<>();
        if (dist == Double.POSITIVE_INFINITY) return path;
        for (Integer at = end; at != null; at = prev[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }


    public double dijkstra(int start, int end) {

        double[] dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;


        PriorityQueue<Node> pq = new PriorityQueue<>(2 * n, comparator);
        pq.offer(new Node(start, 0));


        boolean[] visited = new boolean[n];
        prev = new Integer[n];

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            visited[node.id] = true;

            // found a better path before we got to
            // processing this node, so we can ignore it.
            if (dist[node.id] < node.value) continue;

            List<Edge> edges = graph.get(node.id);
            for (Edge edge : edges) {
                // cannot get a shorter path by revisiting
                // a node you have already visited before.
                if (visited[edge.to]) continue;

                // Relax edge by updating minimum cost if applicable.
                double newDist = dist[edge.from] + edge.cost;
                if (newDist < dist[edge.to]) {
                    prev[edge.to] = edge.from;
                    dist[edge.to] = newDist;
                    pq.offer(new Node(edge.to, dist[edge.to]));
                }
            }

            if (node.id == end) return dist[end];
        }
        // End node is unreachable
        return Double.POSITIVE_INFINITY;
    }

    private void createEmptyGraph() {
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    }
}