package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;

import java.util.List;


public interface EventoRepository extends JpaRepository<Evento, Integer> {

    //List<Evento> findByTipo(String tipoEvento);

    Evento findFirstBysensorTemperatura(SensorTemperatura sensorTemperatura);

    Evento findFirstBysensorMovimiento(SensorMovimiento sensorMovimiento);

    Evento findFirstBysensorAcceso(SensorAcceso sensorAcceso);

    List<Evento> findByTipoEvento(String tipoEvento);
}
