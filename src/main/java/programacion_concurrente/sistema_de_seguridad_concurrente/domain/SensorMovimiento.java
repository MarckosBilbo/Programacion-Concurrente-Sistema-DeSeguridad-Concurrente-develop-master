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


    @OneToMany(mappedBy = "sensorMovimiento")
    private Set<Evento> sensorMovimiento;


    // Elimina la relación con Usuario
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "sensior_id")
    // private Usuario sensior;

}
