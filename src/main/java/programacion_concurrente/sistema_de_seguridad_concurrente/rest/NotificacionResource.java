package programacion_concurrente.sistema_de_seguridad_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.NotificacionDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.EventoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.NotificacionService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/notificacions", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificacionResource {

    private final NotificacionService notificacionService;
    private final EventoRepository eventoRepository;

    public NotificacionResource(final NotificacionService notificacionService,
            final EventoRepository eventoRepository) {
        this.notificacionService = notificacionService;
        this.eventoRepository = eventoRepository;
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> getAllNotificacions() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @GetMapping("/{idNotificacion}")
    public ResponseEntity<NotificacionDTO> getNotificacion(
            @PathVariable(name = "idNotificacion") final Integer idNotificacion) {
        return ResponseEntity.ok(notificacionService.get(idNotificacion));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createNotificacion(
            @RequestBody @Valid final NotificacionDTO notificacionDTO) {
        final Integer createdIdNotificacion = notificacionService.create(notificacionDTO);
        return new ResponseEntity<>(createdIdNotificacion, HttpStatus.CREATED);
    }

    @PutMapping("/{idNotificacion}")
    public ResponseEntity<Integer> updateNotificacion(
            @PathVariable(name = "idNotificacion") final Integer idNotificacion,
            @RequestBody @Valid final NotificacionDTO notificacionDTO) {
        notificacionService.update(idNotificacion, notificacionDTO);
        return ResponseEntity.ok(idNotificacion);
    }

    @DeleteMapping("/{idNotificacion}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNotificacion(
            @PathVariable(name = "idNotificacion") final Integer idNotificacion) {
        notificacionService.delete(idNotificacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notificacionValues")
    public ResponseEntity<Map<Integer, Integer>> getNotificacionValues() {
        return ResponseEntity.ok(eventoRepository.findAll(Sort.by("idEvento"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Evento::getIdEvento, Evento::getIdEvento)));
    }

}
