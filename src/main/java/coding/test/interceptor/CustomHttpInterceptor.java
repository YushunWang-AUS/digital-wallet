package coding.test.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Component
public class CustomHttpInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomHttpInterceptor.class);

    private static final int MAX_PAYLOAD_LENGTH = 1000;

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws IOException {
//        String body = new String();
//
//        PrintWriter pw = response.getWriter();
//        pw.write(body);
//        pw.close();
////        final byte[] contentAsByteArray = ((ContentCachingResponseWrapper) response).getContentAsByteArray();
////        LOGGER.info("Request body:\n" + getContentAsString(contentAsByteArray, response.getCharacterEncoding()));
//        LOGGER.info("Request body:\n" + body);
    }

    private String getContentAsString(byte[] buf, String charsetName) {
        if (buf == null || buf.length == 0) {
            return "";
        }

        try {
            int length = Math.min(buf.length, MAX_PAYLOAD_LENGTH);

            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }

}
