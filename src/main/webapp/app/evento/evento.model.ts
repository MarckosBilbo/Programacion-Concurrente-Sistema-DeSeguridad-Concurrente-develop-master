export class EventoDTO {

  constructor(data:Partial<EventoDTO>) {
    Object.assign(this, data);
  }

  idEvento?: number|null;
  nivelCriticidad?: string|null;
  eventos?: number|null;
  eventoss?: number|null;
  eventosss?: number|null;

}
