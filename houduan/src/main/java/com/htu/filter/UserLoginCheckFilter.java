package com.htu.filter;

import com.alibaba.fastjson.JSON;

import com.htu.common.BaseContext;
import com.htu.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/abcd/*")
public class UserLoginCheckFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        String[] uris=new String[]{
                "/admin/login",
                "/admin/logout",
                "/business/login",
                "/business/logout",
                "/user/login",
                "/user/logout",
                "/photo/**"
        };
        boolean check = Check(uri,uris);
        if (check){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        Object userid=request.getSession().getAttribute("token");
        System.out.println(userid);
        if(userid!=null){
            BaseContext.setCurrentId((Long) userid);
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        response.getWriter().write(JSON.toJSONString( R.error("NOLOGIN")));
    }

    //检查方法
    private boolean Check(String uri,String[] uris) {
        for (String u:uris) {
            boolean bool=PATH_MATCHER.match(u,uri);
            if (bool){
                return true;
            }
        }
        return false;
    }
}
