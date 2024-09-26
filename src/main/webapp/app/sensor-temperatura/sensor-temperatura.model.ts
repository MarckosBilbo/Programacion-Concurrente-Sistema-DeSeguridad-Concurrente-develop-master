export class SensorTemperaturaDTO {

  constructor(data:Partial<SensorTemperaturaDTO>) {
    Object.assign(this, data);
  }

  idSensor?: number|null;
  nombre?: string|null;
  ubicacion?: string|null;
  temperatura?: number|null;
  sensorees?: number|null;

}
