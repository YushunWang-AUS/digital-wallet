package coding.test.handler;

import coding.test.exception.FpException;
import coding.test.exception.FpNoAuthorisedException;
import coding.test.model.FpErrorDTO;
import coding.test.model.FpResponseDTO;
import coding.test.model.FpResponseResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    FpResponseDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FpErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            FpErrorDTO fpError = new FpErrorDTO();
            fpError.setCode(fieldName + ".invalid");
            fpError.setMessage(fieldName + " " + fieldError.getDefaultMessage());
            errors.add(fpError);
        });
        return FpResponseResultDTO.fail(errors);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(FpException.class)
    FpResponseDTO handleFpException(FpException ex) {
        log.error(ex.getMessage());
        return FpResponseResultDTO.fail(new FpErrorDTO(ex));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(FpNoAuthorisedException.class)
    FpResponseDTO handleFpNoAuthorisedException(FpNoAuthorisedException ex) {
        return FpResponseResultDTO.fail(new FpErrorDTO(ex));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    FpResponseDTO handleExceptions(Exception ex) {
        ex.printStackTrace();
        return FpResponseResultDTO.fail(new FpErrorDTO("internal.error", ex.getMessage()));
    }
}

