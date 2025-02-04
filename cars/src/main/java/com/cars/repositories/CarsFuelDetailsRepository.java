package com.cars.repositories;

import com.cars.domains.CarFuelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsFuelDetailsRepository extends JpaRepository<CarFuelDetails, Long> {
}
