package com.cars.dto;

public class ResponseDto {

    private String type;

    private String message;

    public ResponseDto(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
