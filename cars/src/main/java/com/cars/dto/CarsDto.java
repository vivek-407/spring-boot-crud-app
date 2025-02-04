package com.cars.dto;


import com.cars.domains.Cars;

public class CarsDto{

    private Long id;
    private String brand;
    private String model;
    private int year;
    private Long price;

    private CarFuelDto carFuelDto;

    public CarsDto(){}


    public CarsDto(Cars cars) {
        this.id = cars.getId();
        this.brand = cars.getBrand();
        this.model = cars.getModel();
        this.year = cars.getYear();
        this.price = cars.getPrice();
        this.carFuelDto = cars.getCarFuelDetails() != null ? new CarFuelDto(cars.getCarFuelDetails()) : null;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CarFuelDto getCarFuelDto() {
        return carFuelDto;
    }

    public void setCarFuelDto(CarFuelDto carFuelDto) {
        this.carFuelDto = carFuelDto;
    }

    public Cars createEntity() {
        Cars cars = new Cars();
        cars.setBrand(getBrand());
        cars.setModel(getModel());
        cars.setYear(getYear());
        cars.setPrice(getPrice());
        return cars;
    }
}
