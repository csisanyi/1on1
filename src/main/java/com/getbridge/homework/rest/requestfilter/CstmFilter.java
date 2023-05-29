package com.getbridge.homework.rest.requestfilter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class CstmFilter implements Filter {
    private ServletContext servletContext;

    public CstmFilter(){
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(   ServletRequest req,
                            ServletResponse res,
                            FilterChain filterChain)
            throws IOException, ServletException {

        CustomHttpServletRequestWrapper httpReq    = new CustomHttpServletRequestWrapper((HttpServletRequest)req);
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






