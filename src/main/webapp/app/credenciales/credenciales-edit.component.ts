import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { CredencialesService } from 'app/credenciales/credenciales.service';
import { CredencialesDTO } from 'app/credenciales/credenciales.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-credenciales-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './credenciales-edit.component.html'
})
export class CredencialesEditComponent implements OnInit {

  credencialesService = inject(CredencialesService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  currentNombre?: number;

  editForm = new FormGroup({
    nombre: new FormControl({ value: null, disabled: true }),
    contrasenia: new FormControl(null, [Validators.required, Validators.maxLength(255)])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@credenciales.update.success:Credenciales was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentNombre = +this.route.snapshot.params['nombre'];
    this.credencialesService.getCredenciales(this.currentNombre!)
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
    const data = new CredencialesDTO(this.editForm.value);
    this.credencialesService.updateCredenciales(this.currentNombre!, data)
        .subscribe({
          next: () => this.router.navigate(['/credencialess'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
