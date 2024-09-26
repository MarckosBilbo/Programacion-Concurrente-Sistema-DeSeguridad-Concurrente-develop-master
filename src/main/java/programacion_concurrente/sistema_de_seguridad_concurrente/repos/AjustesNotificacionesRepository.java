package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.AjustesNotificaciones;

@Repository
public interface AjustesNotificacionesRepository extends JpaRepository<AjustesNotificaciones, Long> {
}
