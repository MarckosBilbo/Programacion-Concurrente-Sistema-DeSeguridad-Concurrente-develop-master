import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { UsuarioDTO } from 'app/usuario/usuario.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/usuarios';

  getAllUsuarios() {
    return this.http.get<UsuarioDTO[]>(this.resourcePath);
  }

  getUsuario(idUsuario: number) {
    return this.http.get<UsuarioDTO>(this.resourcePath + '/' + idUsuario);
  }

  createUsuario(usuarioDTO: UsuarioDTO) {
    return this.http.post<number>(this.resourcePath, usuarioDTO);
  }

  updateUsuario(idUsuario: number, usuarioDTO: UsuarioDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idUsuario, usuarioDTO);
  }

  deleteUsuario(idUsuario: number) {
    return this.http.delete(this.resourcePath + '/' + idUsuario);
  }

  getUsuarioValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/usuarioValues')
        .pipe(map(transformRecordToMap));
  }

  getUsuariooValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/usuariooValues')
        .pipe(map(transformRecordToMap));
  }

}
