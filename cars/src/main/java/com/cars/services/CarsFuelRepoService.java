package com.cars.services;

import com.cars.domains.CarFuelDetails;

import java.util.Optional;

public interface CarsFuelRepoService {
    CarFuelDetails save(CarFuelDetails carFuelDetails);

    Optional<CarFuelDetails> findById(Long id);
}
