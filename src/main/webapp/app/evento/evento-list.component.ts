import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { EventoService } from 'app/evento/evento.service';
import { EventoDTO } from 'app/evento/evento.model';


@Component({
  selector: 'app-evento-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './evento-list.component.html'})
export class EventoListComponent implements OnInit, OnDestroy {

  eventoService = inject(EventoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  eventoes?: EventoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@evento.delete.success:Evento was removed successfully.`,
      'evento.notificacion.notificacion.referenced': $localize`:@@evento.notificacion.notificacion.referenced:This entity is still referenced by Notificacion ${details?.id} via field Notificacion.`
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
    this.eventoService.getAllEventoes()
        .subscribe({
          next: (data) => this.eventoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idEvento: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.eventoService.deleteEvento(idEvento)
          .subscribe({
            next: () => this.router.navigate(['/eventos'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/eventos'], {
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
