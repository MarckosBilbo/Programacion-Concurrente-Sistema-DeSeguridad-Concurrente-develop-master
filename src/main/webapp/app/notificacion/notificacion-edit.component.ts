import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { NotificacionService } from 'app/notificacion/notificacion.service';
import { NotificacionDTO } from 'app/notificacion/notificacion.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-notificacion-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './notificacion-edit.component.html'
})
export class NotificacionEditComponent implements OnInit {

  notificacionService = inject(NotificacionService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  notificacionValues?: Map<number,string>;
  currentIdNotificacion?: number;

  editForm = new FormGroup({
    idNotificacion: new FormControl({ value: null, disabled: true }),
    mensage: new FormControl(null, [Validators.maxLength(255)]),
    notificacion: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@notificacion.update.success:Notificacion was updated successfully.`,
      NOTIFICACION_NOTIFICACION_UNIQUE: $localize`:@@Exists.notificacion.Notificacion:This Evento is already referenced by another Notificacion.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdNotificacion = +this.route.snapshot.params['idNotificacion'];
    this.notificacionService.getNotificacionValues()
        .subscribe({
          next: (data) => this.notificacionValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.notificacionService.getNotificacion(this.currentIdNotificacion!)
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
    const data = new NotificacionDTO(this.editForm.value);
    this.notificacionService.updateNotificacion(this.currentIdNotificacion!, data)
        .subscribe({
          next: () => this.router.navigate(['/notificacions'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
