export class RolDTO {

  constructor(data:Partial<RolDTO>) {
    Object.assign(this, data);
  }

  idRol?: number|null;
  nombre?: string|null;

}
