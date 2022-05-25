package com.pmp.nwms.web.filter;

import com.pmp.nwms.web.wrapper.HttpServletDisableSessionRequestWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DisableSessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletDisableSessionRequestWrapper wrapper = new HttpServletDisableSessionRequestWrapper((HttpServletRequest) req);
        chain.doFilter(wrapper, resp);
    }

    @Override
    public void destroy() {

    }
}
