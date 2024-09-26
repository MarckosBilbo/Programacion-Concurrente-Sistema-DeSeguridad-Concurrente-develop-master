package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificacionController {
    @MessageMapping("/notify")
    @SendTo("/topic/alerts")
    public String sendNotification(String message) {
        return message;
    }
}
