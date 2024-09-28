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


    @OneToMany(mappedBy = "sensorAcceso")
    private Set<Evento> sensorAcceso;


    // Elimina la relación con Usuario
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "sens_id")
    // private Usuario sens;

}
