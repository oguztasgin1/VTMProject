package com.vtm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequestDto {
    Long companyId;
    Long userId;
    String licensePlate;
    String chassisNumber;
    String label;
    String brand;
    String model;
    String modelYear;
}
