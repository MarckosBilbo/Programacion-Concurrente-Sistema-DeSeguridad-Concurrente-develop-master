import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { NotificacionService } from 'app/notificacion/notificacion.service';
import { NotificacionDTO } from 'app/notificacion/notificacion.model';


@Component({
  selector: 'app-notificacion-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './notificacion-list.component.html'})
export class NotificacionListComponent implements OnInit, OnDestroy {

  notificacionService = inject(NotificacionService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  notificacions?: NotificacionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@notificacion.delete.success:Notificacion was removed successfully.`    };
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
    this.notificacionService.getAllNotificacions()
        .subscribe({
          next: (data) => this.notificacions = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idNotificacion: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.notificacionService.deleteNotificacion(idNotificacion)
          .subscribe({
            next: () => this.router.navigate(['/notificacions'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}
