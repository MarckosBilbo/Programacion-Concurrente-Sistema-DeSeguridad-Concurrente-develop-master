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
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorTemperaturaDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorTemperaturaService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.CustomCollectors;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/sensorTemperaturas", produces = MediaType.APPLICATION_JSON_VALUE)
public class SensorTemperaturaResource {

    private final SensorTemperaturaService sensorTemperaturaService;
    private final UsuarioRepository usuarioRepository;

    public SensorTemperaturaResource(final SensorTemperaturaService sensorTemperaturaService,
            final UsuarioRepository usuarioRepository) {
        this.sensorTemperaturaService = sensorTemperaturaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<SensorTemperaturaDTO>> getAllSensorTemperaturas() {
        return ResponseEntity.ok(sensorTemperaturaService.findAll());
    }

    @GetMapping("/{idSensor}")
    public ResponseEntity<SensorTemperaturaDTO> getSensorTemperatura(
            @PathVariable(name = "idSensor") final Integer idSensor) {
        return ResponseEntity.ok(sensorTemperaturaService.get(idSensor));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSensorTemperatura(
            @RequestBody @Valid final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final Integer createdIdSensor = sensorTemperaturaService.create(sensorTemperaturaDTO);
        return new ResponseEntity<>(createdIdSensor, HttpStatus.CREATED);
    }

    @PutMapping("/{idSensor}")
    public ResponseEntity<Integer> updateSensorTemperatura(
            @PathVariable(name = "idSensor") final Integer idSensor,
            @RequestBody @Valid final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaService.update(idSensor, sensorTemperaturaDTO);
        return ResponseEntity.ok(idSensor);
    }

    @DeleteMapping("/{idSensor}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensorTemperatura(
            @PathVariable(name = "idSensor") final Integer idSensor) {
        final ReferencedWarning referencedWarning = sensorTemperaturaService.getReferencedWarning(idSensor);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorTemperaturaService.delete(idSensor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sensoreesValues")
    public ResponseEntity<Map<Integer, String>> getSensoreesValues() {
        return ResponseEntity.ok(usuarioRepository.findAll(Sort.by("idUsuario"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUsuario, Usuario::getNombre)));
    }

}
