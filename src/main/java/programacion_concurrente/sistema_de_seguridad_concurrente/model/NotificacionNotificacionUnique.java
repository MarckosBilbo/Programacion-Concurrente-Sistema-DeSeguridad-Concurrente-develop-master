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
import programacion_concurrente.sistema_de_seguridad_concurrente.service.NotificacionService;



@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = NotificacionNotificacionUnique.NotificacionNotificacionUniqueValidator.class
)
public @interface NotificacionNotificacionUnique {

    String message() default "{Exists.notificacion.Notificacion}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NotificacionNotificacionUniqueValidator implements ConstraintValidator<NotificacionNotificacionUnique, Integer> {

        private final NotificacionService notificacionService;
        private final HttpServletRequest request;

        public NotificacionNotificacionUniqueValidator(
                final NotificacionService notificacionService, final HttpServletRequest request) {
            this.notificacionService = notificacionService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Integer value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idNotificacion");
            if (currentId != null && value.equals(notificacionService.get(Integer.parseInt(currentId)).getNotificacion())) {
                // value hasn't changed
                return true;
            }
            return !notificacionService.notificacionExists(value);
        }

    }

}
*/
