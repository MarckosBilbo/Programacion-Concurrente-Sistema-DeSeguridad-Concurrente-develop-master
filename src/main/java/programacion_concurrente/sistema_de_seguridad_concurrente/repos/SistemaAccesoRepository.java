package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SistemaAcceso;

@Repository
public interface SistemaAccesoRepository extends JpaRepository<SistemaAcceso, Long> {


}
