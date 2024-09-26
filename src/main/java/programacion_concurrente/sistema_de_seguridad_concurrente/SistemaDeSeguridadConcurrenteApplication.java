package programacion_concurrente.sistema_de_seguridad_concurrente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorMonitorService;



@SpringBootApplication
public class SistemaDeSeguridadConcurrenteApplication {

    private final SensorMonitorService sensorMonitorService;

    @Autowired
    public SistemaDeSeguridadConcurrenteApplication(SensorMonitorService sensorMonitorService) {
        this.sensorMonitorService = sensorMonitorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SistemaDeSeguridadConcurrenteApplication.class, args)
            .getBean(SistemaDeSeguridadConcurrenteApplication.class)
            .startSensorMonitoring();
    }

    public void startSensorMonitoring() {
        sensorMonitorService.generateRandomEvents();
    }
}
