package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class EventoDTO {


    @NotNull
    private Integer idEvento;

    @Size(max = 50)
    private String nivelCriticidad;

    private OffsetDateTime fechaHora;

    @NotNull
    @Size(max = 50)
    private String TipoEvento;

    @Size(max = 255)
    private String descripcion;

    @NotNull
    private Integer sensorTemperatura;

    @NotNull
    private Integer sensorMovimiento;

    @NotNull
    private Integer sensorAcceso;

}

