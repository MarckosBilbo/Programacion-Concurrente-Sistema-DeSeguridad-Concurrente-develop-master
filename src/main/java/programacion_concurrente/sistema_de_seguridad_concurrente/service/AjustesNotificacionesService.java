package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.AjustesNotificaciones;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.AjustesNotificacionesRepository;

@Service
public class AjustesNotificacionesService {
    @Autowired
    private AjustesNotificacionesRepository ajustesNotificacionesRepository;

    public AjustesNotificaciones obtenerAjustes() {
        return ajustesNotificacionesRepository.findById(1L).orElse(new AjustesNotificaciones());
    }

    public AjustesNotificaciones guardarAjustes(AjustesNotificaciones ajustes) {
        return ajustesNotificacionesRepository.save(ajustes);
    }
}
