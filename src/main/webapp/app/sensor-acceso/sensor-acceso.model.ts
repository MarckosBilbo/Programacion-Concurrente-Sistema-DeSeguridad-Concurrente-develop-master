export class SensorAccesoDTO {

  constructor(data:Partial<SensorAccesoDTO>) {
    Object.assign(this, data);
  }

  idSensor?: number|null;
  nombre?: string|null;
  ubicacion?: string|null;
  respuesta?: string|null;
  sens?: number|null;

}
