package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorMovimientoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.EventoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@Service
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public SensorMovimientoService(final SensorMovimientoRepository sensorMovimientoRepository,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<SensorMovimientoDTO> findAll() {
        final List<SensorMovimiento> sensorMovimientoes = sensorMovimientoRepository.findAll(Sort.by("idSensor"));
        return sensorMovimientoes.stream()
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .toList();
    }

    public SensorMovimientoDTO get(final Integer idSensor) {
        return sensorMovimientoRepository.findById(idSensor)
                .map(sensorMovimiento -> mapToDTO(sensorMovimiento, new SensorMovimientoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = new SensorMovimiento();
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        return sensorMovimientoRepository.save(sensorMovimiento).getIdSensor();
    }

    public void update(final Integer idSensor, final SensorMovimientoDTO sensorMovimientoDTO) {
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(idSensor)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorMovimientoDTO, sensorMovimiento);
        sensorMovimientoRepository.save(sensorMovimiento);
    }

    public void delete(final Integer idSensor) {
        sensorMovimientoRepository.deleteById(idSensor);
    }

    private SensorMovimientoDTO mapToDTO(final SensorMovimiento sensorMovimiento,
            final SensorMovimientoDTO sensorMovimientoDTO) {
        sensorMovimientoDTO.setIdSensor(sensorMovimiento.getIdSensor());
        sensorMovimientoDTO.setNombre(sensorMovimiento.getNombre());
        sensorMovimientoDTO.setUbicacion(sensorMovimiento.getUbicacion());
        sensorMovimientoDTO.setSensior(sensorMovimiento.getSensior() == null ? null : sensorMovimiento.getSensior().getIdUsuario());
        return sensorMovimientoDTO;
    }

    private SensorMovimiento mapToEntity(final SensorMovimientoDTO sensorMovimientoDTO,
            final SensorMovimiento sensorMovimiento) {
        sensorMovimiento.setNombre(sensorMovimientoDTO.getNombre());
        sensorMovimiento.setUbicacion(sensorMovimientoDTO.getUbicacion());
        final Usuario sensior = sensorMovimientoDTO.getSensior() == null ? null : usuarioRepository.findById(sensorMovimientoDTO.getSensior())
                .orElseThrow(() -> new NotFoundException("sensior not found"));
        sensorMovimiento.setSensior(sensior);
        return sensorMovimiento;
    }

    public ReferencedWarning getReferencedWarning(final Integer idSensor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(idSensor)
                .orElseThrow(NotFoundException::new);
        final Evento eventossEvento = eventoRepository.findFirstBysensorMovimiento(sensorMovimiento);
        if (eventossEvento != null) {
            referencedWarning.setKey("sensorMovimiento.evento.eventoss.referenced");
            referencedWarning.addParam(eventossEvento.getIdEvento());
            return referencedWarning;
        }
        return null;
    }

}
