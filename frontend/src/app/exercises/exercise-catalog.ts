import { Component, OnDestroy, inject, signal } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';

import { ExerciseApi } from './exercise-api';
import { Exercise } from './exercise.model';

@Component({
  selector: 'app-exercise-catalog',
  template: `
    <h2>Exercise catalog</h2>
    @switch (state()) {
      @case ('loading') {
        <p>Loading exercises…</p>
      }
      @case ('error') {
        <p class="error">
          Could not load exercises. Is the backend running on port 8080?
        </p>
      }
      @case ('empty') {
        <p>No exercises in the catalog.</p>
      }
      @case ('ready') {
        <ul>
          @for (ex of exercises(); track ex.id) {
            <li class="exercise-row">
              <strong>{{ ex.name }}</strong>
              @if (ex.muscleGroup) {
                <span class="badge">{{ ex.muscleGroup }}</span>
              }
              <span class="defaults">
                @if (ex.sets) {
                  {{ ex.sets }} sets
                }
                @if (ex.targetRepetitions) {
                  &middot; {{ ex.targetRepetitions }} reps
                }
              </span>
            </li>
          }
        </ul>
      }
    }
  `,
  styles: `
    h2 { font-size: 1.2rem; }
    ul { list-style: none; padding: 0; margin: 0; }
    .exercise-row {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      padding: 0.35rem 0;
      border-bottom: 1px solid #e5e5e5;
    }
    .defaults {
      color: #666;
      font-size: 0.85rem;
      margin-left: auto;
    }
    .badge {
      background: #eef;
      border-radius: 999px;
      font-size: 0.7rem;
      padding: 0.15rem 0.5rem;
    }
    .error { color: #b00020; }
  `,
})
export class ExerciseCatalog implements OnDestroy {
  private readonly api = inject(ExerciseApi);
  private readonly destroy$ = new Subject<void>();

  protected readonly exercises = signal<Exercise[]>([]);
  protected readonly state = signal<'loading' | 'ready' | 'error' | 'empty'>('loading');

  constructor() {
    this.api
      .getExercises()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (exercises) => {
          this.exercises.set(exercises);
          this.state.set(exercises.length > 0 ? 'ready' : 'empty');
        },
        error: () => this.state.set('error'),
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
  }
}
