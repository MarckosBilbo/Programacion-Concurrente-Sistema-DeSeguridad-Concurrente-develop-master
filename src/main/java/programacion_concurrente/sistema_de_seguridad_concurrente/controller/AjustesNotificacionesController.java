package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.AjustesNotificaciones;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.AjustesNotificacionesService;

@RestController
@RequestMapping("/ajustes-notificaciones")
public class AjustesNotificacionesController {
    @Autowired
    private AjustesNotificacionesService ajustesNotificacionesService;

    @GetMapping
    public AjustesNotificaciones obtenerAjustes() {
        return ajustesNotificacionesService.obtenerAjustes();
    }

    @PostMapping
    public AjustesNotificaciones guardarAjustes(@RequestBody AjustesNotificaciones ajustes) {
        return ajustesNotificacionesService.guardarAjustes(ajustes);
    }
}
