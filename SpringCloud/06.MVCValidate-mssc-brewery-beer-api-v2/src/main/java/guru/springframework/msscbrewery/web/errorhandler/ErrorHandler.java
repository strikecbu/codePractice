package guru.springframework.msscbrewery.web.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> validateErrorHandler(MethodArgumentNotValidException ex) {
        List<String> collect = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Field: %s, Error Message: %s%n",
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.BAD_REQUEST);
    }
}
