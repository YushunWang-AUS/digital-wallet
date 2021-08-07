package coding.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class FpResponseDTO {

    private Boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
