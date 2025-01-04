package kamzy.io.FutoLocate.Utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Resource not found: " + ex.getMessage());
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleGeneralError(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }


}
