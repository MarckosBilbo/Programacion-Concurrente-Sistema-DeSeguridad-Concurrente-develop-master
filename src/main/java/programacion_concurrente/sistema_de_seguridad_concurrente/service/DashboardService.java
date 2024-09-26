package programacion_concurrente.sistema_de_seguridad_concurrente.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.DashboardDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final SensorAccesoRepository sensorAccesoRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;

    @Autowired
    public DashboardService(SensorAccesoRepository sensorAccesoRepository,
                            SensorMovimientoRepository sensorMovimientoRepository,
                            SensorTemperaturaRepository sensorTemperaturaRepository) {
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
    }

    public List<DashboardDTO> getDashboardData() {
        List<DashboardDTO> accesoData = sensorAccesoRepository.findAll().stream()
            .map(sensor -> {
                DashboardDTO dto = new DashboardDTO();
                dto.setSensorId(sensor.getIdSensor());
                dto.setSensorType("Acceso");
                dto.setStatus(sensor.getRespuesta());
                dto.setLastReadingTime(sensor.getLastModifiedDate());
                dto.setLastReadingValue(sensor.getRespuesta());
                // Assuming getRecentActivity() returns the most recent event
                Evento recentEvent = sensor.getSensorAcceso().stream().findFirst().orElse(null);
                if (recentEvent != null) {
                    dto.setRecentActivityTime(recentEvent.getFechaHora());
                    dto.setEventType(recentEvent.getTipoEvento());
                    dto.setEventDescription(recentEvent.getDescripcion());
                }
                return dto;
            }).collect(Collectors.toList());

        List<DashboardDTO> movimientoData = sensorMovimientoRepository.findAll().stream()
            .map(sensor -> {
                DashboardDTO dto = new DashboardDTO();
                dto.setSensorId(sensor.getIdSensor());
                dto.setSensorType("Movimiento");
                dto.setStatus("Activo"); // Assuming status is always "Activo"
                dto.setLastReadingTime(sensor.getLastModifiedDate());
                dto.setLastReadingValue("Movimiento detectado");
                Evento recentEvent = sensor.getSensorMovimiento().stream().findFirst().orElse(null);
                if (recentEvent != null) {
                    dto.setRecentActivityTime(recentEvent.getFechaHora());
                    dto.setEventType(recentEvent.getTipoEvento());
                    dto.setEventDescription(recentEvent.getDescripcion());
                }
                return dto;
            }).collect(Collectors.toList());

        List<DashboardDTO> temperaturaData = sensorTemperaturaRepository.findAll().stream()
            .map(sensor -> {
                DashboardDTO dto = new DashboardDTO();
                dto.setSensorId(sensor.getIdSensor());
                dto.setSensorType("Temperatura");
                dto.setStatus(sensor.getTemperatura() + "°C");
                dto.setLastReadingTime(sensor.getLastUpdated());
                dto.setLastReadingValue(sensor.getTemperatura() + "°C");
                Evento recentEvent = sensor.getSensorTemperatura().stream().findFirst().orElse(null);
                if (recentEvent != null) {
                    dto.setRecentActivityTime(recentEvent.getFechaHora());
                    dto.setEventType(recentEvent.getTipoEvento());
                    dto.setEventDescription(recentEvent.getDescripcion());
                }
                return dto;
            }).collect(Collectors.toList());

        accesoData.addAll(movimientoData);
        accesoData.addAll(temperaturaData);
        return accesoData;
    }
}
