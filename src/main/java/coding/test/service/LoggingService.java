package coding.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.ResponseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingService {

    private final Logger log = LoggerFactory.getLogger(LoggingService.class);

    public static final String SEPARATOR = "==============================================================================================";

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Map<String, String> parameters = buildParametersMap(httpServletRequest);
            stringBuilder.append("\n" + SEPARATOR);
            stringBuilder.append("\nREQUEST: ");
            stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
            stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
            stringBuilder.append("\nheaders=[").append(buildHeadersMap(httpServletRequest)).append("] ");

            if (!parameters.isEmpty()) {
                stringBuilder.append("\nparameters=[").append(parameters).append("] ");
            }
            if (body != null) {
                stringBuilder.append("\n" + new ObjectMapper().writeValueAsString(body));
            }
            stringBuilder.append("\n" + SEPARATOR);

            log.info(stringBuilder.toString());
        } catch (JsonProcessingException e) {
            log.warn("Parse json request body failed. reason:" + e.getMessage());
        }
    }

    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("\n" + SEPARATOR);
            stringBuilder.append("\nRESPONSE ");
            stringBuilder.append("status=[").append(httpServletResponse.getStatus()).append("] ");
            stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
            stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
            stringBuilder.append("\nresponseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
            stringBuilder.append("\n").append(new ObjectMapper().writeValueAsString(body));
            stringBuilder.append("\n" + SEPARATOR);
            log.info(stringBuilder.toString());
        } catch (JsonProcessingException e) {
            log.warn("Parse json request body failed. reason:" + e.getMessage());
        }
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}

