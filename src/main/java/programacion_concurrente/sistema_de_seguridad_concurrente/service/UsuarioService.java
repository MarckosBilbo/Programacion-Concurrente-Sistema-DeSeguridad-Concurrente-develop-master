package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> login(String nombre, Integer idUsuario) {
        return usuarioRepository.findByNombreAndIdUsuario(nombre, idUsuario);
    }

    public Usuario createUser(String nombre) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setRol(Usuario.Rol.USER);
        return usuarioRepository.save(usuario);
    }
}
