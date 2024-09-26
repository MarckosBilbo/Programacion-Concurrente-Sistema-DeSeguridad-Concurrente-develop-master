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
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.EventoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.EventoService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.CustomCollectors;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventoResource {

    private final EventoService eventoService;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorAccesoRepository sensorAccesoRepository;

    public EventoResource(final EventoService eventoService,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorAccesoRepository sensorAccesoRepository) {
        this.eventoService = eventoService;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoDTO> getEvento(
            @PathVariable(name = "idEvento") final Integer idEvento) {
        return ResponseEntity.ok(eventoService.get(idEvento));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createEvento(@RequestBody @Valid final EventoDTO eventoDTO) {
        final Integer createdIdEvento = eventoService.create(eventoDTO);
        return new ResponseEntity<>(createdIdEvento, HttpStatus.CREATED);
    }

    @PutMapping("/{idEvento}")
    public ResponseEntity<Integer> updateEvento(
            @PathVariable(name = "idEvento") final Integer idEvento,
            @RequestBody @Valid final EventoDTO eventoDTO) {
        eventoService.update(idEvento, eventoDTO);
        return ResponseEntity.ok(idEvento);
    }

    @DeleteMapping("/{idEvento}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvento(
            @PathVariable(name = "idEvento") final Integer idEvento) {
        final ReferencedWarning referencedWarning = eventoService.getReferencedWarning(idEvento);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        eventoService.delete(idEvento);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eventosValues")
    public ResponseEntity<Map<Integer, String>> getEventosValues() {
        return ResponseEntity.ok(sensorTemperaturaRepository.findAll(Sort.by("idSensor"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorTemperatura::getIdSensor, SensorTemperatura::getNombre)));
    }

    @GetMapping("/eventossValues")
    public ResponseEntity<Map<Integer, String>> getEventossValues() {
        return ResponseEntity.ok(sensorMovimientoRepository.findAll(Sort.by("idSensor"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorMovimiento::getIdSensor, SensorMovimiento::getNombre)));
    }

    @GetMapping("/eventosssValues")
    public ResponseEntity<Map<Integer, String>> getEventosssValues() {
        return ResponseEntity.ok(sensorAccesoRepository.findAll(Sort.by("idSensor"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SensorAcceso::getIdSensor, SensorAcceso::getNombre)));
    }

}
