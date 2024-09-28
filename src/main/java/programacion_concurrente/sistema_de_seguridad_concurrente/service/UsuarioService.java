package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.UsuarioDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("idUsuario"));
        return usuarios.stream()
            .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
            .toList();
    }

    public UsuarioDTO get(final Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getIdUsuario();
    }

    public void update(final Integer idUsuario, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setActivo(usuario.getActivo());
        usuarioDTO.setUsuario(usuario.getUsuario().getNombre());
        usuarioDTO.setUsuarioo(usuario.getUsuarioo().getIdRol());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setActivo(usuarioDTO.getActivo());
        // Aquí debes obtener las entidades Credenciales y Rol desde sus respectivos repositorios
        // y asignarlas a usuario.setUsuario() y usuario.setUsuarioo()
        return usuario;
    }

    public ReferencedWarning getReferencedWarning(final Integer idUsuario) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(NotFoundException::new);
        // Aquí se puede agregar lógica para verificar referencias si es necesario
        return null;
    }
}
