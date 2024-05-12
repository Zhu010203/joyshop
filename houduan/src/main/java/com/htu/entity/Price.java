package com.htu.entity;

import lombok.Data;

@Data
public class Price {
    private Long type;

    private Long bid;

    private Double low;

    private Double high;
}
