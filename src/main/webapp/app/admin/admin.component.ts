import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})
export class AdminComponent {
    nombre: string = ''; // Inicializar con un valor predeterminado

    constructor(private authService: AuthService, private router: Router) {}

    createUser() {
        this.authService.createUser(this.nombre).subscribe(response => {
            alert(response);
        });
    }

    logout() {
        this.router.navigate(['/login']);
    }
}
