package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SistemaAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SistemaAccesoRepository;

@Service
public class SistemaAccesoService {
    @Autowired
    private SistemaAccesoRepository sistemaAccesoRepository;

    public SistemaAcceso getEstadoSistema() {
        return sistemaAccesoRepository.findById(1L).orElse(new SistemaAcceso());
    }

    public SistemaAcceso cambiarEstado(SistemaAcceso sistemaAcceso) {
        return sistemaAccesoRepository.save(sistemaAcceso);
    }
}
