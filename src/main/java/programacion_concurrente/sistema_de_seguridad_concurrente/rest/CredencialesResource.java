package programacion_concurrente.sistema_de_seguridad_concurrente.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
import programacion_concurrente.sistema_de_seguridad_concurrente.model.CredencialesDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.CredencialesService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/credencialess", produces = MediaType.APPLICATION_JSON_VALUE)
public class CredencialesResource {

    private final CredencialesService credencialesService;

    public CredencialesResource(final CredencialesService credencialesService) {
        this.credencialesService = credencialesService;
    }

    @GetMapping
    public ResponseEntity<List<CredencialesDTO>> getAllCredencialess() {
        return ResponseEntity.ok(credencialesService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<CredencialesDTO> getCredenciales(
            @PathVariable(name = "nombre") final Integer nombre) {
        return ResponseEntity.ok(credencialesService.get(nombre));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createCredenciales(
            @RequestBody @Valid final CredencialesDTO credencialesDTO) {
        final Integer createdNombre = credencialesService.create(credencialesDTO);
        return new ResponseEntity<>(createdNombre, HttpStatus.CREATED);
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<Integer> updateCredenciales(
            @PathVariable(name = "nombre") final Integer nombre,
            @RequestBody @Valid final CredencialesDTO credencialesDTO) {
        credencialesService.update(nombre, credencialesDTO);
        return ResponseEntity.ok(nombre);
    }

    @DeleteMapping("/{nombre}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCredenciales(
            @PathVariable(name = "nombre") final Integer nombre) {
        final ReferencedWarning referencedWarning = credencialesService.getReferencedWarning(nombre);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        credencialesService.delete(nombre);
        return ResponseEntity.noContent().build();
    }

}
