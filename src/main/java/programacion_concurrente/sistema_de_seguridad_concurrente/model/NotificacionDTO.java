package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotificacionDTO {

    private Integer idNotificacion;

    @Size(max = 255)
    private String mensage;

    @NotNull
    @NotificacionNotificacionUnique
    private Integer notificacion;

}
