package coding.test.exception;

import coding.test.model.FpError;

public class FpException extends Exception {

    private String code;
    private String message;

    public FpException(String message) {
        this.message = message;
    }

    public FpException(FpError fpError) {
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
