package rs.customer.service.coreCustomer.framework;

import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class InboundRequestMiddleware implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("x-request-id", UUID.randomUUID().toString());
        request.setAttribute("x-request-received-timestamp", System.currentTimeMillis());
        chain.doFilter(request, response);
    }
}
