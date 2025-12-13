package com.example.community_education.Tool.security;


import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * token验证，和权限添加
 * */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        //如果请求头中没有Authorization信息则放行
        if(token == null){
            chain.doFilter(request,response);
            return;
        }
        logger.info("请求" + request.getRequestURI() + "携带的token值" + token);
        // 如果请求头中有token，则进行解析，并且设置认证信息
        getAuthentication(token);

        super.doFilterInternal(request, response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        UsernamePasswordAuthenticationToken authentication = null;
        String token = tokenHeader.replace("Bearer ", "");

        String username = TokenUtil.getUsername(token);
        Set<GrantedAuthority> role = TokenUtil.getRole(token);
        if (username != null ){
            authentication = new UsernamePasswordAuthenticationToken(username, null,
                    role);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return null;
    }
}
