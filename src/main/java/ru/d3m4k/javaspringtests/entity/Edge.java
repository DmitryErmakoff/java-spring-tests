package ru.d3m4k.javaspringtests.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Edge {
    private int destination;
    private int weight;
}
