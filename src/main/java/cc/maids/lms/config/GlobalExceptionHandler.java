
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status.value()), ex.getMessage());
        problemDetail.setType(URI.create("about:blank")); // You can set appropriate type URI
        problemDetail.setTitle("Validation Error");
        problemDetail.setStatus(status.value());
        problemDetail.setDetail("Validation failed. Check 'errors' field for details.");
        problemDetail.setInstance(URI.create(request.getDescription(false)));

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        problemDetail.setProperties(Collections.singletonMap("errors", errors));

        return new ResponseEntity<>(problemDetail, headers, status);
    }


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex, WebRequest request) {
        ProblemDetail problemDetail;
        List<String> errors = new ArrayList<>();

        if (ex instanceof BadCredentialsException) {
            problemDetail = ProblemDetail.forStatusAndDetail(UNAUTHORIZED, "Authentication Failure: Bad Credentials");
        } else if (ex instanceof AccessDeniedException) {
            problemDetail = ProblemDetail.forStatusAndDetail(FORBIDDEN, "Authentication Failure: Access Denied");
        } else {
            problemDetail = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
        errors.add(ex.getMessage());
        problemDetail.setProperties(Collections.singletonMap("errors", errors));

        return problemDetail;
    }


}
