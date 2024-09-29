import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SensorMonitorService {

    constructor(private http: HttpClient) { }

    generateRandomEvents(): Observable<void> {
        return this.http.get<void>('/api/sensorMonitor/generateRandomEvents');
    }
}
