package com.cars.services;

import com.cars.domains.Cars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CarsRepoService {
    Page<Cars> getAllCarsWithPagination(Pageable pageable);

    Optional<Cars> findCarsById(Long id);

    Cars save(Cars newCar);

    Optional<Cars> findCarWithFuelDetails(Long id);

    Page<Cars> getAllCarsWithSearchAndPagination(Pageable pageable, String searchString);

    Cars findCarByBrandAndModelAndYearAndPrice(String brand, String model, int year, Long price);
}
