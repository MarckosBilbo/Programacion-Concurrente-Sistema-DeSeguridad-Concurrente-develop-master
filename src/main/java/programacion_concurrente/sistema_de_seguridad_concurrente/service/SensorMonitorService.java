package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.EventoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

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
        initializeSensors();
    }

    private void initializeSensors() {
        if (sensorTemperaturaRepository.findAll().isEmpty()) {
            SensorTemperatura sensorTemperatura = new SensorTemperatura();
            sensorTemperatura.setNombre("Sensor de Temperatura 1");
            sensorTemperatura.setTemperatura(25);
            sensorTemperaturaRepository.save(sensorTemperatura);
        }

        if (sensorMovimientoRepository.findAll().isEmpty()) {
            SensorMovimiento sensorMovimiento = new SensorMovimiento();
            sensorMovimiento.setNombre("Sensor de Movimiento 1");
            sensorMovimientoRepository.save(sensorMovimiento);
        }

        if (sensorAccesoRepository.findAll().isEmpty()) {
            SensorAcceso sensorAcceso = new SensorAcceso();
            sensorAcceso.setNombre("Sensor de Acceso 1");
            sensorAccesoRepository.save(sensorAcceso);
        }
    }

    public void generateRandomEvents() {
        System.out.println("================================================================================================================================================================================================");
        System.out.println("================================================================================================================================================================================================");
        List<SensorTemperatura> sensorTemperaturas = sensorTemperaturaRepository.findAll();
        List<SensorMovimiento> sensorMovimientos = sensorMovimientoRepository.findAll();
        List<SensorAcceso> sensorAccesos = sensorAccesoRepository.findAll();

        if (sensorTemperaturas.isEmpty() && sensorMovimientos.isEmpty() && sensorAccesos.isEmpty()) {
            logger.warning("No sensors found in the database. No events will be generated.");
            return;
        }

        int eventsPerSensor = 10; // Number of events to generate per sensor

        List<Evento> eventosTemperatura = generateEvents("Temperatura", sensorTemperaturas.size() * eventsPerSensor);
        List<Evento> eventosMovimiento = generateEvents("Movimiento", sensorMovimientos.size() * eventsPerSensor);
        List<Evento> eventosAcceso = generateEvents("Acceso", sensorAccesos.size() * eventsPerSensor);

        assignEventsToSensors(eventosTemperatura, sensorTemperaturas);
        assignEventsToSensors(eventosMovimiento, sensorMovimientos);
        assignEventsToSensors(eventosAcceso, sensorAccesos);

        List<Evento> allEvents = new ArrayList<>();
        allEvents.addAll(eventosTemperatura);
        allEvents.addAll(eventosMovimiento);
        allEvents.addAll(eventosAcceso);

        Collections.shuffle(allEvents); // Mezclar los eventos

        latch = new CountDownLatch(allEvents.size());

        for (Evento evento : allEvents) {
            executorService.submit(() -> {
                logger.info("$$ Arrancamos la Tarea del " + evento.getDescripcion() + " $$");
                logEvent(evento);
                try {
                    Thread.sleep(500 + RANDOM.nextInt(4000)); // Sleep between 0.5 and 4.5 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                logger.info("&& Acabada la tarea del " + evento.getDescripcion() + " &&");
                latch.countDown();
            });
        }
        logger.info("<< >> Todas las tareas 'metidas' en el Executor Service << >>");
    }

    private List<Evento> generateEvents(String tipo, int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> {
                Evento evento = new Evento();
                evento.setTipoEvento(tipo);
                evento.setDescripcion("Evento de " + tipo + " #" + i);
                return evento;
            })
            .collect(Collectors.toList());
    }

    public void awaitCompletion() {
        if (latch == null) {
            logger.warning("Latch is null. No tasks were submitted.");
            return;
        }
        try {
            latch.await();
            logger.info("*** Todas las tareas han finalizado ***");
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
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            logger.info("*** Cerramos el Executor Service ***\n");
            System.out.println("================================================================================================================================================================================================");
            System.out.println("================================================================================================================================================================================================");

        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
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
        System.out.println("\nEvento: " + evento.getDescripcion() + " asignado a sensor: " + getSensorId(evento) + "\n");
    }

    private String getSensorId(Evento evento) {
        String suffix = generateAlphanumericSuffix();
        if (evento.getSensorTemperatura() != null) {
            return "SensorTemperatura_" + suffix;
        } else if (evento.getSensorMovimiento() != null) {
            return "SensorMovimiento_" + suffix;
        } else if (evento.getSensorAcceso() != null) {
            return "SensorAcceso_" + suffix;
        }
        return "Sensor desconocido";
    }

    private String generateAlphanumericSuffix() {
        return RANDOM.ints(4, 0, ALPHANUMERIC.length())
            .mapToObj(i -> String.valueOf(ALPHANUMERIC.charAt(i)))
            .collect(Collectors.joining());
    }
}
