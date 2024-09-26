package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.UsuarioAutorizado;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.UsuarioAutorizadoService;

import java.util.List;

@RestController
@RequestMapping("/usuarios-autorizados")
public class UsuarioAutorizadoController {
    @Autowired
    private UsuarioAutorizadoService usuarioAutorizadoService;

    @GetMapping
    public List<UsuarioAutorizado> listarUsuarios() {
        return usuarioAutorizadoService.listarUsuarios();
    }

    @PostMapping
    public UsuarioAutorizado agregarUsuario(@RequestBody UsuarioAutorizado usuario) {
        return usuarioAutorizadoService.agregarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioAutorizadoService.eliminarUsuario(id);
    }
}

