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
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorMovimientoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorMovimientoService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.CustomCollectors;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/sensorMovimientos", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorMovimientoResource {

    private final SensorMovimientoService sensorMovimientoService;
    private final UsuarioRepository usuarioRepository;

    public SensorMovimientoResource(final SensorMovimientoService sensorMovimientoService,
            final UsuarioRepository usuarioRepository) {
        this.sensorMovimientoService = sensorMovimientoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<SensorMovimientoDTO>> getAllSensorMovimientos() {
        return ResponseEntity.ok(sensorMovimientoService.findAll());
    }

    @GetMapping("/{idSensor}")
    public ResponseEntity<SensorMovimientoDTO> getSensorMovimiento(
            @PathVariable(name = "idSensor") final Integer idSensor) {
        return ResponseEntity.ok(sensorMovimientoService.get(idSensor));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorMovimiento(
            @RequestBody @Valid final SensorMovimientoDTO sensorMovimientoDTO) {
        final Integer createdIdSensor = sensorMovimientoService.create(sensorMovimientoDTO);
        return new ResponseEntity<>(createdIdSensor, HttpStatus.CREATED);
    }

    @PutMapping("/{idSensor}")
    public ResponseEntity<Integer> updateSensorMovimiento(
            @PathVariable(name = "idSensor") final Integer idSensor,
            @RequestBody @Valid final SensorMovimientoDTO sensorMovimientoDTO) {
        sensorMovimientoService.update(idSensor, sensorMovimientoDTO);
        return ResponseEntity.ok(idSensor);
    }

    @DeleteMapping("/{idSensor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorMovimiento(
            @PathVariable(name = "idSensor") final Integer idSensor) {
        final ReferencedWarning referencedWarning = sensorMovimientoService.getReferencedWarning(idSensor);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorMovimientoService.delete(idSensor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sensiorValues")
    public ResponseEntity<Map<Integer, String>> getSensiorValues() {
        return ResponseEntity.ok(usuarioRepository.findAll(Sort.by("idUsuario"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUsuario, Usuario::getNombre)));
    }

}
