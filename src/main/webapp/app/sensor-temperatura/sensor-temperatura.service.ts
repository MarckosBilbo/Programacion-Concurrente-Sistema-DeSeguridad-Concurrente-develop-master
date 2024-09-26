import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { SensorTemperaturaDTO } from 'app/sensor-temperatura/sensor-temperatura.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class SensorTemperaturaService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/sensorTemperaturas';

  getAllSensorTemperaturas() {
    return this.http.get<SensorTemperaturaDTO[]>(this.resourcePath);
  }

  getSensorTemperatura(idSensor: number) {
    return this.http.get<SensorTemperaturaDTO>(this.resourcePath + '/' + idSensor);
  }

  createSensorTemperatura(sensorTemperaturaDTO: SensorTemperaturaDTO) {
    return this.http.post<number>(this.resourcePath, sensorTemperaturaDTO);
  }

  updateSensorTemperatura(idSensor: number, sensorTemperaturaDTO: SensorTemperaturaDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idSensor, sensorTemperaturaDTO);
  }

  deleteSensorTemperatura(idSensor: number) {
    return this.http.delete(this.resourcePath + '/' + idSensor);
  }

  getSensoreesValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/sensoreesValues')
        .pipe(map(transformRecordToMap));
  }

}
