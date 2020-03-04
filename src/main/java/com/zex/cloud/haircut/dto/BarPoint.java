package com.zex.cloud.haircut.dto;

import lombok.Data;

import java.util.List;

@Data
public class BarPoint {

    private String name;

    private List<BrokenLinePoint> values;

}
