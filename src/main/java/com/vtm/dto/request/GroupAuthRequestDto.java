package com.vtm.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupAuthRequestDto {
    Long vehicleId;
    Long groupId;

}
