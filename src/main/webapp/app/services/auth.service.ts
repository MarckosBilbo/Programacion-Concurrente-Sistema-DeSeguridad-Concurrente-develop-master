import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(nombre: string, idUsuario: number): Observable<string> {
    return this.http.post<string>('/api/auth/login', { nombre, idUsuario });
  }

  createUser(nombre: string): Observable<string> {
    return this.http.post<string>('/api/auth/createUser', { nombre });
  }
}
