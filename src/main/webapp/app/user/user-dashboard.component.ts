import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SensorMonitorService } from '../services/sensor-monitor.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html'
})
export class UserDashboardComponent {
  constructor(private sensorMonitorService: SensorMonitorService, private router: Router) {}

  generateEvents() {
    this.sensorMonitorService.generateRandomEvents().subscribe(
        response => {
          console.log('Events generated:', response);
        },
        error => {
          console.error('Error generating events:', error);
        }
    );
  }

  goBack() {
    this.router.navigate(['/']);
  }
}
