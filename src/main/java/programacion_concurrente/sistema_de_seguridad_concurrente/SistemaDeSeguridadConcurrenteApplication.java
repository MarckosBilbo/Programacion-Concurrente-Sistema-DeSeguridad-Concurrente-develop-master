/*
// SistemaDeSeguridadConcurrenteApplication.java
package programacion_concurrente.sistema_de_seguridad_concurrente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Usuario;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.UsuarioRepository;

@SpringBootApplication
public class SistemaDeSeguridadConcurrenteApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static void main(String[] args) {
        SpringApplication.run(SistemaDeSeguridadConcurrenteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario Admin predefinido si no existe
        if (usuarioRepository.findByNombreAndIdUsuario("admin", 1234).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setIdUsuario(6969);
            admin.setNombre("papito");
            admin.setRol(Usuario.Rol.ADMIN);
            usuarioRepository.save(admin);
        }
    }
}
*/







package programacion_concurrente.sistema_de_seguridad_concurrente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.SensorMonitorService;

@SpringBootApplication
public class SistemaDeSeguridadConcurrenteApplication implements CommandLineRunner {

    private final SensorMonitorService sensorMonitorService;

    @Autowired
    public SistemaDeSeguridadConcurrenteApplication(SensorMonitorService sensorMonitorService) {
        this.sensorMonitorService = sensorMonitorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SistemaDeSeguridadConcurrenteApplication.class, args)
            .getBean(SistemaDeSeguridadConcurrenteApplication.class)
            .startSensorMonitoring();
    }

    @Override
    public void run(String... args) throws Exception {
        // No need to initialize sensors here as it's done in SensorMonitorService
    }

    public void startSensorMonitoring() {
        sensorMonitorService.generateRandomEvents();
        sensorMonitorService.shutdownExecutorService();
    }
}












/*package programacion_concurrente.sistema_de_seguridad_concurrente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SistemaDeSeguridadConcurrenteApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SistemaDeSeguridadConcurrenteApplication.class, args);
    }

}

*/
