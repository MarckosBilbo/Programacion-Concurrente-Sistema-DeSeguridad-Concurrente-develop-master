package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.EventoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SensorMonitorService {

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final EventoRepository eventoRepository;
    private final ExecutorService executorService;

    @Autowired
    public SensorMonitorService(SensorTemperaturaRepository sensorTemperaturaRepository,
                                SensorMovimientoRepository sensorMovimientoRepository,
                                SensorAccesoRepository sensorAccesoRepository,
                                EventoRepository eventoRepository,
                                ExecutorService taskExecutor) {
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.eventoRepository = eventoRepository;
        this.executorService = taskExecutor;
    }

    public void generateRandomEvents() {
        List<SensorTemperatura> sensorTemperaturas = sensorTemperaturaRepository.findAll();
        List<SensorMovimiento> sensorMovimientos = sensorMovimientoRepository.findAll();
        List<SensorAcceso> sensorAccesos = sensorAccesoRepository.findAll();

        List<Evento> eventosTemperatura = generateEvents("Temperatura", sensorTemperaturas.size());
        List<Evento> eventosMovimiento = generateEvents("Movimiento", sensorMovimientos.size());
        List<Evento> eventosAcceso = generateEvents("Acceso", sensorAccesos.size());

        assignEventsToSensors(eventosTemperatura, sensorTemperaturas);
        assignEventsToSensors(eventosMovimiento, sensorMovimientos);
        assignEventsToSensors(eventosAcceso, sensorAccesos);

        eventosTemperatura.forEach(evento -> executorService.submit(() -> logEvent(evento)));
        eventosMovimiento.forEach(evento -> executorService.submit(() -> logEvent(evento)));
        eventosAcceso.forEach(evento -> executorService.submit(() -> logEvent(evento)));
    }

    private List<Evento> generateEvents(String tipo, int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> {
                Evento evento = new Evento();
                evento.setTipoEvento(tipo);
                evento.setNivelCriticidad("Alta");
                evento.setDescripcion("Evento de " + tipo + " #" + i);
                evento.setFechaHora(OffsetDateTime.now());
                return evento;
            })
            .collect(Collectors.toList());
    }

    private void assignEventsToSensors(List<Evento> eventos, List<? extends Sensor> sensores) {
        for (int i = 0; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            Sensor sensor = sensores.get(i % sensores.size());
            if (sensor instanceof SensorTemperatura) {
                evento.setSensorTemperatura((SensorTemperatura) sensor);
            } else if (sensor instanceof SensorMovimiento) {
                evento.setSensorMovimiento((SensorMovimiento) sensor);
            } else if (sensor instanceof SensorAcceso) {
                evento.setSensorAcceso((SensorAcceso) sensor);
            }
            eventoRepository.save(evento);
        }
    }

    private void logEvent(Evento evento) {
        System.out.println("Evento: " + evento.getDescripcion() + " asignado a sensor: " + getSensorId(evento));
    }

    private String getSensorId(Evento evento) {
        if (evento.getSensorTemperatura() != null) {
            return "SensorTemperatura #" + evento.getSensorTemperatura().getIdSensor();
        } else if (evento.getSensorMovimiento() != null) {
            return "SensorMovimiento #" + evento.getSensorMovimiento().getIdSensor();
        } else if (evento.getSensorAcceso() != null) {
            return "SensorAcceso #" + evento.getSensorAcceso().getIdSensor();
        }
        return "Sensor desconocido";
    }
}
