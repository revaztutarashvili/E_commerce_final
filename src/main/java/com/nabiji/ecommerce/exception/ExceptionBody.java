package com.nabiji.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionBody {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionBody(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}