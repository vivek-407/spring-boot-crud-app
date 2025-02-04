package com.cars.services.impl;

import com.cars.domains.CarFuelDetails;
import com.cars.domains.Cars;
import com.cars.dto.CarFuelDto;
import com.cars.dto.CarsDto;
import com.cars.dto.CarsResponseDto;
import com.cars.exceptions.NotAcceptableException;
import com.cars.exceptions.NotFoundException;
import com.cars.services.CarsFuelRepoService;
import com.cars.services.CarsRepoService;
import com.cars.services.CarsService;
import com.cars.utils.enums.RecordStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Implementation of the CarsService interface to handle car and fuel related business logic.
 * This class provides methods for CRUD operations on car and fuel details, as well as pagination and search.
 */
@Service
public class CarsServiceImpl implements CarsService {

    private static final Logger logger = LoggerFactory.getLogger(CarsServiceImpl.class);


    private CarsRepoService carsRepoService;

    private CarsFuelRepoService carsFuelRepoService;

    @Autowired
    public CarsServiceImpl(CarsRepoService carsRepoService, CarsFuelRepoService carsFuelRepoService) {
        this.carsRepoService = carsRepoService;
        this.carsFuelRepoService = carsFuelRepoService;
    }


    /**
     * Retrieves a paginated list of all cars, optionally filtered by a search string.
     *
     * @param page         The page number to retrieve.
     * @param size         The number of records per page.
     * @param searchString The string to search for in car attributes.
     * @return A response DTO containing total records, filtered records, and car details.
     */
    @Override
    public CarsResponseDto getAllCarsDetails(int page, int size, String searchString) {
        logger.info("CarsServiceImpl | getAllCarsDetails | page {}, size {}, searchString {}", page, size, searchString);
        Page<Cars> carsPage;

        // If searchString is empty, use search functionality, otherwise fetch all cars (for better performance)
        if (!StringUtils.hasText(searchString)) {
            carsPage = carsRepoService.getAllCarsWithPagination(PageRequest.of(page, size));
        } else {
            carsPage = carsRepoService.getAllCarsWithSearchAndPagination(PageRequest.of(page, size), searchString);
        }

        long totalRecords = carsPage.getTotalElements();
        long filteredRecords = carsPage.getNumberOfElements();
        logger.info("CarsServiceImpl | getAllCarsDetails | total records {} and filtered records {}", totalRecords, filteredRecords);

        List<CarsDto> carsData = carsPage.hasContent()
                ? carsPage.getContent().stream().map(CarsDto::new).collect(Collectors.toList())
                : Collections.emptyList();

        return new CarsResponseDto(totalRecords, filteredRecords, carsData);
    }

