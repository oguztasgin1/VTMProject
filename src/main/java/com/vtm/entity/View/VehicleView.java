package com.vtm.entity.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleView {
    String licensePlate;
    String chassisNumber;
    String label;
    String brand;
    String model;
    String modelYear;
}
