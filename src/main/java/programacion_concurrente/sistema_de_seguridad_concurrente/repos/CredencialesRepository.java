package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Credenciales;


public interface CredencialesRepository extends JpaRepository<Credenciales, Integer> {
}
