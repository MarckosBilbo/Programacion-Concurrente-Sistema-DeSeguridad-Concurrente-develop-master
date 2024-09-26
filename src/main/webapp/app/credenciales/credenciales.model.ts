export class CredencialesDTO {

  constructor(data:Partial<CredencialesDTO>) {
    Object.assign(this, data);
  }

  nombre?: number|null;
  contrasenia?: string|null;

}
