package by.ld1995.coursework.controllers

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.security.Principal

@Component
class WebSocketEventListener(private val messagingTemplate: SimpMessageSendingOperations) {

    private val logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectedEvent) {
        val user: Principal = event.user!!
        logger.info("Received a new web socket connection, ${user.name}")
        //оповестить во всех чата что пользователь онлайн
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val user: Principal = event.user!!
        logger.info("User Disconnected, ${user.name}")
    }
}