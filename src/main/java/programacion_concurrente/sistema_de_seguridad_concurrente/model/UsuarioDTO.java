package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    @NotNull
    private Integer idUsuario;

    @NotNull
    @Size(max = 100)
    private String nombre;

}


//@NotNull
//@UsuarioUsuarioUnique
//private Integer usuario;

//@NotNull
//@UsuarioUsuariooUnique
//private Long usuarioo;
