package com.cars.controllers;

import com.cars.dto.CarsDto;
import com.cars.dto.CarsResponseDto;
import com.cars.dto.ResponseDto;
import com.cars.services.CarsService;
import com.cars.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing car-related operations.
 * Provides endpoints for CRUD operations and searching cars.
 */
@RestController
@RequestMapping("/rest/cars")
public class CarsController {

    private CarsService carsService;

    /**
     * Constructor for CarsController to inject CarsService.
     *
     * @param carsService the service layer for car operations
     */
    @Autowired
    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    /**
     * Fetches a paginated list of all cars with optional search string.
     * If searchString is provided, filters cars based on various fields like brand, model, year, price, fuel type, and transmission type.
     *
     * @param page         the page number to retrieve, default is 0
     * @param size         the number of cars per page, default is 10
     * @param searchString the search string to filter the cars (optional)
     * @return a response containing a paginated list of cars
     */
    @GetMapping
    public CarsResponseDto getAllCarsDetails(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "10") int size,
                                             @RequestParam(required = false) String searchString) {
        return carsService.getAllCarsDetails(page, size, searchString);
    }

    /**
     * Retrieves a specific car's details, including fuel information, based on the car's ID.
     *
     * @param id the ID of the car to retrieve
     * @return the details of the car along with its fuel details
     */
    @GetMapping("/{id}")
    public CarsDto getCarWithFuelDetailsById(@PathVariable(name = "id") Long id) {
        return carsService.getCarWithFuelDetailsById(id);
    }

    /**
     * Creates a new car with associated fuel details.
     * The car details are validated before being persisted.
     *
     * @param carsDto the car details to create
     * @return the created car details with fuel information
     */
    @PostMapping
    public CarsDto createCarWithFuelDetails(@RequestBody @Validated CarsDto carsDto) {
        return carsService.createNewCarWithFuelDetails(carsDto);
    }

    /**
     * Updates the details of an existing car identified by its ID.
     * The car details are validated before updating.
     *
     * @param id      the ID of the car to update
     * @param carsDto the updated car details
     * @return the updated car details with fuel information
     */
    @PutMapping("/{id}")
    public CarsDto updateCar(@PathVariable(name = "id") Long id,
                             @RequestBody @Validated CarsDto carsDto) {
        return carsService.updateCarDetails(id, carsDto);
    }

    /**
     * Deletes the car identified by the provided ID.
     *
     * @param id the ID of the car to delete
     * @return a response indicating the success or failure of the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseDto deleteCar(@PathVariable Long id) {
        carsService.deleteCarDetails(id);
        return new ResponseDto(Constants.SUCCESS, Constants.CARS_DELETE_SUCCESS);
    }

}
