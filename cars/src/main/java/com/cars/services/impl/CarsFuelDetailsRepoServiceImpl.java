package com.cars.services.impl;

import com.cars.domains.CarFuelDetails;
import com.cars.repositories.CarsFuelDetailsRepository;
import com.cars.services.CarsFuelRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarsFuelDetailsRepoServiceImpl implements CarsFuelRepoService {

    private CarsFuelDetailsRepository carsFuelDetailsRepository;

    @Autowired
    public CarsFuelDetailsRepoServiceImpl(CarsFuelDetailsRepository carsFuelDetailsRepository){
        this.carsFuelDetailsRepository = carsFuelDetailsRepository;
    }

    @Override
    public CarFuelDetails save(CarFuelDetails carFuelDetails) {
        return carsFuelDetailsRepository.save(carFuelDetails);
    }

    @Override
    public Optional<CarFuelDetails> findById(Long id) {
        return carsFuelDetailsRepository.findById(id);
    }
}
