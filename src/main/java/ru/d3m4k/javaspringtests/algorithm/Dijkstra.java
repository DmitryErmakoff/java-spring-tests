package ru.d3m4k.javaspringtests.algorithm;

import ru.d3m4k.javaspringtests.entity.Edge;

import java.util.*;

public class Dijkstra {
    private final Map<Integer, List<Edge>> graph = new HashMap<>();

    public void addEdge(int source, int destination, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Величина не может быть отрицательной");
        }
        graph.putIfAbsent(source, new ArrayList<>());
        graph.get(source).add(new Edge(destination, weight));
    }

    public Map<Integer, Integer> shortestPath(int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        for (int vertex : graph.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        priorityQueue.add(new Edge(start, 0));

        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            int currentDistance = distances.get(current.getDestination());
            if (currentDistance == Integer.MAX_VALUE) {
                continue;
            }

            for (Edge neighbor : graph.getOrDefault(current.getDestination(), Collections.emptyList())) {
                int newDist = currentDistance + neighbor.getWeight();
                if (newDist < distances.getOrDefault(neighbor.getDestination(), Integer.MAX_VALUE)) {
                    distances.put(neighbor.getDestination(), newDist);
                    priorityQueue.add(new Edge(neighbor.getDestination(), newDist));
                }
            }
        }

        for (int vertex : graph.keySet()) {
            distances.putIfAbsent(vertex, Integer.MAX_VALUE);
        }

        if (graph.isEmpty()) {
            return Collections.emptyMap();
        }

        return distances;
    }
}