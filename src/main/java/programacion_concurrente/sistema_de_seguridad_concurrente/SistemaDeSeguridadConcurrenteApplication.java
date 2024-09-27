package programacion_concurrente.sistema_de_seguridad_concurrente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorMonitorService;

@SpringBootApplication
public class SistemaDeSeguridadConcurrenteApplication implements CommandLineRunner {

    private final SensorMonitorService sensorMonitorService;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorAccesoRepository sensorAccesoRepository;

    @Autowired
    public SistemaDeSeguridadConcurrenteApplication(SensorMonitorService sensorMonitorService,
                                                    SensorTemperaturaRepository sensorTemperaturaRepository,
                                                    SensorMovimientoRepository sensorMovimientoRepository,
                                                    SensorAccesoRepository sensorAccesoRepository) {
        this.sensorMonitorService = sensorMonitorService;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SistemaDeSeguridadConcurrenteApplication.class, args)
            .getBean(SistemaDeSeguridadConcurrenteApplication.class)
            .startSensorMonitoring();
    }

    @Override
    public void run(String... args) throws Exception {
        initializeSensors();
    }

    private void initializeSensors() {
        if (sensorTemperaturaRepository.count() == 0) {
            SensorTemperatura sensorTemperatura = new SensorTemperatura();
            sensorTemperatura.setNombre("Temperatura Sensor");
            sensorTemperatura.setIdSensor(generateRandomId()); // Establecer un valor predeterminado para idSensor
            sensorTemperaturaRepository.save(sensorTemperatura);
        }
        if (sensorMovimientoRepository.count() == 0) {
            SensorMovimiento sensorMovimiento = new SensorMovimiento();
            sensorMovimiento.setNombre("Movimiento Sensor");
            sensorMovimiento.setIdSensor(generateRandomId()); // Establecer un valor predeterminado para idSensor
            sensorMovimientoRepository.save(sensorMovimiento);
        }
        if (sensorAccesoRepository.count() == 0) {
            SensorAcceso sensorAcceso = new SensorAcceso();
            sensorAcceso.setNombre("Acceso Sensor");
            sensorAcceso.setIdSensor(generateRandomId()); // Establecer un valor predeterminado para idSensor
            sensorAccesoRepository.save(sensorAcceso);
        }
    }

    private Integer generateRandomId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }

    public void startSensorMonitoring() {
        sensorMonitorService.generateRandomEvents();
        sensorMonitorService.awaitCompletion();
        sensorMonitorService.shutdownExecutorService();
    }
}
