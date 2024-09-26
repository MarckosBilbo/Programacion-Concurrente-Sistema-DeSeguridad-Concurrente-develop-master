import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SensorTemperaturaService } from 'app/sensor-temperatura/sensor-temperatura.service';
import { SensorTemperaturaDTO } from 'app/sensor-temperatura/sensor-temperatura.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-sensor-temperatura-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './sensor-temperatura-edit.component.html'
})
export class SensorTemperaturaEditComponent implements OnInit {

  sensorTemperaturaService = inject(SensorTemperaturaService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  sensoreesValues?: Map<number,string>;
  currentIdSensor?: number;

  editForm = new FormGroup({
    idSensor: new FormControl({ value: null, disabled: true }),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    ubicacion: new FormControl(null, [Validators.maxLength(100)]),
    temperatura: new FormControl(null, [Validators.required]),
    sensorees: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@sensorTemperatura.update.success:Sensor Temperatura was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdSensor = +this.route.snapshot.params['idSensor'];
    this.sensorTemperaturaService.getSensoreesValues()
        .subscribe({
          next: (data) => this.sensoreesValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.sensorTemperaturaService.getSensorTemperatura(this.currentIdSensor!)
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
    const data = new SensorTemperaturaDTO(this.editForm.value);
    this.sensorTemperaturaService.updateSensorTemperatura(this.currentIdSensor!, data)
        .subscribe({
          next: () => this.router.navigate(['/sensorTemperaturas'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
