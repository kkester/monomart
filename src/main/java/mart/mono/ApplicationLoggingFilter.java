package mart.mono;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ApplicationLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            log.info("Request: {} : {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        } else {
            log.info("Request: {}", request.getRemoteAddr());
        }
        filterChain.doFilter(request, response);
    }
}
