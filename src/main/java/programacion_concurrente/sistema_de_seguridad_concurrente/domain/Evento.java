package programacion_concurrente.sistema_de_seguridad_concurrente.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Eventos")
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvento;

    @Column(nullable = false)
    private String tipoEvento;

    @Column(nullable = false)
    private String nivelCriticidad;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_acceso_id")
    private SensorAcceso sensorAcceso;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_movimiento_id")
    private SensorMovimiento sensorMovimiento;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_temperatura_id")
    private SensorTemperatura sensorTemperatura;


    @Column(nullable = false)
    private OffsetDateTime fechaHora;


    @Column(nullable = false)
    private String descripcion;


    @OneToOne
    @JoinColumn(name = "notificacion_id")
    private Notificacion notificacion;

}

