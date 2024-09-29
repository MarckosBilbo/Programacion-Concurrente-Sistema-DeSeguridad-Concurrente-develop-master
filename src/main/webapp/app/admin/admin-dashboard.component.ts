import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { SensorMonitorService } from '../services/sensor-monitor.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent {
  newUserName: string = 'admin';

  constructor(private authService: AuthService, private sensorMonitorService: SensorMonitorService, private router: Router) {}

  createUser() {
    if (this.newUserName) {
      this.authService.createUser(this.newUserName).subscribe(
          response => {
            console.log('User created:', response);
          },
          error => {
            console.error('Error creating user:', error);
          }
      );
    }
  }

  generateEvents() {
    this.sensorMonitorService.generateRandomEvents().subscribe();
  }

  goBack() {
    this.router.navigate(['/']);
  }
}
