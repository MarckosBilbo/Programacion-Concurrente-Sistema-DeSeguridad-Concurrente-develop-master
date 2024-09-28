/*package programacion_concurrente.sistema_de_seguridad_concurrente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SistemaAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SistemaAccesoService;

@RestController
@RequestMapping("/sistema-acceso")
public class SistemaAccesoController {
    @Autowired
    private SistemaAccesoService sistemaAccesoService;

    @GetMapping
    public SistemaAcceso getEstado() {
        return sistemaAccesoService.getEstadoSistema();
    }

    @PostMapping
    public SistemaAcceso cambiarEstado(@RequestBody SistemaAcceso sistemaAcceso) {
        return sistemaAccesoService.cambiarEstado(sistemaAcceso);
    }
}


*/
