package com.fadgiras.exos.exception;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private String errorCode;


    public ErrorDetails(Date timestamp, String message, String details, String errorCode) {
     super();
     this.timestamp = timestamp;
     this.message = message;
     this.details = details;
     this.errorCode = errorCode;
}

    public ErrorDetails(Date timestamp, String message, String details) {
         super();
         this.timestamp = timestamp;
         this.message = message;
         this.details = details;
    }

    public Date getTimestamp() {
         return timestamp;
    }

    public String getMessage() {
         return message;
    }

    public String getDetails() {
         return details;
    }

    public String getErrorCode() {
          return errorCode;
    }
}