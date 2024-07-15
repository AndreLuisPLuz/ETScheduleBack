package ets.schedule.Exceptions;

import org.springframework.http.HttpStatusCode;

import ets.schedule.data.responses.ErrorResponse;

public class ApplicationException extends RuntimeException {
    private HttpStatusCode statusCode;
    private ErrorResponse errorResponse;

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ApplicationException(
            HttpStatusCode statusCode,
            String message
    ) {
        super();
        this.statusCode = statusCode;
        this.errorResponse = new ErrorResponse(message);
    }

    public ApplicationException(
            HttpStatusCode statusCode,
            String message,
            Throwable cause
    ) {
        super(cause);
        this.statusCode = statusCode;
        this.errorResponse = new ErrorResponse(message);
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
