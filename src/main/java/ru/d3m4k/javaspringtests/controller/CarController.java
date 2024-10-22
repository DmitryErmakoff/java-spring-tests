package ru.d3m4k.javaspringtests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.d3m4k.javaspringtests.entity.Car;
import ru.d3m4k.javaspringtests.repository.CarRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("")
    public List<Car> saveCar() {
        Car car1 = new Car(1L, "Audi");
        Car car2 = new Car(2L, "Porsche");
        Car car3 = new Car(3L, "Mercedes");
        return carRepository.saveAll(List.of(car1, car2, car3));
    }
}
