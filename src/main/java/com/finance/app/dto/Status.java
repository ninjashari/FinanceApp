package com.finance.app.dto;

import lombok.Data;

@Data
public class Status {
    private Integer code;
    private String status;
    private String message;
    private String token;
}
