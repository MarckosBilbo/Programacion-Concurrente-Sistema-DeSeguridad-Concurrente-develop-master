package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.EventoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.EventoService;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getEventosByTipo(@RequestParam String tipoEvento) {
        List<EventoDTO> eventos = eventoService.getEventosByTipo(tipoEvento);
        return ResponseEntity.ok(eventos);
    }

}
