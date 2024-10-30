package ru.d3m4k.javaspringtests;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import ru.d3m4k.javaspringtests.algorithm.Dijkstra;
import ru.d3m4k.javaspringtests.repository.CarRepository;

import java.util.Map;

class DijkstraTest {

	@DisplayName("Проверяет корректность расстояний, когда существует только один путь")
	@Test
	public void shouldReturnCorrectDistancesForSinglePath() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 1);
		dijkstra.addEdge(1, 2, 1);

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		assertThat(distances).containsEntry(0, 0);
		assertThat(distances).containsEntry(1, 1);
		assertThat(distances).containsEntry(2, 2);
	}

	@DisplayName("Проверяет, что оптимальные расстояния возвращаются при наличии нескольких путей")
	@Test
	public void shouldReturnOptimalDistancesForMultiplePaths() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 4);
		dijkstra.addEdge(0, 2, 1);
		dijkstra.addEdge(2, 1, 2);
		dijkstra.addEdge(1, 3, 1);
		dijkstra.addEdge(2, 3, 5);

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		assertThat(distances).containsEntry(0, 0);
		assertThat(distances).containsEntry(1, 3);
		assertThat(distances).containsEntry(2, 1);
		assertThat(distances).containsEntry(3, 4);
	}

	@DisplayName("Проверяет, что возвращаются начальные расстояния, когда граф не имеет рёбер")
	@Test
	public void shouldReturnInitialDistanceWhenNoEdgesArePresent() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 1); // добавляем только одно ребро

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		assertThat(distances).containsEntry(0, 0);
		assertThat(distances).containsEntry(1, 1);
	}

	@DisplayName("Проверяет, что для разъединенного возвращаются бесконечные расстояния для недоступных вершин")
	@Test
	public void shouldIndicateInfiniteDistancesInDisconnectedGraph() {
		Dijkstra dijkstra = new Dijkstra();
		dijkstra.addEdge(0, 1, 1);
		dijkstra.addEdge(2, 3, 1);

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		assertThat(distances).containsEntry(0, 0);
		assertThat(distances).containsEntry(1, 1);
		assertThat(distances).containsEntry(2, Integer.MAX_VALUE);
	}

	@DisplayName("Проверяет, что выбрасывается исключение при добавлении рёбер с отрицательным весом")
	@Test
	public void shouldThrowExceptionForNegativeWeight() {
		Dijkstra dijkstra = new Dijkstra();
		assertThatThrownBy(() -> dijkstra.addEdge(0, 1, -1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Величина не может быть отрицательной");
	}

	@DisplayName("Проверяет, что возвращаются пустые расстояния для пустого графа")
	@Test
	public void shouldReturnEmptyDistancesForEmptyGraph() {
		Dijkstra dijkstra = new Dijkstra();

		Map<Integer, Integer> distances = dijkstra.shortestPath(0);
		assertThat(distances).isEmpty();
	}
}
