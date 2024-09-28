/*package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.ConfiguracionSistema;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.ConfiguracionSistemaRepositoryNotification;

@Service
public class ConfiguracionSistemaServiceNotification {
    @Autowired
    private ConfiguracionSistemaRepositoryNotification configuracionSistemaRepository;

    public ConfiguracionSistema obtenerConfiguracion() {
        return configuracionSistemaRepository.findById(1L).orElse(new ConfiguracionSistema());
    }

    public ConfiguracionSistema guardarConfiguracion(ConfiguracionSistema configuracion) {
        return configuracionSistemaRepository.save(configuracion);
    }
}
*/
