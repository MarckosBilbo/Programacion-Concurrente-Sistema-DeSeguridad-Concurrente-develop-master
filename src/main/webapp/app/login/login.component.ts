import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    idUsuario: number = 0;
    nombre: string = '';

    constructor(private authService: AuthService, private router: Router) {}

    onSubmit() {
        this.authService.login(this.nombre, this.idUsuario).subscribe(response => {
            if (response === 'Admin') {
                this.router.navigate(['/admin']);
            } else if (response === 'User') {
                this.router.navigate(['/user']);
            }
        }, error => {
            alert('Invalid credentials');
        });
    }
}
