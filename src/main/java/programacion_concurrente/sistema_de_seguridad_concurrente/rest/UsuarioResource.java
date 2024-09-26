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
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Credenciales;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Rol;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.UsuarioDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.CredencialesRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.RolRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.UsuarioService;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.CustomCollectors;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;
    private final CredencialesRepository credencialesRepository;
    private final RolRepository rolRepository;

    public UsuarioResource(final UsuarioService usuarioService,
            final CredencialesRepository credencialesRepository,
            final RolRepository rolRepository) {
        this.usuarioService = usuarioService;
        this.credencialesRepository = credencialesRepository;
        this.rolRepository = rolRepository;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.get(idUsuario));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdIdUsuario = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Integer> updateUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(idUsuario, usuarioDTO);
        return ResponseEntity.ok(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "idUsuario") final Integer idUsuario) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(idUsuario);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarioValues")
    public ResponseEntity<Map<Integer, String>> getUsuarioValues() {
        return ResponseEntity.ok(credencialesRepository.findAll(Sort.by("nombre"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Credenciales::getNombre, Credenciales::getContrasenia)));
    }

    @GetMapping("/usuariooValues")
    public ResponseEntity<Map<Long, String>> getUsuariooValues() {
        return ResponseEntity.ok(rolRepository.findAll(Sort.by("idRol"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Rol::getIdRol, Rol::getNombre)));
    }

}
