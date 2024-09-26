import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { SensorTemperaturaService } from 'app/sensor-temperatura/sensor-temperatura.service';
import { SensorTemperaturaDTO } from 'app/sensor-temperatura/sensor-temperatura.model';


@Component({
  selector: 'app-sensor-temperatura-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sensor-temperatura-list.component.html'})
export class SensorTemperaturaListComponent implements OnInit, OnDestroy {

  sensorTemperaturaService = inject(SensorTemperaturaService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  sensorTemperaturas?: SensorTemperaturaDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@sensorTemperatura.delete.success:Sensor Temperatura was removed successfully.`,
      'sensorTemperatura.evento.eventos.referenced': $localize`:@@sensorTemperatura.evento.eventos.referenced:This entity is still referenced by Evento ${details?.id} via field Eventos.`
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
    this.sensorTemperaturaService.getAllSensorTemperaturas()
        .subscribe({
          next: (data) => this.sensorTemperaturas = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idSensor: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.sensorTemperaturaService.deleteSensorTemperatura(idSensor)
          .subscribe({
            next: () => this.router.navigate(['/sensorTemperaturas'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/sensorTemperaturas'], {
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
