package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Rol;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.RolDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.RolRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@Service
public class RolService {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    public RolService(final RolRepository rolRepository,
            final UsuarioRepository usuarioRepository) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<RolDTO> findAll() {
        final List<Rol> rols = rolRepository.findAll(Sort.by("idRol"));
        return rols.stream()
                .map(rol -> mapToDTO(rol, new RolDTO()))
                .toList();
    }

    public RolDTO get(final Long idRol) {
        return rolRepository.findById(idRol)
                .map(rol -> mapToDTO(rol, new RolDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RolDTO rolDTO) {
        final Rol rol = new Rol();
        mapToEntity(rolDTO, rol);
        return rolRepository.save(rol).getIdRol();
    }

    public void update(final Long idRol, final RolDTO rolDTO) {
        final Rol rol = rolRepository.findById(idRol)
                .orElseThrow(NotFoundException::new);
        mapToEntity(rolDTO, rol);
        rolRepository.save(rol);
    }

    public void delete(final Long idRol) {
        rolRepository.deleteById(idRol);
    }

    private RolDTO mapToDTO(final Rol rol, final RolDTO rolDTO) {
        rolDTO.setIdRol(rol.getIdRol());
        rolDTO.setNombre(rol.getNombre());
        return rolDTO;
    }

    private Rol mapToEntity(final RolDTO rolDTO, final Rol rol) {
        rol.setNombre(rolDTO.getNombre());
        return rol;
    }

    public ReferencedWarning getReferencedWarning(final Long idRol) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Rol rol = rolRepository.findById(idRol)
                .orElseThrow(NotFoundException::new);
        final Usuario usuariooUsuario = usuarioRepository.findFirstByUsuarioo(rol);
        if (usuariooUsuario != null) {
            referencedWarning.setKey("rol.usuario.usuarioo.referenced");
            referencedWarning.addParam(usuariooUsuario.getIdUsuario());
            return referencedWarning;
        }
        return null;
    }

}
