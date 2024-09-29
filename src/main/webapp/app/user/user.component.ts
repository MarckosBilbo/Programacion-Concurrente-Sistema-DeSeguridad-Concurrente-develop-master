import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SensorMonitorService } from '../services/sensor-monitor.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

  constructor(private sensorMonitorService: SensorMonitorService, private router: Router) {}

  logout() {
    this.router.navigate(['/login']);
  }
}
