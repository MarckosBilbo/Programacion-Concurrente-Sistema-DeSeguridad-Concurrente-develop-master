package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Credenciales;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Rol;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.UsuarioDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.CredencialesRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.RolRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CredencialesRepository credencialesRepository;
    private final RolRepository rolRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorAccesoRepository sensorAccesoRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final CredencialesRepository credencialesRepository, final RolRepository rolRepository,
            final SensorTemperaturaRepository sensorTemperaturaRepository,
            final SensorMovimientoRepository sensorMovimientoRepository,
            final SensorAccesoRepository sensorAccesoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.credencialesRepository = credencialesRepository;
        this.rolRepository = rolRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
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
        usuarioDTO.setUsuario(usuario.getUsuario() == null ? null : usuario.getUsuario().getNombre());
        usuarioDTO.setUsuarioo(usuario.getUsuarioo() == null ? null : usuario.getUsuarioo().getIdRol());
        return usuarioDTO;
    }
    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setActivo(usuarioDTO.getActivo());

        final Credenciales credenciales = usuarioDTO.getUsuario() == null ? null : credencialesRepository.findById(usuarioDTO.getUsuario())
            .orElseThrow(() -> new NotFoundException("usuario not found"));
        usuario.setUsuario(credenciales);

        final Rol rol = usuarioDTO.getUsuarioo() == null ? null : rolRepository.findById(usuarioDTO.getUsuarioo())
            .orElseThrow(() -> new NotFoundException("usuarioo not found"));
        usuario.setUsuarioo(rol);

        return usuario;
    }

    public boolean usuarioExists(final Integer nombre) {
        return usuarioRepository.existsByUsuarioNombre(nombre);
    }

    public boolean usuariooExists(final Long idRol) {
        return usuarioRepository.existsByUsuariooIdRol(idRol);
    }

    public ReferencedWarning getReferencedWarning(final Integer idUsuario) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(NotFoundException::new);
        final SensorTemperatura sensoreesSensorTemperatura = sensorTemperaturaRepository.findFirstBySensorees(usuario);
        if (sensoreesSensorTemperatura != null) {
            referencedWarning.setKey("usuario.sensorTemperatura.sensorees.referenced");
            referencedWarning.addParam(sensoreesSensorTemperatura.getIdSensor());
            return referencedWarning;
        }
        final SensorMovimiento sensiorSensorMovimiento = sensorMovimientoRepository.findFirstBySensior(usuario);
        if (sensiorSensorMovimiento != null) {
            referencedWarning.setKey("usuario.sensorMovimiento.sensior.referenced");
            referencedWarning.addParam(sensiorSensorMovimiento.getIdSensor());
            return referencedWarning;
        }
        final SensorAcceso sensSensorAcceso = sensorAccesoRepository.findFirstBySens(usuario);
        if (sensSensorAcceso != null) {
            referencedWarning.setKey("usuario.sensorAcceso.sens.referenced");
            referencedWarning.addParam(sensSensorAcceso.getIdSensor());
            return referencedWarning;
        }
        return null;
    }

}
