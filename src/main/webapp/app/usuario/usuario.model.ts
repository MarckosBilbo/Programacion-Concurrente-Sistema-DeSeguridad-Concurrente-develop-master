export class UsuarioDTO {

  constructor(data:Partial<UsuarioDTO>) {
    Object.assign(this, data);
  }

  idUsuario?: number|null;
  nombre?: string|null;
  email?: string|null;
  activo?: boolean|null;
  usuario?: number|null;
  usuarioo?: number|null;

}
