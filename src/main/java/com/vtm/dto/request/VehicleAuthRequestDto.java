package com.vtm.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAuthRequestDto {
    Long companyId;
    Long userId; //Hangi user' ı yetkilendireceksem onun userId'si.
    Long vehicleId;
}
