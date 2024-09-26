import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { CredencialesService } from 'app/credenciales/credenciales.service';
import { CredencialesDTO } from 'app/credenciales/credenciales.model';


@Component({
  selector: 'app-credenciales-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './credenciales-list.component.html'})
export class CredencialesListComponent implements OnInit, OnDestroy {

  credencialesService = inject(CredencialesService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  credencialeses?: CredencialesDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@credenciales.delete.success:Credenciales was removed successfully.`,
      'credenciales.usuario.usuario.referenced': $localize`:@@credenciales.usuario.usuario.referenced:This entity is still referenced by Usuario ${details?.id} via field Usuario.`
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
    this.credencialesService.getAllCredencialeses()
        .subscribe({
          next: (data) => this.credencialeses = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(nombre: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.credencialesService.deleteCredenciales(nombre)
          .subscribe({
            next: () => this.router.navigate(['/credencialess'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/credencialess'], {
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
