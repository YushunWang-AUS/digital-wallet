package coding.test.model;

import coding.test.exception.FpException;

public class FpErrorDTO {

    private String code;
    private String message;

    public FpErrorDTO() {
    }

    public FpErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public FpErrorDTO(FpException fpException) {
        this.code = fpException.getCode();
        this.message = fpException.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
