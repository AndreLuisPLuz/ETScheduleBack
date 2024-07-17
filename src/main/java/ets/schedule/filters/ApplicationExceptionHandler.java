package ets.schedule.filters;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.responses.ErrorResponse;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ApplicationException.class })
    protected ResponseEntity<ErrorResponse> handleConflict(
            ApplicationException ex,
            WebRequest request
    ) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getErrorResponse());
    }
}
