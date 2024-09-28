package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorAccesoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;

@Service
public class SensorAccesoService {

    private final SensorAccesoRepository sensorAccesoRepository;

    public SensorAccesoService(final SensorAccesoRepository sensorAccesoRepository) {
        this.sensorAccesoRepository = sensorAccesoRepository;
    }

    public List<SensorAccesoDTO> findAll() {
        final List<SensorAcceso> sensorAccesoes = sensorAccesoRepository.findAll(Sort.by("idSensor"));
        return sensorAccesoes.stream()
            .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
            .toList();
    }

    public SensorAccesoDTO get(final Integer idSensor) {
        return sensorAccesoRepository.findById(idSensor)
            .map(sensorAcceso -> mapToDTO(sensorAcceso, new SensorAccesoDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = new SensorAcceso();
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        return sensorAccesoRepository.save(sensorAcceso).getIdSensor();
    }

    public void update(final Integer idSensor, final SensorAccesoDTO sensorAccesoDTO) {
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(idSensor)
            .orElseThrow(NotFoundException::new);
        mapToEntity(sensorAccesoDTO, sensorAcceso);
        sensorAccesoRepository.save(sensorAcceso);
    }

    public void delete(final Integer idSensor) {
        sensorAccesoRepository.deleteById(idSensor);
    }

    private SensorAccesoDTO mapToDTO(final SensorAcceso sensorAcceso,
                                     final SensorAccesoDTO sensorAccesoDTO) {
        sensorAccesoDTO.setIdSensor(sensorAcceso.getIdSensor());
        sensorAccesoDTO.setNombre(sensorAcceso.getNombre());

        return sensorAccesoDTO;
    }

    private SensorAcceso mapToEntity(final SensorAccesoDTO sensorAccesoDTO,
                                     final SensorAcceso sensorAcceso) {
        sensorAcceso.setNombre(sensorAccesoDTO.getNombre());
        return sensorAcceso;
    }

    public ReferencedWarning getReferencedWarning(final Integer idSensor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorAcceso sensorAcceso = sensorAccesoRepository.findById(idSensor)
            .orElseThrow(NotFoundException::new);
        // Aquí se puede agregar lógica para verificar referencias a eventos si es necesario
        return null;
    }
}
