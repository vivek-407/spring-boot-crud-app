package com.cars.services.impl;

import com.cars.domains.Cars;
import com.cars.repositories.CarsRepository;
import com.cars.services.CarsRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarsRepoServiceImpl implements CarsRepoService {


    private CarsRepository carsRepository;


    @Autowired
    public CarsRepoServiceImpl(CarsRepository carsRepository){
        this.carsRepository = carsRepository;
    }
    @Override
    public Page<Cars> getAllCarsWithPagination(Pageable pageable) {
        return carsRepository.findAll(pageable);
    }

    @Override
    public Optional<Cars> findCarsById(Long id) {
        return carsRepository.findById(id);
    }

    @Override
    public Cars save(Cars car) {
        return carsRepository.save(car);
    }

    @Override
    public Optional<Cars> findCarWithFuelDetails(Long id) {
        return carsRepository.findCarWithFuelDetails(id);
    }

    @Override
    public Page<Cars> getAllCarsWithSearchAndPagination(Pageable pageable, String searchString) {
        return carsRepository.getAllCarsWithSearchAndPagination(pageable,searchString);
    }

    @Override
    public Cars findCarByBrandAndModelAndYearAndPrice(String brand, String model, int year, Long price) {
        return carsRepository.findCarByBrandAndModelAndYearAndPrice(brand,model,year,price);
    }


}
