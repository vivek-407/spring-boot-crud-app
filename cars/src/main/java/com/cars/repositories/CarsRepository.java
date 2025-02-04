package com.cars.repositories;

import com.cars.domains.Cars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {


    @Query("SELECT car FROM Cars car LEFT JOIN FETCH car.carFuelDetails WHERE car.id = :id")
    Optional<Cars> findCarWithFuelDetails(@Param("id") Long id);

    @Query(value = "SELECT car FROM Cars car " +
            "LEFT JOIN FETCH car.carFuelDetails fuel " +
            "WHERE LOWER(car.brand) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(car.model) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(CAST(car.year AS string)) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(CAST(car.price AS string)) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(fuel.fuelType) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(fuel.transmissionType) LIKE LOWER(CONCAT('%', :searchString, '%')) ", countQuery = "SELECT COUNT(car) FROM Cars car " +
            "LEFT JOIN car.carFuelDetails fuel " +
            "WHERE (:searchString IS NULL OR LOWER(car.brand) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(car.model) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(CAST(car.year AS string)) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(CAST(car.price AS string)) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(fuel.fuelType) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(fuel.transmissionType) LIKE LOWER(CONCAT('%', :searchString, '%')))")
    Page<Cars> getAllCarsWithSearchAndPagination(Pageable pageable,
                                                 @Param("searchString") String searchString);

    Cars findCarByBrandAndModelAndYearAndPrice(String brand, String model, int year, Long price);
}
