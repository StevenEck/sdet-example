package com.sdet.example.model;

import com.sdet.example.coverage.Generated;

import java.time.Instant;

public class ApiResponse {

    private Instant instant;
    private String message;

    public ApiResponse(){
        instant = Instant.now(); //timestamp in UTC due to databases utilizing UTC for date time purposes
        message = "Welcome to the machine";

    }

    //setters
    @Generated
    public void setMessage(){
        this.message = message;
    }

    @Generated
    public void setInstant(){
        this.instant = instant;
    }

    // getters
    public String getMessage() {
        return message;
    }

    public Instant getInstant() {
        return instant;
    }

}
