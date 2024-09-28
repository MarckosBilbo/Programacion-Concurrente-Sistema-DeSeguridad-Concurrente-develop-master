package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;

import java.util.Set;


@Getter
@Setter
public class SensorTemperaturaDTO {

    @NotNull
    private Integer idSensor;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @NotNull
    private Integer temperatura;

    @NotNull
    private Set<Evento> sensorTemperatura;


    //@NotNull
    //private Integer sensorees;

}
