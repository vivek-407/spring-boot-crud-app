package com.cars.exceptions;

public class NotFoundException extends BaseException {


    public NotFoundException(NotFound notFound) {
        super(404, notFound.getStatusCode(), notFound.getErrorMessage(),
                notFound.getDeveloperMessage());
    }

    public enum NotFound {
        CAR_NOT_FOUND("4040001", "Car Not Found.", "Car Not Found, Please pass the valid Id."),
        FUEL_NOT_FOUND("404002", "Fuel Details Not Found.", "Fuel Details Not Found");
        private String statusCode;
        private String errorMessage;
        private String developerMessage;

        private NotFound(String statusCode, String errorMessage, String developerMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
            this.developerMessage = developerMessage;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getDeveloperMessage() {
            return developerMessage;
        }

        public void setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
        }
    }
}
