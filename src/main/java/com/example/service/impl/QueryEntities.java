package com.example.service.impl;

import lombok.Data;

@Data
public class QueryEntities {

    private Integer price;
    private String brand;

    private boolean gaming;
    private boolean camera;
    private boolean battery;
    private boolean maxBattery;
    private boolean maxRam;
    private boolean maxCamera;
    private boolean maxHz;
}