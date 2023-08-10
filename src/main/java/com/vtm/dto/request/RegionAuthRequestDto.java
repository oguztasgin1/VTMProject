package com.vtm.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegionAuthRequestDto {
    Long vehicleId;
    Long regionId;
}
