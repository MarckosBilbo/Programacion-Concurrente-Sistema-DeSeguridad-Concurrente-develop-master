package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;


public interface SensorAccesoRepository extends JpaRepository<SensorAcceso, Integer> {

    SensorAcceso findFirstBySens(Usuario usuario);

}
