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
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorAccesoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorAccesoService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.CustomCollectors;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/sensorAccesos", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorAccesoResource {

    private final SensorAccesoService sensorAccesoService;
    private final UsuarioRepository usuarioRepository;

    public SensorAccesoResource(final SensorAccesoService sensorAccesoService,
            final UsuarioRepository usuarioRepository) {
        this.sensorAccesoService = sensorAccesoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<SensorAccesoDTO>> getAllSensorAccesos() {
        return ResponseEntity.ok(sensorAccesoService.findAll());
    }

    @GetMapping("/{idSensor}")
    public ResponseEntity<SensorAccesoDTO> getSensorAcceso(
            @PathVariable(name = "idSensor") final Integer idSensor) {
        return ResponseEntity.ok(sensorAccesoService.get(idSensor));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorAcceso(
            @RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
        final Integer createdIdSensor = sensorAccesoService.create(sensorAccesoDTO);
        return new ResponseEntity<>(createdIdSensor, HttpStatus.CREATED);
    }

    @PutMapping("/{idSensor}")
    public ResponseEntity<Integer> updateSensorAcceso(
            @PathVariable(name = "idSensor") final Integer idSensor,
            @RequestBody @Valid final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoService.update(idSensor, sensorAccesoDTO);
        return ResponseEntity.ok(idSensor);
    }

    @DeleteMapping("/{idSensor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorAcceso(
            @PathVariable(name = "idSensor") final Integer idSensor) {
        final ReferencedWarning referencedWarning = sensorAccesoService.getReferencedWarning(idSensor);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorAccesoService.delete(idSensor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sensValues")
    public ResponseEntity<Map<Integer, String>> getSensValues() {
        return ResponseEntity.ok(usuarioRepository.findAll(Sort.by("idUsuario"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUsuario, Usuario::getNombre)));
    }
}
