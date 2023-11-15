package ru.ekrem.financialliteracy.handler;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ekrem.financialliteracy.dto.ResponseData;
import ru.ekrem.financialliteracy.handler.exception.JwtException;
import ru.ekrem.financialliteracy.handler.exception.NotFoundException;
import ru.ekrem.financialliteracy.handler.exception.RegistrationProcessException;
import ru.ekrem.financialliteracy.handler.exception.SqlOperationException;

@RestControllerAdvice
public class ExceptionApiHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseData> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseData.<String>builder()
                        .success(false)
                        .data(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(RegistrationProcessException.class)
    public ResponseEntity<ResponseData> registrationException(RegistrationProcessException exception) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ResponseData.<String>builder()
                        .success(false)
                        .data(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(SqlOperationException.class)
    public ResponseEntity<ResponseData> sqlOperationException(SqlOperationException exception) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ResponseData.<String>builder()
                        .success(false)
                        .data(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {JwtException.class})
    @ResponseBody
    public ResponseEntity<ResponseData> jwtTimeOutException(JwtException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseData.<String>builder()
                        .success(false)
                        .data(exception.getMessage())
                        .build());
    }
}
