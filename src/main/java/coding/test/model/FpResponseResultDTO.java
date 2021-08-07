package coding.test.model;

import java.util.ArrayList;
import java.util.List;

public class FpResponseResultDTO {

    public static FpResponseDTO success() {
        FpResponseDTO fpResponse = new FpResponseDTO();
        fpResponse.setSuccess(true);
        return fpResponse;
    }

    public static FpResponseDTO success(Object result) {
        FpResponseDTO fpResponse = new FpResponseDTO();
        fpResponse.setSuccess(true);
        fpResponse.setResult(result);
        return fpResponse;
    }

    public static FpResponseDTO fail(List<FpErrorDTO> fpErrors) {
        FpResponseDTO fpResponse = new FpResponseDTO();
        fpResponse.setSuccess(false);
        fpResponse.setResult(fpErrors);
        return fpResponse;
    }

    public static FpResponseDTO fail(FpErrorDTO fpError) {
        List fpErrors = new ArrayList();
        fpErrors.add(fpError);
        return fail(fpErrors);
    }

}
