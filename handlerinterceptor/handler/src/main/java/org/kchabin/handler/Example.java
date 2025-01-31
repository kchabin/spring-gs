package org.kchabin.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Example implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        //ex : 요청 헤더에서 토큰 확인
        String token = request.getHeader("Authorization");
        if (token == null || !isValidToken(token)){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false; // 요청 중단
        }

            //your code
            return true; //요청 처리 계속 진행
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

        if(modelAndView != null){  //모델이 null이 아닐 경우에만!
            String userAvatar = getUserAvatar();  //유저 아바타 url 가져오기
            modelAndView.addObject("userAvatar", userAvatar); //모델에 아바타 추가
        }
    }


    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex
    ) {

    }

    private boolean isValidToken(String token){
        //토큰 유효성 검증 로직
        return "valid-token".equals(token);
    }

    private String getUserAvatar() {
        //로그인한 사용자의 아바타 url 반환
        return "https://example.com/avatar/user123.png";
    }
}
