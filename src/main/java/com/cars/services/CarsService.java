package com.cars.services;

import com.cars.dto.CarsDto;
import com.cars.dto.CarsResponseDto;

public interface CarsService {
    CarsResponseDto getAllCarsDetails(int page, int size, String searchString);

    CarsDto getCarWithFuelDetailsById(Long id);

    CarsDto createNewCarWithFuelDetails(CarsDto carsDto);

    CarsDto updateCarDetails(Long id, CarsDto carsDto);

    void deleteCarDetails(Long id);
}
