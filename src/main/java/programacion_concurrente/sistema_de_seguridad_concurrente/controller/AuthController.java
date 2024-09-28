package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.UsuarioService;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nombre, @RequestParam Integer idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioService.login(nombre, idUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getRol() == Usuario.Rol.ADMIN) {
                return ResponseEntity.ok("Admin");
            } else {
                return ResponseEntity.ok("User");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestParam String nombre) {
        Usuario usuario = usuarioService.createUser(nombre);
        return ResponseEntity.ok("User created with ID: " + usuario.getIdUsuario());
    }
}
