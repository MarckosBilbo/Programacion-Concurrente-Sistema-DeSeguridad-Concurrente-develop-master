import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CredencialesDTO } from 'app/credenciales/credenciales.model';


@Injectable({
  providedIn: 'root',
})
export class CredencialesService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/credencialess';

  getAllCredencialeses() {
    return this.http.get<CredencialesDTO[]>(this.resourcePath);
  }

  getCredenciales(nombre: number) {
    return this.http.get<CredencialesDTO>(this.resourcePath + '/' + nombre);
  }

  createCredenciales(credencialesDTO: CredencialesDTO) {
    return this.http.post<number>(this.resourcePath, credencialesDTO);
  }

  updateCredenciales(nombre: number, credencialesDTO: CredencialesDTO) {
    return this.http.put<number>(this.resourcePath + '/' + nombre, credencialesDTO);
  }

  deleteCredenciales(nombre: number) {
    return this.http.delete(this.resourcePath + '/' + nombre);
  }

}
