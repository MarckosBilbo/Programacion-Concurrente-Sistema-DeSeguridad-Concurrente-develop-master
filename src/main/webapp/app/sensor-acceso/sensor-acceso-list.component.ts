import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { SensorAccesoService } from 'app/sensor-acceso/sensor-acceso.service';
import { SensorAccesoDTO } from 'app/sensor-acceso/sensor-acceso.model';


@Component({
  selector: 'app-sensor-acceso-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sensor-acceso-list.component.html'})
export class SensorAccesoListComponent implements OnInit, OnDestroy {

  sensorAccesoService = inject(SensorAccesoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  sensorAccesoes?: SensorAccesoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@sensorAcceso.delete.success:Sensor Acceso was removed successfully.`,
      'sensorAcceso.evento.eventosss.referenced': $localize`:@@sensorAcceso.evento.eventosss.referenced:This entity is still referenced by Evento ${details?.id} via field Eventosss.`
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
    this.sensorAccesoService.getAllSensorAccesoes()
        .subscribe({
          next: (data) => this.sensorAccesoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idSensor: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.sensorAccesoService.deleteSensorAcceso(idSensor)
          .subscribe({
            next: () => this.router.navigate(['/sensorAccesos'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/sensorAccesos'], {
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
