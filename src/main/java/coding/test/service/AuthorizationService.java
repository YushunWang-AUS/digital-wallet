package coding.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class AuthorizationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String HMAC_SHA256 = "HMAC_SHA256";

    @Value("${auth.hmac.key}")
    private String HMAC_KEY;

    @Value("${auth.required.action}")
    private String AUTH_REQ_ACTION;


    public boolean isValidationTarget(HttpServletRequest httpRequest, String resourcePath) {
        if (AUTH_REQ_ACTION == null || AUTH_REQ_ACTION.length() == 0) return false;
        String[] actions = AUTH_REQ_ACTION.split(",");

        // TODO: will support * masked path later
        if (Arrays.asList(actions).contains(resourcePath)) {
            return true;
        }
        return false;
    }

    public boolean validateToken(HttpServletRequest request) {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(auth)) return false;

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replaceFirst(HMAC_SHA256, "").trim();
        log.info(String.format("Get token: %s", token));
        if (!StringUtils.hasText(token)) return false;

        try {
            // TODO: hmac_value should be calculated by some value here, eg. using partnerCode "alibaba"
            String partnerCode = "alibaba";
            String hashedValue = encode(HMAC_KEY, partnerCode);
            log.info(String.format("Calculated hashedValue:%s", token));

            // TODO: use given hashedValue as the coding test
            hashedValue = "7252322798a42f4005596e78ab602e300acf51c13628bb4bff6b5aa929cadec4";
            if (token.equals(hashedValue)) return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("Calculate token failed. token:%s", token));
            return false;
        }

        return false;

    }

    private String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Hex.encode(sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8))).toString();
    }

}
