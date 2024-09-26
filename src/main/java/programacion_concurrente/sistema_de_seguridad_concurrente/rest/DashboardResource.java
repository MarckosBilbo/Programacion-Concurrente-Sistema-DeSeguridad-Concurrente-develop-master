package programacion_concurrente.sistema_de_seguridad_concurrente.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.DashboardService;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.DashboardDTO;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<List<DashboardDTO>> getDashboardData() {
        List<DashboardDTO> dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}
