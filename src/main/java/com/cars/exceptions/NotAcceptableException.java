package com.cars.exceptions;

public class NotAcceptableException extends BaseException{
    public NotAcceptableException(NotAcceptableException.NotAcceptable notAcceptable) {
        super(404, notAcceptable.getStatusCode(), notAcceptable.getErrorMessage(),
                notAcceptable.getDeveloperMessage());
    }

    public enum NotAcceptable {
        CAR_ALREADY_EXIST("4060001", "Car Already Exists With Same Details.", "Car Already Exists With Same Details.");
        private String statusCode;
        private String errorMessage;
        private String developerMessage;

        private NotAcceptable(String statusCode, String errorMessage, String developerMessage) {
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
