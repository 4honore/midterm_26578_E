package com.medilink.medilink.HMS.entity;

import lombok.Data;

/**
 * DTO for Location to handle the flat JSON request payloads
 */
@Data
public class LocationDTO {
    private String provinceCode;
    private String provinceName;
    private String district;
    private String sector;
    private String cell;
    private String village;
    private String hospitalName;
}
