package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.ConfiguracionSistema;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.ConfiguracionSistemaServiceNotification;

@RestController
@RequestMapping("/configuracion-sistema")
public class ConfiguracionSistemaControllerNotification {
    @Autowired
    private ConfiguracionSistemaServiceNotification configuracionSistemaService;

    @GetMapping
    public ConfiguracionSistema obtenerConfiguracion() {
        return configuracionSistemaService.obtenerConfiguracion();
    }

    @PostMapping
    public ConfiguracionSistema guardarConfiguracion(@RequestBody ConfiguracionSistema configuracion) {
        return configuracionSistemaService.guardarConfiguracion(configuracion);
    }
}
