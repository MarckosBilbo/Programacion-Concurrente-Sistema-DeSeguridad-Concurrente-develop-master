import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SensorAccesoService } from 'app/sensor-acceso/sensor-acceso.service';
import { SensorAccesoDTO } from 'app/sensor-acceso/sensor-acceso.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-sensor-acceso-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './sensor-acceso-edit.component.html'
})
export class SensorAccesoEditComponent implements OnInit {

  sensorAccesoService = inject(SensorAccesoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  sensValues?: Map<number,string>;
  currentIdSensor?: number;

  editForm = new FormGroup({
    idSensor: new FormControl({ value: null, disabled: true }),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    ubicacion: new FormControl(null, [Validators.maxLength(100)]),
    respuesta: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    sens: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@sensorAcceso.update.success:Sensor Acceso was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdSensor = +this.route.snapshot.params['idSensor'];
    this.sensorAccesoService.getSensValues()
        .subscribe({
          next: (data) => this.sensValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.sensorAccesoService.getSensorAcceso(this.currentIdSensor!)
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
    const data = new SensorAccesoDTO(this.editForm.value);
    this.sensorAccesoService.updateSensorAcceso(this.currentIdSensor!, data)
        .subscribe({
          next: () => this.router.navigate(['/sensorAccesos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
