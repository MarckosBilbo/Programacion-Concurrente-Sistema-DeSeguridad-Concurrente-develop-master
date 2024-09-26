import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { UsuarioService } from 'app/usuario/usuario.service';
import { UsuarioDTO } from 'app/usuario/usuario.model';


@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './usuario-list.component.html'})
export class UsuarioListComponent implements OnInit, OnDestroy {

  usuarioService = inject(UsuarioService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  usuarios?: UsuarioDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@usuario.delete.success:Usuario was removed successfully.`,
      'usuario.sensorTemperatura.sensorees.referenced': $localize`:@@usuario.sensorTemperatura.sensorees.referenced:This entity is still referenced by Sensor Temperatura ${details?.id} via field Sensorees.`,
      'usuario.sensorMovimiento.sensior.referenced': $localize`:@@usuario.sensorMovimiento.sensior.referenced:This entity is still referenced by Sensor Movimiento ${details?.id} via field Sensior.`,
      'usuario.sensorAcceso.sens.referenced': $localize`:@@usuario.sensorAcceso.sens.referenced:This entity is still referenced by Sensor Acceso ${details?.id} via field Sens.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.usuarioService.getAllUsuarios()
        .subscribe({
          next: (data) => this.usuarios = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idUsuario: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.usuarioService.deleteUsuario(idUsuario)
          .subscribe({
            next: () => this.router.navigate(['/usuarios'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/usuarios'], {
                  state: {
                    msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                  }
                });
                return;
              }
              this.errorHandler.handleServerError(error.error)
            }
          });
    }
  }

}
