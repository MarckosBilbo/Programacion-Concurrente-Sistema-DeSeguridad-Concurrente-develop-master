/*package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Credenciales;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.CredencialesDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.CredencialesRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@Service
public class CredencialesService {

    private final CredencialesRepository credencialesRepository;
    private final UsuarioRepository usuarioRepository;

    public CredencialesService(final CredencialesRepository credencialesRepository,
            final UsuarioRepository usuarioRepository) {
        this.credencialesRepository = credencialesRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<CredencialesDTO> findAll() {
        final List<Credenciales> credencialeses = credencialesRepository.findAll(Sort.by("nombre"));
        return credencialeses.stream()
                .map(credenciales -> mapToDTO(credenciales, new CredencialesDTO()))
                .toList();
    }

    public CredencialesDTO get(final Integer nombre) {
        return credencialesRepository.findById(nombre)
                .map(credenciales -> mapToDTO(credenciales, new CredencialesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CredencialesDTO credencialesDTO) {
        final Credenciales credenciales = new Credenciales();
        mapToEntity(credencialesDTO, credenciales);
        return credencialesRepository.save(credenciales).getNombre();
    }

    public void update(final Integer nombre, final CredencialesDTO credencialesDTO) {
        final Credenciales credenciales = credencialesRepository.findById(nombre)
                .orElseThrow(NotFoundException::new);
        mapToEntity(credencialesDTO, credenciales);
        credencialesRepository.save(credenciales);
    }

    public void delete(final Integer nombre) {
        credencialesRepository.deleteById(nombre);
    }

    private CredencialesDTO mapToDTO(final Credenciales credenciales,
            final CredencialesDTO credencialesDTO) {
        credencialesDTO.setNombre(credenciales.getNombre());
        credencialesDTO.setContrasenia(credenciales.getContrasenia());
        return credencialesDTO;
    }

    private Credenciales mapToEntity(final CredencialesDTO credencialesDTO,
            final Credenciales credenciales) {
        credenciales.setContrasenia(credencialesDTO.getContrasenia());
        return credenciales;
    }

    public ReferencedWarning getReferencedWarning(final Integer nombre) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Credenciales credenciales = credencialesRepository.findById(nombre)
                .orElseThrow(NotFoundException::new);
        final Usuario usuarioUsuario = usuarioRepository.findFirstByUsuario(credenciales);
        if (usuarioUsuario != null) {
            referencedWarning.setKey("credenciales.usuario.usuario.referenced");
            referencedWarning.addParam(usuarioUsuario.getIdUsuario());
            return referencedWarning;
        }
        return null;
    }

}
*/
