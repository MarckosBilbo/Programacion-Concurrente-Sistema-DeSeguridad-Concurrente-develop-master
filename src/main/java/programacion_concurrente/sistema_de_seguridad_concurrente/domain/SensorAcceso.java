package programacion_concurrente.sistema_de_seguridad_concurrente.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SensorAccesoes")
@Getter
@Setter
public class SensorAcceso extends Sensor {

    @Column(length = 100)
    private String ubicacion;

    @Column(nullable = false)
    private String respuesta;

    @OneToMany(mappedBy = "sensorAcceso")
    private Set<Evento> sensorAcceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sens_id", nullable = false)
    private Usuario sens;

    public OffsetDateTime getLastModifiedDate() {
        return this.getLastUpdated();
    }
}
