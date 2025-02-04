package com.cars.services.impl;

import com.cars.domains.CarFuelDetails;
import com.cars.domains.Cars;
import com.cars.dto.CarsDto;
import com.cars.exceptions.NotAcceptableException;
import com.cars.exceptions.NotFoundException;
import com.cars.services.CarsRepoService;
import com.cars.utils.enums.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarsServiceImplTest {

    @Mock
    private CarsRepoService carsRepoService;

    @InjectMocks
    private CarsServiceImpl carsServiceImpl;

    private Cars existingCar;
    private CarsDto carsDto;

    private CarFuelDetails carFuelDetails;

    @BeforeEach
    void setUp() {
        existingCar = new Cars();
        existingCar.setId(1L);
        existingCar.setBrand("Toyota");
        existingCar.setModel("Camry");
        existingCar.setYear(2023);
        existingCar.setPrice(25000L);

        carsDto = new CarsDto();
        carsDto.setBrand("Toyota");
        carsDto.setModel("Camry");
        carsDto.setYear(2023);
        carsDto.setPrice(25000L);

        carFuelDetails = new CarFuelDetails();
        carFuelDetails.setId(1L);
        carFuelDetails.setFuelType(FuelType.PETROL);
        carFuelDetails.setFuelCapacity(50L);

    }

    @Test
    void validateCarExist_ShouldPass_WhenNoExistingCar() {
        // Mock repository to return null (no existing car found)
        when(carsRepoService.findCarByBrandAndModelAndYearAndPrice(anyString(), anyString(), anyInt(), anyLong()))
                .thenReturn(null);

        assertDoesNotThrow(() -> carsServiceImpl.validateCarExist(null, carsDto));

        verify(carsRepoService, times(1))
                .findCarByBrandAndModelAndYearAndPrice("Toyota", "Camry", 2023, 25000L);
    }

    @Test
    void validateCarExist_ShouldThrowException_WhenCarAlreadyExistsForCreate() {
        // Mock repository to return an existing car
        when(carsRepoService.findCarByBrandAndModelAndYearAndPrice(anyString(), anyString(), anyInt(), anyLong()))
                .thenReturn(existingCar);

        assertThrows(NotAcceptableException.class, () -> carsServiceImpl.validateCarExist(null, carsDto));

        verify(carsRepoService, times(1))
                .findCarByBrandAndModelAndYearAndPrice("Toyota", "Camry", 2023, 25000L);
    }

    @Test
    void validateCarExist_ShouldPass_WhenUpdatingSameCar() {
        when(carsRepoService.findCarByBrandAndModelAndYearAndPrice(anyString(), anyString(), anyInt(), anyLong()))
                .thenReturn(existingCar);

        assertDoesNotThrow(() -> carsServiceImpl.validateCarExist(1L, carsDto));

        verify(carsRepoService, times(1))
                .findCarByBrandAndModelAndYearAndPrice("Toyota", "Camry", 2023, 25000L);
    }

    @Test
    void validateCarExist_ShouldThrowException_WhenUpdatingToDuplicateCar() {
        Cars anotherCar = new Cars();
        anotherCar.setId(2L); // Different ID
        anotherCar.setBrand("Toyota");
        anotherCar.setModel("Camry");
        anotherCar.setYear(2023);
        anotherCar.setPrice(25000L);

        when(carsRepoService.findCarByBrandAndModelAndYearAndPrice(anyString(), anyString(), anyInt(), anyLong()))
                .thenReturn(anotherCar);

        assertThrows(NotAcceptableException.class, () -> carsServiceImpl.validateCarExist(1L, carsDto));

        verify(carsRepoService, times(1))
                .findCarByBrandAndModelAndYearAndPrice("Toyota", "Camry", 2023, 25000L);
    }

    @Test
    void getCarWithFuelDetailsById_ShouldThrowNotFoundException_WhenCarNotFound() {
        // Mocking repository to return empty (car not found)
        when(carsRepoService.findCarWithFuelDetails(anyLong())).thenReturn(Optional.empty());

        // Assertion
        assertThrows(NotFoundException.class, () -> carsServiceImpl.getCarWithFuelDetailsById(1L));

        // verify
        verify(carsRepoService, times(1)).findCarWithFuelDetails(1L);
    }

    @Test
    void updateCarDetails_ShouldThrowNotFoundException_WhenCarNotFound() {
        when(carsRepoService.findCarsById(anyLong())).thenReturn(Optional.empty());

        // assertion
        assertThrows(NotFoundException.class, () -> carsServiceImpl.updateCarDetails(1L, carsDto));

        // verify
        verify(carsRepoService, times(1)).findCarsById(1L);
    }

    @Test
    void deleteCarDetails_ShouldThrowNotFoundException_WhenCarNotFound() {
        // Mocking repository to return empty (car not found)
        when(carsRepoService.findCarsById(anyLong())).thenReturn(Optional.empty());

        // Asserting that NotFoundException is thrown
        assertThrows(NotFoundException.class, () -> carsServiceImpl.deleteCarDetails(1L));

        //verify
        verify(carsRepoService, times(1)).findCarsById(1L);
    }

    /**
     * Test successful car deletion.
     */
    @Test
    void deleteCarDetails_ShouldDeleteCarSuccessfully() {
        when(carsRepoService.findCarsById(1L)).thenReturn(Optional.of(existingCar));

        // Execute
        carsServiceImpl.deleteCarDetails(1L);

        // Verify deletion (status change)
        verify(carsRepoService, times(1)).save(any());
    }

    /**
     * Test successful car update.
     */
    @Test
    void updateCarDetails_ShouldUpdateCarSuccessfully() {
        when(carsRepoService.findCarsById(1L)).thenReturn(Optional.of(existingCar));
        when(carsRepoService.save(any())).thenReturn(existingCar);

        // Execute
        CarsDto updatedDto = new CarsDto();
        updatedDto.setBrand("Honda");
        updatedDto.setModel("Accord");
        updatedDto.setYear(2024);
        updatedDto.setPrice(28000L);

        CarsDto result = carsServiceImpl.updateCarDetails(1L, updatedDto);

        // Assertions
        assertNotNull(result);
        assertEquals("Honda", result.getBrand());
        assertEquals("Accord", result.getModel());

        // Verify update calls
        verify(carsRepoService, times(1)).findCarsById(1L);
        verify(carsRepoService, times(1)).save(any());
    }

    /**
     * Test successful car retrieval by ID.
     */
    @Test
    void getCarWithFuelDetailsById_ShouldReturnCarDetails() {
        when(carsRepoService.findCarWithFuelDetails(1L)).thenReturn(Optional.of(existingCar));

        // Execute
        CarsDto result = carsServiceImpl.getCarWithFuelDetailsById(1L);

        // Assertions
        assertNotNull(result);
        assertEquals(existingCar.getBrand(), result.getBrand());

        verify(carsRepoService, times(1)).findCarWithFuelDetails(1L);
    }

    /**
     * Test successful car creation.
     */
    @Test
    void createNewCarWithFuelDetails_ShouldCreateCarSuccessfully() {
        // Mock: Car does not exist
        when(carsRepoService.findCarByBrandAndModelAndYearAndPrice(any(), any(), anyInt(), anyLong()))
                .thenReturn(null);
        when(carsRepoService.save(any())).thenReturn(existingCar);

        // Execute
        CarsDto result = carsServiceImpl.createNewCarWithFuelDetails(carsDto);

        // Assertions
        assertNotNull(result);
        assertEquals(existingCar.getBrand(), result.getBrand());
        assertEquals(existingCar.getModel(), result.getModel());
        assertEquals(existingCar.getYear(), result.getYear());

        // Verify save method was called
        verify(carsRepoService, times(1)).save(any());
    }
}
