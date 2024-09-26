import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { SensorMovimientoDTO } from 'app/sensor-movimiento/sensor-movimiento.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class SensorMovimientoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/sensorMovimientos';

  getAllSensorMovimientoes() {
    return this.http.get<SensorMovimientoDTO[]>(this.resourcePath);
  }

  getSensorMovimiento(idSensor: number) {
    return this.http.get<SensorMovimientoDTO>(this.resourcePath + '/' + idSensor);
  }

  createSensorMovimiento(sensorMovimientoDTO: SensorMovimientoDTO) {
    return this.http.post<number>(this.resourcePath, sensorMovimientoDTO);
  }

  updateSensorMovimiento(idSensor: number, sensorMovimientoDTO: SensorMovimientoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idSensor, sensorMovimientoDTO);
  }

  deleteSensorMovimiento(idSensor: number) {
    return this.http.delete(this.resourcePath + '/' + idSensor);
  }

  getSensiorValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/sensiorValues')
        .pipe(map(transformRecordToMap));
  }

}
