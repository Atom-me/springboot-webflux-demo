package com.atom.springbootwebflux.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * WebSocketHandler 接口，直接实现WebSocket支持，实现该接口来处理 WebSocket 消息。
 * handle(WebSocketSession session) 方法，接收 WebSocketSession 对象，即获取客户端信息、发送消息和接收消息的操作对象。
 * receive() 方法，接收消息，使用 map 操作获取的 Flux 中包含的消息持续处理，并拼接出返回消息 Flux 对象。
 * send() 方法，发送消息。消息为“ECHO--->  ”开头的。
 *
 * @author Atom
 */
@Component
public class EchoHandler implements WebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoHandler.class);

    @Override
    public List<String> getSubProtocols() {
        return WebSocketHandler.super.getSubProtocols();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        LOGGER.info("WebSocket客户端握手消息：{}", session.getHandshakeInfo().getUri());
        return session.send(
                session.receive()
                        .map(msg -> session.textMessage(
                                "ECHO---> " + msg.getPayloadAsText())));
    }
}
