package com.cars.domains;


import com.cars.utils.enums.FuelType;
import com.cars.utils.enums.RecordStatus;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "car_fuel_details")
@Where(clause = "rec_status<>'DELETE'")
public class CarFuelDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long fuelCapacity;

    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "transmission_type")
    private String transmissionType;

    @Column(name = "rec_status")
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus = RecordStatus.CREATE;


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

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }


    @Override
    public String toString() {
        return "CarFuelDetails{" +
                "id=" + id +
                ", fuelCapacity=" + fuelCapacity +
                ", fuelType=" + fuelType +
                ", transmissionType='" + transmissionType + '\'' +
                ", recordStatus=" + recordStatus +
                '}';
    }
}
