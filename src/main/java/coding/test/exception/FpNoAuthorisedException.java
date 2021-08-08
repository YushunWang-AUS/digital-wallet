package coding.test.exception;

public class FpNoAuthorisedException extends FpException {

    private String code = "no.authorisation";
    private String message;

    public FpNoAuthorisedException(String message) {
        super(message);
        this.message = message;
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
