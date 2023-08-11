package com.vtm.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneUpdateRequestDto {
    Long vehicleId;
    Long regionId;
    Long fleetId;
    Long groupId;
}
