import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { NotificacionDTO } from 'app/notificacion/notificacion.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class NotificacionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/notificacions';

  getAllNotificacions() {
    return this.http.get<NotificacionDTO[]>(this.resourcePath);
  }

  getNotificacion(idNotificacion: number) {
    return this.http.get<NotificacionDTO>(this.resourcePath + '/' + idNotificacion);
  }

  createNotificacion(notificacionDTO: NotificacionDTO) {
    return this.http.post<number>(this.resourcePath, notificacionDTO);
  }

  updateNotificacion(idNotificacion: number, notificacionDTO: NotificacionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idNotificacion, notificacionDTO);
  }

  deleteNotificacion(idNotificacion: number) {
    return this.http.delete(this.resourcePath + '/' + idNotificacion);
  }

  getNotificacionValues() {
    return this.http.get<Record<string,number>>(this.resourcePath + '/notificacionValues')
        .pipe(map(transformRecordToMap));
  }

}
