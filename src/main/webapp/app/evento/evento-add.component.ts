import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { EventoService } from 'app/evento/evento.service';
import { EventoDTO } from 'app/evento/evento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-evento-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './evento-add.component.html'
})
export class EventoAddComponent implements OnInit {

  eventoService = inject(EventoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  eventosValues?: Map<number,string>;
  eventossValues?: Map<number,string>;
  eventosssValues?: Map<number,string>;

  addForm = new FormGroup({
    nivelCriticidad: new FormControl(null, [Validators.maxLength(50)]),
    eventos: new FormControl(null, [Validators.required]),
    eventoss: new FormControl(null, [Validators.required]),
    eventosss: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@evento.create.success:Evento was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new EventoDTO(this.addForm.value);
    this.eventoService.createEvento(data)
        .subscribe({
          next: () => this.router.navigate(['/eventos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
