package com.vtm.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleAuthResponseDto {
    Long licensePlate;
    Long chassisNumber;
    String label;
    String brand;
    String model;
    String modelYear;
    String companyName;
    String userName;
}
