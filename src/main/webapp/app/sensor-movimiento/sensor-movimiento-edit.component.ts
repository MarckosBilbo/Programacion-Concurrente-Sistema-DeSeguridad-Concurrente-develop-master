import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SensorMovimientoService } from 'app/sensor-movimiento/sensor-movimiento.service';
import { SensorMovimientoDTO } from 'app/sensor-movimiento/sensor-movimiento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-sensor-movimiento-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './sensor-movimiento-edit.component.html'
})
export class SensorMovimientoEditComponent implements OnInit {

  sensorMovimientoService = inject(SensorMovimientoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  sensiorValues?: Map<number,string>;
  currentIdSensor?: number;

  editForm = new FormGroup({
    idSensor: new FormControl({ value: null, disabled: true }),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    ubicacion: new FormControl(null, [Validators.maxLength(100)]),
    sensior: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@sensorMovimiento.update.success:Sensor Movimiento was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdSensor = +this.route.snapshot.params['idSensor'];
    this.sensorMovimientoService.getSensiorValues()
        .subscribe({
          next: (data) => this.sensiorValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.sensorMovimientoService.getSensorMovimiento(this.currentIdSensor!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new SensorMovimientoDTO(this.editForm.value);
    this.sensorMovimientoService.updateSensorMovimiento(this.currentIdSensor!, data)
        .subscribe({
          next: () => this.router.navigate(['/sensorMovimientos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