    /**
     * Retrieves details of a car, including its associated fuel details, by car ID.
     *
     * @param id The ID of the car to retrieve.
     * @return A DTO representing the car with its fuel details.
     * @throws NotFoundException If the car is not found.
     */
    @Override
    public CarsDto getCarWithFuelDetailsById(Long id) {
        logger.info("CarsServiceImpl | getCarWithFuelDetailsById | car id {}", id);
        Cars cars = carsRepoService.findCarWithFuelDetails(id)
                .orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.CAR_NOT_FOUND));
        return new CarsDto(cars);

    }

    /**
     * Creates a new car with its associated fuel details.
     * If the fuel details already exist, they are updated; otherwise, new fuel details are created.
     *
     * @param carsDto DTO containing the details of the car and its fuel details.
     * @return A DTO representing the created car.
     */
    @Transactional
    @Override
    public CarsDto createNewCarWithFuelDetails(CarsDto carsDto) {
        logger.info("CarsServiceImpl | createNewCarWithFuelDetails | car dto {}", carsDto);

        CarFuelDto carFuelDto = carsDto.getCarFuelDto();

        Cars cars = carsDto.createEntity();
        validateCarExist(null, carsDto);


        if (carFuelDto != null) {
            if (carFuelDto.getId() != null) {
                logger.info("CarsServiceImpl | createNewCarWithFuelDetails | fuel id is present {}", carFuelDto.getId());
                // If Fuel ID is present, fetch existing record
                CarFuelDetails existingFuel = carsFuelRepoService.findById(carFuelDto.getId())
                        .orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.FUEL_NOT_FOUND));

                // Update existing fuel details
                existingFuel.setFuelType(carFuelDto.getFuelType());
                existingFuel.setFuelCapacity(carFuelDto.getFuelCapacity());
                existingFuel.setTransmissionType(carFuelDto.getTransmissionType());
                existingFuel.setRecordStatus(RecordStatus.UPDATE);
                // Save updated fuel details
                carsFuelRepoService.save(existingFuel);
                cars.setCarFuelDetails(existingFuel);
                cars.setCarFuelDetailsId(existingFuel.getId());

            } else {
                // If no ID, create new Fuel details
                CarFuelDetails newFuelDetails = carFuelDto.createEntity();
                newFuelDetails = carsFuelRepoService.save(newFuelDetails);
                cars.setCarFuelDetails(newFuelDetails);
                cars.setCarFuelDetailsId(newFuelDetails.getId());
                logger.info("CarsServiceImpl | createNewCarWithFuelDetails | fuel id is not present, newly created {}", newFuelDetails.getId());
            }
        }

        // Save the car entity with fuel details
        cars = carsRepoService.save(cars);
        logger.info("CarsServiceImpl | createNewCarWithFuelDetails | car details created successfully {}", cars);

        return new CarsDto(cars);
    }

    public void validateCarExist(Long currentCarId, CarsDto carsDto) {
        Cars existingCar = carsRepoService.findCarByBrandAndModelAndYearAndPrice(
                carsDto.getBrand(), carsDto.getModel(), carsDto.getYear(), carsDto.getPrice());

        if (existingCar != null && !existingCar.getId().equals(currentCarId)) {
            throw new NotAcceptableException(NotAcceptableException.NotAcceptable.CAR_ALREADY_EXIST);
        }

    }

    /**
     * Updates the details of an existing car, including its associated fuel details.
     *
     * @param id      The ID of the car to update.
     * @param carsDto The DTO containing the updated car and fuel details.
     * @return A DTO representing the updated car.
     * @throws NotFoundException If the car is not found.
     */
    @Override
    @Transactional
    public CarsDto updateCarDetails(Long id, CarsDto carsDto) {
        logger.info("CarsServiceImpl | updateCarDetails | car id {} and carsDto {}", id, carsDto);
        Cars cars = carsRepoService.findCarsById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.CAR_NOT_FOUND));
        validateCarExist(id,carsDto);


        // Update car attributes
        cars.setBrand(carsDto.getBrand());
        cars.setPrice(carsDto.getPrice());
        cars.setYear(carsDto.getYear());
        cars.setModel(carsDto.getModel());
        cars.setRecordStatus(RecordStatus.UPDATE);


        // Handle Fuel Details
        CarFuelDto carFuelDto = carsDto.getCarFuelDto();
        if (carFuelDto != null) {
            if (carFuelDto.getId() != null) {
                logger.info("CarsServiceImpl | updateCarDetails | fuel id is present {}", carFuelDto.getId());

                // If Fuel ID is present, fetch existing record
                CarFuelDetails existingFuel = carsFuelRepoService.findById(carFuelDto.getId())
                        .orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.FUEL_NOT_FOUND));

                // Update existing fuel details
                existingFuel.setFuelType(carFuelDto.getFuelType());
                existingFuel.setFuelCapacity(carFuelDto.getFuelCapacity());
                existingFuel.setTransmissionType(carFuelDto.getTransmissionType());
                existingFuel.setRecordStatus(RecordStatus.UPDATE);
                // Save updated fuel details
                carsFuelRepoService.save(existingFuel);
                cars.setCarFuelDetails(existingFuel);
                cars.setCarFuelDetailsId(existingFuel.getId());


            } else {
                // If no ID, create new Fuel details
                CarFuelDetails newFuelDetails = carFuelDto.createEntity();
                newFuelDetails = carsFuelRepoService.save(newFuelDetails);
                cars.setCarFuelDetails(newFuelDetails);
                cars.setCarFuelDetailsId(newFuelDetails.getId());
                logger.info("CarsServiceImpl | updateCarDetails | fuel id is not present, newly created {}", newFuelDetails.getId());

            }
        }

        // Save updated Car entity
        cars = carsRepoService.save(cars);
        logger.info("CarsServiceImpl | updateCarDetails | car details update successfully {}", cars);


        // Return updated DTO
        return new CarsDto(cars);
    }

    /**
     * Deletes a car and marks it as deleted by changing its status.
     *
     * @param id The ID of the car to delete.
     * @throws NotFoundException If the car is not found.
     */
    @Override
    public void deleteCarDetails(Long id) {
        logger.info("CarsServiceImpl | deleteCarDetails | car id {}", id);

        Cars cars = carsRepoService.findCarsById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.CAR_NOT_FOUND));

        cars.setRecordStatus(RecordStatus.DELETE);
        carsRepoService.save(cars);
        logger.info("CarsServiceImpl | deleteCarDetails | delete sucessfully car id {}", id);

    }
}

