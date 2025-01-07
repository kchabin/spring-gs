package gs.websocket.kch;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration //spring configuration class 애너테이션
@EnableWebSocketMessageBroker // 메시지 브로커가 지원하는 웹소켓 메시지 핸들링이 가능하도록 함.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 설정을 위한 기본 메서드 구현
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic"); ///topic으로 시작하는 클라이언트에 greeting messages 돌려줌
        config.setApplicationDestinationPrefixes("/app"); //클라이언트가 /app으로 시작하는 경로로 메시지를 보내면 @MessageMapping이 바인딩된 메서드로 매핑
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/gs-guide-websocket"); //websocket 연결을 시작하는 경로
    }
}
