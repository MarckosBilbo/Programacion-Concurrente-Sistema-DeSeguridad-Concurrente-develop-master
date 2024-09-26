package programacion_concurrente.sistema_de_seguridad_concurrente.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sensorMovimiento")
@Getter
@Setter
public class SensorMovimiento extends Sensor {

    @Column(length = 100)
    private String ubicacion;

    @OneToMany(mappedBy = "sensorMovimiento")
    private Set<Evento> sensorMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensior_id", nullable = false)
    private Usuario sensior;

    public OffsetDateTime getLastModifiedDate() {
        return this.getLastUpdated();
    }
}
