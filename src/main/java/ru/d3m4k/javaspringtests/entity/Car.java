package ru.d3m4k.javaspringtests.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

    @Id
    private Long id;

    private String name;
}
