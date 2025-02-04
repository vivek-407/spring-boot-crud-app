package com.cars.dto;

import java.util.List;

public class CarsResponseDto {
    private long totalRecords;
    private long filteredRecords;
    private List<CarsDto> carsData;

    public CarsResponseDto(long totalRecords, long filteredRecords, List<CarsDto> carsData) {
        this.totalRecords = totalRecords;
        this.filteredRecords = filteredRecords;
        this.carsData = carsData;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getFilteredRecords() {
        return filteredRecords;
    }

    public void setFilteredRecords(long filteredRecords) {
        this.filteredRecords = filteredRecords;
    }

    public List<CarsDto> getCarsData() {
        return carsData;
    }

    public void setCarsData(List<CarsDto> carsData) {
        this.carsData = carsData;
    }
}