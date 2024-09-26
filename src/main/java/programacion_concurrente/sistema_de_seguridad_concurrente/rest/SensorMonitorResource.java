package programacion_concurrente.sistema_de_seguridad_concurrente.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorMonitorService;

@RestController
@RequestMapping("/api/sensorMonitor")
public class SensorMonitorResource {

    private final SensorMonitorService sensorMonitorService;

    @Autowired
    public SensorMonitorResource(SensorMonitorService sensorMonitorService) {
        this.sensorMonitorService = sensorMonitorService;
    }

    @GetMapping("/generateRandomEvents")
    public ResponseEntity<Void> generateRandomEvents() {
        sensorMonitorService.generateRandomEvents();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
