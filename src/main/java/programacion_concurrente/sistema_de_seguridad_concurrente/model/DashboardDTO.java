/*package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class DashboardDTO {
    @NotNull
    private Integer sensorId;

    @Size(max = 50)
    @NotNull
    private String sensorType;

    @Size(max = 50)
    @NotNull
    private String status;

    private OffsetDateTime lastReadingTime;

    @Size(max = 50)
    @NotNull
    private String lastReadingValue;

    private OffsetDateTime recentActivityTime;

    @Size(max = 50)
    @NotNull
    private String eventType;

    @Size(max = 255)
    private String eventDescription;
}
*/
