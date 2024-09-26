import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { SensorAccesoDTO } from 'app/sensor-acceso/sensor-acceso.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class SensorAccesoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/sensorAccesos';

  getAllSensorAccesoes() {
    return this.http.get<SensorAccesoDTO[]>(this.resourcePath);
  }

  getSensorAcceso(idSensor: number) {
    return this.http.get<SensorAccesoDTO>(this.resourcePath + '/' + idSensor);
  }

  createSensorAcceso(sensorAccesoDTO: SensorAccesoDTO) {
    return this.http.post<number>(this.resourcePath, sensorAccesoDTO);
  }

  updateSensorAcceso(idSensor: number, sensorAccesoDTO: SensorAccesoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idSensor, sensorAccesoDTO);
  }

  deleteSensorAcceso(idSensor: number) {
    return this.http.delete(this.resourcePath + '/' + idSensor);
  }

  getSensValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/sensValues')
        .pipe(map(transformRecordToMap));
  }

}
