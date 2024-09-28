package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;

import java.util.Set;

public interface SensorAccesoRepository extends JpaRepository<SensorAcceso, Integer> {
    SensorAcceso findFirstBySensorAcceso(Set<Evento> sensorAcceso);
}
