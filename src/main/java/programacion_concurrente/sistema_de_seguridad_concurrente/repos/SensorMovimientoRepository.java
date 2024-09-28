package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;

import java.util.Set;

public interface SensorMovimientoRepository extends JpaRepository<SensorMovimiento, Integer> {
    SensorMovimiento findFirstBySensorMovimiento(Set<Evento> sensorMovimiento);
}
