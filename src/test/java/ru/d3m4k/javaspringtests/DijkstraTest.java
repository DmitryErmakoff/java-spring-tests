package ru.d3m4k.javaspringtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class DijkstraTest {

	@Test
	public void testShortestPath_WithSinglePath_ShouldReturnCorrectDistances() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 1);
		dijkstra.addEdge(1, 2, 1);

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		Assertions.assertThat(distances).containsEntry(0, 0);
		Assertions.assertThat(distances).containsEntry(1, 1);
		Assertions.assertThat(distances).containsEntry(2, 2);
	}

	@Test
	public void testShortestPath_WithMultiplePaths_ShouldReturnOptimalDistances() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 4);
		dijkstra.addEdge(0, 2, 1);
		dijkstra.addEdge(2, 1, 2);
		dijkstra.addEdge(1, 3, 1);
		dijkstra.addEdge(2, 3, 5);

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		Assertions.assertThat(distances).containsEntry(0, 0);
		Assertions.assertThat(distances).containsEntry(1, 3);
		Assertions.assertThat(distances).containsEntry(2, 1);
		Assertions.assertThat(distances).containsEntry(3, 4);
	}

	@Test
	public void testShortestPath_WithNoEdges_ShouldReturnInitialDistance() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 1); // добавляем только одно ребро

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		Assertions.assertThat(distances).containsEntry(0, 0);
		Assertions.assertThat(distances).containsEntry(1, 1);
	}

	@Test
	public void testShortestPath_WithDisconnectedGraph_ShouldIndicateInfiniteDistances() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 1);
		dijkstra.addEdge(2, 3, 1);

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		Assertions.assertThat(distances).containsEntry(0, 0);
		Assertions.assertThat(distances).containsEntry(1, 1);
		Assertions.assertThat(distances).containsEntry(2, Integer.MAX_VALUE);
	}

	@Test
	public void testShortestPath_WithNegativeWeight_ShouldThrowException() {
		Dijkstra dijkstra = new Dijkstra();
		Assertions.assertThatThrownBy(() -> dijkstra.addEdge(0, 1, -1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Величина не может быть отрицательной");
	}

	@Test
	public void testShortestPath_WithEmptyGraph_ShouldReturnEmptyDistances() {
		Dijkstra dijkstra = new Dijkstra();

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		Assertions.assertThat(distances).isEmpty();
	}
}
