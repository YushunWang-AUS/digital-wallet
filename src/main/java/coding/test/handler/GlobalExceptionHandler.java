package coding.test.handler;

import coding.test.exception.FpException;
import coding.test.model.FpError;
import coding.test.model.FpResponse;
import coding.test.model.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestControllerAdvice
class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    FpResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FpError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            FpError fpError = new FpError();
            fpError.setCode(fieldName + ".invalid");
            fpError.setMessage(fieldName + " " + fieldError.getDefaultMessage());
            errors.add(fpError);
        });
        return ResponseResult.fail(errors);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(FpException.class)
    FpResponse handleFpException(FpException ex) {
        log.error(ex.getMessage());
        FpError fpError = new FpError();
        fpError.setCode(ex.getCode());
        fpError.setMessage(ex.getMessage());
        return ResponseResult.fail(fpError);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    FpResponse handleExceptions(Exception ex) {
        ex.printStackTrace();
        FpError fpError = new FpError();
        fpError.setCode("internal.error");
        fpError.setMessage(ex.getMessage());
        return ResponseResult.fail(fpError);
    }
}

