package coding.test.interceptor;

import coding.test.exception.FpNoAuthorisedException;
import coding.test.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthorizationService authorizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String resourcePath = new UrlPathHelper().getPathWithinApplication(request);

        if (authorizationService.isValidationTarget(request, resourcePath)) {
            if (!authorizationService.validateToken(request)) {
                throw new FpNoAuthorisedException("Sorry, you are not authorized");
            }

        }
        return true;

    }
}