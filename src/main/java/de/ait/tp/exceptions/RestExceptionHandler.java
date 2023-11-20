package de.ait.tp.exceptions;

import de.ait.tp.dto.StandardResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<StandardResponseDto> handlerRestException(RestException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(StandardResponseDto.builder()
                        .message(e.getMessage())
                        .build());

    }

}
