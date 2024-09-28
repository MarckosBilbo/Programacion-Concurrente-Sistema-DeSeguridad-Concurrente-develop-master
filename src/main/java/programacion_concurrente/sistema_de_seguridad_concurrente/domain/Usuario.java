package programacion_concurrente.sistema_de_seguridad_concurrente.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public enum Rol {
        ADMIN,
        USER
        }
    }


    //@OneToMany(mappedBy = "sensorees")
    //private Set<SensorTemperatura> usuariooo;

    // Elimina las relaciones con sensores
    // @OneToMany(mappedBy = "sensior")
    // private Set<SensorMovimiento> usuaroi;

    // @OneToMany(mappedBy = "sens")
    // private Set<SensorAcceso> usuar;

    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    // private Credenciales usuario;


