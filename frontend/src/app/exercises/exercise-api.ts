import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Exercise } from './exercise.model';

/**
 * API client for the exercise catalog.
 *
 * Paths include the backend's context path (/api/v1); in development the
 * request is forwarded to http://localhost:8080 by proxy.conf.json.
 */
@Injectable({ providedIn: 'root' })
export class ExerciseApi {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = '/api/v1';

  getExercises(): Observable<Exercise[]> {
    return this.http.get<Exercise[]>(`${this.baseUrl}/exercises`);
  }
}
