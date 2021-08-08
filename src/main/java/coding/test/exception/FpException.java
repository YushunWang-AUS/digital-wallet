package coding.test.exception;

import coding.test.model.FpErrorDTO;

public class FpException extends RuntimeException {

    private String code = "internal.error";
    private String message;

    public FpException(String message) {
        this.message = message;
    }

    public FpException(FpErrorDTO fpError) {
        this.code = fpError.getCode();
        this.message = fpError.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
