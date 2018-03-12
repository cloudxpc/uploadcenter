package com.uutic.uploadcenter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**").allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String corsMethod = request.getHeader("access-control-request-method");
                String corsHeaders = request.getHeader("access-control-request-headers");
                if (corsMethod != null && corsHeaders != null) {
                    log.info("Preflight request:");
                    log.info("access-control-request-method: " + corsMethod);
                    log.info("access-control-request-headers: " + corsHeaders);
                    return super.preHandle(request, response, handler);
                }

//                String authorization = request.getHeader("Authorization");
//                if (authorization == null || authorization.isEmpty()) {
//                    throw new TokenNotFoundException("Unauthorized");
//                }
//                if (!authorization.startsWith("Bearer ")) {
//                    throw new InvalidTokenException("Invalid authorization header");
//                }
//                String token = authorization.substring(7);
//                Map<String, String> claims = JwtUtil.decode(token);
//                if (!claims.containsKey("user_id") || !claims.containsKey("user_type"))
//                    throw new InvalidTokenException("Cannot find user information in Token");
//                request.setAttribute("user_id", claims.get("user_id"));
//                request.setAttribute("user_type", claims.get("user_type"));

                return super.preHandle(request, response, handler);
            }
        }).addPathPatterns("/**");
    }
}
