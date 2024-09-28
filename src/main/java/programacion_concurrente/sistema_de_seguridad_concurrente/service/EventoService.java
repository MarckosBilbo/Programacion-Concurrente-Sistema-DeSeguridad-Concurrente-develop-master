package programacion_concurrente.sistema_de_seguridad_concurrente.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Evento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.Notificacion;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorAcceso;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorMovimiento;
import programacion_concurrente.sistema_de_seguridad_concurrente.domain.SensorTemperatura;
import programacion_concurrente.sistema_de_seguridad_concurrente.model.EventoDTO;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.EventoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.NotificacionRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorAccesoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorMovimientoRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.repos.SensorTemperaturaRepository;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.NotFoundException;
import programacion_concurrente.sistema_de_seguridad_concurrente.util.ReferencedWarning;


@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorAccesoRepository sensorAccesoRepository;
    private final NotificacionRepository notificacionRepository;

    public EventoService(final EventoRepository eventoRepository,
                         final SensorTemperaturaRepository sensorTemperaturaRepository,
                         final SensorMovimientoRepository sensorMovimientoRepository,
                         final SensorAccesoRepository sensorAccesoRepository,
                         final NotificacionRepository notificacionRepository) {
        this.eventoRepository = eventoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
        this.notificacionRepository = notificacionRepository;
    }

    public List<EventoDTO> findAll() {
        final List<Evento> Eventos = eventoRepository.findAll(Sort.by("idEvento"));
        return Eventos.stream()
            .map(evento -> mapToDTO(evento, new EventoDTO()))
            .toList();
    }

    public EventoDTO get(final Integer idEvento) {
        return eventoRepository.findById(idEvento)
            .map(evento -> mapToDTO(evento, new EventoDTO()))
            .orElseThrow(NotFoundException::new);
    }

    public Integer create(final EventoDTO eventoDTO) {
        final Evento evento = new Evento();
        mapToEntity(eventoDTO, evento);
        return eventoRepository.save(evento).getIdEvento();
    }

    public void update(final Integer idEvento, final EventoDTO eventoDTO) {
        final Evento evento = eventoRepository.findById(idEvento)
            .orElseThrow(NotFoundException::new);
        mapToEntity(eventoDTO, evento);
        eventoRepository.save(evento);
    }

    public void delete(final Integer idEvento) {
        eventoRepository.deleteById(idEvento);
    }

    private EventoDTO mapToDTO(final Evento evento, final EventoDTO eventoDTO) {

        eventoDTO.setIdEvento(evento.getIdEvento());
        eventoDTO.setSensorTemperatura(evento.getSensorTemperatura() == null ? null : evento.getSensorTemperatura().getIdSensor());
        eventoDTO.setSensorMovimiento(evento.getSensorMovimiento() == null ? null : evento.getSensorMovimiento().getIdSensor());
        eventoDTO.setSensorAcceso(evento.getSensorAcceso() == null ? null : evento.getSensorAcceso().getIdSensor());
        return eventoDTO;
    }

    private Evento mapToEntity(final EventoDTO eventoDTO, final Evento evento) {


        final SensorTemperatura sensorTemperatura = eventoDTO.getSensorTemperatura() == null ? null : sensorTemperaturaRepository.findById(eventoDTO.getSensorTemperatura())
            .orElseThrow(() -> new NotFoundException("SensorTemperatura not found"));
        evento.setSensorTemperatura(sensorTemperatura);

        final SensorMovimiento sensorMovimiento = eventoDTO.getSensorMovimiento() == null ? null : sensorMovimientoRepository.findById(eventoDTO.getSensorMovimiento())
            .orElseThrow(() -> new NotFoundException("SensorMovimiento not found"));
        evento.setSensorMovimiento(sensorMovimiento);

        final SensorAcceso sensorAcceso = eventoDTO.getSensorAcceso() == null ? null : sensorAccesoRepository.findById(eventoDTO.getSensorAcceso())
            .orElseThrow(() -> new NotFoundException("SensorAcceso not found"));
        evento.setSensorAcceso(sensorAcceso);

        return evento;
    }

    public ReferencedWarning getReferencedWarning(final Integer idEvento) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Evento evento = eventoRepository.findById(idEvento)
            .orElseThrow(NotFoundException::new);
        final Notificacion notificacionNotificacion = notificacionRepository.findFirstByNotificacion(evento);
        if (notificacionNotificacion != null) {
            referencedWarning.setKey("evento.notificacion.notificacion.referenced");
            referencedWarning.addParam(notificacionNotificacion.getIdNotificacion());
            return referencedWarning;
        }
        return null;
    }

    public List<EventoDTO> getEventosByTipo(String tipo) {
        final List<Evento> eventos = eventoRepository.findByTipoEvento(tipo);
        return eventos.stream()
            .map(evento -> mapToDTO(evento, new EventoDTO()))
            .toList();
    }
}

