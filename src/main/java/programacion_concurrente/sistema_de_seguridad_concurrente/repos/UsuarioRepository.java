package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreAndIdUsuario(String nombre, Integer idUsuario);
}
