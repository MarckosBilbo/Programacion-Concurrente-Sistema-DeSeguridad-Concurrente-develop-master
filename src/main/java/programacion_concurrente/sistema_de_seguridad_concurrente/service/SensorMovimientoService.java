package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorMovimientoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;

@Service
public class SensorMovimientoService {

    private final SensorMovimientoRepository sensorMovimientoRepository;

    public SensorMovimientoService(final SensorMovimientoRepository sensorMovimientoRepository) {
        this.sensorMovimientoRepository = sensorMovimientoRepository;
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
        return sensorMovimientoDTO;
    }

    private SensorMovimiento mapToEntity(final SensorMovimientoDTO sensorMovimientoDTO,
                                         final SensorMovimiento sensorMovimiento) {
        sensorMovimiento.setNombre(sensorMovimientoDTO.getNombre());
        return sensorMovimiento;
    }

    public ReferencedWarning getReferencedWarning(final Integer idSensor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorMovimiento sensorMovimiento = sensorMovimientoRepository.findById(idSensor)
            .orElseThrow(NotFoundException::new);
        // Aquí se puede agregar lógica para verificar referencias a eventos si es necesario
        return null;
    }
}
