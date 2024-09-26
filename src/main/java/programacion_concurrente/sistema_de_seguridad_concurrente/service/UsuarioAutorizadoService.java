package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.UsuarioAutorizado;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioAutorizadoRepository;

import java.util.List;

@Service
public class UsuarioAutorizadoService {
    @Autowired
    private UsuarioAutorizadoRepository usuarioAutorizadoRepository;

    public List<UsuarioAutorizado> listarUsuarios() {
        return usuarioAutorizadoRepository.findAll();
    }

    public UsuarioAutorizado agregarUsuario(UsuarioAutorizado usuario) {
        return usuarioAutorizadoRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioAutorizadoRepository.deleteById(id);
    }
}
