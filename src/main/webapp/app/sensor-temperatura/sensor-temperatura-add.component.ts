import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SensorTemperaturaService } from 'app/sensor-temperatura/sensor-temperatura.service';
import { SensorTemperaturaDTO } from 'app/sensor-temperatura/sensor-temperatura.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-sensor-temperatura-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './sensor-temperatura-add.component.html'
})
export class SensorTemperaturaAddComponent implements OnInit {

  sensorTemperaturaService = inject(SensorTemperaturaService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  sensoreesValues?: Map<number,string>;

  addForm = new FormGroup({
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    ubicacion: new FormControl(null, [Validators.maxLength(100)]),
    temperatura: new FormControl(null, [Validators.required]),
    sensorees: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@sensorTemperatura.create.success:Sensor Temperatura was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.sensorTemperaturaService.getSensoreesValues()
        .subscribe({
          next: (data) => this.sensoreesValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new SensorTemperaturaDTO(this.addForm.value);
    this.sensorTemperaturaService.createSensorTemperatura(data)
        .subscribe({
          next: () => this.router.navigate(['/sensorTemperaturas'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
