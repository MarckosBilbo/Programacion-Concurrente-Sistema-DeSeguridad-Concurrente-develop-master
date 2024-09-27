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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SensorMonitorService {

    private static final Logger logger = Logger.getLogger(SensorMonitorService.class.getName());

    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final EventoRepository eventoRepository;
    private final ExecutorService executorService;
    private CountDownLatch latch;

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

        if (sensorTemperaturas.isEmpty() && sensorMovimientos.isEmpty() && sensorAccesos.isEmpty()) {
            logger.warning("No sensors found in the database. No events will be generated.");
            return;
        }

        List<Evento> eventosTemperatura = generateEvents("Temperatura", sensorTemperaturas.size());
        List<Evento> eventosMovimiento = generateEvents("Movimiento", sensorMovimientos.size());
        List<Evento> eventosAcceso = generateEvents("Acceso", sensorAccesos.size());

        assignEventsToSensors(eventosTemperatura, sensorTemperaturas);
        assignEventsToSensors(eventosMovimiento, sensorMovimientos);
        assignEventsToSensors(eventosAcceso, sensorAccesos);

        int totalEvents = eventosTemperatura.size() + eventosMovimiento.size() + eventosAcceso.size();
        latch = new CountDownLatch(totalEvents);

        eventosTemperatura.forEach(evento -> executorService.submit(() -> {
            logEvent(evento);
            logger.info("Starting task for event: " + evento.getDescripcion());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logger.info("Completed task for event: " + evento.getDescripcion());
            latch.countDown();
        }));
        eventosMovimiento.forEach(evento -> executorService.submit(() -> {
            logEvent(evento);
            logger.info("Starting task for event: " + evento.getDescripcion());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logger.info("Completed task for event: " + evento.getDescripcion());
            latch.countDown();
        }));
        eventosAcceso.forEach(evento -> executorService.submit(() -> {
            logEvent(evento);
            logger.info("Starting task for event: " + evento.getDescripcion());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logger.info("Completed task for event: " + evento.getDescripcion());
            latch.countDown();
        }));

        logger.info("All tasks submitted to ExecutorService");
    }

    public void awaitCompletion() {
        if (latch == null) {
            logger.warning("Latch is null. No tasks were submitted.");
            return;
        }
        try {
            latch.await();
            logger.info("All tasks completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdownExecutorService() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
            logger.info("ExecutorService shut down");
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
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
