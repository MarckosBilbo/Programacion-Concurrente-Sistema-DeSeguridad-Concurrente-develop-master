package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorMonitorDTO {

    @NotNull
    private Integer idSensor;

    @Size(max = 50)
    @NotNull
    private String sensorType;

    @Size(max = 50)
    @NotNull
    private String status;

    private OffsetDateTime lastReadingTime;

    @Size(max = 50)
    private String lastReadingValue;
    private OffsetDateTime lastDetectionTime;
}
