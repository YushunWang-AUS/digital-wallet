package coding.test.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseResult {

    public static FpResponse success(Object result) {
        FpResponse fpResponse = new FpResponse();
        fpResponse.setSuccess(true);
        fpResponse.setResult(result);
        return fpResponse;
    }

    public static FpResponse fail(List<FpError> fpErrors) {
        FpResponse fpResponse = new FpResponse();
        fpResponse.setSuccess(false);
        fpResponse.setResult(fpErrors);
        return fpResponse;
    }

    public static FpResponse fail(FpError fpError) {
        List fpErrors = new ArrayList();
        fpErrors.add(fpError);
        return fail(fpErrors);
    }

}
