import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SensorAccesoService } from 'app/sensor-acceso/sensor-acceso.service';
import { SensorAccesoDTO } from 'app/sensor-acceso/sensor-acceso.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-sensor-acceso-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './sensor-acceso-add.component.html'
})
export class SensorAccesoAddComponent implements OnInit {

  sensorAccesoService = inject(SensorAccesoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  sensValues?: Map<number,string>;

  addForm = new FormGroup({
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    ubicacion: new FormControl(null, [Validators.maxLength(100)]),
    respuesta: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    sens: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@sensorAcceso.create.success:Sensor Acceso was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.sensorAccesoService.getSensValues()
        .subscribe({
          next: (data) => this.sensValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new SensorAccesoDTO(this.addForm.value);
    this.sensorAccesoService.createSensorAcceso(data)
        .subscribe({
          next: () => this.router.navigate(['/sensorAccesos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
