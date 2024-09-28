package programacion_concurrente.sistema_de_seguridad_concurrente.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SensorTemperaturas")
@Getter
@Setter
public class SensorTemperatura extends Sensor {


    @Column(nullable = false)
    private Integer temperatura;

    @OneToMany(mappedBy = "sensorTemperatura")
    private Set<Evento> sensorTemperatura;

    // Elimina la relación con Usuario
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "sensorees_id")
    // private Usuario sensorees;

}
