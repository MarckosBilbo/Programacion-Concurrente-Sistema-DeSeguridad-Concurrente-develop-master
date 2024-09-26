import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { NotificacionService } from 'app/notificacion/notificacion.service';
import { NotificacionDTO } from 'app/notificacion/notificacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-notificacion-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './notificacion-add.component.html'
})
export class NotificacionAddComponent implements OnInit {

  notificacionService = inject(NotificacionService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  notificacionValues?: Map<number,string>;

  addForm = new FormGroup({
    mensage: new FormControl(null, [Validators.maxLength(255)]),
    notificacion: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@notificacion.create.success:Notificacion was created successfully.`,
      NOTIFICACION_NOTIFICACION_UNIQUE: $localize`:@@Exists.notificacion.Notificacion:This Evento is already referenced by another Notificacion.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.notificacionService.getNotificacionValues()
        .subscribe({
          next: (data) => this.notificacionValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new NotificacionDTO(this.addForm.value);
    this.notificacionService.createNotificacion(data)
        .subscribe({
          next: () => this.router.navigate(['/notificacions'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
