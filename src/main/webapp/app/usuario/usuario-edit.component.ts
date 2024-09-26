import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { UsuarioService } from 'app/usuario/usuario.service';
import { UsuarioDTO } from 'app/usuario/usuario.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-usuario-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './usuario-edit.component.html'
})
export class UsuarioEditComponent implements OnInit {

  usuarioService = inject(UsuarioService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  usuarioValues?: Map<number,string>;
  usuariooValues?: Map<number,string>;
  currentIdUsuario?: number;

  editForm = new FormGroup({
    idUsuario: new FormControl({ value: null, disabled: true }),
    nombre: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    email: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
    activo: new FormControl(false),
    usuario: new FormControl(null, [Validators.required]),
    usuarioo: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@usuario.update.success:Usuario was updated successfully.`,
      USUARIO_USUARIO_UNIQUE: $localize`:@@Exists.usuario.Usuario:This Credenciales is already referenced by another Usuario.`,
      USUARIO_USUARIOO_UNIQUE: $localize`:@@Exists.usuario.Usuarioo:This Rol is already referenced by another Usuario.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdUsuario = +this.route.snapshot.params['idUsuario'];
    this.usuarioService.getUsuarioValues()
        .subscribe({
          next: (data) => this.usuarioValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.usuarioService.getUsuariooValues()
        .subscribe({
          next: (data) => this.usuariooValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.usuarioService.getUsuario(this.currentIdUsuario!)
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
    const data = new UsuarioDTO(this.editForm.value);
    this.usuarioService.updateUsuario(this.currentIdUsuario!, data)
        .subscribe({
          next: () => this.router.navigate(['/usuarios'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
