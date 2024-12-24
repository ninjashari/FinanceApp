package com.finance.app.model;

import lombok.Data;

@Data
public class Status {
    private Integer code;
    private String status;
    private String message;
    private String token;
}
