package com.alex.safetynet.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor

public class FloodDto {
    private String address;
    private List<FloodPersonDto> foyers;

    public FloodDto(List<FloodPersonDto> foyers) {
        this.foyers = foyers;
    }
}
