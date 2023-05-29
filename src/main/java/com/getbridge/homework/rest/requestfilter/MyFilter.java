package com.getbridge.homework.rest.requestfilter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.logging.*;

@Component
public class MyFilter implements javax.servlet.Filter {
    private ServletContext servletContext;
    private Logger log;

    public MyFilter(){
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        log = Logger.getLogger(MyFilter.class.getName());
    }

    public void doFilter(   ServletRequest req,
                            ServletResponse res,
                            FilterChain filterChain)
            throws IOException, ServletException {

        MyServletRequestWrapper httpReq    = new MyServletRequestWrapper((HttpServletRequest)req);
        HttpServletResponse    httpRes   = (HttpServletResponse)res;

        HttpSession session = httpReq.getSession();
        if(null == session.getAttribute("globalId")){
            filterChain.doFilter(httpReq, httpRes);
        } else {
            String userid = session.getAttribute("globalId").toString();
            httpReq.addHeader("X-AUTHENTICATED-USER", userid);
            filterChain.doFilter(httpReq, httpRes);
        }

    }

    public void destroy(){
    }

}






