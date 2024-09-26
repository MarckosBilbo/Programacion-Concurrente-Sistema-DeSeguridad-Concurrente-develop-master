package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Credenciales;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Rol;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByUsuario(Credenciales credenciales);

    Usuario findFirstByUsuarioo(Rol rol);

    boolean existsByUsuarioNombre(Integer nombre);

    boolean existsByUsuariooIdRol(Long idRol);

}
