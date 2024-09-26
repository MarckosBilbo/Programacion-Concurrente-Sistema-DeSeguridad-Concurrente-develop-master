import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { EventoDTO } from 'app/evento/evento.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class EventoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/eventos';

  getAllEventoes() {
    return this.http.get<EventoDTO[]>(this.resourcePath);
  }

  getEvento(idEvento: number) {
    return this.http.get<EventoDTO>(this.resourcePath + '/' + idEvento);
  }

  createEvento(eventoDTO: EventoDTO) {
    return this.http.post<number>(this.resourcePath, eventoDTO);
  }

  updateEvento(idEvento: number, eventoDTO: EventoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idEvento, eventoDTO);
  }

  deleteEvento(idEvento: number) {
    return this.http.delete(this.resourcePath + '/' + idEvento);
  }

  getEventosValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/eventosValues')
        .pipe(map(transformRecordToMap));
  }

  getEventossValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/eventossValues')
        .pipe(map(transformRecordToMap));
  }

  getEventosssValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/eventosssValues')
        .pipe(map(transformRecordToMap));
  }

}
