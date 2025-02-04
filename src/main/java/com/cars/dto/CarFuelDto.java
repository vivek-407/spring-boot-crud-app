package com.cars.dto;

import com.cars.domains.CarFuelDetails;
import com.cars.utils.enums.FuelType;


public class CarFuelDto {

    private Long id;
    private Long fuelCapacity;
    private FuelType fuelType;
    private String transmissionType;

    public CarFuelDto() {

    }

    public CarFuelDto(CarFuelDetails carFuelDetails) {
        this.id = carFuelDetails.getId();
        this.fuelCapacity = carFuelDetails.getFuelCapacity();
        this.fuelType = carFuelDetails.getFuelType();
        this.transmissionType = carFuelDetails.getTransmissionType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Long fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Override
    public String toString() {
        return "CarFuelDto{" +
                "id=" + id +
                ", fuelCapacity=" + fuelCapacity +
                ", fuelType=" + fuelType +
                ", transmissionType='" + transmissionType + '\'' +
                '}';
    }

    public CarFuelDetails createEntity() {
        CarFuelDetails carFuelDetails = new CarFuelDetails();
        carFuelDetails.setFuelCapacity(getFuelCapacity());
        carFuelDetails.setFuelType(getFuelType());
        carFuelDetails.setTransmissionType(getTransmissionType());
        return carFuelDetails;

    }
}
