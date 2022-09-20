package com.fadgiras.exos.exception;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String details;
    private String errorCode;


    public ErrorDetails(Date timestamp, String errorCode, String details) {
         super();
         this.timestamp = timestamp;
         this.errorCode = errorCode;
         this.details = details;
    }

    public Date getTimestamp() {
         return timestamp;
    }

    public String getDetails() {
         return details;
    }

    public String getErrorCode() {
          return errorCode;
    }
}