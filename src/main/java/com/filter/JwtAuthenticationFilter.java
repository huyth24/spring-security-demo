package com.filter;

import com.config.jwt.JwtProvider;
import com.entities.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.response.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements Filter {

    private ObjectMapper mapper = new ObjectMapper();

    @Value("${url.common}")
    private String urlCommon;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        List<String> lstUrlCommon = Arrays.asList(urlCommon.split(","));
        if (lstUrlCommon.stream().anyMatch(el -> el.equals(url))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser instanceof CustomUserDetail && isRequestValid(request, (CustomUserDetail) currentUser)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        response.setContentType("application/json");
        response.getOutputStream().print(mapper.writeValueAsString(new ResponseData("token has expired", null)));
    }

    public boolean isRequestValid(HttpServletRequest request, CustomUserDetail userDetail) {
        JwtProvider jwtProvider = new JwtProvider();
        String token = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(token) && jwtProvider.validationToken(token)) {
            Long userId = jwtProvider.getUserIdFromToken(token);
            if (userId == userDetail.getUserId()) {
                return true;
            }
            return false;
        }
        return false;
    }
}
