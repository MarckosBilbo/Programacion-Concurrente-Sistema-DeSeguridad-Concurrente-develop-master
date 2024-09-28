package programacion_concurrente.sistema_de_seguridad_concurrente.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;

import java.util.Set;

public interface SensorTemperaturaRepository extends JpaRepository<SensorTemperatura, Integer> {

    SensorTemperatura findFirstBySensorTemperatura(Set<Evento> sensorTemperatura);

}
