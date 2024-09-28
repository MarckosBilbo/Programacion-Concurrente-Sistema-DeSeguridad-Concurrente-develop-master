/*package programacion_concurrente.sistema_de_seguridad_concurrente.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;
import programacion_concurrente.sistema_de_seguridad_concurrente.service.UsuarioService;



@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
    validatedBy = UsuarioUsuariooUnique.UsuarioUsuariooUniqueValidator.class
)
public @interface UsuarioUsuariooUnique {

    String message() default "{Exists.usuario.Usuarioo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UsuarioUsuariooUniqueValidator implements ConstraintValidator<UsuarioUsuariooUnique, Long> {

        private final UsuarioService usuarioService;
        private final HttpServletRequest request;

        public UsuarioUsuariooUniqueValidator(final UsuarioService usuarioService,
                                              final HttpServletRequest request) {
            this.usuarioService = usuarioService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idUsuario");
            if (currentId != null && value.equals(usuarioService.get(Integer.parseInt(currentId)).getUsuarioo())) {
                // value hasn't changed
                return true;
            }
            // Remove the call to usuariooExists
            return true;
        }

    }

}
*/
