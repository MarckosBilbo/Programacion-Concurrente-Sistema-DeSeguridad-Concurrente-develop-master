# Proyecto de Sistema de Seguridad Concurrente

Repositorio(DEFINITIVO)-->https://github.com/MarckosBilbo/Programacion-Concurrente-Sistema-DeSeguridad-Concurrente-develop-master

Repositorio (NO TERMINADO) para ver commits)-->https://github.com/Programacion-Concurrente/Programacion-Concurrente-Sistema-DeSeguridad-Concurrente


### -Este proyecto consiste en una aplicación de seguridad concurrente desarrollada con Spring Boot en el backend y Angular en el frontend. La aplicación permite la gestión de usuarios y la monitorización de eventos generados por sensores.

## Ejecución del Proyecto
-Backend: Ejecutar la aplicación Spring Boot (Muestra los hilos por consola)
-Frontend: Ejecutar la aplicación Angular (Por desgracia dicho fronend no se abre en base a la ejecucion de la Springboot aplicattion)
-Navegador: Una vez lanzada la aplicattion (Springboot) por consola al menos se puede acceder (mientrar aun "Runnea")  a http://localhost:8080 para interactuar con la aplicación(Angular frontend).



### Funcionalidad General

- **Backend (Spring Boot)**: Gestiona la lógica de negocio, la autenticación de usuarios y la generación de eventos de sensores.
- **Frontend (Angular)**: Proporciona una interfaz de usuario para el inicio de sesión, la gestión de usuarios y la visualización de eventos de sensores.

### Clases Importantes del Backend

#### `Usuario.java`
Define la entidad `Usuario` con atributos como `idUsuario`, `nombre` y `rol`. Los roles pueden ser `ADMIN` o `USER`.

#### `UsuarioRepository.java`
Interfaz que extiende `JpaRepository` para operaciones CRUD en la entidad `Usuario`.

#### `UsuarioService.java`
Servicio que contiene la lógica de negocio relacionada con los usuarios, como el inicio de sesión y la creación de usuarios.

#### `AuthController.java`
Controlador REST que maneja las solicitudes de autenticación y creación de usuarios.

#### `SensorMonitorService.java`
Servicio que gestiona la generación y asignación de eventos a los sensores. Utiliza un `ExecutorService` para manejar la concurrencia.

#### `SensorMonitorResource.java`
Controlador REST que expone un endpoint para generar eventos aleatorios de sensores.

### Clases Secundarias

#### Sensores de Movimiento

- **Dominio**:
  - `SensorMovimiento.java`: Define la entidad `SensorMovimiento` con atributos como `idSensor` y `nombre`.

- **Repositorio**:
  - `SensorMovimientoRepository.java`: Interfaz que extiende `JpaRepository` para operaciones CRUD en la entidad `SensorMovimiento`.

- **Servicio**:
  - `SensorMovimientoService.java`: Servicio que contiene la lógica de negocio relacionada con los sensores de movimiento.

#### Sensores de Temperatura

- **Dominio**:
  - `SensorTemperatura.java`: Define la entidad `SensorTemperatura` con atributos como `idSensor`, `nombre` y `temperatura`.

- **Repositorio**:
  - `SensorTemperaturaRepository.java`: Interfaz que extiende `JpaRepository` para operaciones CRUD en la entidad `SensorTemperatura`.

- **Servicio**:
  - `SensorTemperaturaService.java`: Servicio que contiene la lógica de negocio relacionada con los sensores de temperatura.

#### Sensores de Acceso

- **Dominio**:
  - `SensorAcceso.java`: Define la entidad `SensorAcceso` con atributos como `idSensor` y `nombre`.

- **Repositorio**:
  - `SensorAccesoRepository.java`: Interfaz que extiende `JpaRepository` para operaciones CRUD en la entidad `SensorAcceso`.

- **Servicio**:
  - `SensorAccesoService.java`: Servicio que contiene la lógica de negocio relacionada con los sensores de acceso.

#### Eventos

- **Dominio**:
  - `Evento.java`: Define la entidad `Evento` con atributos como `idEvento`, `tipoEvento`, `descripcion` y referencias a los sensores.

- **Repositorio**:
  - `EventoRepository.java`: Interfaz que extiende `JpaRepository` para operaciones CRUD en la entidad `Evento`.


## DOCKER.COMPOSE.YML:


services:
  
  backend:
    ports:
      - "8080:8080"
    depends_on:
      - db

  
  frontend:
   ports:
      - "80:80"
    depends_on:
      - backend

  db:
    image: mysql:5.7
    environment:
      MYSQL_ROOT_PASSWORD: PROTEGIDA
      MYSQL_DATABASE: Sistema-De-Seguridad-Concurrente
    ports:
      - "3307:3306"


### Configuración de Rutas en Angular

```typescript
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminDashboardComponent } from './admin/admin-dashboard.component';
import { UserDashboardComponent } from './user/user-dashboard.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'admin/dashboard', component: AdminDashboardComponent },
  { path: 'user/dashboard', component: UserDashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

