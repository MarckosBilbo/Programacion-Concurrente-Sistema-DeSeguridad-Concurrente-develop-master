import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { EventoService } from 'app/evento/evento.service';
import { EventoDTO } from 'app/evento/evento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-evento-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './evento-edit.component.html'
})
export class EventoEditComponent implements OnInit {

  eventoService = inject(EventoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  eventosValues?: Map<number,string>;
  eventossValues?: Map<number,string>;
  eventosssValues?: Map<number,string>;
  currentIdEvento?: number;

  editForm = new FormGroup({
    idEvento: new FormControl({ value: null, disabled: true }),
    nivelCriticidad: new FormControl(null, [Validators.maxLength(50)]),
    eventos: new FormControl(null, [Validators.required]),
    eventoss: new FormControl(null, [Validators.required]),
    eventosss: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@evento.update.success:Evento was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentIdEvento = +this.route.snapshot.params['idEvento'];
    this.eventoService.getEventosValues()
        .subscribe({
          next: (data) => this.eventosValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.eventoService.getEventossValues()
        .subscribe({
          next: (data) => this.eventossValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.eventoService.getEventosssValues()
        .subscribe({
          next: (data) => this.eventosssValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.eventoService.getEvento(this.currentIdEvento!)
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
    const data = new EventoDTO(this.editForm.value);
    this.eventoService.updateEvento(this.currentIdEvento!, data)
        .subscribe({
          next: () => this.router.navigate(['/eventos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
