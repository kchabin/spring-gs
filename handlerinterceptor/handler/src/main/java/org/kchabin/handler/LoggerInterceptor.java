package org.kchabin.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.Enumeration;

@Component
public class LoggerInterceptor  implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("[preHandle] [" + request + "]" + "[" + request.getMethod()
        + "]" + request.getRequestURI() + getParameters(request));

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        log.info("[postHandle] [" + request + "]");
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {

        if (ex != null) {
            ex.printStackTrace(); //예외 스택 트레이스 출력
        }

        log.info("[afterCompletion] [" + request + "] [exception: " + ex + "]");
    }

    private String getParameters(HttpServletRequest request) {

        StringBuilder posted = new StringBuilder();
        Enumeration<?> e = request.getParameterNames();
        if(e!=null){
            posted.append("?");
        }

        while (e.hasMoreElements()) {
            if(posted.length() > 1){
                posted.append("&");
            }

            String curr = (String) e.nextElement();
            posted.append(curr).append("=");

            //대소문자 구분 없이 패스워드 필터링 및 마스킹
            if (curr.toLowerCase().contains("password") || curr.toLowerCase().contains("pass") || curr.toLowerCase().contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }


        String ipAddr = getRemoteAddr(request);

        if(ipAddr != null && !ipAddr.isEmpty()){
            posted.append("&_psip=").append(ipAddr);
        }

        return posted.toString();
    }

    //X-FORWARDED-FOR 헤더 중 첫번째 IP만 가져오기
    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if(ipFromHeader != null && !ipFromHeader.isEmpty()){

            String clientIp = ipFromHeader.split(",")[0].trim();
            log.debug("ip from proxy - X-FORWARDED-FOR: " + clientIp);
            return clientIp;
        }

        return request.getRemoteAddr();
    }
}
