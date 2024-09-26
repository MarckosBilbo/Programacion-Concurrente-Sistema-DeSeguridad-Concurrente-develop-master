import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { RolDTO } from 'app/rol/rol.model';


@Injectable({
  providedIn: 'root',
})
export class RolService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/rols';

  getAllRols() {
    return this.http.get<RolDTO[]>(this.resourcePath);
  }

  getRol(idRol: number) {
    return this.http.get<RolDTO>(this.resourcePath + '/' + idRol);
  }

  createRol(rolDTO: RolDTO) {
    return this.http.post<number>(this.resourcePath, rolDTO);
  }

  updateRol(idRol: number, rolDTO: RolDTO) {
    return this.http.put<number>(this.resourcePath + '/' + idRol, rolDTO);
  }

  deleteRol(idRol: number) {
    return this.http.delete(this.resourcePath + '/' + idRol);
  }

}
