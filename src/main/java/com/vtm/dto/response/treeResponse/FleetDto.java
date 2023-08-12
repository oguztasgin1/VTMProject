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
public class FleetDto {
    private String fleetName;
    private List<GroupDto> groups;
}
