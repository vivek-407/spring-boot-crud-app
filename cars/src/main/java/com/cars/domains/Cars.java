package com.cars.domains;

import com.cars.utils.enums.RecordStatus;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Where(clause = "rec_status<>'DELETE'")
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CarFuelDetails.class)
    @JoinColumn(name = "car_fuel_details_id", insertable = false, updatable = false)
    private CarFuelDetails carFuelDetails;

    @Column(name = "car_fuel_details_id")
    private Long carFuelDetailsId;

    @Column(name = "rec_status")
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus = RecordStatus.CREATE;


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

    public CarFuelDetails getCarFuelDetails() {
        return carFuelDetails;
    }

    public void setCarFuelDetails(CarFuelDetails carFuelDetails) {
        this.carFuelDetails = carFuelDetails;
    }

    public Long getCarFuelDetailsId() {
        return carFuelDetailsId;
    }

    public void setCarFuelDetailsId(Long carFuelDetailsId) {
        this.carFuelDetailsId = carFuelDetailsId;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }


    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", carFuelDetails=" + carFuelDetails +
                ", carFuelDetailsId=" + carFuelDetailsId +
                ", recordStatus=" + recordStatus +
                '}';
    }
}
