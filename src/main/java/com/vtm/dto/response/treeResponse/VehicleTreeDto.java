package com.vtm.dto.response.treeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleTreeDto {
    private String regionName;
    private List<FleetDto> fleets;
}
