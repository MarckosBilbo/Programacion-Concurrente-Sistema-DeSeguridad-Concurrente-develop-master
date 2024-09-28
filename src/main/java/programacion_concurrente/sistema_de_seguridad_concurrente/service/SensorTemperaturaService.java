package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.SensorTemperaturaDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;

@Service
public class SensorTemperaturaService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;

    public SensorTemperaturaService(final SensorTemperaturaRepository sensorTemperaturaRepository) {
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
    }

    public List<SensorTemperaturaDTO> findAll() {
        final List<SensorTemperatura> sensorTemperaturas = sensorTemperaturaRepository.findAll(Sort.by("idSensor"));
        return sensorTemperaturas.stream()
            .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
            .toList();
    }

    public SensorTemperaturaDTO get(final Integer idSensor) {
        return sensorTemperaturaRepository.findById(idSensor)
            .map(sensorTemperatura -> mapToDTO(sensorTemperatura, new SensorTemperaturaDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = new SensorTemperatura();
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        return sensorTemperaturaRepository.save(sensorTemperatura).getIdSensor();
    }

    public void update(final Integer idSensor, final SensorTemperaturaDTO sensorTemperaturaDTO) {
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(idSensor)
            .orElseThrow(NotFoundException::new);
        mapToEntity(sensorTemperaturaDTO, sensorTemperatura);
        sensorTemperaturaRepository.save(sensorTemperatura);
    }

    public void delete(final Integer idSensor) {
        sensorTemperaturaRepository.deleteById(idSensor);
    }

    private SensorTemperaturaDTO mapToDTO(final SensorTemperatura sensorTemperatura,
                                          final SensorTemperaturaDTO sensorTemperaturaDTO) {
        sensorTemperaturaDTO.setIdSensor(sensorTemperatura.getIdSensor());
        sensorTemperaturaDTO.setNombre(sensorTemperatura.getNombre());
        sensorTemperaturaDTO.setTemperatura(sensorTemperatura.getTemperatura());
        return sensorTemperaturaDTO;
    }

    private SensorTemperatura mapToEntity(final SensorTemperaturaDTO sensorTemperaturaDTO,
                                          final SensorTemperatura sensorTemperatura) {
        sensorTemperatura.setNombre(sensorTemperaturaDTO.getNombre());
        sensorTemperatura.setTemperatura(sensorTemperaturaDTO.getTemperatura());
        return sensorTemperatura;
    }

    public ReferencedWarning getReferencedWarning(final Integer idSensor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SensorTemperatura sensorTemperatura = sensorTemperaturaRepository.findById(idSensor)
            .orElseThrow(NotFoundException::new);
        // Aquí se puede agregar lógica para verificar referencias a eventos si es necesario
        return null;
    }
}
