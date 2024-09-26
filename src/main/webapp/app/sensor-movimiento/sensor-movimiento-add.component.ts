import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { SensorMovimientoService } from 'app/sensor-movimiento/sensor-movimiento.service';
import { SensorMovimientoDTO } from 'app/sensor-movimiento/sensor-movimiento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-sensor-movimiento-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './sensor-movimiento-add.component.html'
})
export class SensorMovimientoAddComponent implements OnInit {

  sensorMovimientoService = inject(SensorMovimientoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  sensiorValues?: Map<number,string>;

  addForm = new FormGroup({
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    ubicacion: new FormControl(null, [Validators.maxLength(100)]),
    sensior: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@sensorMovimiento.create.success:Sensor Movimiento was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.sensorMovimientoService.getSensiorValues()
        .subscribe({
          next: (data) => this.sensiorValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new SensorMovimientoDTO(this.addForm.value);
    this.sensorMovimientoService.createSensorMovimiento(data)
        .subscribe({
          next: () => this.router.navigate(['/sensorMovimientos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
