package com.example.fintech_java.dto;

import lombok.Data;
import java.util.List;

@Data
public class HuobiResponse {
    private List<HuobiTickerDto> data;
}


