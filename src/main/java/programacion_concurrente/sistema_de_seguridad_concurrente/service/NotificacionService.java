package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Notificacion;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.NotificacionDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.EventoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.NotificacionRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;


@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final EventoRepository eventoRepository;

    public NotificacionService(final NotificacionRepository notificacionRepository,
            final EventoRepository eventoRepository) {
        this.notificacionRepository = notificacionRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<NotificacionDTO> findAll() {
        final List<Notificacion> notificacions = notificacionRepository.findAll(Sort.by("idNotificacion"));
        return notificacions.stream()
                .map(notificacion -> mapToDTO(notificacion, new NotificacionDTO()))
                .toList();
    }

    public NotificacionDTO get(final Integer idNotificacion) {
        return notificacionRepository.findById(idNotificacion)
                .map(notificacion -> mapToDTO(notificacion, new NotificacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final NotificacionDTO notificacionDTO) {
        final Notificacion notificacion = new Notificacion();
        mapToEntity(notificacionDTO, notificacion);
        return notificacionRepository.save(notificacion).getIdNotificacion();
    }

    public void update(final Integer idNotificacion, final NotificacionDTO notificacionDTO) {
        final Notificacion notificacion = notificacionRepository.findById(idNotificacion)
                .orElseThrow(NotFoundException::new);
        mapToEntity(notificacionDTO, notificacion);
        notificacionRepository.save(notificacion);
    }

    public void delete(final Integer idNotificacion) {
        notificacionRepository.deleteById(idNotificacion);
    }

    private NotificacionDTO mapToDTO(final Notificacion notificacion,
            final NotificacionDTO notificacionDTO) {
        notificacionDTO.setIdNotificacion(notificacion.getIdNotificacion());
        notificacionDTO.setMensage(notificacion.getMensage());
        notificacionDTO.setNotificacion(notificacion.getNotificacion() == null ? null : notificacion.getNotificacion().getIdEvento());
        return notificacionDTO;
    }

    private Notificacion mapToEntity(final NotificacionDTO notificacionDTO,
                                     final Notificacion notificacion) {
        notificacion.setMensage(notificacionDTO.getMensage());
        final Evento evento = notificacionDTO.getNotificacion() == null ? null : eventoRepository.findById(notificacionDTO.getNotificacion())
            .orElseThrow(() -> new NotFoundException("notificacion not found"));
        notificacion.setNotificacion(evento);
        return notificacion;
    }

    public boolean notificacionExists(final Integer idEvento) {
        return notificacionRepository.existsByNotificacionIdEvento(idEvento);
    }
}
