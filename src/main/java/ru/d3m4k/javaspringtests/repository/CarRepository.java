package ru.d3m4k.javaspringtests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.d3m4k.javaspringtests.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
