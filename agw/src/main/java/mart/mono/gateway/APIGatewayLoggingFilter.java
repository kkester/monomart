package mart.mono.gateway;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class APIGatewayLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            String requestURI = httpServletRequest.getRequestURI();
            if (!requestURI.contains("/actuator/prometheus")) {
                log.info("Request: {} : {}", httpServletRequest.getMethod(), requestURI);
            }
        } else {
            log.info("Request: {}", request.getRemoteAddr());
        }
        filterChain.doFilter(request, response);
    }
}
