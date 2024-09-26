export class SensorMovimientoDTO {

  constructor(data:Partial<SensorMovimientoDTO>) {
    Object.assign(this, data);
  }

  idSensor?: number|null;
  nombre?: string|null;
  ubicacion?: string|null;
  sensior?: number|null;

}
