export class NotificacionDTO {

  constructor(data:Partial<NotificacionDTO>) {
    Object.assign(this, data);
  }

  idNotificacion?: number|null;
  mensage?: string|null;
  notificacion?: number|null;

}
