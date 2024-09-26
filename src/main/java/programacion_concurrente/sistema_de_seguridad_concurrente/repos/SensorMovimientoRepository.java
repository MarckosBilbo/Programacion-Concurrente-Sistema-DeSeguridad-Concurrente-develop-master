package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;


public interface SensorMovimientoRepository extends JpaRepository<SensorMovimiento, Integer> {

    SensorMovimiento findFirstBySensior(Usuario usuario);

}
