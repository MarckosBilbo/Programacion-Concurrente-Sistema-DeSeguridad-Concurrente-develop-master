import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  nombre: string = 'admin';
  idUsuario: number = 1;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    if (this.nombre && this.idUsuario !== null) {
      this.authService.login(this.nombre, this.idUsuario).subscribe(response => {
        if (response === 'redirect:/admin/dashboard') {
          this.router.navigate(['/admin/dashboard']);
        } else if (response === 'redirect:/user/dashboard') {
          this.router.navigate(['/user/dashboard']);
        }
      });
    }
  }
}
