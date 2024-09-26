import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { SensorMovimientoService } from 'app/sensor-movimiento/sensor-movimiento.service';
import { SensorMovimientoDTO } from 'app/sensor-movimiento/sensor-movimiento.model';


@Component({
  selector: 'app-sensor-movimiento-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sensor-movimiento-list.component.html'})
export class SensorMovimientoListComponent implements OnInit, OnDestroy {

  sensorMovimientoService = inject(SensorMovimientoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  sensorMovimientoes?: SensorMovimientoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@sensorMovimiento.delete.success:Sensor Movimiento was removed successfully.`,
      'sensorMovimiento.evento.eventoss.referenced': $localize`:@@sensorMovimiento.evento.eventoss.referenced:This entity is still referenced by Evento ${details?.id} via field Eventoss.`
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
    this.sensorMovimientoService.getAllSensorMovimientoes()
        .subscribe({
          next: (data) => this.sensorMovimientoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(idSensor: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.sensorMovimientoService.deleteSensorMovimiento(idSensor)
          .subscribe({
            next: () => this.router.navigate(['/sensorMovimientos'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/sensorMovimientos'], {
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
